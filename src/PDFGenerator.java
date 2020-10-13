import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class PDFGenerator {
    private int factureNumber;
    private String name;
    private String secondname;

    public PDFGenerator(int factureNumber, String name, String secondname) {
        this.factureNumber = factureNumber;
        this.name = name;
        this.secondname = secondname;
    }

    public void finalGenerator () throws FileNotFoundException, DocumentException {
                var doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream("Faktura " + factureNumber +"-"+ dateNumber()+" "+name+" "+secondname +".pdf"));
                doc.open();
                addFactureTitle(doc,factureNumber);
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
                paragraph.add("Faktura wystawiona dnia: ");
                addDate(paragraph);
                document.add(paragraph);
                document.add (new Paragraph("Sprawdzenie"));

    }

    private void addEmptyLine(Paragraph paragraph,int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph("  "));
        }
    }

    private void addDate(Paragraph paragraph) {
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd" );
        paragraph.add(new Paragraph(sdf1.format(nowDate)));
    }

    private String dateNumber() {
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yy" );
        return sdf1.format(nowDate);
    }

}
