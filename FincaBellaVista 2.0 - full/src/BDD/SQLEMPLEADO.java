package BDD;

import clases.ConexionBD;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class SQLEMPLEADO {

    //---------------Variables------------------------//
    private ConexionBD conectar = new ConexionBD();
    private Connection con = conectar.getConnection();
    private static ResultSet rs;
    private static Statement st;
    private Icon ico = null;
    private java.awt.Frame pa;
    //------------------------------------------------//

    public SQLEMPLEADO(java.awt.Frame parent) {
        pa = parent;
    }

    public Icon ico() {
        Image imagen = new ImageIcon(getClass().getResource("/imagenes/Cuadrialla Seleccionado.png")).getImage();
        ImageIcon im = new ImageIcon(imagen);
        return this.ico = new ImageIcon(im.getImage());
    }

    //Muestra Tabla Completa
    public TableModel mostrarDatos(DefaultTableModel modelo) {
        try {
            st = con.createStatement();
            String registro = "SELECT a.CODIGO_EMPLEADO as Codigo, a.NOMBRE_EMPLEADO as Nombre ,"
                    + " a.APELLIDO_EMPLEADO AS Apellido, a.DPI,"
                    + " a.TELEFONO_EMPLEADO as Telefono, a.DIRECCION_EMPLEADO as Direccion,"
                    + " a.EMAIL_EMPLEADO as Email, gn.nombre_tipogenero AS Genero, a.fechacomienzo as Fecha,"
                    + " em.nombre_tipoempleado as Tipo, a.salariodia_empleado as Salario, es.NOMBRE_ESTADOEMPLEADO as Estado "
                    + " FROM " + conectar.user_admin + ".EMPLEADO a "
                    + " INNER JOIN "+conectar.user_admin+".tipo_genero gn on (a.GENERO_EMPLEADO = gn.codigo_tipogenero) "
                    + " INNER JOIN "+conectar.user_admin+".tipo_empleado em on (a.tipo_empleado = em.codigo_tipoempleado)"
                    + " INNER JOIN "+conectar.user_admin+".estado_empleado es on (a.codigo_estadoempleado = es.codigo_estadoempleado) ORDER BY 1";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }

            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas + 1; i++) {
                    if (i < cantColumnas) {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                modelo.addRow(fila);
            }

            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Mostrar Datos " + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    //Realiza Busqueda por Nombre
    public TableModel buscarDatosNombre(String nombreEmpleado, DefaultTableModel modelo) {
        try {
            st = con.createStatement();
            String registro = "SELECT a.CODIGO_EMPLEADO as Codigo, a.NOMBRE_EMPLEADO as Nombres ,"
                    + " a.APELLIDO_EMPLEADO AS Apellidos,a.DPI,\n"
                    + " a.TELEFONO_EMPLEADO as Telefono, a.DIRECCION_EMPLEADO as Direccion,"
                    + " a.EMAIL_EMPLEADO as Email, gn.nombre_tipogenero AS Genero, a.fechacomienzo as Fecha,"
                    + " em.nombre_tipoempleado as Tipo, a.salariodia_empleado as Salario, es.NOMBRE_ESTADOEMPLEADO as Estado "
                    + " FROM " + conectar.user_admin + ".EMPLEADO a "
                    + " INNER JOIN "+conectar.user_admin+".tipo_genero gn on (a.GENERO_EMPLEADO = gn.codigo_tipogenero) "
                    + " INNER JOIN "+conectar.user_admin+".tipo_empleado em on (a.tipo_empleado = em.codigo_tipoempleado)"
                    + " INNER JOIN "+conectar.user_admin+".estado_empleado es on (a.codigo_estadoempleado = es.codigo_estadoempleado) WHERE a.NOMBRE_EMPLEADO = '" + nombreEmpleado + "'";
            st.execute(registro);
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }

            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas + 1; i++) {
                    if (i < cantColumnas) {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                modelo.addRow(fila);
            }

            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Por Nombre:\n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    //Busca Empleados por su estado
    public TableModel buscarDatosEstado(int estadoEmpleado, DefaultTableModel modelo) {
        try {
            st = con.createStatement();
            String registro = "SELECT a.CODIGO_EMPLEADO as Codigo, a.NOMBRE_EMPLEADO as Nombre ,"
                    + " a.APELLIDO_EMPLEADO AS Apellido,a.DPI ,"
                    + " a.TELEFONO_EMPLEADO as Telefono, a.DIRECCION_EMPLEADO as Direccion,"
                    + " a.EMAIL_EMPLEADO as Email, gn.nombre_tipogenero AS Genero, a.fechacomienzo as Fecha,"
                    + " em.nombre_tipoempleado as Tipo, a.salariodia_empleado as Salario, es.NOMBRE_ESTADOEMPLEADO as Estado "
                    + " FROM " + conectar.user_admin + ".EMPLEADO a "
                    + " INNER JOIN "+conectar.user_admin+".tipo_genero gn on (a.GENERO_EMPLEADO = gn.codigo_tipogenero) "
                    + " INNER JOIN "+conectar.user_admin+".tipo_empleado em on (a.tipo_empleado = em.codigo_tipoempleado) "
                    + " INNER JOIN "+conectar.user_admin+".estado_empleado es on (a.codigo_estadoempleado = es.codigo_estadoempleado) WHERE a.CODIGO_ESTADOEMPLEADO = '" + estadoEmpleado + "'";
            st.execute(registro);
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }

            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas + 1; i++) {
                    if (i < cantColumnas) {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                modelo.addRow(fila);
            }

            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Empleados Segun Su Estado:\n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    //Busca Empleados segun su Genero
    public TableModel buscarDatosGenero(int generoEmpleado, DefaultTableModel modelo) {
        try {
            st = con.createStatement();
            String registro = "SELECT a.CODIGO_EMPLEADO as Codigo, a.NOMBRE_EMPLEADO as Nombre ,"
                    + " a.APELLIDO_EMPLEADO AS Apellido,a.DPI,"
                    + " a.TELEFONO_EMPLEADO as Telefono, a.DIRECCION_EMPLEADO as Direccion,"
                    + " a.EMAIL_EMPLEADO as Email, gn.nombre_tipogenero AS Genero, a.fechacomienzo as Fecha,"
                    + " em.nombre_tipoempleado as Tipo, a.salariodia_empleado as Salario, es.NOMBRE_ESTADOEMPLEADO as Estado "
                    + " FROM " + conectar.user_admin + ".EMPLEADO a "
                    + " INNER JOIN "+conectar.user_admin+".tipo_genero gn on (a.GENERO_EMPLEADO = gn.codigo_tipogenero) "
                    + " INNER JOIN "+conectar.user_admin+".tipo_empleado em on (a.tipo_empleado = em.codigo_tipoempleado)"
                    + " INNER JOIN "+conectar.user_admin+".estado_empleado es on (a.codigo_estadoempleado = es.codigo_estadoempleado) WHERE a.GENERO_EMPLEADO = '" + generoEmpleado + "'";
            st.execute(registro);
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }

            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas + 1; i++) {
                    if (i < cantColumnas) {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                modelo.addRow(fila);
            }

            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Empleados Segun Su Genero:\n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    //Busca Empleados Segun el Tipo
    public TableModel buscarDatosTipo(int tipoEmpleado, DefaultTableModel modelo) {
        try {
            st = con.createStatement();
            String registro = "SELECT a.CODIGO_EMPLEADO as Codigo, a.NOMBRE_EMPLEADO as Nombre ,"
                    + " a.APELLIDO_EMPLEADO AS Apellido,a.DPI,"
                    + " a.TELEFONO_EMPLEADO as Telefono, a.DIRECCION_EMPLEADO as Direccion,"
                    + " a.EMAIL_EMPLEADO as Email, gn.nombre_tipogenero AS Genero, a.fechacomienzo as Fecha,"
                    + " em.nombre_tipoempleado as Tipo, a.salariodia_empleado as Salario, es.NOMBRE_ESTADOEMPLEADO as Estado "
                    + " FROM " + conectar.user_admin + ".EMPLEADO a "
                    + " INNER JOIN "+conectar.user_admin+".tipo_genero gn on (a.GENERO_EMPLEADO = gn.codigo_tipogenero) "
                    + " INNER JOIN "+conectar.user_admin+".tipo_empleado em on (a.tipo_empleado = em.codigo_tipoempleado) "
                    + " INNER JOIN "+conectar.user_admin+".estado_empleado es on (a.codigo_estadoempleado = es.codigo_estadoempleado) WHERE a.TIPO_EMPLEADO = '" + tipoEmpleado + "'";
            st.execute(registro);
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }

            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas + 1; i++) {
                    if (i < cantColumnas) {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                modelo.addRow(fila);
            }

            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Al Mostrar Datos " + e);
            System.out.println(e);
        }
        return modelo;
    }

    //Busca Empleado Segun un Codigo
    public TableModel buscarDatos(int codigoEmpleado, DefaultTableModel modelo) {
        try {
            st = con.createStatement();
            String registro = "SELECT a.CODIGO_EMPLEADO as Codigo, a.NOMBRE_EMPLEADO as Nombre ,"
                    + " a.APELLIDO_EMPLEADO AS Apellido,a.DPI,"
                    + " a.TELEFONO_EMPLEADO as Telefono, a.DIRECCION_EMPLEADO as Direccion,"
                    + " a.EMAIL_EMPLEADO as Email, gn.nombre_tipogenero AS Genero, a.fechacomienzo as Fecha,"
                    + " em.nombre_tipoempleado as Tipo, a.salariodia_empleado as Salario, es.NOMBRE_ESTADOEMPLEADO as Estado "
                    + " FROM " + conectar.user_admin + ".EMPLEADO a "
                    + "                       INNER JOIN "+conectar.user_admin+".tipo_genero gn on (a.GENERO_EMPLEADO = gn.codigo_tipogenero) "
                    + "                       INNER JOIN "+conectar.user_admin+".tipo_empleado em on (a.tipo_empleado = em.codigo_tipoempleado) "
                    + "                       INNER JOIN "+conectar.user_admin+".estado_empleado es on (a.codigo_estadoempleado = es.codigo_estadoempleado) WHERE CODIGO_EMPLEADO = '" + codigoEmpleado + "'";
            st.execute(registro);
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas + 1; i++) {
                    if (i < cantColumnas) {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Empleado:\n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    //Busca Empleados Segun una Fecha de inicio
    public TableModel buscarDatosFecha(DefaultTableModel modelo, String fecha) {
        try {
            st = con.createStatement();
            String registro = "SELECT a.CODIGO_EMPLEADO as Codigo, a.NOMBRE_EMPLEADO as Nombre ,"
                    + " a.APELLIDO_EMPLEADO AS Apellido,a.DPI,"
                    + " a.TELEFONO_EMPLEADO as Telefono, a.DIRECCION_EMPLEADO as Direccion,"
                    + " a.EMAIL_EMPLEADO as Email, gn.nombre_tipogenero AS Genero, a.fechacomienzo as Fecha,"
                    + " em.nombre_tipoempleado as Tipo, a.salariodia_empleado as Salario, es.NOMBRE_ESTADOEMPLEADO as Estado "
                    + " FROM " + conectar.user_admin + ".EMPLEADO a "
                    + "                       INNER JOIN "+conectar.user_admin+".tipo_genero gn on (a.GENERO_EMPLEADO = gn.codigo_tipogenero)  "
                    + "                       INNER JOIN "+conectar.user_admin+".tipo_empleado em on (a.tipo_empleado = em.codigo_tipoempleado) "
                    + "                       INNER JOIN "+conectar.user_admin+".estado_empleado es on (a.codigo_estadoempleado = es.codigo_estadoempleado) where TRUNC (FECHACOMIENZO) = TO_DATE ('" + fecha + "', 'DD/MM/RR')";
            st.execute(registro);
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            for (int i = 1; i < cantColumnas + 1; i++) {
                modelo.addColumn(rsMD.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas + 1; i++) {
                    if (i < cantColumnas) {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Buscar Segun Una Fecha De Inicio" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    //Elimina un Empleado Segun El Codigo
    public void eliminarDatos(int codigoEmpleado) {
        try {
            st = con.createStatement();;
            String registro = "DELETE FROM " + conectar.user_admin + ".EMPLEADO WHERE CODIGO_EMPLEADO = '" + codigoEmpleado + "'";
            st.execute(registro);
            JOptionPane.showMessageDialog(pa, "Dato Eliminado Con Exito", "<SQL> EMPLEADOS", JOptionPane.OK_OPTION, ico());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Eliminar: \n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Guarda Un Nuevo Empleado
    public void guardar(String ruta, String nombreEmpleado, int codigoEmpleado, String apellidoEmpleado,
            int telefonoEmpleado,String dpi, String direccionEmpleado, String emailEmpleado, String fecha,
            String horaRegistro, int codigoGenero, int tipoempleado, float salariodia, int estadoEmpleado) {
            String insert = "INSERT INTO "+conectar.user_admin+".EMPLEADO (CODIGO_EMPLEADO,NOMBRE_EMPLEADO,APELLIDO_EMPLEADO,dpi,"
                + "         DPI_EMPLEADO,TELEFONO_EMPLEADO,DIRECCION_EMPLEADO,EMAIL_EMPLEADO,GENERO_EMPLEADO,"
                + "         TIPO_EMPLEADO, SALARIODIA_EMPLEADO,CODIGO_ESTADOEMPLEADO,FECHACOMIENZO) "
                + "         values(?,?,?,?,?,?,?,?,?,?,?,?,TO_DATE('" + fecha + " " + horaRegistro + "','DD-MM-YYYY HH24:MI:SS'))";
        PreparedStatement ps = null;
        try {
            File file = new File(ruta);
            InputStream fi = new FileInputStream(file);
            ps = con.prepareStatement(insert);
            ps.setInt(1, codigoEmpleado);
            ps.setString(2, nombreEmpleado);
            ps.setString(3, apellidoEmpleado);
            ps.setString(4, dpi);
            ps.setBlob(5, fi);
            ps.setInt(6, telefonoEmpleado);
            ps.setString(7, direccionEmpleado);
            ps.setString(8, emailEmpleado);
            ps.setInt(9, codigoGenero);
            ps.setInt(10, tipoempleado);
            ps.setFloat(11, salariodia);
            ps.setInt(12, estadoEmpleado);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(pa, "Dato Guardado Con Exito", "<SQL> EMPLEADOS", JOptionPane.OK_OPTION, ico());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Guardar: \n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }

     //Guarda Un Nuevo Empleado
    public void guardarSF( String nombreEmpleado, int codigoEmpleado, String apellidoEmpleado,
            int telefonoEmpleado,String dpi, String direccionEmpleado, String emailEmpleado, String fecha,
            String horaRegistro, int codigoGenero, int tipoempleado, float salariodia, int estadoEmpleado) {
            String insert = "INSERT INTO "+conectar.user_admin+".EMPLEADO (CODIGO_EMPLEADO,NOMBRE_EMPLEADO,APELLIDO_EMPLEADO,dpi,"
                + "         TELEFONO_EMPLEADO,DIRECCION_EMPLEADO,EMAIL_EMPLEADO,GENERO_EMPLEADO,"
                + "         TIPO_EMPLEADO, SALARIODIA_EMPLEADO,CODIGO_ESTADOEMPLEADO,FECHACOMIENZO) "
                + "         values(?,?,?,?,?,?,?,?,?,?,?,TO_DATE('" + fecha + " " + horaRegistro + "','DD-MM-YYYY HH24:MI:SS'))";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(insert);
            ps.setInt(1, codigoEmpleado);
            ps.setString(2, nombreEmpleado);
            ps.setString(3, apellidoEmpleado);
            ps.setString(4, dpi);
            ps.setInt(5, telefonoEmpleado);
            ps.setString(6, direccionEmpleado);
            ps.setString(7, emailEmpleado);
            ps.setInt(8, codigoGenero);
            ps.setInt(9, tipoempleado);
            ps.setFloat(10, salariodia);
            ps.setInt(11, estadoEmpleado);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(pa, "Dato Guardado Con Exito", "<SQL> EMPLEADOS", JOptionPane.OK_OPTION, ico());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Guardar: \n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Actualiza Datos de Empleado
    public void actualizarDatosSinFoto(String ruta, String nombreEmpleado, int codigoEmpleado, String apellidoEmpleado,
            int telefonoEmpleado, String direccionEmpleado, String emailEmpleado, String fecha,String dpi,
            String horaRegistro, int codigoGenero, int tipoempleado, float salariodia, int estadoEmpleado) {
        try {
            st = con.createStatement();
            String registro = " UPDATE "+conectar.user_admin+".EMPLEADO SET NOMBRE_EMPLEADO = '" + nombreEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro);
            
            String registro12 = " UPDATE "+conectar.user_admin+".EMPLEADO SET DPI = '" + dpi + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro12);
            
            String registro1 = " UPDATE "+conectar.user_admin+".EMPLEADO SET APELLIDO_EMPLEADO = '" + apellidoEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro1);
            String registro2 = " UPDATE "+conectar.user_admin+".EMPLEADO SET TELEFONO_EMPLEADO = '" + telefonoEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro2);
            String registro3 = " UPDATE "+conectar.user_admin+".EMPLEADO SET DIRECCION_EMPLEADO = '" + direccionEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro3);
            String registro4 = " UPDATE "+conectar.user_admin+".EMPLEADO SET EMAIL_EMPLEADO = '" + emailEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro4);
            String registro5 = " UPDATE "+conectar.user_admin+".EMPLEADO SET GENERO_EMPLEADO = '" + codigoGenero + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro5);
            String registro6 = " UPDATE "+conectar.user_admin+".EMPLEADO SET FECHACOMIENZO = '" + fecha + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro6);
            String registro7 = " UPDATE "+conectar.user_admin+".EMPLEADO SET TIPO_EMPLEADO = '" + tipoempleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro7);
            String registro8 = " UPDATE "+conectar.user_admin+".EMPLEADO SET SALARIODIA_EMPLEADO = '" + salariodia + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro8);
            String registro9 = " UPDATE "+conectar.user_admin+".EMPLEADO SET CODIGO_ESTADOEMPLEADO = '" + estadoEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro9);
            JOptionPane.showMessageDialog(pa, "Datos Actualizados Con Exito", "<SQL> EMPLEADOS", JOptionPane.OK_OPTION, ico());
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(pa, "Error A Actualizar Datos: \n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Actualiza Datos de Empleado
    public void actualizarDatosConFoto(String ruta, String nombreEmpleado, int codigoEmpleado, String apellidoEmpleado,
            int telefonoEmpleado, String direccionEmpleado, String emailEmpleado, String fecha,String dpi,
            String horaRegistro, int codigoGenero, int tipoempleado, float salariodia, int estadoEmpleado) {
        try {
            st = con.createStatement();
            String registro = " UPDATE "+conectar.user_admin+".EMPLEADO SET NOMBRE_EMPLEADO = '" + nombreEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro);
            String registro12 = " UPDATE "+conectar.user_admin+".EMPLEADO SET DPI = '" + dpi + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro12);
            String registro1 = " UPDATE "+conectar.user_admin+".EMPLEADO SET APELLIDO_EMPLEADO = '" + apellidoEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro1);
            String registro2 = " UPDATE "+conectar.user_admin+".EMPLEADO SET TELEFONO_EMPLEADO = '" + telefonoEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro2);
            String registro3 = " UPDATE "+conectar.user_admin+".EMPLEADO SET DIRECCION_EMPLEADO = '" + direccionEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro3);
            String registro4 = " UPDATE "+conectar.user_admin+".EMPLEADO SET EMAIL_EMPLEADO = '" + emailEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro4);
            String registro5 = " UPDATE "+conectar.user_admin+".EMPLEADO SET GENERO_EMPLEADO = '" + codigoGenero + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro5);
            String registro6 = " UPDATE "+conectar.user_admin+".EMPLEADO SET FECHACOMIENZO = '" + fecha + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro6);
            String registro7 = " UPDATE "+conectar.user_admin+".EMPLEADO SET TIPO_EMPLEADO = '" + tipoempleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro7);
            String registro8 = " UPDATE "+conectar.user_admin+".EMPLEADO SET SALARIODIA_EMPLEADO = '" + salariodia + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro8);
            String registro9 = " UPDATE "+conectar.user_admin+".EMPLEADO SET CODIGO_ESTADOEMPLEADO = '" + estadoEmpleado + "' WHERE UPPER(CODIGO_EMPLEADO) = " + codigoEmpleado + " ";
            st.execute(registro9);
            String UPDATE = "UPDATE "+conectar.user_admin+".EMPLEADO SET DPI_EMPLEADO = ? WHERE CODIGO_EMPLEADO = " + codigoEmpleado + " ";
            PreparedStatement ps = null;
            File file = new File(ruta);
            InputStream fi = new FileInputStream(file);
            ps = con.prepareStatement(UPDATE);
            ps.setBlob(1, fi);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(pa, "Datos Actualizados Con Exito", "<SQL> EMPLEADOS", JOptionPane.OK_OPTION, ico());
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(pa, "Error A Actualizar Datos: \n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(pa, "Datos Actualizados Con Exito", "<SQL> EMPLEADOS", JOptionPane.OK_OPTION, ico());
        }
    }

    //Devuelve el nombre del empleado
    public String getNombreEmpleado(int codigoEmpleado) {
        DefaultTableModel modelo = new DefaultTableModel();
        String nombreEmpleado = null;
        try {
            st = con.createStatement();
            String registro = "SELECT NOMBRE_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO WHERE UPPER(CODIGO_EMPLEADO) = '" + codigoEmpleado + "'";
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
                    if (i == 1) {
                        nombreEmpleado = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Nombre Del Empleado:\n " + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return nombreEmpleado;
    }

    //Devuelve el Codigo Del Empleado segun el nombre
    public int getCodigoEmpleado(String nombreEmpleado) {
        DefaultTableModel modelo = new DefaultTableModel();
        int codigoEmpleado = 0;
        try {
            st = con.createStatement();
            String registro = "SELECT CODIGO_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO WHERE NOMBRE_EMPLEADO = '" + nombreEmpleado + "'";
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
                        codigoEmpleado = Integer.parseInt(fila[i].toString());
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver El Codigo Del Empleado " + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return codigoEmpleado;
    }

    //Devuelve el codigo del empleado segun el apellido
    public int getCodigoEmpleadoApellido(String apellidoEmpleado) {
        DefaultTableModel modelo = new DefaultTableModel();
        int codigoEmpleado = 0;
        try {
            st = con.createStatement();
            String registro = "SELECT CODIGO_EMPLEADO FROM " + conectar.user_admin + ".DB_ADMIN.EMPLEADO WHERE APELLIDO_EMPLEADO = '" + apellidoEmpleado + "'";
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
                        codigoEmpleado = Integer.parseInt(fila[i].toString());
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver El Codigo Del Empleado:\n " + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return codigoEmpleado;
    }

    //Devuelve el codigo del empleado segun el apellido
    public int getDPI(int codigoEmpleado) {
        DefaultTableModel modelo = new DefaultTableModel();
        int dpi = 0;
        try {
            st = con.createStatement();
            String registro = "SELECT DPI FROM " + conectar.user_admin + ".DB_ADMIN.EMPLEADO WHERE CODIGO_EMPLEADO = '" + codigoEmpleado + "'";
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
                        dpi = Integer.parseInt(fila[i].toString());
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver El Codigo Del Empleado:\n " + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return dpi;
    }
    
    //Devuelve el Telefono del Empleado Segun su Codigo De Empleado
    public String getTelefonoEmpleado(int codigoEmpleado) {
        DefaultTableModel modelo = new DefaultTableModel();
        String TelefonoEmpleado = null;
        try {
            st = con.createStatement();
            String registro = "SELECT TELEFONO_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO WHERE UPPER(CODIGO_EMPLEADO) = '" + codigoEmpleado + "'";
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
                    if (i == 1) {
                        TelefonoEmpleado = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver El Telefono Del Empleado:\n " + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return TelefonoEmpleado;
    }

    //Devuelve la Direccion Del Empleado Segun el Codigo
    public String getDireccionEmpleado(int codigoEmpleado) {
        DefaultTableModel modelo = new DefaultTableModel();
        String DireccionEmpleado = null;
        try {
            st = con.createStatement();
            String registro = "SELECT DIRECCION_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO WHERE UPPER(CODIGO_EMPLEADO) = '" + codigoEmpleado + "'";
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
                    if (i == 1) {
                        DireccionEmpleado = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver La Direccion del Empleado:\n " + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return DireccionEmpleado;
    }

    //Devuelve El E-mail del Empleado segun el codigo
    public String getEmailEmpleado(int codigoEmpleado) {
        DefaultTableModel modelo = new DefaultTableModel();
        String EmailEmpleado = null;
        try {
            st = con.createStatement();
            String registro = "SELECT EMAIL_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO WHERE UPPER(CODIGO_EMPLEADO) = '" + codigoEmpleado + "'";
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
                    if (i == 1) {
                        EmailEmpleado = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver El Correo Electronico:\n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return EmailEmpleado;
    }

    //Devuelve El Salario del Empleado Segun codigo
    public String getSalarioEmpleado(int codigoEmpleado) {
        DefaultTableModel modelo = new DefaultTableModel();
        String SalarioEmpleado = null;
        try {
            st = con.createStatement();
            String registro = "SELECT SALARIODIA_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO WHERE UPPER(CODIGO_EMPLEADO) = '" + codigoEmpleado + "'";
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
                    if (i == 1) {
                        SalarioEmpleado = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver El Salario:\n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return SalarioEmpleado;
    }

    //Devuelve los apellidos del empleado segun su codigo
    public String getApellidoEmpleado(int codigoEmpleado) {
        DefaultTableModel modelo = new DefaultTableModel();
        String ApellidoEmpleado = null;
        try {
            st = con.createStatement();
            String registro = "SELECT APELLIDO_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO WHERE UPPER(CODIGO_EMPLEADO) = '" + codigoEmpleado + "'";
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
                    if (i == 1) {
                        ApellidoEmpleado = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Apellidos:\n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return ApellidoEmpleado;
    }

    //Devuelve El Genero Del Empleado Segun el Codigo
    public String getGenero(int codigoEmpleado) {
        String Genero = null;
        DefaultTableModel modelo = new DefaultTableModel();
        try {
            st = con.createStatement();
            String registro = "SELECT GE.NOMBRE_TIPOGENERO FROM " + conectar.user_admin + ".TIPO_GENERO GE INNER JOIN "+conectar.user_admin+".EMPLEADO NO ON(QU.CODIGO_TIPOQUIMICO = NO.CODIGO_TIPOQUIMICO) WHERE CODIGO_EMPLEADO = '" + 2 + "'";
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
                        Genero = fila[i].toString();
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Genero Del Empleado:\n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return Genero;
    }

    //Devuelve El Dpi Segun el codigo 
    public Blob getBlobImagen(int codigoEmpleado) {
        Blob blob = null;
        try {
            st = con.createStatement();
            String registro = "SELECT DPI_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO WHERE CODIGO_EMPLEADO = " + codigoEmpleado + " ";
            rs = st.executeQuery(registro);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    if (i < cantColumnas) {
                        blob = rs.getBlob(i + 1);
                    }
                }
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al Devolver El DPI:\n" + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return blob;
    }

    //Valida si Existe Empleado
    public boolean existeEmpleado(int codigoEmpleado) {
        boolean existe = false;
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            st = con.createStatement();
            String registro = "SELECT * FROM " + conectar.user_admin + ".EMPLEADO WHERE CODIGO_EMPLEADO = '" + codigoEmpleado + "'";
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
                    if (i == 1) {
                        existe = true;
                    }
                }
                modelo.addRow(fila);
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(pa, "Error Al validar Existencia:\n " + e, "<SQL> EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }

    //Devuelve el ultimo Codigo de empleado
    public int getRegistroMaximo() {
        DefaultTableModel modelo = new DefaultTableModel();
        int registroMaximo = 0;
        try {
            st = con.createStatement();
            String registro = "select max(CODIGO_EMPLEADO) FROM " + conectar.user_admin + ".EMPLEADO ";
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
            rs.close();
        } catch (Exception e) {
        }
        return registroMaximo;
    }
}
