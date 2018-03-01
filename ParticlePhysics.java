package application;

import application.Classes.Particle;

public class ParticlePhysics {
	public static void updateParticleMotion() {
		
		for (Particle p : Main.particles) {
			//Optimization. If a particle is right next to us, just apply the same force to them as to us. Makes processing take a lot less time.
			
			//////////////////////////////
			//This is just simple physics calculations so that all particles will gravitate towards the center of the screen.
			float x = (p.x - Main.w/2);
			float y = (p.y - Main.h/2);
			float dt = 0.1f;
			float distSq = x*x + y*y;
			float dist = (float) Math.sqrt(distSq); 
			float fx = x/dist;
			float fy = y/dist;
			
			//Apply the force
			p.xVel -= dt*fx;
			p.yVel -= dt*fy;
			
			//Do it again for your mouse so you can disrupt things.
			float x2 = (p.x + Main.drawX - Main.w/2);
			float y2 = (p.y + Main.drawY - Main.h/2);
			float dt2 = 0.1f;
			float distSq2 = x2*x2 + y2*y2;
			float dist2 = (float) Math.sqrt(distSq2); 
			float fx2 = x2/dist2;
			float fy2 = y2/dist2;
			
			//Apply the force
			p.xVel += (dt2*fx2)/4;
			p.yVel += (dt2*fy2)/4;
			//////////////////////////////////////////////////////////////////
			
			//Apply friction
			p.xVel = (p.xVel * 0.9999f);
			p.yVel = (p.yVel * 0.9999f);
				
			//Apply the velocity to the x and y, slow it down a bit too.
			p.x += p.xVel/10;
			p.y += p.yVel/10;
		}
	}
}
