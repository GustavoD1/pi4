package ejercicio1;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record AlmacenesEdge(AlmacenesVertex source,AlmacenesVertex target,Integer action, Double weight)
      implements SimpleEdgeAction<AlmacenesVertex, Integer>{

	
	public static AlmacenesEdge of(AlmacenesVertex c1, AlmacenesVertex c2, Integer action) {
		
		Integer espacioRequerido = 0;
		if (action>=0 && c2.indice()<DatosAlmacenes.getNumProductos()) {
		   //espacioRequerido = DatosAlmacenes.getProducto(c2.indice()).metroscubicosrequeridos();
		   espacioRequerido = 1;
		}
		AlmacenesEdge a = new AlmacenesEdge(c1, c2, action, espacioRequerido * 1.0);
		return a;
	}
}
