package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.to.FileActionTo;
import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileActionUtil {

    public static File saveFileActionToFile(List<FileActionTo> fileActionTos) {
        int size = fileActionTos.size();
        int width = 12 + size + size ;

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Fileactions");

        int rownum = 0;
        Row row;
        Cell cell;

        row = sheet.createRow(0);
        row.createCell(0, CellType.STRING).setCellValue("FileName");
        row.createCell(1, CellType.NUMERIC).setCellValue("Action");

        for (FileActionTo t : fileActionTos) {
            rownum++;
            row = sheet.createRow(rownum);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(t.getFileName());
            cell = row.createCell(1, CellType.NUMERIC);
            cell.setCellValue(t.getActions());
        }

        sheet = wb.getSheetAt(0);

        // determine the type of the category axis from it's first category value (value in A2 in this case)
        XDDFDataSource date = null;
        CellType type = CellType.ERROR;
        row = sheet.getRow(1);
        if (row != null) {
            cell = row.getCell(0);
            if (cell != null) {
                type = cell.getCellType();
                if (type == CellType.STRING) {
                    date = XDDFDataSourcesFactory.fromStringCellRange((XSSFSheet) sheet, new CellRangeAddress(1, size, 0, 0));
                } else if (type == CellType.NUMERIC) {
                    date = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet, new CellRangeAddress(1, size, 0, 0));
                } else if (type == CellType.FORMULA) {
                    type = cell.getCachedFormulaResultType();
                    if (type == CellType.STRING) {
                        date = XDDFDataSourcesFactory.fromStringCellRange((XSSFSheet) sheet, new CellRangeAddress(1, size, 0, 0));
                    } else if (type == CellType.NUMERIC) {
                        date = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet, new CellRangeAddress(1, size, 0, 0));
                    }
                }
            }
        }

        if (date != null) { // if no type of category axis found, don't create a chart at all
            XDDFNumericalDataSource<Double> high = XDDFDataSourcesFactory.fromNumericCellRange((XSSFSheet) sheet, new CellRangeAddress(1, size, 1, 1));

            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 6, 5, width, 28);

            XSSFChart chart = drawing.createChart(anchor);

            // bar chart
            XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
            XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
            leftAxis.setTitle("Number of actions");
            leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

            // category axis crosses the value axis between the strokes and not midpoint the strokes
            leftAxis.setCrossBetween(AxisCrossBetween.BETWEEN);

            XDDFChartData data = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
            XDDFChartData.Series series1 = data.addSeries(date, high);
            series1.setTitle("action", new CellReference(sheet.getSheetName(), 0, 1, true, true));
            chart.plot(data);

            XDDFBarChartData bar = (XDDFBarChartData) data;
            bar.setBarDirection(BarDirection.COL);

            // looking for "Stacked Bar Chart"? uncomment the following line
            bar.setBarGrouping(BarGrouping.STACKED);

            // correcting the overlap so bars really are stacked and not side by side
            chart.getCTChart().getPlotArea().getBarChartArray(0).addNewOverlap().setVal((byte) 100);


            // add data labels
            for (int s = 0; s < 1; s++) {
                chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(s).addNewDLbls();
                chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(s).getDLbls()
                        .addNewDLblPos().setVal(org.openxmlformats.schemas.drawingml.x2006.chart.STDLblPos.CTR);
                chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(s).getDLbls().addNewShowVal().setVal(true);
                chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(s).getDLbls().addNewShowLegendKey().setVal(false);
                chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(s).getDLbls().addNewShowCatName().setVal(false);
                chart.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(s).getDLbls().addNewShowSerName().setVal(false);
            }

        }


        File tempFile = null;
        try {
            File dir = new File(FileUtil.getFilePath("file") + "//temp//actions");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            tempFile = File.createTempFile("temp", ".xlsx", dir);
            FileOutputStream outFile = new FileOutputStream(tempFile);
            wb.write(outFile);
            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Created file: " + tempFile);
        return tempFile;
    }

}
