package ejercicio2;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jgrapht.GraphPath;
import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;
import us.lsi.gurobi.GurobiSolution;

public class SolucionCursos {

    public static SolucionCursos create(GurobiSolution gs) {
        return new SolucionCursos(gs.objVal, gs.values);
    }

    public static SolucionCursos create(List<Integer> ls) {
        return new SolucionCursos(ls);
    }
    
    public static SolucionCursos of (List<Integer> ls) {
    	return new SolucionCursos(ls);
    }

	public static SolucionCursos of(GraphPath<CursosVertex, CursosEdge> path) {
		return SolucionCursos.ofEdges(path.getEdgeList());
	}
	
	public static SolucionCursos ofEdges(List<CursosEdge> ls) {
		List<Integer> alternativas = List2.empty();
		for (CursosEdge alternativa : ls) {
			alternativas.add(alternativa.action());
		}
		SolucionCursos s = SolucionCursos.of(alternativas);
		return s;
	}
    private Integer numCursos;
    private Map<Integer, Integer> solucion;
    private Double puntuacionTotal;
    private Integer costeTotal;

    private SolucionCursos(Double vo, Map<String, Double> vbles) {
        numCursos = 0;
        solucion = Map2.empty();
        puntuacionTotal = 0.0;
        costeTotal = 0;

        for (Map.Entry<String, Double> par : vbles.entrySet()) {
            if (par.getValue() > 0) {
                Integer i = Integer.parseInt(par.getKey().split("_")[1]);
                numCursos++;
                solucion.put(i, 1);
                puntuacionTotal += DatosCursos.getRelevancia(i);
                costeTotal += DatosCursos.getCoste(i);
            }
        }
    }

    private SolucionCursos(List<Integer> ls) {
        numCursos = 0;
        solucion = Map2.empty();
        puntuacionTotal = 0.0;
        costeTotal = 0;

        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i) > 0) {
                numCursos++;
                solucion.put(i, 1);
                puntuacionTotal += DatosCursos.getRelevancia(i);
                costeTotal += DatosCursos.getCoste(i);
            }
        }
    }

    private SolucionCursos() {
        numCursos = 0;
        solucion = Map2.empty();
        puntuacionTotal = 0.0;
        costeTotal = 0;
    }

    @Override
    public String toString() {
        return solucion.entrySet().stream()
                .map(p -> "Curso " + p.getKey() + ": Seleccionado")
                .collect(Collectors.joining("\n", "Cursos seleccionados:\n", String.format("\nTotal de cursos seleccionados: %d\nPuntuación total: %.2f\nCoste total: %d", numCursos, puntuacionTotal, costeTotal)));
    }

    public static void print(GurobiSolution gs) {
        String2.toConsole("%s\n%s\n%s", String2.linea(), create(gs), String2.linea());
    }

    public Integer getNumCursos() {
        return numCursos;
    }

    public Map<Integer, Integer> getSolucion() {
        return solucion;
    }

    public Double getPuntuacionTotal() {
        return puntuacionTotal;
    }

    public Integer getCosteTotal() {
        return costeTotal;
    }
}
