package Screens;

import Panels.Clientes;
import Panels.Faturas;
import RelcCon.MostrarPanel;
import Panels.Home;
import Panels.Recibo;
import Reports.ReportView;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class Main extends javax.swing.JFrame {
    
    MostrarPanel LayPanel = new MostrarPanel();
    Home home = new Home();
    Recibo recibo = new Recibo();
    Faturas fatura = new Faturas();
    Clientes cliente = new Clientes();

    public Main() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icons/logo64.png")));
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Icons/logo64.png")).getImage());
        homeV();
    }
        
    
    public void homeV(){
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        LayPanel.mostrarPanel(MainPanel,home);
        btnClose.setVisible(false);
    }
    
    public  void reciboV(){
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        recibo.refreshTabelRec();
        recibo.refreshFatura();
        recibo.limpar();
        LayPanel.mostrarPanel(MainPanel,recibo);
        btnClose.setVisible(true);
    }
    public  void faturaV(){
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        fatura.refreshClient();
        fatura.refreshTabelFac();
        fatura.limpar();
        LayPanel.mostrarPanel(MainPanel,fatura);
        btnClose.setVisible(true);
    }
    public  void clienteV(){
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
        cliente.refreshTabelCli();
        cliente.limpar();
        LayPanel.mostrarPanel(MainPanel,cliente);
        btnClose.setVisible(true);
    }
    
    public void printAllRecibos(){
        try {
            ReportView r = new ReportView("src\\reports\\recibos.jasper");
            r.setVisible(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void printAllFaturas(){
        try {
            ReportView r = new ReportView("src\\reports\\facturas.jasper");
            r.setVisible(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        MainPanel = new javax.swing.JPanel();
        PanelToolbar = new javax.swing.JPanel();
        toolbar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnFatura = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        btnRecibo = new javax.swing.JButton();
        btnClose = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        addCliente = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        addFactura = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        printAllFaturas = new javax.swing.JMenuItem();
        printAllRecibos = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        menuHelp = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(800, 650));
        setPreferredSize(new java.awt.Dimension(800, 650));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        MainPanel.setBackground(new java.awt.Color(245, 245, 245));
        MainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        MainPanel.setPreferredSize(new java.awt.Dimension(800, 500));
        MainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        toolbar1.setBackground(new java.awt.Color(245, 245, 245));
        toolbar1.setFloatable(false);
        toolbar1.setRollover(true);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/home.png"))); // NOI18N
        jButton2.setToolTipText("Painel Principal");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        toolbar1.add(jButton2);

        jButton3.setText(" ");
        jButton3.setEnabled(false);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar1.add(jButton3);

        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/client.png"))); // NOI18N
        btnCliente.setFocusable(false);
        btnCliente.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCliente.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        toolbar1.add(btnCliente);

        jButton4.setText(" ");
        jButton4.setEnabled(false);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar1.add(jButton4);

        btnFatura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/fatura.png"))); // NOI18N
        btnFatura.setToolTipText("Novo Recibo");
        btnFatura.setFocusable(false);
        btnFatura.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFatura.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFaturaActionPerformed(evt);
            }
        });
        toolbar1.add(btnFatura);

        jButton5.setText(" ");
        jButton5.setEnabled(false);
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbar1.add(jButton5);

        btnRecibo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/recibo.png"))); // NOI18N
        btnRecibo.setToolTipText("Novo Recibo");
        btnRecibo.setFocusable(false);
        btnRecibo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRecibo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReciboActionPerformed(evt);
            }
        });
        toolbar1.add(btnRecibo);

        btnClose.setBackground(new java.awt.Color(245, 245, 245));
        btnClose.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        btnClose.setForeground(new java.awt.Color(255, 51, 51));
        btnClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/back.png"))); // NOI18N
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnClose.setOpaque(true);
        btnClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCloseMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout PanelToolbarLayout = new javax.swing.GroupLayout(PanelToolbar);
        PanelToolbar.setLayout(PanelToolbarLayout);
        PanelToolbarLayout.setHorizontalGroup(
            PanelToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelToolbarLayout.createSequentialGroup()
                .addComponent(toolbar1, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelToolbarLayout.setVerticalGroup(
            PanelToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(PanelToolbarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(toolbar1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(btnClose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText("Ficheiro");

        addCliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        addCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/clientMenu.png"))); // NOI18N
        addCliente.setText(" Clientes");
        addCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addClienteActionPerformed(evt);
            }
        });
        jMenu1.add(addCliente);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/faturaMenu.png"))); // NOI18N
        jMenuItem1.setText("Faturas");
        jMenu1.add(jMenuItem1);

        addFactura.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.SHIFT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        addFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/reciboMenu.png"))); // NOI18N
        addFactura.setText("Recibos");
        addFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFacturaActionPerformed(evt);
            }
        });
        jMenu1.add(addFactura);

        jMenuBar1.add(jMenu1);

        jMenu4.setEnabled(false);
        jMenuBar1.add(jMenu4);

        jMenu3.setText("Imprimir");

        printAllFaturas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        printAllFaturas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/printMenu.png"))); // NOI18N
        printAllFaturas.setText("Todas Faturas");
        printAllFaturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printAllFaturasActionPerformed(evt);
            }
        });
        jMenu3.add(printAllFaturas);

        printAllRecibos.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_DOWN_MASK | java.awt.event.InputEvent.CTRL_DOWN_MASK));
        printAllRecibos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/printMenu.png"))); // NOI18N
        printAllRecibos.setText("Todos Recibos");
        printAllRecibos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printAllRecibosActionPerformed(evt);
            }
        });
        jMenu3.add(printAllRecibos);

        jMenuBar1.add(jMenu3);

        jMenu5.setEnabled(false);
        jMenuBar1.add(jMenu5);

        menuHelp.setText("Sobre");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_about_18px.png"))); // NOI18N
        jMenuItem2.setText("Sobre");
        menuHelp.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icons8_help_18px.png"))); // NOI18N
        jMenuItem3.setText("Ajuda");
        menuHelp.add(jMenuItem3);

        jMenuBar1.add(menuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(PanelToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void addClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addClienteActionPerformed
       clienteV();
    }//GEN-LAST:event_addClienteActionPerformed

    private void addFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFacturaActionPerformed
        
    }//GEN-LAST:event_addFacturaActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
         
    }//GEN-LAST:event_formWindowOpened

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       homeV();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReciboActionPerformed
       reciboV();
    }//GEN-LAST:event_btnReciboActionPerformed

    private void btnCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCloseMouseClicked
        homeV();
    }//GEN-LAST:event_btnCloseMouseClicked

    private void printAllFaturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printAllFaturasActionPerformed
        printAllFaturas();
    }//GEN-LAST:event_printAllFaturasActionPerformed

    private void btnFaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFaturaActionPerformed
       faturaV();
    }//GEN-LAST:event_btnFaturaActionPerformed

    private void printAllRecibosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printAllRecibosActionPerformed
       printAllRecibos();
    }//GEN-LAST:event_printAllRecibosActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
       clienteV();
    }//GEN-LAST:event_btnClienteActionPerformed

    public static void main(String args[]) {
  
        try {
           javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
  
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel MainPanel;
    private javax.swing.JPanel PanelToolbar;
    private javax.swing.JMenuItem addCliente;
    private javax.swing.JMenuItem addFactura;
    private javax.swing.JButton btnCliente;
    private javax.swing.JLabel btnClose;
    private javax.swing.JButton btnFatura;
    private javax.swing.JButton btnRecibo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem printAllFaturas;
    private javax.swing.JMenuItem printAllRecibos;
    private javax.swing.JToolBar toolbar1;
    // End of variables declaration//GEN-END:variables
}
