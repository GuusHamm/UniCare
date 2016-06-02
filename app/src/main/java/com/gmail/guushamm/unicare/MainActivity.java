package com.gmail.guushamm.unicare;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
	NavigationView navigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

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
				for(int entry = 0; entry < fm.getBackStackEntryCount(); entry++){
					stackName = fm.getBackStackEntryAt(entry).getName();
				}
				if(stackName != null) {
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
				}
				else
				{
					navigationView.getMenu().getItem(0).setChecked(true);
					setTitle(navigationView.getMenu().getItem(0).getTitle());
				}

			}
		});

		// Inflate the first fragment, which is also the holder of the tabbed swipe view.
		fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containerView, new HomeFragment() ).commit();
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
		} else if(id == R.id.nav_afspraak) {
			fragmentTransaction.addToBackStack("Afspraken");
            fragmentTransaction.replace(R.id.containerView, new AppointmentFragment()).commit();
		} else if (id == R.id.nav_wachtrij) {
			fragmentTransaction.addToBackStack("Wachtrij");
            fragmentTransaction.replace(R.id.containerView, new QueueFragment()).commit();
		} else if (id == R.id.nav_video) {
			fragmentTransaction.addToBackStack("Video's");
			fragmentTransaction.replace(R.id.containerView, new YoutubePlayerFragment()).commit();
		} else if(id == R.id.nav_route) {
			Address origin = new Address("kerkstraat", "5", "Casteren");
			Address destination = new Address("De Run", "4600", "Veldhoven");
			openGoogleMaps(origin, destination);

			//Testing
			Address test = new Address(origin.toJson());
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
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		openGoogleMaps(null, new Address("De Run", "4600", "Veldhoven"));
	}

	public void openGoogleMaps(Address origin, Address destination) {
		// Ask permission if it's not set for location
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
			} else {
				ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
			}
		}

		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			GPSTracker gps = new GPSTracker(this);
			double lat = 0.0;
			double longitude = 0.0;
			if (gps.canGetLocation()) {
				lat = gps.getLatitude();
				longitude = gps.getLongitude();

				String googleMapsString = "http://maps.google.com/maps?saddr=LATITUDE,LONGITUDE&daddr=DESSTREET+DESNUMBER+DESCITY";
				//		String googleMapsString = "http://maps.google.com/maps?saddr=ORIGINSTREET+ORIGINNUMBER+ORIGINCITY&daddr=DESSTREET+DESNUMBER+DESCITY";
				//
				//		googleMapsString = googleMapsString.replace("ORIGINSTREET", origin.getStreet());
				//		googleMapsString = googleMapsString.replace("ORIGINNUMBER", origin.getNumber());
				//		googleMapsString = googleMapsString.replace("ORIGINCITY", origin.getCity());
				googleMapsString = googleMapsString.replace("LATITUDE", String.valueOf(lat));
				googleMapsString = googleMapsString.replace("LONGITUDE", String.valueOf(longitude));


				//Destination
				googleMapsString = googleMapsString.replace("DESSTREET", destination.getStreet());
				googleMapsString = googleMapsString.replace("DESNUMBER", destination.getNumber());
				googleMapsString = googleMapsString.replace("DESCITY", destination.getCity());

				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsString));
				startActivity(intent);
			} else {
				gps.showSettingsAlert();
			}
		}


	}


}
