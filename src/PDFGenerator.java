import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Stream;

public class PDFGenerator {

            public void finalGenerator () throws FileNotFoundException, DocumentException {
                var doc = new Document();
                int number = 123;
                PdfWriter.getInstance(doc, new FileOutputStream("Faktura numer " + number + ".pdf"));
                doc.open();
                addFactureTitle(doc,number);
                addAnything(doc);
                doc.close();
            }

    private void addFactureTitle(Document document,int number) {
        document.addTitle("Faktura numer: " + number);
    }

    private void addAnything(Document document) throws DocumentException {
                document.add(new Paragraph("Title of the document"));
                Paragraph paragraph = new Paragraph();
                addEmptyLine(paragraph,2);
                document.add(paragraph);
                document.add (new Paragraph("Sprawdzenie"));
    }

    private void addEmptyLine(Paragraph paragraph,int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph("  "));
        }
    }

}
