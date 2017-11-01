package assignment.weatherunderground;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

    private Spinner spHistoryDetailsOptions;
    private ListView lvObservations;
    private TextView tvDailySummary;

    private String[] historyDetailsOptions = new String[]{"Daily Summary", "Observations"};

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
                        updateUIElements(historyElement.getDailysummary(), historyElement.getObservations());
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

    private void updateUIElements(ArrayList<HistoryDailySummaryElement> historyDailySummaryElement,
                                  ArrayList<HistoryObservationElement> historyObservationElement) {
        HistoryObservationsAdapter historyObservationsAdapter = new HistoryObservationsAdapter(WeatherDetailsActivity.this, historyObservationElement);
        lvObservations.setAdapter(historyObservationsAdapter);

        if (historyDailySummaryElement != null && historyDailySummaryElement.size() > 0) {
            HistoryDailySummaryElement dailySummary = historyDailySummaryElement.get(0);
            tvDailySummary.setText(getDailySummaryString(dailySummary));
        }

        spHistoryDetailsOptions.setSelection(0);
    }

    private String getDailySummaryString(HistoryDailySummaryElement dailySummary) {
        StringBuilder sb = new StringBuilder();
        sb.append("Date:");
        sb.append(dailySummary.getDate().getPretty());
        sb.append("\nFog:");
        sb.append(dailySummary.getFog());
        sb.append("\nRain:");
        sb.append(dailySummary.getRain());
        sb.append("\nSnow:");
        sb.append(dailySummary.getSnow());
        sb.append("\nMaxTempi:");
        sb.append(dailySummary.getMaxtempi());
        sb.append("\nMaxTempm:");
        sb.append(dailySummary.getMaxtempm());
        sb.append("\nMeanTempi:");
        sb.append(dailySummary.getMeantempi());
        sb.append("\nMeanTempm:");
        sb.append(dailySummary.getMeantempm());
        sb.append("\nMinTempi:");
        sb.append(dailySummary.getMintempi());
        sb.append("\nMinTempm:");
        sb.append(dailySummary.getMintempm());

        return sb.toString();
    }

    private void initUIWidgets() {
        spHistoryDetailsOptions = (Spinner) findViewById(R.id.spHistoryDetailsOptions);
        spHistoryDetailsOptions.setAdapter(new ArrayAdapter<String>(WeatherDetailsActivity.this,
                                                                    android.R.layout.simple_spinner_item,
                                                                    historyDetailsOptions));
        spHistoryDetailsOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (historyDetailsOptions[position].equals("Daily Summary")) {
                    tvDailySummary.setVisibility(View.VISIBLE);
                    lvObservations.setVisibility(View.GONE);
                } else {
                    tvDailySummary.setVisibility(View.GONE);
                    lvObservations.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        lvObservations = (ListView) findViewById(R.id.lvHistoryObservations);
        tvDailySummary = (TextView) findViewById(R.id.tvDailySummary);
    }
}
