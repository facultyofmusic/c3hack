package lanex.one;

import java.awt.image.BufferedImage;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Enemy extends Character{
	
	public Enemy(MobTypes mob) {
		super((float)Math.random()*320 + 200, (float)Math.random()*320 + 200);
		if (mob == MobTypes.LARGE_MOB)
		{
			health = 150;
			img = "large_mob.png";
			sight = 2f;
			super.MAX_SPEED = 3.5f;
			radius = 30;
			mass = 3;
		}
		if (mob == MobTypes.MEDIUM_MOB)
		{
			img = "medium_mob.png";
			sight = 2.5f;
			super.MAX_SPEED = 4.5f;
			mass =  2;
		}
		if (mob == MobTypes.SMALL_MOB)
		{
			health = 50;
			img = "small_mob.png";
			sight = 3f;
			super.MAX_SPEED = 5.5f;
			radius = 20;
			mass = 1;
		}
		damage = mass/30;
	}
	
	public void update (Player player)
	{
		super.update();
		//System.out.println("I UPDATE!");
		if (player.position.distance(position) < (player.agroRadius + sightRadius))
		{
			//System.out.println("I WANT EAT PLAYER!");
			Vector2f accel = player.position.copy().sub(position).normalise();
			super.accelerate (accel);
			collisionCheck(player, this);
		}
	}

}
