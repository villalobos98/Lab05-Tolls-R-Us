import java.util.*;

/**
 * A class that encapsulate the toll schedule for cars travelling on
 * the NY Thruway.
 *
 * @author James Heliotis
 */
public class TollSchedule {

    /**
     * Last exit = Number of exits - 1 because we count from 0
     */
    public final static int LAST_EXIT = 62;

    /**
     * Toll rate in dollars per mile
     */
    public final static double CENTS_PER_MILE = 0.04;

    private static ExitInfo[] EXIT_INFO = new ExitInfo[]{
            new ExitInfo( 0,
                          "New York City Line - Major Deegan Expressway (I-87)",
                          0.0
            ),
            new ExitInfo( 1, "Hall Place - McLean Avenue", 0.48 ),
            new ExitInfo( 2, "Yonkers Avenue - Raceway", 0.92 ),
            new ExitInfo( 3, "Mile Square Road", 1.77 ),
            new ExitInfo( 4, "Cross County Parkway - Mile Square Road", 2.18 ),
            new ExitInfo( 5,
                          "White Plains - Central Park Avenue (NY Route 100)",
                          2.7
            ),
            new ExitInfo( 6, "Yonkers - Bronxville - Tuckahoe Road", 4.00 ),
            new ExitInfo( 7, "Ardsley - NY Route 9A", 7.58 ),
            new ExitInfo( 8,
                          "White Plains - Rye - Cross Westchester Expressway " +
                          "(I-287)",
                          11.31
            ),
            new ExitInfo( 9, "Tarrytown - Sleepy Hollow - US Route 9", 12.65 ),
            new ExitInfo( 10, "Nyack - South Nyack - US Route 9W", 16.75 ),
            new ExitInfo( 11, "Nyack - South Nyack - US Route 9W", 17.63 ),
            new ExitInfo( 12,
                          "West Nyack - NY Route 303 - Palisades Center Drive",
                          18.76
            ),
            new ExitInfo( 13,
                          "New Jersey - Palisades Interstate Parkway - Bear " +
                          "Mountain State Park",
                          20.94
            ),
            new ExitInfo( 14, "Spring Valley - Nanuet - NY Route 59", 22.80 ),
            new ExitInfo( 15, "New Jersey - I-287 - NJ Route 17 South", 30.17 ),
            new ExitInfo( 16, "Harriman - US Route 6 - NY Route 17", 45.20 ),
            new ExitInfo( 17,
                          "Newburgh - Scranton - I-84 - NY Routes 17K & 300",
                          60.10
            ),
            new ExitInfo( 18, "New Paltz - Poughkeepsie - NY Route 299",
                          76.01
            ),
            new ExitInfo( 19,
                          "Kingston - NY Route 28 - Kingston-Rhinecliff Bridge",
                          91.37
            ),
            new ExitInfo( 20, "Saugerties - Woodstock - NY Route 32", 101.25 ),
            new ExitInfo( 21, "Catskill - Cairo - NY Route 23", 113.89 ),
            new ExitInfo( 22, "Selkirk - NY Routes 144 & 396", 134.93 ),
            new ExitInfo( 23,
                          "Albany (Downtown) - Troy - Rensselaer - I-787 - US" +
                          " Route 9W",
                          141.92
            ),
            new ExitInfo( 24, "Albany - Montreal - I-90 East - I-87 North",
                          148.15
            ),
            new ExitInfo( 25, "Schenectady - I-890 - NY Routes 7 & 146",
                          153.83
            ),
            new ExitInfo( 26, "Schenectady - Scotia - I-890 - NY Routes 5 & 5S",
                          162.22
            ),
            new ExitInfo( 27, "Amsterdam - NY Route 30", 173.59 ),
            new ExitInfo( 28, "Fultonville - Fonda - NY Route 30A", 182.17 ),
            new ExitInfo( 29, "Canajoharie - Sharon Springs - NY Route 10",
                          194.10
            ),
            new ExitInfo( 30, "Herkimer - Mohawk - NY Route 28", 219.70 ),
            new ExitInfo( 31, "Utica - I-790 - NY Routes 8 & 12", 232.85 ),
            new ExitInfo( 32, "Westmoreland - Rome - NY Route 233", 243.37 ),
            new ExitInfo( 33, "Verona - Rome - Oneida - NY Route 365", 252.71 ),
            new ExitInfo( 34, "Canastota - Oneida - Chittenango - NY Route 13",
                          261.50
            ),
            new ExitInfo( 35, "Syracuse - East Syracuse - NY Route 298",
                          278.93
            ),
            new ExitInfo( 36, "Watertown - Binghamton - I-81", 282.93 ),
            new ExitInfo( 37, "Syracuse - Liverpool - Electronics Parkway",
                          283.79
            ),
            new ExitInfo( 38, "Syracuse - Liverpool - County Route 57",
                          285.95
            ),
            new ExitInfo( 39, "Syracuse - Fulton - I-690 - NY Route 690",
                          289.53
            ),
            new ExitInfo( 40, "Weedsport - Auburn - NY Route 34", 304.19 ),
            new ExitInfo( 41, "Waterloo - Clyde - NY Route 414", 320.41 ),
            new ExitInfo( 42, "Geneva - Lyons - NY Route 14", 327.10 ),
            new ExitInfo( 43, "Manchester - Palmyra - NY Route 21", 340.15 ),
            new ExitInfo( 44, "Canandaigua - Victor - NY Route 332", 347.13 ),
            new ExitInfo( 45, "Rochester - Victor - I-490", 350.99 ),
            new ExitInfo( 46, "Rochester - Corning - I-390", 362.44 ),
            new ExitInfo( 47, "Rochester - LeRoy - I-490 - NY Route 19",
                          378.56
            ),
            new ExitInfo( 48, "Batavia - NY Route 98", 390.13 ),
            new ExitInfo( 49, "Depew - Lockport - NY Route 78", 417.27 ),
            new ExitInfo( 50, "Niagara Falls - I-290", 420.34 ),
            new ExitInfo( 51, "Buffalo - NY Route 33 - Airport", 421.57 ),
            new ExitInfo( 52, "Buffalo - Cheektowaga - Walden Avenue", 423.19 ),
            new ExitInfo( 53,
                          "Buffalo (Downtown) - Canada - Niagara Falls - I-190",
                          426.17
            ),
            new ExitInfo( 54, "West Seneca - East Aurora - NY Routes 400 & 16",
                          427.94
            ),
            new ExitInfo( 55,
                          "Springville - Orchard Park - Lackawanna - West " +
                          "Seneca - US Route 219 - Ridge Road",
                          429.47
            ),
            new ExitInfo( 56,
                          "Blasdell - Orchard Park - Mile Strip Road (NY " +
                          "Route 179)",
                          432.45
            ),
            new ExitInfo( 57, "Hamburg - East Aurora - NY Route 75", 436.22 ),
            new ExitInfo( 58, "Silver Creek - Irving - NY Routes 5, 20 & 438",
                          455.54
            ),
            new ExitInfo( 59, "Dunkirk - Fredonia - NY Route 60", 467.74 ),
            new ExitInfo( 60, "Westfield - Mayville - NY Route 394", 485.00 ),
            new ExitInfo( 61, "Ripley - Shortman Road", 494.92 ),
            new ExitInfo( 62, "Pennsylvania State Line (I-90)", 496.00 )
    };

//    public static List< ExitInfo > SCHEDULE =
//            Collections.unmodifiableList( Arrays.asList( EXIT_INFO ) );

