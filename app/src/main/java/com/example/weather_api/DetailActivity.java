package com.example.weather_api;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    private ListView listView;
    private TextView locationName;
    private JSONArray daysArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        listView = findViewById(R.id.list_view);
        locationName = findViewById(R.id.locationNameView);

        // Parse the JSON data
        String responseData = getIntent().getStringExtra("response_data");

        try {
            JSONObject jsonObject = new JSONObject(responseData);
            daysArray = jsonObject.getJSONArray("days");
            locationName.setText("Address : " + jsonObject.getString("address").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Set the custom adapter to the ListView
        DaysAdapter adapter = new DaysAdapter();
        listView.setAdapter(adapter);
    }

    private class DaysAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return daysArray.length();
        }

        @Override
        public Object getItem(int position) {
            try {
                return daysArray.getJSONObject(position);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_day, parent, false);
            }

            TextView datetimeTextView = convertView.findViewById(R.id.datetime_text_view);
            TextView tempTextView = convertView.findViewById(R.id.temp_text_view);
            TextView humidityTextView = convertView.findViewById(R.id.humidity_text_view);
            TextView precipTextView = convertView.findViewById(R.id.precip_text_view);
            TextView feelslikeTextView = convertView.findViewById(R.id.feelslike_text_view);
            TextView windgustTextView = convertView.findViewById(R.id.windgust_text_view);
            TextView windspeedTextView = convertView.findViewById(R.id.windspeed_text_view);
            TextView winddirTextView = convertView.findViewById(R.id.winddir_text_view);
            TextView pressureTextView = convertView.findViewById(R.id.pressure_text_view);
            TextView cloudcoverTextView = convertView.findViewById(R.id.cloudcover_text_view);
            TextView visibilityTextView = convertView.findViewById(R.id.visibility_text_view);
            TextView solarradiationTextView = convertView.findViewById(R.id.solarradiation_text_view);
            TextView solarenergyTextView = convertView.findViewById(R.id.solarenergy_text_view);

            try {
                JSONObject dayObject = daysArray.getJSONObject(position);
                String datetime = dayObject.getString("datetime");
                double temp = dayObject.getDouble("temp");
                double humidity = dayObject.getDouble("humidity");
                double precip = dayObject.getDouble("precip");
                double feelslike = dayObject.getDouble("feelslike");
                double windgust = dayObject.getDouble("windgust");
                double windspeed = dayObject.getDouble("windspeed");
                double winddir = dayObject.getDouble("winddir");
                double pressure = dayObject.getDouble("pressure");
                double cloudcover = dayObject.getDouble("cloudcover");
                double visibility = dayObject.getDouble("visibility");
                double solarradiation = dayObject.getDouble("solarradiation");
                double solarenergy = dayObject.getDouble("solarenergy");

                datetimeTextView.setText(datetime);
                tempTextView.setText("Temperature: " + temp + "°C");
                humidityTextView.setText("Humidity: " + humidity + "%");
                precipTextView.setText("Precipitation: " + precip);
                feelslikeTextView.setText("Feels Like: " + feelslike + "°C");
                windgustTextView.setText("Wind Gust: " + windgust);
                windspeedTextView.setText("Wind Speed: " + windspeed);
                winddirTextView.setText("Wind Direction: " + winddir);
                pressureTextView.setText("Pressure: " + pressure);
                cloudcoverTextView.setText("Cloud Cover: " + cloudcover);
                visibilityTextView.setText("Visibility: " + visibility);
                solarradiationTextView.setText("Solar Radiation: " + solarradiation);
                solarenergyTextView.setText("Solar Energy: " + solarenergy);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }
}