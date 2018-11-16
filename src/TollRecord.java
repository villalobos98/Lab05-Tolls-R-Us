/* A few useful items are provided to you. You must write the rest. */
/**
 * @author: Isaias Villalobos
 * @Description: Toll record object that stores many behaviors such as storing the time,exit.
 * @Date: 2/20/18
 *
 */

import java.util.Scanner;

public class TollRecord implements Comparable<TollRecord> {

    /**
     * For printing toll records in reports
     * using {@link String#format(String, Object...)}
     */
    private static final String TOLL_RECORD_FORMAT = "[%11s] on #%2d, time %5d";
    private static final String OFF_FORMAT = "; off #%2d, time %5d";

    private String tag;
    private int exit;
    private int onTime;
    private int offTime;
    private int offExit;


    /**
     * Value of uninitialized integer fields in this record
     */
    public static final int UNINITIALIZED = -1;

    /**
     * @param onTime integer for the time coming in to exit
     * @param tag tag of the vehicle
     * @param onExit the exit the person gets on
     */
    public TollRecord(int onTime, String tag, int onExit) {
        this.tag = tag;
        this.exit = onExit;
        this.onTime = onTime;
        this.offExit = UNINITIALIZED;
        this.offTime = UNINITIALIZED;
    }

    /**
     * @return the exit the car gets off
     */
    public int getOffExit() {
        return this.offExit;
    }

    /**
     * @param offExit the off exit
     * @param offTime the on exit
     */
    public void setOffExit(int offExit, int offTime) {
        this.offExit = offExit;
        this.offTime = offTime;
    }

    /**
     * @return the tag of the vehicle
     */
    public String getTag() {
        return this.tag;
    }

    /**
     * @return the on exit
     */
    public int getOnExit() {
        return this.exit;
    }

    /**
     * @return check if the vehicle has finsiehd a trip
     */
    public boolean isFinished() {
        return this.offExit != UNINITIALIZED;
    }

    /**
     * @return get the time the vehicle enters the exit
     */
    public int getOnTime() {
        return this.onTime;
    }

    /**
     * @return get the time the vehicle comes off the exit.
     */
    public int getOffTime() {
        return this.offTime;
    }

    /**
     * @return get the fare for teh vehicle, given an on exit and off exit
     */
    public double getFare() {
        return TollSchedule.getFare(exit, offExit);
    }

    /**
     * @param o other object that we use
     * @return boolean, check if two objects are equal.
     */
    public boolean equals(Object o) {
        if (o instanceof TollRecord) {
            TollRecord toll = (TollRecord) o;
            return this.tag.equals(toll.tag) && this.exit == toll.exit
                    && this.offTime == toll.offTime &&
                    this.offExit == toll.offExit;
        }
        return false;
    }

    /**
     * @return String reprensentation of the object toll record
     */
    public String toString() {

        if (offTime == UNINITIALIZED)
            return String.format(TOLL_RECORD_FORMAT, this.tag, this.exit, this.onTime);

        return String.format(TOLL_RECORD_FORMAT + OFF_FORMAT, this.tag, this.exit, this.onTime, this.offExit, this.offTime);
    }

    /**
     * Method not used, but its a simple printout
     */
    public String report() {
        return "[ " + this.tag + " ] on " + getOnExit() + "; off " + offExit + ", time " + offTime;
    }

    /**
     * @return hashchode created from the time, exit, and string hashcode
     */
    public int hashCode() {
        return this.offExit + this.offTime + this.onTime + this.exit
                + this.tag.hashCode();
    }

    /**
     * @param t tollrecord object that gets passed in to be compared.
     * @return A method of comparing by time, and exit.
     */
    @Override
    public int compareTo(TollRecord t) {
        if (this.offTime == UNINITIALIZED && t.offTime == UNINITIALIZED) {
            return this.onTime - t.onTime;
        }
        else if (this.offTime == UNINITIALIZED) {
            return 1;
        }
        else if(t.offTime == UNINITIALIZED){
            return -1;
        }
        else{
            return this.offTime- t.offTime;
        }
    }

    /**
     * @return  speed that the vehicle that is traveling.
     */
    public double getSpeed() {
        double time = (getOffTime() - getOnTime()) / TollRoadDatabase.MINUTES_PER_HOUR;
        double position = Math.abs(TollSchedule.getLocation(getOffExit()) -
                TollSchedule.getLocation(getOnExit()));

        double speed = position / time;
        return speed;

    }

    /**
     * @return boolean check if the vehicle is speeding
     */
    public boolean speeding() {


        return offExit != UNINITIALIZED && getSpeed() > TollRoadDatabase.SPEED_LIMIT;
    }

    /**
     * @param args main to run thee program.
     */
    public static void main(String[] args) {
        //use this as a test case, optional.


        TollRoadDatabase t;
        try {

            t = new TollRoadDatabase(args[0]);
            t.summaryReport();
            System.out.println();
            t.onRoadReport();
            t.speederReport();
            System.out.println();
            t.printBills();
            while (true) {
                System.out.println();
                System.out.println("b <string> to see bill for license tag");
                System.out.println("e <number> to see activity at exit");
                System.out.println("q to quit");
                Scanner scan = new Scanner(System.in);
                String s = scan.next();

                if (s.equals("b"))
                    t.printCustSummary(scan.next());
                else if (s.equals("e"))
                    t.printExitActivity(scan.nextInt());
                else {
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
