import java.time.LocalDate;
import java.util.List;

public class DateIteration {

    public static void main(String[] args) {
        LocalDate startDate = LocalDate.parse("2025-10-10");
        LocalDate endDate = LocalDate.parse("2025-10-15");

        List<LocalDate> dateList = startDate.datesUntil(endDate).toList();
        for(int i = 0; i < dateList.size(); i++){
            System.out.println(dateList.get(i));
        }

        System.out.println(endDate.getDayOfWeek());
    }
}
