package ejercicio1;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

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

		for (Integer id_fichero = 1; id_fichero < DatosAlmacenes.ntest; id_fichero++) {

			DatosAlmacenes.iniDatos("resources/ejercicio1/DatosEntrada"+ id_fichero + ".txt");
			System.out.println("=============");
			System.out.println("\tResultados para el test " + id_fichero + "\n");
			
			//DatosAlmacenes.toConsole();

			// V�rtices clave

			AlmacenesVertex start = AlmacenesVertex.start();

			// Grafo

			

			System.out.println("\n#### Algoritmo BT ####");
			
			// Algoritmo BT
			
			EGraph<AlmacenesVertex, AlmacenesEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Max)
					//.type(Type.All)
					//.solutionNumber(Integer.MAX_VALUE)
					.edgeWeight(x -> x.weight())
					//.heuristic(MulticonjuntoHeuristic::heuristic)
					.build();
			
			
			//GreedyOnGraph<MulticonjuntoVertex, MulticonjuntoEdge> rr = GreedyOnGraph.of(graph);
			
			//GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> r = rr.path();
			
			//System.out.println("Voraz = "+r.getWeight()+"  == "+SolucionMulticonjunto.of(r));
			
			BT<AlmacenesVertex, AlmacenesEdge, SolucionAlmacen> bta = BT.of(graph,
					SolucionAlmacen::of, null, null, true);

			//if (rr.isSolution(r)) {
			//	bta = BT.of(graph, SolucionMulticonjunto::of, r.getWeight(), r, true);
			//}
			Optional<GraphPath<AlmacenesVertex, AlmacenesEdge>> gp = bta.search();
			System.out.println(SolucionAlmacen.of(gp.get()));
			
			List<Integer> gp_as = gp.get().getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
			
			System.out.println(gp_as);

			
			for (SolucionAlmacen s: bta.getSolutions()) {
			//	System.out.println(s);
			}
//			System.out.println(bta.path.getEdgeList().stream().map(x -> x.action())
//					.collect(Collectors.toList()));
			
			
//			GraphColors.toDot(bta.graph(), "ficheros_generados/p4/ejemplo1/multiconjuntosBTGraph.gv", 
//					v -> v.toGraph(),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, MulticonjuntoVertex.goal().test(v)),
//					e -> GraphColors.colorIf(Color.red, bta.optimalPath.getEdgeList().contains(e)));

		}
	}

}


