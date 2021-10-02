package BDD;
import clases.ConexionBD;
import java.awt.HeadlessException;
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

public class SQLActividad {
    //---------------------Variables--------------------//
    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection();
    private static Statement st;
    private static ResultSet rs;
    private Icon ico = null;
    private java.awt.Frame pa;
    //------------------------------------------------//

    public SQLActividad(java.awt.Frame parent) {
        pa = parent;
    }
    
    public Icon ico() {
        Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Actividad seleccionado.png")).getImage();
        ImageIcon im = new ImageIcon(imagen);
        return this.ico = new ImageIcon(im.getImage());
    }
    
    //Muestra la tabla completa
    public TableModel mostrarDatos(DefaultTableModel modelo){
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_ACTIVIDAD AS \"Codigo\", NOMBRE_ACTIVIDAD AS \"Actividad\", UTILIZA_QUIMICO AS \"Usa Quimico\" FROM "+conectar.user_admin+".ACTIVIDAD ORDER BY 1";
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
            JOptionPane.showMessageDialog(pa, "Error Al Imprimir Tabla:\n" +e,"<SQL> ACTIVIDADES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }   
    //Busca una actividad por su codigo
    public TableModel buscarDatos(DefaultTableModel modelo, int codigoActividad){
        try{
            st=cn.createStatement();
            String registro = "SELECT CODIGO_ACTIVIDAD AS \"Codigo\", NOMBRE_ACTIVIDAD AS \"Actividad\", UTILIZA_QUIMICO AS \"Usa Quimico\" FROM "+conectar.user_admin+".ACTIVIDAD WHERE CODIGO_ACTIVIDAD = '"+codigoActividad+"'";
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
                JOptionPane.showMessageDialog(pa, "No Existe Actividad","<SQL> ACTIVIDADES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar:\n" + e,"<SQL> ACTIVIDADES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }   
    
    
    //Guarda la actividad en la base de datos
    public void guardarDatos(int codigoActividad, String nombreActividad , char usaQuimico) throws SQLException{
        try{
            st=cn.createStatement();
            String registro = "INSERT INTO "+conectar.user_admin+".ACTIVIDAD (CODIGO_ACTIVIDAD, NOMBRE_ACTIVIDAD, UTILIZA_QUIMICO) VALUES("+codigoActividad+", '"+nombreActividad+"', '"+usaQuimico+"')";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Guardado","<SQL> ACTIVIDADES",JOptionPane.OK_OPTION,ico());
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Ya Existe Una Actividad con ese Codigo:\n "+e,"<SQL> ACTIVIDADES", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Elimina la actividad en la base de datos
    public void eliminarDatos(int codigoActividad){
        try{
            st=cn.createStatement();
            String registro = "DELETE FROM "+conectar.user_admin+".ACTIVIDAD WHERE CODIGO_ACTIVIDAD = '"+codigoActividad+"'";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Eliminado","<SQL> ACTIVIDADES",JOptionPane.OK_OPTION,ico());
        }catch(Exception e){
            JOptionPane.showMessageDialog(pa, "Error Al Eliminar:\n" + e,"<SQL> ACTIVIDADES", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Actualiza la actividad seleccionada
    public void actualizarDatos(int codigoActividad, String nombreNew, int newCodigo, char usaQuimico){
        try{
            if(codigoActividad!=newCodigo){
                JOptionPane.showMessageDialog(pa, "No Puedes Actualizar El Codigo De La Actividad","<SQL> ACTIVIDADES", JOptionPane.ERROR_MESSAGE);
            }else{
                st=cn.createStatement();
                String registro = "UPDATE "+conectar.user_admin+".ACTIVIDAD SET NOMBRE_ACTIVIDAD = '"+nombreNew+"' WHERE CODIGO_ACTIVIDAD = "+codigoActividad+" ";
                st.execute(registro);
                String registro1 = "UPDATE "+conectar.user_admin+".ACTIVIDAD SET UTILIZA_QUIMICO = '"+usaQuimico+"' WHERE CODIGO_ACTIVIDAD = "+codigoActividad+" ";
                st.execute(registro1);
                st.close();
                JOptionPane.showMessageDialog(pa, "Datos Actualizados con Exito","<SQL> ACTIVIDADES",JOptionPane.OK_OPTION,ico());
            }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(pa, "Error Al Actualizar:\n" + e,"<SQL> ACTIVIDADES", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    //Devuelve el nombre de la actividad segun el codigo
    public String getNombreActividad(int codigoActividad){
        DefaultTableModel modelo = new DefaultTableModel();
        String nombreActividad = null;
        try{
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".ACTIVIDAD WHERE CODIGO_ACTIVIDAD = '"+codigoActividad+"'";
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
                        nombreActividad = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Nombre:\n" + e,"<SQL> ACTIVIDADES",JOptionPane.ERROR_MESSAGE);
        }
        return nombreActividad;
    }
    //Devuelve el codigo de la actividad segun el nombre de la actividad
    public int getCodigoActividad(String nombreActividad){
        DefaultTableModel modelo = new DefaultTableModel();
        int codigoActividad = 0;
        try{
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".ACTIVIDAD WHERE NOMBRE_ACTIVIDAD = '"+nombreActividad+"'";
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
                        codigoActividad = Integer.parseInt(fila[i].toString());
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Codigo:\n" + e,"<SQL> ACTIVIDADES",JOptionPane.ERROR_MESSAGE);
        }
        return codigoActividad;
    }
    //Devuelve si la actividad usa quimico
    public boolean getUsaQuimico(int codigoActividad){
        DefaultTableModel modelo = new DefaultTableModel();
        boolean usaQuimico = false;
        try{
            st=cn.createStatement();
            String registro = "SELECT UTILIZA_QUIMICO FROM "+conectar.user_admin+".ACTIVIDAD WHERE CODIGO_ACTIVIDAD = '"+codigoActividad+"'";
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
                        String v = fila[i].toString();
                        if(v.equals("S"))
                            usaQuimico = true;
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Si la Actividad Usa Quimico:\n" + e,"<SQL> ACTIVIDADES",JOptionPane.ERROR_MESSAGE);
        }
        return usaQuimico;
    }


    
    //Devuelve el ultimo registro ingresado
    public int getRegistroMaximo(){
        DefaultTableModel modelo = new DefaultTableModel();
        int registroMaximo = 0;
        try{
            st=cn.createStatement();
            String registro = "select max(CODIGO_ACTIVIDAD) FROM "+conectar.user_admin+".ACTIVIDAD ";
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
    //Valida si existe una actividad con el codigo ingresado
    public boolean existeActividad(int codigoActividad){
        boolean existe = false;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT * FROM "+conectar.user_admin+".ACTIVIDAD WHERE CODIGO_ACTIVIDAD = '"+codigoActividad+"'";
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
            JOptionPane.showMessageDialog(pa, "Error Al Validar Existencia:\n" + e,"<SQL> ACTIVIDADES", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }   
    
}
