package fincabellavista.venasSecundarias;
import BDD.SQLActividad;
import clases.ConexionBD;
import SQLclasesSecundarias.SQLDetalleEmpleado;
import SQLclasesSecundarias.SQLDetalleEmpleadoCorte;
import SQLclasesSecundarias.SQLRegistroActividad;
import clases.Objetos.ObjDetallesActividad;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RegistroActividad extends javax.swing.JDialog {
    private ConexionBD conectar = new ConexionBD();
    private Connection cn = conectar.getConnection(); 
    private static Statement st;
    private static ResultSet rs;
    private SQLDetalleEmpleado SQLdetalleEmpleado = new SQLDetalleEmpleado();
    private java.awt.Frame pa;    
    private SQLRegistroActividad registroActividad = new SQLRegistroActividad(pa);
    private SQLDetalleEmpleadoCorte SQLdetalleCorte = new SQLDetalleEmpleadoCorte(pa);
    private SQLActividad SQLActividad = new SQLActividad(pa);

    public RegistroActividad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        pa = parent;
        initComponents();
        limpiarListaActividades();
        limpiarListaAreas();
        limpiarTxt();
        listaActividades();
        listaAreas();
        listaProducto();
        this.setLocationRelativeTo(null);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jButtonEliminar.setEnabled(false);
        Date date = new Date();
        jCalendario.setDate(date);
        this.fecha=jCalendario.getDate();
        try{
            this.area = jComboBoxArea.getSelectedItem().toString();
            this.nombreActividad = jComboBoxActividad.getSelectedItem().toString();
            this.producto = jComboBoxProducto.getSelectedItem().toString();
        }catch(Exception ex){
            
        }
        if(this.codigoDetalleActividad==0){
            jTextCodigo.setText(Integer.toString(registroActividad.getRegistroMaximo()+1));
            this.codigoDetalleActividad = registroActividad.getRegistroMaximo()+1;
        }
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/Actividad seleccionado.png")).getImage());
    }

    private int detalleEmpleados=0;
    private int detalleMaquinas=0;
    private int detalleQuimicos=0;
    private int codigoDetalleActividad = 0;
    private String producto = null;
    private Date fecha;
    private String area = null;
    private String nombreActividad = null;
    public void cargarDetalles(int detalleEmpleados, 
                               int codigoDetalleActividad,String producto, Date fecha, String area, String nombreActividad) throws SQLException{
        this.detalleEmpleados = detalleEmpleados;
        this.codigoDetalleActividad = codigoDetalleActividad;
        this.fecha = fecha;
        this.area = area;
        this.producto = producto;
        this.nombreActividad = nombreActividad;
        jCalendario.setDate(fecha);
        if(codigoDetalleActividad>0){
//                registroActividad.getRegistroMaximo()+1){
          jTextCodigo.setText(Integer.toString(codigoDetalleActividad));
            if(registroActividad.existeRegistro(codigoDetalleActividad)==true){
                DefaultTableModel modelo = new DefaultTableModel();
                this.jTable1.setModel(registroActividad.buscarDatos(modelo, codigoDetalleActividad));
                if(jTable1.getRowCount()>0){
                    jComboBoxArea.setSelectedItem(String.valueOf(jTable1.getValueAt(0, 2)));
                    jComboBoxActividad.setSelectedItem(String.valueOf(jTable1.getValueAt(0, 3)));
                    jComboBoxProducto.setSelectedItem(String.valueOf(jTable1.getValueAt(0, 4)));
                }
            }
        }
        else{
            jTextCodigo.setText(Integer.toString(registroActividad.getRegistroMaximo()+1));
            this.codigoDetalleActividad = registroActividad.getRegistroMaximo()+1;
        }
        if(codigoDetalleActividad>0){
            jComboBoxActividad.setSelectedItem(this.nombreActividad);
            jComboBoxArea.setSelectedItem(this.area);
            jTextCodigo.setText(Integer.toString(codigoDetalleActividad));
        }
        if(detalleEmpleados>0){
            jComboBoxActividad.setSelectedItem(this.nombreActividad);
            jComboBoxArea.setSelectedItem(this.area);
            jTextDetalleEmpleados.setText(Integer.toString(this.detalleEmpleados));
        }
        if(detalleMaquinas>0){
            jComboBoxActividad.setSelectedItem(this.nombreActividad);
            jComboBoxArea.setSelectedItem(this.area);
//            jTextDetalleMaquinas.setText(Integer.toString(this.detalleMaquinas));
        }
        if(detalleQuimicos>0){
//            jTextDetalleQuimicos.setText(Integer.toString(this.detalleQuimicos));
            jComboBoxArea.setSelectedItem(this.area);
            jComboBoxActividad.setSelectedItem(this.nombreActividad);
        }
    }   
    
    private void mostrarDatos(){
        if(jTextCodigo.getText().length()>0){
                if(jTextDetalleEmpleados.getText().length()>0){
                    if(registroActividad.existeRegistro(Integer.parseInt(jTextCodigo.getText()))==true){
                        if(SQLdetalleCorte.existeDetalle(Integer.parseInt(jTextDetalleEmpleados.getText()))==true || SQLdetalleEmpleado.existeDetalle(Integer.parseInt(jTextDetalleEmpleados.getText()))==true){
                            try {
                                ObjDetallesActividad detalles = registroActividad.datosDetalleActividad(Integer.parseInt(jTextCodigo.getText()));
                                if(detalles.getDetalleEmpleados()!=0){
//                                    this.dispose();
                                    limpiarVentana();
                                }else{
                                    guardar();
//                                    this.dispose();
                                    limpiarVentana();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(RegistroActividad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if(jTextDetalleEmpleados.getText().length()==0){
                            int vDetalleEmpleados = 0;
                            int vDetalleMaquina = 0;
                            int vDetalleQuimico = 0;
                            if(jTextDetalleEmpleados.getText().length()>0){
                                vDetalleEmpleados = Integer.parseInt(jTextDetalleEmpleados.getText());
                            }
                                registroActividad.eliminarDetalles(vDetalleEmpleados,vDetalleMaquina,vDetalleQuimico);
                                registroActividad.eliminarDatos(Integer.parseInt(jTextCodigo.getText()));
                        }
                    }else{
                        if(SQLdetalleCorte.existeDetalle(Integer.parseInt(jTextDetalleEmpleados.getText()))==true || SQLdetalleEmpleado.existeDetalle(Integer.parseInt(jTextDetalleEmpleados.getText()))==true){
                            try {
                                ObjDetallesActividad detalles = registroActividad.datosDetalleActividad(Integer.parseInt(jTextCodigo.getText()));
                                if(detalles.getDetalleEmpleados()!=0){
//                                    this.dispose();
                                    limpiarVentana();
                                }else{
                                    guardar();
//                                    this.dispose();
                                    limpiarVentana();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(RegistroActividad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if(jTextDetalleEmpleados.getText().length()==0){
                            int vDetalleEmpleados = 0;
                            int vDetalleMaquina = 0;
                            int vDetalleQuimico = 0;
                            if(jTextDetalleEmpleados.getText().length()>0){
                                vDetalleEmpleados = Integer.parseInt(jTextDetalleEmpleados.getText());
                            }
                                registroActividad.eliminarDetalles(vDetalleEmpleados,vDetalleMaquina,vDetalleQuimico);
                                registroActividad.eliminarDatos(Integer.parseInt(jTextCodigo.getText()));
                        }
//                        this.dispose();
                        limpiarVentana();
                    }
                }else if(registroActividad.existeRegistro(Integer.parseInt(jTextCodigo.getText()))==true){
                if(jTextDetalleEmpleados.getText().length()>0){
                    int detalle = Integer.parseInt(jTextDetalleEmpleados.getText());
                    if(detalle==0){
                                int vDetalleEmpleados = 0;
                                int vDetalleMaquina = 0;
                                int vDetalleQuimico = 0;
                                if(jTextDetalleEmpleados.getText().length()>0){
                                    vDetalleEmpleados = Integer.parseInt(jTextDetalleEmpleados.getText());
                                }
                                    registroActividad.eliminarDetalles(vDetalleEmpleados,vDetalleMaquina,vDetalleQuimico);
                        limpiarVentana();
                        registroActividad.eliminarDatos(Integer.parseInt(jTextCodigo.getText()));
                    }else{
                        limpiarVentana();
                    }
                }else{
                    registroActividad.eliminarDatos(Integer.parseInt(jTextCodigo.getText()));
                    limpiarVentana();
                }
            }else{
                if(jTextDetalleEmpleados.getText().length()>0){
                    if(jTextDetalleEmpleados.getText().length()>0 || !jTextCodigo.getText().equals(jTextDetalleEmpleados.getText())){
                        int opc = JOptionPane.showConfirmDialog(pa, "¿Seguro que deseas continuar?\nPuede que pierdas informacion","Registro de Actividad",JOptionPane.ERROR_MESSAGE);
                        if(opc==0){
                            if(jTextCodigo.getText().length()>0 && detalleEmpleados>0){
                                if(registroActividad.existeRegistro(Integer.parseInt(jTextCodigo.getText()))==false){
                                    registroActividad.eliminarDetalles(detalleEmpleados, detalleMaquinas, detalleQuimicos);
                                }
                            }
                        }else{
                            limpiarVentana();
                        }
                    }
                }
            }
        }
        DefaultTableModel modelo1 = new DefaultTableModel();
        this.jTable1.setModel(registroActividad.mostrarDatos(modelo1));
        jBottonMostrarTabla1.setSelected(false);
        jButtonEliminar.setEnabled(false);
        jButtonOk.setEnabled(true);
        limpiarTxt();
        jTextCodigo.setText(Integer.toString(registroActividad.getRegistroMaximo()+1));
        this.codigoDetalleActividad = registroActividad.getRegistroMaximo()+1;
        jCalendario.setDate(new Date());
    }
    

    public void listaActividades(){
        try {
            st=cn.createStatement();
            String r = "SELECT NOMBRE_ACTIVIDAD FROM "+conectar.user_admin+".ACTIVIDAD ORDER BY 1";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    jComboBoxActividad.addItem(fila[i].toString());
                }
            }
//            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error Al Conectar");
        }
    }   
    public void limpiarListaActividades(){
        jComboBoxActividad.removeAllItems();
    }
    private int codigoNombreActividad(){
        int codigoNombreEmpleado = 0;
        try {
            String r = "SELECT CODIGO_ACTIVIDAD FROM "+conectar.user_admin+".ACTIVIDAD WHERE NOMBRE_ACTIVIDAD= '"+jComboBoxActividad.getSelectedItem().toString()+"'";
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
//            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error");
            System.out.println(e);
        }
        return codigoNombreEmpleado;
    }

    public void listaAreas(){
        try {
            st=cn.createStatement();
            String r = "SELECT NOMBRE_AREA FROM "+conectar.user_admin+".AREA ORDER BY 1";
            rs = st.executeQuery(r);
            ResultSetMetaData rsMD;
            rsMD = rs.getMetaData();
            int cantColumnas = rsMD.getColumnCount();
            while(rs.next()){
                Object[] fila = new Object[cantColumnas];
                for (int i = 0; i < cantColumnas; i++) {
                    fila[i]= rs.getObject(i+1);
                    jComboBoxArea.addItem(fila[i].toString());
                }
            }
//            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error Al Conectar: "+e);
        }
    }   
    public void limpiarListaAreas(){
        jComboBoxArea.removeAllItems();
    }
    private int codigoNombreArea(){
        int codigoNombreEmpleado = 0;
        try {
            String r = "SELECT CODIGO_AREA FROM "+conectar.user_admin+".AREA WHERE NOMBRE_AREA= '"+jComboBoxArea.getSelectedItem().toString()+"' ORDER BY 1";
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
//            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error");
            System.out.println(e);
        }
        return codigoNombreEmpleado;
    }

    
    private void limpiarTxt()
    {
        jTextDetalleEmpleados.setText("");
        jCalendario.setDate(null);            
    }
    public void limpiarVentana(){
        if(jTable1.getRowCount()>0 || jTextDetalleEmpleados.getText().length()>0){            
            jTextDetalleEmpleados.setText("");
            jCalendario.setDate(new Date());
            this.area = jComboBoxArea.getSelectedItem().toString();
            
            this.producto = jComboBoxProducto.getSelectedItem().toString();
            this.nombreActividad = jComboBoxActividad.getSelectedItem().toString();
            DefaultTableModel model = new DefaultTableModel();
            jTable1.setModel(model);
        }
        jCalendario.setDate(new Date());
        jTextDetalleEmpleados.setText("");
    }

    private void eliminarDatos(){
        int vDetalleEmpleados =0;
        int vDetalleMaquinas = 0;
        int vDetalleQuimicos = 0;
        if(jTextDetalleEmpleados.getText().length()>0){
            vDetalleEmpleados = Integer.parseInt(jTextDetalleEmpleados.getText());
            int codigoRegistroActividad = Integer.parseInt(jTextCodigo.getText());
            registroActividad.eliminarDatos(codigoRegistroActividad,new ObjDetallesActividad(vDetalleEmpleados, vDetalleMaquinas, vDetalleQuimicos));
            limpiarTxt();
            jCalendario.setDate(new Date());
        }
    }
        
    


private void guardar(){
        try{
            int codigoRegistroActividad = Integer.parseInt(jTextCodigo.getText());
            int detalleEmpleado = 0;
            if(jTextDetalleEmpleados.getText().length()>0)
                detalleEmpleado = Integer.parseInt(jTextDetalleEmpleados.getText());
            int detalleMaquina=0;
            int detalleQuimico = 0;
            int actividad = codigoNombreActividad();
            int area = codigoNombreArea();
            
            int producto = codigoProducto();
            DateFormat variableFecha = DateFormat.getDateInstance();
            String fecha = variableFecha.format(jCalendario.getDate());

            Calendar horaSistema = Calendar.getInstance();        
            String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
            String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
            String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
            String horaRegistro = hora+":"+minutos+":"+segundos;
            if(SQLdetalleEmpleado.existeDetalle(detalleEmpleado) ||  SQLdetalleCorte.existeDetalle(detalleEmpleado)){
                
                            int selec = JOptionPane.showConfirmDialog(this, "¿Desea Guardar?","REGISTRO ACTIVIDAD",JOptionPane.OK_OPTION,JOptionPane.CANCEL_OPTION);
                            if(selec == 0){
                                registroActividad.actualizarDatosDetalle(area, actividad, producto, codigoRegistroActividad, detalleEmpleados, detalleMaquina, detalleQuimico, fecha, horaRegistro);
                                mostrarDatos();
                                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                                limpiarTxt();
                                jCalendario.setDate(new Date());
                            }
                            else{
                                eliminarDatos();
                            }
                        }
                    
        }catch(HeadlessException | NumberFormatException e){
            JOptionPane.showMessageDialog(this, "Ingresa una fecha","REGISTRO ACTIVIDAD",JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarPorCodigo(){
        codigoDetalleActividad = Integer.parseInt(jTextCodigo.getText());
        if(registroActividad.existeRegistro(codigoDetalleActividad)==true){
            DefaultTableModel modelo = new DefaultTableModel();
            try {
                jButtonEliminar.setEnabled(true);
                jButtonOk.setEnabled(false);
                this.jTable1.setModel(registroActividad.buscarDatos(modelo, codigoDetalleActividad));
                if(jTable1.getRowCount()>0){
                    jTextCodigo.setText(String.valueOf(jTable1.getValueAt(0, 0)));
                    String fecha = String.valueOf(jTable1.getValueAt(0, 1));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date fechaSelec = null;
                    fechaSelec = sdf.parse(fecha);
                    jCalendario.setDate(fechaSelec);
                    ObjDetallesActividad detalles = registroActividad.datosDetalleActividad(Integer.parseInt(jTextCodigo.getText()));
                    jTextDetalleEmpleados.setText(Integer.toString(detalles.getDetalleEmpleados()));   
                    jComboBoxArea.setSelectedItem(String.valueOf(jTable1.getValueAt(0, 2)));
                    jComboBoxActividad.setSelectedItem(String.valueOf(jTable1.getValueAt(0, 3)));
                    jComboBoxProducto.setSelectedItem(String.valueOf(jTable1.getValueAt(0, 4)));
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistroActividad.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(RegistroActividad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(this, "No existe este codigo","REGISTRO ACTIVIDAD",JOptionPane.WARNING_MESSAGE);
            jTextCodigo.setText("");
        }
    }
    private void buscarPorArea(){
        int codigoArea = codigoNombreArea();
        DefaultTableModel modelo = new DefaultTableModel();
        try {
            this.jTable1.setModel(registroActividad.buscarDatosArea(modelo, codigoArea));
            if(jTable1.getRowCount()==0)
                JOptionPane.showMessageDialog(null, "No hay Actividades para esa area");
        } catch (SQLException ex) {
            Logger.getLogger(RegistroActividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void buscarPorActividad(){
        int codigoActividad = codigoNombreActividad();
        DefaultTableModel modelo = new DefaultTableModel();
        try {
            this.jTable1.setModel(registroActividad.buscarDatosActividad(modelo, codigoActividad));
            if(jTable1.getRowCount()==0)
                JOptionPane.showMessageDialog(pa, "No hay Actividades");
        } catch (SQLException ex) {
            Logger.getLogger(RegistroActividad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void buscarPorFecha(){
        try{
            DateFormat variableFecha = DateFormat.getDateInstance();
            String fecha = variableFecha.format(jCalendario.getDate());
            DefaultTableModel modelo = new DefaultTableModel();
            this.jTable1.setModel(registroActividad.buscarDatosFecha(modelo, fecha));
            if(jTable1.getRowCount()==0)
                JOptionPane.showMessageDialog(null, "No hay Actividades en este dia: "+fecha);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Ingresa Una Fecha");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Titulo = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jTextCodigo = new javax.swing.JTextField();
        jButtonOk = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jLabelCodigo = new javax.swing.JLabel();
        jCalendario = new com.toedter.calendar.JDateChooser();
        jLabelFechaInicio = new javax.swing.JLabel();
        jLabelArea = new javax.swing.JLabel();
        jBottonMostrarTabla1 = new javax.swing.JToggleButton();
        jLabelActividad = new javax.swing.JLabel();
        jButtonDetalleEmpleado = new javax.swing.JButton();
        jTextDetalleEmpleados = new javax.swing.JTextField();
        jLabelArea1 = new javax.swing.JLabel();
        jComboBoxActividad = new javax.swing.JComboBox<>();
        jComboBoxArea = new javax.swing.JComboBox<>();
        jButtonBuscar1 = new javax.swing.JButton();
        jLabelActividad1 = new javax.swing.JLabel();
        jComboBoxProducto = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new rojerusan.RSTableMetro();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("REGISTRO DE ACTIVIDADES");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(92, 125, 137));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        Titulo.setBackground(new java.awt.Color(51, 204, 255));
        Titulo.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        Titulo.setForeground(new java.awt.Color(102, 102, 102));
        Titulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actividad seleccionado.png"))); // NOI18N
        Titulo.setText("REGISTRO DE ACTIVIDAD");
        Titulo.setBorder(null);
        Titulo.setBorderPainted(false);
        Titulo.setContentAreaFilled(false);
        Titulo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jPanel3.setBackground(new java.awt.Color(124, 147, 100));

        jTextCodigo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextCodigo.setToolTipText("Codigo  Registro Actividad");
        jTextCodigo.setName(""); // NOI18N
        jTextCodigo.setNextFocusableComponent(jComboBoxArea);
        jTextCodigo.setSelectionColor(new java.awt.Color(0, 0, 0));
        jTextCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextCodigoMouseClicked(evt);
            }
        });
        jTextCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextCodigoKeyTyped(evt);
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
        jLabelCodigo.setText("Codigo Registro");

        jCalendario.setToolTipText("Fecha");
        jCalendario.setNextFocusableComponent(jComboBoxArea);
        jCalendario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jCalendarioMouseClicked(evt);
            }
        });

        jLabelFechaInicio.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelFechaInicio.setForeground(new java.awt.Color(255, 255, 255));
        jLabelFechaInicio.setText("FECHA :");

        jLabelArea.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelArea.setForeground(new java.awt.Color(255, 255, 255));
        jLabelArea.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelArea.setText("AREA :");

        jBottonMostrarTabla1.setBackground(new java.awt.Color(51, 204, 255));
        jBottonMostrarTabla1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jBottonMostrarTabla1.setForeground(new java.awt.Color(255, 255, 255));
        jBottonMostrarTabla1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo.png"))); // NOI18N
        jBottonMostrarTabla1.setToolTipText("Mostrar Datos");
        jBottonMostrarTabla1.setBorder(null);
        jBottonMostrarTabla1.setBorderPainted(false);
        jBottonMostrarTabla1.setContentAreaFilled(false);
        jBottonMostrarTabla1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBottonMostrarTabla1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo.png"))); // NOI18N
        jBottonMostrarTabla1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Mostrar Datos Completo Seleccionado.png"))); // NOI18N
        jBottonMostrarTabla1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBottonMostrarTabla1ActionPerformed(evt);
            }
        });

        jLabelActividad.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelActividad.setForeground(new java.awt.Color(255, 255, 255));
        jLabelActividad.setText("ACTIVIDAD :");

        jButtonDetalleEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Detalle personal.png"))); // NOI18N
        jButtonDetalleEmpleado.setToolTipText("Agregar Detalle Empleado");
        jButtonDetalleEmpleado.setBorderPainted(false);
        jButtonDetalleEmpleado.setContentAreaFilled(false);
        jButtonDetalleEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonDetalleEmpleado.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Detalle personal.png"))); // NOI18N
        jButtonDetalleEmpleado.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Detalle personal seleccionado.png"))); // NOI18N
        jButtonDetalleEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButtonDetalleEmpleadoMouseClicked(evt);
            }
        });
        jButtonDetalleEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDetalleEmpleadoActionPerformed(evt);
            }
        });

        jTextDetalleEmpleados.setEditable(false);
        jTextDetalleEmpleados.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        jTextDetalleEmpleados.setToolTipText("Codigo Detalle Empleados");
        jTextDetalleEmpleados.setName(""); // NOI18N
        jTextDetalleEmpleados.setSelectionColor(new java.awt.Color(0, 0, 0));
        jTextDetalleEmpleados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextDetalleEmpleadosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextDetalleEmpleadosMouseEntered(evt);
            }
        });
        jTextDetalleEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextDetalleEmpleadosActionPerformed(evt);
            }
        });
        jTextDetalleEmpleados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextDetalleEmpleadosKeyTyped(evt);
            }
        });

        jLabelArea1.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabelArea1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelArea1.setText("Detalle Empleados");

        jComboBoxActividad.setToolTipText("Actividad");
        jComboBoxActividad.setNextFocusableComponent(jComboBoxProducto);
        jComboBoxActividad.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxActividadItemStateChanged(evt);
            }
        });

        jComboBoxArea.setToolTipText("Area");
        jComboBoxArea.setNextFocusableComponent(jComboBoxActividad);
        jComboBoxArea.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxAreaItemStateChanged(evt);
            }
        });

        jButtonBuscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo.png"))); // NOI18N
        jButtonBuscar1.setToolTipText("Buscar Datos");
        jButtonBuscar1.setBorder(null);
        jButtonBuscar1.setBorderPainted(false);
        jButtonBuscar1.setContentAreaFilled(false);
        jButtonBuscar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonBuscar1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo.png"))); // NOI18N
        jButtonBuscar1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Buscar Completo Seleccionado.png"))); // NOI18N
        jButtonBuscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscar1ActionPerformed(evt);
            }
        });

        jLabelActividad1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelActividad1.setForeground(new java.awt.Color(255, 255, 255));
        jLabelActividad1.setText("ASIGNAR A :");

        jComboBoxProducto.setToolTipText("Actividad");
        jComboBoxProducto.setNextFocusableComponent(jTextDetalleEmpleados);
        jComboBoxProducto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxProductoItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabelActividad1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelActividad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelArea, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabelFechaInicio))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBoxArea, 0, 156, Short.MAX_VALUE)
                            .addComponent(jComboBoxActividad, 0, 156, Short.MAX_VALUE)
                            .addComponent(jComboBoxProducto, 0, 156, Short.MAX_VALUE)
                            .addComponent(jCalendario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelCodigo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(77, 77, 77)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelArea1)
                            .addComponent(jTextDetalleEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(118, 118, 118)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButtonEliminar))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jBottonMostrarTabla1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonBuscar1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jButtonDetalleEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextCodigo)
                            .addComponent(jLabelCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelArea, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxArea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabelActividad1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jTextDetalleEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonDetalleEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabelArea1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jBottonMostrarTabla1)
                                .addComponent(jButtonBuscar1)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonOk, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonEliminar))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(186, 186, 186)
                .addComponent(Titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(127, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jTextCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextCodigoKeyTyped
      
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();

        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(rootPane, "Ingresar Solo Numero");
        }
        else{
            if(teclaEnter==KeyEvent.VK_ENTER){
                if(jTextCodigo.getText().length()>0){
                    buscarPorCodigo();
                }
                else{
                    String [] botones1 = {"CODIGO","AREA", "ACTIVIDAD","FECHA", "CANCELAR"};
                    Object [] botones = {"CODIGO","AREA", "ACTIVIDAD","FECHA", "CANCELAR"};
                    int vSelec = JOptionPane.showOptionDialog (null, "¿Por que campo quieres buscar?", "Buscar", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null/*icono*/, botones1, botones[0]);
                    if(vSelec==0){
                        buscarPorCodigo();
                    }else if(vSelec==1){
                        buscarPorArea();
                    }else if(vSelec==2){
                        buscarPorActividad();
                    }else if(vSelec==3){
                        buscarPorFecha();
                    }
                }
            }
        }
    }//GEN-LAST:event_jTextCodigoKeyTyped
    private void jButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOkActionPerformed
