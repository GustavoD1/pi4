package ejercicio2;
import java.util.*;

import ejercicio2.DatosCursos.Curso;
import us.lsi.common.*;

public record CursosVertexI (Integer indice, List<Integer> cursosSeleccionados, 
		Integer presupuestoRestante, Set<Integer> areas) 
implements CursosVertex {

	public static CursosVertexI of(Integer indice, List<Integer> cursosSeleccionados, 
			Integer presupuestoRestante, Set<Integer> areas) {
	     return new CursosVertexI(indice, cursosSeleccionados, presupuestoRestante, areas);
	}
	
	private List<Curso> cursosRestantes(Integer area, Integer i) {
		List<Curso> ls = new ArrayList<Curso>();
		for (int k = i; k < DatosCursos.getNumCursos(); k++) {
			if (DatosCursos.getArea(k).equals(area)) {
				ls.add(DatosCursos.getCurso(k));
			}
		}
		return ls;
	}
		
	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		if (indice < DatosCursos.getNumCursos()) {
			Integer area = DatosCursos.getArea(indice);
			// Si sólo queda un curso de un area sin cubrir
			if (!areas.contains(area) && cursosRestantes(area, indice + 1).isEmpty()) {
				if (this.presupuestoRestante >= DatosCursos.getCoste(indice)) {
			           alternativas.add(1);
				}
			}else {
				if (this.presupuestoRestante >= DatosCursos.getCoste(indice)) {
			           alternativas.add(1);
				}
				alternativas.add(0);
			}
		}
		return alternativas;
	}

	@Override
	public CursosVertex neighbor(Integer a) {
		List<Integer> cursosSeleccionadosAux = List2.copy(cursosSeleccionados);
		Set<Integer> areasAux = Set2.copy(areas);
		if (a == 1) { 
			cursosSeleccionadosAux.add(indice);
			areasAux.add(DatosCursos.getArea(indice));
		}
		Integer presupuestoRestanteAux = presupuestoRestante - DatosCursos.getCoste(indice) * a;
		return CursosVertexI.of(this.indice + 1, cursosSeleccionadosAux, presupuestoRestanteAux, areasAux);
	}

	@Override
	public CursosEdge edge(Integer a) {
		return CursosEdge.of(this, this.neighbor(a), a);
	}

	public Boolean goal() {
		return this.indice == DatosCursos.getNumCursos();
	}
	
	public Boolean goalHasSolution() {
		// El coste total de los cursos seleccionados no debe superar un presupuesto total
		// asignado para el programa.
		if (presupuestoRestante < 0 || 
				(DatosCursos.getNumAreas() > 0 && cursosSeleccionados.isEmpty())) {
			return false;
		}
		Multiset<Integer> mAreas = Multiset.empty();
		Double duracionMedia = 0.0;
		for (Integer indexCurso: cursosSeleccionados) {
			Curso curso = DatosCursos.getCurso(indexCurso);
			mAreas.add(curso.area());
			duracionMedia += curso.duracion();
		}
		duracionMedia = duracionMedia / cursosSeleccionados.size();
		// El programa debe incluir al menos un curso de cada área de conocimiento.
		// El número de cursos de tecnología seleccionados debe ser superior o igual a los
		// seleccionados de cualquiera otra área.
		for (int i = 0; i < DatosCursos.getNumAreas(); i ++) {
			if (mAreas.count(i) <= 0 || mAreas.count(i) > mAreas.count(0)) {
				return false;
			}
		}
		// Se debe garantizar que la duración media de los cursos seleccionados sea de al
		// menos 20 horas.
	 	return duracionMedia >= 20.0;
	}

	@Override
	public String toString() {
		return "CursosVertexI [indice=" + indice + ", cursosSeleccionados=" + cursosSeleccionados
				+ ", presupuestoRestante=" + presupuestoRestante + ", areas=" + areas + "]";
	}
	
}
