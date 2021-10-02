package BDD;

import clases.ConexionBD;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SQLTipoEmpleado {
    //---------------------Variables--------------------//
    ConexionBD conectar = new ConexionBD();
    Connection cn = conectar.getConnection();    
    private static Statement st;
    static ResultSet rs;
    private Icon ico = null;
    private java.awt.Frame pa;
    //------------------------------------------------//

    public SQLTipoEmpleado(java.awt.Frame parent) {
        pa = parent;
    }
    
    public Icon ico() {
        Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Tipo Empleado Seleccionado.png")).getImage();
        ImageIcon im = new ImageIcon(imagen);
        return this.ico = new ImageIcon(im.getImage());
    }
    
    //Muestra la tabla completa
    public TableModel mostrarDatos(DefaultTableModel modelo){
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_TIPOEMPLEADO AS CODIGO, NOMBRE_TIPOEMPLEADO AS \"TIPO EMPLEADO\" FROM "+conectar.user_admin+".TIPO_EMPLEADO ORDER BY 1";
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
            JOptionPane.showMessageDialog(pa, "Error Al Imprimir Tabla:\n" +e,"<SQL> TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    } 
    //Busca un Tipo de Empleado por su codigo
    public TableModel buscarDatos(DefaultTableModel modelo,int codigoTipoEmpleado){
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_TIPOEMPLEADO AS CODIGO, NOMBRE_TIPOEMPLEADO AS \"TIPO EMPLEADO\" FROM "+conectar.user_admin+".TIPO_EMPLEADO WHERE CODIGO_TIPOEMPLEADO = '"+codigoTipoEmpleado+"'";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            boolean existe = false;
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    if(i==1){
                        existe = true;
                    }
                }
                modelo.addRow(fila);
            }
            if(existe == false){
                JOptionPane.showMessageDialog(pa, "No Existe Tipo de Empleado","<SQL> TIPOS DE EMPLEADOS", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar:\n" + e,"<SQL> TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    
    //Guarda el Tipo de Empleado en la base de datos
    public void guardarDatos(int codigoTipoEmpleado, String nombreTipoEmpleado){
        try{
                st=cn.createStatement();
                String registro = "INSERT INTO "+conectar.user_admin+".TIPO_EMPLEADO (CODIGO_TIPOEMPLEADO, NOMBRE_TIPOEMPLEADO) VALUES("+codigoTipoEmpleado+", '"+nombreTipoEmpleado+"')";
                st.execute(registro);
                JOptionPane.showMessageDialog(pa, "Dato Guardado","<SQL> TIPOS DE EMPLEADOS",JOptionPane.OK_OPTION,ico());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(pa, "Error Ya Existe Un Tipo de Empleado con ese Codigo:\n "+ex,"<SQL> TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
            }
        
    }
    //Elimina el Tipo de Empleado en la base de datos
    public void eliminarDatos(int codigoTipoEmpleado){
        try{
            String registro = "DELETE FROM "+conectar.user_admin+".TIPO_EMPLEADO WHERE CODIGO_TIPOEMPLEADO = '"+codigoTipoEmpleado+"'";
            st.execute(registro);
            System.out.println(registro);
            JOptionPane.showMessageDialog(pa, "Dato Eliminado","<SQL> TIPOS DE EMPLEADOS",JOptionPane.OK_OPTION,ico());
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Eliminar:\n" + e,"<SQL> TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Actualiza el Tipo de Empleado seleccionada
    public void actualizarDatos(int codigoTipoEmpleado, String nombreTipoEmpleadoNew, int newCodigo){
        try{
            if(codigoTipoEmpleado!=newCodigo){
                JOptionPane.showMessageDialog(pa, "No Puedes Actualizar El Codigo Del Tipo de Empleado","<SQL> TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
            }else{
                st=cn.createStatement();
                String registro = " UPDATE "+conectar.user_admin+".TIPO_EMPLEADO SET NOMBRE_TIPOEMPLEADO = '"+nombreTipoEmpleadoNew+"' WHERE CODIGO_TIPOEMPLEADO = "+codigoTipoEmpleado+" ";
                st.execute(registro);
                JOptionPane.showMessageDialog(pa, "Datos Actualizados con Exito","<SQL> TIPOS DE EMPLEADOS",JOptionPane.OK_OPTION,ico());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Actualizar:\n" + e,"<SQL> TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Devuelve el nombre del Tipo de Empleado segun el codigo    
    public String getNombreTipoEmpleado(int codigoTipoEmpleado){
        String nombreTipoEmpleado = null;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT NOMBRE_TIPOEMPLEADO FROM "+conectar.user_admin+".TIPO_EMPLEADO WHERE CODIGO_TIPOEMPLEADO = '"+codigoTipoEmpleado+"'";
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
                        nombreTipoEmpleado = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Nombre:\n" + e,"<SQL> TIPOS DE EMPLEADOS",JOptionPane.ERROR_MESSAGE);
        }
        return nombreTipoEmpleado;       
    }   
    //Devuelve el codigo del Tipo de Empleado segun el nombre del Tipo de Empleado
    public int getCodigoTipoEmpleado(String nombreTipoEmpleado){
        DefaultTableModel modelo = new DefaultTableModel();
        int codigoTipoEmpleado = 0;
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_TIPOEMPLEADO FROM "+conectar.user_admin+".TIPO_EMPLEADO WHERE NOMBRE_TIPOEMPLEADO = '"+nombreTipoEmpleado+"'";
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
                        codigoTipoEmpleado = Integer.parseInt(fila[i].toString());
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Codigo:\n" + e,"<SQL> TIPOS DE EMPLEADOS",JOptionPane.ERROR_MESSAGE);
        }
        return codigoTipoEmpleado;
    }
    
    //Devuelve el ultimo registro ingresado
    public int getRegistroMaximo(){
        DefaultTableModel modelo = new DefaultTableModel();
        int registroMaximo = 0;
        try{
            st=cn.createStatement();
            String registro = "select max(CODIGO_TIPOEMPLEADO) FROM "+conectar.user_admin+".TIPO_EMPLEADO ";
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
    //Valida si existe un Tipo de Empleado con el codigo ingresado
    public boolean existeTipoEmpleado(int codigoTipoEmpleado){
        boolean existe = false;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".TIPO_EMPLEADO WHERE CODIGO_TIPOEMPLEADO = '"+codigoTipoEmpleado+"'";
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
                    if(i==1){
                        existe = true;
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Validar Existencia:\n" + e,"<SQL> TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }
    
}
