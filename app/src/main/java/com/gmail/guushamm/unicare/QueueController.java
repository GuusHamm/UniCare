package com.gmail.guushamm.unicare;

import android.os.CountDownTimer;
import android.view.View;
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

/**
 * Created by guushamm on 16/06/16.
 */
public class QueueController {
	private int waiting;
	private String appointmentTime;
	private int waitTime;
	private static TextView waitingTextView;
	private static TextView appointmentTimeTextView;
	private static TextView waitTimeTextView;
	private static TextView homeTimeTextView;


	private static QueueController instance;

	public static QueueController getInstance(View view){
		waitingTextView = (TextView) view.findViewById(R.id.peopleWaiting);
		appointmentTimeTextView = (TextView) view.findViewById(R.id.appointmentTime);
		waitTimeTextView = (TextView) view.findViewById(R.id.waitTime);
		homeTimeTextView = (TextView) view.findViewById(R.id.textAppointmentOvertime);

		if (instance == null){
			instance = new QueueController(view);
		}else {
			instance.updateTextViews();
		}

		return instance;
	}

	private QueueController(final View view) {

		RequestQueue queue = Volley.newRequestQueue(view.getContext());

//		This is the site that hosts our api, so we send a request to it to get the number of waiting people
		String url = "http://www.guushamm.tech/unicare/wait";

		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(final JSONObject response) {
				try {
					// Make curent date and add extra minutes
					Date date = new Date();
					DateTime dt = new DateTime(date);
					DateTime newdt = dt.plusMinutes(response.getInt("wachttijd"));
					newdt.withZone(DateTimeZone.forTimeZone(TimeZone.getDefault()));

					appointmentTime = newdt.toString("HH:mm");

					waitTime = response.getInt("wachttijd");
					waiting = response.getInt("wachtende");

					final int waitTimePerWaiting = (waitTime * 60000) / waiting;

					CountDownTimer waitingCountDownTimer = new CountDownTimer((waitTime * 60 * 1000),waitTimePerWaiting) {
						@Override
						public void onTick(long millisUntilFinished) {
							waiting--;
							updateTextViews();

						}

						@Override
						public void onFinish() {
							waiting = 0;
							updateTextViews();
						}
					}.start();

					CountDownTimer countDownTimer = new CountDownTimer((waitTime * 60 * 1000),60000) {
						@Override
						public void onTick(long millisUntilFinished) {
							waitTime--;
							updateTextViews();
						}

						@Override
						public void onFinish() {
							instance = new QueueController(view);
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
	}

	private void updateTextViews(){
		if (waitingTextView != null){
			waitingTextView.setText(String.format("%d",waiting));
		}

		if (waitTimeTextView != null){
			waitTimeTextView.setText(String.format("%d min", waitTime));
		}

		if (appointmentTimeTextView != null){
			appointmentTimeTextView.setText(String.format("%s",appointmentTime));
		}

		if(homeTimeTextView != null){
			homeTimeTextView.setText(String.format("(+%d min)", waitTime));
		}

	}
}
