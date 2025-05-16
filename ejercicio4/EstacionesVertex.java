package ejercicio4;


import java.util.ArrayList;
import java.util.List;


import us.lsi.graphs.virtual.VirtualVertex;


public interface EstacionesVertex extends
	VirtualVertex<EstacionesVertex, EstacionesEdge, Integer> {

	Integer indice();
	List<Integer> camino();
	Double costeAcumulado();

	public static EstacionesVertex start() {
	    List <Integer> camino = new ArrayList<Integer>();
	    camino.add(0); // Nodo donde se inicia el camnino
	 	return  EstacionesVertexI.of(1,camino,0.0);
		
	}
}
	
	

