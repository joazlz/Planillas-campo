package fincabellavista.VentanasConsultas;

import clases.ConexionBD;
import clases.ExportarAExcel;
import java.awt.FileDialog;
import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.text.DateFormat;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Planilla extends javax.swing.JFrame {
    private SQLConsultasPlanilla Consulta = new SQLConsultasPlanilla(this);
    private Date fecha1 = new Date();
    private Date fecha2 = new Date();
    private boolean HojaTodos = false;
    private String tipo = null;
    
    public Planilla() {
        initComponents();
        this.setLocationRelativeTo(null);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jDate1.setDate(fecha1);
        jDate2.setDate(fecha2);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/reportar.png")).getImage());
    }

    private void limpiarLabel(){
        jLabelDiasLababorados.setText("");
        labelDiasLaborados.setText("");
        jLabelTotalPersonas.setText("");
        labelTotalPersonas.setText("");
        jLabelTotalAPagar.setText("");
        labelTotalAPagar.setText("");
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new rojerusan.RSTableMetro();
        jPanel3 = new javax.swing.JPanel();
        jDate1 = new com.toedter.calendar.JDateChooser();
        jDate2 = new com.toedter.calendar.JDateChooser();
        jLabelDe = new javax.swing.JLabel();
        jLabelAl = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabelDiasLababorados = new javax.swing.JLabel();
        jLabelTotalPersonas = new javax.swing.JLabel();
        jLabelTotalAPagar = new javax.swing.JLabel();
        labelDiasLaborados = new javax.swing.JLabel();
        labelTotalPersonas = new javax.swing.JLabel();
        labelTotalAPagar = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jButtonHojaDePagos = new javax.swing.JButton();
        jButtonInformeAct = new javax.swing.JButton();
        jButtonExportarHDP = new javax.swing.JButton();
        jButtonExportarHDP1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelPlanilla = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PLANILLA");

        jPanel1.setBackground(new java.awt.Color(92, 125, 137));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBar(null);

        jTable1.setAltoHead(25);
        jTable1.setColorBackgoundHead(new java.awt.Color(77, 86, 86));
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
        jScrollPane2.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(77, 86, 86));
        jPanel3.setToolTipText("");

        jLabelDe.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelDe.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDe.setText("De:");

        jLabelAl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelAl.setForeground(new java.awt.Color(255, 255, 255));
        jLabelAl.setText("Al:");

        jPanel4.setBackground(new java.awt.Color(77, 86, 86));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(77, 86, 86)), "Informacion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setName(""); // NOI18N

        jLabelDiasLababorados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelDiasLababorados.setForeground(new java.awt.Color(255, 255, 255));
        jLabelDiasLababorados.setText("Dias Laborados:");

        jLabelTotalPersonas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTotalPersonas.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalPersonas.setText("Total de Personas:");

        jLabelTotalAPagar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabelTotalAPagar.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTotalAPagar.setText("Total a Pagar:");

        labelDiasLaborados.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelDiasLaborados.setForeground(new java.awt.Color(255, 255, 255));

        labelTotalPersonas.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelTotalPersonas.setForeground(new java.awt.Color(255, 255, 255));

        labelTotalAPagar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        labelTotalAPagar.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTotalPersonas)
                    .addComponent(jLabelTotalAPagar)
                    .addComponent(jLabelDiasLababorados))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelDiasLaborados, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(labelTotalAPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTotalPersonas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelDiasLababorados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelDiasLaborados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelTotalAPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTotalAPagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabelTotalPersonas)
                    .addComponent(labelTotalPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(77, 86, 86));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Generar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setName(""); // NOI18N

        jButtonHojaDePagos.setForeground(new java.awt.Color(255, 255, 255));
        jButtonHojaDePagos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Hoja De Pagos Normal.png"))); // NOI18N
        jButtonHojaDePagos.setText(" HOJA DE PAGOS");
        jButtonHojaDePagos.setToolTipText(" HOJA DE PAGOS");
        jButtonHojaDePagos.setBorderPainted(false);
        jButtonHojaDePagos.setContentAreaFilled(false);
        jButtonHojaDePagos.setDefaultCapable(false);
        jButtonHojaDePagos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonHojaDePagos.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Hoja De Pagos Normal.png"))); // NOI18N
        jButtonHojaDePagos.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Hoja De Pagos Seleccionado.png"))); // NOI18N
        jButtonHojaDePagos.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonHojaDePagos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonHojaDePagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonHojaDePagosActionPerformed(evt);
            }
        });

        jButtonInformeAct.setForeground(new java.awt.Color(255, 255, 255));
        jButtonInformeAct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Informe de Actividades Normal.png"))); // NOI18N
        jButtonInformeAct.setText("INFO DE ACTIVIDADES");
        jButtonInformeAct.setToolTipText("INFORME DE ACTIVIDADES");
        jButtonInformeAct.setBorderPainted(false);
        jButtonInformeAct.setContentAreaFilled(false);
        jButtonInformeAct.setDefaultCapable(false);
        jButtonInformeAct.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonInformeAct.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Informe de Actividades Normal.png"))); // NOI18N
        jButtonInformeAct.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Informe de Actividades Seleccionado.png"))); // NOI18N
        jButtonInformeAct.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButtonInformeAct.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonInformeAct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInformeActActionPerformed(evt);
            }
        });

        jButtonExportarHDP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar PDFExcel.png"))); // NOI18N
        jButtonExportarHDP.setToolTipText("Exportar");
        jButtonExportarHDP.setBorderPainted(false);
        jButtonExportarHDP.setContentAreaFilled(false);
        jButtonExportarHDP.setDefaultCapable(false);
        jButtonExportarHDP.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar PDFExcel.png"))); // NOI18N
        jButtonExportarHDP.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar PDFExcel Seleccionado.png"))); // NOI18N
        jButtonExportarHDP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportarHDPActionPerformed(evt);
            }
        });

        jButtonExportarHDP1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar PDFExcel.png"))); // NOI18N
        jButtonExportarHDP1.setToolTipText("Exportar");
        jButtonExportarHDP1.setBorderPainted(false);
        jButtonExportarHDP1.setContentAreaFilled(false);
        jButtonExportarHDP1.setDefaultCapable(false);
        jButtonExportarHDP1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar PDFExcel.png"))); // NOI18N
        jButtonExportarHDP1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Exportar PDFExcel Seleccionado.png"))); // NOI18N
        jButtonExportarHDP1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportarHDP1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButtonInformeAct)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonExportarHDP1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jButtonHojaDePagos, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonExportarHDP, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 134, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jButtonExportarHDP, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonHojaDePagos)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonInformeAct, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonExportarHDP1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDe)
                    .addComponent(jDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAl)
                    .addComponent(jDate2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabelDe, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDate1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelAl, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(jDate2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelPlanilla.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        jLabelPlanilla.setForeground(new java.awt.Color(102, 102, 102));
        jLabelPlanilla.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelPlanilla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reportar.png"))); // NOI18N
        jLabelPlanilla.setText("PLANILLA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addComponent(jLabelPlanilla, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabelPlanilla)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
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

    private void jButtonHojaDePagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonHojaDePagosActionPerformed
        
        String[] botones1 = Consulta.TipoDeEmpleados();
        Object[] botones = Consulta.TipoDeEmpleados();
        int vSelec = JOptionPane.showOptionDialog(this, "¿De que personal la desea?", "Generar", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones1, botones[0]);
        if (vSelec == 0) {
            try{
                limpiarLabel();
                DateFormat variableFecha = DateFormat.getDateInstance();
                String fecha1 = variableFecha.format(jDate1.getDate());
                String fecha2 = variableFecha.format(jDate2.getDate());
                this.jTable1.setModel(Consulta.HojaDePagos(fecha1, fecha2));
                jLabelDiasLababorados.setText("Dias Laborados:");
                String diasL = Consulta.DiasTrabajados(fecha1, fecha2);
                labelDiasLaborados.setText(diasL);
                jLabelTotalPersonas.setText("Total De Personas:");
                String totalP = Integer.toString(jTable1.getRowCount());
                labelTotalPersonas.setText(totalP);
                jLabelTotalAPagar.setText("Total A Pagar:");
                float sumaTotal=0; 
                for(int i =0; i < jTable1.getRowCount(); i++){
                    String cant = String.valueOf(jTable1.getValueAt(i, 3));
                        sumaTotal +=Float.parseFloat(cant);
                }
                labelTotalAPagar.setText(Float.toString(sumaTotal));
                HojaTodos=true;
            }catch(Exception e){
            }
        }else{
            this.tipo = botones1[vSelec];
            limpiarLabel();
            DateFormat variableFecha = DateFormat.getDateInstance();
            String fecha1 = variableFecha.format(jDate1.getDate());
            String fecha2 = variableFecha.format(jDate2.getDate());
            String tipo = this.tipo;
            this.jTable1.setModel(Consulta.HojaDePagosTipo(fecha1, fecha2,tipo));
            jLabelDiasLababorados.setText("Dias Laborados:");
            String diasL = Consulta.DiasTrabajados(fecha1, fecha2);
            labelDiasLaborados.setText(diasL);
            jLabelTotalPersonas.setText("Total De Personas:");
            String totalP = Integer.toString(jTable1.getRowCount());
            labelTotalPersonas.setText(totalP);
            jLabelTotalAPagar.setText("Total A Pagar:");
            float sumaTotal=0; 
            for(int i =0; i < jTable1.getRowCount(); i++){
                String cant = String.valueOf(jTable1.getValueAt(i, 3));
                    sumaTotal +=Float.parseFloat(cant);
            }
            labelTotalAPagar.setText(Float.toString(sumaTotal));
        }
    }//GEN-LAST:event_jButtonHojaDePagosActionPerformed

    private void jButtonExportarHDPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportarHDPActionPerformed
        String[] botones1 = {"Excel", "Otros"};
            Object[] botones = {"Excel", "Otros"};
            int vSelec = JOptionPane.showOptionDialog(this, "Deseas Exportar A:", "Exportar", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones1, botones[0]);
            switch (vSelec) {
                case 0:
                    if(this.jTable1.getRowCount()==0){
                        JOptionPane.showMessageDialog(this, "No hay datos en la tabla para exportar.","PLANILLA", JOptionPane.WARNING_MESSAGE);
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
                        nom.add("Hoja De Pagos");
                        String file=dialog.getDirectory()+dialog.getFile().concat(".xls");
                        try{
                            clases.ExportarAExcel e = new clases.ExportarAExcel(new File(file),tb , nom);
                            if(e.export()){
                             Image  imagen = new ImageIcon(getClass().getResource("/imagenes/Excel.png")).getImage();
                            ImageIcon im = new ImageIcon(imagen);
                            Icon expo = new ImageIcon(im.getImage());
                            JOptionPane.showMessageDialog(this, "Los Datos Fueron Exportados a Excel", "PLANILA", JOptionPane.OK_OPTION, expo);
                            }
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(this, "Ocurrio un error " + ex.getMessage(),"PLANILLA",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case 1:
                    if(HojaTodos==true){
                        try {
                            SimpleDateFormat variableFecha = new SimpleDateFormat("dd/MM/YYYY");
                            ConexionBD conectar = new ConexionBD();
                            Connection cn = conectar.getConnection();
                            String fecha1 = variableFecha.format(jDate1.getDate());
                            System.out.println(fecha1);
                            String fecha2 = variableFecha.format(jDate2.getDate());
                            System.out.println(fecha2);
                            String rutaReporte = "\\\\servidor\\users\\Administrator\\documents\\compartido\\FincaBellaVista 2.0 - full\\build\\classes\\Reporte\\HojaDePagos.jrxml";
                            Map parametros = new HashMap();
                            parametros.put("Fecha1", fecha1);
                            parametros.put("Fecha2", fecha2);
                            JasperReport reporte = JasperCompileManager.compileReport(rutaReporte);
                            JasperPrint informe = JasperFillManager.fillReport(reporte, parametros, cn);
                            JasperViewer ventana = new JasperViewer(informe, false);
                            ventana.setIconImage(new ImageIcon(getClass().getResource("/imagenes/reportar.png")).getImage());
                            ventana.setTitle("Hoja De Pagos");
                            ventana.setVisible(true);
                        } catch (JRException ex) {
                            Logger.getLogger(Planilla.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }else{
                        try {
                            SimpleDateFormat variableFecha = new SimpleDateFormat("dd/MM/YYYY");
                            ConexionBD conectar = new ConexionBD();
                            Connection cn = conectar.getConnection();
                            String fecha1 = variableFecha.format(jDate1.getDate());
                            String fecha2 = variableFecha.format(jDate2.getDate());
                            String tipo = this.tipo;
                            String rutaReporte = "\\\\servidor\\users\\Administrator\\documents\\compartido\\FincaBellaVista 2.0 - full\\build\\classes\\Reporte\\HojaDePagos_1.jrxml";
                            Map parametros = new HashMap();
                            parametros.put("Fecha1", fecha1);
                            parametros.put("Fecha2", fecha2);
                            parametros.put("tipo", tipo);
                            JasperReport reporte = JasperCompileManager.compileReport(rutaReporte);
                            JasperPrint informe = JasperFillManager.fillReport(reporte, parametros, cn);
                            JasperViewer ventana = new JasperViewer(informe, false);
                            ventana.setIconImage(new ImageIcon(getClass().getResource("/imagenes/reportar.png")).getImage());
                            ventana.setTitle("Hoja De Pagos");
                            ventana.setVisible(true);
                        } catch (JRException ex) {
                            Logger.getLogger(Planilla.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                default:
                    break;
            }
    }//GEN-LAST:event_jButtonExportarHDPActionPerformed

    private void jButtonInformeActActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInformeActActionPerformed
        try{
            limpiarLabel();
            labelTotalPersonas.setText("");
            DateFormat variableFecha = DateFormat.getDateInstance();
            String fecha1 = variableFecha.format(jDate1.getDate());
            String fecha2 = variableFecha.format(jDate2.getDate());
            this.jTable1.setModel(Consulta.ActividadesRealizadas(fecha1, fecha2));
            jLabelDiasLababorados.setText("Actividad Más Realizada:");
            String ActAreaMasRealizada = Consulta.ActividadAreaMasRealizada(fecha1, fecha2);
            labelDiasLaborados.setText(ActAreaMasRealizada);
            jLabelTotalAPagar.setText("Costo de Actividad:");
            String PrecioAct = Consulta.PrecioActividadMasRealizada(fecha1, fecha2);
            labelTotalAPagar.setText(PrecioAct);
            jLabelTotalPersonas.setText("");
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_jButtonInformeActActionPerformed

    private void jButtonExportarHDP1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportarHDP1ActionPerformed
            String[] botones1 = {"Excel", "Otros"};
            Object[] botones = {"Excel", "Otros"};
            int vSelec = JOptionPane.showOptionDialog(this, "Deseas Exportar A:", "Exportar", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones1, botones[0]);
            switch (vSelec) {
                case 0:
                    if(this.jTable1.getRowCount()==0){
                        JOptionPane.showMessageDialog(this, "No hay datos en la tabla para exportar.","ACTIVIDADES", JOptionPane.WARNING_MESSAGE);
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
                        nom.add("Informe De Actividades");
                        String file=dialog.getDirectory()+dialog.getFile().concat(".xls");
                        try{
                            clases.ExportarAExcel e = new clases.ExportarAExcel(new File(file),tb , nom);
                            if(e.export()){
                                JOptionPane.showMessageDialog(this, "Los datos fueron exportados a excel.", "ACTIVIDADES",JOptionPane.INFORMATION_MESSAGE);
                            }
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(this, "Ocurrio un error " + ex.getMessage(),"ACTIVIDADES",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case 1:
                    try {
                        DateFormat variableFecha = DateFormat.getDateInstance();
                        ConexionBD conectar = new ConexionBD();
                        Connection cn = conectar.getConnection();
                        String fecha1 = variableFecha.format(jDate1.getDate());
                        String fecha2 = variableFecha.format(jDate2.getDate());
                        String rutaReporte = "C:\\Users\\joaqu\\Desktop\\FincaBellaVista 2.0\\src\\Reporte\\InformeActividades.jrxml";
                        Map parametros = new HashMap();
                        parametros.put("fecha1", fecha1);
                        parametros.put("fecha2", fecha2);
                        JasperReport reporte = JasperCompileManager.compileReport(rutaReporte);
                        JasperPrint informe = JasperFillManager.fillReport(reporte, parametros, cn);
                        JasperViewer ventana = new JasperViewer(informe, false);
                        ventana.setIconImage(new ImageIcon(getClass().getResource("/imagenes/reportar.png")).getImage());
                        ventana.setTitle("Informe De Actividades");
                        ventana.setVisible(true);
                    } catch (JRException ex) {
                        Logger.getLogger(Planilla.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    break;
                default:
                    break;
            }
    }//GEN-LAST:event_jButtonExportarHDP1ActionPerformed

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
            java.util.logging.Logger.getLogger(Planilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Planilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Planilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Planilla.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Planilla().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExportarHDP;
    private javax.swing.JButton jButtonExportarHDP1;
    private javax.swing.JButton jButtonHojaDePagos;
    private javax.swing.JButton jButtonInformeAct;
    private com.toedter.calendar.JDateChooser jDate1;
    private com.toedter.calendar.JDateChooser jDate2;
    private javax.swing.JLabel jLabelAl;
    private javax.swing.JLabel jLabelDe;
    private javax.swing.JLabel jLabelDiasLababorados;
    private javax.swing.JLabel jLabelPlanilla;
    private javax.swing.JLabel jLabelTotalAPagar;
    private javax.swing.JLabel jLabelTotalPersonas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private rojerusan.RSTableMetro jTable1;
    private javax.swing.JLabel labelDiasLaborados;
    private javax.swing.JLabel labelTotalAPagar;
    private javax.swing.JLabel labelTotalPersonas;
    // End of variables declaration//GEN-END:variables
}
