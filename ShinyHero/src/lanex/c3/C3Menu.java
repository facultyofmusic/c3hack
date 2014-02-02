package lanex.c3;

import lanex.engine.Button;
import lanex.engine.ERM;
import lanex.engine.ScreenPage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;

public class C3Menu extends ScreenPage {

	private Button start_button, help_button, exit_button;
	public static Music music;

	public C3Menu() {
		help_button = new Button(C3App.RENDER_WIDTH / 2 - 200, 600, 400, 100, "help_button.png", Color.yellow);
		exit_button = new Button(C3App.RENDER_WIDTH / 2  + 200, 600, 500, 100, "highscore_button.png", Color.green);
		start_button = new Button(C3App.RENDER_WIDTH / 2 - 600, 600, 400, 100, "start_button.png", Color.red);
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING
//		if (music == null) {
//			try{
//				music = new Music("data/music/music.ogg");
//			}catch (Exception ex){
//				ex.printStackTrace();
//			}
//			music.loop();
//		}

		g.clear();
		g.setColor(Color.white);
		g.drawString("IN GAME!!!", 100, 100);

		// ERM.listRes();
		// System.out.println("IMAGE: " + ERM.getImage("room.png"));

		g.drawImage(ERM.getImage("menu_back.png"), 0, 0);

		help_button.render(g);
		exit_button.render(g);
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
		help_button.updateHoverStatus(newx, newy);
		start_button.updateHoverStatus(newx, newy);
		exit_button.updateHoverStatus(newx, newy);
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
				C3App.splash.reset();
				C3App.setPage("help");
			} else if (start_button.ifOnButton(x, y)) {
				C3App.splash.reset();
				C3App.setPage("custom");
			} else if (exit_button.ifOnButton(x, y)) {
				System.exit(0);
			}
		}

	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub

	}

}
