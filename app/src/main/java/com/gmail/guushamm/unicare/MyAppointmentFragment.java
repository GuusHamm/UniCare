package com.gmail.guushamm.unicare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import me.everything.providers.android.calendar.Event;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.Random;

/**
 * Created by guushamm on 17/06/16.
 */
public class MyAppointmentFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// Inflate tab_layout and setup Views.
		final View view = inflater.inflate(R.layout.my_appointment_layout, null);


		Event nextAppointment = ((MainActivity) getActivity()).getCalendarEventList().get(0);

		if (nextAppointment != null){
			TextView textView = (TextView) view.findViewById(R.id.my_appointment_textview);
			LinkedMap<String,String[]> appointmentTypes = new LinkedMap<>();
			appointmentTypes.put("acne", new String[]{"60","Een behandeling met vloeibare stikstof"});
			appointmentTypes.put("een verdachte moedervlek",new String[]{"15","Een controle van de moedervlek"});
			appointmentTypes.put("diabetes onderzoek",new String[]{"30","Een preventief diabetes onderzoek"});

			int randomNumber = new Random().nextInt(appointmentTypes.size());
			String dokter = nextAppointment.title;

			System.out.println(nextAppointment.eventLocation);
//			Antoon Coolenlaan 1, 5644 RX Eindhoven

			String funFact = "";
			if (nextAppointment.eventLocation.equals("Michelangelolaan 2, 5623 EJ Eindhoven")){
				funFact = "\n\nFun fact: Dit ziekenhuis staat op nummer 1 van de kwaliteitsnormen-ranking van Zorgverzekeraars Nederland";
			}
			dokter = dokter.substring(dokter.lastIndexOf(" ")+1);
			textView.setText(dokter);
			textView.setText(String.format("U heeft een afspraak voor %s met dokter %s. De afspraak duurt gemiddeld %s minuten, %s zal tijdens deze afspraak worden uitgevoerd.%s",appointmentTypes.get(randomNumber),dokter,appointmentTypes.getValue(randomNumber)[0],appointmentTypes.getValue(randomNumber)[1],funFact));
		}

		return view;
	}
}
