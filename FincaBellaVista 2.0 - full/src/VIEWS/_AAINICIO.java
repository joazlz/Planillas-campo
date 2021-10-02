package VIEWS;

import VIEWS.ACTIVIDAD;
import fincabellavista.VentanasConsultas.ConsultaActividades;
import fincabellavista.VentanasConsultas.ConsultaCombustible;
import fincabellavista.VentanasConsultas.ConsultaCosecha;
import fincabellavista.VentanasConsultas.Planilla;
import fincabellavista.venasSecundarias.RegistroActividad;
import java.awt.Image;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class _AAINICIO extends javax.swing.JFrame {

    Image image = new ImageIcon(getClass().getResource("/imagenes/icono.png")).getImage();
    ImageIcon im = new ImageIcon(image);
    Icon ico = new ImageIcon(im.getImage());

    public _AAINICIO() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Finca Bella Vista");
        setIconImage(image);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButtonRegistroActividad = new javax.swing.JButton();
        jButtonConsultas = new javax.swing.JButton();
        jButtonReportes = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButtonActividades = new javax.swing.JButton();
        jButtonProducto = new javax.swing.JButton();
        jButtonArea = new javax.swing.JButton();
        jButtonTipoEmpleado = new javax.swing.JButton();
        jButtonEmpleado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("FINCA BELLA VISTA");
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(101, 138, 96));

        jButtonRegistroActividad.setBackground(new java.awt.Color(0, 153, 153));
        jButtonRegistroActividad.setForeground(new java.awt.Color(255, 255, 255));
        jButtonRegistroActividad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Registro Actividades.png"))); // NOI18N
        jButtonRegistroActividad.setText("REGISTRO ACTIVIDAD");
        jButtonRegistroActividad.setToolTipText("REGISTRO ACTIVIDAD");
        jButtonRegistroActividad.setAutoscrolls(true);
        jButtonRegistroActividad.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonRegistroActividad.setBorderPainted(false);
        jButtonRegistroActividad.setContentAreaFilled(false);
        jButtonRegistroActividad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonRegistroActividad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonRegistroActividad.setNextFocusableComponent(jButtonArea);
        jButtonRegistroActividad.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Registro Actividades.png"))); // NOI18N
        jButtonRegistroActividad.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Registro Actividades Seleccionado.png"))); // NOI18N
        jButtonRegistroActividad.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jButtonRegistroActividad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonRegistroActividadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonRegistroActividadMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonRegistroActividadMousePressed(evt);
            }
        });
        jButtonRegistroActividad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRegistroActividadActionPerformed(evt);
            }
        });

        jButtonConsultas.setBackground(new java.awt.Color(0, 153, 153));
        jButtonConsultas.setForeground(new java.awt.Color(255, 255, 255));
        jButtonConsultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Consultas Normal.png"))); // NOI18N
        jButtonConsultas.setText("CONSULTAS");
        jButtonConsultas.setToolTipText("CONSULTAS");
        jButtonConsultas.setAutoscrolls(true);
        jButtonConsultas.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonConsultas.setBorderPainted(false);
        jButtonConsultas.setContentAreaFilled(false);
        jButtonConsultas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonConsultas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonConsultas.setNextFocusableComponent(jButtonArea);
        jButtonConsultas.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Consultas Normal.png"))); // NOI18N
        jButtonConsultas.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Consultas Seleccionado.png"))); // NOI18N
        jButtonConsultas.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jButtonConsultas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonConsultasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonConsultasMouseExited(evt);
            }
        });
        jButtonConsultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultasActionPerformed(evt);
            }
        });

        jButtonReportes.setBackground(new java.awt.Color(0, 153, 153));
        jButtonReportes.setForeground(new java.awt.Color(255, 255, 255));
        jButtonReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Report Normal.png"))); // NOI18N
        jButtonReportes.setText("PAGOS");
        jButtonReportes.setToolTipText("REPORTES");
        jButtonReportes.setAutoscrolls(true);
        jButtonReportes.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonReportes.setBorderPainted(false);
        jButtonReportes.setContentAreaFilled(false);
        jButtonReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonReportes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonReportes.setNextFocusableComponent(jButtonArea);
        jButtonReportes.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Report Normal.png"))); // NOI18N
        jButtonReportes.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Report Seleccionado.png"))); // NOI18N
        jButtonReportes.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jButtonReportes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonReportesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonReportesMouseExited(evt);
            }
        });
        jButtonReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReportesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jButtonRegistroActividad, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButtonReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jButtonConsultas, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                    .addComponent(jButtonConsultas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonRegistroActividad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );

        jPanel4.setBackground(new java.awt.Color(0, 180, 255));
        jPanel4.setToolTipText("");

        jButtonActividades.setBackground(new java.awt.Color(10, 120, 255));
        jButtonActividades.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonActividades.setForeground(new java.awt.Color(255, 255, 255));
        jButtonActividades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actividad pequeño.png"))); // NOI18N
        jButtonActividades.setText("ACTIVIDADES");
        jButtonActividades.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonActividades.setBorderPainted(false);
        jButtonActividades.setContentAreaFilled(false);
        jButtonActividades.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonActividades.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonActividades.setNextFocusableComponent(jButtonArea);
        jButtonActividades.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actividad pequeño.png"))); // NOI18N
        jButtonActividades.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Actividad seleccionado.png"))); // NOI18N
        jButtonActividades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonActividadesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonActividadesMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonActividadesMousePressed(evt);
            }
        });
        jButtonActividades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActividadesActionPerformed(evt);
            }
        });

        jButtonProducto.setBackground(new java.awt.Color(10, 120, 255));
        jButtonProducto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonProducto.setForeground(new java.awt.Color(255, 255, 255));
        jButtonProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Producto Pequeño.png"))); // NOI18N
        jButtonProducto.setText("PRODUCTOS");
        jButtonProducto.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonProducto.setBorderPainted(false);
        jButtonProducto.setContentAreaFilled(false);
        jButtonProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonProducto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonProducto.setNextFocusableComponent(jButtonArea);
        jButtonProducto.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Producto Pequeño.png"))); // NOI18N
        jButtonProducto.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Producto Seleccionado.png"))); // NOI18N
        jButtonProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonProductoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonProductoMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonProductoMousePressed(evt);
            }
        });
        jButtonProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProductoActionPerformed(evt);
            }
        });

        jButtonArea.setBackground(new java.awt.Color(10, 120, 255));
        jButtonArea.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonArea.setForeground(new java.awt.Color(255, 255, 255));
        jButtonArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Area Pequeño.png"))); // NOI18N
        jButtonArea.setText("AREAS");
        jButtonArea.setToolTipText("AREAS");
        jButtonArea.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonArea.setBorderPainted(false);
        jButtonArea.setContentAreaFilled(false);
        jButtonArea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonArea.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonArea.setNextFocusableComponent(jButtonArea);
        jButtonArea.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Area Pequeño.png"))); // NOI18N
        jButtonArea.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Area Seleccionado.png"))); // NOI18N
        jButtonArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonAreaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonAreaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonAreaMousePressed(evt);
            }
        });
        jButtonArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAreaActionPerformed(evt);
            }
        });

        jButtonTipoEmpleado.setBackground(new java.awt.Color(10, 120, 255));
        jButtonTipoEmpleado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonTipoEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jButtonTipoEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Tipo Empleado Pequeño.png"))); // NOI18N
        jButtonTipoEmpleado.setText("TIPOS DE EMPLEADOS");
        jButtonTipoEmpleado.setToolTipText("TIPOS DE EMPLEADOS");
        jButtonTipoEmpleado.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonTipoEmpleado.setBorderPainted(false);
        jButtonTipoEmpleado.setContentAreaFilled(false);
        jButtonTipoEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonTipoEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonTipoEmpleado.setNextFocusableComponent(jButtonArea);
        jButtonTipoEmpleado.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Tipo Empleado Pequeño.png"))); // NOI18N
        jButtonTipoEmpleado.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Tipo Empleado Seleccionado.png"))); // NOI18N
        jButtonTipoEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonTipoEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonTipoEmpleadoMouseExited(evt);
            }
        });
        jButtonTipoEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTipoEmpleadoActionPerformed(evt);
            }
        });

        jButtonEmpleado.setBackground(new java.awt.Color(10, 120, 255));
        jButtonEmpleado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButtonEmpleado.setForeground(new java.awt.Color(255, 255, 255));
        jButtonEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cuadrialla Normal.png"))); // NOI18N
        jButtonEmpleado.setText("EMPLEADOS");
        jButtonEmpleado.setToolTipText("EMPLEADOS");
        jButtonEmpleado.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButtonEmpleado.setBorderPainted(false);
        jButtonEmpleado.setContentAreaFilled(false);
        jButtonEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonEmpleado.setNextFocusableComponent(jButtonArea);
        jButtonEmpleado.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cuadrialla Normal.png"))); // NOI18N
        jButtonEmpleado.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Cuadrialla Seleccionado.png"))); // NOI18N
        jButtonEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonEmpleadoMouseExited(evt);
            }
        });
        jButtonEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEmpleadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButtonActividades, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonArea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonTipoEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 943, Short.MAX_VALUE)
            .addComponent(jButtonEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButtonProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jButtonActividades, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jButtonArea, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonTipoEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAreaActionPerformed
        AREA ventanaArea = new AREA();
        if (ventanaArea != null) {
            ventanaArea.dispose();
        }
        ventanaArea.eliminarDerivadas();
        ventanaArea.medidasDerivadas();
        ventanaArea.setVisible(true);
    }//GEN-LAST:event_jButtonAreaActionPerformed
    private void jButtonAreaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAreaMousePressed
        jButtonArea.setOpaque(true);
    }//GEN-LAST:event_jButtonAreaMousePressed
    private void jButtonAreaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAreaMouseEntered
        jButtonArea.setOpaque(true);
    }//GEN-LAST:event_jButtonAreaMouseEntered
    private void jButtonAreaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonAreaMouseExited
        jButtonArea.setOpaque(false);
    }//GEN-LAST:event_jButtonAreaMouseExited
    private void jButtonActividadesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonActividadesMouseEntered
        jButtonActividades.setOpaque(true);
    }//GEN-LAST:event_jButtonActividadesMouseEntered
    private void jButtonActividadesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonActividadesMouseExited
        jButtonActividades.setOpaque(false);
    }//GEN-LAST:event_jButtonActividadesMouseExited
    private void jButtonActividadesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonActividadesMousePressed
        jButtonActividades.setOpaque(true);
    }//GEN-LAST:event_jButtonActividadesMousePressed
    private void jButtonActividadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActividadesActionPerformed
        ACTIVIDAD ventanaActividad = new ACTIVIDAD();
        if (ventanaActividad != null) {
            ventanaActividad.dispose();
        }
        ventanaActividad.setVisible(true);
    }//GEN-LAST:event_jButtonActividadesActionPerformed
    private void jButtonRegistroActividadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRegistroActividadMouseEntered
        jButtonRegistroActividad.setOpaque(true);
    }//GEN-LAST:event_jButtonRegistroActividadMouseEntered
    private void jButtonRegistroActividadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRegistroActividadMouseExited
        jButtonRegistroActividad.setOpaque(false);
    }//GEN-LAST:event_jButtonRegistroActividadMouseExited
    private void jButtonRegistroActividadMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonRegistroActividadMousePressed
        jButtonRegistroActividad.setOpaque(false);
    }//GEN-LAST:event_jButtonRegistroActividadMousePressed
    private void jButtonRegistroActividadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRegistroActividadActionPerformed

        RegistroActividad ventanaRegistroActividad = new RegistroActividad(this, true);
        if (ventanaRegistroActividad != null) {
            ventanaRegistroActividad.dispose();
            ventanaRegistroActividad.limpiarVentana();
        }
        try {
            ventanaRegistroActividad.cargarDetalles(0, 0, "", new Date(), "", "");
        } catch (SQLException ex) {
            Logger.getLogger(_AAINICIO.class.getName()).log(Level.SEVERE, null, ex);
        }
        ventanaRegistroActividad.limpiarListaActividades();
        ventanaRegistroActividad.limpiarListaProducto();
        ventanaRegistroActividad.limpiarListaAreas();
        ventanaRegistroActividad.listaActividades();
        ventanaRegistroActividad.listaAreas();
        ventanaRegistroActividad.listaProducto();
        ventanaRegistroActividad.setVisible(true);
    }//GEN-LAST:event_jButtonRegistroActividadActionPerformed
    private void jButtonTipoEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonTipoEmpleadoMouseEntered
        jButtonTipoEmpleado.setOpaque(true);
    }//GEN-LAST:event_jButtonTipoEmpleadoMouseEntered
    private void jButtonTipoEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonTipoEmpleadoMouseExited
        jButtonTipoEmpleado.setOpaque(false);
    }//GEN-LAST:event_jButtonTipoEmpleadoMouseExited
    private void jButtonTipoEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTipoEmpleadoActionPerformed
        TIPOEMPLEADO ventanaTipoEmpleado = new TIPOEMPLEADO();
        if (ventanaTipoEmpleado != null) {
            ventanaTipoEmpleado.dispose();
        }
        ventanaTipoEmpleado.setVisible(true);
    }//GEN-LAST:event_jButtonTipoEmpleadoActionPerformed
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int vSelec = JOptionPane.showConfirmDialog(this, "¿Desea Salir?", "FINCA BELLA VISTA", JOptionPane.OK_OPTION, JOptionPane.CANCEL_OPTION, ico);
        if (vSelec == 0) {
            System.exit(0);
        }
    }//GEN-LAST:event_formWindowClosing
    private void jButtonEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEmpleadoMouseEntered
        jButtonEmpleado.setOpaque(true);
    }//GEN-LAST:event_jButtonEmpleadoMouseEntered
    private void jButtonEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonEmpleadoMouseExited
        jButtonEmpleado.setOpaque(false);
    }//GEN-LAST:event_jButtonEmpleadoMouseExited
    private void jButtonEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEmpleadoActionPerformed

        EMPLEADO ventanaEMPLEADO = new EMPLEADO();
        if (ventanaEMPLEADO != null) {
            ventanaEMPLEADO.dispose();
        }
        ventanaEMPLEADO.setVisible(true);
    }//GEN-LAST:event_jButtonEmpleadoActionPerformed

    private void jButtonReportesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonReportesMouseEntered
        jButtonReportes.setOpaque(true);
    }//GEN-LAST:event_jButtonReportesMouseEntered

    private void jButtonReportesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonReportesMouseExited
        jButtonReportes.setOpaque(false);
    }//GEN-LAST:event_jButtonReportesMouseExited

    private void jButtonReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReportesActionPerformed
        Planilla ventanaPlanilla = new Planilla();
          
        ventanaPlanilla.setVisible(true);
        if (ventanaPlanilla != null) {
            ventanaPlanilla.dispose();
        }
        ventanaPlanilla.setVisible(true);
    }//GEN-LAST:event_jButtonReportesActionPerformed

    private void jButtonConsultasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonConsultasMouseEntered
        jButtonConsultas.setOpaque(true);
    }//GEN-LAST:event_jButtonConsultasMouseEntered

    private void jButtonConsultasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonConsultasMouseExited
        jButtonConsultas.setOpaque(false);
    }//GEN-LAST:event_jButtonConsultasMouseExited

    private void jButtonConsultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultasActionPerformed

        String[] botones1 = {"Cosecha", "Actividades", "Combustible"};
        Object[] botones = {"Cosecha", "Actividades", "Combustible"};
        int vSelec = JOptionPane.showOptionDialog(this, "Consultas de:", "CONSULTAS", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, botones1, botones[0]);
        switch (vSelec) {
            case 0: {

                ConsultaCosecha ventanaConCosecha = new ConsultaCosecha();
                if (ventanaConCosecha != null) {
                    ventanaConCosecha.dispose();
                }
                ventanaConCosecha.setVisible(true);
            }
            break;
            case 1: {

                ConsultaActividades ventanaConActividad = new ConsultaActividades();
                if (ventanaConActividad != null) {
                    ventanaConActividad.dispose();
                }
                ventanaConActividad.setVisible(true);
            }
            break;
            case 2: {
                /**
                 * if(ventanaConCombustible!=null){
                 * ventanaConCombustible.dispose(); }
                 * ventanaConCombustible.setVisible(true);*
                 */
            }
            break;

        }

    }//GEN-LAST:event_jButtonConsultasActionPerformed

    private void jButtonProductoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonProductoMouseEntered
        jButtonProducto.setOpaque(true);
    }//GEN-LAST:event_jButtonProductoMouseEntered

    private void jButtonProductoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonProductoMouseExited
        jButtonProducto.setOpaque(false);
    }//GEN-LAST:event_jButtonProductoMouseExited

    private void jButtonProductoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonProductoMousePressed
        jButtonProducto.setOpaque(false);
    }//GEN-LAST:event_jButtonProductoMousePressed

    private void jButtonProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProductoActionPerformed
        ORGANIZACION ventanaProducto = new ORGANIZACION();

        if (ventanaProducto != null) {
            ventanaProducto.dispose();
        }
        ventanaProducto.setVisible(true);
    }//GEN-LAST:event_jButtonProductoActionPerformed

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
            java.util.logging.Logger.getLogger(_AAINICIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(_AAINICIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(_AAINICIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(_AAINICIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new _AAINICIO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonActividades;
    private javax.swing.JButton jButtonArea;
    private javax.swing.JButton jButtonConsultas;
    private javax.swing.JButton jButtonEmpleado;
    private javax.swing.JButton jButtonProducto;
    private javax.swing.JButton jButtonRegistroActividad;
    private javax.swing.JButton jButtonReportes;
    private javax.swing.JButton jButtonTipoEmpleado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables

}
