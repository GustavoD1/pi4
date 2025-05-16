package ejercicio2;

import java.util.*;
import org.jgrapht.GraphPath;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestPD {

	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));
		for (Integer id_fichero = 1; id_fichero <= DatosCursos.ntest; id_fichero++) {
			System.out.println("==========================");
			System.out.println("Entradas para el test " + id_fichero);
			DatosCursos.iniDatos("resources/ejercicio2/DatosEntrada"+ id_fichero + ".txt");
			System.out.println("==========================");
			//DatosCursos.toConsole();
			CursosVertex start = CursosVertex.start();
			// Algoritmo PD
			EGraph<CursosVertex, CursosEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.edgeWeight(x -> x.weight())
					.heuristic(CursosHeuristic::heuristic)
					.build();
			PDR<CursosVertex, CursosEdge, SolucionCursos> pdr;			
			GreedyOnGraph<CursosVertex, CursosEdge> ga = GreedyOnGraph.of(graph);
			Optional<GraphPath<CursosVertex, CursosEdge>> gv = ga.search();
			System.out.println("\n#### Algoritmo Voraz ####");
			if (gv.isPresent()) {
				System.out.println("SVoraz = " + SolucionCursos.of(gv.get()));
				pdr = PDR.of(graph,SolucionCursos::of,gv.get().getWeight(),gv.get(),true);
			}else {
				System.out.println("No hay solucion voraz");
				pdr = PDR.of(graph, null,null, null, true);
			}
			System.out.println("\n#### Algoritmo PD ####");
			Optional<GraphPath<CursosVertex, CursosEdge>> gp = pdr.search();
			if (gp.isPresent()) {
				GraphPath<CursosVertex, CursosEdge> gPath = gp.get();
				System.out.println("SPDR = " + SolucionCursos.of(gPath));
				GraphColors.toDot(pdr.outGraph, "ficheros_generados/p4/ejemplo2/cursosPDRGraphTest" + id_fichero + ".gv", 
						v -> String.format("(%d,%s)", v.indice(), v.cursosSeleccionados().toString() +" " + v.presupuestoRestante().toString()),
						e -> e.action().toString(), 
						v -> GraphColors.colorIf(Color.blue, v.goal()),
						e -> GraphColors.colorIf(Color.blue, pdr.optimalPath.getEdgeList().contains(e)));
			} else {
				System.out.println("No hay solucion BT");
			}
		}
	}
}

