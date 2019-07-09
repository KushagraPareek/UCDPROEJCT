package module6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.data.ShapeFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimpleLinesMarker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.geo.Location;
import parsing.ParseFeed;
import processing.core.PApplet;

/** An applet that shows airports (and routes)
 * on a world map.  
 * @author Adam Setters and the UC San Diego Intermediate Software Development
 * MOOC team
 *
 */
@SuppressWarnings("serial")
public class AirportMap extends PApplet {
	
	UnfoldingMap map;
	private List<Marker> airportList;
	List<Marker> routeList;
	private CommonMarker lastSelected;
	private CommonMarker lastClicked;
	private HashMap<Integer, Location> airports;
	
	public void setup() {
		// setting up PAppler
		size(800,600, OPENGL);
		
		// setting up map and default events
		map = new UnfoldingMap(this, 50, 50, 750, 550);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// get features from airport data
		List<PointFeature> features = ParseFeed.parseAirports(this, "airports.dat");
		
		// list for markers, hashmap for quicker access when matching with routes
		airportList = new ArrayList<Marker>();
		airports = new HashMap<Integer, Location>();
		
		// create markers from features
		for(PointFeature feature : features) {
			AirportMarker m = new AirportMarker(feature);
	
			m.setRadius(5);
			airportList.add(m);
			
			// put airport in hashmap with OpenFlights unique id for key
			airports.put(Integer.parseInt(feature.getId()), feature.getLocation());
		
		}
		
		
		// parse route data
		List<ShapeFeature> routes = ParseFeed.parseRoutes(this, "routes.dat");
		routeList = new ArrayList<Marker>();
		for(ShapeFeature route : routes) {
			
			// get source and destination airportIds
			int source = Integer.parseInt((String)route.getProperty("source"));
			int dest = Integer.parseInt((String)route.getProperty("destination"));
			
			// get locations for airports on route
			if(airports.containsKey(source) && airports.containsKey(dest)) {
				route.addLocation(airports.get(source));
				route.addLocation(airports.get(dest));
			}
			
			SimpleLinesMarker sl = new SimpleLinesMarker(route.getLocations(), route.getProperties());
		
			//System.out.println(sl.getProperties());
			
			//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		    routeList.add(sl);
		}
		
		
		
		//UNCOMMENT IF YOU WANT TO SEE ALL ROUTES
		map.addMarkers(routeList);
		hideAllRoutes();
		map.addMarkers(airportList);
		
	}
	
	@Override
	public void mouseMoved()
	{
		// clear the last selection
		if (lastSelected != null) {
			for(Marker m: airportList){
				m.setHidden(false);
				
			}
			hideAllRoutes();
			lastSelected.setSelected(false);
			lastSelected = null;
		
		}
		selectMarkerIfHover(airportList);
		
	}
	
	public void mouseClicked(){
		if(lastClicked!=null){
			hideAllRoutes();
			lastClicked = null;
		}
		
		showRoutes();
	}
	
	private void selectMarkerIfHover(List<Marker> airports){
		
		if(lastSelected!=null){
			
			return;
		}
		
		for(Marker m: airports){
			CommonMarker mark = (CommonMarker)m;
			if(mark.isInside(map, mouseX, mouseY)){
				lastSelected = mark;
				lastSelected.setSelected(true);
				for(Marker m1: airports){
					CommonMarker m2 = (CommonMarker)m1;
					if(m2 != lastSelected){
						m2.setHidden(true);
					}
				}
				return;
			}
		}
	}
	
	private void hideAllRoutes(){
		for(Marker m: routeList){
			m.setHidden(true);
		}
	}
	
	private void showRoutes(){
		if(lastClicked!=null){
			return;
		}
		
		for(Marker mark: airportList){
			CommonMarker m = (CommonMarker)mark;
			if(m.isInside(map, mouseX, mouseY)){
				lastClicked = m;
				//TODO: Complete from here
			}
		}
	}
	public void draw() {
		background(0);
		map.draw();
		fill(255,255,255);
		rect(10,10,200,30);
		fill(0);
		text("Airports in the world",20,25);
		
	}
	

}
