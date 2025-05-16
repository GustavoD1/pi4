package ejercicio3;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAStar {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id_fichero = 1; id_fichero < DatosFestival.ntest+3; id_fichero++) {

			String fichero = "resources/ejercicio3/DatosEntrada" + id_fichero + ".txt";

			DatosFestival.iniDatos(fichero);
			DatosFestival.ordenaDatos();
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			// V�rtices clave

			FestivalVertex start = FestivalVertex.start();

			// Grafo

			System.out.println("#### Algoritmo A* ####");

			// Algoritmo A*
			EGraph<FestivalVertex, FestivalEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Min)
					.edgeWeight(x -> x.weight())
					.heuristic(FestivalHeuristic::heuristic)
					.build();
					
			AStar<FestivalVertex,FestivalEdge,?> aStar = AStar.ofGreedy(graph);
			
			GraphPath<FestivalVertex, FestivalEdge> gp = aStar.search().get();
			
			SolucionFestival s_as = SolucionFestival.of(gp);
			
			System.out.println(s_as);

//			GraphColors.toDot(aStar.outGraph, "ficheros_generados/p4/ejemplo1/multiconjuntosAStarGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
		}
	}

}

