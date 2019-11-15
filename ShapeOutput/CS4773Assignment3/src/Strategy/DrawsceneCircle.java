package Strategy;

import Builders.Shape;

public class DrawsceneCircle implements DrawInterface {

	@Override
	public void draw(Shape shape) {
		System.out.printf("Circle, Color: %s, Origin: (%d, %d), Radius: %d\n", shape.getColor(), shape.getXcoordinate(), shape.getYcoordinate(), shape.getRadius());
	}
}