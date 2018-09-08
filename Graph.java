import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 
 * @author Siddhant
 *
 */
public class Graph {
	
	// A global map to keep track of all variables of graph
	public Map<String,Vertex> vertexMap = new HashMap<String,Vertex>( );

	/**
	 *  Method to add a new edge to the graph
	 * @param src
	 * @param dest
	 * @param wt
	 */
	public void addEdge(String src, String dest, Double wt) {
		Vertex v = getVertex(src);
        Vertex w = getVertex(dest);
        
        //Update the neighbors list
        v.neighbors.put(w, wt);
        
        //Update the vertex status
        v.vertexStatus = true;
        w.vertexStatus=true;
        
        //Update the edge flag 
        v.edgeStatusFlag = true;
        w.edgeStatusFlag = true;
        
        //Put the make the value of edge true in the map
        v.edgesMap.put(w, true);
	}
	
	/**
	 *  Method to remove an edge from the graph
	 * @param src
	 * @param dest
	 */
	public void removeEdge(String src, String dest)
	{
		Vertex v = getVertex(src);
        Vertex w = getVertex(dest);
        
        //Remove the neighbor from vertex neighbor list
        v.neighbors.remove(w);
        
        // Update the edge status to false in edge map
        v.edgesMap.put(w, false);
        
        
	}
	
	/**
	 * Method to make an edge DOWN
	 * @param src
	 * @param dest
	 */
	public void edgeDown(String src, String dest) {
		Vertex v = getVertex(src);
		Vertex w = getVertex(dest);
		
		v.edgeStatusFlag=false;
		w.edgeStatusFlag=false;
		// Update the edge status to false in edge map
		v.edgesMap.put(w, false);
	}
	
	/**
	 * Method to make an edge UP
	 * @param src
	 * @param dest
	 */
	public void edgeUp(String src, String dest) {
		Vertex v = getVertex(src);
		Vertex w = getVertex(dest);
		
		// Update the edge status to true in edge map
		v.edgesMap.put(w, true);
	}
	
	
	/**
	 * Method to  add a vertex to map
	 * @param vertexName
	 */
	public void addVertex(String vertexName) 
	{
		//Check if vertex is already present if not create a new and add in the vertex map
		Vertex v = vertexMap.get( vertexName );
		if(v==null)
		{
			v = new Vertex(vertexName);
            vertexMap.put(vertexName, v);
            
		}
	}
	
