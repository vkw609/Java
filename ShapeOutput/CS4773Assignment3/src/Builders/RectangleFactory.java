package Builders;
public class RectangleFactory implements BuilderInterface {

	private Shape rectangle;
	private int height, width;

	public RectangleFactory(int width, int height)
	{
		this.rectangle = new Shape();
		this.width = width;
		this.height = height;
	}
	
	@Override
	public Shape getShape() {
		return this.rectangle;
	}
	
	@Override
	public void setCircleRadius() {
	}

	public void getRectangleWidth(int width) {
		this.width = width;
	}
	
	@Override
	public void setRectangleWidth() {
		rectangle.setWidth(this.width);
	}

	public void getRectangleHeight(int height) {
		this.height = height;
	}
	
	@Override
	public void setRectangleHeight() {
		rectangle.setHeight(this.height);
	}

	@Override
	public void setShapeColor() {
		rectangle.setColor("Red");
	}

	@Override
	public void xLocation() {
		rectangle.setXcoordinate(0);
	}

	@Override
	public void yLocation() {
		rectangle.setYcoordinate(0);
	}
}
