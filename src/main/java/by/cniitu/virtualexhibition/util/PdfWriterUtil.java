package by.cniitu.virtualexhibition.util;

import by.cniitu.virtualexhibition.to.UserActionTo;
import by.cniitu.virtualexhibition.web.controller.file.FileUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class PdfWriterUtil {

    public static File writeActionToPdf(List<UserActionTo> actionTos){

        Document document = new Document();

        File tempFile = null;
        try {

            File dir = new File(FileUtil.getFilePath("file") + "//temp//statistics");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            tempFile = File.createTempFile("temp", ".pdf", dir);
            FileOutputStream outFile = new FileOutputStream(tempFile);

            com.itextpdf.text.pdf.PdfWriter.getInstance(document, outFile);

            document.setPageSize(PageSize.A4.rotate());
            document.open();

            Font font = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD, BaseColor.BLACK);
            Paragraph paragraph = new Paragraph("IT IS A HEADER", font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(10);

            Image img = Image.getInstance(FileUtil.getFilePath("image") + "grafik.png");
            img.setAlignment(Element.ALIGN_CENTER);
            img.scaleAbsolute(440,340);

            Paragraph paragraph2 = new Paragraph("Will be developed soon using python", font);
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            paragraph2.setSpacingAfter(10);
            paragraph2.setSpacingBefore(10);

            PdfPTable table = new PdfPTable(6);
            addTableHeader(table);
            for(UserActionTo to : actionTos) {
                addRows(table, to);
            }


            document.add(paragraph);
            document.add(table);
            document.newPage();
            document.add(paragraph2);
            document.add(img);
            document.close();

        } catch (IOException | DocumentException e){
            e.printStackTrace();
        }
        System.out.println("Created file: " + tempFile);
        return tempFile;
    }

    private static void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Email", "FileName", "FileType", "ActionType", "Time")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBackgroundColor(BaseColor.WHITE);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private static void addRows(PdfPTable table, UserActionTo to) {
        PdfPCell name = new PdfPCell(new Phrase(to.getName()));
        name.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(name);

        PdfPCell email = new PdfPCell(new Phrase(to.getEmail()));
        email.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(email);

        PdfPCell fileName = new PdfPCell(new Phrase(to.getFileName()));
        fileName.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(fileName);

        PdfPCell fileType = new PdfPCell(new Phrase(to.getFileType()));
        fileType.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(fileType);

        PdfPCell actionType = new PdfPCell(new Phrase(to.getActionType()));
        actionType.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(actionType);

        PdfPCell time = new PdfPCell(new Phrase(to.getTime()));
        time.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(time);
    }

}
