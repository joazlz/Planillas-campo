package VIEWS;
import clases.ConexionBD;
import BDD.SQLActividad;
import BDD.SQLTipoEmpleado;
import clases.ExportarAExcel;
import com.sun.glass.events.KeyEvent;
import java.awt.FileDialog;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class TIPOEMPLEADO extends javax.swing.JFrame {
    //---------------------Variables--------------------//
    private SQLTipoEmpleado TipoEmpleado = new SQLTipoEmpleado(this);
    private int codigoTipoEmpleado = 0;
    private boolean buscando = false;
    //-------------------------------------------------//
    
    public TIPOEMPLEADO() {
        initComponents();
        this.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/Tipo Empleado Seleccionado.png")).getImage());
        jButtonEliminar.setVisible(false);
        jButtonEliminar.setEnabled(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTextCodigo.requestFocus();
        jButtonActualizar.setEnabled(false); 
        jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
    } 
    
    //Muestra Toda La Tabla en el jTable
    private void mostrarDatos(){  
            DefaultTableModel modelo1 = new DefaultTableModel();
            this.jTable1.setModel(TipoEmpleado.mostrarDatos(modelo1));   
            jBottonMostrarTabla.setSelected(false);
    }     
    //Guarda una nueva TipoEmpleado
    private void guardarDatos(){ 
        try{
            if(jTextCodigo.getText().length()==0 || jTextNombre.getText().length()==0){
                JOptionPane.showMessageDialog(this, "Ingrese Datos Para Guardar","TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
            }else if(Integer.parseInt(jTextCodigo.getText())>0){
                if(TipoEmpleado.existeTipoEmpleado(Integer.parseInt(jTextCodigo.getText().toString()))){
                        JOptionPane.showMessageDialog(this, "Este Codigo ya Existe\nEs Para el Tipo: "+ " "+ TipoEmpleado.getNombreTipoEmpleado(Integer.parseInt(jTextCodigo.getText().toString())),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                        int codigoTipoEmpleado = Integer.parseInt(jTextCodigo.getText());
                        buscarDatosCodigo();
                }else if(existeNombre()==true){
                            JOptionPane.showMessageDialog(this, "Este Nombre ya Existe\nEs Para El Codigo: "+ " "+ TipoEmpleado.getCodigoTipoEmpleado(jTextNombre.getText()),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                }else if(TipoEmpleado.existeTipoEmpleado(Integer.parseInt(jTextCodigo.getText().toString()))==true){
                    JOptionPane.showMessageDialog(this, "Este Codigo ya Existe\nEs Para La Medida Basica: "+ " "+ TipoEmpleado.getNombreTipoEmpleado(Integer.parseInt(jTextCodigo.getText().toString())),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                    int codigoTipoEmpleado = Integer.parseInt(jTextCodigo.getText());
                    buscarDatosCodigo();
                }else {
                    int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Guardar?","TIPOS DE EMPLEADOS",JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION);
                    if(respuesta == 0){
                        try{
                            int codigoTipoEmpleado = Integer.parseInt(jTextCodigo.getText());
                            String nombreTipoEmpleado = jTextNombre.getText(); 
                            TipoEmpleado.guardarDatos(codigoTipoEmpleado, nombreTipoEmpleado);
                            limpiarjText();
                            mostrarDatos();
                        }catch(Exception e){
                            JOptionPane.showMessageDialog(this, "Ingrese Datos Para Poder Guardar","TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                        }
                        jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this, "No Ingrese Valores Negativos","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);                
                JOptionPane.showMessageDialog(this, "Usa Este Codigo: "+Integer.toString(TipoEmpleado.getRegistroMaximo()+1),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
            }
        }catch(java.lang.NumberFormatException e){
            JOptionPane.showMessageDialog(this, "No Ingrese Signos","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
            JOptionPane.showMessageDialog(this, "Usa Este Codigo: "+Integer.toString(TipoEmpleado.getRegistroMaximo()+1),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
            jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
        }
    } 
    //Elimina una TipoEmpleado
    private void eliminarDatos(){
        int codigoTipoEmpleado = Integer.parseInt(jTextCodigo.getText());
        TipoEmpleado.eliminarDatos(codigoTipoEmpleado);
        jButtonActualizar.setEnabled(false);
        jButtonEliminar.setEnabled(false);
    }
    //Busca TipoEmpleado por codigo
    private void buscarDatosCodigo(){
        try{
            DefaultTableModel modelo1 = new DefaultTableModel();
            int codigoTipoEmpleado = Integer.parseInt(jTextCodigo.getText());
            if(codigoTipoEmpleado>0){
                boolean existe = TipoEmpleado.existeTipoEmpleado(codigoTipoEmpleado);
                if(existe==true){
                    this.jTable1.setModel(TipoEmpleado.buscarDatos(modelo1,codigoTipoEmpleado));
                    this.codigoTipoEmpleado = codigoTipoEmpleado;
                    this.buscando=true;
                    jTextNombre.setText(TipoEmpleado.getNombreTipoEmpleado(codigoTipoEmpleado));
                    jButtonOk.setEnabled(false);
                    jButtonActualizar.setEnabled(true);
                    jButtonEliminar.setEnabled(true);
                }else{
                    codigoTipoEmpleado = Integer.parseInt(jTextCodigo.getText());
                    if(jTextNombre.getText().length()>0){
                        guardarDatos();
                        this.jTable1.setModel(TipoEmpleado.buscarDatos(modelo1,codigoTipoEmpleado));
                    }else if(existe == false){
                            JOptionPane.showMessageDialog(null, "No Existe Tipo De Empleado","TIPOS DE EMPLEADOS", JOptionPane.OK_CANCEL_OPTION);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this, "No Ingreses Numeros Negativos","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
                jTextCodigo.setText("");
            }
        }catch(java.lang.NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Ingrese en el Campo de Codigo solo Numeros","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Ver existencia de nombre de TipoEmpleado
    private boolean existeNombre(){
        String nombreTipoEmpleado = jTextNombre.getText(); 
        int codigoTipoEmpleado = TipoEmpleado.getCodigoTipoEmpleado(nombreTipoEmpleado);
        return TipoEmpleado.existeTipoEmpleado(codigoTipoEmpleado);
    }
    //Busca TipoEmpleado por nombre
    private void buscarDatosNombre(){
        try{
            DefaultTableModel modelo1 = new DefaultTableModel();
            String nombreTipoEmpleado = jTextNombre.getText(); 
            int codigoTipoEmpleado = TipoEmpleado.getCodigoTipoEmpleado(nombreTipoEmpleado);
            this.jTable1.setModel(TipoEmpleado.buscarDatos(modelo1,codigoTipoEmpleado));
            boolean existe = TipoEmpleado.existeTipoEmpleado(codigoTipoEmpleado);
            if(existe==true){
                jTextCodigo.setText(Integer.toString(codigoTipoEmpleado));
                this.codigoTipoEmpleado=codigoTipoEmpleado;
                this.buscando=true;
                jButtonOk.setEnabled(false);
                jButtonActualizar.setEnabled(true);
                jButtonEliminar.setEnabled(true);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ingrese Un Nombre Para Buscar","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Actualiza el nombre de una TipoEmpleado
    private void actualizarDatos(){
        try{
            int codigoTipoEmpleado = Integer.parseInt(jTextCodigo.getText());
            String nombreTipoEmpleado = jTextNombre.getText();
            if(nombreTipoEmpleado.length()>0){
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Actualizar?","TIPOS DE EMPLEADOS",JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION);
                if(respuesta == 0){
                    TipoEmpleado.actualizarDatos(this.codigoTipoEmpleado, nombreTipoEmpleado,codigoTipoEmpleado);
                    limpiarjText();
                    jButtonActualizar.setEnabled(false);
                    jButtonEliminar.setEnabled(false);
                    jButtonActualizar.setOpaque(false);
                    jButtonEliminar.setOpaque(false);
                    jButtonOk.setEnabled(true);
                    jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
                }
            }else{    
                JOptionPane.showMessageDialog(this, "Coloca Algun Nombre Para El Tipo Bodega","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "No Puedes Dejar Campos Vacios","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
    }
    //Limpia los jTex
    private void limpiarjText(){
        jTextNombre.setText("");
    }
    //Reinicia valores de la Ventna
    public void limpiarVentana(){
        if(jTable1.getRowCount()>0 || jTextNombre.getText().length()>0){
            jTextNombre.setText("");
            DefaultTableModel model = new DefaultTableModel();
            jTable1.setModel(model);
            jTable1.getTableHeader().setReorderingAllowed(false);
            jTextCodigo.requestFocus();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextCodigo = new javax.swing.JTextField();
        jTextNombre = new javax.swing.JTextField();
        jLabelCodigo = new javax.swing.JLabel();
        jLabelNombre = new javax.swing.JLabel();
        jBottonMostrarTabla = new javax.swing.JToggleButton();
        jButtonBuscar = new javax.swing.JButton();
        jButtonOk = new javax.swing.JButton();
        jButtonActualizar = new javax.swing.JButton();
        Titulo = new javax.swing.JToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new rojerusan.RSTableMetro();
        jButtonExportar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TIPOS DE EMPLEADOS");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(92, 125, 137));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(124, 147, 100));

        jTextCodigo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextCodigo.setToolTipText("Codigo Tipo Empleado");
        jTextCodigo.setName(""); // NOI18N
        jTextCodigo.setNextFocusableComponent(jTextNombre);
        jTextCodigo.setSelectionColor(new java.awt.Color(0, 0, 0));
        jTextCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextCodigoKeyTyped(evt);
            }
        });

        jTextNombre.setToolTipText("Nombre Tipo Empleado");
        jTextNombre.setNextFocusableComponent(jTextCodigo);
        jTextNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextNombreKeyTyped(evt);
            }
        });

        jLabelCodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo.setText("CODIGO TIPO");

        jLabelNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNombre.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombre.setText("NOMBRE TIPO");

        jBottonMostrarTabla.setBackground(new java.awt.Color(51, 204, 255));
        jBottonMostrarTabla.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBottonMostrarTabla.setForeground(new java.awt.Color(255, 255, 255));
        jBottonMostrarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo.png"))); // NOI18N
        jBottonMostrarTabla.setToolTipText("Mostar Datos");
        jBottonMostrarTabla.setBorder(null);
        jBottonMostrarTabla.setBorderPainted(false);
        jBottonMostrarTabla.setContentAreaFilled(false);
        jBottonMostrarTabla.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBottonMostrarTabla.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo.png"))); // NOI18N
        jBottonMostrarTabla.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo Seleccionado.png"))); // NOI18N
        jBottonMostrarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBottonMostrarTablaActionPerformed(evt);
            }
        });

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo.png"))); // NOI18N
        jButtonBuscar.setToolTipText("Buscar Datos");
        jButtonBuscar.setBorder(null);
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

        jButtonActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar Completo.png"))); // NOI18N
        jButtonActualizar.setToolTipText("Actualizar Datos");
        jButtonActualizar.setBorder(null);
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButtonActualizar)
                        .addGap(8, 8, 8)
                        .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jBottonMostrarTabla)))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCodigo)
                    .addComponent(jLabelNombre))
                .addGap(135, 135, 135))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jBottonMostrarTabla, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonBuscar))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelCodigo)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonActualizar))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Titulo.setBackground(new java.awt.Color(51, 204, 255));
        Titulo.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        Titulo.setForeground(new java.awt.Color(102, 102, 102));
        Titulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Tipo Empleado Seleccionado.png"))); // NOI18N
        Titulo.setText("TIPOS DE EMPLEADOS");
        Titulo.setBorder(null);
        Titulo.setBorderPainted(false);
        Titulo.setContentAreaFilled(false);
        Titulo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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
        jTable1.setMultipleSeleccion(false);
        jTable1.setRowHeight(20);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTable1KeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

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

        jButtonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Eliminar Completo.png"))); // NOI18N
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonExportar)
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jButtonExportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonEliminar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
      
    private void jBottonMostrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBottonMostrarTablaActionPerformed
        mostrarDatos();
        jButtonActualizar.setEnabled(false);
        jButtonEliminar.setEnabled(false);
        jButtonOk.setEnabled(true);
        limpiarjText();
        jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
    }//GEN-LAST:event_jBottonMostrarTablaActionPerformed
    private void jTextCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCodigoKeyTyped
        int numeroCaracteres = 4;
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();
        
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();   
            JOptionPane.showMessageDialog(this, "Ingrear Solo Numero","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }
        else{
            if(this.buscando==true){
                jButtonActualizar.setEnabled(false);
                jButtonOk.setEnabled(true);
            }
            if(teclaEnter==KeyEvent.VK_ENTER){
                try{
                    if(jTextCodigo.getText().length()>0){
                        int codigo = Integer.parseInt(jTextCodigo.getText());
                        if(TipoEmpleado.existeTipoEmpleado(Integer.parseInt(jTextCodigo.getText().toString()))){
                            JOptionPane.showMessageDialog(this, "Este Codigo ya Existe\nEs Para El Tipo: "+ " "+ TipoEmpleado.getNombreTipoEmpleado(Integer.parseInt(jTextCodigo.getText().toString())),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                            buscarDatosCodigo();
                        }else{
                            guardarDatos();
                        }
                    }else if(Integer.parseInt(jTextCodigo.getText())<0){
                        JOptionPane.showMessageDialog(this, "No Ingreses Numeros Negativos","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
                        jTextCodigo.setText("");
                    }else if(jTextNombre.getText().length()>0){
                        buscarDatosNombre();
                    }else{
                        JOptionPane.showMessageDialog(this, "Ingrese Valores","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
                    }
                }catch(java.lang.NumberFormatException e){
                    JOptionPane.showMessageDialog(this, "Ingrese en el Campo de Codigo solo Numeros","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
                    JOptionPane.showMessageDialog(this, "Usa Este Codigo: "+Integer.toString(TipoEmpleado.getRegistroMaximo()+1),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                    jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
                }
            }else if (jTextCodigo.getText().length()>numeroCaracteres){
                evt.consume();
                getToolkit().beep();
                JOptionPane.showMessageDialog(this, "No Ingresar más de 5 Digitos","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTextCodigoKeyTyped
    private void jTextNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNombreKeyTyped
        int numeroCaracteres =29;
        char teclaEnter = evt.getKeyChar();
        if (jTextNombre.getText().length()>numeroCaracteres){
            evt.consume();
            getToolkit().beep();
            JOptionPane.showMessageDialog(this, "No Ingresar más de 30 Caracteres","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }else if(teclaEnter == KeyEvent.VK_ENTER){                
            try{
                    if(jTextNombre.getText().length() > 0 && jTextCodigo.getText().length()<0){
                        buscarDatosNombre();
                    }else{
                        if(existeNombre()==true){
                            JOptionPane.showMessageDialog(this, "Este Nombre ya Existe\nEs Para El Codigo: "+ " "+ TipoEmpleado.getCodigoTipoEmpleado(jTextNombre.getText()),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                        }else if(TipoEmpleado.existeTipoEmpleado(Integer.parseInt(jTextCodigo.getText().toString()))==true){
                            JOptionPane.showMessageDialog(this, "Este Codigo ya Existe\nEs Para El Tipo: "+ " "+ TipoEmpleado.getNombreTipoEmpleado(Integer.parseInt(jTextCodigo.getText().toString())),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                            int codigoTipoEmpleado = Integer.parseInt(jTextCodigo.getText());
                            buscarDatosCodigo();
                        }else if(jTextNombre.getText().length()>0&&jTextCodigo.getText().length()>0){
                            guardarDatos();
                            jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
                        }else if(Integer.parseInt(jTextCodigo.getText())<0){
                            JOptionPane.showMessageDialog(this, "No Ingreses Numeros Negativos","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
                            jTextCodigo.setText("");
                            JOptionPane.showMessageDialog(this, "Usa Este Codigo: "+Integer.toString(TipoEmpleado.getRegistroMaximo()+1),"TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                            jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
                        }else{
                            JOptionPane.showMessageDialog(this, "No Existe","TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
                            jButtonActualizar.setEnabled(false);
                            jButtonActualizar.setEnabled(false);
                            jButtonOk.setEnabled(true);
                        }
                    }
            }catch(java.lang.NumberFormatException e){
                JOptionPane.showMessageDialog(this, "Ingrese en el Campo de Codigo solo Numeros","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTextNombreKeyTyped
    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if(jTextCodigo.getText().length()==0 && jTextNombre.getText().length()==0){
            JOptionPane.showMessageDialog(this, "Ingrese Datos Para Buscar","TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
        }else if(jTextCodigo.getText().length()==0){
            buscarDatosNombre();
        }else if (jTextNombre.getText().length()==0 || jTextCodigo.getText().length()!=0){
            buscarDatosCodigo();
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed
    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        if(jTextCodigo.getText().length()==0 && jTextNombre.getText().length()==0){
            JOptionPane.showMessageDialog(this, "Ingrese Datos Para Guardar","TIPOS DE EMPLEADOS", JOptionPane.ERROR_MESSAGE);
        }else if(jTextNombre.getText().length()==0){
            JOptionPane.showMessageDialog(this, "Ingrese Nombre Para Guardar","TIPOS DE EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        } else if(jTextCodigo.getText().length()==0){
            JOptionPane.showMessageDialog(this, "Ingrese Codigo Para Guardar","TIPOS DE EMPLEADOS", JOptionPane.WARNING_MESSAGE);
        }else{
            guardarDatos();
        }
    }//GEN-LAST:event_jButtonOkActionPerformed
    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
            actualizarDatos();
            mostrarDatos();
    }//GEN-LAST:event_jButtonActualizarActionPerformed
    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Eliminarlo?","TIPOS DE EMPLEADOS",JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION);
        if(respuesta == 0){
            eliminarDatos();
            mostrarDatos();
            limpiarjText();
            jButtonEliminar.setOpaque(false);
            jButtonOk.setEnabled(true);
            jTextCodigo.setText(Integer.toString(TipoEmpleado.getRegistroMaximo()+1));
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed
    private void jButtonExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportarActionPerformed
        if(this.jTable1.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "No Hay Datos en la Tabla Para Exportar.","TIPOS DE EMPLEADOS", JOptionPane.INFORMATION_MESSAGE);
            this.jTextCodigo.grabFocus();
            return;
        }
        FileDialog dialog = new FileDialog(this,"Save",FileDialog.SAVE);
        dialog.setTitle("Guardar Archivo");
        dialog.setMultipleMode(false);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        if(dialog.getFile()!=null){
            List<JTable> tb = new ArrayList<>();
            List<String>nom = new ArrayList<>();
            tb.add(jTable1);
            nom.add("TIPOS DE EMPLEADOS");
            String file=dialog.getDirectory()+dialog.getFile().concat(".xls");
            try{
                ExportarAExcel e = new ExportarAExcel(new File(file),tb , nom);
                if(e.export()){
                     Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Excel.png")).getImage();
                            ImageIcon im = new ImageIcon(imagen);
                            Icon expo = new ImageIcon(im.getImage());
                            JOptionPane.showMessageDialog(this, "Los Datos Fueron Exportados a Excel", "TIPOS DE EMPLEADOS", JOptionPane.OK_OPTION, expo);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Ocurrio un Error " + ex.getMessage(),"TIPOS DE EMPLEADOS",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonExportarActionPerformed
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int filaSeleccionada = jTable1.rowAtPoint(evt.getPoint());
        String codigo = String.valueOf(jTable1.getValueAt(filaSeleccionada, 0));
        jTextCodigo.setText(codigo);
        this.codigoTipoEmpleado=Integer.parseInt(codigo);
        this.buscando=true;
        jTextNombre.setText(String.valueOf(jTable1.getValueAt(filaSeleccionada, 1)));
        jButtonOk.setEnabled(false);
        jButtonActualizar.setEnabled(true);
        jButtonEliminar.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked
    private void jTable1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyTyped
        JOptionPane.showMessageDialog(this, "Selecciona y edita en la Caja de Texto","TIPOS DE EMPLEADOS", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_jTable1KeyTyped
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        limpiarVentana();
    }//GEN-LAST:event_formWindowClosing
        
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
            java.util.logging.Logger.getLogger(TIPOEMPLEADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TIPOEMPLEADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TIPOEMPLEADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TIPOEMPLEADO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TIPOEMPLEADO().setVisible(true);
            }
        });
    }   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Titulo;
    private javax.swing.JToggleButton jBottonMostrarTabla;
    private javax.swing.JButton jButtonActualizar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonExportar;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JLabel jLabelCodigo;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private rojerusan.RSTableMetro jTable1;
    private javax.swing.JTextField jTextCodigo;
    private javax.swing.JTextField jTextNombre;
    // End of variables declaration//GEN-END:variables
}