    /**
     * This is a completely static class and should not be constructed.
     * A private constructor is provided to enforce this rule.
     */
    private TollSchedule() {}

    /**
     * Get the fare from an incoming exit to an outgoing exit
     *
     * @param incoming the enter booth
     * @param outgoing  the exit booth
     * @return the fare
     */
    public static double getFare( int incoming, int outgoing ) {
        return Math.abs( EXIT_INFO[ incoming ].getLocation() -
                         EXIT_INFO[ outgoing ].getLocation() ) *
               TollSchedule.CENTS_PER_MILE;
    }

    /**
     * Indicate whether an exit number is valid or not
     *
     * @param exit the exit number of the booth
     * @return true iff the exit number is in the range
     *         specified by {@link TollSchedule#LAST_EXIT}
     */
    public static boolean isValid( int exit ) {
         return exit >= 0 && exit <= TollSchedule.LAST_EXIT;
    }

    /**
     * Get the exit's official name.
     *
     * @param exit the exit number
     * @return the exit name
     * @rit.pre isValid( exit )
     */
    public static String getInterchange( int exit ) {
        return TollSchedule.EXIT_INFO[ exit ].getName();
    }

    /**
     * Get the exit's linear location on the road.
     * @param exit the exit number
     * @return the exit's mileage marker
     * @rit.pre isValid( exit )
     */
    public static double getLocation( int exit ) {
        return TollSchedule.EXIT_INFO[ exit ].getLocation();
    }
}
