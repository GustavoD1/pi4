package ejercicio2.manual;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import us.lsi.common.List2;
import us.lsi.common.Multiset;
import us.lsi.common.Set2;
import ejercicio2.DatosCursos;
import ejercicio2.DatosCursos.Curso;

public record CursosProblem (Integer indice,List<Integer> cursosSeleccionados,
		Integer presupuestoRestante, Set<Integer> areas) {
	
	public static CursosProblem initial() {
		return  CursosProblem.of(0,List2.empty(),DatosCursos.getPresupuestoTotal(),Set2.empty());
	}
	
	public static CursosProblem of(Integer indice,List<Integer> cursosSeleccionados,
			Integer presupuestoRestante, Set<Integer> areas) {
	     return new CursosProblem(indice,cursosSeleccionados,presupuestoRestante,areas);
	}
	
	public Boolean goal() {
		return this.indice == DatosCursos.getNumCursos();
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

	public CursosProblem neighbor(Integer a) {
		List<Integer> cursosSeleccionadosAux = List2.copy(cursosSeleccionados);
		Set<Integer> areasAux = Set2.copy(areas);
		if (a == 1) { 
			cursosSeleccionadosAux.add(indice);
			areasAux.add(DatosCursos.getArea(indice));
		}
		Integer presupuestoRestanteAux = presupuestoRestante - DatosCursos.getCoste(indice) * a;
		return CursosProblem.of(this.indice + 1, cursosSeleccionadosAux, presupuestoRestanteAux, areasAux);
	}

	public Double heuristic() {
		Double h = 0.;
		if (indice() < DatosCursos.getNumCursos()) {
			for (int i = indice(); i < DatosCursos.getNumCursos(); i++) {
			    h += DatosCursos.getCurso(i).relevancia();
			}
		} 
		return h;
	}
	
	@Override
	public String toString() {
		return "CursosVertexI [indice=" + indice + ", cursosSeleccionados=" + cursosSeleccionados + ", presupuestoRestante=" + presupuestoRestante
				+ "]";
	}

}
