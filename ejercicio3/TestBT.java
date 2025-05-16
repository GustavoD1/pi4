package ejercicio3;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id_fichero = 1; id_fichero < DatosFestival.ntest+3; id_fichero++) {

			DatosFestival.iniDatos("resources/ejercicio3/DatosEntrada"+ id_fichero + ".txt");
			DatosFestival.ordenaDatos();
			System.out.println("=============");
			System.out.println("\tResultados para el test " + id_fichero + "\n");
			
			FestivalVertex start = FestivalVertex.start();

			System.out.println("\n#### Algoritmo BT ####");
			
			// Algoritmo BT
			
			EGraph<FestivalVertex, FestivalEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Min)
					.edgeWeight(x -> x.weight())
					.heuristic(FestivalHeuristic::heuristic)
					.build();
			
			
			BT<FestivalVertex, FestivalEdge, SolucionFestival> bta = BT.of(graph, SolucionFestival::of);

			Optional<GraphPath<FestivalVertex, FestivalEdge>> gp = bta.search();
			if (gp.isPresent()) {
			   System.out.println(SolucionFestival.of(gp.get()));
			} else {
				System.out.println("No hay solucion");
			}

		}
	}

}


