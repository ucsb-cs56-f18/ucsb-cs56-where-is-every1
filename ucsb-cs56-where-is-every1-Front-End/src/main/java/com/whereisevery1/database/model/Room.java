package com.whereisevery1.database.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.annotation.Id;

public class Room {

    @Id
    public long id;

    private HashMap<String,ArrayList<List<Integer>>> times_dates;
    private int number;

    public Room(int number) {
    	this.times_dates = new HashMap<String,ArrayList<List<Integer>>>();
        this.number = number;
    }

    public int getNumber() {
    	return number;
    }

    public HashMap<String,ArrayList<List<Integer>>> getTimes(){
    	return times_dates;
    }
    public void addTimesDates(String dates, String times) {
    	String [] t = times.split(":|(?i)[a-z]{2}\\s*-*\\s*");
    	String [] d = dates.split("\\s");

    	for(String day : d) {
    		if(this.times_dates.containsKey(day)){
    			this.times_dates.get(day).add(Arrays.asList(Integer.parseInt(t[0]+t[1]), Integer.parseInt(t[2] + t[3])));
    		}
    		else {
    			ArrayList<List<Integer>> temp = new ArrayList<List<Integer>>();
    			temp.add(Arrays.asList(Integer.parseInt(t[0]+t[1]), Integer.parseInt(t[2] + t[3])));
    			times_dates.put(day, temp);
    		}
    	}
    }
}
