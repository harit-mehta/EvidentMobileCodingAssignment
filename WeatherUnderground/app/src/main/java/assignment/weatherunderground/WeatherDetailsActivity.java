package assignment.weatherunderground;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import assignment.weatherunderground.adapters.HistoryObservationsAdapter;
import assignment.weatherunderground.restapi.RESTCoreApi;
import assignment.weatherunderground.restapi.retrofitmodels.HistoryResponseModel;
import assignment.weatherunderground.restapi.retrofitmodels.historyelementmodels.HistoryDailySummaryElement;
import assignment.weatherunderground.restapi.retrofitmodels.historyelementmodels.HistoryElement;
import assignment.weatherunderground.restapi.retrofitmodels.historyelementmodels.HistoryObservationElement;

/**
 WeatherDetailsActivity
 <p>
 Created by hmehta on 10/29/17.
 */

public class WeatherDetailsActivity extends AppCompatActivity {

    static final String EXTRA_KEY_REQUESTED_DATE = "extraKeyRequestedDate";

    private static final String TAG = "WeatherDetailsActivity";

    private ListView lvObservations;
    private RadioGroup rgHistoryOptions;
    private LinearLayout llSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_weather_details);
        initUIWidgets();
        processIntent(getIntent());
    }

    private void processIntent(Intent incomingIntent) {
        Log.d(TAG, "processIntent");
        String requestedDate = incomingIntent.getStringExtra(EXTRA_KEY_REQUESTED_DATE);
        if (requestedDate != null && !requestedDate.equals("")) {
            Log.d(TAG, "request date :" + requestedDate);
            RESTCoreApi restCoreApi = RESTCoreApi.getInstance();
            restCoreApi.getWeatherHistory(requestedDate, new RESTCoreApi.GetHistoryApiListener() {
                ProgressDialog pd = null;

                @Override
                public void beforeCall() {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                    }
                    pd = ProgressDialog.show(WeatherDetailsActivity.this,
                                             "Fetching History",
                                             "Please wait...",
                                             true,
                                             false);
                }

                @Override
                public void afterCall(HistoryResponseModel historyResponseModel, String error) {
                    if (pd != null && pd.isShowing()) {
                        pd.dismiss();
                        pd = null;
                    }

                    if (historyResponseModel != null) {
                        Log.d(TAG, "History response:");
                        Log.d(TAG, historyResponseModel.getResponse().toString());
                        Log.d(TAG, "History details:");
                        HistoryElement historyElement = historyResponseModel.getHistory();
                        Log.d(TAG, historyElement.getDate().toString());
                        loadWeatherHistory(historyElement.getDailysummary(), historyElement.getObservations());
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(WeatherDetailsActivity.this);
                        builder.setTitle("Error");
                        builder.setMessage(error + "Please try again.");
                        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                WeatherDetailsActivity.this.finish();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
                    }
                }
            });
        } else {
            Log.d(TAG, "Request date null");
        }
    }

    private void loadWeatherHistory(ArrayList<HistoryDailySummaryElement> historyDailySummaryElement,
                                    ArrayList<HistoryObservationElement> historyObservationElement) {
        HistoryObservationsAdapter historyObservationsAdapter = new HistoryObservationsAdapter(WeatherDetailsActivity.this, historyObservationElement);
        lvObservations.setAdapter(historyObservationsAdapter);

        if (historyDailySummaryElement != null && historyDailySummaryElement.size() > 0) {
            HistoryDailySummaryElement dailySummary = historyDailySummaryElement.get(0);
            loadSummaryDetails(dailySummary);
        }
    }

    private void loadSummaryDetails(HistoryDailySummaryElement dailySummary) {

        TextView tvDate = (TextView) findViewById(R.id.tvDateSummary);
        TextView tvFog = (TextView) findViewById(R.id.tvFogSummary);
        TextView tvRain = (TextView) findViewById(R.id.tvRainSummary);
        TextView tvSnow = (TextView) findViewById(R.id.tvSnowSummary);
        TextView tvMaxTempi = (TextView) findViewById(R.id.tvMaxTempiSummary);
        TextView tvMaxTempm = (TextView) findViewById(R.id.tvMaxTempmSummary);
        TextView tvMeanTempi = (TextView) findViewById(R.id.tvMeanTempiSummary);
        TextView tvMeanTempm = (TextView) findViewById(R.id.tvMeanTempmSummary);
        TextView tvMinTempi = (TextView) findViewById(R.id.tvMinTempiSummary);
        TextView tvMinTempm = (TextView) findViewById(R.id.tvMinTempmSummary);

        tvDate.setText(dailySummary.getDate().getPretty());
        tvFog.append(" : " + dailySummary.getFog());
        tvRain.append(" : " + dailySummary.getRain());
        tvSnow.append(" : " + dailySummary.getSnow());
        tvMaxTempi.append(" : " + dailySummary.getMaxtempi());
        tvMaxTempm.append(" : " + dailySummary.getMaxtempm());
        tvMeanTempi.append(" : " + dailySummary.getMeantempi());
        tvMeanTempm.append(" : " + dailySummary.getMeantempm());
        tvMinTempi.append(" : " + dailySummary.getMintempi());
        tvMinTempm.append(" : " + dailySummary.getMintempm());
    }

    private void initUIWidgets() {
        rgHistoryOptions = (RadioGroup) findViewById(R.id.rgHistoryDetailsOptions);
        rgHistoryOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {
                if (checkedId == R.id.rbDailySummary) {
                    llSummary.setVisibility(View.VISIBLE);
                    lvObservations.setVisibility(View.GONE);
                } else if (checkedId == R.id.rbObservations) {
                    llSummary.setVisibility(View.GONE);
                    lvObservations.setVisibility(View.VISIBLE);
                }
            }
        });

        lvObservations = (ListView) findViewById(R.id.lvHistoryObservations);
        llSummary = (LinearLayout) findViewById(R.id.llHistorySummary);
    }
}
