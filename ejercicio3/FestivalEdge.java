package ejercicio3;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record FestivalEdge(FestivalVertex source,FestivalVertex target,Integer action, Double weight)
      implements SimpleEdgeAction<FestivalVertex, Integer>{

	
	public static FestivalEdge of(FestivalVertex source, FestivalVertex target, Integer action) {	
		Double costeEntrada = (double)DatosFestival.getCosteAsignacion(source.tipo(),source.area());
		return new FestivalEdge(source, target, action, costeEntrada * action);
	}
}
