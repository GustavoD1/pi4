package ejercicio1;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;


public interface AlmacenesVertex extends
	VirtualVertex<AlmacenesVertex, AlmacenesEdge, Integer> {

	Integer indice();
	List<Integer> espacioDisponible();
	List<Set<Integer>> productosAlmacenados();
	Integer cantidadAlmacenda();
	Double accionReal();

	public static AlmacenesVertex start() {
	
		List<Integer> espacioDisponibleAux = IntStream.range(0, DatosAlmacenes.getNumAlmacenes()).boxed()
				.map(i ->DatosAlmacenes.getMetrosCubicosAlmacen(i)).toList();
		List<Set<Integer>> productosAlmacenados = IntStream.range(0, DatosAlmacenes.getNumAlmacenes()).boxed()
				.map(i ->Set2.<Integer>empty()).toList();

		return  AlmacenesVertexI.of(0,productosAlmacenados,espacioDisponibleAux, 0);
	}
}
	
	

