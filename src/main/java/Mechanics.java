import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class Mechanics {

    public static ArrayList<Integer> hoursPerDay(int howMany) {
        ArrayList<Integer> myWorkHour = new ArrayList<Integer>();
        while (howMany > 3) {
            Random rand = new Random();
            int hour = rand.nextInt(2) + 1;
            myWorkHour.add(hour);
            howMany -= hour;
        }
        myWorkHour.add(howMany);
        return myWorkHour;
    }

    public static ArrayList<String> myWorks() {
        ArrayList<String> works = new ArrayList();
        works.add("przygotowanie ogłoszeń o wynajmie");
        works.add("aktualizacja ogłoszeń o wynajmie");
        works.add("udzielanie informacji na temat wynajmowanych lokali");
        works.add("wstępne negocjacje warunków najmu");
        works.add("prace biurowe");
        works.add("skanowanie i przygotowywanie dokumentów");
        return works;
    }

    public static ArrayList<Day> workDays(int enumMonth) {
        ArrayList<Day> days = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, enumMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        for (int i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            cal.set(Calendar.DAY_OF_MONTH, i);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) continue;
            days.add(new Day(dateFormat.format(cal.getTime())));
        }
        return days;
    }

    public static ArrayList<Day> workDays(int enumMonth, int workToDayOfMont, int year) {
        ArrayList<Day> days = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, enumMonth);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
        for (int i = 1; i <= workToDayOfMont; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i);
            if (cal.get(Calendar.DAY_OF_WEEK) == 1) continue;
            days.add(new Day(dateFormat.format(cal.getTime())));
        }
        return days;
    }

    public static ArrayList<Day> dayWithHourAndTask(ArrayList<Integer> hours, ArrayList<String> tasks, ArrayList<Day> days) {
        int numberOfDay = days.size();

        for (Integer hour : hours) {
            numberOfDay--;
            Random rand = new Random();
            days.get(numberOfDay).workHours.add(hour);
            days.get(numberOfDay).workNames.add(tasks.get(rand.nextInt(tasks.size())));
            if (numberOfDay == 0) {
                numberOfDay = days.size();
            }
        }
        return days;
    }
}
