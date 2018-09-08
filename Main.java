import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * 
 * @author Siddhant
 *
 */
public class Main {
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		String outputTOFile = new String();
		
		/*
		 * Check if sufficient parameters are passed to the program. 
		 */
		if(args.length<3)
		{
			System.out.println("Insufficient arguments\nEnter in format: java Main <network file> <queries file> <output file>");
			System.exit(0);
		}
		else {
			
			//Read input, network and output file names
			String networkFile = new File(args[0]).toString();
			String queriesFile = new File(args[1]).toString();
			File  outputFileName = new File(args[2]);
			Graph graph  = new Graph();
			File file = null;
			Scanner queryFileScanner = null;
			try {
				// Read queries file 
				file = new File(queriesFile);
				queryFileScanner = new Scanner(file);
				String line;
				
				//Setup network
				CreateNetwork network = new CreateNetwork();
				graph = network.createNetwork(networkFile);
				
				// Read queries file
				while(queryFileScanner.hasNextLine()) {
					line = queryFileScanner.nextLine();
					StringTokenizer st = new StringTokenizer(line);
					String token = st.nextToken();
					//Read the first word of queries file, interpret it as a command an take appropriate action using switch case
					switch (token) {
					
					case "graph":
						networkFile = st.nextToken();
						network = new CreateNetwork();
						graph = new Graph();
						graph = network.createNetwork(networkFile);
						break;

					case "print":
						outputTOFile +=graph.printGraph();
						break;
						
					case "deleteedge":
						String deleteEdgeVertex1 = st.nextToken();
						String deleteEdgeVertex2 = st.nextToken();
						graph.removeEdge(deleteEdgeVertex1, deleteEdgeVertex2);
						break;

					case "path":
						String src1 = st.nextToken();
						String dest1 = st.nextToken();	
						Vertex src = graph.getVertex(src1);
						Vertex dest = graph.getVertex(dest1);
						outputTOFile += graph.findShortestPath(src, dest);
						break;

					case "reachable":
						outputTOFile += graph.printReachableVertices();
						break;

					case "vertexup":
						String vertexup = st.nextToken();
						graph.vertexUp(vertexup);
						
						break;

					case "vertexdown":
						String vertexdown = st.nextToken();
						graph.vertexDown(vertexdown);
						break;

					case "addedge":
						String srcEdge = st.nextToken();
						String destEdge = st.nextToken();
						Double wt = Double.parseDouble(st.nextToken());
						graph.addEdge(srcEdge, destEdge, wt);
						break;

					case "edgedown":
						String srcEdgeDown = st.nextToken();
						String destEdgeDown = st.nextToken();
						graph.edgeDown(srcEdgeDown, destEdgeDown);
						break;
					
					case "edgeup":
						String srcEdgeUp = st.nextToken();
						String destEdgeUp = st.nextToken();
						graph.edgeUp(srcEdgeUp, destEdgeUp);
						break;

					case "quit":
						graph.ouputWrite(outputTOFile, outputFileName);
						System.exit(0);
						break;

					
					default:
						System.out.println("Incorrect command encountered");
						break;
					}
					
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				queryFileScanner.close();
			}
			
			//Write the output to file
			graph.ouputWrite(outputTOFile, outputFileName);
			//System.out.println("Finished");
			System.exit(0);
		}
	}

}
