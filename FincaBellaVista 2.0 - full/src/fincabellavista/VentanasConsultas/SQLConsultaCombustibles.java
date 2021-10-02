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


public class SQLConsultaCombustibles {
    
    //---------------------Variables--------------------//
    ConexionBD conectar = new ConexionBD();
    Connection cn = conectar.getConnection();     
    private static Statement st;
    static ResultSet rs;
    private java.awt.Frame pa;
    
    public SQLConsultaCombustibles(java.awt.Frame parent) {
        pa = parent;
    }
    
    /**    
    * ENTRADA DE COMBUSTIBLE segun rango de fecha
    * @return 
    */
    public TableModel EntradasDeCombustible(String fecha1,String fecha2){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro ="select to_char(aa.fecha,'dd/mm/yyyy') as fecha ,aa.cantidad,aa.combustible "
                    + "from("
                    + "select DISTINCT " +
"to_date(compr.fecha_compra,'DD/MM/RR') as fecha, " +
"compr.cantidad_producto as cantidad, " +
"comb.nombre_tipocombustible as combustible " +
"from compra compr " +
"         inner join tipo_combustible comb on(compr.codigo_producto=comb.codigo_tipocombustible) " +
"         inner join salida_combustible sali on(sali.tipo_combustible=comb.codigo_tipocombustible)  " +
"where trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR'))aa "+
"ORDER BY to_char(aa.fecha,'mm')desc,to_char(aa.fecha,'dd')desc ";
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
                JOptionPane.showMessageDialog(pa, "No Existen Entradas de Combustible","<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Entradas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    

    /**    
    * ENTRADA DE COMBUSTIBLE segun rango de fecha y un tipo de combustible
    * @return 
    */
    public TableModel EntradasDeCombustible(String fecha1,String fecha2, String Combustible){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro ="select to_char(aa.fecha,'dd/mm/yyyy') as fecha,aa.cantidad,aa.combustible "
                    + "from( "
                    + "select DISTINCT " +
"to_date(compr.fecha_compra,'DD/MM/RR') as fecha, " +
"compr.cantidad_producto as cantidad, " +
"comb.nombre_tipocombustible as combustible " +
"from compra compr " +
"         inner join tipo_combustible comb on(compr.codigo_producto=comb.codigo_tipocombustible) " +
"         inner join salida_combustible sali on(sali.tipo_combustible=comb.codigo_tipocombustible)  " +
"where trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+Combustible+"' " +
"or  trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+Combustible+"')aa " +
"ORDER BY to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Entradas de Combustible para \n "+Combustible,"<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Entradas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    

    
    
    
    /**    
    * SALIDAS DE COMBUSTIBLE segun rango de fecha
    * @return 
    */
    public TableModel SalidasDeCombustible(String fecha1,String fecha2){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro ="select to_char(aa.fecha,'dd/mm/yyyy') as fecha,aa.cantidad,aa.combustible,aa.maquina,aa.conductor "
                    + "from( "
                    + "select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') )aa " +
"order by to_char(aa.fecha,'mm')desc,to_char(aa.fecha,'dd')desc ";
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
                JOptionPane.showMessageDialog(pa, "No Existen Salidas de Combustible","<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }    
    
    /**    
    * SALIDAS DE COMBUSTIBLE segun rango de fecha y un tipo de combustible
    * @return 
    */
    public TableModel SalidasDeCombustible(String fecha1,String fecha2, String Combustible){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro ="select to_char(aa.fecha,'dd/mm/yyyy') as fecha,aa.cantidad,aa.combustible,aa.maquina,aa.conductor "
                    + "from( "
                    + "select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"and comb.nombre_tipocombustible = '"+Combustible+"')aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Salidas de Combustible para \n"+Combustible,"<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    /**    
    * SALIDAS DE COMBUSTIBLE segun rango de fecha y un vehiculo
    * @return 
    */
    public TableModel SalidasDeCombustibleVehi(String fecha1,String fecha2, String Vehiculo){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha,aa.cantidad,aa.combustible,aa.maquina,aa.conductor "
                    + "from( "
                    + "select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"AND maq.nombre_maquina = '"+Vehiculo+"')aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Salidas de Combustible para \n"+Vehiculo,"<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    /**    
    * SALIDAS DE COMBUSTIBLE segun rango de fecha y un conductor
    * @return 
    */
    public TableModel SalidasDeCombustibleCon(String fecha1,String fecha2, String Conductor){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha,aa.cantidad,aa.combustible,aa.maquina,aa.conductor "
                    + "from( "
                    + "select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"and emp.nombre_empleado||' '||emp.apellido_empleado = '"+Conductor+"' )aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Salidas de Combustible para \n"+Conductor,"<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    
    
    /**    
    * SALIDAS DE COMBUSTIBLE segun rango de fecha y un tipo de combustible y un vehiculo
    * @return 
    */
    public TableModel SalidasDeCombustible(String fecha1,String fecha2,String Combustible,String Vehiculo){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha,aa.cantidad,aa.combustible,aa.maquina,aa.conductor "
                    + "from( "
                    + "select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"and comb.nombre_tipocombustible = '"+Combustible+"' " +
"AND maq.nombre_maquina = '"+Vehiculo+"')aa " +
"order by to_char(aa.fecha,'/mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Salidas de Combustible para \n"+Combustible+" \n"+Vehiculo,"<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    /**    
    * SALIDAS DE COMBUSTIBLE segun rango de fecha y un tipo de combustible y un conductor
    * @return 
    */
    public TableModel SalidasDeCombustibleCon(String fecha1,String fecha2,String Combustible,String Conductor){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha,aa.cantidad,aa.combustible,aa.maquina,aa.conductor "
                    + "from( "
                    + "select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"and comb.nombre_tipocombustible = '"+Combustible+"' " +
"and emp.nombre_empleado||' '||emp.apellido_empleado = '"+Conductor+"' )aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Salidas de Combustible para \n"+Combustible+" \n"+Conductor,"<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    /**    
    * SALIDAS DE COMBUSTIBLE segun rango de fecha y un tipo de vehiculo y un conductor
    * @return 
    */
    public TableModel SalidasDeCombustibleVehi(String fecha1,String fecha2,String Vehiculo,String Conductor){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha, aa.cantidad, aa.combustible,aa.maquina,aa.conductor "
                    + "from( "
                    + "select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"AND maq.nombre_maquina = '"+Vehiculo+"' " +
"and emp.nombre_empleado||' '||emp.apellido_empleado = '"+Conductor+"')aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc ";
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
                JOptionPane.showMessageDialog(pa, "No Existen Salidas de Combustible para \n"+Conductor+" \n"+Vehiculo,"<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    
    /**    
    * SALIDAS DE COMBUSTIBLE segun rango de fecha y un tipo de combustible y un vehiculo y un conductor
    * @return 
    */
    public TableModel SalidasDeCombustible(String fecha1,String fecha2,String Combustible,String Vehiculo,String Conductor){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha,aa.cantidad,aa.combustible,aa.maquina,aa.conductor "
                    + "from( "
                    + "select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"and comb.nombre_tipocombustible = '"+Combustible+"' " +
"AND maq.nombre_maquina = '"+Vehiculo+"' " +
"and emp.nombre_empleado||' '||emp.apellido_empleado = '"+Conductor+"')aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Salidas de Combustible para \n"+Combustible+" \n"+Vehiculo+" \n"+Conductor,"<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    /**    
    * ENTRADAS Y SALIDAS DE COMBUSTIBLE segun rango de fecha 
    * @return 
    */
    public TableModel EntradaSalidasDeCombustible(String fecha1,String fecha2,String combustible){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha , aa.cantidad , aa.combustible, aa.maquina, aa.conductor "
                    + "from( "
                    + "select DISTINCT " +
"to_date(compr.fecha_compra,'DD/MM/RR') as fecha, " +
"compr.cantidad_producto as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"case sali.maquina when 1 THEN ' '   ELSE ' '   END AS MAQUINA, " +
"case sali.conductor when 1 THEN ' '   ELSE ' '   END AS CONDUCTOR " +
"from compra compr " +
"         inner join tipo_combustible comb on(compr.codigo_producto=comb.codigo_tipocombustible) " +
"         inner join salida_combustible sali on(sali.tipo_combustible=comb.codigo_tipocombustible) " +
"where trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and " +
"To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+combustible+"' " +
"or  trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and " +
"To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+combustible+"' " +
"union all " +
"select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"and comb.nombre_tipocombustible = '"+combustible+"')aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Entradas/Salidas de Combustible","<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Entradas/Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    /**    
    * ENTRADAS Y SALIDAS DE COMBUSTIBLE segun rango de fecha y combustible y vehiculo
    * @return 
    */
    public TableModel EntradaSalidasDeCombustible(String fecha1,String fecha2,String combustible,String vehiculo){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha, aa.cantidad , aa.combustible, aa.maquina, aa.conductor "
                    + "from( "
                    + "select DISTINCT " +
"to_date(compr.fecha_compra,'DD/MM/RR') as fecha, " +
"compr.cantidad_producto as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"case sali.maquina when 1 THEN ' '   ELSE ' '   END AS MAQUINA, " +
"case sali.conductor when 1 THEN ' '   ELSE ' '   END AS CONDUCTOR " +
"from compra compr " +
"         inner join tipo_combustible comb on(compr.codigo_producto=comb.codigo_tipocombustible) " +
"         inner join salida_combustible sali on(sali.tipo_combustible=comb.codigo_tipocombustible) " +
"where trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and " +
"To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+combustible+"' " +
"or  trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and " +
"To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+combustible+"' " +
"union all " +
"select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"and comb.nombre_tipocombustible = '"+combustible+"' " +
"AND maq.nombre_maquina = '"+vehiculo+"' )aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Entradas/Salidas de Combustible","<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Entradas/Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    
    /**    
    * ENTRADAS Y SALIDAS DE COMBUSTIBLE segun rango de fecha y combustible y conductor
    * @return 
    */
    public TableModel EntradaSalidasDeCombustibleCon(String fecha1,String fecha2,String combustible,String Conductor){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha , aa.cantidad , aa.combustible, aa.maquina, aa.conductor "
                    + "from( "
                    + "select DISTINCT " +
"to_date(compr.fecha_compra,'DD/MM/RR') as fecha, " +
"compr.cantidad_producto as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"case sali.maquina when 1 THEN ' '   ELSE ' '   END AS MAQUINA, " +
"case sali.conductor when 1 THEN ' '   ELSE ' '   END AS CONDUCTOR " +
"from compra compr " +
"         inner join tipo_combustible comb on(compr.codigo_producto=comb.codigo_tipocombustible) " +
"         inner join salida_combustible sali on(sali.tipo_combustible=comb.codigo_tipocombustible) " +
"where trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and " +
"To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+combustible+"' " +
"or  trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and " +
"To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+combustible+"' " +
"union all " +
"select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"and comb.nombre_tipocombustible = '"+combustible+"' " +
"and emp.nombre_empleado||' '||emp.apellido_empleado = '"+Conductor+"' )aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc";
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
                JOptionPane.showMessageDialog(pa, "No Existen Entradas/Salidas de Combustible","<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Entradas/Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    
    
    /**    
    * ENTRADAS Y SALIDAS DE COMBUSTIBLE segun rango de fecha y combustible y vehiculo y conductor
    * @return 
    */
    public TableModel EntradaSalidasDeCombustible(String fecha1,String fecha2,String combustible,String vehiculo,String Conductor){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "select to_char(aa.fecha,'dd/mm/yyyy') as fecha , aa.cantidad , aa.combustible, aa.maquina, aa.conductor  "
                    + "from( "
                    + "select DISTINCT " +
"to_date(compr.fecha_compra,'DD/MM/RR') as fecha, " +
"compr.cantidad_producto as cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"case sali.maquina when 1 THEN ' '   ELSE ' '   END AS MAQUINA, " +
"case sali.conductor when 1 THEN ' '   ELSE ' '   END AS CONDUCTOR " +
"from compra compr " +
"         inner join tipo_combustible comb on(compr.codigo_producto=comb.codigo_tipocombustible) " +
"         inner join salida_combustible sali on(sali.tipo_combustible=comb.codigo_tipocombustible) " +
"where trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and " +
"To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+combustible+"' " +
"or  trunc (compr.fecha_compra) between to_date ('"+fecha1+"', 'DD/MM/RR') and " +
"To_date ('"+fecha2+"','DD/MM/RR') AND comb.nombre_tipocombustible = '"+combustible+"' " +
"union all " +
"select " +
"to_date(sal.fecha_salidacombustible,'DD/MM/RR') as fecha, " +
"sal.cantidad, " +
"comb.nombre_tipocombustible as combustible, " +
"maq.nombre_maquina as maquina, " +
"emp.nombre_empleado||' '||emp.apellido_empleado as conductor " +
" " +
"from salida_combustible sal " +
"    inner join tipo_combustible comb on(sal.tipo_combustible=comb.codigo_tipocombustible) " +
"    inner join maquina maq on(maq.codigo_maquina=sal.maquina) " +
"    inner join tipo_medidaderivada medi on(medi.codigo_medidaderivada=sal.tipo_medida) " +
"    inner join empleado emp on(emp.codigo_empleado=sal.conductor) " +
"where trunc (sal.fecha_salidacombustible) between to_date ('"+fecha1+"', 'DD/MM/RR') and To_date ('"+fecha2+"','DD/MM/RR') " +
"and comb.nombre_tipocombustible = '"+combustible+"' " +
"AND maq.nombre_maquina = '"+vehiculo+"' " +
"and emp.nombre_empleado||' '||emp.apellido_empleado = '"+Conductor+"')aa " +
"order by to_char(aa.fecha,'mm')desc, to_char(aa.fecha,'dd')desc ";
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
                JOptionPane.showMessageDialog(pa, "No Existen Entradas/Salidas de Combustible","<SQL> COMBUSTIBLES", JOptionPane.OK_CANCEL_OPTION);
            }
            rs.close();
        }catch(HeadlessException | SQLException e ){
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Entradas/Salidas de Combustible:\n" + e,"<SQL> COMBUSTIBLES", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }
    
}
