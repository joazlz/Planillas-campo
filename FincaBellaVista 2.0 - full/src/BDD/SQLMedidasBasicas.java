package BDD;

import clases.ConexionBD;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SQLMedidasBasicas {
    //---------------------Variables--------------------//
    ConexionBD conectar = new ConexionBD();
    Connection cn = conectar.getConnection();
    private static Statement st;
    private static ResultSet rs;
    private Icon ico = null;
    private java.awt.Frame pa;
    //------------------------------------------------//

    public SQLMedidasBasicas(java.awt.Frame parent) {
        pa = parent;
    }
    
    public Icon ico() {
        Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Medida Basica Seleccionado.png")).getImage();
        ImageIcon im = new ImageIcon(imagen);
        return this.ico = new ImageIcon(im.getImage());
    }
    //Muestra la tabla completa
    public TableModel mostrarDatos(DefaultTableModel modelo){
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_MEDIDABASICA AS CODIGO, NOMBRE_MEDIDABASICA AS NOMBRE FROM "+conectar.user_admin+".TIPO_MEDIDABASICA ORDER BY 1 ASC";
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
            JOptionPane.showMessageDialog(pa, "Error Al Imprimir Tabla:\n" +e,"<SQL> MEDIDAS BASICAS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }   
    //Busca una medida basica segun su codigo
    public TableModel buscarDatos(DefaultTableModel modelo, int codigoMedidaBasica){
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_MEDIDABASICA AS CODIGO, NOMBRE_MEDIDABASICA AS NOMBRE FROM "+conectar.user_admin+".TIPO_MEDIDABASICA WHERE CODIGO_MEDIDABASICA = '"+codigoMedidaBasica+"' ORDER BY 1 ASC";
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
                JOptionPane.showMessageDialog(pa, "No Existe Actividad","<SQL> MEDIDAS BASICAS", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar:\n" + e,"<SQL> MEDIDAS BASICAS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    
    //Guarda una nueva Medida Basica
    public void guardarDatos(int codigoMedidaBasica, String nombreMedidaBasica) throws SQLException{
        try{  
            st=cn.createStatement();
            String registro = "INSERT INTO TIPO_MEDIDABASICA (CODIGO_MEDIDABASICA, NOMBRE_MEDIDABASICA) VALUES("+codigoMedidaBasica+", '"+nombreMedidaBasica+"')";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Guardado","<SQL> MEDIDAS BASICAS",JOptionPane.OK_OPTION,ico());
        }catch(java.sql.SQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog(pa, "Error Ya Existe Una Actividad con ese Codigo:\n "+e,"<SQL> MEDIDAS BASICAS", JOptionPane.ERROR_MESSAGE);
        }
    } 
    //Elimina una Medida Basica
    public void eliminarDatos(int codigoMedidaBasica){
        try{
            st=cn.createStatement();
            String registro = "DELETE FROM "+conectar.user_admin+".TIPO_MEDIDABASICA WHERE CODIGO_MEDIDABASICA = '"+codigoMedidaBasica+"'";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Eliminado","<SQL> MEDIDAS BASICAS",JOptionPane.OK_OPTION,ico());
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Eliminar:\n" + e,"<SQL> MEDIDAS BASICAS", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Actualiza una medida basica segun su codigo
    public void actualizarDatos(int codigoMedidaBasica, String nombreMedidaBasicaNew, int newCodigo){
        try{
            if(codigoMedidaBasica!=newCodigo){
                JOptionPane.showMessageDialog(pa, "No Puedes Actualizar El Codigo De La Actividad","<SQL> MEDIDAS BASICAS", JOptionPane.ERROR_MESSAGE);
            }else{
                st=cn.createStatement();
                String registro = " UPDATE TIPO_MEDIDABASICA SET NOMBRE_MEDIDABASICA = '"+nombreMedidaBasicaNew+"' WHERE CODIGO_MEDIDABASICA = "+codigoMedidaBasica+" ";
                st.execute(registro);
                JOptionPane.showMessageDialog(pa, "Datos Actualizados con Exito","<SQL> MEDIDAS BASICAS",JOptionPane.OK_OPTION,ico());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Actualizar:\n" + e,"<SQL> MEDIDAS BASICAS", JOptionPane.ERROR_MESSAGE);
        }
    }  


    //Devuelve el nombre de una medida basica segun el codigo
    public String getNombreMedidaBasica(int codigoMedidaBasica){
        String nombreMedidaBasica = null;
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT NOMBRE_MEDIDABASICA FROM "+conectar.user_admin+".TIPO_MEDIDABASICA WHERE CODIGO_MEDIDABASICA  = '"+codigoMedidaBasica+"'";
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
                        nombreMedidaBasica = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Nombre:\n" + e,"<SQL> MEDIDAS BASICAS",JOptionPane.ERROR_MESSAGE);
        }
        return nombreMedidaBasica; 
    }
    //Devuelve el codigo de una medida basica segun el nombre
    public int getCodigoMedidaBasica(String nombreMedidaBasica){
        int codigoMedidaBasica = 0;
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_MEDIDABASICA FROM "+conectar.user_admin+".TIPO_MEDIDABASICA WHERE NOMBRE_MEDIDABASICA = '"+nombreMedidaBasica+"'";
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
                        codigoMedidaBasica = Integer.parseInt(fila[i].toString());
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Codigo:\n" + e,"<SQL> MEDIDAS BASICAS",JOptionPane.ERROR_MESSAGE);
        }
        
        return codigoMedidaBasica;
    }
    
    
    //Devuelve el ultimo codigo ingresado
    public int getRegistroMaximo(){
        DefaultTableModel modelo = new DefaultTableModel();
        int registroMaximo = 0;
        try{
            st=cn.createStatement();
            String registro = "select max(CODIGO_MEDIDABASICA) FROM "+conectar.user_admin+".TIPO_MEDIDABASICA";
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
    //Valida si existe una medida basica
    public boolean existeMedidaBasica(int codigoMedidaBasica){
        boolean existe = false;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".TIPO_MEDIDABASICA WHERE CODIGO_MEDIDABASICA = '"+codigoMedidaBasica+"'";
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
            JOptionPane.showMessageDialog(pa, "Error Al Validar Existencia:\n" + e,"<SQL> MEDIDAS BASICAS", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }
    
}
