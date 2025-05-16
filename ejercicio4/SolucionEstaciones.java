package ejercicio4;

import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

public class SolucionEstaciones {

    
    public static SolucionEstaciones create(List<Integer> ls) {
        return new SolucionEstaciones(ls);
    }
    
    public static SolucionEstaciones of(GraphPath<EstacionesVertex, EstacionesEdge> g) {
        return new SolucionEstaciones(g);
    }

    private Integer numEstaciones;
    private List<Estacion> camino;
    private Double tiempoTotal;
    private Double costeTotal;

    private SolucionEstaciones(List<Integer> ls) {
        numEstaciones = ls.size();
        camino = ls.stream().map(i -> DatosEstaciones.grafoTiempo.getVertex(i)).collect(Collectors.toList());
    	camino.add(camino.get(0));
    	
        tiempoTotal = calculaTiempoTotal(camino);

    }
    
    private SolucionEstaciones(GraphPath<EstacionesVertex, EstacionesEdge> g) {
    	List<Integer> ls = g.getEdgeList().stream().map(x -> x.action())
				.collect(Collectors.toList()); // getEdgeList();
		
		ls.add(0, 0);
        numEstaciones = ls.size();
        camino = ls.stream().map(i -> DatosEstaciones.grafoTiempo.getVertex(i)).collect(Collectors.toList());
    	camino.add(camino.get(0));
    	
        tiempoTotal = calculaTiempoTotal(camino);
        costeTotal = calculaCosteTotal(camino);

    }

    private SolucionEstaciones() {
        numEstaciones = 0;
        camino = List.of();
        tiempoTotal = 0.0;
       // tiempoMedio = 0.0;
    }

    private Double calculaTiempoTotal(List<Estacion> camino) {
    	Double tiempoTotal = 0.0;
    
		for (int i = 0; i < camino.size() - 1; i=i+1) {
			tiempoTotal += DatosEstaciones.grafoCompleto.getEdge(camino.get(i), camino.get(i+1)).tiempo();
			
		}
		return tiempoTotal;
    }
    
    private Double calculaCosteTotal(List<Estacion> camino) {
    	Double costeTotal = 0.0;
    
		for (int i = 0; i < camino.size() - 1; i=i+1) {
			costeTotal += DatosEstaciones.grafoCompleto.getEdge(camino.get(i), camino.get(i+1)).costeBillete();
			
		}
		return costeTotal;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Resumen del recorrido:\n");

        result.append("Camino seguido: ").append(camino.stream()
                .map(Estacion::nombre)
                .collect(Collectors.joining(" -> "))).append("\n");

        result.append(String.format("Tiempo total: %.2f min\n", tiempoTotal));
      
        return result.toString();
    }

   
    public Integer getNumEstaciones() {
        return numEstaciones;
    }

    public List<Estacion> getCamino() {
        return camino;
    }

    public Double getTiempoTotal() {
        return tiempoTotal;
    }
    public Double getCosteTotal() {
        return costeTotal;
    }

}
