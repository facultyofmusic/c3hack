package lanex.c3;

import java.util.LinkedList;
import java.util.List;

import lanex.c3.midi.Channel;
import lanex.c3.midi.MusicMap;
import lanex.c3.midi.MusicPlayer;
import lanex.c3.midi.ScrollingMusicSheet;
import lanex.engine.Button;
import lanex.engine.ERM;
import lanex.engine.ScreenPage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class C3Gameover extends ScreenPage {

	private Button menu_button, retry_button;

	public static float score, maxScore;

	public static List<Short> indivScores;

	public static String lastName;
	public static Channel lastChannel;

	public C3Gameover() {
		menu_button = new Button(C3App.RENDER_WIDTH - 309, 585, 256, 85,
				"btn_mainmenu.png", Color.red);
		retry_button = new Button(C3App.RENDER_WIDTH / 2 - 225, 520, 450, 150,
				"btn_retry.png", Color.green);
		indivScores = new LinkedList<>();
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING

		g.clear();
		g.setColor(Color.white);

		// ERM.listRes();
		// System.out.println("IMAGE: " + ERM.getImage("room.png"));

		g.drawImage(ERM.getImage("def_background.png"), 0, 0);

		g.drawString(lastName + " : " + score / maxScore + "%", 10, 10);

		float notesMissed = 0f, stdDev = 0f;
		
		float average = score /maxScore * 300;
		

		for (Short s : indivScores) {
			notesMissed += s > 0 ? 1 : 0;
			stdDev += s - average;
		}
		
		g.drawString(notesMissed / indivScores.size() + "% notes hit", 10, 30);

		g.drawString(stdDev / indivScores.size() + " standard deviation from a " + average + " average.", 10, 50);

		
		
		menu_button.render(g);
		retry_button.render(g);

		// WIDTH SHOULD BE 400
		// HEIGHT SHOULD BE 100

	}

	@Override
	public void keyPressed(int key, char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(int key, char c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		menu_button.updateHoverStatus(newx, newy);
		retry_button.updateHoverStatus(newx, newy);
	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		// CHECK BUTTONS
		{
			if (menu_button.ifOnButton(x, y)) {
				C3App.splash.reset();
				C3SplashScreen.setRedirect("main_menu");
				C3App.setPage("splash");
			} else if (retry_button.ifOnButton(x, y)) {
				score = 0;
				indivScores.clear();
				C3Game.testMap = MusicMap.fromPath(lastName);
				C3Game.musicPlayer = new MusicPlayer();
				C3Game.scrollSheet = new ScrollingMusicSheet(lastChannel);
				C3Game.paused = false;
				C3Game.playing = false;

				C3App.splash.reset();
				C3SplashScreen.setRedirect("game");
				C3App.setPage("splash");
			}
		}

	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub

	}

}
