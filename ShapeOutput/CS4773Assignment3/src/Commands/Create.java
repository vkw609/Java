package Commands;

public class Create implements Command{
	
	CommandInterface shape;

	public Create(CommandInterface shape)
	{
		this.shape = shape;
	}

	@Override
	public void execute() {
		shape.create();
	}

	@Override
	public void undo() {
		Boolean undo = false;
		shape.getShape().setNoShape(undo);
	}
}