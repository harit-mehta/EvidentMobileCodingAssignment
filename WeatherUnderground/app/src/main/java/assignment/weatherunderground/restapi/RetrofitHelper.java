package assignment.weatherunderground.restapi;

import assignment.weatherunderground.restapi.retrofitmodels.HistoryResponseModel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RetrofitHelper {

    private static RetrofitHelper mInstance = null;

    private RetrofitService mRetrofitService;

    private RetrofitHelper(String baseURL) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        httpClientBuilder.addInterceptor(loggingInterceptor);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClientBuilder.build()).build();

        mRetrofitService = retrofit.create(RetrofitService.class);
    }

    public static synchronized RetrofitHelper getInstance(String baseURL) {
        if (mInstance == null) {
            mInstance = new RetrofitHelper(baseURL);
        }
        return mInstance;
    }

    public RetrofitService getRetrofitService() {
        return mRetrofitService;
    }

    public interface RetrofitService {

        @GET ("history_{date_string}/q/GA/Alpharetta.json")
        Call<HistoryResponseModel> getWeatherHistory(@Path ("date_string") String dateString);
    }
}
