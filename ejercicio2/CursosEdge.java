package ejercicio2;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CursosEdge(CursosVertex source,CursosVertex target,Integer action, Double weight)
      implements SimpleEdgeAction<CursosVertex, Integer>{

	
	public static CursosEdge of(CursosVertex c1, CursosVertex c2, Integer action) {
		Double relevancia = 0.0;
		if (c1.indice() < DatosCursos.getNumCursos()) {
  		   relevancia = (double) DatosCursos.getCurso(c1.indice()).relevancia();
		}
		CursosEdge a = new CursosEdge(c1, c2, action, relevancia * action);
		return a;
	}
}
