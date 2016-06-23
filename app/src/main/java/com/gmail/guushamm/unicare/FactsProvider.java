package com.gmail.guushamm.unicare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Chris on 10-6-2016.
 */

public class FactsProvider {
    private List<String> factsList;

    public FactsProvider()
    {
        factsList = new ArrayList<String>();
        String[] facts= new String[]{"Zenuwsignalen van en naar de hersenen gaan ruim 270 kilometer per uur.",
                "The brain operates on the same amount of power as 10-watt light bulb.",
                "De menselijke hersencel kan meer dan 5x zoveel informatie onthouden dan een encyclopedie.",
                "Je hersenen gebruiken 20% van de zuurstof dat je in bloed zit.",
                "De hersenen zijn 's nachts meer bezig dan overdag.",
                "Wetenschappers zeggen hoe hoger je IQ hoe meer je droomt.",
                "Neuronen groeien door gedurende een geheel menselijk leven.",
                "Information travels at different speeds within different types of neurons.",
                "De hersenen zelf kunnen geen pijn voelen.",
                "80% van de hersenen is water.",
                "Facial hair grows faster than any other hair on the body.",
                "Every day the average person loses 60-100 strands of hair.",
                "Women’s hair is about half the diameter of men’s hair.",
                "The fastest growing nail is on the middle finger.",
                "There are as many hairs per square inch on your body as a chimpanzee.",
                "Blondines hebben meer haar.",
                "Vingernagels groeien bijna 4x sneller dan teennagels.",
                "The lifespan of a human hair is 3 to 7 years on average.",
                "You must lose over 50% of your scalp hairs before it is apparent to anyone.",
                "Human hair is virtually indestructible.",
                "The largest internal organ is the small intestine.",
                "The acid in your stomach is strong enough to dissolve razorblades.",
                "You get a new stomach lining every three to four days.",
                "The surface area of a human lung is equal to a tennis court.",
                "Women’s hearts beat faster than men’s.",
                "Scientists have counted over 500 different liver functions.",
                "The aorta is nearly the diameter of a garden hose.",
                "Your left lung is smaller than your right lung to make room for your heart.",
                "You could remove a large part of your internal organs and survive.",
                "The adrenal glands change size throughout life."};
        factsList.addAll(Arrays.asList(facts));
    }

	public String getRandomFact() {
		Random random = new Random();
		int randomint = random.nextInt(factsList.size());
		return factsList.get(randomint);

	}
}
