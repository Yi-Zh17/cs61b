package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler{
    // Instance variables
    NGramMap mp;
    // Constructor
    public HistoryTextHandler(NGramMap map) {
        mp = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        String response = "";
        // Print each words
        for(String word : words) {
            TimeSeries ts = mp.weightHistory(word, startYear, endYear);
            response += word + ": " + ts.toString() + '\n';
        }
        return response;
    }
}
