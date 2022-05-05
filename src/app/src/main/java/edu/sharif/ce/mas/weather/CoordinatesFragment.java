package edu.sharif.ce.mas.weather;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class CoordinatesFragment extends Fragment {

    EditText xInput;
    EditText yInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_city, container
                , false);
        xInput = root.findViewById(R.id.xinput);
        yInput = root.findViewById(R.id.yinput);
        return root;
    }
}
