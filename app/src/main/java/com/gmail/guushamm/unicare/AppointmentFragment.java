package com.gmail.guushamm.unicare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Erwin on 26-5-2016.
 */
public class AppointmentFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate tab_layout and setup Views.
        View view = inflater.inflate(R.layout.appointment_layout, null);

        return view;
    }

}