package assignment.weatherunderground.restapi.retrofitmodels.historyelementmodels;

import assignment.weatherunderground.restapi.retrofitmodels.DateElement;

public class HistoryObservationElement {
    private DateElement date;
    private String fog;
    private String rain;
    private String snow;
    private String tempm;
    private String tempi;

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

    public String getTempm() {
        return tempm;
    }

    public void setTempm(String tempm) {
        this.tempm = tempm;
    }

    public String getTempi() {
        return tempi;
    }

    public void setTempi(String tempi) {
        this.tempi = tempi;
    }

    @Override
    public String toString() {
        return "HistoryObservationElement{" +
                "date=" + date +
                ", fog='" + fog + '\'' +
                ", rain='" + rain + '\'' +
                ", snow='" + snow + '\'' +
                ", tempm='" + tempm + '\'' +
                ", tempi='" + tempi + '\'' +
                '}';
    }
}
