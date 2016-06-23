package com.gmail.guushamm.unicare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.*;
import com.gmail.guushamm.unicare.Alarm.ScheduleClient;
import me.everything.providers.android.calendar.Event;

import java.text.SimpleDateFormat;
import java.util.*;

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
		TextView factsText = (TextView) view.findViewById(R.id.factsText);
		FactsProvider factsProvider = new FactsProvider();
		factsText.setText(factsProvider.getRandomFact());
		QueueController.getInstance(view);

		WebView webView =(WebView) view.findViewById(R.id.YoutubeView);

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		List<String> videos = new ArrayList<>();

		videos.add("https://www.youtube.com/embed/8v2mEg9Cpvs");
		videos.add("https://www.youtube.com/embed/ye27aIJD6qg");
		videos.add("https://www.youtube.com/embed/0pz5C1nIKqU#t=6s");

		Random random = new Random();

		webView.loadUrl(videos.get(random.nextInt(videos.size())));

		final FragmentTransaction  fragmentTransaction= this.getActivity().getSupportFragmentManager().beginTransaction();

		FrameLayout frameAfspraken = (FrameLayout) view.findViewById(R.id.frameAfspraken);

		frameAfspraken.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentTransaction.addToBackStack("Afspraken");
				fragmentTransaction.replace(R.id.containerView, new AppointmentFragment()).commitAllowingStateLoss();
			}
		});


		RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);

		relativeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				fragmentTransaction.addToBackStack("Mij  afspraak");
				fragmentTransaction.replace(R.id.containerView, new MyAppointmentFragment()).commitAllowingStateLoss();
			}
		});

		FrameLayout frameNews = (FrameLayout) view.findViewById(R.id.frameNews);

		frameNews.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				String url = "http://www.nu.nl/gezondheid";
//				Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
//				startActivity(i);
				setAlarm();
			}
		});

		return view;
	}

	private void setAlarm() {
		MainActivity main = (MainActivity) getActivity();
		ScheduleClient scheduleClient = new ScheduleClient(main);
		scheduleClient.doBindService();
		Toast.makeText(main, "Alarm set", Toast.LENGTH_SHORT).show();
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
