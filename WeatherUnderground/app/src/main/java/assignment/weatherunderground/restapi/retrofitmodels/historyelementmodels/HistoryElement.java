package assignment.weatherunderground.restapi.retrofitmodels.historyelementmodels;

import java.util.ArrayList;

import assignment.weatherunderground.restapi.retrofitmodels.DateElement;

/**
 HistoryElement
 <p>
 Created by hmehta on 10/29/17.
 */

public class HistoryElement {
    private DateElement date;
    private ArrayList<HistoryDailySummaryElement> dailysummary;
    private ArrayList<HistoryObservationElement> observations;

    public DateElement getDate() {
        return date;
    }

    public void setDate(DateElement date) {
        this.date = date;
    }

    public ArrayList<HistoryDailySummaryElement> getDailysummary() {
        return dailysummary;
    }

    public void setDailysummary(ArrayList<HistoryDailySummaryElement> dailysummary) {
        this.dailysummary = dailysummary;
    }

    public ArrayList<HistoryObservationElement> getObservations() {
        return observations;
    }

    public void setObservations(ArrayList<HistoryObservationElement> observations) {
        this.observations = observations;
    }
}
