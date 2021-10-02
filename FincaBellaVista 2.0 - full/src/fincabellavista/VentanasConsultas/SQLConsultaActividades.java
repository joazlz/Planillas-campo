package fincabellavista.VentanasConsultas;

import clases.ConexionBD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class SQLConsultaActividades {
    
    //---------------------Variables--------------------//
    ConexionBD conectar = new ConexionBD();
    Connection cn = conectar.getConnection();     
    private static Statement st;
    static ResultSet rs;
    private java.awt.Frame pa;
    
    public SQLConsultaActividades(java.awt.Frame parent) {
        pa = parent;
    }
    
    /**    
    * Busqueda de las actividades de un empleado
    * @return Fecha, Actividad , Area, Empleado
    */
    public TableModel buscarActividadesEmpleado(String nombreEmpleado,int codigoProducto,String fecha1, String fecha2){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT to_char(aa.fecha,'dd-mm-yyyy') as fecha ,aa.actividad,aa.area,aa.empleado" +
" FROM(" +
" SELECT rac.fecha_actividad AS FECHA ,act.nombre_actividad As Actividad, ar.nombre_area As Area, emp.nombre_empleado||' '||emp.apellido_empleado as empleado " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area)" +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where emp.nombre_empleado||' '||emp.apellido_empleado = '"+nombreEmpleado+"'  "
                    + "                 and rac.codigo_producto = "+codigoProducto+"" +
"                                    and TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
" UNION ALL " +
" SELECT rac.fecha_actividad AS FECHA ,act.nombre_actividad As Actividad, ar.nombre_area As Area, emp.nombre_empleado||' '||emp.apellido_empleado as empleado " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN COSECHA DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area) " +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where emp.nombre_empleado||' '||emp.apellido_empleado = '"+nombreEmpleado+"' "
                    + "                 and rac.codigo_producto = "+codigoProducto+" "+
"                                    and TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    order by 1,3 " +
")AA " +
"order by TO_CHAR(aa.fecha,'MM')ASC,TO_CHAR(aa.fecha,'dd')ASC ";
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
                JOptionPane.showMessageDialog(pa, "No Existen Actividades de El Empleado","<SQL> ACTIVIDAD", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Actividad:\n" + e,"<SQL> ACTIVIDAD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    

    /**    
    * Busqueda de las actividades de un empleado
     * @return Fecha, Actividad , Area, Empleado
    */
    public TableModel buscarActividadesAreaEmpleado(String nombreEmpleado,int codigoProducto,String fecha1, String fecha2,String Actividad){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT to_char(aa.fecha,'dd-mm-yyyy') as fecha ,aa.actividad,aa.area,aa.empleado" +
" FROM( " +
" SELECT rac.fecha_actividad AS FECHA ,act.nombre_actividad As Actividad, ar.nombre_area As Area, emp.nombre_empleado||' '||emp.apellido_empleado as empleado" +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area)" +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where emp.nombre_empleado||' '||emp.apellido_empleado = '"+nombreEmpleado+"'  "
                    + "              and ACT.nombre_actividad = '"+Actividad+"'" 
                    + "              and rac.codigo_producto = "+codigoProducto+" " +
"                                    and TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')" +
" UNION ALL " +
" SELECT rac.fecha_actividad AS FECHA ,act.nombre_actividad As Actividad, ar.nombre_area As Area, emp.nombre_empleado||' '||emp.apellido_empleado as empleado" +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN COSECHA DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area)" +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where emp.nombre_empleado||' '||emp.apellido_empleado = '"+nombreEmpleado+"' " +
"                                    and ACT.nombre_actividad = '"+Actividad+"' "
                    + "              and TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    order by 1,3 " +
