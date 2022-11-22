package totp;

import java.util.Date;

public class TotpUtil {
    static final String HOTP_HMAC_ALGORITHM = "HmacSHA1";
    long counter;
    private final String secretKey;

    public TotpUtil(String secretKey) {
        long timeStamp = System.currentTimeMillis() / 1000; // mills to unix time.
        counter = timeStamp / 30; // for 30s
        this.secretKey = secretKey;
    }

    public String otp() throws Exception {
        int passwordLength = 6;  // choosing the number of digits to show
        OtpGenerator otpGenerator = new OtpGenerator(HOTP_HMAC_ALGORITHM, secretKey, counter);
        return otpGenerator.generate(passwordLength);
    }

    public String validTill() {
        long validUpto = (counter + 1) * 30 * 1000;
        return new Date(validUpto).toString();
    }
}
