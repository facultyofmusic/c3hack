package lanex.one;

class Stairs extends StaticEntity {


	public Stairs(float x, float y, float radius, String img) {
		super(x, y, radius, img);
	}

	@Override
	public void onHit()
	{
		OneGame.makeFloor();
	}
}
