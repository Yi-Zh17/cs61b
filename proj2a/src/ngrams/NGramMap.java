package ngrams;

import edu.princeton.cs.algs4.In;

import java.sql.Time;
import java.util.*;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    HashMap<String, TimeSeries> wordFile;
    TimeSeries countFile;

    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // Initialize maps
        wordFile = new HashMap<>();
        countFile = new TimeSeries();

        // Read in words file
        In word_file = new In(wordsFilename);
        if(!word_file.exists()) {throw new IllegalArgumentException("Cannot find the word file.");}

        // global variables
        String curWord = null;
        TimeSeries ts = new TimeSeries();
        // Initialize variables
        if(word_file.hasNextLine()) {
            String line = word_file.readLine();
            // Split by tab
            String[] splitLine = line.split("\t");
            curWord = splitLine[0];
            ts.put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
        }

        // Read lines
        while(word_file.hasNextLine()) {
            String nextLine = word_file.readLine();
            String[] splitLine = nextLine.split("\t");
            if(Objects.equals(splitLine[0], curWord)) {
                ts.put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
            } else {
                // Save to map and start a new time series
                wordFile.put(curWord, ts);
                curWord = splitLine[0];
                ts = new TimeSeries();

                // Store new data
                ts.put(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]));
            }
        }
        // Save last ts
        wordFile.put(curWord, ts);
        word_file.close();

        // Read in counts file
        In count_file = new In(countsFilename);
        if(!count_file.exists()) {throw new IllegalArgumentException("Cannot find the word file.");}

        // Read lines
        while(!count_file.isEmpty()) {
            String line = count_file.readLine();
            String[] splitLine = line.split(",");
            countFile.put(Integer.parseInt(splitLine[0]), Double.parseDouble(splitLine[1]));
        }
        count_file.close();
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if(!wordFile.containsKey(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(wordFile.get(word), startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        if(!wordFile.containsKey(word)){
            return new TimeSeries();
        }
        TimeSeries ts = wordFile.get(word);
        return new TimeSeries(ts, ts.firstKey(), ts.lastKey());
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(countFile, countFile.firstKey(), countFile.lastKey());
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        if(!wordFile.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries wordHis = countHistory(word, startYear, endYear);
        TimeSeries countHis = totalCountHistory();
        return wordHis.dividedBy(countHis);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        if(!wordFile.containsKey(word)) {
            return new TimeSeries();
        }
        TimeSeries wordHis = countHistory(word);
        TimeSeries countHis = totalCountHistory();
        return wordHis.dividedBy(countHis);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries result = new TimeSeries();
        for(String word : words) {
            TimeSeries temp = weightHistory(word, startYear, endYear);
            result = result.plus(temp);
        }
        return result;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries result = new TimeSeries();
        for(String word : words) {
            TimeSeries temp = weightHistory(word);
            result = result.plus(temp);
        }
        return result;
    }
}
