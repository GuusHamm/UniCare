package com.gmail.guushamm.unicare;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;
import me.everything.providers.android.calendar.CalendarProvider;
import me.everything.providers.android.calendar.Event;

import com.gmail.guushamm.unicare.Alarm.ScheduleClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	NavigationView navigationView;
	private static final int MY_PERMISSIONS_REQUEST_READ_CALENDAR = 0;
	private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
	private static List<Event> events;
	private CustomAddress destination;
	private CustomAddress origin;
	private boolean calendarPermission;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		progressDialog = new ProgressDialog(MainActivity.this);
		progressDialog.setMessage("Afspraken ophalen");
		progressDialog.setIndeterminate(false);
		progressDialog.setCancelable(true);
		progressDialog.show();
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		events = new ArrayList<Event>();
		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.READ_CALENDAR},
				MY_PERMISSIONS_REQUEST_READ_CALENDAR);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		navigationView.getMenu().getItem(0).setChecked(true);

		// Select current menu item
		getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
			@Override
			public void onBackStackChanged() {
				FragmentManager fm = getSupportFragmentManager();
				String stackName = null;
				for (int entry = 0; entry < fm.getBackStackEntryCount(); entry++) {
					stackName = fm.getBackStackEntryAt(entry).getName();
				}
				if (stackName != null) {
					switch (stackName) {
						case "Start":
							navigationView.getMenu().getItem(0).setChecked(true);
							setTitle(navigationView.getMenu().getItem(0).getTitle());
							break;
						case "Afspraken":
							navigationView.getMenu().getItem(1).setChecked(true);
							setTitle(navigationView.getMenu().getItem(1).getTitle());
							break;
						case "Wachtrij":
							navigationView.getMenu().getItem(2).setChecked(true);
							setTitle(navigationView.getMenu().getItem(2).getTitle());
							break;
						case "Video's":
							navigationView.getMenu().getItem(3).setChecked(true);
							setTitle(navigationView.getMenu().getItem(3).getTitle());
							break;
						default:
							navigationView.getMenu().getItem(0).setChecked(true);
							setTitle(navigationView.getMenu().getItem(0).getTitle());
							break;
					}
				} else {
					navigationView.getMenu().getItem(0).setChecked(true);
					setTitle(navigationView.getMenu().getItem(0).getTitle());
				}

			}
		});

		// Inflate the first fragment, which is also the holder of the tabbed swipe view.
		fragmentManager = getSupportFragmentManager();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();

		NotificationController notificationController = new NotificationController(this);
		notificationController.startAlarm();


		GPSTracker tracker = new GPSTracker(this, this);
		tracker.createProxyAlert("De Run 4600, 5504 DB Veldhoven");


	}

	public void createActualAlert(LocationManager locationManager, LatLong latLong, PendingIntent pendingIntent) {

		ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			System.out.println("Test in check");
			return;
		}
		locationManager.addProximityAlert(latLong.getLatitude(), latLong.getLongitude(), 100, -1, pendingIntent);
	}



	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		if (id == R.id.nav_home) {
			fragmentTransaction.addToBackStack("Start");
			fragmentTransaction.replace(R.id.containerView, new HomeFragment()).commit();
		} else if (id == R.id.nav_afspraak) {
			fragmentTransaction.addToBackStack("Afspraken");
			fragmentTransaction.replace(R.id.containerView, new AppointmentFragment()).commit();
		} else if (id == R.id.nav_wachtrij) {
			fragmentTransaction.addToBackStack("Wachtrij");
			fragmentTransaction.replace(R.id.containerView, new QueueFragment()).commit();
		} else if (id == R.id.nav_video) {
			fragmentTransaction.addToBackStack("Video's");
			fragmentTransaction.replace(R.id.containerView, new YoutubePlayerFragment()).commit();
		} else if (id == R.id.nav_route) {
			origin = new CustomAddress("kerkstraat", "5", "Casteren", "5529 AK");
			destination = new CustomAddress("Michelangelolaan", "2", "Eindhoven", "5623 EJ");
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);

			//Testing
			CustomAddress test = new CustomAddress(destination.toJson());
			System.out.println("breakpoint test");
		} else if (id == R.id.nav_share) {

		} else if (id == R.id.nav_send) {

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


		// Highlight the selected item has been done by NavigationView
		item.setChecked(true);
		// Set action bar title
		setTitle(item.getTitle());
		// Close the navigation drawer
		drawer.closeDrawers();


		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		System.out.println("breakpoint test fragment mainactivity" + String.valueOf(requestCode));
		switch (requestCode) {
			case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION: {
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					openGoogleMaps(destination);
				} else {
					Toast.makeText(this, "GPS permission not given!", Toast.LENGTH_SHORT);

					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}

			case MY_PERMISSIONS_REQUEST_READ_CALENDAR: {
				System.out.println("TEEEEEEEEEEEST" + String.valueOf(grantResults[0]));
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					fillEventList();
					calendarPermission = true;


				} else {
					System.out.println("PERMISSION NOT GIVEN");
					calendarPermission = false;
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}

			// other 'switch' lines to check for other
			// permissions this app might request
		}

	}

	public void openGoogleMaps(CustomAddress destination) {
		// Ask permission if it's not set for location
		/*
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
		}
		else
		{
			//ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
			String googleMapsString = "http://maps.google.com/maps?daddr=DESSTREET+DESNUMBER+DESCITY";

		//Destination
		googleMapsString = googleMapsString.replace("DESSTREET", destination.getStreet());
		googleMapsString = googleMapsString.replace("DESNUMBER", destination.getNumber());
		googleMapsString = googleMapsString.replace("DESCITY", destination.getCity());

			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsString));
			startActivity(intent);
		}
		*/
		String googleMapsString = "http://maps.google.com/maps?daddr=DESSTREET+DESNUMBER+DESCITY";

		//Destination
		googleMapsString = googleMapsString.replace("DESSTREET", destination.getStreet());
		googleMapsString = googleMapsString.replace("DESNUMBER", destination.getNumber());
		googleMapsString = googleMapsString.replace("DESCITY", destination.getCity());

		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsString));
		startActivity(intent);
	}

	public void openGoogleMaps(String destination) {
		String googleMapsString = "http://maps.google.com/maps?daddr=";
		googleMapsString += destination;
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsString));
		startActivity(intent);
	}

	public boolean getCalendarPermission() {
		return calendarPermission;
	}

	public void fillEventList() {
		if (events != null && !events.isEmpty()) {
			events.clear();
		}
		CalendarProvider calendarProvider = new CalendarProvider(this);
		List<me.everything.providers.android.calendar.Calendar> calendars = calendarProvider.getCalendars().getList();
		for (me.everything.providers.android.calendar.Calendar i : calendars) {

			List<Event> eventsList = calendarProvider.getEvents(i.id).getList();
			Iterator<Event> it = eventsList.iterator();
			while (it.hasNext()) {
				Event event = it.next();
				String title = event.title;
				if (title != null && title.contains("Afspraak met")) {
					events.add(event);
				}
			}
//			if (events != null) {
//				for (Event j:events) {
//					if(j.title.contains("Afspraak met")) {
//						events.add(j);
//					}
//				}
//			}
		}
		progressDialog.dismiss();
	}

	public List<Event> getCalendarEventList() {
		return events;
	}

	public void createProxyAlert() {
		GPSTracker gpsTracker = new GPSTracker(this);
		gpsTracker.createProxyAlert("");
	}
}
