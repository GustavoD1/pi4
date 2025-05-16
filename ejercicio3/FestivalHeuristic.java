package ejercicio3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class FestivalHeuristic {

	public static Double heuristic(FestivalVertex v1, Predicate<FestivalVertex> goal, FestivalVertex v2) {
		return (double) haux(v1);
	}

	private static Integer haux(FestivalVertex v) {
		Integer sum_coste = 0;
		List<Integer> entradasPtes = new ArrayList<Integer>(v.entradasPorAsignarTipo());
		Integer index = v.indice();
		Integer tipo = v.tipo();
		while (tipo < DatosFestival.getNumTiposEntrada()) {
			Integer ordenArea = DatosFestival.getOrdenArea(index);
			while (ordenArea < DatosFestival.getNumAreas() && 
					entradasPtes.get(tipo) > 0) {
				Integer area = DatosFestival.getAreaFromOrden(index);
				Integer entradas = Math.min(entradasPtes.get(tipo), 
						v.aforoDisponibleArea().get(area)); 
				sum_coste += entradas * DatosFestival.getCosteAsignacion(index);
				entradasPtes.set(tipo, entradasPtes.get(tipo) - entradas);
				index++;
				ordenArea++;
			}
			tipo++;
			index = tipo*DatosFestival.getNumAreas();
		}
		return sum_coste;
	}

}


