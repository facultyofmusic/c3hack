package lanex.one;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Music;
import org.newdawn.slick.ScalableGame;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;

public class OneApp extends BasicGame implements ComponentListener {
	public static int CLIP_X, CLIP_Y, CLIP_WIDTH, CLIP_HEIGHT,
			RENDER_WIDTH = 1280, RENDER_HEIGHT = 720;
	public static AppGameContainer app;
	public static ScreenPage currentPage;
	public static OneSplash splash;
	private static float alpha = 1;
	private static float musicVol = 1, sfxVol = 1;
	public static HashMap<String, ScreenPage> pages;
	public static GameContainer container;
	// public static THConsole c;
	public static OneInternalConsole THC;
	boolean showConsole = false;

	public OneApp(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		
		THC = OneInternalConsole.getInstance(container, this);
		THC.append("initializing pages...");

		OneSplash.setRedirect("main_menu");
		//
		//
		//
		//
		// TEMP!
		OneGame.initNew();
		//
		//
		//
		//
		//

		pages = new HashMap<String, ScreenPage>();

		splash = new OneSplash();

		pages.put("splash", splash);
		pages.put("game", new OneGame());
		pages.put("main_menu", new OneMenu());
		pages.put("help", new OneHelp());
		pages.put("highscore", new OneHighscore());
		pages.put("custom", new OneCustomization());
		pages.put("gameover", new OneGameover());

		setPage("splash");

		THC.append("pages done.");
		THC.newl();

	}

	@Override
	public void update(GameContainer container, int delta) {
	}

	public static void setPage(String id) {
		THC.append("setting current page to: " + id);

		currentPage = pages.get(id);

		alpha = 1;
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		// g.scale(1f, 1f);
		// System.out.println("CURRENTPAGE~: " + currentPage);
		currentPage.render(container, g);

		if (alpha > 0) {
			g.setColor(new Color(0, 0, 0, alpha -= 0.125));
			// System.out.println("SCREEN WIDTH: " + OneApp.app.getWidth() +
			// ", SCREEN HEIGHT" + OneApp.app.getHeight());
			g.fillRect(0, 0, OneApp.RENDER_WIDTH, OneApp.RENDER_HEIGHT);
		}

		if (showConsole)
			THC.render(container, g);
	}

	/**
	 * @see org.newdawn.slick.InputListener#keyPressed(int, char)
	 */
	public void keyPressed(int key, char c) {
		if (c == '`') {
			THC.setFocus(!showConsole);
			showConsole = !showConsole;
		}
		currentPage.keyPressed(key, c);
	}

	/**
	 * @see org.newdawn.slick.InputListener#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
		currentPage.keyReleased(key, c);
	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseMoved(int, int, int, int)
	 */
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		currentPage.mouseMoved(oldx, oldy, newx, newy);
	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseDragged(int, int, int, int)
	 */
	public void mouseDragged(int oldx, int oldy, int newx, int newy) {
		currentPage.mouseDragged(oldx, oldy, newx, newy);
	}

	/**
	 * @see org.newdawn.slick.InputListener#mousePressed(int, int, int)
	 */
	public void mousePressed(int button, int x, int y) {
		currentPage.mousePressed(button, x, y);

	}

	/**
	 * @see org.newdawn.slick.InputListener#mouseReleased(int, int, int)
	 */
	public void mouseReleased(int button, int x, int y) {
		currentPage.mouseReleased(button, x, y);
	}

	public static void main(String args[]) {

		try {
			app = new AppGameContainer(new OneApp(
					"Touhou Project Doujin 12.1"), 1280, 720,
					false);

			// app.setDisplayMode(1280, 720, true);
			// app.setDisplayMode(960, 720, false);
			// app.setDisplayMode(1024, 768, false);

			CLIP_X = (int) (app.getWidth() * 0.046875);
			CLIP_Y = (int) (app.getHeight() * 0.03125);
			CLIP_WIDTH = (int) (app.getWidth() * 0.6015625);
			CLIP_HEIGHT = (int) (app.getHeight() * 0.9375);
			app.setShowFPS(false);
			app.setAlwaysRender(false);
			app.setUpdateOnlyWhenVisible(true);
			app.setClearEachFrame(false);
			app.setTargetFrameRate(60);
			app.setSmoothDeltas(true);
			app.setMusicOn(true);
			app.setVerbose(true);
			app.setVSync(true);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void componentActivated(AbstractComponent source) {
		// TODO Auto-generated method stub

	}
}
