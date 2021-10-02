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

public class SQLMedidaDerivada {
    //---------------------Variables--------------------//
    ConexionBD conectar = new ConexionBD();
    Connection cn = conectar.getConnection();      
    private static Statement st;
    static ResultSet rs;
    private Icon ico = null;
    private java.awt.Frame pa;
    //------------------------------------------------//

    public SQLMedidaDerivada(java.awt.Frame parent) {
        pa = parent;
    }
    
    public Icon ico() {
        Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Medida Derivada Seleccionado.png")).getImage();
        ImageIcon im = new ImageIcon(imagen);
        return this.ico = new ImageIcon(im.getImage());
    }
        
    //Muestra la tabla completa
    public TableModel mostrarDatos(DefaultTableModel modelo){
        try{
            st=cn.createStatement();
            String registro = "select md.codigo_medidaderivada as Codigo, md.nombre_medidaderivada as Nombre,mb.nombre_medidabasica as \"Medida Basica\" "
                    + "         FROM "+conectar.user_admin+".tipo_medidaderivada md INNER JOIN "+conectar.user_admin+".tipo_medidabasica mb on (md.codigo_medidabasica = mb.codigo_medidabasica) "
                    + "         order by 1 asc";
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
            JOptionPane.showMessageDialog(pa, "Error Al Imprimir Tabla:\n" +e,"<SQL> MEDIDAS DERIVADAS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    //Busca una Medida Derivada segun su codigo
    public TableModel buscarDatos(DefaultTableModel modelo, int codigoMedidaDerivada){
        try{
            st=cn.createStatement();
            String registro = "select md.codigo_medidaderivada as Codigo, md.nombre_medidaderivada as Nombre, mb.nombre_medidabasica as \"Medida Basica\" "
                    + "         FROM "+conectar.user_admin+".tipo_medidaderivada md INNER JOIN "+conectar.user_admin+".tipo_medidabasica mb on (md.codigo_medidabasica = mb.codigo_medidabasica) "
                    + "         WHERE CODIGO_MEDIDADERIVADA = '"+codigoMedidaDerivada+"'";
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
            rs.close();
            if(existe == false){
                JOptionPane.showMessageDialog(pa, "No Existe Medida Derivada","<SQL> MEDIDAS DERIVADAS", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar:\n" + e,"<SQL> MEDIDAS DERIVADAS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    //Busca una Medida Derivada segun su codigo con Medida Basica 
    public TableModel buscarPorMedidaBasica(DefaultTableModel modelo, int codigoMedidaBasica){
        try{
            st=cn.createStatement();
            String registro = "select md.codigo_medidaderivada as Codigo, md.nombre_medidaderivada as Nombre, mb.nombre_medidabasica as \"Medida Basica\" "
                    + "             FROM "+conectar.user_admin+".tipo_medidaderivada md INNER JOIN "+conectar.user_admin+".tipo_medidabasica mb on (md.codigo_medidabasica = mb.codigo_medidabasica) "
                    + "             WHERE mb.codigo_medidabasica = '"+codigoMedidaBasica+"' order by codigo_medidaderivada asc";
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
                    if(i==1)
                        existe=true;
                }
                modelo.addRow(fila);
            }
            rs.close();
            if(existe == false){
                JOptionPane.showMessageDialog(pa, "No Existes Medida Derivada","<SQL> MEDIDAS DERIVADAS", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Por Medida Basica:\n" + e,"<SQL> MEDIDAS DERIVADAS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }


    
    
    //Guarda una nueva Medida Derivada
    public void guardarDatos(int codigoMedidaDerivada, String nombreMedidaDerivada, int medidaBasica) throws SQLException{
        try{  
            st = cn.createStatement();
            String registro = "INSERT INTO TIPO_MEDIDADERIVADA (CODIGO_MEDIDADERIVADA, NOMBRE_MEDIDADERIVADA, CODIGO_MEDIDABASICA) VALUES("+codigoMedidaDerivada+", '"+nombreMedidaDerivada+"', "+medidaBasica+")";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Guardado","<SQL> MEDIDAS DERIVADAS",JOptionPane.OK_OPTION,ico());
        }catch(java.sql.SQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog(pa, "Error Ya Existe Una Medida Derivada con ese Codigo:\n "+e,"<SQL> MEDIDAS DERIVADAS", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Elimina una Medida Derivada
    public void eliminarDatos(int codigoMedidaDerivada){
        try{
            String registro = "DELETE FROM "+conectar.user_admin+".TIPO_MEDIDADERIVADA WHERE CODIGO_MEDIDADERIVADA = '"+codigoMedidaDerivada+"'";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Eliminado","<SQL> MEDIDAS DERIVADAS",JOptionPane.OK_OPTION,ico());
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Eliminar:\n" + e,"<SQL> MEDIDAS DERIVADAS", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Actualiza una Medida Derivada segun su codigo
    public void actualizarDatos(int codigoMedidaDerivada, String nombreMedidaDerivadaNew, int codigoMedidaBasicaNew){
        try{
            st = cn.createStatement();
            String registro = " UPDATE TIPO_MEDIDADERIVADA SET NOMBRE_MEDIDADERIVADA = '"+nombreMedidaDerivadaNew+"' WHERE CODIGO_MEDIDADERIVADA = "+codigoMedidaDerivada+" ";
            st.execute(registro);
            String registro1 = " UPDATE TIPO_MEDIDADERIVADA SET CODIGO_MEDIDABASICA = "+codigoMedidaBasicaNew+" WHERE CODIGO_MEDIDADERIVADA = "+codigoMedidaDerivada+" ";
            st.execute(registro1);
            JOptionPane.showMessageDialog(pa, "Datos Actualizados con Exito","<SQL> MEDIDAS DERIVADAS",JOptionPane.OK_OPTION,ico());        
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Actualizar:\n" + e,"<SQL> MEDIDAS DERIVADAS", JOptionPane.ERROR_MESSAGE);
        }
    }


      
    
    //Devuelve el nombre de una Medida Derivada segun el codigo
    public String getNombreMedidaDerivada(int codigoMedidaDerivada){
        String nombreMedidaDerivada = null;
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT NOMBRE_MEDIDADERIVADA FROM "+conectar.user_admin+".TIPO_MEDIDADERIVADA WHERE CODIGO_MEDIDADERIVADA = '"+codigoMedidaDerivada+"'";
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
                        nombreMedidaDerivada = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Nombre:\n" + e,"<SQL> MEDIDAS DERIVADAS",JOptionPane.ERROR_MESSAGE);
        }
        return nombreMedidaDerivada;
    }
    //Devuelve el codigo de una Medida Basica segun el nombre
    public int getCodigoMedidaBasica(int codigoMedidaDerivada){
        int codigoMedidaBasica = 0;
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_MEDIDABASICA FROM "+conectar.user_admin+".TIPO_MEDIDADERIVADA WHERE CODIGO_MEDIDADERIVADA = '"+codigoMedidaDerivada+"'";
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
                        codigoMedidaBasica = Integer.parseInt(fila[i].toString()) -1;
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Codigo:\n" + e,"<SQL> MEDIDAS DERIVADAS",JOptionPane.ERROR_MESSAGE);
        }
        return codigoMedidaBasica;
    }    
    //Devuelve el codigo de una Medida Derivada segun el nombre
    public int getCodigoMedidaDerivada(String nombreMedidaDerivada){
        int codigoMedidaBasica = 0;
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_MEDIDADERIVADA FROM "+conectar.user_admin+".TIPO_MEDIDADERIVADA WHERE NOMBRE_MEDIDADERIVADA = '"+nombreMedidaDerivada+"'";
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
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Codigo Medida Basica:\n" + e,"<SQL> MEDIDAS DERIVADAS",JOptionPane.ERROR_MESSAGE);
        }
        return codigoMedidaBasica;
    }
    

    
    //Devuelve el ultimo codigo ingresado
    public int getRegistroMaximo(){
        DefaultTableModel modelo = new DefaultTableModel();
        int registroMaximo = 0;
        try{
            st=cn.createStatement();
            String registro = "select max(codigo_medidaderivada) FROM "+conectar.user_admin+".TIPO_MEDIDADERIVADA";
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
    //Valida si existe una Medida Derivada
    public boolean existeMedidaDerivada(int codigoMedidaDerivada){
        boolean existe = false;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".TIPO_MEDIDADERIVADA WHERE CODIGO_MEDIDADERIVADA = '"+codigoMedidaDerivada+"'";
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
            JOptionPane.showMessageDialog(pa, "Error Al Validar Existencia:\n" + e,"<SQL> MEDIDAS DERIVADAS", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }  
}
