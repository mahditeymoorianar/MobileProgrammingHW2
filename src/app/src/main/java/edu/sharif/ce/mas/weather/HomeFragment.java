package edu.sharif.ce.mas.weather;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

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

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    ConstraintLayout cityLayout;
    RadioGroup radioGroup;
    Timer timer = new Timer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container
                , false);
        radioGroup = root.findViewById(R.id.radiogroup);
        cityLayout = root.findViewById(R.id.cityLayout);
        radioGroup.check(0);
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                if (i == R.id.cityradiobutton) {
                    cityLayout.removeAllViews();
                    EditText cityInp = new EditText(getContext());
                    cityInp.setBackgroundResource(R.drawable.edit_text_bg);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    cityInp.setLayoutParams(params);
                    cityInp.setHint("City Name");
                    cityInp.setGravity(Gravity.CENTER);
                    cityLayout.addView(cityInp);
                    cityInp.setOnKeyListener((view, i1, keyEvent) -> {
                        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                            timer.cancel();
                            timer = new Timer();
                            if (i1 == KeyEvent.KEYCODE_ENTER) {
                                HomeFragment.getCoordinatesFromName(cityInp.getText().toString());
                                return true;
                            }
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    HomeFragment.getCoordinatesFromName(cityInp.getText().toString());
                                }
                            }, 5000);
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
                            timer.cancel();
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    HomeFragment.getCoordinatesFromName(cityInp.getText().toString());
                                }
                            }, 5000);
                        }
                    });
                }
                else if (i == R.id.xyradiobutton){
                    cityLayout.removeAllViews();
                    EditText xInp = new EditText(getContext());
                    xInp.setBackgroundResource(R.drawable.edit_text_bg);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                            300, ViewGroup.LayoutParams.WRAP_CONTENT);
                    xInp.setLayoutParams(params);
                    xInp.setHint("X");
                    xInp.setInputType(InputType.TYPE_CLASS_NUMBER);
                    xInp.setGravity(Gravity.CENTER);
                    xInp.setId(View.generateViewId());
                    cityLayout.addView(xInp);
                    EditText yInp = new EditText(getContext());
                    yInp.setBackgroundResource(R.drawable.edit_text_bg);
                    yInp.setLayoutParams(params);
                    yInp.setHint("Y");
                    yInp.setInputType(InputType.TYPE_CLASS_NUMBER);
                    yInp.setGravity(Gravity.CENTER);
                    yInp.setId(View.generateViewId());
                    cityLayout.addView(yInp);
                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(cityLayout);
                    constraintSet.connect(yInp.getId(),ConstraintSet.LEFT,xInp.getId(),
                            ConstraintSet.RIGHT,20);
                    constraintSet.connect(yInp.getId(), ConstraintSet.RIGHT, cityLayout.getId(),
                            ConstraintSet.RIGHT, 10);
                    constraintSet.connect(xInp.getId(), ConstraintSet.LEFT, cityLayout.getId(),
                            ConstraintSet.LEFT, 10);
                    constraintSet.applyTo(cityLayout);
                    xInp.setOnKeyListener((v, keyCode, event) -> {
                        if (event.getAction() == KeyEvent.ACTION_DOWN && !yInp.getText().toString().equals("")) {
                            timer.cancel();
                            timer = new Timer();
                            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                HomeFragment.requestData(xInp.getText().toString(), yInp.getText().toString());
                                return true;
                            }
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    HomeFragment.requestData(xInp.getText().toString(), yInp.getText().toString());
                                }
                            }, 5000);
                        }
                        return false;
                    });
                    yInp.setOnKeyListener((v, keyCode, event) -> {
                        if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                !xInp.getText().toString().equals("")) {
                            timer.cancel();
                            timer = new Timer();
                            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                HomeFragment.requestData(xInp.getText().toString(),
                                        yInp.getText().toString());
                                return true;
                            }
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    HomeFragment.requestData(xInp.getText().toString(),
                                            yInp.getText().toString());
                                }
                            }, 5000);
                        }
                        return false;
                    });
                }
        });
        return root;
    }


    public static void requestData(String x, String y){
        // TODO: request weather data from x y
        System.out.println(x);
        System.out.println(y);
    }
    public static void getCoordinatesFromName(String name){
        // TODO: request coordinates from city name and the requestData
        System.out.println(name);
    }
}