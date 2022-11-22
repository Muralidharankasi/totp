package totp;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base32;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GenerateQrCode {
    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }


    public static void main(String args[]) throws WriterException, IOException, NotFoundException, EncoderException {
        String data = "otpauth://totp/ilarum.work@gmail.com?secret=" + getSecret() + "&issuer=Im Studios";
        String path = System.getProperty("user.dir") + "/Quote.png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        generateQRcode(data, path, charset, hashMap, 200, 200);
        System.out.println("QR Code created successfully.");
    }

    public static String getSecret() throws FileNotFoundException {
        String generatedKey = getGeneratedSecretKey();
        Base32 base32 = new Base32();
        return base32.encodeAsString(generatedKey.getBytes());
    }

    public static String getGeneratedSecretKey() throws FileNotFoundException {
        File myObj = new File(System.getProperty("user.dir") + "/secretKey");
        Scanner myReader = new Scanner(myObj);
        return myReader.nextLine();
    }
}
