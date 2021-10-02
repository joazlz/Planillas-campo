package VIEWS;
import clases.ConexionBD;
import BDD.SQLArea;
import clases.ExportarAExcel;
import com.sun.glass.events.KeyEvent;
import java.awt.FileDialog;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class AREA extends javax.swing.JFrame {
    //------------------Variables-----------------------//
    private SQLArea area = new SQLArea(this);
    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection(); 
    private static Statement st;
    private static ResultSet rs;
    //--------------------------------------------------//
    
    public AREA() {
        initComponents();
        this.setLocationRelativeTo(null);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTextCodigo.requestFocus();
        eliminarDerivadas();
        medidasDerivadas();
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/Area Seleccionado.png")).getImage());
        jButtonEliminar.setVisible(false);
        jButtonActualizar.setEnabled(false);
        jButtonEliminar.setEnabled(false);
        jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
    }

    //Llena el combo box de las medidas
    public void medidasDerivadas(){
        try {
            st=cn.createStatement();
            String r = "SELECT NOMBRE_MEDIDADERIVADA FROM "+conectar.user_admin+".TIPO_MEDIDADERIVADA where codigo_medidabasica = 4 order by codigo_medidaderivada";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    jComboBoxMedidaDerivada.addItem(fila[i].toString());
                }
            }
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error Al Mostrar Medidas Derivadas:\n"+e,"AREAS",JOptionPane.ERROR_MESSAGE);
        }
        jComboBoxMedidaDerivada.select("Unidad");
    }  
    //Devuelve El codigo De La medida Seleccionada
    private int codigoMedidaDerivada(){
        int codigoMedidaDerivada = 0;
        try {
            String r = "SELECT CODIGO_MEDIDADERIVADA FROM "+conectar.user_admin+".TIPO_MEDIDADERIVADA WHERE NOMBRE_MEDIDADERIVADA = '"+jComboBoxMedidaDerivada.getSelectedItem().toString()+"' order by codigo_medidaderivada";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    codigoMedidaDerivada = Integer.parseInt(fila[i].toString());
                }
            }
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error Al Devolver Codigo Medida Derivada:\n"+e,"AREAS",JOptionPane.ERROR_MESSAGE);
        }
        return codigoMedidaDerivada;
    }
    //Limpia el Combo Box
    public void eliminarDerivadas(){
        jComboBoxMedidaDerivada.removeAll();
    }      
    
    //Muestra Datos De La Tabla
    private void mostrarDatos(){
        DefaultTableModel modelo1 = new DefaultTableModel();
        this.jTable1.setModel(area.mostrarDatos(modelo1));
        jBottonMostrarTabla.setSelected(false);
    }   
    //Guarda Una Nueva Area
    private void guardarDatos(){ 
        try{
            if(jTextCodigo.getText().length()>0){
                int codigoArea = Integer.parseInt(jTextCodigo.getText());
                if(area.existeArea(Integer.parseInt(jTextCodigo.getText().toString()))){
                    JOptionPane.showMessageDialog(this, "Este codigo ya existe\nEs para el area : "+ " "+ area.getNombreArea(Integer.parseInt(jTextCodigo.getText().toString())),"AREAS",JOptionPane.WARNING_MESSAGE);
                    JOptionPane.showMessageDialog(this, "Usa El Siguiente: "+(area.getRegistroMaximo()+1),"AREAS",JOptionPane.INFORMATION_MESSAGE);
                    jTextCodigo.setText(Integer.toString((area.getRegistroMaximo()+1)));
                    jTextCodigo.requestFocus();
                }else{
                    if(codigoArea>0){
                        try{
                            if(jTextCantidadPlantas.getText().length()>0){
                                int cantidadPlanta = Integer.parseInt(jTextCantidadPlantas.getText());
                                if(cantidadPlanta>=0){
                                    try{
                                        if(jTextCantidadPlantas.getText().length()>0){
                                            int altura = Integer.parseInt(jTextAltitud.getText());
                                            if(altura>=0){
                                                if(jTextNombreArea.getText().length()>0){
                                                    int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Guardar?","AREAS",JOptionPane.WARNING_MESSAGE);
                                                    if(respuesta == 0){
                                                            String nombreArea = jTextNombreArea.getText();
                                                            int altitudMedida = codigoMedidaDerivada();
                                                        try {
                                                            area.guardarDatos(codigoArea, nombreArea, altura, cantidadPlanta, altitudMedida);
                                                            limpiarjText();
                                                            mostrarDatos();
                                                            jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));        
                                                        } catch (SQLException ex) {
                                                            JOptionPane.showMessageDialog(this, "Error Al Guardar:\n"+ex,"AREAS",JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    }
                                                }else{
                                                    JOptionPane.showMessageDialog(this, "Ingrese Un Nombre al Area","AREAS",JOptionPane.WARNING_MESSAGE);
                                                    jTextNombreArea.requestFocus();
                                                }
                                            }else{
                                                JOptionPane.showMessageDialog(this, "No Ingrese Valor Negativo En La Altitud","AREAS",JOptionPane.WARNING_MESSAGE);
                                                jTextAltitud.setText("");
                                                jTextAltitud.requestFocus();
                                            }
                                        }else{
                                            JOptionPane.showMessageDialog(this, "No Deje La Altura del Area Vacia","AREAS",JOptionPane.WARNING_MESSAGE);
                                            jTextAltitud.requestFocus();
                                        }
                                    }catch(java.lang.NumberFormatException e){
                                        JOptionPane.showMessageDialog(this, "No Ingrese Signos En La Altitud","AREAS",JOptionPane.WARNING_MESSAGE);
                                        jTextAltitud.setText("");
                                        jTextAltitud.requestFocus();
                                    }
                                }else{
                                    JOptionPane.showMessageDialog(this, "No Ingrese Valor Negativo En La Cantidad de Plantas","AREAS",JOptionPane.WARNING_MESSAGE);
                                    jTextCantidadPlantas.setText("");
                                    jTextCantidadPlantas.requestFocus();
                                }
                            }else{
                                JOptionPane.showMessageDialog(this, "No Deje La Cantidad de Plantas Vacia","AREAS",JOptionPane.WARNING_MESSAGE);
                                jTextCantidadPlantas.requestFocus();
                            }
                        }catch(java.lang.NumberFormatException e){
                            JOptionPane.showMessageDialog(this, "No Ingrese Signos En La Cantidad de Plantas","AREAS",JOptionPane.WARNING_MESSAGE);
                            jTextCantidadPlantas.setText("");
                            jTextCantidadPlantas.requestFocus();
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "No Ingrese Signos En El Codigo","AREAS",JOptionPane.WARNING_MESSAGE);
                        JOptionPane.showMessageDialog(this, "Usa El Siguiente: "+(area.getRegistroMaximo()+1),"AREAS",JOptionPane.INFORMATION_MESSAGE);
                        jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
                        jTextCodigo.requestFocus();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(this, "No Deje El Codigo Vacio ","AREAS",JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(this, "Usa El Siguiente: "+(area.getRegistroMaximo()+1),"AREAS",JOptionPane.INFORMATION_MESSAGE);
                jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
                jTextCodigo.requestFocus();
            }
        }catch(java.lang.NumberFormatException e){
            JOptionPane.showMessageDialog(this, "No Ingrese Signos En El Codigo","AREAS",JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Usa El Siguiente: "+(area.getRegistroMaximo()+1),"AREAS",JOptionPane.INFORMATION_MESSAGE);
            jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
            jTextCodigo.requestFocus();
        }
    }
    //Elimina Un Area
    private void eliminarDatos(){
        int codigoArea = Integer.parseInt(jTextCodigo.getText());
        area.eliminarDatos(codigoArea);
    }  
    //Busca Area Por Codigo
    private void buscarDatosCodigo(){
        try{
            DefaultTableModel modelo1 = new DefaultTableModel();
            int codigoArea = Integer.parseInt(jTextCodigo.getText().toString());
            if(area.existeArea(codigoArea)==true){
                jButtonActualizar.setEnabled(true);
                jButtonEliminar.setEnabled(true);
                jButtonOk.setEnabled(false);
                this.jTable1.setModel(area.buscarDatos(codigoArea, modelo1));
                jTextNombreArea.setText(area.getNombreArea(codigoArea));
                jTextCantidadPlantas.setText(Integer.toString(area.getCantidadPlantasArea(codigoArea)));
                jTextAltitud.setText(Integer.toString(area.getTamanio(codigoArea)));
            }else{
                JOptionPane.showMessageDialog(this, "No Existe Codigo de Area","AREAS",JOptionPane.WARNING_MESSAGE);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ingrese Un Valor Valido Para Buscar","AREAS",JOptionPane.ERROR_MESSAGE);
            jTextCodigo.setText("");
            jTextCodigo.requestFocus();
        }
    }
    //Busca Area Por Nombre
    private void buscarDatosNombre(){
        try{
            DefaultTableModel modelo1 = new DefaultTableModel();
            String nombreArea = jTextNombreArea.getText().toString();
            int codigoArea = area.getCodigoArea(nombreArea);
            if(area.existeArea(codigoArea)==true){
                jButtonActualizar.setEnabled(true);
                jButtonEliminar.setEnabled(true);
                jButtonOk.setEnabled(false);
                this.jTable1.setModel(area.buscarDatos(codigoArea, modelo1));
                jTextCodigo.setText(Integer.toString(codigoArea));
                jTextNombreArea.setText(area.getNombreArea(codigoArea));
                jTextCantidadPlantas.setText(Integer.toString(area.getCantidadPlantasArea(codigoArea)));
                jTextAltitud.setText(Integer.toString(area.getTamanio(codigoArea)));
            }else{
                JOptionPane.showMessageDialog(this, "No Existe Area Con Este Nombre","AREAS",JOptionPane.WARNING_MESSAGE);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Ingrese Un Valor Para Buscar","AREAS",JOptionPane.WARNING_MESSAGE);
        }
    }
    //Actualiza Datos de Area
    private void actualizarDatos(){
        try{
            if(jTextCodigo.getText().length()>0){
                int codigoArea = Integer.parseInt(jTextCodigo.getText());
                if(codigoArea>0){
                    try{
                        if(jTextCantidadPlantas.getText().length()>0){
                            int cantidadPlanta = Integer.parseInt(jTextCantidadPlantas.getText());
                            if(cantidadPlanta>=0){
                                try{
                                    if(jTextCantidadPlantas.getText().length()>0){
                                        int altura = Integer.parseInt(jTextAltitud.getText());
                                        if(altura>=0){
                                            if(jTextNombreArea.getText().length()>0){
                                                int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Actualizar?","AREAS",JOptionPane.WARNING_MESSAGE);
                                                if(respuesta == 0){
                                                    String nombreNew = jTextNombreArea.getText();
                                                    int cantidadPlantasNew = Integer.parseInt(jTextCantidadPlantas.getText());
                                                    int altitudNew = Integer.parseInt(jTextAltitud.getText());
                                                    int codigoMedida = codigoMedidaDerivada();
                                                    area.actualizarDatos(codigoArea, nombreNew, cantidadPlantasNew, altitudNew, codigoMedida);
                                                    limpiarjText();
                                                    mostrarDatos();
                                                    jButtonActualizar.setEnabled(false);                                                    
                                                    jButtonEliminar.setEnabled(false);
                                                    jButtonOk.setEnabled(true);
                                                    jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
                                                }
                                            }else{
                                                JOptionPane.showMessageDialog(this, "Ingrese Un Nombre al Area","AREAS",JOptionPane.WARNING_MESSAGE);
                                                jTextNombreArea.requestFocus();
                                            }
                                        }else{
                                            JOptionPane.showMessageDialog(this, "No Ingrese Valor Negativo En La Altitud","AREAS",JOptionPane.WARNING_MESSAGE);
                                            jTextAltitud.setText("");
                                            jTextAltitud.requestFocus();
                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(this, "No Deje La Altura del Area Vacia","AREAS",JOptionPane.WARNING_MESSAGE);
                                        jTextAltitud.requestFocus();
                                    }
                                }catch(java.lang.NumberFormatException e){
                                    JOptionPane.showMessageDialog(this, "No Ingrese Signos En La Altitud","AREAS",JOptionPane.WARNING_MESSAGE);
                                    jTextAltitud.setText("");
                                    jTextAltitud.requestFocus();
                                }
                            }else{
                                JOptionPane.showMessageDialog(this, "No Ingrese Valor Negativo En La Cantidad de Plantas","AREAS",JOptionPane.WARNING_MESSAGE);
                                jTextCantidadPlantas.setText("");
                                jTextCantidadPlantas.requestFocus();
                            }
                        }else{
                            JOptionPane.showMessageDialog(this, "No Deje La Cantidad de Plantas Vacia","AREAS",JOptionPane.WARNING_MESSAGE);
                            jTextCantidadPlantas.requestFocus();
                        }
                    }catch(java.lang.NumberFormatException e){
                        JOptionPane.showMessageDialog(this, "No Ingrese Signos En La Cantidad de Plantas","AREAS",JOptionPane.WARNING_MESSAGE);
                        jTextCantidadPlantas.setText("");
                        jTextCantidadPlantas.requestFocus();
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "No Ingrese Signos En El Codigo","AREAS",JOptionPane.WARNING_MESSAGE);
                    JOptionPane.showMessageDialog(this, "Usa El Siguiente: "+area.getRegistroMaximo()+1,"AREAS",JOptionPane.INFORMATION_MESSAGE);
                    jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
                    jTextCodigo.requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(this, "No Deje El Codigo Vacio ","AREAS",JOptionPane.WARNING_MESSAGE);
                JOptionPane.showMessageDialog(this, "Usa El Siguiente: "+area.getRegistroMaximo()+1,"AREAS",JOptionPane.INFORMATION_MESSAGE);
                jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
                jTextCodigo.requestFocus();
            }
        }catch(java.lang.NumberFormatException e){
            JOptionPane.showMessageDialog(this, "No Ingrese Signos En El Codigo","AREAS",JOptionPane.WARNING_MESSAGE);
            JOptionPane.showMessageDialog(this, "Usa El Siguiente: "+area.getRegistroMaximo()+1,"AREAS",JOptionPane.INFORMATION_MESSAGE);
            jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
            jTextCodigo.requestFocus();
        }
    }
    //Limpia los JText
    private void limpiarjText(){
        jTextAltitud.setText("");
        jTextCantidadPlantas.setText("");
        jTextNombreArea.setText("");
    }
    //Limpia Toda La Ventana
    public void limpiarVentana(){
        if(jTable1.getRowCount()>0 || jTextNombreArea.getText().length()>0 || jTextAltitud.getText().length()>0
                || jTextCantidadPlantas.getText().length()>0){
            jTextAltitud.setText("");
            jTextCantidadPlantas.setText("");
            jTextNombreArea.setText("");
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
        jBottonTituloEtiqueta = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jButtonActualizar = new javax.swing.JButton();
        jButtonOk = new javax.swing.JButton();
        jButtonBuscar = new javax.swing.JButton();
        jBottonMostrarTabla = new javax.swing.JToggleButton();
        jLabelCodigo = new javax.swing.JLabel();
        jTextCodigo = new javax.swing.JTextField();
        jLabelNombreArea = new javax.swing.JLabel();
        jTextNombreArea = new javax.swing.JTextField();
        jLabelCantidadPlantas = new javax.swing.JLabel();
        jTextCantidadPlantas = new javax.swing.JTextField();
        jLabelAltitud = new javax.swing.JLabel();
        jTextAltitud = new javax.swing.JTextField();
        jComboBoxMedidaDerivada = new java.awt.Choice();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new rojerusan.RSTableMetro();
        jButtonExportar2 = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("AREAS");
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(92, 125, 137));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jBottonTituloEtiqueta.setBackground(new java.awt.Color(51, 204, 255));
        jBottonTituloEtiqueta.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jBottonTituloEtiqueta.setForeground(new java.awt.Color(102, 102, 102));
        jBottonTituloEtiqueta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Area Seleccionado.png"))); // NOI18N
        jBottonTituloEtiqueta.setText("AREAS");
        jBottonTituloEtiqueta.setBorder(null);
        jBottonTituloEtiqueta.setBorderPainted(false);
        jBottonTituloEtiqueta.setContentAreaFilled(false);
        jBottonTituloEtiqueta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanel3.setBackground(new java.awt.Color(124, 147, 100));

        jButtonActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar Completo.png"))); // NOI18N
        jButtonActualizar.setToolTipText("Actualizar Datos");
        jButtonActualizar.setBorderPainted(false);
        jButtonActualizar.setContentAreaFilled(false);
        jButtonActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonActualizar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar Completo.png"))); // NOI18N
        jButtonActualizar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actualizar seleccionado Seleccionado.png"))); // NOI18N
        jButtonActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActualizarActionPerformed(evt);
            }
        });

        jButtonOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar Completo.png"))); // NOI18N
        jButtonOk.setToolTipText("Guardar Datos");
        jButtonOk.setBorderPainted(false);
        jButtonOk.setContentAreaFilled(false);
        jButtonOk.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonOk.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar Completo.png"))); // NOI18N
        jButtonOk.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Guardar Completo Seleccionado.png"))); // NOI18N
        jButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOkActionPerformed(evt);
            }
        });

        jButtonBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo.png"))); // NOI18N
        jButtonBuscar.setToolTipText(" Buscar Datos");
        jButtonBuscar.setBorderPainted(false);
        jButtonBuscar.setContentAreaFilled(false);
        jButtonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonBuscar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo.png"))); // NOI18N
        jButtonBuscar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo Seleccionado.png"))); // NOI18N
        jButtonBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarActionPerformed(evt);
            }
        });

        jBottonMostrarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo.png"))); // NOI18N
        jBottonMostrarTabla.setToolTipText("Mostar Datos");
        jBottonMostrarTabla.setBorderPainted(false);
        jBottonMostrarTabla.setContentAreaFilled(false);
        jBottonMostrarTabla.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jBottonMostrarTabla.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo.png"))); // NOI18N
        jBottonMostrarTabla.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo Seleccionado.png"))); // NOI18N
        jBottonMostrarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBottonMostrarTablaActionPerformed(evt);
            }
        });

        jLabelCodigo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCodigo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCodigo.setText("CODIGO");

        jTextCodigo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextCodigo.setToolTipText("Codigo Area");
        jTextCodigo.setNextFocusableComponent(jTextNombreArea);
        jTextCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextCodigoKeyTyped(evt);
            }
        });

        jLabelNombreArea.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelNombreArea.setForeground(new java.awt.Color(255, 255, 255));
        jLabelNombreArea.setText("NOMBRE");

        jTextNombreArea.setToolTipText("Nombre Area");
        jTextNombreArea.setNextFocusableComponent(jTextCantidadPlantas);
        jTextNombreArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextNombreAreaKeyTyped(evt);
            }
        });

        jLabelCantidadPlantas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCantidadPlantas.setForeground(new java.awt.Color(255, 255, 255));
        jLabelCantidadPlantas.setText("CANTIDAD PLANTAS");

        jTextCantidadPlantas.setToolTipText("Cantidad de Plantas");
        jTextCantidadPlantas.setNextFocusableComponent(jTextAltitud);
        jTextCantidadPlantas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextCantidadPlantasKeyTyped(evt);
            }
        });

        jLabelAltitud.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelAltitud.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAltitud.setText("TAMAÑO");

        jTextAltitud.setToolTipText("Altitud de Area");
        jTextAltitud.setNextFocusableComponent(jComboBoxMedidaDerivada);
        jTextAltitud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextAltitudKeyTyped(evt);
            }
        });

        jComboBoxMedidaDerivada.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelCodigo))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButtonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jBottonMostrarTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextNombreArea, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelNombreArea))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelCantidadPlantas)
                                    .addComponent(jTextCantidadPlantas, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelAltitud)
                                    .addComponent(jTextAltitud, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxMedidaDerivada, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)))))
                .addGap(24, 24, 24))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextCodigo)
                            .addComponent(jLabelCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextNombreArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelNombreArea))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelAltitud)
                                    .addComponent(jLabelCantidadPlantas))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextCantidadPlantas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextAltitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jComboBoxMedidaDerivada, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBottonMostrarTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
        );

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
        jTable1.setFuenteHead(new java.awt.Font("Dialog", 1, 12)); // NOI18N
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

        jButtonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Eliminar Completo.png"))); // NOI18N
        jButtonEliminar.setToolTipText("Eliminar");
        jButtonEliminar.setBorderPainted(false);
        jButtonEliminar.setContentAreaFilled(false);
        jButtonEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBottonTituloEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExportar2)
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jBottonTituloEtiqueta)
                            .addComponent(jButtonExportar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
        guardarDatos();
        jButtonActualizar.setEnabled(false);
        jButtonEliminar.setEnabled(false);
    }//GEN-LAST:event_jButtonOkActionPerformed
    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(this, "¿Esta Seguro Que Desea Eliminarlo?","AREAS",JOptionPane.WARNING_MESSAGE);
        if(respuesta == 0){
            eliminarDatos();
            mostrarDatos();
            jButtonActualizar.setEnabled(false);
            jButtonEliminar.setEnabled(false);
            limpiarjText();
            jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed
    private void jButtonBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscarActionPerformed
        if(jTextCodigo.getText().length() !=0)
            buscarDatosCodigo();
        else{
            if(jTextNombreArea.getText().length()!=0)
                buscarDatosNombre();
            else{
                buscarDatosCodigo();
            }
        }
    }//GEN-LAST:event_jButtonBuscarActionPerformed
    private void jBottonMostrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBottonMostrarTablaActionPerformed
        limpiarjText();
        mostrarDatos();
        jButtonActualizar.setEnabled(false);
        jButtonEliminar.setEnabled(false);
        jButtonOk.setEnabled(true);
        jTextCodigo.setText(Integer.toString(area.getRegistroMaximo()+1));
    }//GEN-LAST:event_jBottonMostrarTablaActionPerformed
    private void jButtonActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActualizarActionPerformed
        actualizarDatos();
    }//GEN-LAST:event_jButtonActualizarActionPerformed
    private void jTextCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCodigoKeyTyped
        int numeroCaracteres = 1;
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();   
            JOptionPane.showMessageDialog(this, "Ingrear Solo Numero","AREAS",JOptionPane.WARNING_MESSAGE);
        }
        else{
            if(teclaEnter==KeyEvent.VK_ENTER){
                if(jTextCodigo.getText().length()>0){
                    buscarDatosCodigo();
                }
                else{
                    if(jTextNombreArea.getText().length()>0){
                        buscarDatosNombre();
                    }else{
                        JOptionPane.showMessageDialog(this, "Ingrese Valores Para Buscar","AREAS",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }else if (jTextCodigo.getText().length()>numeroCaracteres){
                evt.consume();
                getToolkit().beep();
                JOptionPane.showMessageDialog(this, "No Ingresar Mas de 2 Digitos","AREAS",JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_jTextCodigoKeyTyped
    private void jTextNombreAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextNombreAreaKeyTyped
        int numeroCaracteres =29;
        char teclaEnter = evt.getKeyChar();
        
            if (jTextNombreArea.getText().length()>numeroCaracteres){
                evt.consume();
                getToolkit().beep();
                JOptionPane.showMessageDialog(this, "No ingresar mas de 30 caracteres","AREAS",JOptionPane.WARNING_MESSAGE);
            }else if(teclaEnter == KeyEvent.VK_ENTER){
                if(jTextNombreArea.getText().length() >0){
                    buscarDatosNombre();
                }else if(jTextCodigo.getText().length() >0){
                    buscarDatosCodigo();
                }else{
                    JOptionPane.showMessageDialog(this, "Ingrese Valores para Buscar","AREAS",JOptionPane.WARNING_MESSAGE);
                }   
            }
    }//GEN-LAST:event_jTextNombreAreaKeyTyped
    private void jTextCantidadPlantasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCantidadPlantasKeyTyped
        int numeroCaracteres = 5;
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();
        
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();   
            JOptionPane.showMessageDialog(this, "Ingrear Solo Numero","AREAS",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jTextCantidadPlantasKeyTyped
    private void jTextAltitudKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextAltitudKeyTyped
        char validar = evt.getKeyChar();
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();   
            JOptionPane.showMessageDialog(this, "Ingrear Solo Numero","AREAS",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jTextAltitudKeyTyped
    private void jButtonExportar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportar2ActionPerformed
        if(this.jTable1.getRowCount()==0){
            JOptionPane.showMessageDialog(this, "No hay datos en la tabla para exportar.","AREAS", JOptionPane.INFORMATION_MESSAGE);
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
            nom.add("Areas");
            String file=dialog.getDirectory()+dialog.getFile().concat(".xls");
            try{
                ExportarAExcel e = new ExportarAExcel(new File(file),tb , nom);
                if(e.export()){
                     Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Excel.png")).getImage();
                            ImageIcon im = new ImageIcon(imagen);
                            Icon expo = new ImageIcon(im.getImage());
                            JOptionPane.showMessageDialog(this, "Los Datos Fueron Exportados a Excel", "AREAS", JOptionPane.OK_OPTION, expo);
                }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Ocurrio un error " + ex.getMessage(),"AREAS",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButtonExportar2ActionPerformed
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int filaSeleccionada = jTable1.rowAtPoint(evt.getPoint());
        String codigo = String.valueOf(jTable1.getValueAt(filaSeleccionada, 0));
        jTextCodigo.setText(codigo);
        jTextNombreArea.setText(String.valueOf(jTable1.getValueAt(filaSeleccionada, 1)));
        jTextCantidadPlantas.setText(String.valueOf(jTable1.getValueAt(filaSeleccionada,2)));
        jTextAltitud.setText(String.valueOf(jTable1.getValueAt(filaSeleccionada, 3)));
        jComboBoxMedidaDerivada.select(String.valueOf(jTable1.getValueAt(filaSeleccionada, 4)));
        jButtonOk.setEnabled(false);
        jButtonActualizar.setEnabled(true);
        jButtonEliminar.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked
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
            java.util.logging.Logger.getLogger(AREA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AREA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AREA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AREA.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AREA().setVisible(true);
            }
        });
    }           

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton jBottonMostrarTabla;
    private javax.swing.JToggleButton jBottonTituloEtiqueta;
    private javax.swing.JButton jButtonActualizar;
    private javax.swing.JButton jButtonBuscar;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonExportar2;
    private javax.swing.JButton jButtonOk;
    private java.awt.Choice jComboBoxMedidaDerivada;
    private javax.swing.JLabel jLabelAltitud;
    private javax.swing.JLabel jLabelCantidadPlantas;
    private javax.swing.JLabel jLabelCodigo;
    private javax.swing.JLabel jLabelNombreArea;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private rojerusan.RSTableMetro jTable1;
    private javax.swing.JTextField jTextAltitud;
    private javax.swing.JTextField jTextCantidadPlantas;
    private javax.swing.JTextField jTextCodigo;
    private javax.swing.JTextField jTextNombreArea;
    // End of variables declaration//GEN-END:variables
}