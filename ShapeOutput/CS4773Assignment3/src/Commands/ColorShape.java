package Commands;

public class ColorShape implements Command{

	CommandInterface shape;
	String oldColor, shapeColor;
	
	public ColorShape(CommandInterface shape, String newColor) {
		this.shape = shape;
		this.oldColor = this.shape.getShape().getColor();
		this.shapeColor = newColor;
	}
	
	@Override
	public void execute() {
		shape.color(this.shapeColor);
	}
	
	@Override
	public void undo() {
		this.shape.getShape().setColor(oldColor);
	}


}
