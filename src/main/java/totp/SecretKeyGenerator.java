package totp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class SecretKeyGenerator {
    public static void main(String[] args) throws IOException {

        // create a string of all characters
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        int length = 16;

        for (int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        String randomString = sb.toString();
        String secretFilePath = System.getProperty("user.dir")+"/secretKey";
        System.out.println(secretFilePath);
        try {
            FileWriter fileWriter = new FileWriter(secretFilePath);
            fileWriter.write(randomString);
            fileWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}