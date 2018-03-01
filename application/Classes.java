package application;

//Here we store all of our classes... I have gravity point in here but I never put that to use.
//Maybe some other time.
public class Classes {
	
	//This is all pretty self-explanatory.
	
	public static class Particle {
		public float x;
		public float y; 
		public float yVel = (float)((Math.random() - 0.5)*5);
		public float xVel = (float)((Math.random() - 0.5)*5);
		
		public Particle(int startx, int starty) {
			x = startx;
			y = starty;
		}
	}
	
	public static class GravityPoint {
		public int x;
		public int y;
		public double power = 50;
	}
}
