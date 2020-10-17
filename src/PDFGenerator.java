import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
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
    private String buyerAddress;
    private String buyerCity;
    private Boolean paymentMethod;
    private int NIP;

    public PDFGenerator(int factureNumber, String buyer, String buyerAddress, String buyerCity, Boolean paymentMethod, int nip) {
        this.factureNumber = factureNumber;
        this.buyer = buyer;
        this.buyerAddress = buyerAddress;
        this.buyerCity = buyerCity;
        this.paymentMethod = paymentMethod;
        this.NIP = nip;
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
        addBuyer(document);
        addNIP(document);
        addPaymentMethod(document);

    }



    private void addPaymentMethod(Document document) throws IOException, DocumentException {
        Paragraph paragraph = new Paragraph();

        Chunk first = new Chunk ("Forma płatności:   ",setFont(true));
        Chunk second = new Chunk(paymentMethod(),setFont(false));
        paragraph.add(first); paragraph.add(second);

        document.add(paragraph);
        addEmptyParagraph(document,1);
    }

    private String paymentMethod() {
        if(paymentMethod == null)
            return "karta/gotówka";
        else if (!paymentMethod)
            return "gotówka";
        else
            return "karta";
    }

    private void addNIP(Document document) throws DocumentException, IOException {
        Paragraph paragraph = new Paragraph();

        Chunk first = new Chunk ("NIP:                      ",setFont(true));
        Chunk second = new Chunk(Integer.toString(NIP),setFont(false));
        paragraph.add(first); paragraph.add(second);

        document.add(paragraph);
        addEmptyParagraph(document,1);

    }

    private void addBuyer(Document document) throws DocumentException, IOException {
        Paragraph paragraph = new Paragraph();
        addBuyerP1(paragraph);
        Paragraph paragraph1 = new Paragraph();
        addBuyerP2(paragraph1);
        Paragraph paragraph2 = new Paragraph();
        addBuyerP3(paragraph2);

        document.add(paragraph);
        document.add(paragraph1);
        document.add(paragraph2);
        addEmptyParagraph(document,1);
    }

    private void addBuyerP1(Paragraph paragraph) throws IOException, DocumentException {
        Chunk first = new Chunk ("Nabywca:             ",setFont(true));
        Chunk second = new Chunk(buyer,setFont(false));
        paragraph.add(first); paragraph.add(second);
    }

    private void addBuyerP2(Paragraph paragraph) throws IOException, DocumentException {
        Chunk first = new Chunk ("                             ",setFont(false));
        Chunk second = new Chunk(buyerAddress,setFont(false));
        paragraph.add(first); paragraph.add(second);
    }

    private void addBuyerP3(Paragraph paragraph) throws IOException, DocumentException {
        Chunk first = new Chunk ("Adres                    ",setFont(true));
        Chunk second = new Chunk(buyerCity,setFont(false));
        paragraph.add(first); paragraph.add(second);
    }


    private void addSeller(Document document) throws DocumentException, IOException {
        Paragraph paragraph = new Paragraph();
        addSellerP1(paragraph);

        Paragraph paragraph1 = new Paragraph();
        addSellerP2(paragraph1);

        Paragraph paragraph2 = new Paragraph();
        addSellerP3(paragraph2);

        document.add(paragraph);
        document.add(paragraph1);
        document.add(paragraph2);

        addEmptyParagraph(document,1);

    }

    private void addSellerP1(Paragraph paragraph) throws IOException, DocumentException {
        Chunk first = new Chunk ("Sprzedawca:         ",setFont(true));
        Chunk second = new Chunk("AMER SPORTS POLAND SP. Z O.O.",setFont(false));
        paragraph.add(first); paragraph.add(second);
    }

    private void addSellerP2(Paragraph paragraph) throws IOException, DocumentException {
        Chunk first = new Chunk ("Adres:",setFont(true));
        Chunk second = new Chunk("                   ul. Opolska 110, 31-323 Kraków",setFont(false));
        paragraph.add(first); paragraph.add(second);
    }

    private void addSellerP3(Paragraph paragraph) throws IOException, DocumentException {
        Chunk first = new Chunk ("NIP:",setFont(true));
        Chunk second = new Chunk("                      526 285 53 43",setFont(false));
        paragraph.add(first); paragraph.add(second);
    }


    private void addTitle(Document document) throws IOException, DocumentException {
        Paragraph paragraph = new Paragraph("FAKTURA VAT " + factureNumber + "/" + dateNumber() + "/S",setFont(16));
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);
        addEmptyParagraph(document,1);
    }

    private void addShopInformation(Document document) throws DocumentException, IOException {
        Chunk glue = new Chunk(new VerticalPositionMark());
        Paragraph paragraph = new Paragraph("Miejscowość:        " + "     Sosnowiec",setFont(false));
        paragraph.add(new Chunk(glue));
        paragraph.add(new Paragraph("Oryginał/Kopia",setFont(false)));
        Paragraph paragraph1 = new Paragraph("Data wystawienia:     " + addDate(),setFont(false));
        document.add(paragraph);
        document.add(paragraph1);
        addEmptyParagraph(document,1);
    }

    private Font setFont(boolean isBold) throws IOException, DocumentException {
        int defaultSize = 10;
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        if(isBold)
            return new Font(helvetica,defaultSize,Font.BOLD);
        else
            return new Font(helvetica,defaultSize);
    }

    private Font setFont(int size) throws IOException, DocumentException {
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        return new Font(helvetica, size, Font.BOLD);
    }

    private void addLogo(Document document) throws IOException, DocumentException {
        Image logo = Image.getInstance("LogoAmerSports.jpg");
        logo.setAlignment(Image.MIDDLE);
        scale(logo);
        document.add(logo);
        addEmptyParagraph(document,1);
    }

    private void scale (Image image) {
        float imageSize = 80;
        float scale = image.getWidth() / image.getHeight();
        image.scaleAbsolute(imageSize*scale, imageSize);
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
