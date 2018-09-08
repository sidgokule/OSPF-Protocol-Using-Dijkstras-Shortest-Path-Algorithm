import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Siddhant
 *
 */
public class CreateNetwork {
	
	/**
	 * Method to create a graph from the network file passed from the command line as argument
	 * @param networkFile
	 * @return
	 */
	public Graph createNetwork(String networkFile) {
		String src=null;
		String dest=null;
		double wt=0.0;
		Graph graph = new Graph();
		try {
			//Initializations for file reading
			File file = new File(networkFile);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			//While the network file contains line read a line one by one
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
				//Take the first word as source
				src=line.split(" ")[0];
				//Take the second word as destination
				dest=line.split(" ")[1];
				//Take the third word as edge weight
				wt=Double.parseDouble(line.split(" ")[2]);
				stringBuffer.append("\n");
				//Add source as vertex to the graph
				graph.addVertex(src);
				//Add destination as vertex to the graph
				graph.addVertex(dest);
				//Add an edge from source to destination
				graph.addEdge(src, dest, wt);
				//Add an edge from destination to source
				graph.addEdge(dest, src, wt);
				
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	return graph;
	}

}
