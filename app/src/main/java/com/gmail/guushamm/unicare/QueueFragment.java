package com.gmail.guushamm.unicare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.TimeZone;

public class QueueFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate tab_layout and setup Views.
        final View view = inflater.inflate(R.layout.queue_layout, null);

		// Instantiate the RequestQueue.
		RequestQueue queue = Volley.newRequestQueue(view.getContext());

//		This is the site that hosts our api, so we send a request to it to get the number of waiting people
		String url = "http://www.guushamm.tech/unicare";

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						TextView waitingTextView = (TextView) view.findViewById(R.id.peopleWaiting);
						TextView timeTextView = (TextView) view.findViewById(R.id.appointmentTime);
						TextView waitTime = (TextView) view.findViewById(R.id.waitTime);
						waitingTextView.setText("Response: " + response.toString());
						try {
							waitingTextView.setText(String.format("%d",response.getInt("wachtende")));

							// Make curent date and add extra minutes
							Date date = new Date();
							DateTime dt = new DateTime(date);
							DateTime newdt = dt.plusMinutes(response.getInt("wachttijd"));
							newdt.withZone(DateTimeZone.forTimeZone(TimeZone.getDefault()));


							timeTextView.setText(newdt.toString("HH:mm"));
							waitTime.setText(String.format("%s min",response.getString("wachttijd")));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {


					}
				});

		queue.add(jsObjRequest);

        return view;
    }

}
