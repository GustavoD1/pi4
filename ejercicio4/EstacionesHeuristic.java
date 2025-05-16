package ejercicio4;

import java.util.function.Predicate;


public class EstacionesHeuristic {

	public static Double heuristic(EstacionesVertex v1, Predicate<EstacionesVertex> goal, EstacionesVertex v2) {
		Double h = 0.;
		 
		return h;
	}
	
	/* Deprecation
 	public static Double heuristic2(EstacionesVertex v1, Predicate<EstacionesVertex> goal, EstacionesVertex v2) {
		Double h = 0.;
		Integer aristasQuedan = DatosEstaciones.n - v1.indice() + 1;
		h = aristasQuedan*DatosEstaciones.menorPesoArista;
		return h;
	}
  */
	public static Double heuristic2(EstacionesVertex v1, Predicate<EstacionesVertex> goal, EstacionesVertex v2) {

        Double h = 0.;

        if(v1.indice() < DatosEstaciones.n) {

                        Integer aristasQuedan = DatosEstaciones.n - v1.indice() + 1;

                        h = aristasQuedan*DatosEstaciones.menorPesoArista;

        }

        return h;

}

}


