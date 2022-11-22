package totp;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class OtpGenerator {

    private final Mac algorithmInstance;
    private final String secretKey;
    private final long counter;

    public OtpGenerator(final String algorithm, String secretKey, long counter) throws NoSuchAlgorithmException {
        try {
            this.algorithmInstance = Mac.getInstance(algorithm);
            this.secretKey = secretKey;
            this.counter = counter;
        } catch (final NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException(e);
        }
    }

    public String generate(int passwordLength) throws Exception {
        try {
            byte[] hash = generateHash();
            return getDigitsFromHash(hash, passwordLength);
        } catch (Exception e) {
            throw new TotpException("Otp Generation Failed...", e);
        }
    }

    private byte[] generateHash() throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] data = new byte[8];
        long value = counter;
        for (int i = 8; i-- > 0; value >>>= 8) {
            data[i] = (byte) value;
        }

        String algorithmName = this.algorithmInstance.getAlgorithm();
        Base32 codec = new Base32();
        byte[] decodedKey = codec.decode(secretKey);
        SecretKeySpec key = new SecretKeySpec(decodedKey, algorithmName);
        Mac mac = Mac.getInstance(algorithmName);
        mac.init(key);
        return mac.doFinal(data);
    }

    private String getDigitsFromHash(byte[] hash, int passwordLength) {
        String digitFormat = "%0" + passwordLength + "d";
        int offset = hash[hash.length - 1] & 0xF;

        long truncatedHash = 0;

        for (int i = 0; i < 4; ++i) {
            truncatedHash <<= 8;
            truncatedHash |= (hash[offset + i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= Math.pow(10, passwordLength);
        return String.format(digitFormat, truncatedHash);
    }

}
