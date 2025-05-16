package ejercicio1.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ejercicio1.DatosAlmacenes;
import ejercicio1.SolucionAlmacen;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class AlmacenesPDR {

	public static record Spm(Integer a, Integer weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Map<AlmacenesProblem, Spm> memory;
	public static Integer mejorValor = Integer.MIN_VALUE; 

	public static SolucionAlmacen search() {
		memory =  Map2.empty();
		mejorValor = Integer.MIN_VALUE; // Estamos maximizando
		// mejorValor = Integer.MAX_VALUE; // Estamos minimizando
		
		pdr_search(AlmacenesProblem.initial(), 0, memory);
		return getSolucion();
	}

	private static Spm pdr_search(AlmacenesProblem prob, Integer acumulado, Map<AlmacenesProblem, Spm> memoria) {

		Spm res = null;
		Boolean esTerminal = prob.goal();
		Boolean esSolucion = prob.goalHasSolution();

		if (memory.containsKey(prob)) {
			res = memory.get(prob);
			
		} else if (esTerminal && esSolucion) {
			res = Spm.of(null, 0);
			memory.put(prob, res);
			if (acumulado > mejorValor) { // Estamos maximizando
			//if (acumulado < mejorValor) { // Estamos minimizando
				mejorValor = acumulado;
			}
		} else {
			List<Spm> soluciones = List2.empty();
			for (Integer action : prob.actions()) {
				Double cota = acotar(acumulado, prob, action); 
				if (cota < mejorValor) { // Estamos maximizando
				//if (cota > mejorValor) { // Estamos minimizando
					continue;
				}
				AlmacenesProblem vecino = prob.neighbor(action);
				Integer espacioRequerido = 0;
				if (action>=0 && prob.indice()<DatosAlmacenes.getNumProductos()) {
				   espacioRequerido = 1;
				}
				Spm s = pdr_search(vecino, acumulado + espacioRequerido, memory);
				if (s != null) {
					Spm amp = Spm.of(action, s.weight() + action);
					soluciones.add(amp);
				}
			}
			res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null); // Estamos maximizando
			// res = soluciones.stream().min(Comparator.naturalOrder()).orElse(null); // Estamos minimizando 

			if (res != null)
				memory.put(prob, res);
		}

		return res;
	}

	private static Double acotar(Integer acum, AlmacenesProblem p, Integer a) {
		Integer espacioRequerido = 0;
		if (a>=0 && p.indice()<DatosAlmacenes.getNumProductos()) {
		   espacioRequerido = 1;
		}
		return acum + espacioRequerido + p.neighbor(a).heuristic();
	}

	public static SolucionAlmacen getSolucion() {
		List<Integer> acciones = List2.empty();
		AlmacenesProblem prob = AlmacenesProblem.initial();
		Spm spm = memory.get(prob);
		while (spm != null && spm.a != null) {
			AlmacenesProblem old = prob;
			acciones.add(spm.a);
			prob = old.neighbor(spm.a);
			spm = memory.get(prob);
		}
		return SolucionAlmacen.of(acciones);
	}

}
