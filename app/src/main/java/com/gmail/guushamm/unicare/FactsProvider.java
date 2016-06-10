package com.gmail.guushamm.unicare;

import java.util.ArrayList;
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
        factsList.add("Banging your head against a wall burns 150 calories an hour.");
        factsList.add("In the UK, it is illegal to eat mince pies on Christmas Day!");
        factsList.add("Pteronophobia is the fear of being tickled by feathers!");
        factsList.add("When hippos are upset, their sweat turns red.");
        factsList.add("A flock of crows is known as a murder.");
        factsList.add("“Facebook Addiction Disorder” is a mental disorder identified by Psychologists.");
        factsList.add("The average woman uses her height in lipstick every 5 years.");
        factsList.add("29th May is officially “Put a Pillow on Your Fridge Day“.");
        factsList.add("Cherophobia is the fear of fun.");
        factsList.add("Human saliva has a boiling point three times that of regular water.");
        factsList.add("If you lift a kangaroo’s tail off the ground it can’t hop.");
        factsList.add("Hyphephilia are people who get aroused by touching fabrics.");
        factsList.add("Billy goats urinate on their own heads to smell more attractive to females.");

    }

    public String getRandomFact()
    {
        Random random = new Random();
        int randomint = random.nextInt(13);
        return factsList.get(randomint);

    }
}
