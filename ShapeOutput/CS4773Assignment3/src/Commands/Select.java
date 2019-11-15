package Commands;

public class Select  implements Command{

	CommandInterface shape;
	public Select(CommandInterface shape)
	{
		this.shape = shape;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		shape.select();
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
	}

}