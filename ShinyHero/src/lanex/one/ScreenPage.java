package lanex.one;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;


abstract class ScreenPage {
	private ScreenPage instance;
	
	public abstract void render(GameContainer container, Graphics g);
	
	/**
	 * @see org.newdawn.slick.InputListener#keyPressed(int, char)
	 */
	public abstract void keyPressed(int key, char c);

	/**
	 * @see org.newdawn.slick.InputListener#keyReleased(int, char)
	 */
	public abstract void keyReleased(int key, char c);
	


	/**
	 * @see org.newdawn.slick.InputListener#mouseMoved(int, int, int, int)
	 */
	public abstract void mouseMoved(int oldx, int oldy, int newx, int newy);

	/**
	 * @see org.newdawn.slick.InputListener#mouseDragged(int, int, int, int)
	 */
	public abstract void mouseDragged(int oldx, int oldy, int newx, int newy);
	
	/**
	 * @see org.newdawn.slick.InputListener#mousePressed(int, int, int)
	 */
	public abstract void mousePressed(int button, int x, int y);
	/**
	 * @see org.newdawn.slick.InputListener#mouseReleased(int, int, int)
	 */
	public abstract void mouseReleased(int button, int x, int y);
}