" )AA " +
"order by TO_CHAR(aa.fecha,'MM')ASC,TO_CHAR(aa.fecha,'dd')ASC";
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
                JOptionPane.showMessageDialog(pa, "No Existen Actividades de El Empleado","<SQL> ACTIVIDAD", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Actividad:\n" + e,"<SQL> ACTIVIDAD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    

    /**    
    * Busqueda de las actividades de un empleado
    * @return Fecha, Actividad , Area, Empleado
    */
    public TableModel buscarActividadesEmpleado(String nombreEmpleado,int codigoProducto,String fecha1, String fecha2,String Area){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT to_char(aa.fecha,'dd-mm-yyyy') as fecha ,aa.actividad,aa.area,aa.empleado " +
" FROM( " +
" SELECT rac.fecha_actividad AS FECHA ,act.nombre_actividad As Actividad, ar.nombre_area As Area, emp.nombre_empleado||' '||emp.apellido_empleado as empleado " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area)" +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where emp.nombre_empleado||' '||emp.apellido_empleado = '"+nombreEmpleado+"'  "
                    + "              and ar.nombre_area = '"+Area+"'" + 
                    "              and rac.codigo_producto = "+codigoProducto+" " +
"                                    and TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
" UNION ALL " +
" SELECT rac.fecha_actividad AS FECHA ,act.nombre_actividad As Actividad, ar.nombre_area As Area, emp.nombre_empleado||' '||emp.apellido_empleado as empleado " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN COSECHA DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area)" +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where emp.nombre_empleado||' '||emp.apellido_empleado = '"+nombreEmpleado+"' " +
"                                    and ar.nombre_area = '"+Area+"' "
                    + "              and rac.codigo_producto = "+codigoProducto+" " 
                    + "              and TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    order by 1,3 " +
" )AA " +
"order by TO_CHAR(aa.fecha,'MM')ASC,TO_CHAR(aa.fecha,'dd')ASC";
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
                JOptionPane.showMessageDialog(pa, "No Existen Actividades de El Empleado","<SQL> ACTIVIDAD", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Actividad:\n" + e,"<SQL> ACTIVIDAD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    

    /**    
    * Busqueda de las actividades de un empleado
     * @return Fecha, Actividad , Area, Empleado
    */
    public TableModel buscarActividadesEmpleado(String nombreEmpleado,int codigoProducto,String Actividad,String fecha1, String fecha2,String Area){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = " SELECT to_char(aa.fecha,'dd-mm-yyyy') as fecha ,aa.actividad,aa.area,aa.empleado " +
" FROM( " +
" SELECT rac.fecha_actividad AS FECHA ,act.nombre_actividad As Actividad, ar.nombre_area As Area, emp.nombre_empleado||' '||emp.apellido_empleado as empleado" +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area)" +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where emp.nombre_empleado||' '||emp.apellido_empleado = '"+nombreEmpleado+"'  "
                    + "              and ar.nombre_area = '"+Area+"'"
                    + "              and act.nombre_actividad = '"+Actividad+"' "
                    + "              and rac.codigo_producto = "+codigoProducto+" " +
"                                    and TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
" UNION ALL " +
" SELECT rac.fecha_actividad AS FECHA ,act.nombre_actividad As Actividad, ar.nombre_area As Area, emp.nombre_empleado||' '||emp.apellido_empleado as empleado " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN COSECHA DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area)" +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where emp.nombre_empleado||' '||emp.apellido_empleado = '"+nombreEmpleado+"' " +
"                                    and ar.nombre_area = '"+Area+"'"
                    + "              and rac.codigo_producto = "+codigoProducto+" " 
                    + "              and act.nombre_actividad = '"+Actividad+"'" +
                    "                and TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    order by 1,3" +
