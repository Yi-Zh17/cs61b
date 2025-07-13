package ngrams;

import net.sf.saxon.serialize.BinaryTextDecoder;
import org.antlr.v4.runtime.tree.Tree;
import org.apache.logging.log4j.message.TimestampMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        for(int i = startYear; i <= endYear; ++i) {
            if(ts.containsKey(i)) {
                this.put(i, ts.get(i));
            }
        }
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        return new ArrayList<>(this.keySet());
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        return new ArrayList<>(this.values());
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        if(this.isEmpty() && ts.isEmpty()) {
            return new TimeSeries();
        } else if(this.isEmpty()) {
            return new TimeSeries(ts, ts.years().getFirst(), ts.years().getLast());
        } else if(ts.isEmpty()) {
            return new TimeSeries(this, this.years().getFirst(), this.years().getLast());
        } else {
            TimeSeries result = new TimeSeries();
            TreeMap<Integer, Integer> comparison = this.compare(ts);
            for(int i : comparison.keySet()) {
                if(comparison.get(i) == 3) {
                    result.put(i, this.get(i) + ts.get(i));
                } else if(comparison.get(i) == 2) {
                    result.put(i, ts.get(i));
                } else if(comparison.get(i) == 1) {
                    result.put(i, this.get(i));
                }
            }
            return result;
        }
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries quo = new TimeSeries();

        TreeMap<Integer, Integer> comparison = this.compare(ts);

        for(int i : comparison.keySet()) {
            if(comparison.get(i) == 3) {
                quo.put(i, this.get(i) / ts.get(i));
            } else if(comparison.get(i) == 1) {
                throw new IllegalArgumentException("TS value missing in the given year");
            }
        }

        return quo;
    }

    // Helper function
    private TreeMap<Integer, Integer> compare(TimeSeries ts) {
        TreeMap<Integer, Integer> result = new TreeMap<>();
        int minimum = Math.min(this.years().getFirst(), ts.years().getFirst());
        int maximum = Math.max(this.years().getLast(), ts.years().getLast());

        for(int i = minimum; i <= maximum; ++i) {
            if(this.containsKey(i) && ts.containsKey(i)) {
                result.put(i, 3);
            } else if(ts.containsKey(i)) {
                result.put(i, 2);
            } else if(this.containsKey(i)) {
                result.put(i, 1);
            } else {
                result.put(i, 0);
            }
        }
        return result;
    }

}
