package lanex.c3;

import java.util.HashMap;

import lanex.engine.ScreenPage;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

import ui.AudioInputProcessor;


public class C3App extends BasicGame implements ComponentListener {
	public static AppGameContainer app;
	public static ScreenPage currentPage;
	public static C3SplashScreen splash;
	
	
	private static float alpha = 1;
	private static float musicVol = 1, sfxVol = 1;
	public static int RENDER_WIDTH = 1280, RENDER_HEIGHT = 720;
	
	
	public static HashMap<String, ScreenPage> pages;
	public static GameContainer container;

	
	public static C3InternalConsole THC;
	boolean showConsole = false;

	public C3App(String title) {
		super(title);
	}

	@Override
	public void init(GameContainer con) throws SlickException {
		container = con;
		
		THC = C3InternalConsole.getInstance(con, this);
		THC.append("initializing pages...");

		C3SplashScreen.setRedirect("game");
		
		pages = new HashMap<String, ScreenPage>();

		splash = new C3SplashScreen();

		pages.put("splash", splash);
		pages.put("game", new C3Game());
		pages.put("main_menu", new C3Menu());
		pages.put("help", new C3Help());
		pages.put("custom", new C3Customization());
		pages.put("gameover", new C3Gameover());

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
			g.fillRect(0, 0, C3App.RENDER_WIDTH, C3App.RENDER_HEIGHT);
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

		AudioInputProcessor aiprocessor = new AudioInputProcessor();
		(new Thread(aiprocessor)).start();

		try {
			app = new AppGameContainer(new C3App(
					"c3 hack"), 1280, 720,
					false);
			
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
