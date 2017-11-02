package assignment.weatherunderground;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnGetHistory;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIWidgets();
    }

    private void initUIWidgets() {
        btnGetHistory = (Button) findViewById(R.id.btnGetHistory);
        btnGetHistory.setOnClickListener(MainActivity.this);

        datePicker = (DatePicker) findViewById(R.id.datePickerActivityMain);
        Calendar c = Calendar.getInstance();
        datePicker.init(c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH),
                        null);
    }

    private String formatSelectedDate() {
        return datePicker.getYear()
                + String.format(Locale.US, "%02d", (datePicker.getMonth() + 1))
                + String.format(Locale.US, "%02d", datePicker.getDayOfMonth());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGetHistory) {
            String selectedDate = formatSelectedDate();
            Intent intent = new Intent(MainActivity.this, WeatherDetailsActivity.class);
            intent.putExtra(WeatherDetailsActivity.EXTRA_KEY_REQUESTED_DATE, selectedDate);
            startActivity(intent);
        }
    }
}
