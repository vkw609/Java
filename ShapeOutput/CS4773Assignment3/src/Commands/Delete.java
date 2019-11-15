package Commands;

public class Delete implements Command{

	CommandInterface shape;
	public Delete(CommandInterface shape) {
		this.shape = shape;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shape.delete();
	}

	@Override
	public void undo() {
		Boolean undo = false;
		shape.getShape().setNoShape(undo);
	}
}