import java.util.ArrayList;
import java.util.Arrays;

public class Phone {

    public static boolean phoneValidator(String phone) {

        if (phone.length() != 12) {
            return false;
        } else if (phone.charAt(0) != '+') {
            return false;
        }

        ArrayList<String> supplierIds = new ArrayList<String>(Arrays.asList("30", "20", "70", "50", "31"));
        if (!supplierIds.contains(phone.substring(3, 5))) {
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(phoneValidator("+36706285232"));
        System.out.println(phoneValidator("+36106285232"));
    }


//    https://hu.wikipedia.org/wiki/Magyar_mobilszolg%C3%A1ltat%C3%B3k
}
