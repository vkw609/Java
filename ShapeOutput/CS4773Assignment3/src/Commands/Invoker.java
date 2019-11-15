package Commands;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
	
	
	Command command;
	private List<Command> commandHistory;

	public Invoker (Command newCommand){
		command = newCommand;
		commandHistory = new ArrayList<Command>();
		commandHistory.add(newCommand);
	}

	public void execute()
	{
		command.execute();
	}

	public void undo()
	{
		command.undo();
	}
	
	public void printCommandHistory() {
		for(Command command : commandHistory) {
			System.out.println(command);
		}
	}
}