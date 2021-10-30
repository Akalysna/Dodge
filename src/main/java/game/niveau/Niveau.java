package game.niveau;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import node.machine.MachineFactory;
import node.machine.MachineFactory.TypeMachine;

public class Niveau {
	
	private String name; 
	private String cubyPath; 
	private String musicPath; 
	private String levelPath; 
	
	private List<Stage> stages; 
	
	public Niveau(String levelPath) {
		this("", "", "", levelPath); 
	}
	
	public Niveau(String name, String cubyPath, String musicPath, String levelPath) {

		this.name = name;
		this.cubyPath = cubyPath;
		this.musicPath = musicPath;
		this.levelPath = levelPath;
		
		this.stages = new ArrayList<>(); 
		
		readLevel(levelPath);
	}

	private void readLevel(String levelPath) {
		
		boolean firstLine = true; 
		Stage stage = new Stage(); 
		String l;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(levelPath));
			
			while ((l = in.readLine()) != null) {
				
				String[] line = l.split(":");
				
				if(line[0].equals("Stage")) {
					if(firstLine) {
						stage = new Stage();
						firstLine = false;
					}
					else 
						this.stages.add(stage); 
				}
				
				for (int i = 0; i < line.length/2; i++) {
					
					MachineFactory mf = new MachineFactory(); 
					stage.addNode(mf.get(TypeMachine.SIMPLE, Integer.valueOf(line[2+i]), Integer.valueOf(line[2+i+1]))); 
				}
				
			}
			
			this.stages.add(stage); 
			
			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getLevelPath() { return levelPath; }

	public List<Stage> getStages() { return stages; }
	
	

}
