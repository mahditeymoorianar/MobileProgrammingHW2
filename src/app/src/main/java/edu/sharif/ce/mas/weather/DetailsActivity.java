package edu.sharif.ce.mas.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import edu.sharif.ce.mas.weather.Model.Day;

public class DetailsActivity extends AppCompatActivity {

    ImageView weatherIconImageView;
    TextView nightTempTextView;
    TextView tempTextView;
    TextView tempFeelsLikeTextView;
    TextView windSpeedTextViewDetails;
    TextView morningTempTextView;
    TextView eveningTempTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        weatherIconImageView = findViewById(R.id.weatherImageView);
        nightTempTextView = findViewById(R.id.nightTempTextView);
        tempTextView = findViewById(R.id.tempTextView);
        tempFeelsLikeTextView = findViewById(R.id.tempFeelsLikeTextView);
        windSpeedTextViewDetails = findViewById(R.id.windSpeedTextViewDetails);
        morningTempTextView = findViewById(R.id.morningTempTextView);
        eveningTempTextView = findViewById(R.id.eveningTempTextView);

        Intent intent = getIntent();
        int dayIndex = intent.getIntExtra("dayIndex", -1);
        if (dayIndex != -1) {

            Day currentDay = Day.days.get(dayIndex);

            //int resId = getResources().getIdentifier("rainy",
                //    "drawable", getPackageName());
            //weatherIconImageView.setImageResource(resId);
            tempTextView.setText(currentDay.temperature + "°");
            nightTempTextView.setText(currentDay.nightTemp + "°");
            tempFeelsLikeTextView.setText(currentDay.temperature_feels_like + "°");
            windSpeedTextViewDetails.setText(currentDay.wind_speed + " KM / Hour");
            morningTempTextView.setText(currentDay.morningTemp + "°");
            eveningTempTextView.setText(currentDay.eveningTemp + "°");

        } else {
            // toast an error
        }

    }
}