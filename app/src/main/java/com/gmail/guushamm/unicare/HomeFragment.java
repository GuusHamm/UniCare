package com.gmail.guushamm.unicare;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.everything.providers.android.calendar.Event;

/**
 * Created by Erwin on 26-5-2016.
 */
public class HomeFragment extends Fragment {

	private boolean calendarPermission;
	private Event nextAppointment;
	private Timer timer;
	private TimerTask timerTask;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Inflate tab_layout and setup Views.
		final View view = inflater.inflate(R.layout.home_layout, null);

		RequestQueue queue = Volley.newRequestQueue(view.getContext());
		String url = "http://www.guushamm.tech/unicare/wait";
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject response) {
				final TextView waitTime = (TextView) view.findViewById(R.id.textAppointmentOvertime);
				try {


					final int[] toWaitTime = {response.getInt("wachttijd")};
					waitTime.setText(String.format("(+%d min)", toWaitTime[0]));


					CountDownTimer countDownTimer = new CountDownTimer((toWaitTime[0] * 60 * 1000),60000) {
						@Override
						public void onTick(long millisUntilFinished) {
							toWaitTime[0]--;
							waitTime.setText(String.format("(+%d min)", toWaitTime[0]));
						}

						@Override
						public void onFinish() {
							waitTime.setText(String.format("(+%d min)", 0));
						}
					}.start();

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

	@Override
	public void onResume() {
		super.onResume();
		calendarPermission = ((MainActivity) getActivity()).getCalendarPermission();
		if (calendarPermission) {
			if (!((MainActivity) getActivity()).getCalendarEventList().isEmpty()) {
				nextAppointment = ((MainActivity) getActivity()).getCalendarEventList().get(0);
				if (nextAppointment != null) {
					setRouteButton(nextAppointment.eventLocation);
					String appointmentInfo = nextAppointment.title + ", " + nextAppointment.eventLocation;
					TextView appointmentInfoText = (TextView) getView().findViewById(R.id.textAppointmentInfo);
					TextView appointmentTimeText = (TextView) getView().findViewById(R.id.textAppointmentTime);
					appointmentInfoText.setText(appointmentInfo);
					appointmentTimeText.setText(new SimpleDateFormat("dd/MM HH:mm").format(new Date(nextAppointment.dTStart)));
				}
			}

		}

		try {
			timer = new Timer();
			timerTask = new TimerTask() {
				@Override
				public void run() {
					updateUI();

				}
			};
			timer.schedule(timerTask, 10000, 10000);
		} catch (IllegalStateException e) {
			android.util.Log.i("Damn", "resume error");
		}


	}

	private void updateUI() {
		getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				TextView factsText = (TextView) ((MainActivity) getActivity()).findViewById(R.id.factsText);
				FactsProvider factsProvider = new FactsProvider();
				factsText.setText(factsProvider.getRandomFact());
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		timer.cancel();
	}

	private void setRouteButton(final String location) {
		ImageButton routeButton = (ImageButton) getActivity().findViewById(R.id.imagebuttonRoute);
		routeButton.setVisibility(View.VISIBLE);

		routeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//                CustomAddress address = new CustomAddress();
				System.out.println(location);
				System.out.println();
				((MainActivity) getActivity()).openGoogleMaps(location);
			}
		});
	}
}
