package Commands;

public class Move implements Command{
	
	CommandInterface shape;
	private int prevX, prevY;
	private int x, y;
	
	public Move(CommandInterface shape, int x, int y) {
		this.shape = shape;
		this.x = x;
		this.y = y;
		this.prevX = this.shape.getShape().getXcoordinate();
		this.prevY = this.shape.getShape().getYcoordinate();
	}

	@Override
	public void execute() {
		shape.move(this.x, this.y);
	}

	@Override
	public void undo() {
		shape.getShape().setXcoordinate(prevX);
		shape.getShape().setYcoordinate(prevY);
	}

}