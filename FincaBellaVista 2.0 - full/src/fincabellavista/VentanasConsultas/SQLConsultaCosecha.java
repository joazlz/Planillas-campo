package fincabellavista.VentanasConsultas;

import clases.ConexionBD;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SQLConsultaCosecha {
    
//---------------------Variables--------------------//
    ConexionBD conectar = new ConexionBD();
    Connection cn = conectar.getConnection();     
    private static Statement st;
    static ResultSet rs;
    private java.awt.Frame pa;//falta metodo constructor
    
    public SQLConsultaCosecha(java.awt.Frame parent) {
        pa = parent;
    }
    
    /**
     * VER CORTE DE UNA SOLA FECHA SEGUN PRODUCTO, TODOS LOS EMPLEADOS 
     * @return Codigo, Mes, Fecha, Jornales, Libras, Area
     */
    public TableModel buscarCorteFecha(String fecha1,String fecha2, int codigoProducto){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT aa.codigo, aa.mes, to_char(aa.fecha,'dd-MM-yyyy') AS FECHA,aa.jornales,aa.libras,aa.area " +
"FROM( " +
"   SELECT " +
"        rac.codigo_registroactividad as codigo, " +
"        TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH') AS MES, " +
"        TO_DATE(rac.fecha_actividad, 'DD-MM-RR') as fecha, " +
"        COUNT(DEMP.CANTIDAD) AS JORNALES, " +
"        sum(demp.cantidad) as libras, " +
"        ar.nombre_area as Area " +
"    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                    INNER JOIN AREA AR ON(AR.CODIGO_AREA = RAC.AREA) " +
"    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"    AND rac.codigo_producto = "+codigoProducto+" " +
"    group by rac.codigo_registroactividad, " +
"             TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH'), " +
"             TO_DATE(rac.fecha_actividad, 'DD-MM-RR'), " +
"             ar.nombre_area " +
"             )AA " +
"    order by to_char(aa.fecha,'MM') ASC, to_char(aa.fecha,'dd') ASC ";
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
                JOptionPane.showMessageDialog(pa, "No Existe Cosecha segun el Producto Seleccionado","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:\n" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    
    
    /**
     * VER CORTE DE UNA SOLA FECHA SEGUN PRODUCTO,TODOS LOS EMPLEADO Y SEGUN AREA 
     * @return codigo, Mes, Fecha, Jornales, libras, Area
     */
    public TableModel buscarCorteFechaSegunArea(String fecha1,String fecha2, int codigoProducto,String nombreArea){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT aa.codigo, aa.mes, to_char(aa.fecha,'dd-MM-yyyy') AS FECHA,aa.JORNALES,aa.libras,aa.area " +
"FROM( " +
    "SELECT " +
"        rac.codigo_registroactividad as codigo," +
"        TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH') AS MES," +
"        TO_DATE(rac.fecha_actividad, 'DD-MM-RR') as fecha, " +
"        COUNT(DEMP.CANTIDAD) AS JORNALES," +
"        sum(demp.cantidad) as libras," +
"        ar.nombre_area as Area" +
"    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                    INNER JOIN AREA AR ON(AR.CODIGO_AREA = RAC.AREA)" +
"    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')" +
"    AND rac.codigo_producto = " + codigoProducto + " " +
"    AND ar.nombre_area='"+nombreArea+"'" +
"    group by rac.codigo_registroactividad," +
"             TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH')," +
"             TO_DATE(rac.fecha_actividad, 'DD-MM-RR')," +
"             ar.nombre_area " +
"             )AA " +
"    order by to_char(aa.fecha,'MM') ASC, to_char(aa.fecha,'dd') ASC ";
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
                JOptionPane.showMessageDialog(pa, "No Existe Cosecha Segun La Fecha Seleccionada","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:\n" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    
    
    public TableModel buscarCorteFechaSegunArea(String fecha1,String fecha2,String nombreArea, int codigoProducto){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT  aa.mes, to_char(aa.fecha,'dd-MM-yyyy') AS FECHA,aa.JORNALES,aa.libras,aa.area " +
"FROM( " +
    "SELECT " +
"        TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH') AS MES," +
"        TO_DATE(rac.fecha_actividad, 'DD-MM-RR') as fecha," +
"        COUNT(DEMP.CANTIDAD) AS JORNALES," +
"        sum(demp.cantidad) as libras," +
"        ar.nombre_area as Area" +
"    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                    INNER JOIN AREA AR ON(AR.CODIGO_AREA = RAC.AREA)" +
"    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')" +
"    AND rac.codigo_producto = " + codigoProducto + " " +
"    AND ar.nombre_area='"+nombreArea+"'" +
"    group by " +
"             TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH')," +
"             TO_DATE(rac.fecha_actividad, 'DD-MM-RR')," +
"             ar.nombre_area " +
"             )AA " +
"    order by to_char(aa.fecha,'MM') ASC, to_char(aa.fecha,'dd') ASC ";
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
                JOptionPane.showMessageDialog(pa, "No Existe Cosecha Segun La Fecha Seleccionada","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:\n" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
        
    /**
     * VER CORTE DE UN RANGO DE FECHAS SEGUN PRODUCTO,SEGUN UN EMPLEADO Y SEGUN TODAS AREA
     * @return Mes, Fecha, Empleado, Libras, Area
     */
    public TableModel buscarCorteSegunTodasArea(String fecha1,String fecha2, int codigoProducto,String nombreEmpleado){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT  aa.mes, to_char(aa.fecha,'dd-MM-yyyy') AS FECHA,AA.EMPLEADO,aa.libras,aa.area " +
"FROM( " +
    "SELECT " +
"        TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH') AS MES," +
"        TO_DATE(rac.fecha_actividad, 'DD-MM-RR') as fecha," +
"        EMP.NOMBRE_EMPLEADO||' '||emp.apellido_empleado AS EMPLEADO," +
"        sum(demp.cantidad) as libras," +
"        ar.nombre_area as Area" +
"    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                    INNER JOIN AREA AR ON(AR.CODIGO_AREA = RAC.AREA)" +
"    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"    AND rac.codigo_producto = "+codigoProducto+"" +
"    AND emp.nombre_empleado||' '||emp.apellido_empleado= '"+nombreEmpleado+"' " +
"    group by TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH')," +
"             TO_DATE(rac.fecha_actividad, 'DD-MM-RR')," +
"             EMP.NOMBRE_EMPLEADO||' '||emp.apellido_empleado," +
"             ar.nombre_area " +
"             )AA " +
"    order by to_char(aa.fecha,'MM') ASC, to_char(aa.fecha,'dd') ASC ";
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
                JOptionPane.showMessageDialog(pa, "No Existe Cosecha Segun El Rango de Fechas y Las Areas Seleccionadas","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    
    /**
     * VER CORTE DE UNA SOLA FECHA SEGUN PRODUCTO,SEGUN UN EMPLEADO Y SEGUN UN AREA
     * @return Mes, Fecha, Empleado, Libras, Area
     **/
    public TableModel buscarCorteSegunEmpleado(String fecha1,String fecha2, int codigoProducto,String nombreArea,String nombreEmpleado){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT  aa.mes, to_char(aa.fecha,'dd-MM-yyyy') AS FECHA,aa.EMPLEADO,aa.libras,aa.area " +
"FROM( " +
    "SELECT " +
"        TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH') AS MES," +
"        TO_DATE(rac.fecha_actividad, 'DD-MM-RR') as fecha," +
"        EMP.NOMBRE_EMPLEADO||' '||emp.apellido_empleado AS EMPLEADO," +
"        sum(demp.cantidad) as libras," +
"        ar.nombre_area as Area" +
"    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO)" +
"                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                    INNER JOIN AREA AR ON(AR.CODIGO_AREA = RAC.AREA)" +
"    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"    AND rac.codigo_producto = " + codigoProducto + " " +
"    AND ar.nombre_area='"+nombreArea+"'" +
"    AND emp.nombre_empleado||' '||emp.apellido_empleado='"+nombreEmpleado+"' " +
"    group by TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH')," +
"             TO_DATE(rac.fecha_actividad, 'DD-MM-RR')," +
"             EMP.NOMBRE_EMPLEADO||' '||emp.apellido_empleado," +
"             ar.nombre_area " +
"             )AA " +
"    order by to_char(aa.fecha,'MM') ASC, to_char(aa.fecha,'dd') ASC ";
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
                JOptionPane.showMessageDialog(pa, "No Existe Cosecha Segun La Fecha Selecionada y Segun El Empleado","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:\n" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    

    /**
     * EMPLEADOS DE CORTE SEGUN CODIGO DE ACTIVIDAD
     * @return Empleado, Cantidad Corte
     */
    public TableModel buscarEmpleadosCodigoActividad(int CodigoRegistroActividad){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO AS Empleado, sum(demp.cantidad) as corte" +
"        FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                        INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"        where rac.codigo_registroactividad = "+CodigoRegistroActividad+" " +
"        group by EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO" +
"        order by 1";
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
                JOptionPane.showMessageDialog(pa, "No Hay Empleados Segun Actividad","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    
    
    /**
     * VER CORTE DE UN RANGO DE FECHAS SEGUN PRODUCTO, TODOS LOS EMPLEADOS, SEGUN AREAS
     * @return Mes, Fecha, Libras, Jornales, Area
     */
    public TableModel buscarCorteSegunProducto(String fecha1,String fecha2, int codigoProducto){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT  aa.mes, to_char(aa.fecha,'dd-MM-yyyy') AS FECHA,aa.JORNALES,aa.libras,aa.area " +
"FROM( " +
    "SELECT " +
"        TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH') AS MES," +
"        TO_DATE(rac.fecha_actividad, 'DD-MM-RR') as fecha," +
"        sum(demp.cantidad) as libras," +
"        COUNT(DEMP.CANTIDAD) AS JORNALES," +
"        ar.nombre_area as Area" +
"    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO)" +
"                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                    INNER JOIN AREA AR ON(AR.CODIGO_AREA = RAC.AREA)" +
"    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')" +
"    AND rac.codigo_producto = " + codigoProducto + " " +
"    group by TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH')," +
"             TO_DATE(rac.fecha_actividad, 'DD-MM-RR')," +
"             ar.nombre_area " +
"             )AA " +
"    order by to_char(aa.fecha,'MM') ASC, to_char(aa.fecha,'dd') ASC ";
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
                JOptionPane.showMessageDialog(pa, "No Existe Cosecha Segun El Rango y El Producto Seleccionado","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:\n" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    /**
     * VER CORTE DE UN RANGO DE FECHAS SEGUN PRODUCTO, TODOS LOS EMPLEADOS, SEGUN TODO EL DIA
     * @return Mes, Fecha, Jornales, Libras
     */
    public TableModel buscarCorteSegunDia(String fecha1,String fecha2, int codigoProducto){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT  aa.mes, to_char(aa.fecha,'dd-MM-yyyy') AS FECHA,aa.JORNALES,aa.libras " +
"FROM( " +
    "SELECT " +
"        TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH') AS MES," +
"        TO_DATE(rac.fecha_actividad, 'DD-MM-RR') as fecha," +
"        COUNT(DEMP.CANTIDAD) AS JORNALES," +
"        sum(demp.cantidad) as libras" +
"    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO)" +
"                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')" +
"    AND rac.codigo_producto = " + codigoProducto + " " +
"    group by TO_CHAR(RAC.FECHA_ACTIVIDAD, 'MONTH')," +
"             TO_DATE(rac.fecha_actividad, 'DD-MM-RR')"
                    + ")aa" +
"    order by to_char(aa.fecha,'MM') ASC, to_char(aa.fecha,'dd') ASC";
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
                JOptionPane.showMessageDialog(pa, "No Existe Cosecha Segun El Dia Seleccionado","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    
    
    /**
     * EMPLEADOS DE CORTE SEGUN UN RANGO DE FECHA
     * @return Empleado, Cantidad Corte
     */
    public TableModel buscarEmpleadosCorteSegunFecha(String fecha1,String fecha2){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO AS Empleado, sum(demp.cantidad) as corte" +
"        FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                        INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"        where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"        group by EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO" +
"        order by 1";
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
                JOptionPane.showMessageDialog(pa, "No Hay Empleados De Cosecha:","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    /**
     * EMPLEADOS DE CORTE SEGUN UN RANGO DE FECHE Y UNA AREA
     * @return Empleado, Cantidad Corte, Cantidad De Dias
     */
    public TableModel buscarEmpleadosCorteSegunFechaArea(String fecha1,String fecha2,String nombreArea){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO AS Empleado, sum(demp.cantidad) as corte,COUNT(demp.cantidad) AS DIAS" +
"        FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO)" +
"                                        INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                        INNER JOIN AREA AR ON(AR.CODIGO_AREA = RAC.AREA)" +
"        where ar.nombre_area = '"+nombreArea+"' " +
"        AND TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"        group by EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO" +
"        order by 1";
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
                JOptionPane.showMessageDialog(pa, "No Hay Empleados Segun Area","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    /**
     * EMPLEADOS DE CORTE SEGUN UNA FECHA ESPECIFICA
     * @return Empleado, Cantidad Corte
     */
    public TableModel buscarEmpleadosFechaEspecifica(String fecha1){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO AS Empleado, sum(demp.cantidad) as corte " +
"        FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                        INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"        where TO_DATE (rac.fecha_actividad , 'DD/MM/RR') = TO_DATE ('"+fecha1+"', 'DD/MM/RR') " +
"        group by EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO " +
"        order by 1";
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
                JOptionPane.showMessageDialog(pa, "No Hay Empleados De Corte","<SQL> COSECHA", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Corte Fecha:" + e,"<SQL> COSECHA", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    
}
