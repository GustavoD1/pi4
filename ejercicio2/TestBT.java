package ejercicio2;

import java.util.Locale;
import java.util.Optional;
import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));
		for (Integer id_fichero = 1; id_fichero <= DatosCursos.ntest; id_fichero++) {
			System.out.println("==========================");
			System.out.println("Entradas para el test " + id_fichero);
			DatosCursos.iniDatos("resources/ejercicio2/DatosEntrada"+ id_fichero + ".txt");
			System.out.println("==========================");
			//DatosCursos.toConsole();
			System.out.println("Resultados para el test " + id_fichero);
			CursosVertex start = CursosVertex.start();
			// Algoritmo BT
			EGraph<CursosVertex, CursosEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.edgeWeight(x -> x.weight())
					.heuristic(CursosHeuristic::heuristic)
					.build();
			
			BT<CursosVertex, CursosEdge, SolucionCursos> bta;
			GreedyOnGraph<CursosVertex, CursosEdge> ga = GreedyOnGraph.of(graph);
			Optional<GraphPath<CursosVertex, CursosEdge>> gv = ga.search();
			System.out.println("\n#### Algoritmo Voraz ####");
			if (gv.isPresent()) {
				System.out.println("SVoraz = " + SolucionCursos.of(gv.get()));
				bta = BT.of(graph,SolucionCursos::of,gv.get().getWeight(),gv.get(),true);
			}else {
				System.out.println("No hay solucion voraz");
				bta = BT.of(graph, null, null, null, true);
			} 
			System.out.println("\n#### Algoritmo BT ####");
			Optional<GraphPath<CursosVertex, CursosEdge>> gp = bta.search();
			if (gp.isPresent()) {
				GraphPath<CursosVertex, CursosEdge> gPath = gp.get();
				System.out.println("SBT = " + SolucionCursos.of(gPath));
				GraphColors.toDot(bta.outGraph(), "ficheros_generados/p4/ejemplo2/cursosBTGraphTest" + id_fichero + ".gv", 
						v -> String.format("(%d,%s)", v.indice(), v.cursosSeleccionados().toString() +" " + v.presupuestoRestante().toString()),
						e -> e.action().toString(), 
						v -> GraphColors.colorIf(Color.blue, v.goal()),
						e -> GraphColors.colorIf(Color.blue, bta.optimalPath.getEdgeList().contains(e)));
			} else {
				System.out.println("No hay solucion BT");
			}
		}
	}

}


