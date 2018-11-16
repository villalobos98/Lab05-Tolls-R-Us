/* A few useful items are provided to you. You must write the rest. */
/**
 * @author: Isaias Villalobos
 * @Description: Collection of toll records objects. This object prints out custom summary for a specific person.
 * @Date: 2/20/18
 */

import java.io.*;
import java.util.*;

public class TollRoadDatabase {
    /**
     * For printing floating point values in dollar/cents format. Example:
     * System.out.println( String.format( DOLLAR_FORMAT, 10.5 );  // $10.50
     */
    private static final String DOLLAR_FORMAT = "$%5.2f";
    private static final String SPEED_FORMAT = "%5.1f MpH";
    Map<String, List<TollRecord>> map;


    /**
     * Universal new line
     */
    private static final String NL = System.lineSeparator();

    /**
     * Conversion constant from minutes to hours
     */
    public static final double MINUTES_PER_HOUR = 60.0;

    /**
     * This toll road's speed limit, in miles per hour
     */
    public static final double SPEED_LIMIT = 65.0;

    /**
     * @param eventFileName The name of the file passed in.
     * @throws IOException, Throws an error.
     * This function builds the tree map that will be used to map the string tag to a list of tollrecord object.
     */
    public TollRoadDatabase(String eventFileName) throws IOException,FileNotFoundException {

        BufferedReader br = null;
        br = new BufferedReader(new FileReader(eventFileName));
        String str;
        this.map = new TreeMap<>();

        while ((str = br.readLine()) != null) {
            String[] strContent = str.trim().split(",");
            TollRecord toll;

            if (!this.map.containsKey(strContent[1])) {
                //person is not in the database.
                List<TollRecord> temp = new ArrayList<>();
                toll = new TollRecord(Integer.parseInt(strContent[0]), strContent[1], Integer.parseInt(strContent[2]));
                temp.add(toll);
                this.map.put(strContent[1], temp);
            } else {
                //person is in database
                List<TollRecord> temp = this.map.get(strContent[1]);
                if (temp.get(temp.size() - 1).isFinished()) {
                    //person is starting a new trip.
                    toll = new TollRecord(Integer.parseInt(strContent[0]), strContent[1], Integer.parseInt(strContent[2]));
                    temp.add(toll);
                } else {
                    //person is in the middle of the trip.
                    temp.get(temp.size() - 1).setOffExit(Integer.parseInt(strContent[2]), Integer.parseInt(strContent[0]));
                }
            }
        }
    }

    /**
     * Print out the number of completed trips.
     */
    public void summaryReport() {
        int completed_trips = 0;

        for (String key : map.keySet()) {
            for (TollRecord t : map.get(key)) {
                if (t.isFinished()) {
                    completed_trips++;
                }
            }
        }
        System.out.println(completed_trips + " completed trips");
    }

    /**
     * Prints out vehicles that still on the road.
     */
    public void onRoadReport() {
        System.out.println("On-Road Report\n==============");

        String str = "";
        for (String m : map.keySet()) {
            int size = map.get(m).size() - 1;
            if (!map.get(m).get(size).isFinished()) {
                System.out.println(map.get(m).get(map.get(m).size() - 1));

            }
        }
        System.out.println(str);
    }

    /**
     * Printout billing report for the vehicles that are still on the toll road.
     */
    public void printBills() {
        System.out.println("BILLING INFORMATION\n===================");
        double sum = 0;
        for (String m : map.keySet()) {
            List<TollRecord> entry = map.get(m);
            for (TollRecord r : entry) {
                if (r.isFinished()) {
                    sum += TollSchedule.getFare(r.getOnExit(), r.getOffExit());
                    System.out.println(r + ": " + String.format(DOLLAR_FORMAT, r.getFare()));
                }
            }
        }

        System.out.println("Total is : " + String.format(DOLLAR_FORMAT, sum));
    }

    /**
     * @param tag string
     * @return Calculation
     * DESCRIPTION: THIS METHOD WAS NOT USED CALCULATION WERE DONE IN PRINT METHOD
     */
    private double bill(String tag) {

        return 0;
    }

    /**
     * Print out the list of cars that are going above the speed limit
     */
    public void speederReport() {
        System.out.println("SPEEDER REPORT\n==============");
        for (String m : map.keySet()) {
            for (TollRecord t : map.get(m)) {
                if (t.speeding()) {
                    System.out.println("Vehicle " + t.getTag() + ", Starting " +
                            "at time " + t.getOnTime() + "\n\t\t from " +
                            TollSchedule.getInterchange(t.getOnExit()) + "\n\t\t" + " to " +
                            TollSchedule.getInterchange(t.getOffExit()) + "\n\t\t" +
                            String.format(SPEED_FORMAT, t.getSpeed()));
                }
            }

        }
    }

    /**
     * @param tag given a specific tag, retrieve the toll records's time
     *            exit, tag and fare for that specific vehicle.
     */
    public void printCustSummary(String tag) {

        double sum = 0;
        for (String x : map.keySet()) {
            if (tag.equals(x)) {
                for (TollRecord y : map.get(x)) {
                    if (y.isFinished()) {
                        sum += y.getFare();
                        System.out.println(y + ":" + String.format(DOLLAR_FORMAT, y.getFare()));
                    }
                }
                System.out.println("Vehicle total due: " + String.format(DOLLAR_FORMAT, sum));

            }
        }
    }

    /**
     * @param o another vehicile that checks if the two objects are the same.
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TollRoadDatabase that = (TollRoadDatabase) o;
        return Objects.equals(map, that.map);
    }

    /**
     * @return hash code of the object
     */
    @Override
    public int hashCode() {

        return Objects.hash(map);
    }

    /**
     * @param exit integer,
     * Desc: Printout the records that include a specific exit as their on or off point.
    Records are listed completed first, in order by vehicle tag and then by entry time;
    afterwards incomplete trips are listed in the same ordering.
     */
    public void printExitActivity(int exit) {

        if (!TollSchedule.isValid(exit))
            return;

        List<TollRecord> temp = new ArrayList<>();
        for (String x : map.keySet()) {
            List<TollRecord> tagEntries = (List) map.get(x);
            for (TollRecord y : tagEntries) {
                if (y.getOnExit() == exit || y.getOffExit() == exit) {
                    temp.add(y);
                }
            }
        }
        Collections.sort(temp);
        for (TollRecord t : temp) {
            System.out.println(t);
        }
    }
}

