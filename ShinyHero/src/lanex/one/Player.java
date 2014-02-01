package lanex.one;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Player extends Character{
	protected Room currentRoom;
	protected float agroRadius;
	protected static int score = 0;
	public static int potions, armors, boots, weapons;
	
	public Player (float x, float y)
	{
		super(x, y);
		sightRadius = 100;
		agroRadius = 30;
		radius = agroRadius;
		img = "character.png";
		super.MAX_SPEED = 6f;
		mass = 2;
		damage = 4*mass;
		score = 0;
		clearItems();
	}
	
	public static void clearItems(){
		potions = 0;
		armors = 0;
		boots = 0;
		weapons = 0;
	}
	
	@Override
	public void render(Graphics g){
		super.render(g);
		
		g.setColor(Color.blue);
		g.drawOval(position.x - agroRadius, position.y - agroRadius, agroRadius*2, agroRadius*2);
	}
}
