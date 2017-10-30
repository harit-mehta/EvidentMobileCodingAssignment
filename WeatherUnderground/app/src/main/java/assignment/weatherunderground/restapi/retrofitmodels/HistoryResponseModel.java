package assignment.weatherunderground.restapi.retrofitmodels;

import assignment.weatherunderground.restapi.retrofitmodels.historyelementmodels.HistoryElement;
import assignment.weatherunderground.restapi.retrofitmodels.responseelementmodels.ResponseElement;

/**
 HistoryResponseModel
 <p>
 Created by hmehta on 10/29/17.
 */

public class HistoryResponseModel {
    private ResponseElement response;
    private HistoryElement history;

    public ResponseElement getResponse() {
        return response;
    }

    public void setResponse(ResponseElement response) {
        this.response = response;
    }

    public HistoryElement getHistory() {
        return history;
    }

    public void setHistory(HistoryElement history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "HistoryResponseModel{" +
                "response=" + response +
                ", history=" + history +
                '}';
    }
}
