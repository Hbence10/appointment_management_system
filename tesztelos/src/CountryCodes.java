import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class CountryCodes {

    public static void main(String[] args) {
        ArrayList<Integer> codes = new ArrayList<>();
        ArrayList<String> countries = new ArrayList<>();

        try {
            File txt = new File("src/ListOfCountryCodes.txt");
            Scanner reader = new Scanner(txt);

            while (reader.hasNextLine()) {
                String[] splittedRow = reader.nextLine().split(" ", 2);
                codes.add(Integer.valueOf(splittedRow[0]));
                countries.add(splittedRow[1]);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println(codes.size() == countries.size());
        for (int i = 0; i < codes.size(); i++) {
            String base = "INSERT INTO `phone_country_codes`(`country_code`, `country_name`) VALUES (";
            System.out.println(base + codes.get(i) + ", \"" + countries.get(i)+ "\");");
        }
    }
}
