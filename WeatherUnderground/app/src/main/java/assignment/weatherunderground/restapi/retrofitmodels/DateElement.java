package assignment.weatherunderground.restapi.retrofitmodels;

/**
 DateElement
 <p>
 Created by hmehta on 10/29/17.
 */

public class DateElement {
    private String pretty;
    private String hour;
    private String min;
    private String tzname;

    public String getPretty() {
        return pretty;
    }

    public void setPretty(String pretty) {
        this.pretty = pretty;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getTzname() {
        return tzname;
    }

    public void setTzname(String tzname) {
        this.tzname = tzname;
    }

    @Override
    public String toString() {
        return "DateElement{" +
                "pretty='" + pretty + '\'' +
                ", hour='" + hour + '\'' +
                ", min='" + min + '\'' +
                ", tzname='" + tzname + '\'' +
                '}';
    }
}
