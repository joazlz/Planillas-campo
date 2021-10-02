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

public class SQLArea {
    //-------------------Variables------------------//
    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection();
    private static Statement st;
    static ResultSet rs;
    private Icon ico = null;
    private java.awt.Frame pa;
    //------------------------------------------------//

    public Icon ico() {
        Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Area Seleccionado.png")).getImage();
        ImageIcon im = new ImageIcon(imagen);
        return this.ico = new ImageIcon(im.getImage());
    }
    public SQLArea(java.awt.Frame parent) {
        pa = parent;
    }
    
    //Muestra la tabla completa
    public TableModel mostrarDatos(DefaultTableModel modelo){
        try{
            st=cn.createStatement();
            String registro = "SELECT a.codigo_area as \"Codigo\", a.nombre_area as \"Nombre\", a.cantidadplantas_area as \"Cantidad plantas\", a.tamanio_area as \"Tamaño\", md.nombre_medidaderivada as \"Medida\" "
                    + "             FROM "+conectar.user_admin+".AREA a INNER JOIN "+conectar.user_admin+".tipo_medidaderivada md on (a.ALTITUDMEDIDA_AREA = md.CODIGO_MEDIDADERIVADA) "
                    + "             order by 1 asc";
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
            JOptionPane.showMessageDialog(pa,"Error Al Imprimir Tabla:\n" +e,"<SQL> AREAS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    //Busca un area segun su codigo
    public TableModel buscarDatos(int codigoArea,DefaultTableModel modelo){
         try{
            st=cn.createStatement();
            String registro = "SELECT a.codigo_area as \"Codigo\", a.nombre_area as \"Nombre\", a.cantidadplantas_area as \"Cantidad plantas\", a.tamanio_area as \"Tamaño\", md.nombre_medidaderivada as \"Medida\" "
                    + "         FROM "+conectar.user_admin+".AREA a INNER JOIN "+conectar.user_admin+".tipo_medidaderivada md on (a.ALTITUDMEDIDA_AREA = md.CODIGO_MEDIDADERIVADA) WHERE CODIGO_AREA = '"+codigoArea+"'";
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
                JOptionPane.showMessageDialog(pa, "No Existe Area","<SQL> AREAS", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar:\n" + e,"<SQL> AREAS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    


    //Guarda un Area nueva
    public void guardarDatos(int codigoArea, String nombreArea, int altitudArea, int cantidadPlantas, int altitudMedida) throws SQLException{
        try{  
            String registro = "INSERT INTO "+conectar.user_admin+".AREA (CODIGO_AREA, NOMBRE_AREA, tamanio_AREA, CANTIDADPLANTAS_AREA, ALTITUDMEDIDA_AREA) "
                    + "         VALUES("+codigoArea+", '"+nombreArea+"', "+altitudArea+", "+cantidadPlantas+", "+altitudMedida+")";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Guardado","<SQL> AREAS",JOptionPane.OK_OPTION,ico());
        }catch(java.sql.SQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog(pa, "Error Ya Existe Un Area con ese Codigo:\n "+e,"<SQL> AREAS", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Elimina un Area Existente
    public void eliminarDatos(int codigoArea){
        try{
            String registro = "DELETE FROM "+conectar.user_admin+".AREA WHERE CODIGO_AREA = '"+codigoArea+"'";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Eliminado","<SQL> AREAS",JOptionPane.OK_OPTION,ico());
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Eliminar:\n" + e,"<SQL> AREAS",JOptionPane.ERROR_MESSAGE);
        }
    }
    //Actualiza los datos de una area
    public void actualizarDatos(int codigoArea,String nombreNew,int cantidadPlantasNew,int altitudNew, int codigoMedidaNew){
        try{
            String registro = " UPDATE "+conectar.user_admin+".AREA SET NOMBRE_AREA = '"+nombreNew+"' WHERE CODIGO_AREA = "+codigoArea+" ";
            st.execute(registro);
            String registro1 = " UPDATE "+conectar.user_admin+".AREA SET tamanio_AREA = "+altitudNew+" WHERE CODIGO_AREA = "+codigoArea+" ";
            st.execute(registro1);
            String registro2 = " UPDATE "+conectar.user_admin+".AREA SET CANTIDADPLANTAS_AREA = "+cantidadPlantasNew+" WHERE CODIGO_AREA = "+codigoArea+" ";
            st.execute(registro2);
            String registro3 = " UPDATE "+conectar.user_admin+".AREA SET ALTITUDMEDIDA_AREA = "+codigoMedidaNew+" WHERE CODIGO_AREA = "+codigoArea+" ";
            st.execute(registro3);
            JOptionPane.showMessageDialog(pa, "Dato Actualizado","<SQL> AREAS",JOptionPane.OK_OPTION,ico());
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Actualizar: \n" + e,"<SQL> AREAS",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    //Devuelve el nombre de un Area
    public String getNombreArea(int codigoArea){
        String nombreArea = null;
        boolean existe;
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT NOMBRE_AREA FROM "+conectar.user_admin+".AREA WHERE CODIGO_AREA = '"+codigoArea+"'";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            existe = false;
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    if(i==0){
                        nombreArea = fila[i].toString();
                        existe = true;
                    }
                }
                modelo.addRow(fila);
            }
            if(existe == false){
                
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Nombre:\n" + e,"<SQL> AREAS",JOptionPane.ERROR_MESSAGE);
        }
        return nombreArea;       
    }    
    //Devuelve el codigo de un Area
    public int getCodigoArea(String nombreArea){
        int codigoArea = 0;
        boolean existe;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT CODIGO_AREA FROM "+conectar.user_admin+".AREA WHERE  NOMBRE_AREA = '"+nombreArea+"'";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            existe = false;
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    if(i==0){
                        codigoArea = Integer.parseInt(fila[i].toString());
                        existe = true;
                    }
                }
                modelo.addRow(fila);
            }
            if(existe == false){
                
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Codigo De Area:\n" + e,"<SQL> AREAS",JOptionPane.ERROR_MESSAGE);
        }
        return codigoArea;       
    }
    //Devuelve la cantidad de plantas de un area
    public int getCantidadPlantasArea(int codigoArea){
        int cantidadPlantasArea = 0;
        boolean existe;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT CANTIDADPLANTAS_AREA FROM "+conectar.user_admin+".AREA WHERE CODIGO_AREA = '"+codigoArea+"'";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            existe = false;
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    if(i==0){
                        cantidadPlantasArea = Integer.parseInt(fila[i].toString());
                        existe = true;
                    }
                }
                modelo.addRow(fila);
            }
            if(existe == false){
                
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Cantidad De Plantas:\n" + e,"<SQL> AREAS",JOptionPane.ERROR_MESSAGE);
        }
        return cantidadPlantasArea;       
    }
    //Devuelve la altitud del Area
    public int getTamanio(int codigoArea){
        int altitudArea = 0;
        boolean existe;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT Tamanio_AREA FROM "+conectar.user_admin+".AREA WHERE CODIGO_AREA = '"+codigoArea+"'";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            existe = false;
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    if(i==0){
                        altitudArea = Integer.parseInt(fila[i].toString());
                        existe = true;
                    }
                }
                modelo.addRow(fila);
            }
            if(existe == false){
                
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver La Tamanio:\n" + e,"<SQL> AREAS",JOptionPane.ERROR_MESSAGE);
        }
        return altitudArea;  
    }
    


    //Devuelve el codigo de la ultima area agregada
    public int getRegistroMaximo(){
        DefaultTableModel modelo = new DefaultTableModel();
        int registroMaximo = 0;
        try{
            st=cn.createStatement();
            String registro = "select max(CODIGO_AREA) FROM "+conectar.user_admin+".AREA ";
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
    //Verifica si existe un Area
    public boolean existeArea(int codigoArea){
        boolean existe = false;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".AREA WHERE CODIGO_AREA = '"+codigoArea+"'";
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
            JOptionPane.showMessageDialog(pa, "Error Al Validar Existencia:\n" + e,"<SQL> AREAS",JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }
}