" )AA " +
" order by TO_CHAR(aa.fecha,'MM')ASC,TO_CHAR(aa.fecha,'dd')ASC";
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
                JOptionPane.showMessageDialog(pa, "No Existen Actividades de El Empleado","<SQL> ACTIVIDAD", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Actividad:\n" + e,"<SQL> ACTIVIDAD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    
    
    /**
    * todo todo todo
    * @return codigo, fecha, actividad, area, jornales
    */              
    public TableModel buscarTodoTodoTodo(String fecha1, String fecha2,int codigoProducto){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select aa.codigo,to_char(aa.fecha,'dd-mm-yyyy') as fecha,aa.actividad,aa.area,aa.jornales " +
" from ( " +
" SELECT rac.codigo_registroactividad as codigo,rac.fecha_actividad AS FECHA,act.nombre_actividad As Actividad, ar.nombre_area As Area, count(emp.nombre_empleado)as jornales " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO)  " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area) " +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')  " 
                    + "              and rac.codigo_producto = "+codigoProducto+" " 
                    +"               and act.codigo_actividad!=117 "+
"                                    group by rac.codigo_registroactividad,rac.fecha_actividad,act.nombre_actividad, ar.nombre_area " +
" union all " +
"SELECT rac.codigo_registroactividad as codigo,rac.fecha_actividad AS FECHA,act.nombre_actividad As Actividad, ar.nombre_area As Area, count(emp.nombre_empleado)as jornales " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area) " +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    and act.codigo_actividad=117 " +
                    "                and rac.codigo_producto = "+codigoProducto+" "+
"                                    group by rac.codigo_registroactividad,rac.fecha_actividad,act.nombre_actividad, ar.nombre_area" +
" )aa " +
"order by TO_CHAR(aa.fecha,'MM')ASC,TO_CHAR(aa.fecha,'dd')ASC";
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
                JOptionPane.showMessageDialog(pa, "No Existen Actividades","<SQL> ACTIVIDAD", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Actividad:\n" + e,"<SQL> ACTIVIDAD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    
    
    /**
    * todo todo todo
    * @return codigo, fecha, actividad, area, jornales
    */              
    public TableModel buscarTodoTodoActividad(String Actividad,int codigoProducto,String fecha1, String fecha2){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select aa.codigo,to_char(aa.fecha,'dd-mm-yyyy') as fecha,aa.actividad,aa.area,aa.jornales " +
" from ( " +
" SELECT rac.codigo_registroactividad as codigo,rac.fecha_actividad AS FECHA,act.nombre_actividad As Actividad, ar.nombre_area As Area, count(emp.nombre_empleado)as jornales " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO)  " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area) " +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')  " +
"                                    and act.nombre_actividad = '"+Actividad+"' " +
"                                    and act.codigo_actividad!=117 "+
                    "                and rac.codigo_producto = "+codigoProducto+" "+
"                                    group by rac.codigo_registroactividad,rac.fecha_actividad,act.nombre_actividad, ar.nombre_area " +
" union all " +
" SELECT rac.codigo_registroactividad as codigo,rac.fecha_actividad AS FECHA,act.nombre_actividad As Actividad, ar.nombre_area As Area, count(emp.nombre_empleado)as jornales " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area) " +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    and act.nombre_actividad = '"+Actividad+"' " +
"                                    and act.codigo_actividad=117" +
                    "                and rac.codigo_producto = "+codigoProducto+" "+
"                                    group by rac.codigo_registroactividad,rac.fecha_actividad,act.nombre_actividad, ar.nombre_area" +
" )aa " +
"order by TO_CHAR(aa.fecha,'MM')ASC,TO_CHAR(aa.fecha,'dd')ASC";
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
                JOptionPane.showMessageDialog(pa, "No Existen Actividades","<SQL> ACTIVIDAD", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Actividad:\n" + e,"<SQL> ACTIVIDAD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    } 
    
    /**
    * todo area todo 
    * @return codigo, fecha, actividad, area, jornales
    */
    public TableModel buscarTodoAreaTodo(int codigoProducto,String fecha1, String fecha2,String Area){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select aa.codigo,to_char(aa.fecha,'dd-mm-yyyy') as fecha,aa.actividad,aa.area,aa.jornales " +
" from ( " +
" SELECT rac.codigo_registroactividad as codigo,rac.fecha_actividad AS FECHA,act.nombre_actividad As Actividad, ar.nombre_area As Area, count(emp.nombre_empleado)as jornales " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area) " +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    and ar.nombre_area = '"+Area+"' " +
"                                    and act.codigo_actividad!=117 " +
                    "                and rac.codigo_producto = "+codigoProducto+" "+
