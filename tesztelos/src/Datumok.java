import java.time.LocalDate;

public class Datumok {

    public static void main(String[] args) {
        LocalDate date1 = LocalDate.parse("2025-09-05");
        LocalDate date2 = LocalDate.parse("2025-08-05");

        System.out.println(date2.compareTo(date1)); //date2 kesobb van mint date1 --> eredmeny: 1


        System.out.println(date1.compareTo(date2));//date1 korabban van mint date2 --> eredmeny: -1
    }
}
