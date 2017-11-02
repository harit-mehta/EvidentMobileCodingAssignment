package assignment.weatherunderground;

import org.junit.Test;

import java.io.IOException;

import assignment.weatherunderground.restapi.RetrofitHelper;
import assignment.weatherunderground.restapi.retrofitmodels.HistoryResponseModel;
import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.assertTrue;

public class UnitTestSample {

    @Test
    public void retrofit_api_call_test() {
        RetrofitHelper.RetrofitService retrofitService = RetrofitHelper.getInstance("http://api.wunderground.com/api/2b8169329cbb415a/")
                                                                       .getRetrofitService();
        Call<HistoryResponseModel> call = retrofitService.getWeatherHistory("20171031");
        try {
            Response<HistoryResponseModel> response = call.execute();
            HistoryResponseModel historyResponseModel = response.body();

            assertTrue("History not available",
                       response.isSuccessful()
                               && historyResponseModel != null
                               && historyResponseModel.getHistory().getObservations().size() > 0
                               && historyResponseModel.getHistory().getDailysummary().size() > 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}