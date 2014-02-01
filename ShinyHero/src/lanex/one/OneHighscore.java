package lanex.one;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

public class OneHighscore extends ScreenPage{
	
	private Button menu_button;
	private static ArrayList<Integer> scores;
	
	public OneHighscore(){
		menu_button = new Button(OneApp.RENDER_WIDTH/2 - 200,  550, 400, 100, "menu_button.png");
	}
	
	public static void loadScores(){
		scores = new ArrayList<Integer>();
		int i = 0;
		try{
			BufferedReader in = new BufferedReader(new FileReader("hs.txt"));
			String line = null;
			while((line = in.readLine()) != null && i < 10){
				scores.add(Integer.parseInt(line));
				i++;
			}
			in.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		System.out.println("NUMBER OF SCORES: " + scores.size());
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING
		
		
		g.clear();
		g.setColor(Color.white);	
		
		g.drawImage(ERM.getImage("highscore_back.png"), 0, 0);
		
		menu_button.render(g);

		
		int i = 0;
		for(Integer s : scores){
			g.drawString("    Score: " + s, 40, 200 + 30*i++);
		}

		
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
				OneApp.setPage("main_menu");				
			}
		}
		
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		// TODO Auto-generated method stub
		
	}

}
