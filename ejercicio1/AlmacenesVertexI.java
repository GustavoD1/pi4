package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import us.lsi.common.List2;
import us.lsi.common.Set2;


public record AlmacenesVertexI(Integer indice,List<Set<Integer>> productosAlmacenados,List<Integer> espacioDisponible,Integer cantidadAlmacenda) implements AlmacenesVertex {

	public static AlmacenesVertexI of(Integer indice,List<Set<Integer>> productosAlmacenados, List<Integer> espacioDisponible,Integer cantidadAlmacenda) {
		return  new AlmacenesVertexI(indice, productosAlmacenados, espacioDisponible, cantidadAlmacenda);
	}
	
	
	public Boolean goal() {
		//System.out.println(this.indice == DatosAlmacenes.getNumProductos());
		return  this.indice == DatosAlmacenes.getNumProductos();
	}
	
	public Boolean goalHasSolution() {
		return  true;
	}
	
	
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();

		alternativas.add(-1);
		if (this.indice <= DatosAlmacenes.getNumProductos()) {
			Integer espacioRequerido = DatosAlmacenes.getProducto(indice).metroscubicosrequeridos();

			if (this.indice ==  DatosAlmacenes.getNumProductos() - 1) {
				//System.out.println("Ok " + indice);
				for (int i=0;i<espacioDisponible().size();i++) {
                	//System.out.println("Espacio:" + i + " " + espacioDisponible().get(i) + ">=" + espacioRequerido);
					
                	if (espacioRequerido<=espacioDisponible().get(i)) {
                		//System.out.println(i + " " + incompatibleAlmacen(i) );

                		if (!incompatibleAlmacen(i)) {
                		   alternativas.add(0,i);
                		   return alternativas;
                		}
                	}
                }
			} else {
                for (int i=0;i<espacioDisponible().size();i++) {
                	//System.out.println("Espacio:" + i + " " + espacioDisponible().get(i) + ">=" + espacioRequerido);
                	if (espacioRequerido<=espacioDisponible().get(i)) {
                		//System.out.println(i + " " + incompatibleAlmacen(i) );
                		if (!incompatibleAlmacen(i)) {
                		   alternativas.add(0,i);
                		}
                	}
                }
			}
		}	
		//System.out.println(indice + " -> " + alternativas );
		return alternativas;
	}

	private boolean incompatibleAlmacen(int a)  {
		for (int j : productosAlmacenados().get(a)) {
			if (DatosAlmacenes.sonIncompatibles(indice(), j)) {
				return true;
			}
		}
		return false;
	}
	@Override
	public AlmacenesVertex neighbor(Integer a) {
		List<Integer> espacioDisponibleAux = List2.copy(espacioDisponible);
		List<Set<Integer>> productosAlmacenadosAux  = scopy2(productosAlmacenados);
		Integer cantidadAlmacenadaAux = cantidadAlmacenda();
		if (a!=-1) { 
			Integer espacioRequerido = DatosAlmacenes.getProducto(indice).metroscubicosrequeridos();
			espacioDisponibleAux.set(a, espacioDisponibleAux.get(a) - espacioRequerido);
			productosAlmacenadosAux.get(a).add(indice);
			cantidadAlmacenadaAux = cantidadAlmacenadaAux + espacioRequerido;
		}
		return AlmacenesVertexI.of(this.indice + 1,productosAlmacenadosAux,espacioDisponibleAux, cantidadAlmacenadaAux);
	}

	  public static List<Set<Integer>> scopy2(List<Set<Integer>> ls){
		   List<Set<Integer>> r = new ArrayList<>();
		   for(Set<Integer> c: ls) {
			   Set<Integer> cp = Set2.copy(c);
			   r.add(cp);
		   }
		   return r;
		}
	
	
	
	@Override
	public AlmacenesEdge edge(Integer a) {
		return AlmacenesEdge.of(this, this.neighbor(a), a);
	}

	@Override
	public Double accionReal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "AlmacenesVertexI [indice=" + indice + ", productosAlmacenados=" + productosAlmacenados
				+ ", espacioDisponible=" + espacioDisponible + ", cantidadAlmacenda=" + cantidadAlmacenda + "]";
	}

    

}
