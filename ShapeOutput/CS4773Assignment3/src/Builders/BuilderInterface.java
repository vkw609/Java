package Builders;

public interface BuilderInterface {
	public Shape getShape();
	public abstract void setCircleRadius();
	public abstract void setRectangleWidth();
	public abstract void setRectangleHeight();
	public abstract void setShapeColor();
	public abstract void xLocation();
	public abstract void yLocation();
}