	/**
	 * Method to make a vertex of graph DOWN
	 * @param vertex
	 */
	public void vertexDown(String vertex)
	{
		// To make a vertex down set its vertexStatus flag to FALSE in vertexMap
		Vertex v = vertexMap.get(vertex);
		v.vertexStatus = false;
	}
	
	
	/**
	 * Method to make a vertex of graph UP
	 * @param vertex
	 */
	public void vertexUp(String vertex)
	{
		// To make a vertex up set its vertexStatus flag to TRUE in vertexMap
		Vertex v = vertexMap.get(vertex);
		v.vertexStatus = true;
	}
	
	
	/**
	 * Method to check if vertex is present in graph and return it
	 * @param vertexName
	 * @return
	 */
	public Vertex getVertex(String vertexName)
    {
		//Fetch a vertex from the vertex map.  If it is not present, create and add a vertex and then return it
        Vertex v = vertexMap.get( vertexName );
        if( v == null )
        {
            v = new Vertex(vertexName);
            vertexMap.put(vertexName, v);
        }
        return v;
    }
	
/**
 * Method to print all the reachable vertices from all nodes		
 * @return
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public String printReachableVertices(){
		LinkedList<Vertex> Q = new LinkedList<Vertex>();
		LinkedList<Vertex> graphVertices = new LinkedList<Vertex>();
		LinkedList<Vertex> adjacentVertices = new LinkedList<Vertex>();
		String reachableVerticesString = new String();
		Vertex removeVertex;
		
		//Copy all vertices from vertexMap in a linked list
		for(String name : vertexMap.keySet()){
			graphVertices.add(vertexMap.get(name));
		}
		
		//Sort the vertices alphabetically for printing
		graphVertices = (LinkedList) alphabeticalSort(graphVertices).clone();
		
		//Traverse each vertex one by one
		for(int i = 0; i < graphVertices.size(); i++){
			// Add the vertex to a queue 
			Q.add(graphVertices.get(i));
			
			//Get the first vertex in the queue and add it to print it if it is UP
			if(Q.get(0).vertexStatus == true)
			{
				reachableVerticesString  += Q.get(0).name + "\n";
			}
			
			//Till the queue is not repeat, add all its neighbors in queue and pop one at a time and print its neighbors if one by one
			while(!Q.isEmpty()){
				if(Q.get(0).vertexStatus == true){
					Q.get(0).reached = true;
					removeVertex = Q.removeFirst();

					for(Vertex neighborVertex : removeVertex.neighbors.keySet()){
						//if(!(removeVertex.edgeStatusFlag == false && neighborVertex.edgeStatusFlag == false) && neighborVertex.vertexStatus == true ){
						if(!(removeVertex.edgesMap.get(neighborVertex) == false) && neighborVertex.vertexStatus == true ){
							for(Vertex v1 : graphVertices){
								if(v1 == neighborVertex && neighborVertex.reached == false){
									adjacentVertices.add(neighborVertex);
									v1.reached = true;
									Q.add(v1);
								}
							}
						}
					}

				}
				else if(Q.get(0).vertexStatus == false){
					Q.removeFirst();
				}

			}
			//Alphabetically sort the neighbors for printing
			adjacentVertices = alphabeticalSort(adjacentVertices);
			//Add the neighbor for printing
			for(Vertex v1 : adjacentVertices){
				reachableVerticesString  += "\t"+ v1.name + "\n";
//				System.out.println("\t"+ v1.name + "\n");
			}
			adjacentVertices.clear();
			for(Vertex v : graphVertices){
				v.reached = false;
			}
		}
		return reachableVerticesString ;
	}	
		
	
	/**
	 * Method to calculate shortest path from source to destination using Dijkstra's algorithm
	 * @param src
	 * @param dest
	 * @return
	 */
	public String findShortestPath(Vertex src, Vertex dest)
	{
		LinkedList<Vertex>shortestPath = new LinkedList<>();
		LinkedList<Vertex>discoveredVertices = new LinkedList<>();
		Vertex vertexArray[] ;
		int n = 0;
		int p = 0;
		
		//Update source and add to shortest path
		src.sourceDistance=0.0;
		src.previousVertex=null;
		shortestPath.add(src);
		
		//Make all distances infinity
		for(String vertex : vertexMap.keySet())
		{
			//Initialize all the vertices if the graph by setting distance from source to infinity and previous vertices to null and discovered status as false
			if(vertexMap.get(vertex)!=src)
			{
				vertexMap.get(vertex).sourceDistance = Double.MAX_VALUE;  //Make distances of all vertices except source to infinity
				vertexMap.get(vertex).previousVertex = null;
				vertexMap.get(vertex).discovered=false;
			}
		}
		
		//Till the shortest path list is not empty pop its first element and process it
		while(!(shortestPath.isEmpty()))
		{
			//Remove first vertex from Q and mark it visited
			Vertex currentVertex = shortestPath.remove();
			currentVertex.discovered = true;
			
			//If it is UP only then do further processing
			if(currentVertex.vertexStatus == true) {
				
				//For each neighbor of current vertex, if their is an edge and the neighbor is UP then update the weight of neighbor
				for(Vertex neighbor: currentVertex.neighbors.keySet())
				{
					if(currentVertex.edgesMap.get(neighbor)==true && neighbor.vertexStatus==true)
					{
						// Case when the  neighbor is first discovered
						if(neighbor.sourceDistance == Double.MAX_VALUE && neighbor.discovered == false )
						{
							neighbor.sourceDistance = currentVertex.sourceDistance + currentVertex.distanceFrom(neighbor);
							neighbor.previousVertex = currentVertex;
							discoveredVertices.add(neighbor);
						}
						// Case when the distance of neighbor from source through its previous vertex is more than through the current vertex, update the distance of neighbor
						else if(neighbor.sourceDistance > currentVertex.sourceDistance + currentVertex.distanceFrom(neighbor))
						{
							Double distanceFromCurrentVertexToNeighbor =currentVertex.distanceFrom(neighbor);
							neighbor.sourceDistance = currentVertex.sourceDistance + distanceFromCurrentVertexToNeighbor ;
							neighbor.previousVertex = currentVertex;
							discoveredVertices.add(neighbor);
						}
					}
				}
				
						n = discoveredVertices.size();
						// Create an array to be sent to heap sort
						vertexArray = new Vertex[n];
						p=0;
						// Copy discovered vertices from to array
						for(Vertex v: discoveredVertices)
						{
							vertexArray[p] = v;
							p++;
						}
						
						discoveredVertices.clear();
						
						// Call heap sort on discovered vertices
						vertexArray = hSort(vertexArray,n);
						
						//Copy the heap sorted vertices from array to the original list
						for(int i=0; i<vertexArray.length; i++)
						{
							vertexArray[i].discovered=true;
							discoveredVertices.add(vertexArray[i]);
						}
						
						// Add the first element from shortest path for next iteration of processing
						if(discoveredVertices.size()!=0)
						{
							shortestPath.add(discoveredVertices.removeFirst());
						}
			}
		}
		
		//Print the shortest path
		String shortestPathString= new String();
//		System.out.println(src.name +" "+ dest.name + " " + " "+ dest.sourceDistance);
		if(dest.sourceDistance!=Double.MAX_VALUE)
		{
			shortestPathString+=printShortestPath(dest);
			DecimalFormat format = new DecimalFormat("###.##");
			shortestPathString +=" ";
			shortestPathString += format.format(dest.sourceDistance)+"\n";
		}
		else 
		{
			shortestPathString +="Distance is infinite from "+src+ " to "+ dest;
		}
//		System.out.println(shortestPathString);
		return shortestPathString;
		
	}
	
