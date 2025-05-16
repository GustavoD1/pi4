package ejercicio4;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import us.lsi.common.List2;


public record EstacionesVertexI (Integer indice, List<Integer> camino, Double costeAcumulado) implements EstacionesVertex {

	public static EstacionesVertexI of(Integer indice,List<Integer> camino, Double costeAcumulado) {
	     return new EstacionesVertexI(indice,camino,costeAcumulado);
	}
	
	
	public Boolean goal() {
		return  this.indice == DatosEstaciones.n;
	}
	
	public Boolean goalHasSolution() {
		Integer numEstacionesConsecSatisf = DatosEstaciones.calculaEstacionesConsecSatisf(camino);
		return numEstacionesConsecSatisf != 0;
	}
	
	
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = new ArrayList<Integer>();

		if (indice == DatosEstaciones.n-1) {
			Integer vAnt = camino.get(indice-1);
			
			int ultimo = IntStream.range(0, DatosEstaciones.n) // Genera números de 0 a n-1
		                .filter(num -> !camino.contains(num)) // Filtra los que no están en la lista
		                .findFirst() // Obtiene el único número restante
		                .orElse(-1);
			
			if(DatosEstaciones.existeTramo(vAnt, ultimo) && DatosEstaciones.existeTramo(ultimo, 0)) {
				if(costeAcumulado + 
						DatosEstaciones.grafoCoste.getEdgeWeight(vAnt, ultimo) + 
						DatosEstaciones.grafoCoste.getEdgeWeight(ultimo, 0) <= DatosEstaciones.limiteCoste) {
					alternativas.add(ultimo);
				}
			}
			
		} else if (indice < (DatosEstaciones.n-1)) {	
			Integer vAnt = camino.get(indice-1);
			Set<Integer> vecinosValidos = DatosEstaciones.vecinos(vAnt);
			vecinosValidos.removeAll(camino);
			
			vecinosValidos.removeIf(v -> 
				costeAcumulado + DatosEstaciones.grafoCoste.getEdgeWeight(vAnt, v) > DatosEstaciones.limiteCoste);
			
			alternativas = new ArrayList<>(vecinosValidos);
		} 
		return alternativas;
	}

	
	@Override
	public EstacionesVertex neighbor(Integer a) {
		Integer vAnt = camino.get(indice-1);
		List<Integer> caminoAux = List2.copy(camino);
		caminoAux.add(a); 
		Double nuevoCoste = costeAcumulado+DatosEstaciones.grafoCoste.getEdgeWeight(vAnt, a);
		return EstacionesVertexI.of(this.indice + 1,caminoAux, nuevoCoste);
	}


	@Override
	public EstacionesEdge edge(Integer a) {
		return EstacionesEdge.of(this, this.neighbor(a), a);
	}

	
	@Override
	public String toString() {
		return "EstacionesVertexI [indice=" + indice + ", camino=" + camino + "]";
	}



    

}
