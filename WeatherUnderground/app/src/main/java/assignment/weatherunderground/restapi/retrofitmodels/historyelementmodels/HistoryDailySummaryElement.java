package assignment.weatherunderground.restapi.retrofitmodels.historyelementmodels;

import assignment.weatherunderground.restapi.retrofitmodels.DateElement;

/**
 HistoryDailySummaryElement
 <p>
 Created by hmehta on 10/29/17.
 */

public class HistoryDailySummaryElement {
    private DateElement date;
    private String fog;
    private String rain;
    private String snow;
    private String meantempm;
    private String meantempi;
    private String mintempm;
    private String mintempi;
    private String maxtempm;
    private String maxtempi;

    public DateElement getDate() {
        return date;
    }

    public void setDate(DateElement date) {
        this.date = date;
    }

    public String getFog() {
        return fog;
    }

    public void setFog(String fog) {
        this.fog = fog;
    }

    public String getRain() {
        return rain;
    }

    public void setRain(String rain) {
        this.rain = rain;
    }

    public String getSnow() {
        return snow;
    }

    public void setSnow(String snow) {
        this.snow = snow;
    }

    public String getMeantempm() {
        return meantempm;
    }

    public void setMeantempm(String meantempm) {
        this.meantempm = meantempm;
    }

    public String getMeantempi() {
        return meantempi;
    }

    public void setMeantempi(String meantempi) {
        this.meantempi = meantempi;
    }

    public String getMintempm() {
        return mintempm;
    }

    public void setMintempm(String mintempm) {
        this.mintempm = mintempm;
    }

    public String getMintempi() {
        return mintempi;
    }

    public void setMintempi(String mintempi) {
        this.mintempi = mintempi;
    }

    public String getMaxtempm() {
        return maxtempm;
    }

    public void setMaxtempm(String maxtempm) {
        this.maxtempm = maxtempm;
    }

    public String getMaxtempi() {
        return maxtempi;
    }

    public void setMaxtempi(String maxtempi) {
        this.maxtempi = maxtempi;
    }

    @Override
    public String toString() {
        return "HistoryDailySummaryElement{" +
                "date=" + date +
                ", fog='" + fog + '\'' +
                ", rain='" + rain + '\'' +
                ", snow='" + snow + '\'' +
                ", meantempm='" + meantempm + '\'' +
                ", meantempi='" + meantempi + '\'' +
                ", mintempm='" + mintempm + '\'' +
                ", mintempi='" + mintempi + '\'' +
                ", maxtempm='" + maxtempm + '\'' +
                ", maxtempi='" + maxtempi + '\'' +
                '}';
    }
}
