package lanex.one;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class OneGame extends ScreenPage {
	static Player player;
	static Vector2f mousePosition;
	public static int level = 1;
	boolean mouseON = false;
	public static final float WALL_THICKNESS = 40;
	public static final float leftBound = WALL_THICKNESS,
			rightBound = 720 - WALL_THICKNESS, upBound = WALL_THICKNESS,
			lowBound = 720 - WALL_THICKNESS;
	public static final Rectangle topDoor = new Rectangle(322.5f, 0, 75, 75),
			bottomDoor = new Rectangle(322.5f, 645, 75, 75),
			leftDoor = new Rectangle(0, 322.5f, 75, 75),
			rightDoor = new Rectangle(645, 322.5f, 75, 75);

	// GAME LOGIC
	static Floor currentFloor;
	static Room currentRoom;

	public static void initNew() {
		level = 1;
		player = new Player(100, 100);
		mousePosition = new Vector2f(player.position.x, player.position.y);
		makeFloor();
	}
	public static void makeFloor() {
		currentFloor = new Floor(level);
		currentFloor.generate();
		currentRoom = currentFloor.getRoom(0, 0);
		level++;
	}

	public OneGame() {
		player = new Player(100, 100);
		mousePosition = new Vector2f(0, 0);
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		// PROCESSING

		if (mouseON) {
			Vector2f acceleration = mousePosition.copy().sub(player.position)
					.normalise().scale(1f);
			player.accelerate(acceleration);
			// ERM.getImage("character.png").setRotation((float)
			// acceleration.getTheta());
			// / SET DIRECTION OF PLAYER
		}

		// UPDATE ALL
		{
			player.update();
			currentRoom.update(player);
			checkDoors();
		}

		// OneApp.THC.append("mouse position: " + mousePosition +
		// " Player velocity: " + player.velocity);

		g.clear();
		g.setColor(Color.white);
		//g.drawString("IN GAME!!!", 100, 100);

		// ERM.listRes();
		// System.out.println("IMAGE: " + ERM.getImage("room.png"));

		// ERM.getImage("character.png").setRotation(ERM.getImage("character.png").getRotation()+1);
		currentRoom.render(g);

//		// DEBUG DRAW DOORS TO CHECK
//		g.draw(topDoor);
//		g.draw(bottomDoor);
//		g.draw(leftDoor);
//		g.draw(rightDoor);

		player.render(g);

				
		//DRAW MAP
		g.setColor(Color.black);
		//g.drawRect(980, 0, 300, 300);
		g.fillRect(720, 400, 560, 400);
		
		Image regular = ERM.getImage("small_room.png");
		Image red = ERM.getImage("small_red_room.png");
		regular = regular.getScaledCopy(560/currentFloor.size, 560/currentFloor.size);
		red = red.getScaledCopy(560/currentFloor.size, 560/currentFloor.size);
		for(int x = 0; x < currentFloor.size; x++){
			for(int y = 0; y < currentFloor.size; y++){
				g.drawImage(regular, 720 + x*560/currentFloor.size, y*560/currentFloor.size);
			}
		}
		
		g.drawImage(red, 720 + currentRoom.indX*560/currentFloor.size, currentRoom.indY*560/currentFloor.size);
		
		
		g.drawImage(ERM.getImage("potion.png"), 760, 590);
		g.drawImage(ERM.getImage("boots.png"), 890, 590);
		g.drawImage(ERM.getImage("weapon.png"), 1020, 590);
		g.drawImage(ERM.getImage("armor.png"), 1150, 590);
		

		//STATS
		g.setColor (Color.white);
		g.drawString ("Score: " + player.score,1000,650);
		g.drawString (":" + player.health + " (" + player.potions + ")",800,600);
		g.drawString (":" + player.MAX_SPEED + " (" + player.boots + ")",930,600);
		g.drawString (":" + player.damage + " (" + player.weapons + ")",1060,600);
		g.drawString (":" + player.mass + " (" + player.armors + ")",1190,600);
	}

	public void checkDoors() {
		int x = 0, y = 0;

		if (topDoor.contains(player.position.x, player.position.y)) {
			x = currentRoom.indX;
			y = currentRoom.indY - 1;
			player.position.y = 640;
		} else if (bottomDoor.contains(player.position.x, player.position.y)) {
			x = currentRoom.indX;
			y = currentRoom.indY + 1;
			player.position.y = 80;

		} else if (leftDoor.contains(player.position.x, player.position.y)) {
			x = currentRoom.indX - 1;
			y = currentRoom.indY;
			player.position.x = 640;

		} else if (rightDoor.contains(player.position.x, player.position.y)) {
			x = currentRoom.indX + 1;
			y = currentRoom.indY;
			player.position.x = 80;
		} else {
			return;
		}
		if (x < 0) x = currentFloor.size-1;
		if (y < 0) y = currentFloor.size-1;
		if (x > currentFloor.size-1) x = 0;
		if (y > currentFloor.size-1) y = 0;
		currentRoom = currentFloor.getRoom(x, y);
		ERM.sfx("door.se.ogg");
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
		mousePosition.x = newx;
		mousePosition.y = newy;
	}

	@Override
	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON)
		{
			float theta = (float) Math.atan((y-player.position.y)/(x-player.position.x)); 
			player.velocity = new Vector2f((float)Math.cos(theta)*player.MAX_SPEED/2,(float)Math.sin(theta)*player.MAX_SPEED/2);
		}
		mouseDragged(x, y, x, y);
		mouseON = true;
	}

	@Override
	public void mouseReleased(int button, int x, int y) {
		mouseON = false;
	}

}
