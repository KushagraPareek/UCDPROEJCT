package module1;
import processing.core.*;

@SuppressWarnings("serial")
public class MyApplet extends PApplet{

	private PImage image;
	private String url ="https://images.pexels.com/photos/457882/pexels-photo-457882.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";
	public void setup(){
		size(200,200);
		image = loadImage(url,"jpeg");
		image.resize(0,height);
	}
	
	public void draw(){
		image(image,0,0);
		int[] color = new int[3];
		color = getRatios(second());
		fill(color[0],color[1],color[2]);
		ellipse(3*width/4,height/5,width/5,height/5);
		
	}
	
	private int[] getRatios(float second){
		
		int[] rgb =  new int[3];
		float diff =  (int)Math.abs(30-second);
		float ratio = diff/30;
		
		rgb[0] = (int)(255*ratio);
		rgb[1] = (int)(255*ratio);
		rgb[2] = 0;
		return rgb;
	}
}
