package clases.Objetos;

public class ObjDetallesActividad {

    private int detalleEmpleados = 0;
    private int detalleMaquina = 0;
    private int detalleQuimico = 0;

    public ObjDetallesActividad(int detalleEmpleados, int detalleMaquina, int detalleQuimico) {
        this.detalleEmpleados=detalleEmpleados;
        this.detalleMaquina=detalleMaquina;
        this.detalleQuimico=detalleQuimico;
    }

    public ObjDetallesActividad() {
    }
    
    
    public int getDetalleEmpleados() {
        return detalleEmpleados;
    }

    public void setDetalleEmpleados(int detalleEmpleados) {
        this.detalleEmpleados = detalleEmpleados;
    }

    public int getDetalleMaquina() {
        return detalleMaquina;
    }

    public void setDetalleMaquina(int detalleMaquina) {
        this.detalleMaquina = detalleMaquina;
    }

    public int getDetalleQuimico() {
        return detalleQuimico;
    }

    public void setDetalleQuimico(int detalleQuimico) {
        this.detalleQuimico = detalleQuimico;
    }

    
}
