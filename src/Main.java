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

public class Main {
    public static void main(String[] args) throws FileNotFoundException, DocumentException {

        PDFGenerator gen = new PDFGenerator(123,"Jarosław","Czerniak");
        gen.finalGenerator();

        Date nowDate = new Date();
        System.out.println("Domyslny:" + nowDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd" );
        System.out.println(sdf1.format(nowDate));
    }


}
