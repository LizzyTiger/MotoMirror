package com.morristaedt.mirror.modules;


import java.util.HashMap;
import java.util.Random;

/**
 * Created by Sally/Mary May 2018
 */
public class EncouragementModule {

    public static void getStatement(final EncListener encListener) {

        String statement = "Life is awesome";

        HashMap <Integer,String> statements = new HashMap <Integer,String>();
        statements.put(0,"Take it one step at a time! ");
        statements.put(1,"You can do it. I believe in you. ");
        statements.put(2,"You are fantastic! ");
        statements.put(3,"It gets better. ");
        statements.put(4,"Don't worry. ");
        statements.put(5,"You have beautiful eyes! ");
        statements.put(6,"The best is yet to come. ");
        statements.put(7,"You are fully capable of getting through tough times. ");
        statements.put(8,"This is temporary! ");
        statements.put(9,"You're almost there. ");
        statements.put(10,"You’ve got this... don’t worry. ");
        statements.put(11,"After the darkest night, shines the brightest sun. ");
        statements.put(12,"You inspire me. ");
        statements.put(13,"With the new day comes new strength and new thoughts. ");
        statements.put(14,"It always seems impossible until it’s done. ");
        statements.put(15,"Always do your best, What you plant now, you will harvest later. ");
        statements.put(16,"Keep your eyes on the stars, and your feet on the ground. ");
        statements.put(17,"I’m so proud of you. I just wanted to tell you in case no one has. ");
        statements.put(18,"You are gold baby, solid gold. ");
        statements.put(19,"You are amazing, you are brave, you are strong. ");
        statements.put(20,"You are capable of amazing things. ");
        int randomIndex = new Random().nextInt(statements.size());
        statement = statements.get(randomIndex);

        encListener.ongetStatements(statement);

    }


    public static String getStatement() {

        String statement = "Life is awesome";

        HashMap <Integer,String> statements = new HashMap <Integer,String>();
        statements.put(0,"Take it one step at a time!");
        statements.put(1,"You can do it. I believe in you.");
        statements.put(2,"You are fantastic");
        statements.put(3,"It gets better");
        statements.put(4,"Don't worry");
        statements.put(5,"You have beautiful eyes!");
        statements.put(6,"The best is yet to come.");
        statements.put(7,"You are fully capable of getting through tough times.");
        statements.put(8,"This is temporary!");
        statements.put(9,"You're almost there.");
        statements.put(10,"You’ve got this... don’t worry.");
        statements.put(11,"After the darkest night, shines the brightest sun.");
        statements.put(12,"You inspire me.");
        statements.put(13,"With the new day comes new strength and new thoughts.");
        statements.put(14,"It always seems impossible until it’s done.");
        statements.put(15,"Always do your best, What you plant now, you will harvest later.");
        statements.put(16,"Keep your eyes on the stars, and your feet on the ground.");
        statements.put(17,"I’m so proud of you. I just wanted to tell you in case no one has.");
        statements.put(18,"You are gold baby, solid gold.");
        statements.put(19,"You are amazing, you are brave, you are strong.");
        statements.put(20,"You are capable of amazing things.");

        int randomIndex = new Random().nextInt(statements.size());
        statement = statements.get(randomIndex);

        return(statement);

    }

    public interface EncListener {
        void ongetStatements(String result);

    }

}
