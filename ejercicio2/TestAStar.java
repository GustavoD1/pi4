package ejercicio2;


import java.util.*;
import java.util.stream.Collectors;
import org.jgrapht.GraphPath;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;

public class TestAStar {

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
			// Algoritmo AStar
			EGraph<CursosVertex, CursosEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.edgeWeight(x -> x.weight())
					.heuristic(CursosHeuristic::heuristic)
					.build();
			System.out.println("#### Algoritmo A* ####");
			// Algoritmo A*
			AStar<CursosVertex,CursosEdge,SolucionCursos> aStar = AStar.ofGreedy(graph);
			Optional<GraphPath<CursosVertex, CursosEdge>> gp = aStar.search();
			List<Integer> gp_as = gp.get().getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList());
			SolucionCursos s_as = SolucionCursos.of(gp_as);
			System.out.println(s_as);
			GraphColors.toDot(aStar.outGraph(), "ficheros_generados/p4/ejemplo2/cursosAStarGraphTest" + id_fichero + ".gv", 
					v -> String.format("(%d,%s)", v.indice(), v.cursosSeleccionados().toString() +" " + v.presupuestoRestante().toString()),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.blue, v.goal()),
					e -> GraphColors.colorIf(Color.blue, gp.get().getEdgeList().contains(e)));
		}
	}

}

