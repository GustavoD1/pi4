package ejercicio4;


import java.util.Locale;

import org.jgrapht.GraphPath;

import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAStar {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		for (Integer id_fichero = 1; id_fichero < DatosEstaciones.ntest; id_fichero++) {

			String fichero = "resources/ejercicio4/DatosEntrada" + id_fichero + ".txt";

			DatosEstaciones.iniDatos(fichero);
			
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");


			EstacionesVertex start = EstacionesVertex.start();

			// Grafo

			System.out.println("#### Algoritmo A* ####");

			// Algoritmo A*
			EGraph<EstacionesVertex, EstacionesEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Min)
					.heuristic(EstacionesHeuristic::heuristic2)
					.build();
					
			AStar<EstacionesVertex,EstacionesEdge,SolucionEstaciones> aStar = AStar.of(graph);
			
			GraphPath<EstacionesVertex, EstacionesEdge> gp = aStar.search().get();
			
			SolucionEstaciones s = SolucionEstaciones.of(gp);
			
			System.out.println(s);
		}
	}

}

