package lanex.crystaline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import ui.AudioInputProcessor;

public class Shiny extends BasicGame {

	static Ball[] balls = new Ball[100];
	static final int X_SIZE = 800;
	static final int Y_SIZE = 600;
	Color bg;
	File theme;
	static Input in;
	static AppGameContainer app;
	static float currentPitch;
	static float[] history = new float[X_SIZE],
			pitchHistory = new float[X_SIZE];

	public Shiny(String title) {
		super(title);
		theme = new File("Settings/BnW.shiny-theme");
	}

	public static void main(String[] args) {
		for (int x = 0; x < X_SIZE; x++) {
			history[x] = 0;
		}

		try {
			app = new AppGameContainer(new Shiny("Project Crystaline V0.2"));
			app.setDisplayMode(X_SIZE, Y_SIZE, false);
			app.setSmoothDeltas(true);
			app.setVSync(true);
			app.setShowFPS(false);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	private static class Ball {
		float radius = 10;
		double speed = 0;
		double dir = 0;
		float x = 0;
		float y = 0;
		Color c;
		ArrayList<Sparkle> particles;
		Graphics g;

		float min = 30;
		float cycleSize = 12;
		float numCycles = 3;

		public Color colorShift(float pitch) {
			if (!(pitch > 30 && pitch < 1000))
				return c;

			if (in.isKeyDown(Input.KEY_C)) {

				min = (float) ((99 * min + pitch - 12) / 100);
			}

			pitch -= min;
			float multiplyer = pitch / cycleSize / numCycles;
			float r = 1;
			float g = 0;
			float b = 0;
			// 0 = r, 1 = g, 2 = b

			int step = 0;

			while (pitch >= 0) {
				switch (step) {
				case (0):
					g += Math.min(pitch / 2, 1);
					break;
				case (1):
					r -= Math.min(pitch / 2, 1);
					break;
				case (2):
					b += Math.min(pitch / 2, 1);
					break;
				case (3):
					g -= Math.min(pitch / 2, 1);
					break;
				case (4):
					r += Math.min(pitch / 2, 1);
					break;
				default:
					b -= Math.min(pitch / 2, 1);
					break;
				}
				step = (step + 1) % 6;
				pitch -= 2;
			}

			return new Color(r * multiplyer, g * multiplyer, b * multiplyer);
		}

		public Ball(Color col, Graphics gr) {
			dir = Math.random() * Math.PI * 2;
			speed = Math.random() * 10 + 10;
			x = (float) (Shiny.X_SIZE * Math.random());
			y = (float) (Shiny.Y_SIZE * Math.random());
			c = col;
			particles = new ArrayList<Sparkle>();
			g = gr;
		}

		public void update(int delta) {
			double temp = 69D
					+ (12D * Math
							.log((float) AudioInputProcessor.frequency / 440D))
					/ Math.log(2D);
			c = colorShift((float) temp);
			currentPitch = (float) temp;

			x += speed * Math.cos(dir);
			y += speed * Math.sin(dir);

			if (x < 0 && Math.cos(dir) < 0)
				dir = Math.PI - dir;

			if (x + 2 * radius > Shiny.X_SIZE && Math.cos(dir) > 0)
				dir = Math.PI - dir;

			if (y < 0 && Math.sin(dir) < 0)
				dir = 2 * Math.PI - dir;

			if (y + 2 * radius > Shiny.Y_SIZE && Math.sin(dir) > 0)
				dir = 2 * Math.PI - dir;

			particles.add(new Sparkle(x + (float) Math.random() * 2 * radius, y
					+ (float) Math.random() * 2 * radius, c));

			for (int i = 0; i < particles.size(); i++) {
				if (particles.get(i).alive(delta))
					particles.remove(i);
			}
		}

		public class Sparkle {
			float x;
			Color c;
			float y;
			int time;
			static final int MAX_TIME = 300;

			public Sparkle(float x, float y, Color c) {
				this.c = c;
				this.x = x;
				this.y = y;
				time = MAX_TIME;
			}

			public boolean alive(int delta) {
				time -= delta;
				return time < 0;
			}

			public void update(Graphics g) {
				g.setColor(c);

				g.setLineWidth(1);
				g.drawLine(x + 5 * time / MAX_TIME, y, x - 5 * time / MAX_TIME,
						y);
				g.drawLine(x, y + 5 * time / MAX_TIME, x, y - 5 * time
						/ MAX_TIME);

//				g.setLineWidth(2);
//				if (time <= 2)
//					return;
//				g.drawLine(x + 5 * time / MAX_TIME - 2, y, x - 5 * time
//						/ MAX_TIME + 2, y);
//				g.drawLine(x, y + 5 * time / MAX_TIME - 2, x, y - 5 * time
//						/ MAX_TIME + 2);

			}
		}

		public void drawBall() {
			g.setColor(c);
			// g.drawOval(x, y, 2 * radius, 2 * radius);
			for (int i = 0; i < particles.size(); i++) {
				particles.get(i).update(g);
			}
		}

	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		g.setColor(bg);
		g.fillRect(0, 0, X_SIZE, Y_SIZE);
		for (int i = 0; i < balls.length; i++) {
			balls[i].drawBall();
		}

		String[] notes = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#",
				"A", "A#", "B" };

		String note = (currentPitch > 0) ? notes[((int) (currentPitch + 0.5) % 12)]
				: "NaN";

		g.setColor(Color.white);

		g.drawString((new StringBuilder("Note: ")).append(note).toString(), 20,
				20);
		g.drawString(
				(new StringBuilder("Freq: ")).append(
						AudioInputProcessor.frequency).toString(), 20, 40);
		g.drawString((new StringBuilder("Pitch No.: ")).append(currentPitch)
				.toString(), 20, 60);

		// g.drawLine(0, (currentPitch * 5), 20, (currentPitch * 5));

		// g.setLineWidth(4);

		for (int x = 0; x < X_SIZE; x++) {
			g.setColor(Color.white);
			g.fillRect(X_SIZE - x, Y_SIZE + 279 - (history[x] * 8), 1, 10);
			g.setLineWidth(2);
			g.setColor(Color.red);
			g.fillRect(X_SIZE - x, Y_SIZE + 283 - (pitchHistory[x] * 8), 1, 2);
		}

		// g.setLineWidth(1);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		in = gc.getInput();
		AudioInputProcessor aiprocessor = new AudioInputProcessor();
		(new Thread(aiprocessor)).start();

		ArrayList<Color> colors = new ArrayList<Color>();
		BufferedReader fis = null;
		try {
			fis = new BufferedReader(new FileReader(theme));
			while (true) {
				try {
					colors.add(new Color(Integer.parseInt(fis.readLine(), 16)));
				} catch (NumberFormatException e) {
					break;
				}
			}
			bg = colors.get(colors.size() - 1);
			for (int i = 0; i < balls.length; i++) {
				balls[i] = new Ball(colors.get((int) (Math.random() * (colors
						.size() - 1))), gc.getGraphics());
			}

		} catch (FileNotFoundException e) {
			bg = Color.black;
			for (int i = 0; i < balls.length; i++) {
				balls[i] = new Ball(Color.white, gc.getGraphics());
			}
		} catch (IOException e) {

		}

	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		for (int i = 0; i < balls.length; i++) {
			balls[i].update(delta);
		}

		for (int x = X_SIZE - 1; x > 0; x--) {
			history[x] = history[x - 1];
			pitchHistory[x] = pitchHistory[x - 1];
		}
		pitchHistory[0] = currentPitch;
		history[0] = (int) (currentPitch + 0.5);
	}
}
