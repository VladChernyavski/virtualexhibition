package by.cniitu.virtualexhibition;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BarChart {

    public static List<To> tos = new ArrayList<To>() {{
        add(new To("file", 1823));
        add(new To("file2", 1231));
        add(new To("file3", 1233));
        add(new To("file4", 1323));
        add(new To("file5", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
        add(new To("file6", 1323));
    }};

//    public static void main(String[] args) throws IOException {
//        Workbook wb = new XSSFWorkbook();
//        Sheet sheet = wb.createSheet("Sheet1");
//
//        int rownum = 0;
//        Row row;
//        Cell cell;
//
//        row = sheet.createRow(0);
//        row.createCell(0, CellType.STRING).setCellValue("FileName");
//        row.createCell(1, CellType.NUMERIC).setCellValue("ACTIONS");
//
//        for (To t : tos) {
//            rownum++;
//            row = sheet.createRow(rownum);
//            cell = row.createCell(0, CellType.STRING);
//            cell.setCellValue(t.getFileName());
//            cell = row.createCell(1, CellType.NUMERIC);
//            cell.setCellValue(t.getActions());
//        }
//
//        Drawing drawing = sheet.createDrawingPatriarch();
//        ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 5, 5, 26, 32);
//
//        Chart chart = drawing.createChart(anchor);
//
//        CTChart ctChart = ((XSSFChart) chart).getCTChart();
//        CTPlotArea ctPlotArea = ctChart.getPlotArea();
//        CTBarChart ctBarChart = ctPlotArea.addNewBarChart();
//        CTBoolean ctBoolean = ctBarChart.addNewVaryColors();
//        ctBoolean.setVal(true);
//        ctBarChart.addNewBarDir().setVal(STBarDir.COL);
//
//        for (int r = 2; r < tos.size() + 2; r++) {
//            CTBarSer ctBarSer = ctBarChart.addNewSer();
//            CTSerTx ctSerTx = ctBarSer.addNewTx();
//            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
//            ctStrRef.setF("Sheet1!$A" + r);
//            ctBarSer.addNewIdx().setVal(r - 2);
//            CTAxDataSource cttAxDataSource = ctBarSer.addNewCat();
//            ctStrRef = cttAxDataSource.addNewStrRef();
//            ctStrRef.setF("Sheet1!$A$1");
//            CTNumDataSource ctNumDataSource = ctBarSer.addNewVal();
//            CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
//            ctNumRef.setF("Sheet1!$B$" + r );
//
//            //at least the border lines in Libreoffice Calc ;-)
//            ctBarSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[]{0, 0, 0});
//        }
//
//        //telling the BarChart that it has axes and giving them Ids
//        ctBarChart.addNewAxId().setVal(123456);
//        ctBarChart.addNewAxId().setVal(123457);
//
//        //cat axis
//        CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
//        ctCatAx.addNewAxId().setVal(123456); //id of the cat axis
//        CTScaling ctScaling = ctCatAx.addNewScaling();
//        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
//        ctCatAx.addNewDelete().setVal(false);
//        ctCatAx.addNewAxPos().setVal(STAxPos.B);
//        ctCatAx.addNewCrossAx().setVal(123457); //id of the val axis
//        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
//
//        //val axis
//        CTValAx ctValAx = ctPlotArea.addNewValAx();
//        ctValAx.addNewAxId().setVal(123457); //id of the val axis
//        ctScaling = ctValAx.addNewScaling();
//        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
//        ctValAx.addNewDelete().setVal(false);
//        ctValAx.addNewAxPos().setVal(STAxPos.L);
//        ctValAx.addNewCrossAx().setVal(123456); //id of the cat axis
//        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);
//
//        //legend
//        CTLegend ctLegend = ctChart.addNewLegend();
//        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
//        ctLegend.addNewOverlay().setVal(false);
//
//        System.out.println(ctChart);
//
//        FileOutputStream fileOut = new FileOutputStream("C:\\Users\\u108\\Desktop\\EXCEL\\BarChart.xlsx");
//        wb.write(fileOut);
//        fileOut.close();
//    }

}