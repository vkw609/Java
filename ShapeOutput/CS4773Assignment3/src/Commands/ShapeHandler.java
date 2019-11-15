package Commands;

import Builders.*;
import Strategy.*;

public abstract class ShapeHandler implements CommandInterface{
	Shape shape;
	public CreateInterface createShape;
	public DrawInterface drawShape;

	public Shape getShape()
	{
		return this.shape.getShape();
	}
	
	public void drawShape() {
		drawShape.draw(shape);
	}

	public void select()
	{
		
	}
	
	@Override
	public void color(String color) {
		this.shape.setColor(color);
	}
	
	@Override
	public void move(int x, int y) {
		this.shape.setXcoordinate(x);
		this.shape.setYcoordinate(y);
	}

	@Override
	public void delete() {
		Boolean delete = true;
		this.shape.setNoShape(delete);

	}

	@Override
	public void undo() {

	}
}