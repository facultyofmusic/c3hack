package lanex.one;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class StaticEntity {
	protected Vector2f position;
	protected float radius;
	protected String img;
	protected boolean flag = false;
	
	public StaticEntity (float x, float y, float radius, String img)
	{
		position = new Vector2f(x, y);
		this.radius = radius;
		this.img = img;
	}
	
	public boolean checkCollision(Character c) {
		return Point.distance(position.x, position.y, c.position.x,
				c.position.y) < c.radius + radius;
	}
	
	public void render(Graphics g) {
		g.drawImage(ERM.getImage(img), position.x
				- ERM.getImage(img).getWidth() / 2,
				position.y - ERM.getImage(img).getHeight() / 2);
	}
	
	public void onHit ()
	{
		
	}
	
	public void update (Player p)
	{
		if (checkCollision (p))
		{
			onHit();
			flag = true;
		}
	}
}
