package com.example.whetherforcost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.whetherforcost.adapter.WeatherListAdapter;
import com.example.whetherforcost.model.WeList;
import com.example.whetherforcost.model.WeatherListData;
import com.example.whetherforcost.model.WeatherResponse;
import com.example.whetherforcost.networkcall.ApiKey;
import com.example.whetherforcost.networkcall.RetrofitClient;
import com.example.whetherforcost.networkcall.SuccessCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText city_name;
    ImageView weather_show_img;
    TextView day5_txt,day4_txt, day3_txt, day2_txt, day1_txt,temperature_txt,humidity_txt,cloud_txt;
    RecyclerView data_rv;
    HashMap<String, List<WeatherListData>> weatherHash = new HashMap<>();
    TreeMap<String, List<WeatherListData>> sortedMap = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIds();
        callWeather();
    }

    void getIds(){

        weather_show_img= findViewById(R.id.weather_show_img);
        city_name= findViewById(R.id.city_name);
        data_rv= findViewById(R.id.data_rv);
        data_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        day5_txt= findViewById(R.id.day5_txt);
        day4_txt=  findViewById(R.id.day4_txt);
        day3_txt=  findViewById(R.id.day3_txt);
        day2_txt=  findViewById(R.id.day2_txt);
        day1_txt=  findViewById(R.id.day1_txt);
        temperature_txt=  findViewById(R.id.temperature_txt);
        humidity_txt=  findViewById(R.id.humidity_txt);
        cloud_txt=  findViewById(R.id.cloud_txt);


        city_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(!city_name.getText().toString().isEmpty() && city_name.getText().toString().length()>2) {
                        changeColor(day1_txt);
                        callWeather();
                    }
                    else
                        Utils.showToast(MainActivity.this, "Please enter city name");
                    return true;
                }
                return false;
            }
        });


         findViewById(R.id.search_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!city_name.getText().toString().isEmpty() && city_name.getText().toString().length()>2) {
                    changeColor(day1_txt);
                    callWeather();
                }
                else
                    Utils.showToast(MainActivity.this, "Please enter city name");
            }
        });

        day1_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(day1_txt);
                WeatherListAdapter weatherListAdapter= new WeatherListAdapter(MainActivity.this, sortedMap.get(day1_txt.getText().toString().trim())) ;
                data_rv.setAdapter(weatherListAdapter);
                setValues(sortedMap.get(day1_txt.getText().toString().trim()));
            }
        });

        day2_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(day2_txt);
                WeatherListAdapter weatherListAdapter= new WeatherListAdapter(MainActivity.this, sortedMap.get(day2_txt.getText().toString().trim())) ;
                data_rv.setAdapter(weatherListAdapter);
                setValues(sortedMap.get(day2_txt.getText().toString().trim()));
            }
        });

        day3_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(day3_txt);
                WeatherListAdapter weatherListAdapter= new WeatherListAdapter(MainActivity.this, sortedMap.get(day3_txt.getText().toString().trim())) ;
                data_rv.setAdapter(weatherListAdapter);
                setValues(sortedMap.get(day3_txt.getText().toString().trim()));
            }
        });

        day4_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(day4_txt);
                WeatherListAdapter weatherListAdapter= new WeatherListAdapter(MainActivity.this, sortedMap.get(day4_txt.getText().toString().trim())) ;
                data_rv.setAdapter(weatherListAdapter);
                setValues(sortedMap.get(day4_txt.getText().toString().trim()));
            }
        });

        day5_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeColor(day5_txt);
                WeatherListAdapter weatherListAdapter= new WeatherListAdapter(MainActivity.this, sortedMap.get(day5_txt.getText().toString().trim())) ;
                data_rv.setAdapter(weatherListAdapter);
                setValues(sortedMap.get(day5_txt.getText().toString().trim()));
            }
        });

    }

    private void changeColor(TextView txt){

        day5_txt.setBackgroundColor(getResources().getColor(R.color.white));
        day5_txt.setTextColor(getResources().getColor(R.color.gray));

        day4_txt.setBackgroundColor(getResources().getColor(R.color.white));
        day4_txt.setTextColor(getResources().getColor(R.color.gray));

        day3_txt.setBackgroundColor(getResources().getColor(R.color.white));
        day3_txt.setTextColor(getResources().getColor(R.color.gray));

        day2_txt.setBackgroundColor(getResources().getColor(R.color.white));
        day2_txt.setTextColor(getResources().getColor(R.color.gray));

        day1_txt.setBackgroundColor(getResources().getColor(R.color.white));
        day1_txt.setTextColor(getResources().getColor(R.color.gray));

        txt.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        txt.setTextColor(getResources().getColor(R.color.white));
    }


    void callWeather(){
        city_name.clearFocus();
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(city_name.getWindowToken(), 0);

        String citys= city_name.getText().toString().trim()+",IN";
        Call<WeatherResponse> call = RetrofitClient.getInstance().getApi().getWeatherData(citys,
                ApiKey.WHETHER_APP_ID);

        call.enqueue(new SuccessCallback<WeatherResponse>(true, MainActivity.this){
            @Override
            public void onSuccess(Response<WeatherResponse> response) {
                super.onSuccess(response);
                if(response.body().getCod().equalsIgnoreCase("200")){
                    setData(response.body());
                }

            }

            @Override

            public void onFailure(Response<WeatherResponse> response) {
                super.onFailure(response);

            }
        });
    }

    void setData(WeatherResponse response){
        fillDataHash(response);
        // setValues(response);

    }

    private void setValues(List<WeatherListData> dataArray){
        if(dataArray != null && !dataArray.isEmpty()) {

            try {
                Glide.with(MainActivity.this).load(dataArray.get(0).getIcon())
                        .into(weather_show_img);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                temperature_txt.setText("" + Utils.round(Double.parseDouble(dataArray.get(0).getTempp()), 1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            humidity_txt.setText("Humidity: " + dataArray.get(0).getHumidity());
            cloud_txt.setText("Cloud: " + dataArray.get(0).getCludStatus());
        }
    }

    private void fillDataHash(WeatherResponse response){
        try {
            weatherHash.clear();
            String dates="";
            List<WeatherListData> weatherListData= new ArrayList<>();

            for(int i=0; i< response.getList().size(); i++){
                WeList weList= response.getList().get(i);
                if(dates.isEmpty() || dates.equalsIgnoreCase(Utils.dateOnly(weList.getDtTxt()))){
                    dates= Utils.dateOnly(weList.getDtTxt());
                    WeatherListData weatherListData1= new WeatherListData();
                    weatherListData1.setTime(Utils.timeOnly(weList.getDtTxt()));
                    weatherListData1.setCloadState(weList.getWeather().get(0).getDescription());
                    weatherListData1.setCludStatus(weList.getWeather().get(0).getMain());
                    weatherListData1.setIcon(weList.getWeather().get(0).getIcon());
                    weatherListData1.setTempp(Utils.getCalTemp(String.valueOf(weList.getMain().getTemp())));
                    weatherListData1.setHumidity(String.valueOf(weList.getMain().getHumidity()));
                    weatherListData.add(weatherListData1);
                }else{
                    weatherHash.put(dates, weatherListData);
                    //weatherListData.clear();
                    weatherListData= new ArrayList<>();

                    dates= Utils.dateOnly(weList.getDtTxt());
                    WeatherListData weatherListData1= new WeatherListData();
                    weatherListData1.setTime(Utils.timeOnly(weList.getDtTxt()));
                    weatherListData1.setCloadState(weList.getWeather().get(0).getDescription());
                    weatherListData1.setCludStatus(weList.getWeather().get(0).getMain());
                    weatherListData1.setIcon(weList.getWeather().get(0).getIcon());
                    weatherListData1.setTempp(Utils.getCalTemp(String.valueOf(weList.getMain().getTemp())));
                    weatherListData1.setHumidity(String.valueOf(weList.getMain().getHumidity()));
                    weatherListData.add(weatherListData1);
                }

            }
            weatherHash.put(dates, weatherListData);
            //  weatherListData.clear();

            //++++++++++++++++++++++
            List<String> date= new ArrayList<>();
            date.clear();
            sortedMap.clear();
            sortedMap.putAll(weatherHash);

            for (Map.Entry<String, List<WeatherListData>> entry : sortedMap.entrySet()) {
                date.add(entry.getKey());
            }
            try {
                day1_txt.setText(date.get(0));
                WeatherListAdapter weatherListAdapter= new WeatherListAdapter(MainActivity.this, sortedMap.get(date.get(0))) ;
                data_rv.setAdapter(weatherListAdapter);

                setValues(sortedMap.get(date.get(0)));

                day2_txt.setText(date.get(1));
                day3_txt.setText(date.get(2));
                day4_txt.setText(date.get(3));
                day5_txt.setText(date.get(4));
            } catch (Exception e) {
                e.printStackTrace();
            }


            //*****************************


        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}