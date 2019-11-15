package Commands;


public class Draw implements Command{

	CommandInterface shape;
	public Draw(CommandInterface shape) {
		this.shape = shape;
	}

	@Override
	public void execute() {
		shape.draw();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
	}
}