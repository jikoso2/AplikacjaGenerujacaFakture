import com.itextpdf.text.DocumentException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, DocumentException {

        PDFGenerator gen = new PDFGenerator(123,"Jarosław Czerniak");
        gen.finalGenerator();

    }


}
