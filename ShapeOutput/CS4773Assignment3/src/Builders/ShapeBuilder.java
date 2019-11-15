package Builders;


public class ShapeBuilder {

	private BuilderInterface shape;

	public ShapeBuilder(BuilderInterface shape) {
		this.shape = shape;
	}

	public void buildShape() {
		this.shape.setCircleRadius();
		this.shape.setRectangleWidth();
		this.shape.setRectangleHeight();
		this.shape.setShapeColor();
		this.shape.xLocation();
		this.shape.yLocation();
	}
	
	public Shape getShape() {
		return this.shape.getShape();
	}
	
}