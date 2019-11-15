package Commands;


import Builders.*;

public interface CommandInterface {
	public Shape getShape();
	public void create();
	public void select();
	public void move(int x, int y);
	public void color(String shapeColor);
	public void undo();
	public void draw();
	public void delete();
}