package fincabellavista.VentanasConsultas;

import clases.ConexionBD;
import clases.Objetos.Empleado;
import java.awt.Image;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SQLConsultasPlanilla {

    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection();
    private static Statement st;
    private static ResultSet rs;
    private Icon ico = null;
    private java.awt.Frame pa;
    //------------------------------------------------//

    public Icon ico() {
        Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Reportes Seleccionado.png")).getImage();
        ImageIcon im = new ImageIcon(imagen);
        return this.ico = new ImageIcon(im.getImage());
    }
    public SQLConsultasPlanilla(java.awt.Frame parent) {
        pa = parent;
    }
    
    //Generar hoja de Pagos
    public TableModel HojaDePagos(String fecha1, String fecha2){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT aa.empleado,te.NOMBRE_TIPOEMPLEADO AS \"TIPO EMPLEADO\",SUM(aa.dias)AS DIAS,SUM(aa.sueldo) as salario  " +
"FROM( " +
"    SELECT EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO AS Empleado,emp.tipo_empleado AS TIPO,count(rac.fecha_actividad ) as Dias,sum(emp.salariodia_empleado) as Sueldo " +
"        FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) " +
"                                        INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"        where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"        group by EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO,emp.tipo_empleado " +
"    UNION ALL " +
"    SELECT EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO AS Empleado,emp.tipo_empleado AS TIPO,count(rac.fecha_actividad ) as Dias, sum((demp.cantidad)*pro.precio_corte/100) as Sueldo " +
"        FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                        INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) "
                    + "                  INNER JOIN PRODUCTO PRO ON(PRO.CODIGO = RAC.CODIGO_PRODUCTO)" +
"        where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"        group by EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO,emp.tipo_empleado " +
"    )AA "
                    + "inner join TIPO_EMPLEADO te on (aa.tipo=te.CODIGO_TIPOEMPLEADO)" +
"GROUP BY aa.empleado,te.NOMBRE_TIPOEMPLEADO order by 2,1";
            st.execute(registro);
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
            JOptionPane.showMessageDialog(pa,"Error Al Generar Hoja De Pagos:\n" +e,"<SQL> PLANILA", JOptionPane.ERROR_MESSAGE);
        }
    return modelo;
    }
    //Generar hoja de Pagos
    public TableModel HojaDePagosTipo(String fecha1, String fecha2,String tipo){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT aa.empleado,te.NOMBRE_TIPOEMPLEADO AS \"TIPO EMPLEADO\",SUM(aa.dias)AS DIAS,SUM(aa.sueldo) as salario  " +
"FROM( " +
"    SELECT EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO AS Empleado,emp.tipo_empleado AS TIPO,count(rac.fecha_actividad ) as Dias,sum(emp.salariodia_empleado) as Sueldo " +
"        FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) " +
"                                        INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"        where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"        group by EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO,emp.tipo_empleado " +
"    UNION ALL " +
"    SELECT EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO AS Empleado,emp.tipo_empleado AS TIPO,count(rac.fecha_actividad ) as Dias, sum((demp.cantidad)*pro.precio_corte/100) as Sueldo " +
"        FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) " +
"                                        INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) "
                    + "                  INNER JOIN PRODUCTO PRO ON(PRO.CODIGO = RAC.CODIGO_PRODUCTO) " +
"        where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"        group by EMP.NOMBRE_EMPLEADO||' '||EMP.APELLIDO_EMPLEADO,emp.tipo_empleado " +
"    )AA "
                    + "inner join TIPO_EMPLEADO te on (aa.tipo=te.CODIGO_TIPOEMPLEADO)"
                    + "where te.NOMBRE_TIPOEMPLEADO = '"+tipo+"'  " +
"GROUP BY aa.empleado,te.NOMBRE_TIPOEMPLEADO order by 2,1";
            st.execute(registro);
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
            JOptionPane.showMessageDialog(pa,"Error Al Generar Hoja De Pagos:\n" +e,"<SQL> PLANILA", JOptionPane.ERROR_MESSAGE);
        }
    return modelo;
    }
    
    //Dias Trabajados
    public String DiasTrabajados(String fecha1, String fecha2){
        String diasLaborados = null;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "select count(fecha) " +
"                               from (select DISTINCT to_char(fecha_actividad,'dd-MM-yyyy') as fecha " +
"                                       from registro_actividad " +
"                                       where TRUNC (fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                       )";
            st.execute(registro);
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
                    diasLaborados = fila[i].toString();
                }
                modelo.addRow(fila);
            }
            if(diasLaborados.equals("0"))
                diasLaborados=null;
            rs.close();
        }catch(SQLException e){            
            JOptionPane.showMessageDialog(pa,"Error Al Devolver Dias Laborados:\n" +e,"<SQL> PLANILA", JOptionPane.ERROR_MESSAGE);
        }
    return diasLaborados;
    }
    //Generar hoja de Pagos
    public TableModel ActividadesRealizadas(String fecha1, String fecha2){
        DefaultTableModel modelo = new DefaultTableModel();
        try{
            st=cn.createStatement();
            String registro = "SELECT act.nombre_actividad As \"Actividad\", ar.nombre_area AS \"Area\", aa.empleados AS \"Jornales\" ,aa.costoempleados AS \"Costo Empleados\", aa.diastrabajados as \"Dias Trabajados\" " +
"FROM(  SELECT RAC.CODIGO_ACTIVIDAD As Actividad, RAC.AREA As Area, COUNT (EMP.codigo_EMpleado) AS Empleados, sum(demp.cantidad*40/100) as CostoEmpleados, co.fecha as DiasTrabajados " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN COSECHA DEMP ON(rac.detalle_empleados = DEMP.CODIGO)  " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN ( " +
"                                                                                select area, codigo_actividad,count(fecha) as fecha " +
"                                                                                from (select area,codigo_actividad, to_char(fecha_actividad,'dd-MM-yyyy') as fecha " +
"                                                                                       from registro_actividad " +
"                                                                                       where TRUNC (fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                                                                     ) " +
"                                                                                group by area, codigo_actividad " +
"                                                                                ) co on(rac.codigo_actividad = co.codigo_actividad and rac.area = co.area) " +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    AND Rac.codigo_actividad = 117" +
"                                    group by rac.codigo_actividad, RAC.AREA, co.fecha " +
"    UNION ALL " +
"    SELECT rac.codigo_actividad As Actividad, Rac.AREA As Area, COUNT (EMP.codigo_EMpleado) AS Empleados, sum(emp.salariodia_empleado) as CostoEmpleados, co.fecha as \"Dias Trabajados\" " +
"                                    FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) " +
"                                                                    INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) " +
"                                                                    INNER JOIN (select area, codigo_actividad,count(fecha) as fecha " +
"                                                                                from (select area,codigo_actividad, to_char(fecha_actividad,'dd-MM-yyyy') as fecha " +
"                                                                                       from registro_actividad " +
"                                                                                       where TRUNC (fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                                                                     ) " +
"                                                                                group by area, codigo_actividad " +
"                                                                                ) co on(rac.codigo_actividad = co.codigo_actividad and rac.area = co.area) " +
"                                    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
"                                    group by rac.CODIGO_actividad, RAC.AREA, co.fecha " +
") AA INNER JOIN AREA  AR ON(aa.area = ar.codigo_area) " +
"     INNER JOIN ACTIVIDAD ACT ON(aa.actividad=act.codigo_actividad) " +
"  order by 1";
            st.execute(registro);
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
            JOptionPane.showMessageDialog(pa,"Error Al Mostrar Actividades Realizadas:\n" +e,"<SQL> PLANILA", JOptionPane.ERROR_MESSAGE);
        }
    return modelo;
    }
    //Actividad Mas Realizada y en que area
    public String ActividadAreaMasRealizada(String fecha1, String fecha2){
        String actRealizada = null;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT A" +
                                "      from (SELECT co.fecha, ac.nombre_actividad||'-'||AR.NOMBRE_AREA  as A " +
                                        "      FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN ACTIVIDAD AC ON(rac.codigo_actividad = AC.CODIGO_ACTIVIDAD) " +
                                        "                                    INNER JOIN AREA AR ON(rac.area = AR.CODIGO_AREA) " +
                                        "                                    inner join ( " +
                                        "                                                select area, codigo_actividad,count(fecha) as fecha " +
                                        "                                                from (select area,codigo_actividad, to_char(fecha_actividad,'dd-MM-yyyy') as fecha " +
                                        "                                                       from registro_actividad " +
                                        "                                                       where TRUNC (fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') "
                    + "                                                                         AND codigo_actividad != 128" +
                                        "                                                     ) " +
                                        "                                                group by area, codigo_actividad " +
                                        "                                                order by 2 desc " +
                                        "                                                ) co on(rac.codigo_actividad = co.codigo_actividad and rac.area = co.area) " +
                                        "    where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') " +
                                        "    group by ac.nombre_actividad||'-'||AR.NOMBRE_AREA , co.fecha " +
                                        "    order by 1 desc " +
                                        "    ) ta " +
                                "    WHERE ROWNUM <=1";
            st.execute(registro);
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
                    actRealizada = fila[i].toString();
                }
                modelo.addRow(fila);
            }
            rs.close();
        }catch(SQLException e){            
            JOptionPane.showMessageDialog(pa,"Error Al Devolver Activiada Más Realizada:\n" +e,"<SQL> PLANILA", JOptionPane.ERROR_MESSAGE);
        }
    return actRealizada;
    }
    //Precio De Actividad Mas Realizada
    public String PrecioActividadMasRealizada(String fecha1, String fecha2){
        String precioAct = null;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "SELECT rac.codigo_actividad,co.fecha, sum(emp.salariodia_empleado) as \"Costo Empleados\"\n" +
"FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN DETALLE_EMPLEADOS DEMP ON(rac.detalle_empleados = DEMP.CODIGO_DETALLEEMPLEADO) \n" +
"                                INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) \n" +
"                                INNER JOIN ( \n" +
"                                    select area, codigo_actividad,count(fecha) as fecha \n" +
"                                            from (select area,codigo_actividad, to_char(fecha_actividad,'dd-MM-yyyy') as fecha \n" +
"                                                    from registro_actividad \n" +
"                                                    where TRUNC (fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')  \n"
                    + "                              and codigo_actividad !=117 and codigo_actividad !=128)" +
"                                    group by area, codigo_actividad \n" +
"                                ) co on(rac.codigo_actividad = co.codigo_actividad and rac.area = co.area) \n" +
"where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') \n" +
"group by rac.codigo_actividad, rac.Area, co.fecha \n" +
"union all\n" +
"SELECT rac.codigo_actividad,co.fecha, sum(demp.cantidad*40/100) as \"Costo Empleados\"\n" +
"FROM REGISTRO_ACTIVIDAD RAC     INNER JOIN cosecha DEMP ON(rac.detalle_empleados = DEMP.CODIGO) \n" +
"                                INNER JOIN EMPLEADO EMP ON(demp.CODIGO_EMPLEADO = EMP.CODIGO_EMPLEADO) \n" +
"                                INNER JOIN ( \n" +
"                                    select area, codigo_actividad,count(fecha) as fecha \n" +
"                                            from (select area,codigo_actividad, to_char(fecha_actividad,'dd-MM-yyyy') as fecha \n" +
"                                                    from registro_actividad \n" +
"                                                    where TRUNC (fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR')  \n" +
"                                                    and codigo_actividad !=117 and codigo_actividad !=128)"
                    + "group by area, codigo_actividad \n" +
"                                ) co on(rac.codigo_actividad = co.codigo_actividad and rac.area = co.area) \n" +
"where TRUNC (rac.fecha_actividad) BETWEEN  TO_DATE ('"+fecha1+"', 'DD/MM/RR') and TO_DATE ('"+fecha2+"', 'DD/MM/RR') \n" +
"group by rac.codigo_actividad, rac.Area, co.fecha \n" +
"order by 2 DESC, 3 desc";
            st.execute(registro);
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            int j=0;
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    if(j==0 && i ==2)
                        precioAct = fila[i].toString();
                }
                modelo.addRow(fila);
                j++;
            }
            rs.close();
        }catch(SQLException e){            
            JOptionPane.showMessageDialog(pa,"Error Al Devolver Precio De Activiada Más Realizada:\n" +e,"<SQL> PLANILA", JOptionPane.ERROR_MESSAGE);
        }
    return precioAct;
    }
    
    
    //Tipos de empleados
    public String[] TipoDeEmpleados(){
        String[] TiposDeEmpleados = new String[CountTipoDeEmpleados()+1];
        try{
            TiposDeEmpleados[0] =  "TODOS";
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "Select NOMBRE_TIPOEMPLEADO FROM TIPO_EMPLEADO ORDER BY 1";
            st.execute(registro);
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            int j=0;
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    TiposDeEmpleados[j+1] = fila[i].toString();
                }
                modelo.addRow(fila);
                j++;
            }
            rs.close();
        }catch(SQLException e){            
            JOptionPane.showMessageDialog(pa,"Error Al Crear Botones:\n" +e,"<SQL> PLANILA", JOptionPane.ERROR_MESSAGE);
        }
    return TiposDeEmpleados;
    }
    
    //Tipos de empleados
    public int CountTipoDeEmpleados(){
        int TiposDeEmpleados = 0;
        try{
            DefaultTableModel modelo = new DefaultTableModel();
            st=cn.createStatement();
            String registro = "Select count(NOMBRE_TIPOEMPLEADO) FROM TIPO_EMPLEADO ORDER BY 1";
            st.execute(registro);
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas+1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            int j=0;
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    TiposDeEmpleados=Integer.parseInt(fila[i].toString());
                }
                modelo.addRow(fila);
                j++;
            }
            rs.close();
        }catch(SQLException e){            
            JOptionPane.showMessageDialog(pa,"Error Al Crear Botones:\n" +e,"<SQL> PLANILA", JOptionPane.ERROR_MESSAGE);
        }
    return TiposDeEmpleados;
    }
    
    
}
