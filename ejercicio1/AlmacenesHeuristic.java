package ejercicio1;

import java.util.function.Predicate;


public class AlmacenesHeuristic {

	public static Double heuristic(AlmacenesVertex v1, Predicate<AlmacenesVertex> goal, AlmacenesVertex v2) {
		Double h = 0.;
		if (v1.indice() < DatosAlmacenes.getNumAlmacenes()) {  
			h = (DatosAlmacenes.getNumProductos() - v1.indice())*1.0;
		} 
		return h;
	}


}


