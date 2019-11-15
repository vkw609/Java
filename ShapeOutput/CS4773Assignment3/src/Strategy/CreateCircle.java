package Strategy;

import Builders.*;
import Commands.*;

public class CreateCircle implements CreateInterface, Command{
	
	int radius;
	BuilderInterface buildCircle;
	ShapeBuilder circle;
	CommandInterface shape;
	
	public void Create(CommandInterface shape)
	{
		this.shape = shape;
	}
	
	@Override
	public Shape createCircle(int radius) {
		buildCircle = new CircleFactory(radius);
		circle = new ShapeBuilder(buildCircle);
		circle.buildShape();
		return circle.getShape();
	}

	@Override
	public void execute() {
		shape.create();
	}
	
	public void undo() {
	}

	@Override
	public Shape createRectangle(int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}
}