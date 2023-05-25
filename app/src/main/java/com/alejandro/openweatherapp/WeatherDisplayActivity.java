package com.alejandro.openweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alejandro.openweatherapp.model.APIThread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDisplayActivity extends AppCompatActivity {
    private TextView cityTextView;
    private TextView tempTextView;
    private TextView humidityTextView;
    private TextView descTextView;
    private TextView windTextView;
    private TextView feelsLikeTextView;
    private TextView minTextView;
    private TextView maxTextView;
    private TextView sunriseTextView;
    private TextView sunsetTextView;
    private ImageView iconImageView;
    private Button addToFavButton;
    private APIThread apiThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_display);

        cityTextView = findViewById(R.id.city_text_view);
        tempTextView = findViewById(R.id.temp_text_view);
        descTextView = findViewById(R.id.description_text_view);
        humidityTextView = findViewById(R.id.humidity_text_view);
        windTextView = findViewById(R.id.windspeed_text_view);
        feelsLikeTextView = findViewById(R.id.feels_like_text_view);
        minTextView = findViewById(R.id.min_text_view);
        maxTextView = findViewById(R.id.max_text_view);
        sunsetTextView = findViewById(R.id.sunset_text_view);
        sunriseTextView = findViewById(R.id.sunrise_text_view);
        iconImageView = findViewById(R.id.icon_image_view);
        addToFavButton = findViewById(R.id.add_to_fav);

        if(getIntent().getBooleanExtra("favsButton", false)){
            addToFavButton.setVisibility(View.VISIBLE);
        }

        apiThread = new APIThread(getIntent().getStringExtra("cityName"));
        apiThread.start();

        try {
            apiThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cityTextView.setText("Weather in "+apiThread.getRequest().getCity());
        tempTextView.setText(apiThread.getRequest().getTemperature() + "°C");
        descTextView.setText(apiThread.getRequest().getDescription());
        iconImageView.setImageBitmap(apiThread.getImageBitmap());
        humidityTextView.setText("Humidity: "+apiThread.getRequest().getHumidity()+"%");
        windTextView.setText("Wind Speed: "+apiThread.getRequest().getWindSpeed()+" km/h");
        feelsLikeTextView.setText("Feels like: "+apiThread.getRequest().getFeelsLike() + "°C");
        minTextView.setText("Minimum: "+apiThread.getRequest().getMinimum() + "°C");
        maxTextView.setText("Maximum: "+apiThread.getRequest().getMaximum() + "°C");
        DateFormat obj = new SimpleDateFormat("HH:mm");
        Date sunrise = new Date(Long.parseLong(apiThread.getRequest().getSunrise()+"000"));
        Date sunset = new Date(Long.parseLong(apiThread.getRequest().getSunset()+"000"));
        sunriseTextView.setText("Sunrise: "+ obj.format(sunrise));
        sunsetTextView.setText("Sunset: "+ obj.format(sunset));
    }


    public void onAddToFavClick(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("cityName", getIntent().getStringExtra("cityName"));
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    public void onLocationClick(View view) {
        // Toast.makeText(this, "Not yet implemented", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(WeatherDisplayActivity.this, MapsActivity.class);
        intent.putExtra("cityName", getIntent().getStringExtra("cityName"));
        intent.putExtra("lat", apiThread.getRequest().getLat());
        intent.putExtra("lng", apiThread.getRequest().getLng());
        startActivity(intent);


    }
}