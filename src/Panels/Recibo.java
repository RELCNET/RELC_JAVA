package Panels;

import RelcCon.RELC;
import Reports.ReportView;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class Recibo extends javax.swing.JPanel {
    
    ResultSet Rs = null;
    Statement St = null;

    public Recibo() {
        initComponents();
        refreshTabelRec();
        refreshFatura();
        recId.setVisible(false);
        lblRecibo.setVisible(false);
        limpar();
    }
    
    //ADD RECIBO
    public void addRecibo(){
        
        int confirma = JOptionPane.showConfirmDialog(null, "Fechar factura", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {

            String facID = facId.getText();
            String name = recCliente.getText();
            String desc = recDesc.getText();
            String price = recPrice.getText();
            String data = recDate.getText();

            if (facID.equals("") || name.equals("")|| desc.equals("") || data.equals("") ) {
               JOptionPane.showMessageDialog(null, "preencher todos os campos(*)", "Atenção", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    St = RELC.mycon().createStatement();
                    St.executeUpdate("insert into recibos(facId, name, descricao, price, date) values('"+facID+"', '"+name+"','" + desc + "','" + price + "', '"+data+"') ");

                    JOptionPane.showMessageDialog(null, "Salvo com sucesso");
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar...\nPossivel erro: recibo já existe ou sem número de fatura.", "Erro", JOptionPane.ERROR_MESSAGE);
                    System.out.println(e);
                }
                updateStatus();
            }
            refreshFatura();
            refreshTabelRec();
            limpar();
        }
    }
    
    //UPDATE STATUS
    public void updateStatus(){
        
        String facID = facId.getText();
        String estado = "Pago";
        
        try {
            St = RELC.mycon().createStatement();
            St.executeUpdate("update faturas set estado='"+estado+"' where id='"+facID+"' ");
        } catch (Exception e) {
             System.out.println(e);
        }
    }
    
    //EDITA RECIBO
    public void editRecibo(){
        
        String id = recId.getText();
        String facID = facId.getText();
        String name = recCliente.getText();
        String desc = recDesc.getText();
        String price = recPrice.getText();
        String data = recDate.getText();


        if ( facID.equals("") || name.equals("")|| desc.equals("") || data.equals("") ) {
            JOptionPane.showMessageDialog(null, "preencher todos os campos(*)", "Atenção", JOptionPane.WARNING_MESSAGE);
        }  else {
            try {
                St = RELC.mycon().createStatement();
                St.executeUpdate("update recibos set name='" + name + "', descricao='"+desc+"', price='"+price+"', date='"+data+"' where id='"+id+"' ");
                JOptionPane.showMessageDialog(null, "Salvo com sucesso");
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar...\nPossivel erro: recibo já existe ou sem número de fatura.", "Erro", JOptionPane.ERROR_MESSAGE);
                System.out.println(e);
            }
            limpar();
            refreshTabelRec();
        }
    }
    
    //DELETE RECIBO
     public void deleteRecibo(){
        
        String id = recId.getText();

        if ( id.equals("")) {
            JOptionPane.showMessageDialog(null, "selecionar recibo a apagar(*)", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                try {
                    St = RELC.mycon().createStatement();
                    St.executeUpdate("delete from recibos where id='"+id+"' ");
                   JOptionPane.showMessageDialog(null, "Dados eliminados", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao apagar...\nPossivel erro: base de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                    System.out.println(e);
                }
                limpar();
                refreshTabelRec();
            }
        }
    }
    
    
    //ATUALIZAR TABELA
    public void refreshTabelRec(){
        try {
            DefaultTableModel dt = (DefaultTableModel) recTable.getModel();
            St = RELC.mycon().createStatement();
            Rs = St.executeQuery("select id as Recibo,facId as Factura, date as Data, name as Cliente,descricao as Descrição, price as Valor from recibos order by id desc");
            recTable.setModel(DbUtils.resultSetToTableModel(Rs));
       } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
       //COMBO Cliente
    public void refreshFatura(){
        try {       
            St = RELC.mycon().createStatement();
            Rs = St.executeQuery("select * from faturas where estado='Por pagar' order by id");
            Vector v = new Vector();
            
            while (Rs.next()) {                
                v.add(Rs.getString("id"));
                DefaultComboBoxModel combo = new DefaultComboBoxModel(v);
                recFatura.setModel(combo);
            }
            recFatura.setSelectedIndex(-1);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    //SETAR TABELA
    public void selectFactura(){
        
        try {   
            String id = recFatura.getSelectedItem().toString();
            
            St = RELC.mycon().createStatement();
            Rs = St.executeQuery("select * from faturas where id='"+id+"' ");
            
            while (Rs.next()) {                    
                facId.setText(Rs.getString("id"));
                recCliente.setText(Rs.getString("name"));
                recDesc.setText(Rs.getString("descricao"));
                recPrice.setText(Rs.getString("price"));
                recDate.setText(Rs.getString("date"));
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
     //SETAR TABELA
    public void selectTable(){
        DefaultTableModel model = (DefaultTableModel) recTable.getModel();
        int myindex = recTable.getSelectedRow();
        recId.setText(model.getValueAt(myindex, 0).toString());
        facId.setText(model.getValueAt(myindex, 1).toString());
        recDate.setText(model.getValueAt(myindex, 2).toString());
        recCliente.setText(model.getValueAt(myindex, 3).toString());
        recDesc.setText(model.getValueAt(myindex, 4).toString());
        recPrice.setText(model.getValueAt(myindex, 5).toString());
        btnSave.setVisible(false);
        btnDelete.setVisible(true);
        btnPrint.setVisible(true);
        recId.setVisible(true);
        lblRecibo.setVisible(true);
        recFatura.setVisible(false);
    }
    
    //LIMPAR TUDO
    public void limpar(){
        facId.setText("");
        recId.setText("");
        recDate.setText("");
        recCliente.setText("");
        recFatura.setSelectedIndex(-1);
        recDesc.setText("");
        recPrice.setText("");
        btnSave.setVisible(true);
        btnDelete.setVisible(false);
        btnPrint.setVisible(false);
        recId.setVisible(false);
        lblRecibo.setVisible(false);
        recFatura.setVisible(true);
    }
    
    //IMPRIMIR
    private void imprimirRecibo() {
        int confirmar = JOptionPane.showConfirmDialog(null, "Confirma a impressão do recibo?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                //usando a class HassMap para criar um filtro
                HashMap para = new HashMap();
                para.put("id_para", recId.getText());
                ReportView r = new ReportView("src\\reports\\recibosId.jasper", para);
                r.setVisible(true);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Houve um um erro ao imprimir", "Erro", JOptionPane.ERROR_MESSAGE);
                System.out.println(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ReciboPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        recTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        recDate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        recDesc = new javax.swing.JTextField();
        recPrice = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JLabel();
        btnClear = new javax.swing.JLabel();
        recCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        facId = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        recId = new javax.swing.JTextField();
        btnPrint = new javax.swing.JLabel();
        btnDelete = new javax.swing.JLabel();
        recFatura = new javax.swing.JComboBox<>();
        lblRecibo = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(800, 500));

        ReciboPanel.setBackground(new java.awt.Color(255, 255, 255));
        ReciboPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recibo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 20), new java.awt.Color(0, 102, 102))); // NOI18N
        ReciboPanel.setPreferredSize(new java.awt.Dimension(800, 500));

        recTable.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        recTable.setForeground(new java.awt.Color(51, 51, 51));
        recTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Recibo Nº", "Factura Nº", "Data", "Cliente", "Descrição", "Valor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        recTable.setGridColor(new java.awt.Color(204, 204, 204));
        recTable.setSelectionBackground(new java.awt.Color(0, 187, 255));
        recTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(recTable);
        if (recTable.getColumnModel().getColumnCount() > 0) {
            recTable.getColumnModel().getColumn(0).setPreferredWidth(50);
            recTable.getColumnModel().getColumn(5).setMaxWidth(150);
        }

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setText("Cliente");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Data");

        recDate.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        recDate.setForeground(new java.awt.Color(51, 51, 51));
        recDate.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        recDate.setEnabled(false);

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Descrição");

        recDesc.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        recDesc.setForeground(new java.awt.Color(51, 51, 51));
        recDesc.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        recDesc.setEnabled(false);

        recPrice.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        recPrice.setForeground(new java.awt.Color(51, 51, 51));
        recPrice.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        recPrice.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Valor");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSave.setBackground(new java.awt.Color(51, 204, 0));
        btnSave.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/check.png"))); // NOI18N
        btnSave.setToolTipText("Finalizar e prosseguir");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setOpaque(true);
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
        jPanel3.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 71, 27));

        btnClear.setBackground(new java.awt.Color(255, 153, 0));
        btnClear.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnClear.setForeground(new java.awt.Color(255, 255, 255));
        btnClear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh.png"))); // NOI18N
        btnClear.setToolTipText("Limpar e Atualizar");
        btnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClear.setOpaque(true);
        btnClear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnClearMouseClicked(evt);
            }
        });
        jPanel3.add(btnClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 71, 27));

        recCliente.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        recCliente.setForeground(new java.awt.Color(51, 51, 51));
        recCliente.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        recCliente.setEnabled(false);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel7.setText("Fatura Nº");

        facId.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        facId.setForeground(new java.awt.Color(51, 51, 51));
        facId.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        facId.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(recDate))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel3)
                        .addGap(21, 21, 21)
                        .addComponent(recPrice))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel7)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(recDesc))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(recCliente)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(facId, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))))
                .addGap(162, 162, 162)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(facId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(recCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(recDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        recId.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        recId.setForeground(new java.awt.Color(153, 0, 0));
        recId.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        recId.setEnabled(false);
        jPanel2.add(recId, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 10, 90, -1));

        btnPrint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/print.png"))); // NOI18N
        btnPrint.setToolTipText("Imprimir recibo");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });
        jPanel2.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 50, 30));

        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete.png"))); // NOI18N
        btnDelete.setToolTipText("Apagar recibo");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });
        jPanel2.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 50, 30));

        recFatura.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        recFatura.setForeground(new java.awt.Color(153, 0, 0));
        recFatura.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Faturas" }));
        recFatura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                recFaturaMouseClicked(evt);
            }
        });
        recFatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recFaturaActionPerformed(evt);
            }
        });
        jPanel2.add(recFatura, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 80, -1));

        lblRecibo.setBackground(new java.awt.Color(255, 255, 255));
        lblRecibo.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblRecibo.setText("Recibo Nº");
        lblRecibo.setPreferredSize(new java.awt.Dimension(60, 25));
        jPanel2.add(lblRecibo, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 10, 70, -1));

        javax.swing.GroupLayout ReciboPanelLayout = new javax.swing.GroupLayout(ReciboPanel);
        ReciboPanel.setLayout(ReciboPanelLayout);
        ReciboPanelLayout.setHorizontalGroup(
            ReciboPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReciboPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(ReciboPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ReciboPanelLayout.setVerticalGroup(
            ReciboPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReciboPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ReciboPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ReciboPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 478, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        addRecibo();
    }//GEN-LAST:event_btnSaveMouseClicked

    private void recTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recTableMouseClicked
        selectTable();
    }//GEN-LAST:event_recTableMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        limpar();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        limpar();
        refreshFatura();
    }//GEN-LAST:event_btnClearMouseClicked

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
       imprimirRecibo();
    }//GEN-LAST:event_btnPrintMouseClicked

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
         deleteRecibo();
    }//GEN-LAST:event_btnDeleteMouseClicked

    private void recFaturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_recFaturaMouseClicked
        
    }//GEN-LAST:event_recFaturaMouseClicked

    private void recFaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recFaturaActionPerformed
        selectFactura();
    }//GEN-LAST:event_recFaturaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ReciboPanel;
    private javax.swing.JLabel btnClear;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnPrint;
    private javax.swing.JLabel btnSave;
    private javax.swing.JTextField facId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecibo;
    private javax.swing.JTextField recCliente;
    private javax.swing.JTextField recDate;
    private javax.swing.JTextField recDesc;
    private javax.swing.JComboBox<String> recFatura;
    private javax.swing.JTextField recId;
    private javax.swing.JTextField recPrice;
    private javax.swing.JTable recTable;
    // End of variables declaration//GEN-END:variables
}
