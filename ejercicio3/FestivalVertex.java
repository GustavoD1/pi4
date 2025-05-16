package ejercicio3;


import java.util.List;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;


public interface FestivalVertex extends
	VirtualVertex<FestivalVertex, FestivalEdge, Integer> {

	Integer indice();
	Integer tipo();
	Integer area();
	List<Integer> entradasPorAsignarTipo();
	List<Integer> aforoDisponibleArea();

	public static FestivalVertex start() {
		return  FestivalVertexI.of(0,DatosFestival.getCuotasMinimas(),DatosFestival.getAforoMaximoAreas());
	}
}
	
	

