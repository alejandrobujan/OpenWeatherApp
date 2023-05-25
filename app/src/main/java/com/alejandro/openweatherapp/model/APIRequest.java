package com.alejandro.openweatherapp.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIRequest {
    private static final String apiKey = "0776477a4d15f54d82e4c105f937e4d4";
    private String city;
    private String icon;
    private String description;
    private String temperature;
    private String humidity;
    private String windSpeed;
    private String feelsLike;
    private String minimum;
    private String maximum;
    private String sunrise;
    private String sunset;
    private int offset;
    private double lat;
    private double lng;

    public APIRequest(String query){
        HttpURLConnection connection = null;
        String res = "";
        String urlParameters = "";
        try{
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + query + "&units=metric&appid=" + this.apiKey);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            res = response.toString();
            JSONObject json = new JSONObject(res);
            this.city = json.getString("name");
            JSONObject weather = (JSONObject)json.getJSONArray("weather").get(0);
            this.description = weather.getString("description");
            this.icon = weather.getString("icon");
            this.temperature = ((JSONObject)json.get("main")).getString("temp");
            this.feelsLike = ((JSONObject)json.get("main")).getString("feels_like");
            this.minimum = ((JSONObject)json.get("main")).getString("temp_min");
            this.maximum = ((JSONObject)json.get("main")).getString("temp_max");
            this.sunrise = ((JSONObject)json.get("sys")).getString("sunrise");
            this.sunset = ((JSONObject)json.get("sys")).getString("sunset");
            this.humidity = ((JSONObject)json.get("main")).getString("humidity");
            this.windSpeed = ((JSONObject)json.get("wind")).getString("speed");
            this.offset = Integer.parseInt(json.getString("timezone"));
            this.lat = Double.parseDouble(((JSONObject)json.get("coord")).getString("lat"));
            this.lng = Double.parseDouble(((JSONObject)json.get("coord")).getString("lon"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            Log.d("Request", res);
        }

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
