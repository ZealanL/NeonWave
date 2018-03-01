package application;
	
import java.util.ArrayList;

import application.Classes.Particle;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

//	Made by Zealan Laferte :D
//	Feel free to mess around!
//  Screw with the physics! Make it so you can throw around lines! Do something fun with this!

public class Main extends Application {
	
	//Just basic screen stuffs
	public static int w = 1920;
	public static int h = 1440;
	public static Canvas canvas;
	public static GraphicsContext gc;
	
	//Mode options. Go ahead and mess with these. Mode3D is for cool 3D-ness. Particle mode is for... well... particles.
	public static boolean Mode3D = false;
	public static boolean particleMode = false;
	
	public static int drawX = 0;
	public static int drawY = 0;
	public static boolean shouldDraw = false;
	
	//Arraylist for storing the particles.
	public static ArrayList<Particle> particles = new ArrayList<Particle>();
	
	//Line break points. This way when you stop clicking and click again, we can seperate the different batches of lines/particles.
	//Without this, everything is connected.
	public static ArrayList<Integer> lineBreakPoints = new ArrayList<Integer>();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			//Lets make our width and height match that of the user's screen (not incuding the taskbar).
			w = (int) Screen.getPrimary().getBounds().getMaxX();
			h = (int) Screen.getPrimary().getBounds().getMaxY();
			
			//Blah blah blah crazy-canvas-root-borderplane-application-css-mouse-event-canvas-effects-cursor-window-settings stuff.
			BorderPane root = new BorderPane();
			canvas = new Canvas(w,h);
			Scene scene = new Scene(root,w,h);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			root.getChildren().add(canvas);
			
			//Icons are cool.
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Thumb.png")));
			
			//Titles are also cool.
			primaryStage.setTitle("Neon Wave v1.0");
			
			//Give our canvas a little glow.
			canvas.setEffect(new Glow(0.9));
			
			//Are we not in 3D mode? Give us some more glow!
			if (!Mode3D) {
				Glow glow1 = new Glow(0.9);
				Glow glow2 = new Glow(0.9);
				glow1.setInput(glow2);
				canvas.setEffect(glow1);
			}
			
			//Don't mind these casual mouse events.
			scene.setOnMouseDragged(event -> {
				drawX = (int) event.getX();
				drawY = (int) event.getY();
			});
			
			scene.setOnMouseMoved(event -> {
				drawX = (int) event.getX();
				drawY = (int) event.getY();
			});
			
			scene.setOnMousePressed(event -> {
				drawX = (int) event.getX();
				drawY = (int) event.getY();
				shouldDraw = true;
			});
			
			scene.setOnMouseReleased(event -> {
				shouldDraw = false;
				lineBreakPoints.add(particles.size());
			});
			
			//Switch modes!
			scene.setOnKeyPressed(event -> {
				if (event.getCode() == KeyCode.R) {
					Mode3D = !Mode3D;
				}
			});
			
			root.setCursor(Cursor.NONE);
			
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		//Start our render timer.
		startTimer();
	}
	
	public final void startTimer() {
		//Let's just take a moment of silence to appreciate the JavaFX Animation Timer.
		
		//Set gc.
		gc = canvas.getGraphicsContext2D();
		
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long timestamp) {
				update();
			}
		};
		
		//Start the thing.
		timer.start();
	}

	//OH MAN lets do LITERALLY EVERYTHING (Render, Physics, particle creation).
	static final void update() {
		if (shouldDraw) {
			for (int i = 0; i < 2; i++) {
				Particle p = new Particle(drawX, drawY);
				particles.add(p);
			}
			
		}
		
		//Clear everythin' each frame.
		Render.clearCanvas();
		
		//Update them particles with math-physics-stuff.
		ParticlePhysics.updateParticleMotion();

		//Do we even HAVE any particles?
		if (particles.size() > 0) {
			
			//Oh. Seems we do. Render them!
			Render.renderParticles();
		}
		
		//Now you can see your mouse!
		Render.renderMouse();
	}
	
	
	//Ew default main class.
	public static void main(String[] args) {
		launch(args);
	}
}
