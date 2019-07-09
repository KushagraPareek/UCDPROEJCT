package module6;

import java.util.List;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import processing.core.PGraphics;

/** 
 * A class to represent AirportMarkers on a world map.
 *   
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
public class AirportMarker extends CommonMarker {
	public static List<SimpleLinesMarker> routes;
	
	public AirportMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
	
	}
	
	@Override
	public void drawMarker(PGraphics pg, float x, float y) {
		pg.fill(255,255,0);
		pg.rect(x, y, 10, 10);
		
		
		
	}

	@Override
	public void showTitle(PGraphics pg, float x, float y) {
		 // show rectangle with title
		 String name = "Name: "+getName()+"\n";
		 String country = "Country: "+getCountry()+"\n";
		 String city   = "City: "+getCity()+"\n";
		
		 String info = name + country + city;
		 int width  = country.length()*13;
		 int height = info.length()*2;
		 pg.fill(254,255,205);
		 pg.rect(x-5,y-5,width,height); 
		 pg.textSize(12);
	     pg.fill(0);
	     pg.text("Airpot Info",x+10,y+10);
		 pg.text(info,x+20,y+30);
				 
		
		// show routes
		
		
	}
	
	public String getName(){
		return (String)getProperty("name");
	}
	
	public String getCity(){
		return (String)getProperty("city");
	}
	
	public String getCountry(){
		return (String)getProperty("country");
	}
	public int getCode(){
		return Integer.parseInt((String)getProperty("code"));
	}
}
