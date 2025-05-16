package ejercicio2.manual;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ejercicio2.SolucionCursos;
import ejercicio2.DatosCursos;
import us.lsi.common.List2;
import us.lsi.common.Map2;

public class CursosPDR {

	public static record Spm(Integer a, Integer weight) implements Comparable<Spm> {
		public static Spm of(Integer a, Integer weight) {
			return new Spm(a, weight);
		}
		@Override
		public int compareTo(Spm sp) {
			return this.weight.compareTo(sp.weight);
		}
	}
	
	public static Map<CursosProblem, Spm> memory;
	public static Integer mejorValor = Integer.MIN_VALUE;  // Estamos maximizando
	//public static Integer mejorValor = Integer.MAX_VALUE;  // Estamos minimizando

	public static SolucionCursos search() {
		memory =  Map2.empty();
		mejorValor = Integer.MIN_VALUE; // Estamos maximizando
		//mejorValor = Integer.MAX_VALUE; // Estamos minimizando
		pdr_search(CursosProblem.initial(), 0, memory);
		return getSolucion();
	}

	private static Spm pdr_search(CursosProblem prob, Integer acumulado, Map<CursosProblem, Spm> memoria) {
		// System.out.println(prob + "->" + acumulado + " mejorValor:" + mejorValor);
		Spm res = null;
		Boolean esTerminal = prob.goal();
		Boolean esSolucion = prob.goalHasSolution();
		// System.out.println("esTerminal:" + esTerminal + " esSolucion:" + esSolucion);
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
				//if (cota > mejorValor) { // Estamos manimizando
					continue;
				}
				CursosProblem vecino = prob.neighbor(action);
				//Hay que adaptar al problema
				Integer relevancia = DatosCursos.getCurso(prob.indice()).relevancia();
				Spm s = pdr_search(vecino, (int)(acumulado + relevancia * action), memory);
				if (s != null) {
					Spm amp = Spm.of(action, s.weight() + relevancia * action);
					soluciones.add(amp);
				}
			}
			// Estamos minimizando -< maximizando
			res = soluciones.stream().max(Comparator.naturalOrder()).orElse(null); //Estamos maximizando
			//res = soluciones.stream().min(Comparator.naturalOrder()).orElse(null); //Estamos minimizando
			if (res != null)
				memory.put(prob, res);
		}
		return res;
	}

	private static Double acotar(Integer acum, CursosProblem p, Integer a) {
		Double relevancia = 0.0;
		if (p.indice()<DatosCursos.getNumCursos()) {
  		   relevancia = (double)DatosCursos.getCurso(p.indice()).relevancia();
		}
		return acum + relevancia * a + p.neighbor(a).heuristic();
	}

	public static SolucionCursos getSolucion() {
		List<Integer> acciones = List2.empty();
		CursosProblem prob = CursosProblem.initial();
		Spm spm = memory.get(prob);
		while (spm != null && spm.a != null) {
			CursosProblem old = prob;
			acciones.add(spm.a);
			prob = old.neighbor(spm.a);
			spm = memory.get(prob);
		}
		return SolucionCursos.of(acciones);
	}

}
