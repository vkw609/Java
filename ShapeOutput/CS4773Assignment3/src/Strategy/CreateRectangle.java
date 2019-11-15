package Strategy;

import Builders.*;
import Commands.*;


public class CreateRectangle implements CreateInterface, Command{

	int width, height;
	BuilderInterface buildRectangle;
	ShapeBuilder rectangle;
	CommandInterface shape;
	
	@Override
	public Shape createRectangle(int width, int height) {
		BuilderInterface builder = new RectangleFactory(width, height);
		ShapeBuilder rectangle = new ShapeBuilder(builder);
		rectangle.buildShape();
		return rectangle.getShape();
	}

	@Override
	public Shape createCircle(int radius) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void execute() {
		shape.create();
	}
	
	public void undo() {
	}
}