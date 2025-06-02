package dto;

import java.util.List;

public class ReservaDTO {
	public String destino;
	public String fechaIngreso;
	public String fechaSalida;
	public Integer cantidadAdultos;
	public Integer cantidadHabitaciones;
	public List<Integer> cantidadNinosEdad;
	public Double costoPrevio;
	public Double costoTotal;
		
	public ReservaDTO(String destino, String fechaIngreso, String fechaSalida, Integer cantidadAdultos,
			Integer cantidadHabitaciones, List<Integer> cantidadNinosEdad) {
		this.destino = destino;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.cantidadAdultos = cantidadAdultos;
		this.cantidadHabitaciones = cantidadHabitaciones;
		this.cantidadNinosEdad = cantidadNinosEdad;
	}
	
	public ReservaDTO() {
		
	}
	
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public String getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public Integer getCantidadAdultos() {
		return cantidadAdultos;
	}
	public void setCantidadAdultos(Integer cantidadAdultos) {
		this.cantidadAdultos = cantidadAdultos;
	}
	public Integer getCantidadHabitaciones() {
		return cantidadHabitaciones;
	}
	public void setCantidadHabitaciones(Integer cantidadHabitaciones) {
		this.cantidadHabitaciones = cantidadHabitaciones;
	}
	public List<Integer> getCantidadNinosEdad() {
		return cantidadNinosEdad;
	}
	public void setCantidadNinosEdad(List<Integer> cantidadNinosEdad) {
		this.cantidadNinosEdad = cantidadNinosEdad;
	}
	public Double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(Double costoTotal) {
		this.costoTotal = costoTotal;
	}
	public Double getCostoPrevio() {
		return costoPrevio;
	}

	public void setCostoPrevio(Double costoPrevio) {
		this.costoPrevio = costoPrevio;
	}
}
