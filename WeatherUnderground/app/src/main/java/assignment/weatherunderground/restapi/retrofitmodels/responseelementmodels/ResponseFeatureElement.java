package assignment.weatherunderground.restapi.retrofitmodels.responseelementmodels;

/**
 ResponseFeatureElement
 <p>
 Created by hmehta on 10/29/17.
 */

public class ResponseFeatureElement {
    private Integer history;

    public Integer getHistory() {
        return history;
    }

    public void setHistory(Integer history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "ResponseFeatureElement{" +
                "history=" + history +
                '}';
    }
}
