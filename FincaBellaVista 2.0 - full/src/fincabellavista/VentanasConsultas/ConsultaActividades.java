package fincabellavista.VentanasConsultas;

import SQLclasesSecundarias.SQLDetalleEmpleado;
import fincabellavista.VentanasConsultas.*;
import clases.ConexionBD;
import clases.ExportarAExcel;
import com.sun.javafx.geom.transform.BaseTransform;
import java.awt.FileDialog;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public final class ConsultaActividades extends javax.swing.JFrame {
    
    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection(); 
    private static Statement st;
    private static ResultSet rs;
    private SQLConsultaActividades consultaActividades = new SQLConsultaActividades(this);
   
    
    public ConsultaActividades() {
        initComponents();
        limpiarListaProducto();
        limpiarEmpeado();
        limpiarListaAreas();
        limpiarlistaActividades();
        try {
            listaActividades();
            listaProducto();
            listaAreas();
            listaEmpleado();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaActividades.class.getName()).log(Level.SEVERE, null, ex);
        }
        AutoCompleteDecorator.decorate(jComboBoxNombreEmpleado);
        this.setLocationRelativeTo(null);
        jDateFecha1.setDate(new Date());
        jDateFecha2.enableInputMethods(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setIconImage(new ImageIcon(getClass().getResource("/imagenes/Actividad Seleccionado.png")).getImage());
    }
    
    public void listaEmpleado() throws SQLException{
        try{
            jComboBoxNombreEmpleado.addItem("Todos");
            st=cn.createStatement();
            String r = "SELECT NOMBRE_EMPLEADO||' '||APELLIDO_EMPLEADO FROM EMPLEADO";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    jComboBoxNombreEmpleado.addItem(fila[i].toString());
                }
            }
            rs.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error Al Conectar: "+e);
        }
    }
    public void limpiarEmpeado(){
        jComboBoxNombreEmpleado.removeAll();
    }
    
    public void listaProducto(){
        try {
            st=cn.createStatement();
            String r = "SELECT NOMBRE FROM PRODUCTO ORDER BY CODIGO";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    jComboBoxProducto.addItem(fila[i].toString());
                }
            }
            rs.close();
            jComboBoxProducto.setSelectedIndex(1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error Al Conectar: "+e);
        }
    }   
    public void limpiarListaProducto(){
        jComboBoxProducto.removeAllItems();
    }
    private int codigoProducto(){
        int codigoNombreProducto = 0;
        try {
            String r = "SELECT CODIGO FROM PRODUCTO WHERE NOMBRE= '"+jComboBoxProducto.getSelectedItem().toString()+"' ORDER BY 1";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    codigoNombreProducto = Integer.parseInt(fila[i].toString());
                }
            }
            rs.close();
        }catch(NumberFormatException | SQLException e){
            JOptionPane.showMessageDialog(this, "Error Al Devolver Codigo de Producto:\n"+e.toString());
        }
        return codigoNombreProducto;
    }

    public void listaAreas(){
        try {
            jComboBoxAreas.addItem("Todas");
            st=cn.createStatement();
            String r = "SELECT NOMBRE_AREA FROM AREA ORDER BY 1";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    jComboBoxAreas.addItem(fila[i].toString());
                }
            }
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error Al Conectar: "+e);
        }
    }   
    public void limpiarListaAreas(){
        jComboBoxAreas.removeAllItems();
    }
    
    public void listaActividades(){
        try {
            jComboBoxActividades.addItem("Todas");
            st=cn.createStatement();
            String r = "SELECT NOMBRE_ACTIVIDAD FROM ACTIVIDAD ORDER BY 1";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    jComboBoxActividades.addItem(fila[i].toString());
                }
            }
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error Al Conectar: "+e);
        }
    }   
    public void limpiarlistaActividades(){
        jComboBoxActividades.removeAllItems();
    }
    
    
    private boolean conCodigo= false;
    private String fecha1 = null;
    private String fecha2 = null;
    private int producto = 0;
    private String empleado = null;
    private String area = null;
    private String actividad = null;
    private String fechaSelec=null;
    private void asignarValores(){
        SimpleDateFormat variableFecha = new SimpleDateFormat("dd-MM-YYYY");
        fecha1 = variableFecha.format(jDateFecha1.getDate());
        if(jDateFecha2.getDate()!=null){
        fecha2 = variableFecha.format(jDateFecha2.getDate());
        }else{
            fecha2 = variableFecha.format(jDateFecha1.getDate());
        }
        producto = codigoProducto();
        empleado = jComboBoxNombreEmpleado.getSelectedItem().toString();
        area = jComboBoxAreas.getSelectedItem().toString();
        actividad = jComboBoxActividades.getSelectedItem().toString();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabelPlanilla = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabelDe = new javax.swing.JLabel();
        jDateFecha1 = new com.toedter.calendar.JDateChooser();
        jLabelAl = new javax.swing.JLabel();
        jDateFecha2 = new com.toedter.calendar.JDateChooser();
        jComboBoxAreas = new javax.swing.JComboBox<>();
        jLabelDe1 = new javax.swing.JLabel();
        jLabelDe2 = new javax.swing.JLabel();
        jLabelDe5 = new javax.swing.JLabel();
        jComboBoxProducto = new javax.swing.JComboBox<>();
        jButtonBuscar = new javax.swing.JButton();
        jComboBoxNombreEmpleado = new javax.swing.JComboBox<>();
        jButtonEliminar = new javax.swing.JButton();
        jComboBoxActividades = new javax.swing.JComboBox<>();
        jLabelDe3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabelJornalestxt = new javax.swing.JLabel();
        jLabelAcumuladotxt = new javax.swing.JLabel();
        jLabelJornales = new javax.swing.JLabel();
        jLabelActividades = new javax.swing.JLabel();
        jButtonExportarHDP = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableActividades = new rojerusan.RSTableMetro();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabelEmpleadostxt = new javax.swing.JLabel();
        jLabelAcumuladoEmptxt = new javax.swing.JLabel();
        jLabelEmpleados = new javax.swing.JLabel();
        jLabelAcumuladoEmp = new javax.swing.JLabel();
        jButtonExportarHDP1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTableEmpleados = new rojerusan.RSTableMetro();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta Actividades");

        jPanel1.setBackground(new java.awt.Color(92, 125, 137));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabelPlanilla.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabelPlanilla.setForeground(new java.awt.Color(102, 102, 102));
        jLabelPlanilla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPlanilla.setText("Consulta Actividades");

        jPanel3.setBackground(new java.awt.Color(95, 106, 106));

        jPanel5.setBackground(new java.awt.Color(95, 106, 106));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Parametros", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setName(""); // NOI18N

        jLabelDe.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDe.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDe.setText("De:");

        jLabelAl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelAl.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAl.setText("Al:");

        jComboBoxAreas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelDe1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDe1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDe1.setText("Area:");

        jLabelDe2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDe2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDe2.setText("Empleado");

        jLabelDe5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDe5.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDe5.setText("Producto");

        jComboBoxProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxProducto.setNextFocusableComponent(jComboBoxNombreEmpleado);

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo.png"))); // NOI18N
        jButtonBuscar.setToolTipText(" Buscar Datos");
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

        jComboBoxNombreEmpleado.setEditable(true);
        jComboBoxNombreEmpleado.setToolTipText("Empleados");
        jComboBoxNombreEmpleado.setNextFocusableComponent(jComboBoxAreas);

        jButtonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Eliminar Completo.png"))); // NOI18N
        jButtonEliminar.setToolTipText("Eliminar Busqueda");
        jButtonEliminar.setBorder(null);
        jButtonEliminar.setBorderPainted(false);
        jButtonEliminar.setContentAreaFilled(false);
        jButtonEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonEliminar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Eliminar Completo.png"))); // NOI18N
        jButtonEliminar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Eliminar Completo Seleccionado.png"))); // NOI18N
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jComboBoxActividades.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelDe3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDe3.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDe3.setText("Actividades");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDe)
                            .addComponent(jDateFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDateFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAl)))
                    .addComponent(jLabelDe2)
                    .addComponent(jComboBoxNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDe5)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jComboBoxProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonEliminar)))
                        .addGap(18, 18, 18)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(221, 221, 221))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabelDe1)
                                .addGap(174, 174, 174))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jComboBoxAreas, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDe3)
                            .addComponent(jComboBoxActividades, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDe5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAl, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar))
                .addGap(0, 60, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabelDe, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDe3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDe1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDe2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxActividades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxAreas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(95, 106, 106));

        jPanel10.setBackground(new java.awt.Color(95, 106, 106));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel10.setName(""); // NOI18N

        jLabelJornalestxt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelJornalestxt.setForeground(new java.awt.Color(255, 255, 255));
        jLabelJornalestxt.setText("Jornales:");

        jLabelAcumuladotxt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelAcumuladotxt.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAcumuladotxt.setText("Actividades:");

        jLabelJornales.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelJornales.setForeground(new java.awt.Color(255, 255, 255));

        jLabelActividades.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelActividades.setForeground(new java.awt.Color(255, 255, 255));

        jButtonExportarHDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Excel Pequeño.png"))); // NOI18N
        jButtonExportarHDP.setToolTipText("Exportar");
        jButtonExportarHDP.setBorderPainted(false);
        jButtonExportarHDP.setContentAreaFilled(false);
        jButtonExportarHDP.setDefaultCapable(false);
        jButtonExportarHDP.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Excel Pequeño.png"))); // NOI18N
        jButtonExportarHDP.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Excel.png"))); // NOI18N
        jButtonExportarHDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportarHDPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelJornalestxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelJornales, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelAcumuladotxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelActividades, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonExportarHDP, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAcumuladotxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelActividades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelJornales, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonExportarHDP, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelJornalestxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jScrollPane4.setBorder(null);
        jScrollPane4.setHorizontalScrollBar(null);

        jTableActividades.setAltoHead(25);
        jTableActividades.setColorBackgoundHead(new java.awt.Color(172, 193, 184));
        jTableActividades.setColorBordeHead(new java.awt.Color(255, 255, 255));
        jTableActividades.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        jTableActividades.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        jTableActividades.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        jTableActividades.setFuenteFilas(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTableActividades.setFuenteFilasSelect(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTableActividades.setFuenteHead(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTableActividades.setGridColor(new java.awt.Color(255, 255, 255));
        jTableActividades.setInheritsPopupMenu(true);
        jTableActividades.setIntercellSpacing(new java.awt.Dimension(5, 5));
        jTableActividades.setMultipleSeleccion(false);
        jTableActividades.setRowHeight(20);
        jTableActividades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableActividadesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableActividades);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel13.setBackground(new java.awt.Color(95, 106, 106));

        jPanel14.setBackground(new java.awt.Color(95, 106, 106));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel14.setName(""); // NOI18N

        jLabelEmpleadostxt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelEmpleadostxt.setForeground(new java.awt.Color(255, 255, 255));
        jLabelEmpleadostxt.setText("Empleados:");

        jLabelAcumuladoEmptxt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelAcumuladoEmptxt.setForeground(new java.awt.Color(255, 255, 255));

        jLabelEmpleados.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelEmpleados.setForeground(new java.awt.Color(255, 255, 255));

        jLabelAcumuladoEmp.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelAcumuladoEmp.setForeground(new java.awt.Color(255, 255, 255));

        jButtonExportarHDP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Excel Pequeño.png"))); // NOI18N
        jButtonExportarHDP1.setToolTipText("Exportar");
        jButtonExportarHDP1.setBorderPainted(false);
        jButtonExportarHDP1.setContentAreaFilled(false);
        jButtonExportarHDP1.setDefaultCapable(false);
        jButtonExportarHDP1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Excel.png"))); // NOI18N
        jButtonExportarHDP1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Excel.png"))); // NOI18N
        jButtonExportarHDP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportarHDP1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelEmpleadostxt)
                .addGap(2, 2, 2)
                .addComponent(jLabelEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelAcumuladoEmptxt, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelAcumuladoEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExportarHDP1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAcumuladoEmptxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelEmpleadostxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelEmpleados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButtonExportarHDP1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabelAcumuladoEmp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jScrollPane5.setBorder(null);
        jScrollPane5.setHorizontalScrollBar(null);

        jTableEmpleados.setAltoHead(25);
        jTableEmpleados.setColorBackgoundHead(new java.awt.Color(172, 193, 184));
        jTableEmpleados.setColorBordeHead(new java.awt.Color(255, 255, 255));
        jTableEmpleados.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        jTableEmpleados.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        jTableEmpleados.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        jTableEmpleados.setFuenteFilas(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTableEmpleados.setFuenteFilasSelect(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTableEmpleados.setFuenteHead(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTableEmpleados.setGridColor(new java.awt.Color(255, 255, 255));
        jTableEmpleados.setInheritsPopupMenu(true);
        jTableEmpleados.setIntercellSpacing(new java.awt.Dimension(5, 5));
        jTableEmpleados.setMultipleSeleccion(false);
        jTableEmpleados.setRowHeight(20);
        jScrollPane5.setViewportView(jTableEmpleados);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jLabelPlanilla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabelPlanilla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        jTableEmpleados.setModel(new DefaultTableModel());
        asignarValores();
        int Acumulado = 0;
                if(empleado.equals("Todos")){
                    if(area.equals("Todas")){
                        if(actividad.equals("Todas")){
                            //TODO TODO TODO
                            jTableActividades.setModel(consultaActividades.buscarTodoTodoTodo(fecha1, fecha2, producto));
                             for(int i = 0; i<jTableActividades.getRowCount(); i++){
                            Acumulado += Integer.parseInt(String.valueOf(jTableActividades.getValueAt(i, 4)));
                            }
                            jLabelJornales.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                            jLabelActividades.setText(Integer.toString(jTableActividades.getRowCount()));
                            conCodigo= true;
                        }else{
                            //TODO TODO Actividad 
                            jTableActividades.setModel(consultaActividades.buscarTodoTodoActividad(actividad, producto, fecha1, fecha2));
                             for(int i = 0; i<jTableActividades.getRowCount(); i++){
                            Acumulado += Integer.parseInt(String.valueOf(jTableActividades.getValueAt(i, 4)));
                            }
                            jLabelJornales.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                            jLabelActividades.setText(Integer.toString(jTableActividades.getRowCount()));
                            conCodigo= true;
                        }
                    }else{
                        if(actividad.equals("Todas")){
                            //TODO AREA TODO
                            jTableActividades.setModel(consultaActividades.buscarTodoAreaTodo(producto, fecha1, fecha2, area));
                             for(int i = 0; i<jTableActividades.getRowCount(); i++){
                            Acumulado += Integer.parseInt(String.valueOf(jTableActividades.getValueAt(i, 4)));
                            }
                            jLabelJornales.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                            jLabelActividades.setText(Integer.toString(jTableActividades.getRowCount()));
                            conCodigo= true;
                            
                        }else{
                            //TODO AREA Actividad 
                            jTableActividades.setModel(consultaActividades.buscarTodoAreaActvidiad(actividad, producto, fecha1, fecha2, area));
                             for(int i = 0; i<jTableActividades.getRowCount(); i++){
                            Acumulado += Integer.parseInt(String.valueOf(jTableActividades.getValueAt(i, 4)));
                            }
                            jLabelJornales.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                            jLabelActividades.setText(Integer.toString(jTableActividades.getRowCount()));
                            conCodigo= true;
                        }
                    }
                }else{
                    if(area.equals("Todas")){
                        if(actividad.equals("Todas")){
                            //empleado TODO TODO
                            jTableActividades.setModel(consultaActividades.buscarActividadesEmpleado(empleado, producto, fecha1, fecha2));
                            jLabelJornales.setText(Integer.toString(jTableActividades.getRowCount()));
                            conCodigo= false;
                        }else{
                            //empleado TODO Actividad 
                            jTableActividades.setModel(consultaActividades.buscarActividadesAreaEmpleado(empleado, producto, fecha1, fecha2, actividad));
                            conCodigo= false;
                            jLabelJornales.setText(Integer.toString(jTableActividades.getRowCount()));
                        }
                    }else{
                        if(actividad.equals("Todas")){
                            //empleado AREA TODO
                            jTableActividades.setModel(consultaActividades.buscarActividadesEmpleado(empleado, producto, fecha1, fecha2, area));
                            conCodigo= false;
                            jLabelJornales.setText(Integer.toString(jTableActividades.getRowCount()));
                            
                        }else{
                            //empleado AREA Actividad 
                            jTableActividades.setModel(consultaActividades.buscarActividadesEmpleado(empleado, producto, actividad, fecha1, fecha2, area));
                            conCodigo= false;
                            jLabelJornales.setText(Integer.toString(jTableActividades.getRowCount()));
                        }
                    }
                }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jTableActividadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableActividadesMouseClicked
        try{
            int filaSeleccionada = jTableActividades.rowAtPoint(evt.getPoint());
            int codigo = Integer.parseInt(String.valueOf(jTableActividades.getValueAt(filaSeleccionada, 0)));
            String acti = String.valueOf(jTableActividades.getValueAt(filaSeleccionada, 2));
            if(acti.equals("Cosechar")){
                SQLConsultaCosecha s = new SQLConsultaCosecha(this);
                jTableEmpleados.setModel(s.buscarEmpleadosCodigoActividad(codigo));
                int Acumulado = 0;
                for(int i = 0; i<jTableEmpleados.getRowCount(); i++){
                    Acumulado += Integer.parseInt(String.valueOf(jTableEmpleados.getValueAt(i, 1)));
                }
                jLabelAcumuladoEmptxt.setText("Acumulado");
                jLabelAcumuladoEmp.setText(new DecimalFormat("###,###,###.##").format(Acumulado)+" "+"Lb");
            }else{
                jTableEmpleados.setModel(consultaActividades.ListaEmpleadosActvidiad(codigo, producto));
                jLabelAcumuladoEmptxt.setText("");
                jLabelAcumuladoEmp.setText("");
            }
            jLabelEmpleados.setText(Integer.toString(jTableEmpleados.getRowCount()));
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_jTableActividadesMouseClicked

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        Date date = null;
        jDateFecha2.setDate(date);
        jDateFecha1.setDate(new Date());
        jTableActividades.setModel(new DefaultTableModel());
        jTableEmpleados.setModel(new DefaultTableModel());
        jComboBoxAreas.setSelectedIndex(0);
        jComboBoxNombreEmpleado.setSelectedIndex(0);
        jComboBoxProducto.setSelectedIndex(1);
        jLabelActividades.setText("");
        jLabelAcumuladoEmp.setText("");
        jLabelJornales.setText("");
        jLabelEmpleados.setText("");
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonExportarHDP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportarHDP1ActionPerformed
            asignarValores();
            if(this.jTableEmpleados.getRowCount()==0){
                JOptionPane.showMessageDialog(this, "No hay datos en la tabla para exportar.","COSECHA", JOptionPane.WARNING_MESSAGE);
                return;
            }
            FileDialog dialog = new FileDialog(this,"Save",FileDialog.SAVE);
            dialog.setFile(fechaSelec+" Listado de Empleados");
            dialog.setTitle("Empleados Cosecha");
            dialog.setMultipleMode(false);
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            if(dialog.getFile()!=null){
                List<JTable> tb = new ArrayList<>();
                List<String>nom = new ArrayList<>();
                tb.add(jTableEmpleados);
                nom.add(fechaSelec);
                String file=dialog.getDirectory()+dialog.getFile().concat(".xls");
                try{
                    clases.ExportarAExcel e = new clases.ExportarAExcel(new File(file),tb , nom);
                    if(e.export()){
                        Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Excel.png")).getImage();
                            ImageIcon im = new ImageIcon(imagen);
                            Icon expo = new ImageIcon(im.getImage());
                            JOptionPane.showMessageDialog(this, "Los Datos Fueron Exportados a Excel", "Cosecha", JOptionPane.OK_OPTION, expo); }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(this, "Ocurrio un error " + ex.getMessage(),"COSECHA",JOptionPane.ERROR_MESSAGE);
                }
            }
        
    }//GEN-LAST:event_jButtonExportarHDP1ActionPerformed

    private void jButtonExportarHDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportarHDPActionPerformed
                asignarValores();
                if(jDateFecha2==null){
                    this.fecha2=null;
                }
                String nombreFile="Actividad - ";
                if(this.fecha2==null){
                    nombreFile = jComboBoxProducto.getSelectedItem().toString()+" "+fecha1;
                }else{
                    nombreFile = jComboBoxProducto.getSelectedItem().toString()+" "+fecha1+" "+fecha2;
                }
                if(jComboBoxNombreEmpleado.getSelectedItem().toString().equals("Todos")){
                    nombreFile+=" Todos Los Empleados";
                }else{
                    nombreFile+=" "+jComboBoxNombreEmpleado.getSelectedItem().toString();
                }
                if(jComboBoxAreas.getSelectedItem().toString().equals("Todas")){
                    nombreFile+=" Todas Las Areas";
                }else{
                    nombreFile+=" "+jComboBoxAreas.getSelectedItem().toString();
                }
                if(this.jTableActividades.getRowCount()==0){
                    JOptionPane.showMessageDialog(this, "No hay datos en la tabla para exportar.","Consuta De Actividad", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                FileDialog dialog = new FileDialog(this,"Save",FileDialog.SAVE);
                dialog.setFile(nombreFile);
                dialog.setTitle("Guardar Archivo");
                dialog.setMultipleMode(false);
                dialog.setLocationRelativeTo(this);
                dialog.setVisible(true);
                if(dialog.getFile()!=null){
                    List<JTable> tb = new ArrayList<>();
                    List<String>nom = new ArrayList<>();
                    tb.add(jTableActividades);
                    if(fecha2==null){
                        nom.add(fecha1);
                    }else{
                        nom.add(fecha1+" "+fecha2);
                    }
                    String file=dialog.getDirectory()+dialog.getFile().concat(".xls");
                    try{
                        clases.ExportarAExcel e = new clases.ExportarAExcel(new File(file),tb , nom);
                        if(e.export()){
                            Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Excel.png")).getImage();
                            ImageIcon im = new ImageIcon(imagen);
                            Icon expo = new ImageIcon(im.getImage());
                            JOptionPane.showMessageDialog(this, "Los Datos Fueron Exportados a Excel", "Consuta De Actividad", JOptionPane.OK_OPTION, expo);
                        }else{
                            JOptionPane.showMessageDialog(this, "Ocurrio un error ","Consuta De Actividad",JOptionPane.ERROR_MESSAGE);
                        }
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(this, "Ocurrio un error " + ex.getMessage(),"Consuta De Actividad",JOptionPane.ERROR_MESSAGE);
                    }
                }

        
    }//GEN-LAST:event_jButtonExportarHDPActionPerformed

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
            java.util.logging.Logger.getLogger(ConsultaActividades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultaActividades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultaActividades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultaActividades.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultaActividades().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonExportarHDP;
    private javax.swing.JButton jButtonExportarHDP1;
    private javax.swing.JComboBox<String> jComboBoxActividades;
    private javax.swing.JComboBox<String> jComboBoxAreas;
    private javax.swing.JComboBox<String> jComboBoxNombreEmpleado;
    private javax.swing.JComboBox<String> jComboBoxProducto;
    private com.toedter.calendar.JDateChooser jDateFecha1;
    private com.toedter.calendar.JDateChooser jDateFecha2;
    private javax.swing.JLabel jLabelActividades;
    private javax.swing.JLabel jLabelAcumuladoEmp;
    private javax.swing.JLabel jLabelAcumuladoEmptxt;
    private javax.swing.JLabel jLabelAcumuladotxt;
    private javax.swing.JLabel jLabelAl;
    private javax.swing.JLabel jLabelDe;
    private javax.swing.JLabel jLabelDe1;
    private javax.swing.JLabel jLabelDe2;
    private javax.swing.JLabel jLabelDe3;
    private javax.swing.JLabel jLabelDe5;
    private javax.swing.JLabel jLabelEmpleados;
    private javax.swing.JLabel jLabelEmpleadostxt;
    private javax.swing.JLabel jLabelJornales;
    private javax.swing.JLabel jLabelJornalestxt;
    private javax.swing.JLabel jLabelPlanilla;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private rojerusan.RSTableMetro jTableActividades;
    private rojerusan.RSTableMetro jTableEmpleados;
    // End of variables declaration//GEN-END:variables
}
