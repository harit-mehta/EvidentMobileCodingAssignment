package assignment.weatherunderground;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class WeatherDetailsActivity extends AppCompatActivity {

    static final String EXTRA_KEY_REQUESTED_DATE = "extraKeyRequestedDate";

    private ListView lvObservations;
    private RadioGroup rgHistoryOptions;
    private LinearLayout llSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        initUIWidgets();
        processIntent(getIntent());
    }

    private void processIntent(Intent incomingIntent) {
        String requestedDate = incomingIntent.getStringExtra(EXTRA_KEY_REQUESTED_DATE);
        if (requestedDate != null && !requestedDate.equals("")) {
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
                        HistoryElement historyElement = historyResponseModel.getHistory();
                        if (historyElement.getDailysummary() != null
                                && historyElement.getDailysummary().size() > 0
                                && historyElement.getObservations() != null
                                && historyElement.getObservations().size() > 0) {
                            loadWeatherHistory(historyElement.getDailysummary(), historyElement.getObservations());
                        } else {
                            showNoHistoryAvailableDialog();
                        }
                    } else {
                        showErrorDialog(error);
                    }
                }
            });
        } else {
            showErrorDialog("Could not retrieve the selected date.");
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

    private void showNoHistoryAvailableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WeatherDetailsActivity.this);
        builder.setTitle("History Not Available");
        builder.setMessage("Weather history for the requested date is not available. Please try again with a " +
                                   "different date.");
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

    private void showErrorDialog(String error) {
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
