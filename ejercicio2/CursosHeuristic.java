package ejercicio2;

import java.util.function.Predicate;

public class CursosHeuristic {
	
	public static Double heuristic(CursosVertex v1, Predicate<CursosVertex> goal, CursosVertex v2) {
		Double h = 0.;
		if (v1.indice() < DatosCursos.getNumCursos()) {
			for (int i = v1.indice(); i < DatosCursos.getNumCursos(); i++) {
			    h += DatosCursos.getCurso(i).relevancia();
			}
		} 
		return h;
	}

}