////        if(jTextCodigo.getText().length()==0){
//            JOptionPane.showMessageDialog(null, "Ingrese Codigo Para Guardar");
//        }else if(jTextDetalleEmpleados.getText().length()==0){
//                JOptionPane.showMessageDialog(null, "Ingrese Empleados que trabajan en la actividad");
//        }else{
            guardar();
//        }
    }//GEN-LAST:event_jButtonOkActionPerformed
    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Esta Seguro Que Desea Eliminarlo?");
        if(respuesta == 0){
            eliminarDatos();
            mostrarDatos();
        }
    }//GEN-LAST:event_jButtonEliminarActionPerformed
    private void jBottonMostrarTabla1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBottonMostrarTabla1ActionPerformed
        mostrarDatos();
        jCalendario.setDate(new Date());
    }//GEN-LAST:event_jBottonMostrarTabla1ActionPerformed
    private void jButtonDetalleEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDetalleEmpleadoActionPerformed
        int vSelect = JOptionPane.showConfirmDialog(this, "¿Desea agregar personal a la actividad?");
        if(vSelect == 0){
            try{
                if(jTextCodigo.getText().length() > 0 ){
                    if(registroActividad.existeRegistro(Integer.parseInt(jTextCodigo.getText()))==true && jTextDetalleEmpleados.getText().length()==0){
                        codigoDetalleActividad = (registroActividad.getRegistroMaximo()+1);
                        int codigoRegistroActividad = (registroActividad.getRegistroMaximo()+1);
                        int actividad = codigoNombreActividad();
                        int area = codigoNombreArea();
                        DateFormat variableFecha = DateFormat.getDateInstance();
                        String fecha = variableFecha.format(jCalendario.getDate());
                        Calendar horaSistema = Calendar.getInstance();        
                        String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
                        String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
                        String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
                        String horaRegistro = hora+":"+minutos+":"+segundos;
                        int producto = codigoProducto();
                        registroActividad.guardarDatos(codigoRegistroActividad, 0, 0, 0, area, actividad,producto, fecha, horaRegistro);
                    }else{
                        if(registroActividad.existeRegistro(Integer.parseInt(jTextCodigo.getText()))==true && jTextCodigo.getText().equals(jTextDetalleEmpleados.getText())){
                            codigoDetalleActividad = Integer.parseInt(jTextCodigo.getText());        
                        }else{
                            codigoDetalleActividad = Integer.parseInt(jTextCodigo.getText());
                            int codigoRegistroActividad = Integer.parseInt(jTextCodigo.getText());
                            int actividad = codigoNombreActividad();
                            int area = codigoNombreArea();
                            DateFormat variableFecha = DateFormat.getDateInstance();
                            String fecha = variableFecha.format(jCalendario.getDate());
                            Calendar horaSistema = Calendar.getInstance();        
                        int producto = codigoProducto();
                        String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
                            String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
                            String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
                            String horaRegistro = hora+":"+minutos+":"+segundos;
                            registroActividad.guardarDatos(codigoRegistroActividad, 0, 0, 0, area, actividad,producto, fecha, horaRegistro);
                        }
                    }
                }
                if(jTextDetalleEmpleados.getText().length() > 0 ){
                    detalleEmpleados = Integer.parseInt(jTextDetalleEmpleados.getText());
                }else{
                    detalleEmpleados = 0;
                }
                this.fecha = jCalendario.getDate();
                this.dispose();
                if(codigoNombreActividad()==117 ){
                    DetalleEmpleadoCorte detalleEmp = new DetalleEmpleadoCorte(pa, true);
                    detalleEmp.cargarDetalles(detalleEmpleados, detalleMaquinas, detalleQuimicos,codigoDetalleActividad, fecha, area, nombreActividad);
                    detalleEmp.setVisible(true);
                }else{
                    if(codigoNombreActividad()==128){
                        DetalleEmpleadoPermanente de = new DetalleEmpleadoPermanente(pa, true);
                        de.cargarDetalles(detalleEmpleados, detalleMaquinas, detalleQuimicos, codigoDetalleActividad, fecha, area, nombreActividad);
                        de.setVisible(true);
                    }else{
                    DetalleEmpleado detalleEmp = new DetalleEmpleado(pa, true);
                    detalleEmp.cargarDetalles(detalleEmpleados,codigoDetalleActividad,producto,fecha,area,nombreActividad);
                    detalleEmp.setVisible(true);
                    }
                }
            }catch(NumberFormatException | SQLException e){
                JOptionPane.showMessageDialog(this, "Valor invalido");
            }
        }
    }//GEN-LAST:event_jButtonDetalleEmpleadoActionPerformed
    private void jTextDetalleEmpleadosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextDetalleEmpleadosKeyTyped
        char validar = evt.getKeyChar();
        char teclaEnter = evt.getKeyChar();

        if(teclaEnter == com.sun.glass.events.KeyEvent.VK_ENTER){ 
            int vSelect = JOptionPane.showConfirmDialog(this, "¿Desea agregar personal a la actividad?");
            if(vSelect == 0){
                try{
                    if(jTextCodigo.getText().length() > 0 ){
                        if(registroActividad.existeRegistro(Integer.parseInt(jTextCodigo.getText()))==true && jTextDetalleEmpleados.getText().length()==0){
                            codigoDetalleActividad = (registroActividad.getRegistroMaximo()+1);
                            int codigoRegistroActividad = (registroActividad.getRegistroMaximo()+1);
                            int actividad = codigoNombreActividad();
                            int area = codigoNombreArea();
                            int producto = codigoProducto();
                            DateFormat variableFecha = DateFormat.getDateInstance();
                            String fecha = variableFecha.format(jCalendario.getDate());
                            Calendar horaSistema = Calendar.getInstance();        
                            String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
                            String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
                            String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
                            String horaRegistro = hora+":"+minutos+":"+segundos;
                            registroActividad.guardarDatos(codigoRegistroActividad, 0, 0, 0, area, actividad,producto, fecha, horaRegistro);
                        }else{
                            if(registroActividad.existeRegistro(Integer.parseInt(jTextCodigo.getText()))==true && jTextCodigo.getText().equals(jTextDetalleEmpleados.getText())){
                                codigoDetalleActividad = Integer.parseInt(jTextCodigo.getText());        
                            }else{
                                codigoDetalleActividad = Integer.parseInt(jTextCodigo.getText());
                                int codigoRegistroActividad = Integer.parseInt(jTextCodigo.getText());
                                int actividad = codigoNombreActividad();
                                int area = codigoNombreArea();
                                int producto = codigoProducto();
                                DateFormat variableFecha = DateFormat.getDateInstance();
                                String fecha = variableFecha.format(jCalendario.getDate());
                                Calendar horaSistema = Calendar.getInstance();        
                                String hora = Integer.toString(horaSistema.get(horaSistema.HOUR_OF_DAY));
                                String minutos = Integer.toString(horaSistema.get(horaSistema.MINUTE));
                                String segundos = Integer.toString(horaSistema.get(horaSistema.SECOND));
                                String horaRegistro = hora+":"+minutos+":"+segundos;
                                registroActividad.guardarDatos(codigoRegistroActividad, 0, 0, 0, area, actividad,producto, fecha, horaRegistro);
                            }
                        }
                    }
                    if(jTextDetalleEmpleados.getText().length() > 0 ){
                        detalleEmpleados = Integer.parseInt(jTextDetalleEmpleados.getText());
                    }else{
                        detalleEmpleados = 0;
                    }
                    this.fecha = jCalendario.getDate();
                    this.dispose();
                    if(codigoNombreActividad()==117){
                        DetalleEmpleadoCorte detalleEmp = new DetalleEmpleadoCorte(pa, true);
                        detalleEmp.cargarDetalles(detalleEmpleados, detalleMaquinas, detalleQuimicos,codigoDetalleActividad, fecha, area, nombreActividad);
                        detalleEmp.setVisible(true);
                    }else{
                        if(codigoNombreActividad()==156){
                            DetalleEmpleadoPermanente de = new DetalleEmpleadoPermanente(pa, true);
                            de.cargarDetalles(detalleEmpleados, detalleMaquinas, detalleQuimicos, codigoDetalleActividad, fecha, area, nombreActividad);
                            de.setVisible(true);
                        }
                        DetalleEmpleado detalleEmp = new DetalleEmpleado(pa, true);
                        detalleEmp.cargarDetalles(detalleEmpleados,codigoDetalleActividad,producto,fecha,area,nombreActividad);
                        detalleEmp.setVisible(true);
                    }
                }catch(NumberFormatException | SQLException e){
                    JOptionPane.showMessageDialog(this, "Valor invalido");
                }
            }
        }
    }//GEN-LAST:event_jTextDetalleEmpleadosKeyTyped
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        try {
            int filaSeleccionada = jTable1.rowAtPoint(evt.getPoint());
            jTextCodigo.setText(String.valueOf(jTable1.getValueAt(filaSeleccionada, 0)));
            String fecha = String.valueOf(jTable1.getValueAt(filaSeleccionada, 1));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaSelec = null;
            fechaSelec = sdf.parse(fecha);
            jCalendario.setDate(fechaSelec);
            ObjDetallesActividad detalles = registroActividad.datosDetalleActividad(Integer.parseInt(jTextCodigo.getText()));
            jTextDetalleEmpleados.setText(Integer.toString(detalles.getDetalleEmpleados()));   
            jComboBoxArea.setSelectedItem(String.valueOf(jTable1.getValueAt(filaSeleccionada, 2)));
            jComboBoxActividad.setSelectedItem(String.valueOf(jTable1.getValueAt(filaSeleccionada, 3)));
            jComboBoxProducto.setSelectedItem(String.valueOf(jTable1.getValueAt(filaSeleccionada, 4)));
        } catch (ParseException ex) {
            System.out.println("No convierte");
        } catch (SQLException ex) {
            Logger.getLogger(RegistroActividad.class.getName()).log(Level.SEVERE, null, ex);
        }
        jButtonOk.setEnabled(false);
        jButtonEliminar.setEnabled(true);
    }//GEN-LAST:event_jTable1MouseClicked
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int vSelect = JOptionPane.showConfirmDialog(this, "¿Desea salir del registro de actividad?","REGISTRO ACTIVIDADES",JOptionPane.OK_OPTION,JOptionPane.CANCEL_OPTION);
        if(vSelect == 0){
            if(jTextCodigo.getText().length()>0){
                if(jTextDetalleEmpleados.getText().length()>0){
                    if(registroActividad.existeRegistro(Integer.parseInt(jTextCodigo.getText()))==true){
                        if(SQLdetalleCorte.existeDetalle(Integer.parseInt(jTextDetalleEmpleados.getText()))==true || SQLdetalleEmpleado.existeDetalle(Integer.parseInt(jTextDetalleEmpleados.getText()))==true){
                            try {
                                ObjDetallesActividad detalles = registroActividad.datosDetalleActividad(Integer.parseInt(jTextCodigo.getText()));
                                if(detalles.getDetalleEmpleados()!=0){
                                    this.dispose();
                                    limpiarVentana();
                                }else{
                                    guardar();
                                    this.dispose();
                                    limpiarVentana();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(RegistroActividad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if(jTextDetalleEmpleados.getText().length()==0 ){
                            int vDetalleEmpleados = 0;
                            int vDetalleMaquina = 0;
                            int vDetalleQuimico = 0;
                            if(jTextDetalleEmpleados.getText().length()>0){
                                vDetalleEmpleados = Integer.parseInt(jTextDetalleEmpleados.getText());
                            }
                                registroActividad.eliminarDetalles(vDetalleEmpleados,vDetalleMaquina,vDetalleQuimico);
                                registroActividad.eliminarDatos(Integer.parseInt(jTextCodigo.getText()));
                        }
                    }else{
                        if(SQLdetalleCorte.existeDetalle(Integer.parseInt(jTextDetalleEmpleados.getText()))==true || SQLdetalleEmpleado.existeDetalle(Integer.parseInt(jTextDetalleEmpleados.getText()))==true){
                            try {
                                ObjDetallesActividad detalles = registroActividad.datosDetalleActividad(Integer.parseInt(jTextCodigo.getText()));
                                if(detalles.getDetalleEmpleados()!=0){
                                    this.dispose();
                                    limpiarVentana();
                                }else{
                                    guardar();
                                    this.dispose();
                                    limpiarVentana();
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(RegistroActividad.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if(jTextDetalleEmpleados.getText().length()==0){
                            int vDetalleEmpleados = 0;
                            int vDetalleMaquina = 0;
                            int vDetalleQuimico = 0;
                            if(jTextDetalleEmpleados.getText().length()>0){
                                vDetalleEmpleados = Integer.parseInt(jTextDetalleEmpleados.getText());
                            }
                                registroActividad.eliminarDetalles(vDetalleEmpleados,vDetalleMaquina,vDetalleQuimico);
                                registroActividad.eliminarDatos(Integer.parseInt(jTextCodigo.getText()));
                        }
                        this.dispose();
                        limpiarVentana();
                    }
                }else{
                    registroActividad.eliminarDatos(Integer.parseInt(jTextCodigo.getText()));
                    this.dispose();
                    limpiarVentana();
                }
            }else{
                this.dispose();
                limpiarVentana();
            }
        }
    }//GEN-LAST:event_formWindowClosing
    private void jTextCodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextCodigoMouseClicked
        jButtonEliminar.setEnabled(false);
        jButtonOk.setEnabled(true);
    }//GEN-LAST:event_jTextCodigoMouseClicked
    private void jCalendarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCalendarioMouseClicked
        jButtonEliminar.setEnabled(false);
        jButtonOk.setEnabled(true);
    }//GEN-LAST:event_jCalendarioMouseClicked
    private void jTextDetalleEmpleadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextDetalleEmpleadosMouseClicked
        jButtonEliminar.setEnabled(false);
        jButtonOk.setEnabled(true);
    }//GEN-LAST:event_jTextDetalleEmpleadosMouseClicked
    private void jButtonDetalleEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonDetalleEmpleadoMouseClicked
        jButtonEliminar.setEnabled(false);
        jButtonOk.setEnabled(true);
    }//GEN-LAST:event_jButtonDetalleEmpleadoMouseClicked
    private void jButtonBuscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBuscar1ActionPerformed
        if(jTextCodigo.getText().length()>0){
            if(registroActividad.existeRegistro(Integer.parseInt(jTextCodigo.getText()))==false){
                registroActividad.eliminarDetalles(detalleEmpleados, detalleMaquinas, detalleQuimicos);
            }
        }
        if(jTextCodigo.getText().length()>0){
            buscarPorCodigo();
        }else{
            String [] botones1 = {"CODIGO","AREA", "ACTIVIDAD","FECHA", "CANCELAR"};
            Object [] botones = {"CODIGO","AREA", "ACTIVIDAD","FECHA", "CANCELAR"};
            int vSelec = JOptionPane.showOptionDialog (null, "¿Por que campo quieres buscar?", "Buscar", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null/*icono*/, botones1, botones[0]);
            if(vSelec==0){
                buscarPorCodigo();
            }else if(vSelec==1){
                buscarPorArea();
            }else if(vSelec==2){
                buscarPorActividad();
            }else if(vSelec==3){
                buscarPorFecha();
            }
        }
    }//GEN-LAST:event_jButtonBuscar1ActionPerformed
    private void jComboBoxAreaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxAreaItemStateChanged
        try{
            this.area = jComboBoxArea.getSelectedItem().toString();
            //this.nombreActividad = jComboBoxActividad.getSelectedItem().toString();
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_jComboBoxAreaItemStateChanged
    private void jComboBoxActividadItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxActividadItemStateChanged
        try{
            //this.area = jComboBoxArea.getSelectedItem().toString();
            this.nombreActividad = jComboBoxActividad.getSelectedItem().toString();
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_jComboBoxActividadItemStateChanged
    private void jComboBoxProductoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxProductoItemStateChanged
        // TODO add your handling code here:
         try{
            //this.area = jComboBoxArea.getSelectedItem().toString();
            
        this.producto = jComboBoxProducto.getSelectedItem().toString();
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_jComboBoxProductoItemStateChanged

    private void jTextDetalleEmpleadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextDetalleEmpleadosMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDetalleEmpleadosMouseEntered

    private void jTextDetalleEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextDetalleEmpleadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextDetalleEmpleadosActionPerformed

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
            java.util.logging.Logger.getLogger(RegistroActividad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistroActividad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistroActividad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroActividad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegistroActividad dialog = new RegistroActividad(new javax.swing.JFrame(), true);
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

    public void listaProducto(){
        try {
            st=cn.createStatement();
            String r = "SELECT NOMBRE FROM "+conectar.user_admin+".PRODUCTO ORDER BY CODIGO";
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
//            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error Al Conectar: "+e);
        }
    }   
    public void limpiarListaProducto(){
        jComboBoxProducto.removeAllItems();
    }
    private int codigoProducto(){
        int codigoNombreProducto = 0;
        try {
            String r = "SELECT CODIGO FROM "+conectar.user_admin+".PRODUCTO WHERE NOMBRE= '"+jComboBoxProducto.getSelectedItem().toString()+"' ORDER BY 1";
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
            JOptionPane.showMessageDialog(pa, "Error Al Devolver Codigo de Producto");
            System.out.println(e);
        }
        return codigoNombreProducto;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Titulo;
    private javax.swing.JToggleButton jBottonMostrarTabla1;
    private javax.swing.JButton jButtonBuscar1;
    private javax.swing.JButton jButtonDetalleEmpleado;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonOk;
    private com.toedter.calendar.JDateChooser jCalendario;
    private javax.swing.JComboBox<String> jComboBoxActividad;
    private javax.swing.JComboBox<String> jComboBoxArea;
    private javax.swing.JComboBox<String> jComboBoxProducto;
    private javax.swing.JLabel jLabelActividad;
    private javax.swing.JLabel jLabelActividad1;
    private javax.swing.JLabel jLabelArea;
    private javax.swing.JLabel jLabelArea1;
    private javax.swing.JLabel jLabelCodigo;
    private javax.swing.JLabel jLabelFechaInicio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private rojerusan.RSTableMetro jTable1;
    private javax.swing.JTextField jTextCodigo;
    private javax.swing.JTextField jTextDetalleEmpleados;
    // End of variables declaration//GEN-END:variables
}