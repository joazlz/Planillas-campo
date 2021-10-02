package fincabellavista.venasSecundarias;
import clases.ConexionBD;
import SQLclasesSecundarias.SQLDetalleEmpleado;
import SQLclasesSecundarias.SQLDetalleEmpleadoCorte;
import clases.ExportarAExcel;
import com.sun.glass.events.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

public class DetalleEmpleadoCorte extends javax.swing.JDialog {
    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection(); 
    private static Statement st;
    private static ResultSet rs;
    private java.awt.Frame pa;
    private SQLDetalleEmpleadoCorte dEmpleado = new SQLDetalleEmpleadoCorte(pa);
    
    public DetalleEmpleadoCorte(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        pa = parent;
        initComponents();
        listaEmpleado();
        jTable1.getTableHeader().setReorderingAllowed(false);
        this.setLocationRelativeTo(null);
        AutoCompleteDecorator.decorate(jComboBoxNombreEmpleado);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/Actividad seleccionado.png")).getImage());
        jButtonEliminar.setEnabled(false);
    }
    
        private int detalleEmpleados=0;
        private int detalleMaquinas=0;
        private int detalleQuimicos=0;
        private int codigoDetalleActividad = 0;
        private Date fecha;
        private String area = null;
        private String nombreActividad = null;
    public void cargarDetalles(int detalleEmpleados,int detalleMaquinas,int detalleQuimicos, 
                               int codigoDetalleActividad,Date fecha,String area, String nombreActividad){
        this.detalleEmpleados = detalleEmpleados;
        this.detalleMaquinas = detalleMaquinas;
        this.detalleQuimicos = detalleQuimicos;
        this.codigoDetalleActividad = codigoDetalleActividad;
        this.fecha = fecha;
        this.area = area;
        this.nombreActividad=nombreActividad;
        if(detalleEmpleados>0){
            jTextCodigo.setText(Integer.toString(this.detalleEmpleados));
            mostrarDatos(this.detalleEmpleados);
        }else{
            jTextCodigo.setText(Integer.toString(codigoDetalleActividad));
        }
    }
    private int codigoNombreArea(){
        int codigoNombreEmpleado = 0;
        try {
            String r = "SELECT CODIGO_AREA FROM "+conectar.user_admin+".AREA WHERE NOMBRE_AREA= '"+this.area+"' ORDER BY 1";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    codigoNombreEmpleado = Integer.parseInt(fila[i].toString());
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error");
            System.out.println(e);
        }
        return codigoNombreEmpleado;
    }
    public void listaEmpleado(){
        try {
            st=cn.createStatement();
            String r = "SELECT NOMBRE_EMPLEADO||' '||APELLIDO_EMPLEADO FROM "+conectar.user_admin+".EMPLEADO WHERE codigo_estadoempleado = 501";
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
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error Al Conectar: "+e);
        }
    }
    private int codigoEmpleado(){
        int codigoNombreEmpleado = 0;
        try {
            String r = "SELECT CODIGO_EMPLEADO FROM "+conectar.user_admin+".EMPLEADO WHERE codigo_estadoempleado = 501 "
                    + " AND NOMBRE_EMPLEADO||' '||APELLIDO_EMPLEADO = '"+jComboBoxNombreEmpleado.getSelectedItem().toString()+"'";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    codigoNombreEmpleado = Integer.parseInt(fila[i].toString());
                }
            }
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al devolver codigo empleado");
            System.out.println(e);
        }
        return codigoNombreEmpleado;
    }
    public void mostrarDatos(int codigoDetalleEmpleado){
        DefaultTableModel modelo = new DefaultTableModel();
        jTable1.setModel(dEmpleado.mostrarDatos(modelo, codigoDetalleEmpleado));
        sumar();
        jLabelNoFilas.setText(Integer.toString(jTable1.getRowCount()));
    }
    public void guardar(){
        if(jTextCantidad.getText().length()>0){
            int vSelec = JOptionPane.showConfirmDialog(this, "¿Desea Guardar?","DETALLE EMPLEADO",JOptionPane.WARNING_MESSAGE);
            if (vSelec == 0){
                DateFormat variableFecha = DateFormat.getDateInstance();
                String fecha1 = variableFecha.format(fecha);
                Calendar horaSistema = Calendar.getInstance();        
                String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
                String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
                String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
                String horaRegistro = hora+":"+minutos+":"+segundos;

                int codigoDetalleEmpleado = Integer.parseInt(jTextCodigo.getText());
                int codigoEmpleado = codigoEmpleado();
                float cantidad = Float.parseFloat(jTextCantidad.getText());
                dEmpleado.guardarDatos(codigoDetalleEmpleado,codigoNombreArea(),codigoEmpleado,cantidad);
                mostrarDatos(codigoDetalleEmpleado);
                jTextCantidad.setText("");
                jComboBoxNombreEmpleado.requestFocus();
                jLabelNoFilas.setText(Integer.toString(jTable1.getRowCount()));
            }
        }else{
            JOptionPane.showMessageDialog(this, "Ingresa Cantidad De Libras Cortadas","Detalle Empleados",JOptionPane.WARNING_MESSAGE);
            jTextCantidad.requestFocus();
        }
    }
    public void eliminar(){
        int vSelec = JOptionPane.showConfirmDialog(this, "¿Desea Eliminar?");
        if (vSelec == 0){
            int codigoDetalleEmpleado = Integer.parseInt(jTextCodigo.getText());
            int codigoEmpleado = codigoEmpleado();
            dEmpleado.eliminarDatos(codigoDetalleEmpleado, codigoEmpleado);
            mostrarDatos(codigoDetalleEmpleado);
            jButtonEliminar.setEnabled(false);
            jButtonOk.setEnabled(true);
        }
    }
    private void sumar(){
        float cantidadAcumulada=0; 
            for(int i =0; i < jTable1.getRowCount(); i++){
                String cant = String.valueOf(jTable1.getValueAt(i, 2));
                    cantidadAcumulada +=Float.parseFloat(cant);
            }
            jLabelCantidadAcumulado.setText(Float.toString(cantidadAcumulada));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new rojerusan.RSTableMetro();
        jPanel3 = new javax.swing.JPanel();
        jTextCodigo = new javax.swing.JTextField();
        jComboBoxNombreEmpleado = new javax.swing.JComboBox<>();
        jButtonOk = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jLabelCodigo = new javax.swing.JLabel();
        jLabelNombreEmpleado = new javax.swing.JLabel();
        jLabelNombreEmpleado1 = new javax.swing.JLabel();
        jTextCantidad = new javax.swing.JTextField();
        jLabelAcumulado = new javax.swing.JLabel();
        jLabelCantidadAcumulado = new javax.swing.JLabel();
        jLabelCodigo1 = new javax.swing.JLabel();
        jLabelNoFilas = new javax.swing.JLabel();
        Titulo = new javax.swing.JToggleButton();
        jButtonExportar = new javax.swing.JButton();
        jButtonRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("DETALLE DE EMPLEADOS");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(92, 125, 137));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBar(null);

        jTable1.setAltoHead(25);
        jTable1.setColorBackgoundHead(new java.awt.Color(172, 193, 184));
        jTable1.setColorBordeHead(new java.awt.Color(255, 255, 255));
        jTable1.setColorFilasBackgound2(new java.awt.Color(255, 255, 255));
        jTable1.setColorFilasForeground1(new java.awt.Color(0, 0, 0));
        jTable1.setColorFilasForeground2(new java.awt.Color(0, 0, 0));
        jTable1.setFuenteFilas(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTable1.setFuenteFilasSelect(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jTable1.setFuenteHead(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setInheritsPopupMenu(true);
        jTable1.setIntercellSpacing(new java.awt.Dimension(5, 5));
        jTable1.setRowHeight(20);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(124, 147, 100));

        jTextCodigo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextCodigo.setToolTipText("Codigo Detalle Empleado");
        jTextCodigo.setName(""); // NOI18N
        jTextCodigo.setNextFocusableComponent(jComboBoxNombreEmpleado);
        jTextCodigo.setSelectionColor(new java.awt.Color(0, 0, 0));
        jTextCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextCodigoKeyTyped(evt);
            }
        });

        jComboBoxNombreEmpleado.setEditable(true);
        jComboBoxNombreEmpleado.setToolTipText("Empleados");
        jComboBoxNombreEmpleado.setNextFocusableComponent(jComboBoxNombreEmpleado);
        jComboBoxNombreEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxNombreEmpleadoMouseClicked(evt);
            }
        });
        jComboBoxNombreEmpleado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jComboBoxNombreEmpleadoKeyTyped(evt);
            }
        });

        jButtonOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar Completo.png"))); // NOI18N
        jButtonOk.setToolTipText("Guardar Datos");
        jButtonOk.setBorderPainted(false);
        jButtonOk.setContentAreaFilled(false);
        jButtonOk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonOk.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar Completo.png"))); // NOI18N
        jButtonOk.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar Completo Seleccionado.png"))); // NOI18N
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Eliminar Completo.png"))); // NOI18N
        jButtonEliminar.setToolTipText("Eliminar Datos");
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

        jLabelCodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo.setText("Codigo del Detalle");

        jLabelNombreEmpleado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNombreEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombreEmpleado.setText("Nombre del Empleado");

        jLabelNombreEmpleado1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNombreEmpleado1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombreEmpleado1.setText("Cantidad");

        jTextCantidad.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextCantidad.setToolTipText("Codigo Actividad");
        jTextCantidad.setName(""); // NOI18N
        jTextCantidad.setNextFocusableComponent(jComboBoxNombreEmpleado);
        jTextCantidad.setSelectionColor(new java.awt.Color(0, 0, 0));
        jTextCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextCantidadKeyTyped(evt);
            }
        });

        jLabelAcumulado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelAcumulado.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAcumulado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelAcumulado.setText("Acumulado:");

        jLabelCantidadAcumulado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCantidadAcumulado.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCantidadAcumulado.setText("##");

        jLabelCodigo1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo1.setText("Filas: ");

        jLabelNoFilas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNoFilas.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNoFilas.setText("##");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelNombreEmpleado1)
                    .addComponent(jLabelCodigo)
                    .addComponent(jLabelNombreEmpleado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelCodigo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelNoFilas))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelAcumulado, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonEliminar)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCantidadAcumulado, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelCodigo1)
                        .addComponent(jLabelNoFilas))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelCodigo)
                        .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxNombreEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNombreEmpleado)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNombreEmpleado1)
                    .addComponent(jTextCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAcumulado)
                    .addComponent(jLabelCantidadAcumulado))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        Titulo.setBackground(new java.awt.Color(51, 204, 255));
        Titulo.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        Titulo.setForeground(new java.awt.Color(102, 102, 102));
        Titulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actividad seleccionado.png"))); // NOI18N
        Titulo.setText("DETALLE EMPLEADOS");
        Titulo.setBorder(null);
        Titulo.setBorderPainted(false);
        Titulo.setContentAreaFilled(false);
        Titulo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jButtonExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar Completo.png"))); // NOI18N
        jButtonExportar.setToolTipText("Exportar Tabla A Excel");
        jButtonExportar.setBorder(null);
        jButtonExportar.setBorderPainted(false);
        jButtonExportar.setContentAreaFilled(false);
        jButtonExportar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar Completo.png"))); // NOI18N
        jButtonExportar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar Seleccionado .png"))); // NOI18N
        jButtonExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportarActionPerformed(evt);
            }
        });

        jButtonRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Regresar.png"))); // NOI18N
        jButtonRegresar.setToolTipText("Exportar Tabla A Excel");
        jButtonRegresar.setBorder(null);
        jButtonRegresar.setBorderPainted(false);
        jButtonRegresar.setContentAreaFilled(false);
        jButtonRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonRegresar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Regresar.png"))); // NOI18N
        jButtonRegresar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Regresar seleccionado.png"))); // NOI18N
        jButtonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonRegresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonExportar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonExportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int filaSeleccionada = jTable1.rowAtPoint(evt.getPoint());
        String codigo = String.valueOf(jTable1.getValueAt(filaSeleccionada, 0));
        String nombreEmpleado = String.valueOf(jTable1.getValueAt(filaSeleccionada, 1));
        String cantidad = String.valueOf(jTable1.getValueAt(filaSeleccionada, 2));
        jTextCantidad.setText(cantidad);
        jTextCodigo.setText(codigo);
        jComboBoxNombreEmpleado.setSelectedItem(nombreEmpleado);
        jButtonOk.setEnabled(false);
        jButtonEliminar.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked
    private void jTextCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCodigoKeyTyped
        int numeroCaracteres = 2;
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();

        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrear Solo Numero");
        }
    }//GEN-LAST:event_jTextCodigoKeyTyped
    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        if(jTextCodigo.getText().length()==0){
            JOptionPane.showMessageDialog(this, "Ingrese Codigo Para Guardar","DETALLE EMPLEADO",JOptionPane.WARNING_MESSAGE);
        }else{
            guardar();
            sumar();
            
        }
    }//GEN-LAST:event_jButtonOkActionPerformed
    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        if(jTextCodigo.getText().length()==0){
            JOptionPane.showMessageDialog(this, "Ingrese Codigo Para Eliminar","DETALLE EMPLEADO",JOptionPane.WARNING_MESSAGE);
        }else{
            eliminar();
            sumar();
            jLabelNoFilas.setText(Integer.toString(jTable1.getRowCount()));
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed
    private void jButtonExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportarActionPerformed
        if(this.jTable1.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "No hay datos en la tabla para exportar.","Detalle Empleados", JOptionPane.INFORMATION_MESSAGE);
            this.jTextCodigo.grabFocus();
            return;
        }
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de excel", "xls");
        chooser.setFileFilter(filter);
        chooser.setDialogTitle("Guardar archivo");
        chooser.setMultiSelectionEnabled(false);
        chooser.setAcceptAllFileFilterUsed(false);
        if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
            List<JTable> tb = new ArrayList<>();
            List<String>nom = new ArrayList<>();
            tb.add(jTable1);
            nom.add("Detalle Empleados");
            String file=chooser.getSelectedFile().toString().concat(".xls");
            try{
                ExportarAExcel e = new ExportarAExcel(new File(file),tb , nom);
                if(e.export()){
                    JOptionPane.showMessageDialog(null, "Los datos fueron exportados a excel.", "Detalle Empleados",JOptionPane.INFORMATION_MESSAGE);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Ocurrio un error " + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonExportarActionPerformed
    private void jButtonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegresarActionPerformed
        if(jTable1.getRowCount()>0){
            int vSelect = JOptionPane.showConfirmDialog(null, "¿Desea Regresar?");
            if(vSelect==0){
                this.setVisible(false);
//                this.dispose();
                detalleEmpleados = Integer.parseInt(jTextCodigo.getText());    
                RegistroActividad ventanaRegistroActividad = new RegistroActividad(pa, true);
                try {
                    ventanaRegistroActividad.cargarDetalles(detalleEmpleados, codigoDetalleActividad,"",fecha,area,nombreActividad);
                    conectar.desconectar();
                } catch (SQLException ex) {
                    Logger.getLogger(DetalleEmpleadoCorte.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventanaRegistroActividad.setVisible(true);
            }
        }else{
            int vSelect = JOptionPane.showConfirmDialog(null, "¿Desea Regresar?");
            if(vSelect==0){
                this.setVisible(false);
//                this.dispose();
                RegistroActividad ventanaRegistroActividad = new RegistroActividad(pa, true);
                try {
                    ventanaRegistroActividad.cargarDetalles(0,codigoDetalleActividad,"",fecha,area,nombreActividad);
                } catch (SQLException ex) {
                    Logger.getLogger(DetalleEmpleadoCorte.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventanaRegistroActividad.setVisible(true);
            }
        }
    }//GEN-LAST:event_jButtonRegresarActionPerformed
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if(jTable1.getRowCount()>0){
            int vSelect = JOptionPane.showConfirmDialog(null, "¿Desea Regresar?");
            if(vSelect==0){
                this.setVisible(false);
//                this.dispose();
                detalleEmpleados = Integer.parseInt(jTextCodigo.getText());    
                RegistroActividad ventanaRegistroActividad = new RegistroActividad(pa, true);
                try {
                    ventanaRegistroActividad.cargarDetalles(detalleEmpleados, codigoDetalleActividad,"",fecha,area,nombreActividad);
                    conectar.desconectar();
                } catch (SQLException ex) {
                    Logger.getLogger(DetalleEmpleadoCorte.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventanaRegistroActividad.setVisible(true);
            }
        }else{
            int vSelect = JOptionPane.showConfirmDialog(null, "¿Desea Regresar?");
            if(vSelect==0){
                this.setVisible(false);
//                this.dispose();
                RegistroActividad ventanaRegistroActividad = new RegistroActividad(pa, true);
                try {
                    ventanaRegistroActividad.cargarDetalles(0,codigoDetalleActividad,"",fecha,area,nombreActividad);
                } catch (SQLException ex) {
                    Logger.getLogger(DetalleEmpleadoCorte.class.getName()).log(Level.SEVERE, null, ex);
                }
                ventanaRegistroActividad.setVisible(true);
            }
        }
    }//GEN-LAST:event_formWindowClosing
    private void jComboBoxNombreEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxNombreEmpleadoMouseClicked
        jButtonEliminar.setEnabled(false);
        jButtonOk.setEnabled(true);
    }//GEN-LAST:event_jComboBoxNombreEmpleadoMouseClicked

    private void jTextCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCantidadKeyTyped
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();

        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrear Solo Numero");
        }else if(teclaEnter == KeyEvent.VK_ENTER){       
            guardar();
            sumar();
        }


    }//GEN-LAST:event_jTextCantidadKeyTyped

    private void jComboBoxNombreEmpleadoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jComboBoxNombreEmpleadoKeyTyped
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();

        if(!Character.isLetter(validar)){
            jButtonEliminar.setEnabled(false);
            jButtonOk.setEnabled(true);
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingrear Solo Letras");
        }
    }//GEN-LAST:event_jComboBoxNombreEmpleadoKeyTyped

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
            java.util.logging.Logger.getLogger(DetalleEmpleadoCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetalleEmpleadoCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetalleEmpleadoCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetalleEmpleadoCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DetalleEmpleadoCorte dialog = new DetalleEmpleadoCorte(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Titulo;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonExportar;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButtonRegresar;
    private javax.swing.JComboBox<String> jComboBoxNombreEmpleado;
    private javax.swing.JLabel jLabelAcumulado;
    private javax.swing.JLabel jLabelCantidadAcumulado;
    private javax.swing.JLabel jLabelCodigo;
    private javax.swing.JLabel jLabelCodigo1;
    private javax.swing.JLabel jLabelNoFilas;
    private javax.swing.JLabel jLabelNombreEmpleado;
    private javax.swing.JLabel jLabelNombreEmpleado1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private rojerusan.RSTableMetro jTable1;
    private javax.swing.JTextField jTextCantidad;
    private javax.swing.JTextField jTextCodigo;
    // End of variables declaration//GEN-END:variables
}