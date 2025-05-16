package ejercicio3;

import java.util.Collections;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.List2;



public record FestivalVertexI (Integer indice,List<Integer> entradasPorAsignarTipo,List<Integer> aforoDisponibleArea) implements FestivalVertex {

	public static FestivalVertexI of(Integer indice,List<Integer> entradasPorAsignarTipo,List<Integer> aforoDisponibleArea) {
	     return new FestivalVertexI(indice,entradasPorAsignarTipo,aforoDisponibleArea);
	}

	public Boolean goal() {
		return  this.indice == DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada();
	}
	
	@Override
	public List<Integer> actions() {
		if (indice == DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada()) 
			return List.of();
		Integer tipo = tipo();
		Integer area = area();
		Integer ordenArea = DatosFestival.getOrdenArea(indice);
		if (ordenArea == DatosFestival.getNumAreas()-1) {
			if (aforoDisponibleArea().get(area) >= entradasPorAsignarTipo().get(tipo)) {
				return List.of(entradasPorAsignarTipo().get(tipo));
			} else {
				return List.of();
			}
		} 
		Integer nu = greedyAction().intValue();
		List<Integer> alternativas = IntStream.rangeClosed(0,nu)
						.boxed()
						.collect(Collectors.toList());
		Collections.reverse(alternativas);
		return alternativas;
	}
	
	@Override
	public Integer greedyAction() {
		return Math.min(aforoDisponibleArea.get(area()),entradasPorAsignarTipo.get(tipo()));
	}

	@Override
	public FestivalVertex neighbor(Integer a) {
		Integer tipo = tipo();
		Integer area = area();
		List<Integer> porAsignarTipo = List2.copy(entradasPorAsignarTipo);
		porAsignarTipo.set(tipo, entradasPorAsignarTipo.get(tipo)-a);
		List<Integer> aforoPte = List2.copy(aforoDisponibleArea);
		aforoPte.set(area, aforoDisponibleArea.get(area)-a);
		return FestivalVertexI.of(this.indice + 1,porAsignarTipo,aforoPte);
	}

	@Override
	public String toString() {
		return "FestivalVertexI [indice=" + indice + ", "
				+ "entradasPorAsignarTipo=" + entradasPorAsignarTipo + ", "
				+ "aforoDisponibleArea=" + aforoDisponibleArea
				+ "]";
	}


	@Override
	public FestivalEdge edge(Integer a) {
		return FestivalEdge.of(this, this.neighbor(a), a);
	}

	@Override
	public Integer tipo() {
		return indice() / DatosFestival.getNumAreas();
	}


	@Override
	public Integer area() {
		return DatosFestival.getAreaFromOrden(indice);
	}

}
