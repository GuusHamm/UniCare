package com.gmail.guushamm.unicare;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate tab_layout and setup Views.
        View view = inflater.inflate(R.layout.appointment_layout, null);



        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.add_appointment);
        fab.setColorFilter(Color.parseColor("#FFFFFF"));
        fab.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View view) {
                // Handle the camera action
                try {

                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                    startActivityForResult(intent, 0);

                } catch (Exception e) {

                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                    Intent marketIntent = new Intent(Intent.ACTION_VIEW, marketUri);
                    startActivity(marketIntent);

                }
        	}
        });

        return view;
    }

}
