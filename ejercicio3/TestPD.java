package ejercicio3;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

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
		for (Integer id_fichero = 1; id_fichero < DatosFestival.ntest+3; id_fichero++) {

			String fichero = "resources/ejercicio3/DatosEntrada" + id_fichero + ".txt";
			DatosFestival.iniDatos(fichero);
			DatosFestival.ordenaDatos();
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

			// V�rtices clave

			FestivalVertex start = FestivalVertex.start();

			// Grafo

			System.out.println("\n\n#### Algoritmo PD ####");

			// Algoritmo PD
			
			EGraph<FestivalVertex, FestivalEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Min)
					.edgeWeight(x -> x.weight())
					.heuristic(FestivalHeuristic::heuristic)
					.build();
			
	
			
			//GreedyOnGraph<MulticonjuntoVertex, MulticonjuntoEdge> rr = 
			//		GreedyOnGraph.of(graph);
			
			//GraphPath<MulticonjuntoVertex, MulticonjuntoEdge> r = rr.path();
			
			//System.out.println("Voraz = "+r.getWeight()+"  == "+SolucionMulticonjunto.of(r));
			
			PDR<FestivalVertex, FestivalEdge, ?> pdr = PDR.of(graph, SolucionFestival::of, null, null, true);

			//if (rr.isSolution(r)) {
			//	pdr = PDR.of(graph, null,r.getWeight(), r, true);
			//}
			
			
			Optional<GraphPath<FestivalVertex, FestivalEdge>> gp = pdr.search();
			
			SolucionFestival s_pdr = null;
			
			if (gp.isPresent()) {
				System.out.println(gp.get().getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList()));
				List<Integer> gp_pdr = gp.get().getEdgeList().stream().map(x -> x.action()).collect(Collectors.toList()); // getEdgeList();

				s_pdr = SolucionFestival.of(gp_pdr);
				s_pdr = SolucionFestival.of(gp.get());

			} 
			
			
			System.out.println(s_pdr);
			
			
//			GraphColors.toDot(pdr.outGraph, fichero + ".PDR.gv", 
//					v -> String.format("(%d,%s)", v.indice(), v.entradasAsignadas().toString() +" " + v.coste().toString()),
//					e -> e.action().toString(), 
//					v -> GraphColors.colorIf(Color.red, v.goal()),
//					e -> GraphColors.colorIf(Color.red, gp.isPresent()?gp.get().getEdgeList().contains(e):false));
//
		}
	}
}

