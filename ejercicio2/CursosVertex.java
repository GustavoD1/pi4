package ejercicio2;
import java.util.List;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.graphs.virtual.VirtualVertex;

public interface CursosVertex extends
	VirtualVertex<CursosVertex, CursosEdge, Integer> {

	Integer indice();
	List<Integer> cursosSeleccionados();
	Integer presupuestoRestante();

	public static CursosVertex start() {
		return  CursosVertexI.of(0, List2.empty(), 
				DatosCursos.getPresupuestoTotal(), Set2.empty());
	}
}