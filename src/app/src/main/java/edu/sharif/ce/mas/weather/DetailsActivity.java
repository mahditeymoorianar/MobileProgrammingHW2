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
    TextView feelsLikeText;
    TextView name;

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
        feelsLikeText = findViewById(R.id.textView2);
        name = findViewById(R.id.name);

        Intent intent = getIntent();
        int dayIndex = intent.getIntExtra("dayIndex", -1);
        if (dayIndex != -1) {

            Day currentDay = Day.days.get(dayIndex);

            int id = 0;
            if (currentDay.weatherIcon.equals("mist")){
                id = R.drawable.misty;
                tempTextView.setTextColor(getResources().getColor(R.color.white));
                tempFeelsLikeTextView.setTextColor(getResources().getColor(R.color.white));
                feelsLikeText.setTextColor(getResources().getColor(R.color.white));
            }
            else if (currentDay.weatherIcon.equals("broken_clouds")){
                id = R.drawable.clouds;
            }
            else if (currentDay.weatherIcon.equals("few_clouds")
                    || currentDay.weatherIcon.equals("scattered_clouds") ){
                id = R.drawable.few_clouds2;
            }

            else if (currentDay.weatherIcon.equals("clear_sky")){
                id = R.drawable.sunny;
            }
            else if (currentDay.weatherIcon.equals("snow")){
                id = R.drawable.snowy;
            }
            else if (currentDay.weatherIcon.equals("rain") ||
                    currentDay.weatherIcon.equals("shower_rain")){
                id = R.drawable.rainy;
            }

            name.setText(HomeFragment.cityKey);
            weatherIconImageView.setImageResource(id);
            tempTextView.setText(currentDay.temperature + "°");
            nightTempTextView.setText(currentDay.nightTemp + "°");
            tempFeelsLikeTextView.setText(currentDay.temperature_feels_like + "°");
            windSpeedTextViewDetails.setText(currentDay.wind_speed + " KM/Hour");
            morningTempTextView.setText(currentDay.morningTemp + "°");
            eveningTempTextView.setText(currentDay.eveningTemp + "°");

        } else {
            // toast an error
        }

    }
}