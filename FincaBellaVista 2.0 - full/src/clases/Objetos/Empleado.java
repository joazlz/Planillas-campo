package clases.Objetos;

import java.util.Date;

public class Empleado {

    private String nombre;
    private String actividad;
    private String area;
    private Date fecha;
    private float salarioDia;
    private float salarioTotalEmpleado;
    private int diasTrabajados;

    public int getDiasTrabajados() {
        return diasTrabajados;
    }

    public void setDiasTrabajados(int diasTrabajados) {
        this.diasTrabajados = diasTrabajados;
    }

    public float getSalarioTotalEmpleado() {
        return salarioTotalEmpleado;
    }

    public void setSalarioTotalEmpleado(float salarioTotalEmpleado) {
        this.salarioTotalEmpleado = salarioTotalEmpleado;
    }
    
    public Empleado() {
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getSalarioDia() {
        return salarioDia;
    }

    public void setSalarioDia(float salario) {
        this.salarioDia = salario;
    }

    
    
    
}