	/**
	 * Method to perform Heap Sort
	 * @param vertexArray
	 * @param size
	 * @return
	 */
	public Vertex[] hSort(Vertex [] vertexArray, int size)
	{
		//First build the heap
		vertexArray = buildMinBinaryHeap(vertexArray, size);
		Vertex v ;
		//If the heap property is not satisfied correct it by calling meanHapify
		for(int i = size; i >= 2; i--)
		{
			v = vertexArray[0];
			vertexArray[0] = vertexArray[i-1];
			vertexArray[i-1] = v;
			minHeapify(vertexArray,1,i-1);
		}
		return vertexArray;
	}
	
	/**
	 * Method to built the Min binary heap
	 * @param vertexArray
	 * @param size
	 * @return
	 */
	public Vertex[] buildMinBinaryHeap(Vertex[] vertexArray, int size)
	{
		for(int i = size/2; i>=1; i--)
		{
			vertexArray = minHeapify(vertexArray, 1, size);
		}
		return vertexArray;
	}
	
	/**
	 * Method to satisfy the heap properties
	 * @param vertexArray
	 * @param i
	 * @param n
	 * @return
	 */
	public Vertex[] minHeapify(Vertex[] vertexArray, int i, int n)
	{
		int left = 2 * i;
		int right = 2 * i + 1;
		int smallest = 0;
		// Update the heap so that it satisfies the heap properties
		if(left<=n && vertexArray[left-1].sourceDistance>vertexArray[i-1].sourceDistance)
		{
			smallest = left;
		}
		else
		{
			smallest = i;
		}
		
		if(right <= n && vertexArray[right-1].sourceDistance > vertexArray[smallest-1].sourceDistance)
		{
			smallest = right;
		}
		Vertex v ;
		if(smallest != i)
		{
			v = vertexArray[i-1];
			vertexArray[i-1] = vertexArray[smallest-1];
			vertexArray[smallest-1]= v;
			minHeapify(vertexArray, smallest, n);
		}
		
		return vertexArray;
	}
	
