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

public final class ConsultaCombustible extends javax.swing.JFrame {
    
    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection(); 
    private static Statement st;
    private static ResultSet rs;
    private SQLConsultaCombustibles ConsultaCombustibles = new SQLConsultaCombustibles(this);
   
    
    public ConsultaCombustible() {
        initComponents();
        limpiarEmpeado();
        limpiarListaCombustible();
        limpiarListaVehiculos();
        try {
            listaEmpleado();
            listaCombustible();
            listaVehiculos();
            listaEmpleado();
        } catch (SQLException ex) {
            Logger.getLogger(ConsultaCombustible.class.getName()).log(Level.SEVERE, null, ex);
        }
        AutoCompleteDecorator.decorate(jComboBoxNombreConductor);
        this.setLocationRelativeTo(null);
        jDateFecha1.setDate(new Date());
        jDateFecha2.enableInputMethods(false);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setIconImage(new ImageIcon(getClass().getResource("/imagenes/Actividad Seleccionado.png")).getImage());
    }
    
    public void listaEmpleado() throws SQLException{
        try{
            jComboBoxNombreConductor.addItem("Todos");
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
                    jComboBoxNombreConductor.addItem(fila[i].toString());
                }
            }
            rs.close();
        }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error Al Conectar: "+e);
        }
    }
    public void limpiarEmpeado(){
        jComboBoxNombreConductor.removeAll();
    }
    
    public void listaCombustible(){
        try {
            jComboBoxCombustible.addItem("Todos");
            st=cn.createStatement();
            String r = "SELECT NOMBRE_TIPOCOMBUSTIBLE FROM TIPO_COMBUSTIBLE";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    jComboBoxCombustible.addItem(fila[i].toString());
                }
            }
            rs.close();
            jComboBoxCombustible.setSelectedIndex(1);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error Al Conectar: "+e);
        }
    }   
    public void limpiarListaCombustible(){
        jComboBoxCombustible.removeAllItems();
    }
    
    public void listaVehiculos(){
        try {
            jComboBoxVehiculos.addItem("Todos");
            st=cn.createStatement();
            String r = "SELECT NOMBRE_MAQUINA FROM MAQUINA ORDER BY 1";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    jComboBoxVehiculos.addItem(fila[i].toString());
                }
            }
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error Al Conectar: "+e);
        }
    }   
    public void limpiarListaVehiculos(){
        jComboBoxVehiculos.removeAllItems();
    }
    
    
    
    private String fecha1 = null;
    private String fecha2 = null;
    private String empleado = null;
    private String combustible = null;
    private String vehiculo = null;
    private String fechaSelec=null;
    private void asignarValores(){
        SimpleDateFormat variableFecha = new SimpleDateFormat("dd-MM-YYYY");
        fecha1 = variableFecha.format(jDateFecha1.getDate());
        if(jDateFecha2.getDate()!=null){
        fecha2 = variableFecha.format(jDateFecha2.getDate());
        }else{
            fecha2 = variableFecha.format(jDateFecha1.getDate());
        }
        empleado = jComboBoxNombreConductor.getSelectedItem().toString();
        combustible = jComboBoxCombustible.getSelectedItem().toString();
        vehiculo = jComboBoxVehiculos.getSelectedItem().toString();
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
        jComboBoxCombustible = new javax.swing.JComboBox<>();
        jLabelDe1 = new javax.swing.JLabel();
        jLabelDe2 = new javax.swing.JLabel();
        jButtonBuscar = new javax.swing.JButton();
        jComboBoxNombreConductor = new javax.swing.JComboBox<>();
        jButtonEliminar = new javax.swing.JButton();
        jComboBoxVehiculos = new javax.swing.JComboBox<>();
        jLabelDe3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabelFilastxt = new javax.swing.JLabel();
        jLabelAcumuladotxt = new javax.swing.JLabel();
        jLabelFilas = new javax.swing.JLabel();
        jLabelAcumulado = new javax.swing.JLabel();
        jButtonExportarHDP = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableCombustibles = new rojerusan.RSTableMetro();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Consulta De Combustibles");

        jPanel1.setBackground(new java.awt.Color(92, 125, 137));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabelPlanilla.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabelPlanilla.setForeground(new java.awt.Color(102, 102, 102));
        jLabelPlanilla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPlanilla.setText("Consulta Combustibles");

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

        jComboBoxCombustible.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelDe1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDe1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDe1.setText("Combustible:");

        jLabelDe2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDe2.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDe2.setText("Conductor:");

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

        jComboBoxNombreConductor.setEditable(true);
        jComboBoxNombreConductor.setToolTipText("Empleados");
        jComboBoxNombreConductor.setNextFocusableComponent(jComboBoxCombustible);

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

        jComboBoxVehiculos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabelDe3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDe3.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDe3.setText("Vehiculo:");

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
                    .addComponent(jComboBoxNombreConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(221, 221, 221))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxCombustible, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDe1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelDe3)
                            .addComponent(jComboBoxVehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabelAl, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jDateFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabelDe, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 60, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelDe3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDe1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDe2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxVehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxCombustible, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxNombreConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

        jLabelFilastxt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelFilastxt.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFilastxt.setText("Filas:");

        jLabelAcumuladotxt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelAcumuladotxt.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAcumuladotxt.setText("Acumulado:");

        jLabelFilas.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelFilas.setForeground(new java.awt.Color(255, 255, 255));

        jLabelAcumulado.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelAcumulado.setForeground(new java.awt.Color(255, 255, 255));

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
                .addComponent(jLabelFilastxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelFilas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelAcumuladotxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelAcumulado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonExportarHDP, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelAcumuladotxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelAcumulado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabelFilas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabelFilastxt, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jButtonExportarHDP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane4.setBorder(null);
        jScrollPane4.setHorizontalScrollBar(null);

        jTableCombustibles.setAltoHead(25);
        jTableCombustibles.setColorBackgoundHead(new java.awt.Color(172, 193, 184));
        jTableCombustibles.setColorBordeHead(new java.awt.Color(255, 255, 255));
        jTableCombustibles.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        jTableCombustibles.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        jTableCombustibles.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        jTableCombustibles.setFuenteFilas(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTableCombustibles.setFuenteFilasSelect(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTableCombustibles.setFuenteHead(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTableCombustibles.setGridColor(new java.awt.Color(255, 255, 255));
        jTableCombustibles.setInheritsPopupMenu(true);
        jTableCombustibles.setIntercellSpacing(new java.awt.Dimension(5, 5));
        jTableCombustibles.setMultipleSeleccion(false);
        jTableCombustibles.setRowHeight(20);
        jTableCombustibles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableCombustiblesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTableCombustibles);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jLabelPlanilla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabelPlanilla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        jTableCombustibles.setModel(new DefaultTableModel());
        asignarValores();
        int Acumulado = 0;
        String[] botones1 = {"Entradas", "Salidas","Ambas"};
        Object[] botones = {"Entradas", "Salidas","Ambas"};
        int vSelec = JOptionPane.showOptionDialog(this, "¿Que desea Buscar?", "Buscar", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones1, botones[0]);
        switch (vSelec) {
            //Entrada
            case 0 : {
                    if(combustible.equals("Todos")){
                        jTableCombustibles.setModel(ConsultaCombustibles.EntradasDeCombustible(fecha1, fecha2));
                         for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                            Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                        }
                        jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                        jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                    }
                    else{
                        jTableCombustibles.setModel(ConsultaCombustibles.EntradasDeCombustible(fecha1, fecha2,combustible));
                        for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                            Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                        }
                        jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                        jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                    }                                     
            }break;
            //Salida
            case 1 : {
                    if(empleado.equals("Todos")){
                        if(vehiculo.equals("Todos")){
                            if(combustible.equals("Todos")){
                                jTableCombustibles.setModel(ConsultaCombustibles.SalidasDeCombustible(fecha1, fecha2));
                                for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                }
                                jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                            }else{
                                 jTableCombustibles.setModel(ConsultaCombustibles.SalidasDeCombustible(fecha1, fecha2,combustible));
                                 for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i,1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                            }
                        }else{
                            if(combustible.equals("Todos")){
                                jTableCombustibles.setModel(ConsultaCombustibles.SalidasDeCombustibleVehi(fecha1, fecha2,vehiculo));
                                for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                }
                                jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                            }else{
                                jTableCombustibles.setModel(ConsultaCombustibles.SalidasDeCombustible(fecha1, fecha2,combustible,vehiculo));
                                for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                            }
                        }
                    }else{
                        if(vehiculo.equals("Todos")){
                            if(combustible.equals("Todos")){
                                jTableCombustibles.setModel(ConsultaCombustibles.SalidasDeCombustibleCon(fecha1, fecha2,empleado));
                                for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                            }else{
                                jTableCombustibles.setModel(ConsultaCombustibles.SalidasDeCombustibleCon(fecha1, fecha2,combustible,empleado));
                                for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                            }
                        }else{
                            if(combustible.equals("Todos")){
                                jTableCombustibles.setModel(ConsultaCombustibles.SalidasDeCombustibleVehi(fecha1, fecha2, vehiculo, empleado));
                                for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                            }else{
                                jTableCombustibles.setModel(ConsultaCombustibles.SalidasDeCombustible(fecha1, fecha2, combustible, vehiculo, empleado));
                                for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                            }
                        }
                    }
                
            }break;
            //Ambas
            case 2 : {
                if(combustible.equals("Todos")){
                   JOptionPane.showMessageDialog(this, "No puedes Consultar Entradas y Salidas de Todos los Combustibles \nEspecifica 1.");
                }
                else{
                    if(vehiculo.equals("Todos")){
                        if(empleado.equals("Todos")){
                            jTableCombustibles.setModel(ConsultaCombustibles.EntradaSalidasDeCombustible(fecha1, fecha2,combustible));    
                            for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                        }else{
                            jTableCombustibles.setModel(ConsultaCombustibles.EntradaSalidasDeCombustibleCon(fecha1, fecha2, combustible, empleado));
                            for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                        }
                    }else{
                        if(empleado.equals("Todos")){
                            jTableCombustibles.setModel(ConsultaCombustibles.EntradaSalidasDeCombustible(fecha1, fecha2, combustible, vehiculo));
                            for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                        }else{
                            jTableCombustibles.setModel(ConsultaCombustibles.EntradaSalidasDeCombustible(fecha1, fecha2, combustible, vehiculo,empleado));
                            for(int i = 0; i<jTableCombustibles.getRowCount(); i++){
                                 Acumulado += Integer.parseInt(String.valueOf(jTableCombustibles.getValueAt(i, 1)));
                                 }
                                 jLabelAcumulado.setText(new DecimalFormat("###,###,###.##").format(Acumulado));
                                 jLabelFilas.setText(Integer.toString(jTableCombustibles.getRowCount()));
                        }
                    }
                }
            }break;
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed

    private void jTableCombustiblesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableCombustiblesMouseClicked

    }//GEN-LAST:event_jTableCombustiblesMouseClicked

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        Date date = null;
        jDateFecha2.setDate(date);
        jDateFecha1.setDate(new Date());
        jTableCombustibles.setModel(new DefaultTableModel());
        jComboBoxCombustible.setSelectedIndex(0);
        jComboBoxNombreConductor.setSelectedIndex(0);
        jLabelAcumulado.setText("");
        jLabelFilas.setText("");
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonExportarHDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportarHDPActionPerformed
        asignarValores();
        if(jDateFecha2==null){
            this.fecha2=null;
        }
        String nombreFile=null;

        if(jComboBoxNombreConductor.getSelectedItem().toString().equals("Todos")){
            nombreFile+=" Todos Los Empleados";
        }else{
            nombreFile+=" "+jComboBoxNombreConductor.getSelectedItem().toString();
        }
        if(jComboBoxCombustible.getSelectedItem().toString().equals("Todas")){
            nombreFile+=" Todas Los Combustibles";
        }else{
            nombreFile+=" "+jComboBoxCombustible.getSelectedItem().toString();
        }
        if(jComboBoxCombustible.getSelectedItem().toString().equals("Todas")){
            nombreFile+=" Todas Las Maquinas";
        }else{
            nombreFile+=" "+jComboBoxCombustible.getSelectedItem().toString();
        }
        if(this.jTableCombustibles.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "No hay datos en la tabla para exportar.","CONSULTA COMBUSTIBLE", JOptionPane.WARNING_MESSAGE);
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
            tb.add(jTableCombustibles);
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
                    JOptionPane.showMessageDialog(this, "Los Datos Fueron Exportados a Excel", "CONSULTA COMBUSTIBLE", JOptionPane.OK_OPTION, expo);
                }else{
                    JOptionPane.showMessageDialog(this, "Ocurrio un error ","CONSULTA COMBUSTIBLE",JOptionPane.ERROR_MESSAGE);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Ocurrio un error " + ex.getMessage(),"CONSULTA COMBUSTIBLE",JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(ConsultaCombustible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultaCombustible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultaCombustible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultaCombustible.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultaCombustible().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonExportarHDP;
    private javax.swing.JComboBox<String> jComboBoxCombustible;
    private javax.swing.JComboBox<String> jComboBoxNombreConductor;
    private javax.swing.JComboBox<String> jComboBoxVehiculos;
    private com.toedter.calendar.JDateChooser jDateFecha1;
    private com.toedter.calendar.JDateChooser jDateFecha2;
    private javax.swing.JLabel jLabelAcumulado;
    private javax.swing.JLabel jLabelAcumuladotxt;
    private javax.swing.JLabel jLabelAl;
    private javax.swing.JLabel jLabelDe;
    private javax.swing.JLabel jLabelDe1;
    private javax.swing.JLabel jLabelDe2;
    private javax.swing.JLabel jLabelDe3;
    private javax.swing.JLabel jLabelFilas;
    private javax.swing.JLabel jLabelFilastxt;
    private javax.swing.JLabel jLabelPlanilla;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane4;
    private rojerusan.RSTableMetro jTableCombustibles;
    // End of variables declaration//GEN-END:variables
}
