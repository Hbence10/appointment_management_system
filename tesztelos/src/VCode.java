import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class VCode {

    public static String generateVerificationCode() {
        String code = "";
        String specialCharacters = "\"!@#$%^&*()-_=+[]{};:,.?/\"";
        ArrayList<String> characters = new ArrayList<String>(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));

        for (int i = 97; i <= 122; i++) {
            characters.add(String.valueOf((char) i));
        }

        for (int i = 0; i < specialCharacters.length(); i++) {
            characters.add(String.valueOf(specialCharacters.charAt(i)));
        }

        while(code.length() != 10){
            Random random = new Random();
            code += characters.get(random.nextInt(characters.size()));
        }

        return code;
    }

    public static void main(String[] args) {

        System.out.println(generateVerificationCode());
        System.out.println(generateVerificationCode());
        System.out.println(generateVerificationCode());
    }
}
