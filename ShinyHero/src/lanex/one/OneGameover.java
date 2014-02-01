package lanex.one;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

public class OneGameover extends ScreenPage{
	
	private Button menu_button;
	
	public OneGameover(){
		menu_button = new Button(OneApp.RENDER_WIDTH/2 - 200,  550, 400, 100, "menu_button.png");
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING
		
		
		g.clear();
		g.setColor(Color.white);
		
		//ERM.listRes();
		//System.out.println("IMAGE: " + ERM.getImage("room.png"));

		
		g.drawImage(ERM.getImage("gameover_back.png"), 0, 0);
		
		menu_button.render(g);
		
		
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
		//CHECK BUTTONS
		{
			if (menu_button.ifOnButton(x, y)){
				OneApp.splash.reset();
				OneSplash.setRedirect("main_menu");		
				OneApp.setPage("splash");					
			}
		}
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
