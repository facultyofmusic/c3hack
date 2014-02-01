package lanex.one;

import org.newdawn.slick.geom.Vector2f;

public class Util {
	public static Vector2f setLinearDelta(float direction, float speed) {
		direction %= 360;
		float dd = direction % 90, dx, dy;

		if (direction < 90) {
			dx = (float) Math.cos(Math.toRadians(dd)) * speed;
			dy = -(float) Math.sin(Math.toRadians(dd)) * speed;
		} else if (direction < 180) {
			dx = -(float) Math.sin(Math.toRadians(dd)) * speed;
			dy = -(float) Math.cos(Math.toRadians(dd)) * speed;
		} else if (direction < 270) {
			dx = -(float) Math.cos(Math.toRadians(dd)) * speed;
			dy = (float) Math.sin(Math.toRadians(dd)) * speed;
		} else {
			dx = (float) Math.sin(Math.toRadians(dd)) * speed;
			dy = (float) Math.cos(Math.toRadians(dd)) * speed;
		}
		
		return new Vector2f(dx, dy);
	}
}
