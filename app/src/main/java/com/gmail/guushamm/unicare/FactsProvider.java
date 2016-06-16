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
        String[] facts= new String[]{"Nerve impulses to and from the brain travel as fast as 170 miles per hour.",
                "The brain operates on the same amount of power as 10-watt light bulb.",
                "The human brain cell can hold 5 times as much information as the Encyclopedia Britannica.",
                "Your brain uses 20% of the oxygen that enters your bloodstream.",
                "The brain is much more active at night than during the day.",
                "Scientists say the higher your I.Q. the more you dream.",
                "Neurons continue to grow throughout human life.",
                "Information travels at different speeds within different types of neurons.",
                "The brain itself cannot feel pain.",
                "80% of the brain is water.",
                "Facial hair grows faster than any other hair on the body.",
                "Every day the average person loses 60-100 strands of hair.",
                "Women’s hair is about half the diameter of men’s hair.",
                "The fastest growing nail is on the middle finger.",
                "There are as many hairs per square inch on your body as a chimpanzee.",
                "Blondes have more hair.",
                "Fingernails grow nearly 4 times faster than toenails.",
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

    public String getRandomFact()
    {
        Random random = new Random();
        int randomint = random.nextInt(factsList.size());
        return factsList.get(randomint);

    }
}
