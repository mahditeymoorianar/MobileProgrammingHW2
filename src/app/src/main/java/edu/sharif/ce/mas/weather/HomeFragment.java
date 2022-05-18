package edu.sharif.ce.mas.weather;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import edu.sharif.ce.mas.weather.Model.Day;

public class HomeFragment extends Fragment {
    ConstraintLayout cityLayout;
    RadioGroup radioGroup;
    RecyclerView daysRecyclerView;
    DaysRecyclerViewAdapter recyclerViewAdapter;
    Handler handler = new Handler(Looper.getMainLooper());
    public static String cityKey;

    final String APP_ID = "608ce4a24c71ce732aeea8dcf11a59a9";
    final String WEATHER_URL_ONE_CALL = "https://api.openweathermap.org/data/2.5/onecall";
    final String WEATHER_URL_LOCATION = "https://api.openweathermap.org/data/2.5/weather";
    final String MAPBOX_URL = "https://api.mapbox.com/geocoding/v5";
    final long MIN_TIME = 5000;
    final float MIN_DISTANCE = 1000;
    final int REQUEST_CODE = 101;

    static boolean coOrd = true;
    static String x = "";
    static String y = "";
    static String cityName = "";
    static boolean changed = false;
    String Location_Provider = LocationManager.GPS_PROVIDER;


