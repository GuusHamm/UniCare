package com.gmail.guushamm.unicare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by GuusHamm on 2016-06-02.
 */
public class YoutubePlayerFragment extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// Inflate tab_layout and setup Views.
		View view = inflater.inflate(R.layout.youtube_view_layout, null);

		WebView webView =(WebView) view.findViewById(R.id.YoutubeWebView);

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		List<String> videos = new ArrayList<>();

		videos.add("https://www.youtube.com/embed/8v2mEg9Cpvs");
		videos.add("https://www.youtube.com/embed/ye27aIJD6qg");
		videos.add("https://www.youtube.com/embed/0pz5C1nIKqU");

		Random random = new Random();

		webView.loadUrl(videos.get(random.nextInt(videos.size())));

		return view;
	}
}