"                                    group by rac.codigo_registroactividad,rac.fecha_actividad,act.nombre_actividad, ar.nombre_area " +
" union all " +
" SELECT rac.codigo_registroactividad as codigo,rac.fecha_actividad AS FECHA,act.nombre_actividad As Actividad, ar.nombre_area As Area, count(emp.nombre_empleado)as jornales " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)" +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area)" +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad)" +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    and ar.nombre_area = '"+Area+"'" +
"                                    and act.codigo_actividad=117" +
                    "                and rac.codigo_producto = "+codigoProducto+" "+
"                                    group by rac.codigo_registroactividad,rac.fecha_actividad,act.nombre_actividad, ar.nombre_area" +
" )aa " +
"order by TO_CHAR(aa.fecha,'MM')ASC,TO_CHAR(aa.fecha,'dd')ASC";
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
                JOptionPane.showMessageDialog(pa, "No Existen Actividades","<SQL> ACTIVIDAD", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Actividad:\n" + e,"<SQL> ACTIVIDAD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }            
    
    /**
    * todo area actividad 
    * @return codigo, fecha, actividad, area, jornales 
    */
    public TableModel buscarTodoAreaActvidiad(String Actividad,int codigoProducto,String fecha1, String fecha2,String Area){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = " select aa.codigo,to_char(aa.fecha,'dd-mm-yyyy') as fecha,aa.actividad,aa.area,aa.jornales\n" +
" from ( " +
" SELECT rac.codigo_registroactividad as codigo,rac.fecha_actividad AS FECHA,act.nombre_actividad As Actividad, ar.nombre_area As Area, count(emp.nombre_empleado)as jornales\n" +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) \n" +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO)\n" +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area)\n" +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')  " +
"                                    and ar.nombre_area = '"+Area+"' " +
"                                    and act.nombre_actividad = '"+Actividad+"' " +
"                                    and act.codigo_actividad!=117 " +
                    "                and rac.codigo_producto = "+codigoProducto+" "+
"                                    group by rac.codigo_registroactividad,rac.fecha_actividad,act.nombre_actividad, ar.nombre_area " +
" union all " +
" SELECT rac.codigo_registroactividad as codigo,rac.fecha_actividad AS FECHA,act.nombre_actividad As Actividad, ar.nombre_area As Area, count(emp.nombre_empleado)as jornales " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area) " +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    and ar.nombre_area = '"+Area+"' " +
"                                    and act.nombre_actividad = '"+Actividad+"' " +
"                                    and act.codigo_actividad=117 " +
                    "                and rac.codigo_producto = "+codigoProducto+" "+
"                                    group by rac.codigo_registroactividad,rac.fecha_actividad,act.nombre_actividad, ar.nombre_area " +
" )aa " +
"order by TO_CHAR(aa.fecha,'MM')ASC,TO_CHAR(aa.fecha,'dd')ASC";
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
                JOptionPane.showMessageDialog(pa, "No Existen Actividades","<SQL> ACTIVIDAD", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Actividad:\n" + e,"<SQL> ACTIVIDAD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    } 
    
    
    
    /**
     * Listado de empleados trabajado en una actividad
     */
    public TableModel ListaEmpleadosActvidiad(int CodigoActividad,int codigoProducto){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT  " +
"            emp.nombre_empleado||' '||emp.apellido_empleado as Empleado, " +
"            ar.nombre_area As Area " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN AREA  AR ON(rac.area = ar.codigo_area) " +
"                                                                    INNER JOIN ACTIVIDAD ACT ON(rac.codigo_actividad=act.codigo_actividad) " +
"                                    where rac.codigo_registroactividad = "+CodigoActividad+" " +
                    "                and rac.codigo_producto = "+codigoProducto+" ";
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
                JOptionPane.showMessageDialog(pa, "No Existen Actividades","<SQL> ACTIVIDAD", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(Exception e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Actividad:\n" + e,"<SQL> ACTIVIDAD", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
}
