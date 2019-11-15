package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Commands.*;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws NumberFormatException, IOException{
		
		ArrayList<CommandInterface> drawingList = new ArrayList<CommandInterface>();
		ArrayList<Command> commandList = new ArrayList<Command>();

		File file = new File("./src/Main/commands1.txt");

		String line;
		String [] lineToken;
		
		BufferedReader readBuffer = new BufferedReader(new FileReader(file));
		
		CommandInterface shape = null;
		Invoker invoker = null;

		while((line = readBuffer.readLine()) != null)
		{
			lineToken = line.split(" ");
			
			if(lineToken[0].equals("CREATE")) {
				if(lineToken[1].equals("CIRCLE")) {
					int radius = Integer.parseInt(lineToken[2]);
					CommandInterface circle = new Circle(radius);
					Create createCircle = new Create(circle);
					invoker = new Invoker(createCircle);
					invoker.execute();
					commandList.add(createCircle);
					drawingList.add(circle);
				}
				if(lineToken[1].equals("RECTANGLE")) {
					int width = Integer.parseInt(lineToken[2]);
					int height = Integer.parseInt(lineToken[3]);
					Rectangle rectangle = new Rectangle(width, height);
					Create createRect = new Create(rectangle);
					invoker = new Invoker(createRect);
					invoker.execute();
					commandList.add(createRect);
					drawingList.add(rectangle);
				}
			}
					
			if(lineToken[0].equals("SELECT")) {
				int selectShapeValue = Integer.parseInt(lineToken[1]);
				
				if(selectShapeValue > drawingList.size())
				{
					System.out.println("ERROR: invalid shape for SELECT");
				}
				
				if(selectShapeValue <= drawingList.size()) {
					shape = drawingList.get(Integer.parseInt(lineToken[1])-1);
					Select select = new Select(shape);
					invoker = new Invoker(select);
					invoker.execute();
					commandList.add(select);
				}
			}
					
			if(lineToken[0].equals("MOVE")) {
				Move move = new Move(shape,Integer.parseInt(lineToken[1]), Integer.parseInt(lineToken[2]));
				invoker = new Invoker(move);
				invoker.execute();
				commandList.add(move);
			}	
			
			
			if(lineToken[0].equals("COLOR")) {
				String newColor  = lineToken[1];
				ColorShape color = new ColorShape(shape, newColor);
				invoker = new Invoker(color);
				invoker.execute();
				commandList.add(color);
			}
			
			if(lineToken[0].equals("UNDO")) {
				Command undo = commandList.remove(commandList.size()-1);
				invoker = new Invoker(undo);
				invoker.undo();
			}
					
			if(lineToken[0].equals("DRAW")) {
				Draw draw = new Draw(shape);
				invoker = new Invoker(draw);
				invoker.execute();
				commandList.add(draw);
			}
	
			if(lineToken[0].equals("DELETE")) {
				Delete delete = new Delete(shape);
				invoker = new Invoker(delete);
				invoker.execute();
				commandList.add(delete);
			}
					
			if(lineToken[0].equals("DRAWSCENE")) {
				for(CommandInterface drawShapes : drawingList)
					drawShapes.draw();
			}
		}
	 }
}
