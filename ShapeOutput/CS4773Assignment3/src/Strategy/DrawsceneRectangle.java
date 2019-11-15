package Strategy;

import Builders.Shape;

public class DrawsceneRectangle implements DrawInterface {

	public void draw(Shape shape) {
			System.out.printf("Rectangle, Color: %s, Origin: (%d, %d), Width: %d, Height: %d\n",shape.getColor(), shape.getXcoordinate(), shape.getYcoordinate(), shape.getWidth(), shape.getHeight());
	}
}
