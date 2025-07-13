package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import org.knowm.xchart.XYChart;
import plotting.Plotter;

import java.sql.Time;
import java.util.ArrayList;

public class HistoryHandler extends NgordnetQueryHandler {
    // Instance variable
    NGramMap mp;
    // Constructor
    public HistoryHandler(NGramMap map) {
        mp = map;
    }
    @Override
    public String handle(NgordnetQuery q) {
        // Arrays
        ArrayList<String> labels = new ArrayList<>(q.words());
        ArrayList<TimeSeries> lts = new ArrayList<>();

        int startYear = q.startYear();
        int endYear = q.endYear();
        TimeSeries ts;

        for(String word : labels) {
            ts = mp.weightHistory(word, startYear, endYear);
            lts.add(ts);
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);

        return Plotter.encodeChartAsString(chart);
    }
}
