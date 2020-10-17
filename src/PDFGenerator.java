import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;


import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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
                var doc = new Document(PageSize.A4,25,25,25,25);
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
        addItemTable(document);
        addSummary(document);
        addSignature(document);

    }

    private void addSignature(Document document) throws IOException, DocumentException {

        addEmptyParagraph(document,3);

        Paragraph paragraph1 = new Paragraph("_ _ _ _ _ _ _ _ _ _ _ _ _ _");
        paragraph1.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph1);

        Paragraph paragraph = new Paragraph("pieczątka i podpis         ",setFont(false));
        paragraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(paragraph);

    }

    private void addSummary(Document document) throws IOException, DocumentException {
        addEmptyParagraph(document,1);
        Paragraph paragraph = new Paragraph("Do zapłaty:              " + roundValues(3 * 133.30F) + " zł",setFont(true));
        document.add(paragraph);

        Paragraph paragraph1 = new Paragraph();
        Chunk first = new Chunk ("Słownie złotych:       ",setFont(true));
        Chunk second = new Chunk(NumberTranslation.translacja((int) (3* 133.30F))+"złotych",setFont(false));
        paragraph1.add(first); paragraph1.add(second);

        document.add(paragraph1);

    }


    private void addItemTable(Document document) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(7);

        tableTemplate(table);
        setColumnWidths(table);

        table.setHeaderRows(1);

        createCellToTable(table, "1.");
        createCellToTable(table, "BUTY SPEEDCROSS R.7");
        createCellToTable(table, Integer.toString(3));
        createCellToTable(table, roundValues(3 * 133.30F / 1.23F) + " zł");
        createCellToTable(table, roundValues((3 * 133.30F) - (3 * 133.30F / 1.23F)) + " zł");
        createCellToTable(table, roundValues(133.30F) +" zł");
        createCellToTable(table, roundValues(3 * 133.30F) + " zł");


        addLastTableRow(table);

        document.add(table);
    }

    private String roundValues(float value){
        value *= 100;
        value = Math.round(value);
        value /= 100;
        DecimalFormat myFormatter = new DecimalFormat("###.00");
        return myFormatter.format(value);
    }

    private void addLastTableRow(PdfPTable table) throws DocumentException, IOException {

        createCellToLastRow(table, " ");
        createCellToLastRow(table, " ");
        createCellToLastRow(table, " ");
        createCellToLastRow(table, roundValues(3 * 133.30F / 1.23F) + " zł");
        createCellToLastRow(table, roundValues((3 * 133.30F) - (3 * 133.30F / 1.23F)) + " zł");
        createCellToLastRow(table, roundValues(133.30F) +" zł");
        createCellToLastRow(table, roundValues(3 * 133.30F) + " zł");

    }

    private void setColumnWidths(PdfPTable table) throws DocumentException {
        float[] columnWidths = new float[]{40f,255f,40f,65f,65f,73f,65f};
        table.setWidthPercentage(columnWidths,PageSize.A4);
    }

    private void tableTemplate(PdfPTable table) throws IOException, DocumentException {
        createCellToTableTemplate(table,"L.P");
        createCellToTableTemplate(table,"Nazwa towaru");
        createCellToTableTemplate(table,"Ilość");
        createCellToTableTemplate(table,"Wartość netto");
        createCellToTableTemplate(table,"Podatek VAT (23%)");
        createCellToTableTemplate(table,"Wartość jednostkowa");
        createCellToTableTemplate(table,"Wartość brutto");
    }

    private void createCellToTableTemplate(PdfPTable table,String name) throws IOException, DocumentException {
        Font font =setFont(false);
        font.setSize(11);
        Paragraph phr = new Paragraph(name,font);
        PdfPCell cell = new PdfPCell(phr);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPaddingBottom(7);
        cell.setPaddingTop(7);
        table.addCell(cell);
    }

    private void createCellToTable(PdfPTable table,String name) throws IOException, DocumentException {
        Paragraph phr = new Paragraph(name,setFont(false));
        PdfPCell cell = new PdfPCell(phr);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(6);
        cell.setPaddingTop(3);
        table.addCell(cell);
    }

    private void createCellToLastRow(PdfPTable table,String name) throws IOException, DocumentException {
        Paragraph phr = new Paragraph(name,setFont(10));
        PdfPCell cell = new PdfPCell(phr);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPaddingBottom(4);
        cell.setPaddingTop(2);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(cell);
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
