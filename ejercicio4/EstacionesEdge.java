package ejercicio4;


import us.lsi.graphs.virtual.SimpleEdgeAction;

public record EstacionesEdge(EstacionesVertex source,EstacionesVertex target,Integer action, Double weight)
      implements SimpleEdgeAction<EstacionesVertex, Integer>{
	
	 public static EstacionesEdge of(EstacionesVertex c1, EstacionesVertex c2, Integer action) {
		 Double peso = 0.0;
		 Integer vAnt = c1.camino().get(c1.indice()-1);
		 peso = DatosEstaciones.grafoTiempo.getEdgeWeight(vAnt, action);
		 if(c1.indice() == DatosEstaciones.n-1) {
			 peso += DatosEstaciones.grafoTiempo.getEdgeWeight(action, 0);
		 }
		 
		EstacionesEdge a = new EstacionesEdge(c1, c2, action,peso);
		return a;
	}
	


}
