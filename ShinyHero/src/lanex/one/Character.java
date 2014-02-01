package lanex.one;

import java.awt.Point;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

public abstract class Character {
	protected Vector2f position;
	protected int health;
	protected int mass;
	protected float angle;
	protected float maxSpeed;
	protected Vector2f velocity;
	protected float radius;
	protected float MAX_SPEED, SLOW_SPEED = 0.3f;
	protected String img;
	protected float damage;
	protected float sightRadius;
	protected float sight;

	public Character(float x, float y) {
		position = new Vector2f(x, y);
		velocity = new Vector2f(0, 0);
		health = 100;
		sight = 1.0f;
	}

	public void move() {
		position.add(velocity);
	}

	public boolean checkCollision(Character c) {
		return Point.distance(position.x, position.y, c.position.x,
				c.position.y) < c.radius + radius;
	}

	public void accelerate(Vector2f acceleration) {
		angle = (float) acceleration.getTheta();
		if (velocity.length() < MAX_SPEED) {
			velocity.add(acceleration);
		}
	}

	public static void collideCharacters(Character c1, Character c2) {
		Vector2f v1 = c1.velocity.copy().scale(c1.mass);
		Vector2f v2 = c2.velocity.copy().scale(c2.mass);
		float tmas = 1.0f / (c1.mass + c2.mass);
		Vector2f vcm = v2.add(v1).scale(tmas);
		

		c1.velocity = vcm.copy().sub(c1.velocity);
		c2.velocity = vcm.copy().sub(c2.velocity);
		System.out.println(tmas);
	}

	public static void collisionCheck(Character c1, Character c2) {
		if (c1.checkCollision(c2)) {
			ERM.getSound("hit.se.ogg").play();
			Vector2f v1 = c1.velocity;
			Vector2f v2 = c2.velocity;
			collideCharacters(c1, c2);
			v1.sub(c1.velocity);
			v2.sub(c2.velocity);
			c1.health -= v1.length()/10 + c1.damage;
			c2.health -= v2.length() + c2.damage;
		}
	}

	public void update() {
		sightRadius = sight*health + radius;
		position.add(velocity);
		velocity.sub(velocity.copy().normalise().scale(SLOW_SPEED));
		if (velocity.length() < SLOW_SPEED) {
			velocity.sub(velocity);
		}

		// CHECK COLLISION WITH WALL
		{
			if (position.x < OneGame.leftBound) {
				position.x = OneGame.leftBound;
				if (velocity.x < 0) {
					velocity.x = -velocity.x;
				}
			} else if (position.x > OneGame.rightBound) {
				position.x = OneGame.rightBound;
				if (velocity.x > 0) {
					velocity.x = -velocity.x;
				}
			}

			if (position.y < OneGame.upBound) {
				position.y = OneGame.upBound;
				if (velocity.y < 0) {
					velocity.y = -velocity.y;
				}
			} else if (position.y > OneGame.lowBound) {
				position.y = OneGame.lowBound;
				if (velocity.y > 0) {
					velocity.y = -velocity.y;
				}
			}
		}
	}

	public void render(Graphics g) {
		ERM.getImage(img).setRotation(angle);
		g.drawImage(ERM.getImage(img), position.x
				- ERM.getImage(img).getWidth() / 2,
				position.y - ERM.getImage(img).getHeight() / 2);

		Image redCircle = ERM.getImage("red_circle.png");
		redCircle = redCircle.getScaledCopy((int) sightRadius*2, (int) sightRadius*2);
		redCircle.setAlpha(0.2f);
		g.drawImage(redCircle, position.x - sightRadius, position.y - sightRadius);
		
//
//		g.setColor(Color.blue);
//		g.drawOval(position.x - radius, position.y - radius, radius * 2,
//				radius * 2);
	}
}
