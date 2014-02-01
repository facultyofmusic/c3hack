package lanex.one;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;

public class OneCustomization extends ScreenPage{
	
	private Button hell_button, menu_button;
	
	public OneCustomization(){
		hell_button = new Button(OneApp.RENDER_WIDTH/2 - 200,  550, 400, 100, "start_button.png");
		menu_button = new Button(OneApp.RENDER_WIDTH/2 - 600,  550, 400, 100, "menu_button.png");
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING
		
		
		g.clear();
		
		//ERM.listRes();
		//System.out.println("IMAGE: " + ERM.getImage("room.png"));

		
		g.drawImage(ERM.getImage("custom_back.png"), 0, 0);
		
		hell_button.render(g);
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
			if (hell_button.ifOnButton(x, y)){
				OneGame.initNew();
				OneApp.splash.reset();
				OneSplash.setRedirect("game");		
				OneApp.setPage("splash");				
			}else if (menu_button.ifOnButton(x, y)){
				OneApp.splash.reset();
				OneApp.setPage("main_menu");				
			}
		}
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
