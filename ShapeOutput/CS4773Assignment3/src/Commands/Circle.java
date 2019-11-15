package Commands;

import Strategy.*;

public class Circle extends ShapeHandler{
	private int radius;

	public Circle(int radius) {
		this.radius = radius;
	}

//	@Override
	public void create() {
		createShape = new CreateCircle();
		this.shape = createShape.createCircle(this.radius);
	}

//	@Override
	public void draw() {
		drawShape = new DrawsceneCircle();
		drawShape();
	}

}