package assignment.weatherunderground.restapi.retrofitmodels.responseelementmodels;

/**
 ResponseElement
 <p>
 Created by hmehta on 10/29/17.
 */

public class ResponseElement {
    private String version;
    private String termsofService;
    private ResponseFeatureElement features;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTermsofService() {
        return termsofService;
    }

    public void setTermsofService(String termsofService) {
        this.termsofService = termsofService;
    }

    public ResponseFeatureElement getFeatures() {
        return features;
    }

    public void setFeatures(ResponseFeatureElement features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "ResponseElement{" +
                "version='" + version + '\'' +
                ", termsofService='" + termsofService + '\'' +
                ", features=" + features +
                '}';
    }
}
