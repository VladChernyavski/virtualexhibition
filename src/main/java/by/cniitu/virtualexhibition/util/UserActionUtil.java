package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.to.UserActionTo;
import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UserActionUtil {

    public static File saveActionsToFile(List<UserActionTo> userActions){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("User Action Sheets");

        int rownum = 0;
        Cell cell;
        Row row;

        XSSFCellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rownum);

        // Name
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Name");
        cell.setCellStyle(style);
        // Email
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Email");
        cell.setCellStyle(style);
        // FileName
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("FileName");
        cell.setCellStyle(style);
        // FileType
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("FileType");
        cell.setCellStyle(style);
        // ActionType
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("ActionType");
        cell.setCellStyle(style);
        // Date
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Time");
        cell.setCellStyle(style);


        // Data
        for (UserActionTo action : userActions) {
            rownum++;
            row = sheet.createRow(rownum);

            // Name (A)
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(action.getName());
            // Email (B)
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(action.getEmail());
            // FileName (C)
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(action.getFileName());
            // FileType (D)
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(action.getFileType());
            // ActionType (E)
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue(action.getActionType());
            // Time (F)
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(action.getTime());
        }

        File tempFile = null;
        try {
            File dir = new File(FileUtil.getFilePath("file") + "//temp//statistics");
            if (!dir.exists()){
                dir.mkdirs();
            }
            tempFile = File.createTempFile("temp", ".xlsx", dir);
            FileOutputStream outFile = new FileOutputStream(tempFile);
            workbook.write(outFile);
            outFile.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Created file: " + tempFile);
        return tempFile;
    }

    private static XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

}
