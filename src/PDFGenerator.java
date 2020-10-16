import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PDFGenerator {
    private int factureNumber;
    private String buyer;

    public PDFGenerator(int factureNumber, String buyer) {
        this.factureNumber = factureNumber;
        this.buyer = buyer;
    }

    public void finalGenerator () throws IOException, DocumentException {
                var doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream(fileName()));
                doc.open();
                addFactureTitle(doc,factureNumber);
                addContent(doc);
                doc.close();
            }

    private void addFactureTitle(Document document,int number) {
        document.addTitle("Faktura numer: " + number);
    }

    private void addContent(Document document) throws DocumentException, IOException {

        addLogo(document);
        addShopInformation(document);
        addTitle(document);
        addSeller(document);

    }

    private void addSeller(Document document) throws DocumentException {
        Paragraph paragraph = new Paragraph("Sprzedawca:    AMER SPORTS POLAND SP. Z O.O.");
        Paragraph paragraph1 = new Paragraph("Adres:              ul.Opolska 110, 31-323 Kraków");
        Paragraph paragraph2 = new Paragraph("NIP:                  526 285 53 43");
        document.add(paragraph);
        document.add(paragraph1);
        document.add(paragraph2);

    }

    private void addTitle(Document document) throws IOException, DocumentException {
        Paragraph paragraph = new Paragraph("FAKTURA VAT " + factureNumber + "/" + dateNumber() + "/S",setFont(16));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        addEmptyParagraph(document,2);
    }

    private void addShopInformation(Document document) throws DocumentException, IOException {
        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph paragraph = new Paragraph("Miejscowość:        " + "     Sosnowiec",setFont());
        paragraph.add(new Chunk(glue));
        paragraph.add(new Paragraph("Oryginał/Kopia",setFont()));
        Paragraph paragraph1 = new Paragraph("Data wystawienia:     " + addDate());
        document.add(paragraph);
        document.add(paragraph1);
        addEmptyParagraph(document,2);
    }

    private Font setFont() throws IOException, DocumentException {
        int defaultSize = 12;
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        return new Font(helvetica,defaultSize);
    }

    private Font setFont(int size) throws IOException, DocumentException {
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        return new Font(helvetica,size);
    }

    private void addLogo(Document document) throws IOException, DocumentException {
        Image logo = Image.getInstance("LogoAmerSports.jpg");
        logo.setAlignment(Image.MIDDLE);
        document.add(logo);
        addEmptyParagraph(document,1);
    }

    private void addEmptyParagraph(Document document, int size) throws DocumentException {
        for (int i = 0; i < size; i++) {
            document.add (new Paragraph(" "));
        }
    }

    private String addDate() {
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd" );
        return sdf1.format(nowDate);
    }

    private String dateNumber() {
        Date nowDate = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yy" );
        return sdf1.format(nowDate);
    }

    private String fileName() {
        return "Faktura " + factureNumber +"-"+ dateNumber()+" "+ buyer +".pdf";
    }

}
