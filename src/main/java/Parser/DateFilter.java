package Parser;

import com.google.gson.internal.bind.util.ISO8601Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

public class DateFilter {
    /**
     * 0 - воскр
     * 1 - понедельник
     * 2- вторинк
     * 3 - среда
     * 4 - четверг
     * 5 - пятница
     * 6 - суббота
     * @throws ParseException
     */
    public static void execute() throws ParseException {
        String startDateStr = "2020-05-18";
        String endDateStr = "2020-05-26";
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDateStr);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDateStr);

//        System.out.println(date1);

        LocalDate startDate = LocalDate.of(date1.getYear()+1900, date1.getMonth() +1, date1.getDate()); //год, мес, день
        LocalDate endDate = LocalDate.of(date2.getYear()+1900, date2.getMonth() +1, date2.getDate());

        int workdays = 0;
        if (startDate.isEqual(endDate)) {
            return;
        }
//        System.out.println(startDate);
//        System.out.println(endDate);

        while(startDate.isBefore(endDate) || startDate.equals(endDate)) {
            if (DayOfWeek.MONDAY.equals(startDate.getDayOfWeek())
                    || DayOfWeek.THURSDAY.equals(startDate.getDayOfWeek())
                    || DayOfWeek.WEDNESDAY.equals(startDate.getDayOfWeek())
                    || DayOfWeek.TUESDAY.equals(startDate.getDayOfWeek())
                    || DayOfWeek.FRIDAY.equals(startDate.getDayOfWeek()))
            {
                workdays++;
            }
            startDate = startDate.plusDays(1);
        }

        System.out.println(workdays);

    }
}
