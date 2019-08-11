import java.util.ArrayList;

public class Day {
    String date;
    ArrayList<String> workNames;
    ArrayList<Integer> workHours = new ArrayList<>();

    Day(String data) {
        this.date = data;
        this.workNames = new ArrayList<>();
    }

    @Override
    public String toString() {
        String workName = "";
        for (int i = 0; i < workHours.size(); i++) {
            workName += workNames.get(i) + " " + workHours.get(i) + "\n";
        }
        return date + "\n" + workName;
    }
}
