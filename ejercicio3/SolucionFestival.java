package ejercicio3;

import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

import java.util.HashMap;

import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;
import us.lsi.gurobi.GurobiSolution;

public class SolucionFestival {

    public static SolucionFestival create(GurobiSolution gs) {
        return new SolucionFestival(gs.objVal, gs.values);
    }

    public static SolucionFestival create(List<Integer> ls) {
        return new SolucionFestival(ls);
    }

    public static SolucionFestival of (List<Integer> ls) {
    	return new SolucionFestival(ls);
    }
    
    
    
    
    public static SolucionFestival of(GraphPath<FestivalVertex, FestivalEdge> path) {
		return new SolucionFestival(path);
	}

    
    
    
	public static SolucionFestival ofEdges(List<FestivalEdge> ls) {
		List<Integer> alternativas = List2.empty();
		for (FestivalEdge alternativa : ls) {
			alternativas.add(alternativa.action());
		}
		SolucionFestival s = SolucionFestival.of(alternativas);
		return s;
	}
    
    private Integer numAsignaciones;
    private Map<Integer, Integer> solucion;
    private Double costeTotal;
    private Integer unidadesTotales;

    private SolucionFestival(Double vo, Map<String, Double> vbles) {
        numAsignaciones = 0;
        solucion = Map2.empty();
        costeTotal = 0.0;
        unidadesTotales = 0;

        for (Map.Entry<String, Double> par : vbles.entrySet()) {
            if (par.getValue() > 0) {
                Integer i = Integer.parseInt(par.getKey().split("_")[1]);
                Integer j = Integer.parseInt(par.getKey().split("_")[2]);
                numAsignaciones++;
                solucion.put(i * DatosFestival.getNumAreas() + j, par.getValue().intValue());
                costeTotal += DatosFestival.getCosteAsignacion(i, j) * par.getValue().intValue();
                unidadesTotales += par.getValue().intValue();
            }
        }
    }

    
    
    
    private SolucionFestival(GraphPath<FestivalVertex, FestivalEdge> path) {
    	List<FestivalEdge> ls = path.getEdgeList();
        numAsignaciones = 0;
        solucion = Map2.empty();
        costeTotal = 0.0;
        unidadesTotales = 0;

        for (int i = 0; i < ls.size(); i++) {
        	FestivalVertex v = ls.get(i).source();
        	Integer entradas = ls.get(i).action();
            if (entradas > 0) {
                Integer tipoEntrada = v.tipo();
                Integer area = v.area();
                Integer indiceAux = tipoEntrada*DatosFestival.getNumAreas()+area;
                numAsignaciones++;
                solucion.put(indiceAux, entradas);
                costeTotal += DatosFestival.getCosteAsignacion(tipoEntrada, area) * entradas;
                unidadesTotales += entradas;
            }
        }
    }
    
    
    
    
    private SolucionFestival(List<Integer> ls) {
        numAsignaciones = 0;
        solucion = Map2.empty();
        costeTotal = 0.0;
        unidadesTotales = 0;

        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i) > 0) {
                Integer tipoEntrada = i / DatosFestival.getNumAreas();
                Integer area = i % DatosFestival.getNumAreas();
                numAsignaciones++;
                solucion.put(tipoEntrada * DatosFestival.getNumAreas() + area, ls.get(i));
                costeTotal += DatosFestival.getCosteAsignacion(tipoEntrada, area) * ls.get(i);
                unidadesTotales += ls.get(i);
            }
        }
    }
    
    

    private SolucionFestival() {
        numAsignaciones = 0;
        solucion = Map2.empty();
        costeTotal = 0.0;
        unidadesTotales = 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Resumen de asignaciones:\n");

        Map<Integer, Integer> aforoOcupadoPorArea = new HashMap<>();
        Map<Integer, Map<Integer, Integer>> entradasPorArea = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : solucion.entrySet()) {
            Integer tipoEntrada = entry.getKey() / DatosFestival.getNumAreas();
            Integer area = entry.getKey() % DatosFestival.getNumAreas();
            Integer unidades = entry.getValue();

            if (unidades > 0) {
                aforoOcupadoPorArea.put(area, aforoOcupadoPorArea.getOrDefault(area, 0) + unidades);
                entradasPorArea.computeIfAbsent(area, k -> new HashMap<>())
                        .put(tipoEntrada, entradasPorArea.get(area).getOrDefault(tipoEntrada, 0) + unidades);
            }
        }

        for (int i = 0; i < DatosFestival.getNumAreas(); i++) {
            Integer aforoOcupado = aforoOcupadoPorArea.getOrDefault(i, 0);
            if (aforoOcupado > 0) {
                result.append(String.format("Aforo área %s: %d/%d\n",
                        DatosFestival.getArea(i).nombre(),
                        aforoOcupado,
                        DatosFestival.getAforoMaximoArea(i)));

                entradasPorArea.getOrDefault(i, new HashMap<>()).forEach((tipoEntrada, unidades) ->
                        result.append(String.format("Tipo de entrada %s asignadas: %d unidades\n",
                                DatosFestival.getTipoEntrada(tipoEntrada).tipo(), unidades)
                ));
            }
        }

        result.append(String.format("Coste total: %.2f\nUnidades totales: %d\n", costeTotal, unidadesTotales));

        return result.toString();
    }

    public static void print(GurobiSolution gs) {
        String2.toConsole("%s\n%s\n%s", String2.linea(), create(gs), String2.linea());
    }

    public Integer getNumAsignaciones() {
        return numAsignaciones;
    }

    public Map<Integer, Integer> getSolucion() {
        return solucion;
    }

    public Double getCosteTotal() {
        return costeTotal;
    }

    public Integer getUnidadesTotales() {
        return unidadesTotales;
    }
}
