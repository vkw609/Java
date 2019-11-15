package Builders;

public class Shape 	{
	
	private String color;
	private int x, y;
	private int radius, height, width;
	private Boolean noShape;
	
	public Shape getShape() {
		return this;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getXcoordinate() {
		return x;
	}

	public void setXcoordinate(int x) {
		this.x = x;
	}

	public int getYcoordinate() {
		return y;
	}

	public void setYcoordinate(int y) {
		this.y = y;
	}

	public void setNoShape(Boolean value) {
		this.noShape = value;
	}
}