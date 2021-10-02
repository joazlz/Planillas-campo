package SQLclasesSecundarias;

import clases.ConexionBD;
import clases.Objetos.ObjDetallesActividad;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SQLRegistroActividad {

    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection();
    private static Statement st;
    private static ResultSet rs;
    private java.awt.Frame pa;

    public SQLRegistroActividad(java.awt.Frame parent) {
        pa = parent;
    }

    //Muestra valores en la tabla
    public TableModel mostrarDatos(DefaultTableModel modelo) {
        try {
            SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.MONTH, -1);
            String fecha1 = d.format(calendar1.getTime());//Hace un mes
            calendar1.add(Calendar.MONTH, +1);
            calendar1.add(Calendar.DAY_OF_MONTH, +1);
            String fecha2 = d.format(calendar1.getTime());//Fecha de hoy
            st = cn.createStatement();
            String registro = "select CODIGO_REGISTROACTIVIDAD as CODIGO, "
                    + "               to_char(FECHA_ACTIVIDAD,'DD/MM/yyyy') AS FECHA, "
                    + "               AR.NOMBRE_AREA as Area, "
                    + "               AC.NOMBRE_ACTIVIDAD AS ACTIVIDAD,"
                    + "               PRO.NOMBRE AS \"ASIGNADO A:\" "
                    + "                               FROM " + conectar.user_admin + ".registro_actividad RE INNER JOIN " + conectar.user_admin + ".ACTIVIDAD AC ON(RE.CODIGO_ACTIVIDAD = AC.CODIGO_ACTIVIDAD)"
                    + "                               INNER JOIN " + conectar.user_admin + ".AREA AR ON(RE.Area = AR.CODIGO_Area)"
                    + "         INNER JOIN " + conectar.user_admin + ".PRODUCTO PRO ON(RE.CODIGO_PRODUCTO = PRO.CODIGO)"
                    + "         where TRUNC (RE.fecha_actividad) BETWEEN  TO_DATE ('" + fecha1 + "', 'DD/MM/RR') and TO_DATE ('" + fecha2 + "', 'DD/MM/RR') "
                    + "         order by to_char(FECHA_ACTIVIDAD,'MM') desc, to_char(FECHA_ACTIVIDAD,'dd') desc   ";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(pa, "ERROR AL IMPRIMIR TABLA:\n" + e);
        }
        return modelo;
    }

    //Muestra el valor buscado de la tabla por area
    public TableModel buscarDatosArea(DefaultTableModel modelo, int codigoArea) throws SQLException {
        try {
            SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.MONTH, -1);
            String fecha1 = d.format(calendar1.getTime());//Hace un mes
            calendar1.add(Calendar.MONTH, +1);
            calendar1.add(Calendar.DAY_OF_MONTH, +1);
            String fecha2 = d.format(calendar1.getTime());//Fecha de hoy
            st = cn.createStatement();
            String registro = "select CODIGO_REGISTROACTIVIDAD as CODIGO, "
                    + "               to_char(FECHA_ACTIVIDAD,'DD/MM/yyyy') AS FECHA, "
                    + "               AR.NOMBRE_AREA as Area, "
                    + "               AC.NOMBRE_ACTIVIDAD AS ACTIVIDAD, "
                    + "               PRO.NOMBRE AS \"ASIGNADO A:\" "
                    + "                               FROM " + conectar.user_admin + ".registro_actividad RE INNER JOIN ACTIVIDAD AC ON(RE.CODIGO_ACTIVIDAD = AC.CODIGO_ACTIVIDAD)"
                    + "                               INNER JOIN AREA AR ON(RE.Area = AR.CODIGO_Area)"
                    + "         INNER JOIN PRODUCTO PRO ON(RE.CODIGO_PRODUCTO = PRO.CODIGO)"
                    + "                               where RE.AREA = '" + codigoArea + "' "
                    + "and TRUNC (RE.fecha_actividad) BETWEEN  TO_DATE ('" + fecha1 + "', 'DD/MM/RR') and TO_DATE ('" + fecha1 + "', 'DD/MM/RR') order by 2 desc";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(pa, "ERROR YA EXISTE UN REGISTRO CON ESE CODIGO:\n " + e);
        }
        return modelo;
    }

    //Muestra el valor buscado de la tabla por Actividad
    public TableModel buscarDatosActividad(DefaultTableModel modelo, int codigoActividad) throws SQLException {
        try {
            SimpleDateFormat d = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(new Date());
            calendar1.add(Calendar.MONTH, -1);
            String fecha1 = d.format(calendar1.getTime());//Hace un mes
            calendar1.add(Calendar.MONTH, +1);
            calendar1.add(Calendar.DAY_OF_MONTH, +1);
            String fecha2 = d.format(calendar1.getTime());//Fecha de hoy
            st = cn.createStatement();
            st = cn.createStatement();
            String registro = "select CODIGO_REGISTROACTIVIDAD as CODIGO, "
                    + "               to_char(FECHA_ACTIVIDAD,'DD/MM/yyyy') AS FECHA, "
                    + "               AR.NOMBRE_AREA as Area, "
                    + "               AC.NOMBRE_ACTIVIDAD AS ACTIVIDAD, "
                    + "               PRO.NOMBRE AS \"ASIGNADO A:\" "
                    + "                               FROM " + conectar.user_admin + ".registro_actividad RE INNER JOIN ACTIVIDAD AC ON(RE.CODIGO_ACTIVIDAD = AC.CODIGO_ACTIVIDAD)"
                    + "                               INNER JOIN AREA AR ON(RE.Area = AR.CODIGO_Area)"
                    + "         INNER JOIN PRODUCTO PRO ON(RE.CODIGO_PRODUCTO = PRO.CODIGO)"
                    + "                               where RE.CODIGO_ACTIVIDAD = '" + codigoActividad + "'"
                    + "         and TRUNC (RE.fecha_actividad) BETWEEN  TO_DATE ('" + fecha1 + "', 'DD/MM/RR') and TO_DATE ('" + fecha2 + "', 'DD/MM/RR') order by 2 desc";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(pa, "ERROR YA EXISTE UN REGISTRO CON ESE CODIGO:\n " + e);
        }
        return modelo;
    }

    //Muestra Valores por fecha
    public TableModel buscarDatosFecha(DefaultTableModel modelo, String fecha1) {
        try {
            st = cn.createStatement();
            String registro = "select CODIGO_REGISTROACTIVIDAD as CODIGO, "
                    + "               to_char(FECHA_ACTIVIDAD,'DD/MM/yyyy') AS FECHA, "
                    + "               AR.NOMBRE_AREA as Area, "
                    + "               AC.NOMBRE_ACTIVIDAD AS ACTIVIDAD, "
                    + "               PRO.NOMBRE AS \"ASIGNADO A:\" "
                    + "                               FROM " + conectar.user_admin + ".registro_actividad RE INNER JOIN ACTIVIDAD AC ON(RE.CODIGO_ACTIVIDAD = AC.CODIGO_ACTIVIDAD)"
                    + "                               INNER JOIN AREA AR ON(RE.Area = AR.CODIGO_Area)"
                    + "         INNER JOIN PRODUCTO PRO ON(RE.CODIGO_PRODUCTO = PRO.CODIGO)"
                    + "                                   where TRUNC (FECHA_ACTIVIDAD) = TO_DATE ('" + fecha1 + "', 'DD/MM/RR')";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(pa, "ERROR AL BUSCAR REGISTRO ACTIVIDAD POR FECHA:\n" + e);
        }
        return modelo;
    }

    //Muestra el valor buscado de la tabla por area
    public TableModel buscarDatos(DefaultTableModel modelo, int codigoRegistro) throws SQLException {
        try {
            st = cn.createStatement();
            String registro = "select\n"
                    + "  CODIGO_REGISTROACTIVIDAD as CODIGO,\n"
                    + "  to_char(FECHA_ACTIVIDAD, 'DD/MM/yyyy') AS FECHA,\n"
                    + "  AR.NOMBRE_AREA as Area,\n"
                    + "  AC.NOMBRE_ACTIVIDAD AS ACTIVIDAD,\n"
                    + "  PRO.NOMBRE AS \"ASIGNADO A:\"\n"
                    + "FROM\n"
                    +conectar.user_admin+".registro_actividad RE\n"
                    + "  INNER JOIN "+conectar.user_admin+".ACTIVIDAD AC ON(RE.CODIGO_ACTIVIDAD = AC.CODIGO_ACTIVIDAD)\n"
                    + "  INNER JOIN "+conectar.user_admin+".AREA AR ON(RE.Area = AR.CODIGO_Area)\n"
                    + "  INNER JOIN "+conectar.user_admin+".PRODUCTO PRO ON(RE.CODIGO_PRODUCTO = PRO.CODIGO)\n"
                    + "where\n"
                    + "  RE.CODIGO_REGISTROACTIVIDAD = '"+codigoRegistro+"'\n"
                    + "order by\n"
                    + "  1";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(pa, "ERROR AL BUSCAR UN REGISTRO CON ESE CODIGO:\n " + e);
        }
        return modelo;
    }

    public void guardarCommit() {
        try {

            st = cn.createStatement();
            String registro = "COMMIT";
            st.execute(registro);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(pa, "ERROR AL DAR SALIDA DE QUIMICOS:\n" + e);
        }
    }

    //Guarda un nuevo registro
    public void guardarDatos(int codigoRegistroActividad, int detalleMaquina, int detalleEmpleados, int detalleQuimico, int area, int actividad, int codigoProducto, String fecha, String horaRegistro) {
        try {
            st = cn.createStatement();
            String registro = "INSERT INTO " + conectar.user_admin + ".REGISTRO_ACTIVIDAD (codigo_RegistroActividad, detalle_Maquina, detalle_Empleados, detalle_Quimico, Area, codigo_actividad,codigo_producto, FECHA_ACTIVIDAD) "
                    + "         VALUES ('" + codigoRegistroActividad + "' , '" + detalleMaquina + "' , '" + detalleEmpleados + "' , '" + detalleQuimico + "' , '" + area + "' , '" + actividad + "','" + codigoProducto + "' , TO_DATE('" + fecha + " " + horaRegistro + "', 'DD-MM-YYYY HH24:MI:SS')) ";
            st.execute(registro);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(pa, "ERROR AL GUARDAR " + ex);
        }
    }

    //Eliminar un registro
    public void eliminarDatos(int codigoRegistro, ObjDetallesActividad detallesActividad) {
        try {
            st = cn.createStatement();
            int codigoDetalleEmpleado = detallesActividad.getDetalleEmpleados();
            int codigoDetalleMaquina = detallesActividad.getDetalleMaquina();
            int codigoDetalleQuimico = detallesActividad.getDetalleQuimico();
            String registro = "DELETE FROM " + conectar.user_admin + ".registro_actividad WHERE CODIGO_REGISTROACTIVIDAD = '" + codigoRegistro + "'";
            st.execute(registro);
            if (new SQLDetalleEmpleado().existeDetalle(codigoDetalleEmpleado) == true) {
                String registro1 = "DELETE FROM " + conectar.user_admin + ".detalle_Empleados WHERE codigo_detalleempleado = '" + codigoDetalleEmpleado + "'";
                st.execute(registro1);
            } else if (new SQLDetalleEmpleadoCorte(pa).existeDetalle(codigoDetalleEmpleado) == true) {
                String registro1 = "DELETE FROM " + conectar.user_admin + ".COSECHA WHERE codigo = '" + codigoDetalleEmpleado + "'";
                st.execute(registro1);
            }
            String registro2 = "DELETE FROM " + conectar.user_admin + ".detalle_Maquina WHERE codigo_detalleMaquina = '" + codigoDetalleMaquina + "'";
            st.execute(registro2);
            String registro3 = "DELETE FROM " + conectar.user_admin + ".detalle_Quimico WHERE codigo_detalleQuimico = '" + codigoDetalleQuimico + "'";
            st.execute(registro3);
            JOptionPane.showMessageDialog(pa, "DATO ELIMINADO");
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(pa, "ERROR A ELIMINAR LISTA DETALLES: \n" + e);
        }
    }

    //Eliminar Registro
    public void eliminarDatos(int codigoRegistro) {
        try {
            st = cn.createStatement();
            String registro = "DELETE FROM " + conectar.user_admin + ".registro_actividad WHERE CODIGO_REGISTROACTIVIDAD = '" + codigoRegistro + "'";
            st.execute(registro);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(pa, "ERROR A ELIMINAR ACTIVIDAD: \n" + e);
        }
    }

    //Eliminar un registro
    public void eliminarDetalles(int codigoDetalleEmpleado, int codigoDetalleMaquina, int codigoDetalleQuimico) {
        try {
            st = cn.createStatement();
            if (new SQLDetalleEmpleado().existeDetalle(codigoDetalleEmpleado) == true) {
                String registro1 = "DELETE FROM " + conectar.user_admin + ".detalle_Empleados WHERE codigo_detalleempleado = '" + codigoDetalleEmpleado + "'";
                st.execute(registro1);
            } else if (new SQLDetalleEmpleadoCorte(pa).existeDetalle(codigoDetalleEmpleado) == true) {
                String registro1 = "DELETE FROM " + conectar.user_admin + ".COSECHA WHERE codigo = '" + codigoDetalleEmpleado + "'";
                st.execute(registro1);
            }
            String registro2 = "DELETE FROM " + conectar.user_admin + ".detalle_Maquina WHERE codigo_detalleMaquina = '" + codigoDetalleMaquina + "'";
            st.execute(registro2);
            String registro3 = "DELETE FROM " + conectar.user_admin + ".detalle_Quimico WHERE codigo_detalleQuimico = '" + codigoDetalleQuimico + "'";
            st.execute(registro3);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "ERROR A ELIMINAR DETALLES: \n" + e);
        }
    }

    //Eliminar un registro
    public ObjDetallesActividad datosDetalleActividad(int codigoRegistro) throws SQLException {
        ObjDetallesActividad detallesActividad = new ObjDetallesActividad();
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            st = cn.createStatement();
            String registro = "select DETALLE_EMPLEADOS,DETALLE_MAQUINA,DETALLE_QUIMICO"
                    + "                               FROM " + conectar.user_admin + ".registro_actividad"
                    + "                               where CODIGO_REGISTROACTIVIDAD = '" + codigoRegistro + "'order by 1";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
//            rs.close();
            for (int f = 0; f < modelo.getRowCount(); f++) {
                int c = 0;
                detallesActividad.setDetalleEmpleados(Integer.parseInt(modelo.getValueAt(f, c).toString()));
                detallesActividad.setDetalleMaquina(Integer.parseInt(modelo.getValueAt(f, c + 1).toString()));
                detallesActividad.setDetalleQuimico(Integer.parseInt(modelo.getValueAt(f, c + 2).toString()));
                c++;
            }
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(pa, "ERROR YA EXISTE UN REGISTRO CON ESE CODIGO:\n " + e);
        }
        return detallesActividad;
    }

    //Actualiza un registro
    public void actualizarDatosDetalle(int Area, int Actividad, int Producto, int codigoAct, int detalleEmpleados, int detalleMaquina, int detalleQuimico, String fecha, String horaRegistro) {
        try {
            st = cn.createStatement();
            String registro = " UPDATE " + conectar.user_admin + ".registro_actividad SET DETALLE_EMPLEADOS = '" + detalleEmpleados + "' WHERE CODIGO_REGISTROACTIVIDAD = " + codigoAct + " ";
            st.execute(registro);
            String registro1 = " UPDATE " + conectar.user_admin + ".registro_actividad SET DETALLE_MAQUINA = '" + detalleMaquina + "' WHERE CODIGO_REGISTROACTIVIDAD = " + codigoAct + " ";
            st.execute(registro1);
            String registro2 = " UPDATE " + conectar.user_admin + ".registro_actividad SET DETALLE_QUIMICO = '" + detalleQuimico + "' WHERE CODIGO_REGISTROACTIVIDAD = " + codigoAct + " ";
            st.execute(registro2);
            String registro3 = " UPDATE " + conectar.user_admin + ".registro_actividad SET AREA= '" + Area + "' WHERE CODIGO_REGISTROACTIVIDAD = " + codigoAct + " ";
            st.execute(registro3);
            String registro4 = " UPDATE " + conectar.user_admin + ".registro_actividad SET CODIGO_ACTIVIDAD= '" + Actividad + "' WHERE CODIGO_REGISTROACTIVIDAD = " + codigoAct + " ";
            st.execute(registro4);
            String registro5 = " UPDATE " + conectar.user_admin + ".registro_actividad SET CODIGO_PRODUCTO= '" + Producto + "' WHERE CODIGO_REGISTROACTIVIDAD = " + codigoAct + " ";
            st.execute(registro5);
            String registro6 = " UPDATE " + conectar.user_admin + ".registro_actividad SET FECHA_ACTIVIDAD = TO_DATE('" + fecha + " " + horaRegistro + "', 'DD-MM-YYYY HH24:MI:SS') WHERE CODIGO_REGISTROACTIVIDAD = " + codigoAct + " ";
            st.execute(registro6);
            cn.commit();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(pa, "ERROR AL GUARDAR DETALLES " + ex);
        }
    }

    //Devuelve el Area del registro
    public String getArea(int codigoRegistro) {
        DefaultTableModel modelo = new DefaultTableModel();
        String area = null;
        try {
            st = cn.createStatement();
            String registro = "";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    if (i == 0) {
                        area = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
//            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(pa, "ERROR AL DEVOLVER AREA" + e);
        }
        return area;
    }

    //valida si existe el registro
    public boolean existeRegistro(int codigoRegistro) {
        boolean existe = false;
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            st = cn.createStatement();
            String registro = "select CODIGO_REGISTROACTIVIDAD FROM " + conectar.user_admin + ".registro_actividad WHERE CODIGO_REGISTROACTIVIDAD = '" + codigoRegistro + "'";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    if (i == 0) {
                        existe = true;
                    }
                }
                modelo.addRow(fila);
            }
//            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al validar Existencia " + e);
        }
        return existe;
    }

    //Devuelve el ultimo registro ingresado
    public int getRegistroMaximo() {
        DefaultTableModel modelo = new DefaultTableModel();
        int registroMaximo = 0;
        try {
            st = cn.createStatement();
            String registro = "select max(CODIGO_REGISTROACTIVIDAD) FROM " + conectar.user_admin + ".REGISTRO_ACTIVIDAD ";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    if (i == 0) {
                        registroMaximo = Integer.parseInt(fila[i].toString());
                    }
                }
                modelo.addRow(fila);
            }
//            rs.close();
        } catch (Exception e) {

        }
        return registroMaximo;
    }
}
