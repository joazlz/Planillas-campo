package SQLclasesSecundarias;

import clases.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SQLDetalleEmpleadoCorte {
    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection();
    private static Statement st;
    static ResultSet rs;
    private java.awt.Frame pa;

    public SQLDetalleEmpleadoCorte(java.awt.Frame parent) {
        pa = parent;
    }
        
    //Muestra la tabla completa
    public TableModel mostrarDatos(DefaultTableModel modelo, int codigoDetalleEmpleado){
        try{
            st=cn.createStatement();
            String registro = "SELECT D.CODIGO AS Codigo, E.NOMBRE_EMPLEADO||' '||E.APELLIDO_EMPLEADO AS Nombre, d.cantidad " +
                                "FROM "+conectar.user_admin+".COSECHA D " +
                                "inner join "+conectar.user_admin+".empleado E  on(D.CODIGO_EMPLEADO = E.CODIGO_EMPLEADO) " +
                                "where D.CODIGO = '"+codigoDetalleEmpleado+"'order by 2";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "ERROR AL IMPRIMIR TABLA:\n" +e);
        }
        return modelo;
    }   

    //Guarda un nuevo Detalle de empleado
    public void guardarDatos(int codigoDetalleEmpleado,int codigoArea, int codigoEmpleado, float cantidad){
        try{  
            st=cn.createStatement();
            String registro = "INSERT INTO "+conectar.user_admin+".COSECHA (CODIGO, CODIGO_AREA, CODIGO_EMPLEADO, CANTIDAD) " +
                                "VALUES ('"+codigoDetalleEmpleado+"' , '"+codigoArea+"' , '"+codigoEmpleado+"' , '"+cantidad+"')";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "DATOS GUARDADOS");
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "ERROR AL GUARDAR DATOS:\n"+e);
        }
    }
    
    //Elimina un Detalle de empleado
    public void eliminarDatos(int codigoDetalle, int codigoEmpleado){
        try{
            st=cn.createStatement();
            String registro = "DELETE FROM "+conectar.user_admin+".COSECHA WHERE CODIGO = '"+codigoDetalle+"'"
                    + "and CODIGO_EMPLEADO = '"+codigoEmpleado+"'";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "DATO ELIMINADO");
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "ERROR AL ELIMINAR\n" +e);
        }
    }
      
    //Valida si existe el Detalle
    public boolean existeDetalle( int codigoDetalleEmpleado){
        boolean existe=false;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT *  FROM "+conectar.user_admin+".COSECHA D "
                    + "        where D.CODIGO = '"+codigoDetalleEmpleado+"'order by 1";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    existe = true;
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "ERROR AL VALIDAR EXISTENCIA DE DETALLE COSECHA:\n" +e);
        }
        return existe;
    } 
    
    //Devuelve el codigo maximo del Detalle Cosecha
    public int getRegistroMaximo(){
        DefaultTableModel modelo = new DefaultTableModel();
        int registroMaximo = 0;
        try{
            st=cn.createStatement();
            String registro = "select max(codigo)\n" +
                                "from(select c.codigo\n" +
                                "        FROM "+conectar.user_admin+".COSECHA C union all \n" +
                                "    select d.codigo_detalleempleado\n" +
                                "        FROM "+conectar.user_admin+".detalle_empleados D) ";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    if(i==0){
                        registroMaximo = Integer.parseInt(fila[i].toString());
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){

        }
    return registroMaximo;
    }
}
