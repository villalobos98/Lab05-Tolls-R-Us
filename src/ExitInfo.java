/**
 * @author James Heliotis
 * <p>
 * February 2018
 */
public class ExitInfo {

    private int exitNum;
    private String name;
    private double location;

    public ExitInfo(int exitNum, String name, double location) {
        this.exitNum = exitNum;
        this.name = name;
        this.location = location;
    }

    public int getExitNum() {
        return exitNum;
    }

    public String getName() {
        return name;
    }

    public double getLocation() {
        return location;
    }
}
