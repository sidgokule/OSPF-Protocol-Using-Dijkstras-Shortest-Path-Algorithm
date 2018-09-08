import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author Siddhant
 *
 */
public class Vertex {

	String name = "";
	Boolean vertexStatus = false;
	Map<Vertex, Double> neighbors = new HashMap<>();
	
	Boolean edgeStatusFlag = false;
	Double sourceDistance;
	Vertex previousVertex;
	Boolean discovered=false;
	Boolean reached=false;
	Map<Vertex,Boolean>edgesMap = new HashMap<>();
	
	//Constructor
	public Vertex(String name, Map<Vertex, Double> neighbors, Boolean status) {
		super();
		this.name = name;
		this.neighbors = neighbors;
		this.vertexStatus = status;
	}
	
	public Vertex(String vertexName) {
		// TODO Auto-generated constructor stub
		name = vertexName;
		neighbors = new HashMap<Vertex,Double>();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<Vertex,Double> getNeighbors() {
		return neighbors;
	}
	public void setNeighbors(Map<Vertex,Double> neighbors) {
		this.neighbors = neighbors;
	}
	public Boolean getStatus() {
		return vertexStatus;
	}
	public void setStatus(Boolean status) {
		this.vertexStatus = status;
	}

	public Boolean getEdgeStatusFlag() {
		return edgeStatusFlag;
	}

	public void setEdgeStatusFlag(Boolean edgeStatusFlag) {
		this.edgeStatusFlag = edgeStatusFlag;
	}
	
	public double distanceFrom(Vertex v)
	{
		double wt= neighbors.get(v);
				return wt;
	}

	public Map<Vertex, Boolean> getEdgesMap() {
		return edgesMap;
	}

	public void setEdgesMap(Map<Vertex, Boolean> edgesMap) {
		this.edgesMap = edgesMap;
	}
	
}
