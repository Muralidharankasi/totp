import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import org.apache.commons.codec.EncoderException;
import totp.GenerateQrCode;
import totp.SecretKeyGenerator;
import totp.TotpUtil;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws Exception {
        initiate();
        while (true){
            TotpUtil totpUtil = new TotpUtil(GenerateQrCode.getSecret());
            System.out.println(totpUtil.otp());
            System.out.println(totpUtil.validTill());
            Thread.sleep(10000);
        }
    }

    private static void initiate() throws IOException, EncoderException, NotFoundException, WriterException {
        SecretKeyGenerator.main(null);
        GenerateQrCode.main(null);
    }
}



