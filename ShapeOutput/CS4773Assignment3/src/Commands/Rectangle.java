package Commands;

import Strategy.*;

public class Rectangle extends ShapeHandler{

	private int width;
	private int height;

	public Rectangle (int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void create() {
		createShape = new CreateRectangle();
		this.shape = createShape.createRectangle(this.width,this.height);
	}

	@Override
	public void draw() {
		drawShape = new DrawsceneRectangle();
		drawShape();
	}
}