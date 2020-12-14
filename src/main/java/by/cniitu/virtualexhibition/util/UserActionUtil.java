package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.to.UserActionTo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class UserActionUtil {

    public static File saveActionsToFile(List<UserActionTo> userActions){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("User Action Sheets");

        int rownum = 0;
        Cell cell;
        Row row;

        HSSFCellStyle style = createStyleForTitle(workbook);
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
        cell.setCellValue("Date");
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
            // Date (F)
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue(action.getDate());
        }

        File tempFile = null;
        FileOutputStream outFile = null;
        try {
            //todo change tempFile
            tempFile = File.createTempFile("temp", ".xls", new File("C:\\Users\\u108\\Desktop\\EXCEL"));
//            tempFile = File.createTempFile("temp", "xls", new File("/opt/temp"));
            outFile = new FileOutputStream(tempFile);
            workbook.write(outFile);
            outFile.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Created file: " + tempFile);
        return tempFile;
    }

    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

}
