package VIEWS;

import BDD.SQLEMPLEADO;
import clases.ConexionBD;
import com.sun.glass.events.KeyEvent;
import java.awt.FileDialog;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.text.DateFormat;
import java.sql.*;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class EMPLEADO extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Variables"> 
    String ruta = null;
    SQLEMPLEADO Empleado = new SQLEMPLEADO(this);
    private final ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection();
    private static Statement st;
    private static ResultSet rs;
    private Icon icono = null;
    private VIEWS.VerImagen verImagen = new VIEWS.VerImagen(this, true);
    //---------------------------------------------------------------------// </editor-fold>

    public EMPLEADO() {
        initComponents();
        limpiarEstadoEmpleado();
        limpiarEmpleado();
        limpiarGenero();
        limpiarTipoEmpleado();
        estadoEmpleado();
        try {
            listaEmpleado();
        } catch (SQLException ex) {
            Logger.getLogger(EMPLEADO.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        genero();
        try {
            listaEmpleado();
        } catch (SQLException ex) {
            Logger.getLogger(EMPLEADO.class.getName()).log(Level.SEVERE, null, ex);
        }
        tipoEmpleado();
        AutoCompleteDecorator.decorate(txt_nombreEmpleado);
        jButtonActualizar.setEnabled(false);
        jDateFecha2.setDate(new Date());
        tabla.getTableHeader().setReorderingAllowed(false);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/Cuadrialla Seleccionado.png")).getImage());
        txt_codigoEmpleado.setText(Integer.toString(Empleado.getRegistroMaximo() + 1));
    }

    // <editor-fold defaultstate="collapsed" desc="Metodos a la Base de Datos"> 
    private void eliminarDatos() {
        int codigoEmpleado = Integer.parseInt(txt_codigoEmpleado.getText());
        Empleado.eliminarDatos(codigoEmpleado);
        jButtonActualizar.setEnabled(false);
        //jButtonEliminar.setEnabled(false);
    }

    private void actualizarDatos() {
        if (txt_codigoEmpleado.getText().length() > 0) {
            try {
                int codigoEmpleado = Integer.parseInt(txt_codigoEmpleado.getText());
                if (codigoEmpleado > 0) {
                    if (Empleado.existeEmpleado(codigoEmpleado) == true) {
                        if (txt_nombreEmpleado.getSelectedItem().toString().length() > 0) {
                            if (txt_apellidoEmpleado.getText().length() > 0) {
                                if (txt_telefono.getText().length() > 0) {
                                    try {
                                        int telefono = Integer.parseInt(txt_telefono.getText());
                                        if (telefono > 0) {
                                            if (txt_telefono.getText().length() == 8) {
                                                if (txt_direccion.getText().length() > 0) {
                                                            if (txt_dpi.getText().length() == 13) {

                                                                if (txt_salario.getText().length() > 0) {
                                                                    try {
                                                                        float salarioEmpleado = Float.parseFloat(txt_salario.getText());
                                                                        if (salarioEmpleado > 0) {
                                                                            int telefonoEmpleado = Integer.parseInt(txt_telefono.getText());
                                                                            String nombreEmpleado = txt_nombreEmpleado.getSelectedItem().toString();
                                                                            String apellidoEmpleado = txt_apellidoEmpleado.getText();
                                                                            String direccionEmpleado = txt_direccion.getText();
                                                                            String emailEmpleado = txt_Email.getText();
                                                                            String dpiEmpleado = txt_dpi.getText();
                                                                            int codigoGenero = codigoGenero();
                                                                            int tipoempleado = codigoTipoEmpleado();
                                                                            int estadoEmpleado = codigoEstadoEmpleado();

                                                                            DateFormat variableFecha = DateFormat.getDateInstance();
                                                                            String fecha = variableFecha.format(jDateFecha2.getDate());
                                                                            Calendar horaSistema = Calendar.getInstance();
                                                                            String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
                                                                            String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
                                                                            String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
                                                                            String horaRegistro = hora + ":" + minutos + ":" + segundos;
                                                                                int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Actualizar?", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                                if (respuesta == 0) {
                                                                                    jButtonGuardar.setEnabled(true);//guardar
                                                                                    Empleado.actualizarDatosSinFoto(ruta, nombreEmpleado, codigoEmpleado, apellidoEmpleado, telefonoEmpleado, direccionEmpleado, emailEmpleado, fecha, dpiEmpleado, horaRegistro, codigoGenero, tipoempleado, salarioEmpleado, estadoEmpleado);
                                                                                    mostrarDatos();
                                                                                    limpiarjText();
                                                                                    icono = null;
                                                                                    txt_codigoEmpleado.setText(Integer.toString(Empleado.getRegistroMaximo() + 1));
                                                                                }
                                                                        } else {
                                                                            JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Salario", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                            txt_salario.setText("");
                                                                            txt_salario.requestFocus();
                                                                        }
                                                                    } catch (Exception e) {
                                                                        JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Salario", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                        txt_salario.setText("");
                                                                        txt_salario.requestFocus();
                                                                    }
                                                                } else {
                                                                    JOptionPane.showConfirmDialog(this, "No Dejes El Salario Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                    txt_salario.setText("");
                                                                    txt_salario.requestFocus();
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(this, "Ingrese DPI con 13 digitos", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                txt_dpi.setText("");
                                                                txt_dpi.requestFocus();
                                                            }
                                                } else {
                                                    JOptionPane.showConfirmDialog(this, "No Dejes La Direccion Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                    txt_direccion.requestFocus();
                                                }
                                            } else {
                                                JOptionPane.showConfirmDialog(this, "Ingresa Numero de Telefono de 8 Digitos", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                txt_telefono.requestFocus();
                                            }
                                        } else {
                                            JOptionPane.showConfirmDialog(this, "No Ingrese Signos En El Telefono", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                            txt_telefono.setText("");
                                            txt_telefono.requestFocus();
                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showConfirmDialog(this, "No Ingrese Signos En El Telefono", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                        txt_telefono.setText("");
                                        txt_telefono.requestFocus();
                                    }
                                } else {
                                    if (txt_direccion.getText().length() > 0) {
                                                if (txt_dpi.getText().length() == 13) {

                                                    if (txt_salario.getText().length() > 0) {
                                                        try {
                                                            float salarioEmpleado = Float.parseFloat(txt_salario.getText());
                                                            if (salarioEmpleado > 0) {
                                                                String nombreEmpleado = txt_nombreEmpleado.getSelectedItem().toString();
                                                                String apellidoEmpleado = txt_apellidoEmpleado.getText();
                                                                String direccionEmpleado = txt_direccion.getText();
                                                                String emailEmpleado = txt_Email.getText();
                                                                int codigoGenero = codigoGenero();
                                                                int tipoempleado = codigoTipoEmpleado();
                                                                int estadoEmpleado = codigoEstadoEmpleado();
                                                                DateFormat variableFecha = DateFormat.getDateInstance();
                                                                String fecha = variableFecha.format(jDateFecha2.getDate());
                                                                Calendar horaSistema = Calendar.getInstance();
                                                                String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
                                                                String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
                                                                String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
                                                                String horaRegistro = hora + ":" + minutos + ":" + segundos;
                                                                String dpiEmpleado = txt_dpi.getText();
                                                                    int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Actualizar?", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                    if (respuesta == 0) {
                                                                        jButtonGuardar.setEnabled(true);//guardar
                                                                        Empleado.actualizarDatosSinFoto(ruta, nombreEmpleado, codigoEmpleado, apellidoEmpleado, 0, direccionEmpleado, emailEmpleado, fecha, dpiEmpleado, horaRegistro, codigoGenero, tipoempleado, salarioEmpleado, estadoEmpleado);
                                                                        mostrarDatos();
                                                                        limpiarjText();
                                                                        icono = null;
                                                                        txt_codigoEmpleado.setText(Integer.toString(Empleado.getRegistroMaximo() + 1));
                                                                    }
                                                            } else {
                                                                JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Salario", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                txt_salario.setText("");
                                                                txt_salario.requestFocus();
                                                            }
                                                        } catch (Exception e) {
                                                            JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Salario", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                            txt_salario.setText("");
                                                            txt_salario.requestFocus();
                                                        }
                                                    } else {
                                                        JOptionPane.showConfirmDialog(this, "No Dejes El Salario Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                        txt_salario.setText("");
                                                        txt_salario.requestFocus();
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(this, "Ingrese DPI con 13 digitos", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                    txt_dpi.setText("");
                                                    txt_dpi.requestFocus();
                                                }
                                    } else {
                                        JOptionPane.showConfirmDialog(this, "No Dejes La Direccion Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                        txt_direccion.requestFocus();
                                    }
                                }
                            } else {
                                JOptionPane.showConfirmDialog(this, "No Dejes Los Apellidos Vacios", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                txt_apellidoEmpleado.requestFocus();
                            }
                        } else {
                            JOptionPane.showConfirmDialog(this, "No Dejes El Nombre Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                            txt_nombreEmpleado.requestFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Ese Codigo No Pertenece A Ningun Empleado", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                        txt_codigoEmpleado.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ese Codigo No Pertenece A Ningun Empleado", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                    txt_codigoEmpleado.requestFocus();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ese Codigo No Pertenece A Ningun Empleado", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                txt_codigoEmpleado.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Ese Codigo No Pertenece A Ningun Empleado", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
            txt_codigoEmpleado.requestFocus();
        }
    }

    private void guardar() {
        if (txt_codigoEmpleado.getText().length() > 0) {
            try {
                int codigoEmpleado = Integer.parseInt(txt_codigoEmpleado.getText());
                if (codigoEmpleado > 0) {
                    if (Empleado.existeEmpleado(codigoEmpleado) == false) {
                        if (txt_nombreEmpleado.getSelectedItem().toString().length() > 0) {
                            if (txt_apellidoEmpleado.getText().length() > 0) {
                                if (txt_telefono.getText().length() > 0) {
                                    try {
                                        int telefono = Integer.parseInt(txt_telefono.getText());
                                        if (telefono > 0) {
                                            if (txt_telefono.getText().length() == 8) {
                                                        if (txt_dpi.getText().length() == 13) {
                                                            if (txt_salario.getText().length() > 0) {
                                                                try {
                                                                    float salarioEmpleado = Float.parseFloat(txt_salario.getText());
                                                                    if (salarioEmpleado > 0) {
                                                                        int telefonoEmpleado = Integer.parseInt(txt_telefono.getText());
                                                                        String nombreEmpleado = txt_nombreEmpleado.getSelectedItem().toString();
                                                                        String apellidoEmpleado = txt_apellidoEmpleado.getText();
                                                                        String direccionEmpleado = txt_direccion.getText();
                                                                        String emailEmpleado = txt_Email.getText();
                                                                        int codigoGenero = codigoGenero();
                                                                        int tipoempleado = codigoTipoEmpleado();
                                                                        int estadoEmpleado = codigoEstadoEmpleado();
                                                                        DateFormat variableFecha = DateFormat.getDateInstance();
                                                                        String fecha = variableFecha.format(jDateFecha2.getDate());
                                                                        Calendar horaSistema = Calendar.getInstance();
                                                                        String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
                                                                        String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
                                                                        String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
                                                                        String horaRegistro = hora + ":" + minutos + ":" + segundos;
                                                                        String dpiEmpleado = txt_dpi.getText();
                                                                        int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Guardar?", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                        if (respuesta == 0) {
                                                                                Empleado.guardarSF(nombreEmpleado, codigoEmpleado, apellidoEmpleado, telefonoEmpleado, dpiEmpleado, direccionEmpleado, emailEmpleado, fecha, horaRegistro, codigoGenero, tipoempleado, salarioEmpleado, estadoEmpleado);
                                                                                mostrarDatos();
                                                                                limpiarjText();
                                                                                txt_codigoEmpleado.setText(Integer.toString(Empleado.getRegistroMaximo() + 1));
                                                                            }
                                                                    } else {
                                                                        JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Salario", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                        txt_salario.setText("");
                                                                        txt_salario.requestFocus();
                                                                    }
                                                                } catch (Exception e) {
                                                                    JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Salario", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                    txt_salario.setText("");
                                                                    txt_salario.requestFocus();
                                                                }
                                                            } else {
                                                                JOptionPane.showConfirmDialog(this, "No Dejes El Salario Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                txt_salario.setText("");
                                                                txt_salario.requestFocus();
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(this, "Ingrese DPI con 13 digitos", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                            txt_dpi.setText("");
                                                            txt_dpi.requestFocus();
                                                        }
                                                    
                                            } else {
                                                JOptionPane.showConfirmDialog(this, "Ingresa Numero de Telefono de 8 Digitos", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                txt_telefono.requestFocus();
                                            }
                                        } else {
                                            JOptionPane.showConfirmDialog(this, "No Ingrese Signos En El Telefono", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                            txt_telefono.setText("");
                                            txt_telefono.requestFocus();
                                        }
                                    } catch (Exception e) {
                                        JOptionPane.showConfirmDialog(this, "No Ingrese Signos En El Telefono", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                        txt_telefono.setText("");
                                        txt_telefono.requestFocus();
                                    }
                                } else {
                                    if (txt_direccion.getText().length() > 0) {
                                                if (txt_dpi.getText().length() == 13) {

                                                    if (txt_salario.getText().length() > 0) {
                                                        try {
                                                            float salarioEmpleado = Float.parseFloat(txt_salario.getText());
                                                            if (salarioEmpleado > 0) {
                                                                String nombreEmpleado = txt_nombreEmpleado.getSelectedItem().toString();
                                                                String apellidoEmpleado = txt_apellidoEmpleado.getText();
                                                                String direccionEmpleado = txt_direccion.getText();
                                                                String emailEmpleado = txt_Email.getText();
                                                                int codigoGenero = codigoGenero();
                                                                int tipoempleado = codigoTipoEmpleado();
                                                                int estadoEmpleado = codigoEstadoEmpleado();
                                                                DateFormat variableFecha = DateFormat.getDateInstance();
                                                                String fecha = variableFecha.format(jDateFecha2.getDate());
                                                                Calendar horaSistema = Calendar.getInstance();
                                                                String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
                                                                String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
                                                                String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
                                                                String horaRegistro = hora + ":" + minutos + ":" + segundos;
                                                                String dpiEmpleado = txt_dpi.getText();
                                                                int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Guardar?", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                if (respuesta == 0) {
                                                                        Empleado.guardarSF(nombreEmpleado, codigoEmpleado, apellidoEmpleado, 0, dpiEmpleado, direccionEmpleado, emailEmpleado, fecha, horaRegistro, codigoGenero, tipoempleado, salarioEmpleado, estadoEmpleado);
                                                                        DefaultTableModel modelo1 = new DefaultTableModel();
                                                                        this.tabla.setModel(Empleado.mostrarDatos(modelo1));
                                                                    
                                                                }
                                                            } else {
                                                                JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Salario", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                                txt_salario.setText("");
                                                                txt_salario.requestFocus();
                                                            }
                                                        } catch (Exception e) {
                                                            JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Salario", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                            txt_salario.setText("");
                                                            txt_salario.requestFocus();
                                                        }
                                                    } else {
                                                        JOptionPane.showConfirmDialog(this, "No Dejes El Salario Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                        txt_salario.setText("");
                                                        txt_salario.requestFocus();
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(this, "Ingrese DPI con 13 digitos", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                                    txt_dpi.setText("");
                                                    txt_dpi.requestFocus();
                                                }
                                            
                                    } else {
                                        JOptionPane.showConfirmDialog(this, "No Dejes La Direccion Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                        txt_direccion.requestFocus();
                                    }
                                }
                            } else {
                                JOptionPane.showConfirmDialog(this, "No Dejes Los Apellidos Vacios", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                                txt_apellidoEmpleado.requestFocus();
                            }
                        } else {
                            JOptionPane.showConfirmDialog(this, "No Dejes El Nombre Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                            txt_nombreEmpleado.requestFocus();
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Este Codigo Ya Existe", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                        JOptionPane.showMessageDialog(this, "Usa Siguiente Codigo: " + (Empleado.getRegistroMaximo() + 1), "EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                        txt_codigoEmpleado.setText(Integer.toString(Empleado.getRegistroMaximo() + 1));
                        txt_codigoEmpleado.requestFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Codigo", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                    JOptionPane.showMessageDialog(this, "Usa Siguiente Codigo: " + (Empleado.getRegistroMaximo() + 1), "EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                    txt_codigoEmpleado.setText(Integer.toString(Empleado.getRegistroMaximo() + 1));
                    txt_codigoEmpleado.requestFocus();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "No Ingreses Signos En El Codigo", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(this, "Usa Siguiente Codigo: " + (Empleado.getRegistroMaximo() + 1), "EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                txt_codigoEmpleado.setText(Integer.toString(Empleado.getRegistroMaximo() + 1));
                txt_codigoEmpleado.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No Dejes El Codigo Vacio", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Usa Siguiente Codigo: " + (Empleado.getRegistroMaximo() + 1), "EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
            txt_codigoEmpleado.setText(Integer.toString(Empleado.getRegistroMaximo() + 1));
            txt_codigoEmpleado.requestFocus();
        }
    }// </editor-fold>

    public void listaEmpleado() throws SQLException {
        try {
            st = cn.createStatement();
            String r = "SELECT NOMBRE_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    txt_nombreEmpleado.addItem(fila[i].toString());
                }
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Al Conectar: " + e);
        }
    }

    public void limpiarEmpleado() {
        txt_nombreEmpleado.removeAllItems();
    }

    private int codigoEmpleado() {
        int codigoNombreEmpleado = 0;
        try {
            String r = "SELECT CODIGO_EMPLEADO FROM " + conectar.user_admin + ".EMPLEADO WHERE "
                    + " AND NOMBRE_EMPLEADO = '" + txt_nombreEmpleado.getSelectedItem().toString() + "'";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    codigoNombreEmpleado = Integer.parseInt(fila[i].toString());
                }
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al devolver codigo empleado");
            System.out.println(e);
        }
        return codigoNombreEmpleado;
    }

    private void limpiarjText() {
        txt_apellidoEmpleado.setText("");
        txt_telefono.setText("");;
        txt_direccion.setText("");
        txt_Email.setText("");
        txt_salario.setText("");

        txt_dpi.setText("");
        Date date = new Date();
        icono = null;
        jButtonActualizar.setEnabled(false);
        jDateFecha2.setDate(date);

    }

    public void limpiarVentana() {
        if (tabla.getRowCount() > 0) {
            DefaultTableModel model = new DefaultTableModel();
            tabla.setModel(model);
        }
    }

    private void CargarImagen() {
        try {
            FileDialog dialog = new FileDialog(this, "Cargar Imagen", FileDialog.LOAD);
            dialog.setMultipleMode(false);
            dialog.setVisible(true);
            if (dialog.getFile() != null) {
                ruta = dialog.getDirectory() + dialog.getFile();
                ImageIcon imageIcon = new ImageIcon(ruta);
                icono = new ImageIcon(imageIcon.getImage().getScaledInstance(this.verImagen.getAncho(), this.verImagen.getAlto(), Image.SCALE_SMOOTH));
            }
        } catch (Exception e) {
            System.out.println("Error al enviar imagen");
        }
    }

    private void mostrarDatos() {
        jButtonActualizar.setEnabled(false);
        jButtonBuscar.setEnabled(true);
        jButtonGuardar.setEnabled(true);
        try {
            DefaultTableModel modelo1 = new DefaultTableModel();
            this.tabla.setModel(Empleado.mostrarDatos(modelo1));
            jButton1.setSelected(false);
            icono = null;
            jButtonActualizar.setEnabled(false);
        } catch (Exception e) {
            System.out.println("error al mostrar tabla en el boton");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="Carga Los Combo Box"> 
    public void genero() {
        try {
            st = cn.createStatement();
            String r = "SELECT NOMBRE_TIPOGENERO FROM " + conectar.user_admin + ".TIPO_GENERO";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    jComboBoxGenero.addItem(fila[i].toString());
                }
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Al Mostrar Tipos De Generos", "EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int codigoGenero() {
        int codigoGENERO = 0;
        try {
            String r = "SELECT CODIGO_TIPOGENERO FROM " + conectar.user_admin + ".TIPO_GENERO WHERE NOMBRE_TIPOGENERO = '" + jComboBoxGenero.getSelectedItem().toString() + "'";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    codigoGENERO = Integer.parseInt(fila[i].toString());
                }
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Al Devolver Codigo De Genero Seleccionado", "EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        return codigoGENERO;
    }

    public void limpiarGenero() {
        jComboBoxGenero.removeAllItems();
    }

    public void tipoEmpleado() {
        try {
            st = cn.createStatement();
            String r = "SELECT NOMBRE_TIPOEMPLEADO FROM " + conectar.user_admin + ".TIPO_EMPLEADO";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    jComboBoxtipoempleado.addItem(fila[i].toString());
                }
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Al Mostrar Tipo Empleados", "EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int codigoTipoEmpleado() {
        int codigoTIPOEMPLEADO = 0;
        try {
            String r = "SELECT CODIGO_TIPOEMPLEADO FROM " + conectar.user_admin + ".TIPO_EMPLEADO WHERE NOMBRE_TIPOEMPLEADO = '" + jComboBoxtipoempleado.getSelectedItem().toString() + "'";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    codigoTIPOEMPLEADO = Integer.parseInt(fila[i].toString());
                }
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Al Devolver Codigo Tipo Empleado Seleccionado", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }
        return codigoTIPOEMPLEADO;
    }

    public void limpiarTipoEmpleado() {
        jComboBoxtipoempleado.removeAllItems();
    }

    public void estadoEmpleado() {
        try {
            st = cn.createStatement();
            String r = "SELECT NOMBRE_ESTADOEMPLEADO FROM " + conectar.user_admin + ".ESTADO_EMPLEADO";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    jComboBoxEstadoEmpleado.addItem(fila[i].toString());
                }
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Al Mostrar Estado Empleado", "EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int codigoEstadoEmpleado() {
        int codigoEstado = 0;
        try {
            String r = "SELECT CODIGO_ESTADOEMPLEADO FROM " + conectar.user_admin + ".ESTADO_EMPLEADO WHERE NOMBRE_ESTADOEMPLEADO = '" + jComboBoxEstadoEmpleado.getSelectedItem().toString() + "'";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while (rs.next()) {
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                    codigoEstado = Integer.parseInt(fila[i].toString());
                }
            }
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Al Devolver Codigo Estado Empleado Seleccionado", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }
        return codigoEstado;
    }

    public void limpiarEstadoEmpleado() {
        jComboBoxEstadoEmpleado.removeAllItems();
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Metodos De Buscar"> 
    private void buscarDatosCodigo() {
        try {
            int codigoEmpleado = Integer.parseInt(txt_codigoEmpleado.getText());
            DefaultTableModel modelo = new DefaultTableModel();
            if (Empleado.existeEmpleado(codigoEmpleado) == true) {
                this.tabla.setModel(Empleado.buscarDatos(codigoEmpleado, modelo));
                if (tabla != null) {
                    jButtonGuardar.setEnabled(false);
                    jButtonActualizar.setEnabled(true);
                    int filaSeleccionada = 0;
                    String codigo = String.valueOf(tabla.getValueAt(filaSeleccionada, 0));
                    txt_codigoEmpleado.setText(codigo);
                    txt_nombreEmpleado.setSelectedItem(String.valueOf(tabla.getValueAt(filaSeleccionada, 1)));
                    txt_apellidoEmpleado.setText(String.valueOf(tabla.getValueAt(filaSeleccionada, 2)));
                    txt_telefono.setText(String.valueOf(tabla.getValueAt(filaSeleccionada, 3)));
                    txt_direccion.setText(String.valueOf(tabla.getValueAt(filaSeleccionada, 4)));
                    txt_Email.setText(String.valueOf(tabla.getValueAt(filaSeleccionada, 5)));
                    jComboBoxGenero.setSelectedItem(String.valueOf(tabla.getValueAt(filaSeleccionada, 6)));
                    String fecha = String.valueOf(tabla.getValueAt(filaSeleccionada, 7));
                    jComboBoxtipoempleado.setSelectedItem(String.valueOf(tabla.getValueAt(filaSeleccionada, 8)));
                    txt_salario.setText(String.valueOf(tabla.getValueAt(filaSeleccionada, 9)));
                    jComboBoxEstadoEmpleado.setSelectedItem(String.valueOf(tabla.getValueAt(filaSeleccionada, 10)));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date fechaSelec = null;
                    fechaSelec = sdf.parse(fecha);
                    jDateFecha2.setDate(fechaSelec);

                    Blob blobImagen = Empleado.getBlobImagen(Integer.parseInt(codigo));
                    byte[] bytesLeidos = blobImagen.getBytes(1, (int) blobImagen.length());
                    ImageIcon imageIcon = null;
                    imageIcon = new ImageIcon(bytesLeidos);

                    icono = new ImageIcon(imageIcon.getImage().getScaledInstance(this.verImagen.getAncho(), this.verImagen.getAlto(), Image.SCALE_SMOOTH));

                }
            } else {
                JOptionPane.showMessageDialog(this, "No Existe Empleado Con Ese Codigo", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                limpiarjText();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese Codigo Valido Para Buscar", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void buscarDatosEstado() {
        try {

            DefaultTableModel modelo = new DefaultTableModel();
            jButtonActualizar.setEnabled(true);
            int estadoEmpleado = codigoEstadoEmpleado();
            //jButtonEliminar.setEnabled(true);
            jButtonGuardar.setEnabled(false);

            this.tabla.setModel(Empleado.buscarDatosEstado(estadoEmpleado, modelo));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para buscar " + e, "EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarDatosTipo() {
        try {

            DefaultTableModel modelo = new DefaultTableModel();
            jButtonActualizar.setEnabled(true);
            int tipoEmpleado = codigoTipoEmpleado();
            //jButtonEliminar.setEnabled(true);
            jButtonGuardar.setEnabled(false);

            this.tabla.setModel(Empleado.buscarDatosTipo(tipoEmpleado, modelo));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para buscar " + e, "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarDatosNombre() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            String nombreEmpleado = txt_nombreEmpleado.getSelectedItem().toString();
            this.tabla.setModel(Empleado.buscarDatosNombre(nombreEmpleado, modelo));
            if (tabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No Existen Empleados Con Este Nombre", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para buscar", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarPorFecha() {
        try {
            DateFormat variableFecha = DateFormat.getDateInstance();
            String fecha = variableFecha.format(jDateFecha2.getDate());
            DefaultTableModel modelo = new DefaultTableModel();
            this.tabla.setModel(Empleado.buscarDatosFecha(modelo, fecha));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingresa Una Fecha", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarDatosGenero() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            int generoEmpleado = codigoGenero();
            this.tabla.setModel(Empleado.buscarDatosGenero(generoEmpleado, modelo));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre para buscar" + e, "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarDatosTipoEmpleado() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            int TipoEmpleado = codigoTipoEmpleado();
            this.tabla.setModel(Empleado.buscarDatosTipo(TipoEmpleado, modelo));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingrese Codigo Para Buscar", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }

    }// </editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txt_codigoEmpleado = new javax.swing.JTextField();
        jComboBoxGenero = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JToggleButton();
        jButtonGuardar = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jButtonActualizar = new javax.swing.JButton();
        jLabelCodigo = new javax.swing.JLabel();
        jLabelCodigo1 = new javax.swing.JLabel();
        jLabelCodigo2 = new javax.swing.JLabel();
        jLabelCodigo3 = new javax.swing.JLabel();
        jLabelCodigo4 = new javax.swing.JLabel();
        txt_apellidoEmpleado = new javax.swing.JTextField();
        jLabelCodigo6 = new javax.swing.JLabel();
        txt_telefono = new javax.swing.JTextField();
        txt_direccion = new javax.swing.JTextField();
        jLabelCodigo7 = new javax.swing.JLabel();
        jLabelCodigo8 = new javax.swing.JLabel();
        txt_Email = new javax.swing.JTextField();
        jDateFecha2 = new com.toedter.calendar.JDateChooser();
        jLabelCodigo10 = new javax.swing.JLabel();
        jComboBoxtipoempleado = new javax.swing.JComboBox<>();
        jLabelCodigo11 = new javax.swing.JLabel();
        txt_salario = new javax.swing.JTextField();
        jLabelCodigo9 = new javax.swing.JLabel();
        jComboBoxEstadoEmpleado = new javax.swing.JComboBox<>();
        txt_nombreEmpleado = new javax.swing.JComboBox<>();
        jLabelCodigo12 = new javax.swing.JLabel();
        txt_dpi = new javax.swing.JTextField();
        jBottonTituloEtiqueta = new javax.swing.JToggleButton();
        jButtonExportar2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla = new rojerusan.RSTableMetro();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EMPLEADO");
        setBackground(new java.awt.Color(51, 51, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(92, 125, 137));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(124, 147, 100));

        txt_codigoEmpleado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_codigoEmpleado.setToolTipText("Codigo Empleado");
        txt_codigoEmpleado.setNextFocusableComponent(txt_nombreEmpleado);
        txt_codigoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_codigoEmpleadoActionPerformed(evt);
            }
        });
        txt_codigoEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_codigoEmpleadoKeyTyped(evt);
            }
        });

        jComboBoxGenero.setToolTipText("Genero Del Empleado");
        jComboBoxGenero.setNextFocusableComponent(jComboBoxEstadoEmpleado);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo.png"))); // NOI18N
        jButton1.setToolTipText("Mostrar Tabla");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo.png"))); // NOI18N
        jButton1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo Seleccionado.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButtonGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar Completo.png"))); // NOI18N
        jButtonGuardar.setToolTipText("Guardar");
        jButtonGuardar.setBorderPainted(false);
        jButtonGuardar.setContentAreaFilled(false);
        jButtonGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar Completo.png"))); // NOI18N
        jButtonGuardar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar Completo Seleccionado.png"))); // NOI18N
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo.png"))); // NOI18N
        jButtonBuscar.setToolTipText("Buscar");
        jButtonBuscar.setBorderPainted(false);
        jButtonBuscar.setContentAreaFilled(false);
        jButtonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonBuscar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo.png"))); // NOI18N
        jButtonBuscar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo Seleccionado.png"))); // NOI18N
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jButtonActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar Completo.png"))); // NOI18N
        jButtonActualizar.setToolTipText("Actualizar");
        jButtonActualizar.setBorderPainted(false);
        jButtonActualizar.setContentAreaFilled(false);
        jButtonActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonActualizar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar Completo.png"))); // NOI18N
        jButtonActualizar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar seleccionado Seleccionado.png"))); // NOI18N
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });

        jLabelCodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo.setText("CODIGO ");

        jLabelCodigo1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo1.setText("NOMBRES");

        jLabelCodigo2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo2.setText("GENERO");

        jLabelCodigo3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo3.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo3.setText("FECHA DE INGRESO");

        jLabelCodigo4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo4.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo4.setText("APELLIDOS");

        txt_apellidoEmpleado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_apellidoEmpleado.setToolTipText("Apellidos Del Empleado");
        txt_apellidoEmpleado.setNextFocusableComponent(txt_telefono);

        jLabelCodigo6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo6.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo6.setText("TELEFONO");

        txt_telefono.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_telefono.setToolTipText("Telefono Del Empleado");
        txt_telefono.setNextFocusableComponent(txt_Email);
        txt_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefonoKeyTyped(evt);
            }
        });

        txt_direccion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_direccion.setToolTipText("Direccion Del Empleado");
        txt_direccion.setNextFocusableComponent(jComboBoxtipoempleado);

        jLabelCodigo7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo7.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo7.setText("DIRECCION");

        jLabelCodigo8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo8.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo8.setText("EMAIL");

        txt_Email.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_Email.setToolTipText("E-mail Del Empleado");
        txt_Email.setNextFocusableComponent(txt_direccion);

        jLabelCodigo10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo10.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo10.setText("TIPO");

        jComboBoxtipoempleado.setToolTipText("Tipo De Empleado");
        jComboBoxtipoempleado.setNextFocusableComponent(jComboBoxGenero);

        jLabelCodigo11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo11.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo11.setText("SALARIO ");

        txt_salario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_salario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_salarioKeyTyped(evt);
            }
        });

        jLabelCodigo9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo9.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo9.setText("ESTADO");

        jComboBoxEstadoEmpleado.setToolTipText("Estado Del Empleado");
        jComboBoxEstadoEmpleado.setNextFocusableComponent(txt_salario);

        txt_nombreEmpleado.setEditable(true);
        txt_nombreEmpleado.setToolTipText("Nombres Del Empleado");
        txt_nombreEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nombreEmpleadoMouseClicked(evt);
            }
        });

        jLabelCodigo12.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo12.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo12.setText("DPI");

        txt_dpi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_dpi.setToolTipText("DPI Del Empleado");
        txt_dpi.setNextFocusableComponent(txt_Email);
        txt_dpi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_dpiKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelCodigo4)
                                    .addComponent(jLabelCodigo1)
                                    .addComponent(jLabelCodigo))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_codigoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_apellidoEmpleado)
                                    .addComponent(txt_nombreEmpleado, 0, 476, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelCodigo8, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelCodigo6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabelCodigo7)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_dpi, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabelCodigo3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jDateFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabelCodigo11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_salario, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabelCodigo9, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jComboBoxEstadoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelCodigo2, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                                    .addComponent(jLabelCodigo10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBoxtipoempleado, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(264, 264, 264))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabelCodigo12)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_codigoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCodigo4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_apellidoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCodigo6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_telefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCodigo8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelCodigo7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_direccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabelCodigo10)
                                            .addComponent(jComboBoxtipoempleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabelCodigo2)
                                            .addComponent(jComboBoxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(8, 8, 8)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabelCodigo9)
                                            .addComponent(jComboBoxEstadoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(3, 3, 3)
                                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelCodigo11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_salario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jButtonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelCodigo3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCodigo12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dpi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBottonTituloEtiqueta.setBackground(new java.awt.Color(51, 204, 255));
        jBottonTituloEtiqueta.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jBottonTituloEtiqueta.setForeground(new java.awt.Color(102, 102, 102));
        jBottonTituloEtiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cuadrialla Seleccionado.png"))); // NOI18N
        jBottonTituloEtiqueta.setText("EMPLEADO");
        jBottonTituloEtiqueta.setBorder(null);
        jBottonTituloEtiqueta.setBorderPainted(false);
        jBottonTituloEtiqueta.setContentAreaFilled(false);
        jBottonTituloEtiqueta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBottonTituloEtiqueta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBottonTituloEtiquetaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBottonTituloEtiquetaMouseExited(evt);
            }
        });
        jBottonTituloEtiqueta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBottonTituloEtiquetaActionPerformed(evt);
            }
        });

        jButtonExportar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar Completo.png"))); // NOI18N
        jButtonExportar2.setToolTipText("Exportar Tabla A Excel");
        jButtonExportar2.setBorder(null);
        jButtonExportar2.setBorderPainted(false);
        jButtonExportar2.setContentAreaFilled(false);
        jButtonExportar2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar Completo.png"))); // NOI18N
        jButtonExportar2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar Seleccionado .png"))); // NOI18N
        jButtonExportar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportar2ActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBar(null);

        tabla.setAltoHead(25);
        tabla.setColorBackgoundHead(new java.awt.Color(172, 193, 184));
        tabla.setColorBordeHead(new java.awt.Color(255, 255, 255));
        tabla.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        tabla.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        tabla.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        tabla.setFuenteFilas(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        tabla.setFuenteFilasSelect(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        tabla.setFuenteHead(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tabla.setGridColor(new java.awt.Color(255, 255, 255));
        tabla.setInheritsPopupMenu(true);
        tabla.setIntercellSpacing(new java.awt.Dimension(5, 5));
        tabla.setRowHeight(20);
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabla);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jBottonTituloEtiqueta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExportar2)
                .addGap(284, 284, 284))
            .addComponent(jScrollPane2)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1081, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBottonTituloEtiqueta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonExportar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // <editor-fold defaultstate="collapsed" desc="Acciones de Botones"> 
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        mostrarDatos();
        txt_salario.setText("");
        txt_apellidoEmpleado.setText("");
        txt_telefono.setText("");;
        txt_direccion.setText("");
        txt_Email.setText("");
        txt_dpi.setText("");
        Date date = new Date();
        jDateFecha2.setDate(date);
        txt_codigoEmpleado.setText(Integer.toString(Empleado.getRegistroMaximo() + 1));
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_jButtonGuardarActionPerformed
    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if (txt_nombreEmpleado.getSelectedItem().toString().length() == 0 && txt_codigoEmpleado.getText().length() == 0) {
            String[] botones1 = {"GENERO", "ESTADO EMPLEADO", "TIPO EMPLEADO", "FECHA"};
            Object[] botones = {"GENERO", "ESTADO EMPLEADO", "TIPO EMPLEADO", "FECHA"};
            int vSelec = JOptionPane.showOptionDialog(this, "¿Por que campo quieres buscar?", "Buscar", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones1, botones[0]);
            switch (vSelec) {
                case 0:
                    buscarDatosGenero();
                    break;
                case 1:
                    buscarDatosEstado();
                    break;
                case 2:
                    buscarDatosTipo();
                    break;
                case 3:
                    buscarPorFecha();
                    break;
                default:
                    break;
            }

        } else if (txt_nombreEmpleado.getSelectedItem().toString().length() > 0) {
            buscarDatosNombre();
        } else if (txt_codigoEmpleado.getText().length() > 0) {
            buscarDatosCodigo();
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese Datos Para Buscar");
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed
    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
        actualizarDatos();
    }//GEN-LAST:event_jButtonActualizarActionPerformed
    private void jBottonTituloEtiquetaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBottonTituloEtiquetaMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jBottonTituloEtiquetaMouseEntered
    private void jBottonTituloEtiquetaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBottonTituloEtiquetaMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jBottonTituloEtiquetaMouseExited
    private void jBottonTituloEtiquetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBottonTituloEtiquetaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBottonTituloEtiquetaActionPerformed
    private void jButtonExportar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportar2ActionPerformed
        if (this.tabla.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No hay datos en la tabla para exportar.", "EMPLEADO", JOptionPane.INFORMATION_MESSAGE);
            this.txt_codigoEmpleado.grabFocus();
            return;
        }
        FileDialog dialog = new FileDialog(this, "Save", FileDialog.SAVE);
        dialog.setTitle("Guardar Archivo");
        dialog.setMultipleMode(false);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        if (dialog.getFile() != null) {
            List<JTable> tb = new ArrayList<>();
            List<String> nom = new ArrayList<>();
            tb.add(tabla);
            nom.add("EMPLEADO");
            String file = dialog.getDirectory() + dialog.getFile().concat(".xls");
            try {
                clases.ExportarAExcel e = new clases.ExportarAExcel(new File(file), tb, nom);
                if (e.export()) {
                    Image imagen = new ImageIcon(getClass().getResource("/imagenes/Excel.png")).getImage();
                    ImageIcon im = new ImageIcon(imagen);
                    Icon expo = new ImageIcon(im.getImage());
                    JOptionPane.showMessageDialog(this, "Los Datos Fueron Exportados a Excel", "EMPLEADO", JOptionPane.OK_OPTION, expo);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocurrio un error " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonExportar2ActionPerformed
    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
        try {
            jButtonGuardar.setEnabled(false);
            DefaultTableModel modelo1 = new DefaultTableModel();
            int filaSeleccionada = tabla.rowAtPoint(evt.getPoint());
            String codigo = String.valueOf(tabla.getValueAt(filaSeleccionada, 0));
            txt_codigoEmpleado.setText(codigo);
            txt_nombreEmpleado.setSelectedItem(String.valueOf(tabla.getValueAt(filaSeleccionada, 1)));
            txt_apellidoEmpleado.setText(String.valueOf(tabla.getValueAt(filaSeleccionada, 2)));
            String direccion = (String.valueOf(tabla.getValueAt(filaSeleccionada, 4)));
            if (direccion.equals("null")) {
                txt_direccion.setText("");
            } else {
                txt_direccion.setText(direccion);
            }
            String correo = String.valueOf(tabla.getValueAt(filaSeleccionada, 5));
            if (correo.equals("null")) {
                txt_Email.setText("");
            } else {
                txt_Email.setText(String.valueOf(tabla.getValueAt(filaSeleccionada, 5)));
            }
            String telefono = (String.valueOf(tabla.getValueAt(filaSeleccionada, 3)));
            if (telefono.equals("0") || telefono.equals("null")) {
                txt_telefono.setText("");
            } else {
                txt_telefono.setText(String.valueOf(tabla.getValueAt(filaSeleccionada, 3)));
            }

            txt_salario.setText(String.valueOf(tabla.getValueAt(filaSeleccionada, 9)));
            jComboBoxGenero.setSelectedItem(String.valueOf(tabla.getValueAt(filaSeleccionada, 6)));
            String estado = String.valueOf(tabla.getValueAt(filaSeleccionada, 10));
            jComboBoxEstadoEmpleado.setSelectedItem(estado);
            jComboBoxtipoempleado.setSelectedItem(String.valueOf(tabla.getValueAt(filaSeleccionada, 8)));
            String fecha = String.valueOf(tabla.getValueAt(filaSeleccionada, 7));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fechaSelec = null;
            fechaSelec = sdf.parse(fecha);
            jDateFecha2.setDate(fechaSelec);

            jComboBoxtipoempleado.setSelectedItem(String.valueOf(tabla.getValueAt(filaSeleccionada, 8)));
            Blob blobImagen = Empleado.getBlobImagen(Integer.parseInt(codigo));
            byte[] bytesLeidos = blobImagen.getBytes(1, (int) blobImagen.length());
            ImageIcon imageIcon = null;
            imageIcon = new ImageIcon(bytesLeidos);

            icono = new ImageIcon(imageIcon.getImage().getScaledInstance(this.verImagen.getAncho(), this.verImagen.getAlto(), Image.SCALE_SMOOTH));

        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ParseException ex) {
            System.out.println("No convierte");
        }
        jButtonActualizar.setEnabled(true);
        // jButtonEliminar.setEnabled(true);
    }//GEN-LAST:event_tablaMouseClicked
    private void txt_codigoEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigoEmpleadoKeyTyped
        int numeroCaracteres = 3;
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrear Solo Numero", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        } else {
            if (teclaEnter == KeyEvent.VK_ENTER) {
                if (txt_codigoEmpleado.getText().length() > 0) {
                    buscarDatosCodigo();
                } else {
                    if (txt_nombreEmpleado.getSelectedItem().toString().length() > 0) {
                        buscarDatosNombre();
                    } else {
                        JOptionPane.showMessageDialog(this, "Ingrese Valores Para Buscar", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else if (txt_codigoEmpleado.getText().length() > numeroCaracteres) {
                evt.consume();
                getToolkit().beep();
                JOptionPane.showMessageDialog(this, "No Ingresar Mas de 4 Digitos", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigoEmpleadoKeyTyped
    private void txt_telefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefonoKeyTyped
        int numeroCaracteres = 7;
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrear Solo Numero", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        } else {
            if (teclaEnter == KeyEvent.VK_ENTER) {
                if (txt_telefono.getText().length() > 0) {
                    buscarDatosCodigo();
                } else {
                    buscarDatosNombre();
                }
            } else if (txt_telefono.getText().length() > numeroCaracteres) {
                evt.consume();
                getToolkit().beep();
                JOptionPane.showMessageDialog(this, "No Ingresar Mas de 8 Digitos", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_txt_telefonoKeyTyped
    private void txt_salarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_salarioKeyTyped
        char validar = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrear Solo Numero", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_txt_salarioKeyTyped
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        limpiarVentana();
    }//GEN-LAST:event_formWindowClosing

    private void txt_nombreEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nombreEmpleadoMouseClicked
        //jButtonEliminar.setEnabled(false);
        jButtonGuardar.setEnabled(true);
    }//GEN-LAST:event_txt_nombreEmpleadoMouseClicked

    private void txt_codigoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_codigoEmpleadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigoEmpleadoActionPerformed

    private void txt_dpiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_dpiKeyTyped
        // TODO add your handling code here:
        int numeroCaracteres = 12;
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();

        if (Character.isLetter(validar)) {
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(this, "Ingrear Solo Numero", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        } else {
            if (teclaEnter == KeyEvent.VK_ENTER) {
                if (txt_dpi.getText().length() > 0) {
                    buscarDatosCodigo();
                } else {
                    buscarDatosNombre();
                }
            } else if (txt_dpi.getText().length() > numeroCaracteres) {
                evt.consume();
                getToolkit().beep();
                JOptionPane.showMessageDialog(this, "No Ingresar Mas de 13 Digitos", "EMPLEADOS", JOptionPane.WARNING_MESSAGE);
            }
        }

    }//GEN-LAST:event_txt_dpiKeyTyped
    // </editor-fold>

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EMPLEADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EMPLEADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EMPLEADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EMPLEADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EMPLEADO().setVisible(true);
            }
        });
    }

    // <editor-fold defaultstate="collapsed" desc="Componentes"> 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton jBottonTituloEtiqueta;
    private javax.swing.JToggleButton jButton1;
    private javax.swing.JButton jButtonActualizar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonExportar2;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JComboBox<String> jComboBoxEstadoEmpleado;
    private javax.swing.JComboBox<String> jComboBoxGenero;
    private javax.swing.JComboBox<String> jComboBoxtipoempleado;
    private com.toedter.calendar.JDateChooser jDateFecha2;
    private javax.swing.JLabel jLabelCodigo;
    private javax.swing.JLabel jLabelCodigo1;
    private javax.swing.JLabel jLabelCodigo10;
    private javax.swing.JLabel jLabelCodigo11;
    private javax.swing.JLabel jLabelCodigo12;
    private javax.swing.JLabel jLabelCodigo2;
    private javax.swing.JLabel jLabelCodigo3;
    private javax.swing.JLabel jLabelCodigo4;
    private javax.swing.JLabel jLabelCodigo6;
    private javax.swing.JLabel jLabelCodigo7;
    private javax.swing.JLabel jLabelCodigo8;
    private javax.swing.JLabel jLabelCodigo9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private rojerusan.RSTableMetro tabla;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_apellidoEmpleado;
    private javax.swing.JTextField txt_codigoEmpleado;
    private javax.swing.JTextField txt_direccion;
    private javax.swing.JTextField txt_dpi;
    private javax.swing.JComboBox<String> txt_nombreEmpleado;
    private javax.swing.JTextField txt_salario;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
