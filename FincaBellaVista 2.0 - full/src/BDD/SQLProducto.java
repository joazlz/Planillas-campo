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

public class SQLProducto {
    //---------------------Variables--------------------//
    ConexionBD conectar = new ConexionBD();
    Connection cn = conectar.getConnection();
    private static Statement st;
    static ResultSet rs;
    private Icon ico = null;
    private java.awt.Frame pa;
    //------------------------------------------------//

    public SQLProducto(java.awt.Frame parent) {
        pa = parent;
    }

    public Icon ico() {
        Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Grupo Bodega Seleccionado.png")).getImage();
        ImageIcon im = new ImageIcon(imagen);
        return this.ico = new ImageIcon(im.getImage());
    }
    
    //Muestra la tabla completa
    public TableModel mostrarDatos(DefaultTableModel modelo){
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO, NOMBRE, PRECIO_CORTE AS PRECIO FROM "+conectar.user_admin+".PRODUCTO order by 1";
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
            JOptionPane.showMessageDialog(pa, "Error Al Imprimir Tabla:\n" +e,"<SQL> PRODUCTOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    //Busca un Grupo de bodega segun su codigo
    public TableModel buscarDatos(DefaultTableModel modelo, int codigoProducto){
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO, NOMBRE,PRECIO_CORTE AS PRECIO FROM "+conectar.user_admin+".PRODUCTO WHERE CODIGO = '"+codigoProducto+"'";
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
                JOptionPane.showMessageDialog(pa, "No Existe El Producto","<SQL> PRODUCTOS", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar:\n" + e,"<SQL> PRODUCTOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    
    //Guarda un nuevo Grupo de bodega
    public void guardarDatos(int codigoProducto, String nombreProducto, int precioProducto ) throws SQLException{
        try{
            st=cn.createStatement();
            String registro = "INSERT INTO "+conectar.user_admin+".PRODUCTO (CODIGO, NOMBRE, PRECIO_CORTE) VALUES("+codigoProducto+", '"+nombreProducto+"', "+precioProducto+")";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Guardado","<SQL> PRODUCTOS",JOptionPane.OK_OPTION,ico());
        }catch(java.sql.SQLIntegrityConstraintViolationException e){            
            JOptionPane.showMessageDialog(pa, "Error Ya Existe Un Producto con ese Codigo:\n "+e,"<SQL> PRODUCTOS", JOptionPane.ERROR_MESSAGE);
        }
    }    
    //Elimina un nuevo Grupo de bodega
    public void eliminarDatos(int codigoProducto){
        try{
            st=cn.createStatement();
            String registro = "DELETE FROM "+conectar.user_admin+".PRODUCTO WHERE CODIGO = '"+codigoProducto+"'";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Eliminado","<SQL> PRODUCTOS",JOptionPane.OK_OPTION,ico());
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Eliminar:\n" + e,"<SQL> PRODUCTOS", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Actualiza un Grupo de bodega segun su codigo
    public void actualizarDatos(int codigoProducto, String nombreNew, int codigoProductoNew, int precioProductonew){
        try{
            if(codigoProducto!=codigoProductoNew){
                JOptionPane.showMessageDialog(pa, "No Puedes Actualizar El Codigo Del Producto ","<SQL> PRODUCTOS", JOptionPane.ERROR_MESSAGE);
            }else{
                st=cn.createStatement();
                String registro = " UPDATE "+conectar.user_admin+".PRODUCTO SET NOMBRE = '"+nombreNew+"' WHERE CODIGO = "+codigoProducto+" ";
                st.execute(registro);
                String registro1 = " UPDATE "+conectar.user_admin+".PRODUCTO SET PRECIO_CORTE = '"+precioProductonew+"' WHERE CODIGO = "+codigoProducto+" ";
                st.execute(registro1);
                JOptionPane.showMessageDialog(pa, "Datos Actualizados con Exito","<SQL> PRODUCTOS",JOptionPane.OK_OPTION,ico());
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Actualizar:\n" + e,"<SQL> PRODUCTOS", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     
    //Devuelve el nombre de un Producto segun el codigo
    public String getNombreProducto(int codigoProducto){
        DefaultTableModel modelo = new DefaultTableModel();
        String nombreProducto = null;
        try{
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".PRODUCTO WHERE CODIGO = '"+codigoProducto+"'";
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
                        nombreProducto = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Nombre:\n" + e,"<SQL> PRODUCTOS",JOptionPane.ERROR_MESSAGE);
        }
        return nombreProducto;
    }
    //Devuelve el codigo de un Producot segun el nombre
    public int getCodigoProducto(String nombreProducto){
        DefaultTableModel modelo = new DefaultTableModel();
        int codigoProducto = 0;
        try{
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".PRODUCTO WHERE NOMBRE = '"+nombreProducto+"'";
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
                        codigoProducto = Integer.parseInt(fila[i].toString());
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Codigo:\n" + e,"<SQL> PRODUCTOS",JOptionPane.ERROR_MESSAGE);
        }
        return codigoProducto;
    }

    
    //Valida si existe un Producto
    public boolean existeProducto(int codigoProducto){
        boolean existe = false;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".PRODUCTO WHERE CODIGO = '"+codigoProducto+"'";
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
            JOptionPane.showMessageDialog(pa, "Error Al Validar Existencia:\n" + e,"<SQL> PRODUCTOS", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }
    //Devuelve el ultimo codigo ingresado
    public int getRegistroMaximo(){
        DefaultTableModel modelo = new DefaultTableModel();
        int registroMaximo = 0;
        try{
            st=cn.createStatement();
            String registro = "select max(CODIGO) FROM "+conectar.user_admin+".PRODUCTO ";
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
