package assignment.weatherunderground.restapi;

import android.support.annotation.NonNull;

import assignment.weatherunderground.restapi.retrofitmodels.HistoryResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 RESTCoreApi
 <p>
 Created by hmehta on 10/29/17.
 */

public class RESTCoreApi {

    public interface GetHistoryApiListener {
        void beforeCall();

        void afterCall(HistoryResponseModel historyResponseModel, String error);
    }

    private final String BASE_URL = "http://api.wunderground.com/api/2b8169329cbb415a/";

    private static RESTCoreApi mInstance = null;

    private RetrofitHelper.RetrofitService mRetrofitService;

    private RESTCoreApi() {
        mRetrofitService = RetrofitHelper.getInstance(BASE_URL).getRetrofitService();
    }

    public static synchronized RESTCoreApi getInstance() {
        if (mInstance == null) {
            mInstance = new RESTCoreApi();
        }
        return mInstance;
    }

    public void getWeatherHistory(String requestedDate, final GetHistoryApiListener getHistoryApiListener) {
        Call<HistoryResponseModel> call = mRetrofitService.getWeatherHistory(requestedDate);

        if (getHistoryApiListener != null) {
            getHistoryApiListener.beforeCall();
        }

        call.enqueue(new Callback<HistoryResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<HistoryResponseModel> call,
                                   @NonNull Response<HistoryResponseModel> response) {
                HistoryResponseModel historyResponseModel = null;
                String errorMessgae = "";
                if (response.isSuccessful() && response.body() != null) {
                    historyResponseModel = response.body();
                } else {
                    errorMessgae = "Error fetching history.";
                }

                if (getHistoryApiListener != null) {
                    getHistoryApiListener.afterCall(historyResponseModel, errorMessgae);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HistoryResponseModel> call, @NonNull Throwable t) {
                if (getHistoryApiListener != null) {
                    getHistoryApiListener.afterCall(null, t.getLocalizedMessage());
                }
            }
        });
    }
}
