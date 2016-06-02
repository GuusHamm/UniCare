package com.gmail.guushamm.unicare;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Erwin on 26-5-2016.
 */
public class AppointmentFragment extends Fragment {

    static final long ONE_MINUTE_IN_MILLIS = 60000;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inflate tab_layout and setup Views.
        View view = inflater.inflate(R.layout.appointment_layout, null);

        // Button to add appointment
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == getActivity().RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                System.out.println(contents);

                JSONArray qrCodeArray = null;
                JSONObject qrCode = null;

                try {
                    qrCodeArray = new JSONArray(contents);
                    qrCode = qrCodeArray.getJSONObject(0);

                    Calendar beginTime = Calendar.getInstance();

                    beginTime.set(qrCode.getInt("year"), qrCode.getInt("month") - 1, qrCode.getInt("day"), qrCode.getInt("hour"), qrCode.getInt("minute"));

                    Calendar endTime = Calendar.getInstance();

                    endTime.setTime(new Date(beginTime.getTimeInMillis() + (qrCode.getInt("duration") * ONE_MINUTE_IN_MILLIS)));

                    String title = String.format("Afspraak met %s", qrCode.getString("doctor"));

                    String location = qrCode.getString("location");

                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, title)
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    startActivity(intent);

                } catch (Exception e) {
                    Toast toast = Toast.makeText(getActivity(), "Wooow That QR code is not in a valid format", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
            if (resultCode == getActivity().RESULT_CANCELED) {
                //handle cancel
            }
        }
    }

}
