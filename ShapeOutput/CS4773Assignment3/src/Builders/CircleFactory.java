package Builders;



public class CircleFactory implements BuilderInterface {
	private Shape circle;
	private int radius;

	public CircleFactory(int radius)
	{
		this.radius = radius;
		this.circle = new Shape();
	}

	@Override
	public Shape getShape() {
		return this.circle;
	}

	@Override
	public void setCircleRadius() {
		circle.setRadius(this.radius);

	}

	@Override
	public void setRectangleWidth() {
	}

	@Override
	public void setRectangleHeight() {
	}

	@Override
	public void setShapeColor() {
		circle.setColor("Blue");
	}
	

	@Override
	public void xLocation() {
		circle.setXcoordinate(0);
	}

	@Override
	public void yLocation() {
		circle.setYcoordinate(0);
	}
}