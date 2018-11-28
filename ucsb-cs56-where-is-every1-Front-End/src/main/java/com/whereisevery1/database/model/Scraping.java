package com.whereisevery1.database.model;

import com.whereisevery1.database.model.Building;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

/*
 * TODO: - Write the loading into database method
 * 		 - Parse time, days, rooms/buildings
 * 		 - Use the building class and room
 */
public class Scraping {
	private static HtmlUnitDriver driver;
	private static String course_url =
			"https://my.sa.ucsb.edu/public/curriculum/coursesearch.aspx";
	private static HashMap<String,Building> buildings;
	
	public Scraping() {
		driver = new HtmlUnitDriver();
		driver.setJavascriptEnabled(true);
		driver.get(course_url);
		
		load_times_rooms_days(driver, get_subjectArea(driver));
	}
	
	public static ArrayList<String> get_subjectArea(HtmlUnitDriver driver){
		Select s = new Select(driver.findElementByXPath("//*[@id=\"ctl00_pageContent_courseList\"]"));
		ArrayList<String> temp = new ArrayList<String>();
		
		for(WebElement e : s.getOptions())
			temp.add(e.getText());
		
		return temp;
	}
	
	public static void load_times_rooms_days(HtmlUnitDriver driver, ArrayList<String> courses){
		
		/* TODO: Loop through each course and level
		 * Start with the one subject area
		 * Then move on to all subject areas
		 * and finally to all course levels
		*/
		for(String c : courses) {
			Select course_editbox = new Select(driver.findElementByXPath("//*[@id=\"ctl00_pageContent_courseList\"]"));
			course_editbox.selectByVisibleText(c);
				Select lvl_editbox = new Select(driver.findElementByXPath("//*[@id=\"ctl00_pageContent_dropDownCourseLevels\"]"));
				lvl_editbox.selectByVisibleText("All");
				WebElement search_button = driver.findElementByXPath("//*[@id=\"ctl00_pageContent_searchButton\"]");
				search_button.click();
				
			for(int i = 1; i <= driver.findElements(By.xpath("//*[@id=\"aspnetForm\"]/table/tbody/tr[3]/td/div/center/table/tbody/tr")).size(); i++){
				String location = driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/table/tbody/tr[3]/td/div/center/table/tbody/tr[" + i + "]/td[9]")).getText();
				String[] location_room = location.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
				System.out.println(location_room[0]);
				if(location_room[0].compareTo("T B A") != 0) {
					if(!buildings.containsKey(location_room[0]))
						buildings.put(location, new Building(location_room[0]));

					buildings.get(location_room[0]).addToRoom((location_room.length == 1)? 0 : Integer.parseInt(location_room[1]),
							driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/table/tbody/tr[3]/td/div/center/table/tbody/tr[" + i + "]/td[7]")).toString(), 
							driver.findElement(By.xpath("//*[@id=\"aspnetForm\"]/table/tbody/tr[3]/td/div/center/table/tbody/tr[" + i + "]/td[8]")).toString());
				}
			}
		}
	}
}