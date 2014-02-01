package lanex.one;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

public class OneMenu extends ScreenPage {

	private Button start_button, help_button, highscore_button;
	public static Music music;

	public OneMenu() {
		help_button = new Button(OneApp.RENDER_WIDTH / 2 - 200, 600, 400, 100,		"help_button.png");
		highscore_button = new Button(OneApp.RENDER_WIDTH / 2 - 200, 500, 400,				100, "highscore_button.png");
		start_button = new Button(OneApp.RENDER_WIDTH / 2 - 600, 500, 400, 100,				"start_button.png");
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING
		if (music == null) {
			try{
				music = new Music("data/music/music.ogg");
			}catch (Exception ex){
				ex.printStackTrace();
			}
			music.loop();
		}

		g.clear();
		g.setColor(Color.white);
		g.drawString("IN GAME!!!", 100, 100);

		// ERM.listRes();
		// System.out.println("IMAGE: " + ERM.getImage("room.png"));

		g.drawImage(ERM.getImage("menu_back.png"), 0, 0);

		help_button.render(g);
		highscore_button.render(g);
		start_button.render(g);

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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int button, int x, int y) {
		// CHECK BUTTONS
		{
			if (help_button.ifOnButton(x, y)) {
				OneApp.splash.reset();
				OneApp.setPage("help");
			} else if (highscore_button.ifOnButton(x, y)) {
				OneHighscore.loadScores();
				OneApp.splash.reset();
				OneApp.setPage("highscore");
			} else if (start_button.ifOnButton(x, y)) {
				OneApp.splash.reset();
				OneApp.setPage("custom");
			}
		}

	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub

	}

}
