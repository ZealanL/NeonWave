package application;

import application.Classes.Particle;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class Render {
	
	//Called at the beginning of every frame.
	public static void clearCanvas() {
		Main.gc.setFill(new Color(0,0,0,0.8));
		Main.gc.fillRect(0, 0, Main.w, Main.h);
	}
	
	//Rendering that cute little mouse circle :D
	public static void renderMouse() {
		Main.gc.setFill(new Color(1,1,1,0.5));
		
		double rad = 20;
		
		Main.gc.fillOval(Main.drawX - rad/2, Main.drawY - rad/2, rad, rad);
	}
	
	//Oh boy. Rendering particles into lines.
	public static void renderParticles() {
		
		//An optional particle mode where you can play with particles rather then lines.
		if (!Main.particleMode) {
		
			//For storing the last position of the particle. Of course there is no particle before the first one, so we make it the first one.
			int lastX = (int) Main.particles.get(0).x;
			int lastY = (int) Main.particles.get(0).y;
			
			//For every particle...
			for (int i = 0; i < Main.particles.size(); i++) {
				
				Particle p = Main.particles.get(i);
				
				//Index multiplier. A placeholder for 3D-ness. Makes the older lines look farther back than the new ones.
				double indexMultiplier = 1;
				
				//Use our index multiplier if we are in 3D mode. Else, we're fine with a simple line width of 3.
				if (Main.Mode3D) {
					indexMultiplier = ((double)i / (double)Main.particles.size())/1.5 + 0.25;
					Main.gc.setLineWidth(10 * indexMultiplier);
				} else {
					Main.gc.setLineWidth(3);
				}
	
				//Do we need to break the line?
				if (Main.lineBreakPoints.contains(i)) {
					lastX = (int) p.x;
					lastY = (int) p.y;
				}
				
				double trans = 1;
				
				//Make neon stuff flicker. Looks cool. Not using it right now though.
				/*if (!Main.Mode3D) {
					if (Math.random() > 0.99) trans = 0;
				}*/
				
				//Set the "Stroke" color, "Stroke" the main line. Kinda funny saying it out loud.
				Main.gc.setStroke(new Color((Math.sin(p.x/100) / 2 + 0.5)*indexMultiplier, (Math.sin(p.y/100) / 2 + 0.5)*indexMultiplier, indexMultiplier, trans));
				Main.gc.strokeLine(lastX, lastY, p.x, p.y);
				
				//If we are in 3D-ness, draw psuedo shadows. Wait... Did I spell psuedo right? Idk.
				if (Main.Mode3D) {
					Main.gc.setStroke(new Color(0,0,0,0.8));
					Main.gc.strokeLine(lastX - 2, lastY + 2, p.x - 2, p.y + 2);
				}
				
				//Last, but DEFINITELY not least, set the lastX and lastY to continue our beautiful line.
				lastX = (int) p.x;
				lastY = (int) p.y;
			}
			
		//Are we just doing points? Oh. That makes things way easier. Thats a 4-step process.
		//No need for silly "lastX" or "lastY". Just pixels.
		} else {
			
			//Step 1: Get the pixel writer.
			PixelWriter pw = Main.gc.getPixelWriter();
			
			//Step 2: Loop through all of our particles.
			for (Particle p : Main.particles) {
				
				//Step 3: Set the pixel to a color, like... idk... some cool sine equation that produces neon fun.
				pw.setColor((int)p.x, (int)p.y, new Color((Math.sin(p.x/100) / 2 + 0.5), (Math.sin(p.y/100) / 2 + 0.5), 1, 1));
			}
		}
	}
}