    LocationManager mLocationManager;
    LocationListener mLocationListener;

//    private ActivityResultLauncher<String> requestPermissionLauncher =
//            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
//                if (isGranted) {
//                    // Permission is granted. Continue the action or workflow in your
//                    // app.
//                } else {
//                    // Explain to the user that the feature is unavailable because the
//                    // features requires a permission that the user has denied. At the
//                    // same time, respect the user's decision. Don't link to system
//                    // settings in an effort to convince the user to change their
//                    // decision.
//                }
//            });


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        if (ContextCompat.checkSelfPermission(
//                getActivity(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) ==
//                PackageManager.PERMISSION_GRANTED) {
////            performAction(...);
//        } else {
//            // You can directly ask for the permission.
//            // The registered ActivityResultCallback gets the result of this request.
//            requestPermissionLauncher.launch(Manifest.permission.LOCATION);
//        }

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container
                , false);

        daysRecyclerView = root.findViewById(R.id.daysRecyclerView);
        daysRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewAdapter = new DaysRecyclerViewAdapter(getActivity(), Day.getDays());
        daysRecyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
        radioGroup = root.findViewById(R.id.radioGroup);
        cityLayout = root.findViewById(R.id.cityLayout);
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.cityradiobutton) {
                coOrd = false;
                cityLayout.removeAllViews();
                cityLayout.setForegroundGravity(Gravity.CENTER);
                EditText cityInp = new EditText(getContext());
                cityInp.setBackgroundResource(R.drawable.edit_text_bg);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                cityInp.setLayoutParams(params);
                cityInp.setHint("City Name");
                cityInp.setPadding(25, 8, 25, 8);
                cityInp.setGravity(Gravity.CENTER);
                cityInp.setId(View.generateViewId());
                cityLayout.addView(cityInp);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(cityLayout);
                constraintSet.connect(cityInp.getId(), ConstraintSet.TOP, cityLayout.getId(),
                        ConstraintSet.TOP, 65);
                constraintSet.applyTo(cityLayout);
                cityInp.setOnKeyListener((view, i1, keyEvent) -> {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                        cityKey = cityInp.getText().toString();
                        handler.removeCallbacksAndMessages(null);
                        if (i1 == KeyEvent.KEYCODE_ENTER) {
                            getCoordinatesFromName(cityInp.getText().toString());
                            return true;
                        }
                        handler.postDelayed(() -> getCoordinatesFromName(cityInp.getText().toString()), 5000);
                    }
                    return false;
                });
                cityInp.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        cityKey = cityInp.getText().toString();
                        handler.removeCallbacksAndMessages(null);
                        handler.postDelayed(() -> {
                            if (cityInp.getText().toString() != null){
                                getCoordinatesFromName(cityInp.getText().toString());
                            }
                        }, 5000);
                    }
                });
            } else if (i == R.id.xyradiobutton) {
                coOrd = true;
                cityLayout.removeAllViews();
                EditText xInp = new EditText(getContext());
                xInp.setBackgroundResource(R.drawable.edit_text_bg);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        300, ViewGroup.LayoutParams.WRAP_CONTENT);
                xInp.setLayoutParams(params);
                xInp.setHint("Longitude");
                xInp.setInputType
                        (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                xInp.setGravity(Gravity.CENTER);
                xInp.setId(View.generateViewId());
                cityLayout.addView(xInp);
                EditText yInp = new EditText(getContext());
                yInp.setBackgroundResource(R.drawable.edit_text_bg);
                yInp.setLayoutParams(params);
                yInp.setHint("Latitude");
                yInp.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                yInp.setGravity(Gravity.CENTER);
                yInp.setId(View.generateViewId());
                cityLayout.addView(yInp);
                yInp.setPadding(20, 20, 20, 20);
                xInp.setPadding(20, 20, 20, 20);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(cityLayout);
                constraintSet.connect(yInp.getId(), ConstraintSet.TOP, xInp.getId(),
                        ConstraintSet.BOTTOM, 15);
                constraintSet.connect(yInp.getId(), ConstraintSet.BOTTOM, cityLayout.getId(),
                        ConstraintSet.BOTTOM, 5);
                constraintSet.connect(xInp.getId(), ConstraintSet.TOP, cityLayout.getId(),
                        ConstraintSet.TOP, 5);
                constraintSet.applyTo(cityLayout);
                xInp.setOnKeyListener((v, keyCode, event) -> {
                    if (event.getAction() == KeyEvent.ACTION_DOWN && !yInp.getText().toString().equals("")) {
                        handler.removeCallbacksAndMessages(null);
                        cityKey = xInp.getText().toString() + "°" +", "+
                                yInp.getText().toString() + "°";
                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                            x = xInp.getText().toString();
                            y = yInp.getText().toString();
                            requestData(xInp.getText().toString(), yInp.getText().toString(), false, cityKey);
                            return true;
                        }
                        handler.postDelayed(() -> {
                            cityKey = xInp.getText().toString() + "°" +", "+
                                    yInp.getText().toString() + "°";
                            x = xInp.getText().toString();
                            y = yInp.getText().toString();
                            requestData(xInp.getText().toString(), yInp.getText().toString(), false, cityKey);
                        }, 5000);
                    }
                    return false;
                });
                yInp.setOnKeyListener((v, keyCode, event) -> {
                    if (event.getAction() == KeyEvent.ACTION_DOWN &&
                            !xInp.getText().toString().equals("")) {
                        handler.removeCallbacksAndMessages(null);
                        cityKey = xInp.getText().toString() + "°" +", "+
                                yInp.getText().toString() + "°";
                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                            requestData(xInp.getText().toString(),
                                    yInp.getText().toString(), false, cityKey);
                            x = xInp.getText().toString();
                            y = yInp.getText().toString();
                            return true;
                        }
                        handler.postDelayed(() -> {
                            cityKey = xInp.getText().toString() + "°" +", "+
                                    yInp.getText().toString() + "°";
                            x = xInp.getText().toString();
                            y = yInp.getText().toString();
                            requestData(xInp.getText().toString(), yInp.getText().toString(), false, cityKey);
                        }, 5000);
                    }
                    return false;
                });
            }
        });
        if (coOrd){
            radioGroup.check(R.id.xyradiobutton);
            cityLayout.removeAllViews();
            EditText xInp = new EditText(getContext());
            xInp.setBackgroundResource(R.drawable.edit_text_bg);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    300, ViewGroup.LayoutParams.WRAP_CONTENT);
            xInp.setLayoutParams(params);
            xInp.setHint("Longitude");
            xInp.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            xInp.setGravity(Gravity.CENTER);
            xInp.setId(View.generateViewId());
            cityLayout.addView(xInp);
            EditText yInp = new EditText(getContext());
            yInp.setBackgroundResource(R.drawable.edit_text_bg);
            yInp.setLayoutParams(params);
            yInp.setHint("Latitude");
            yInp.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            yInp.setGravity(Gravity.CENTER);
            yInp.setId(View.generateViewId());
            cityLayout.addView(yInp);
            yInp.setPadding(20, 20, 20, 20);
            xInp.setPadding(20, 20, 20, 20);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(cityLayout);
            constraintSet.connect(yInp.getId(), ConstraintSet.TOP, xInp.getId(),
                    ConstraintSet.BOTTOM, 15);
            constraintSet.connect(yInp.getId(), ConstraintSet.BOTTOM, cityLayout.getId(),
                    ConstraintSet.BOTTOM, 5);
            constraintSet.connect(xInp.getId(), ConstraintSet.TOP, cityLayout.getId(),
                    ConstraintSet.TOP, 5);
            constraintSet.applyTo(cityLayout);
            xInp.setText(x);
            yInp.setText(y);
            xInp.setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN && !yInp.getText().toString().equals("")) {
                    handler.removeCallbacksAndMessages(null);
                    x = xInp.getText().toString();
                    y = yInp.getText().toString();
                    cityKey = xInp.getText().toString() + "°" +", "+
                            yInp.getText().toString() + "°";
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        requestData(xInp.getText().toString(), yInp.getText().toString(), false, cityKey);
                        return true;
                    }
                    handler.postDelayed(() -> {
                        x = xInp.getText().toString();
                        y = yInp.getText().toString();
                        cityKey = xInp.getText().toString() + "°" +", "+
                                yInp.getText().toString() + "°";
                        requestData(xInp.getText().toString(), yInp.getText().toString(), false, cityKey);
                    }, 5000);
                }
                return false;
            });
            yInp.setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        !xInp.getText().toString().equals("")) {
                    handler.removeCallbacksAndMessages(null);
                    cityKey = xInp.getText().toString() + "°" +", "+
                            yInp.getText().toString() + "°";
                    x = xInp.getText().toString();
                    y = yInp.getText().toString();
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        requestData(xInp.getText().toString(),
                                yInp.getText().toString(), false, cityKey);
                        return true;
                    }
                    handler.postDelayed(() -> {
                        x = xInp.getText().toString();
                        y = yInp.getText().toString();
                        cityKey = xInp.getText().toString() + "°" +", "+
                                yInp.getText().toString() + "°";
                        requestData(xInp.getText().toString(), yInp.getText().toString(), false, cityKey);
                    }, 5000);
                }
                return false;
            });
        }
        else{
            radioGroup.check(R.id.cityradiobutton);
            cityLayout.removeAllViews();
            cityLayout.setForegroundGravity(Gravity.CENTER);
            EditText cityInp = new EditText(getContext());
            cityInp.setBackgroundResource(R.drawable.edit_text_bg);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            cityInp.setLayoutParams(params);
            System.out.println(cityKey+"!!!!!!!!!!!!!!!!!!!!!!!!!");
            cityInp.setHint("City Name");
            cityInp.setPadding(25, 8, 25, 8);
            cityInp.setGravity(Gravity.CENTER);
            cityInp.setId(View.generateViewId());
            cityLayout.addView(cityInp);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(cityLayout);
            constraintSet.connect(cityInp.getId(), ConstraintSet.TOP, cityLayout.getId(),
                    ConstraintSet.TOP, 65);
            constraintSet.applyTo(cityLayout);
            cityInp.setText(cityKey);
            cityInp.setOnKeyListener((view, i1, keyEvent) -> {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    cityKey = cityInp.getText().toString();
                    handler.removeCallbacksAndMessages(null);
                    if (i1 == KeyEvent.KEYCODE_ENTER) {
                        getCoordinatesFromName(cityInp.getText().toString());
                        return true;
                    }
                    handler.postDelayed(() -> getCoordinatesFromName(cityInp.getText().toString()), 5000);
                }
                return false;
            });
            cityInp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    cityKey = cityInp.getText().toString();
                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(() -> getCoordinatesFromName(cityInp.getText().toString()), 5000);
                }
            });
        }
        return root;
    }


    public void requestData(String x, String y, boolean check, String key) {

        ConnectivityManager conMgr =  (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){
            Toast.makeText(getActivity(), "Network Error!",
                    Toast.LENGTH_LONG).show();
            if (!check){
                SettingsFragment.mPrefs = getActivity().getPreferences(MODE_PRIVATE);
                String jsonCheck = SettingsFragment.mPrefs.getString(key, "False");
                try {
                    Day.days.clear();
                    if (!jsonCheck.equals("False")) {
                        JSONArray jsonString = new JSONArray(jsonCheck);
                        for (int i = 0; i <= 6; i++) {
                            try {
                                Day.fromJson(jsonString.getJSONObject(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(getActivity(), "Cashed Data!",
                                Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Network Error! No Cashed Data Found",
                                Toast.LENGTH_LONG).show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
                recyclerViewAdapter.updateDataSet();
                System.out.println("An error occured in receiving data");
            }
        }
        else {
            RequestParams params = new RequestParams();
            params.put("lat", x);
            params.put("lon", y);
            params.put("appid", APP_ID);
            sendRequestToNetwork(WEATHER_URL_ONE_CALL, params, key);
        }

    }

    private void sendRequestToNetwork(String API_URL, RequestParams params, String key) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(API_URL, params, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if (API_URL.equals(WEATHER_URL_ONE_CALL)) {
                    System.out.println(cityKey + "cccccccccccc");
                    try {
                        JSONArray jsonString = response.getJSONArray("daily");
                        SharedPreferences.Editor prefsEditor = SettingsFragment.mPrefs.edit();
                        prefsEditor.putString(key, jsonString.toString());
                        prefsEditor.apply();

                        Day.days.clear();
                        for (int i = 0; i <= 6; i++) {
                            Day.fromJson(jsonString.getJSONObject(i));
                        }

                        recyclerViewAdapter.updateDataSet();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String lon = String.valueOf(response.getJSONObject("coord").getDouble("lon"));
                        String lat = String.valueOf(response.getJSONObject("coord").getDouble("lat"));
                        System.out.println(lon + lat + cityKey + "!!!!!!!!!");
                        SharedPreferences.Editor prefsEditor = SettingsFragment.mPrefs.edit();
                        prefsEditor.putString(cityKey, lon + "°" +", " + lat + "°");
                        prefsEditor.apply();

                        requestData(lat, lon, true, lon + "°" +", " + lat + "°");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                  JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);

            }
        });
    }

    public void getCoordinatesFromName(String cityName) {

        ConnectivityManager conMgr =  (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        if (netInfo == null){

            SettingsFragment.mPrefs = getActivity().getPreferences(MODE_PRIVATE);
            String jsonCheck = SettingsFragment.mPrefs.getString(cityKey, "False");
            Toast.makeText(getActivity(), "Network Error!",
                    Toast.LENGTH_LONG).show();
            if (!jsonCheck.equals("False")) {
                requestData("", "", false, jsonCheck);
            }
            else {
                Day.days.clear();
                recyclerViewAdapter.updateDataSet();
                Toast.makeText(getActivity(), "Network Error! No Cashed Data Found",
                        Toast.LENGTH_LONG).show();
            }
            System.out.println("An error occured in receiving data");

        }
        else {
            RequestParams params = new RequestParams();
            params.put("q", cityName);
            params.put("appid", APP_ID);
            sendRequestToNetwork(WEATHER_URL_LOCATION, params, cityKey);
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//
//        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//
//    }



}