	/**
	 * Method to sort the vertices in alphabetical order for printing
	 * @param vertexList
	 * @return
	 */
	public LinkedList<Vertex> alphabeticalSort(LinkedList<Vertex> vertexList)
	{
		String[] tempArray = new String[vertexList.size()];
		LinkedList<Vertex> sortedVertices = new LinkedList<Vertex>();
		String temp = " ";
		
		//Store all graph vertice's names for comparison
		for(int k=0;k<tempArray.length;k++)
		{
			tempArray[k] = vertexList.get(k).name;
		}
		
		//Compare each vertex name with every other and sort them alphabetically
		for(int i=0; i< tempArray.length-1; i++)
		{
			for(int j=i; j < tempArray.length; j++)
			{
				if(tempArray[j].compareTo(tempArray[i])<0)
				{
					temp = tempArray[i];
					tempArray[i] = tempArray[j];
					tempArray[j] = temp;
				}
			}
		}
		
		// Copy the sorted vertices from the array  to list
		for(int i=0; i<tempArray.length; i++)
		{
					sortedVertices.add(getVertex(tempArray[i]));
		}
		
		return sortedVertices;
	}
	
	/**
	 * Method to to print the current graph
	 * @return
	 */
	public String printGraph()
	{
		String a = "";
		LinkedList<Vertex>vertexList = new LinkedList<Vertex>();
		LinkedList<Vertex>adjacentVertices= new LinkedList<Vertex>();
		
			// Copy all vertices of graph to a list
			for(String vertex:vertexMap.keySet())
			{
				vertexList.add(vertexMap.get(vertex));
			}
			//Alphabetically sort the vertices
			vertexList = alphabeticalSort(vertexList);
			
			//Traverse each vertex in the list
			for(Vertex vertex: vertexList)
			{
				//If the vertex is Up then simply print it
				if(vertex.vertexStatus == true)
				{
					a+=vertex.name+"\n";
				}
				//Else append DOWN to it before printing
				else
				{
					a+=vertex.name+" DOWN\n";
				}
					//Traverse each neighbor of the current vertex and add it to a list
					for(Vertex temp : vertex.neighbors.keySet())
					{
								adjacentVertices.add(temp);
					}
					//Sort the neighbors alphabetically
					adjacentVertices = alphabeticalSort(adjacentVertices);
					//Similar to parent vertex, print the children
					for(Vertex temp : adjacentVertices) {
						if(vertex.edgesMap.get(temp))
						{
							a+="\t"+temp.name+ " ";
							a+=vertex.neighbors.get(temp)+"\n";
						}
						else
						{
							a+="\t"+temp.name+ " ";
							a+=vertex.neighbors.get(temp)+" DOWN"+"\n";
						}
						
					}
					adjacentVertices.clear();
			}
			return a;
	}
	
	
	String temp11 = new String();
	/**
	 * Method to print the shortest path to a vertex
	 * @param v
	 * @return
	 */
	public String printShortestPath(Vertex v)
	{
		// Starting backwards, make a recursive call to the previous vertex of current and print it. If it has no previous vertex, print it.
		temp11=new String();
		if(v.previousVertex!=null)
		{
			printShortestPath(v.previousVertex);
			temp11 += " ";
		}
		temp11+=v.name;
		return temp11;
	}
	
	/**
	 * Method to write the ouput to a file
	 * @param outputTOFile
	 * @param outputFileName
	 * @throws IOException
	 */
	public void ouputWrite(String outputTOFile, File outputFileName) throws IOException
	{
		//Initialize the writers
		OutputStream os = new FileOutputStream(outputFileName);
		Writer wr = new OutputStreamWriter(os);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));
		try {
			//Write output to the file
			wr.write(outputTOFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
			wr.close();
			bufferedWriter.close();
			os.close();
		}
	}
	
}
