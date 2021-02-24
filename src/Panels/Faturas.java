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

public class Faturas extends javax.swing.JPanel {
    
    ResultSet Rs = null;
    Statement St = null;

    public Faturas() {
        initComponents();
        refreshClient();
        refreshTabelFac();
        facId.setVisible(false);
        lblEstado.setVisible(false);
        facStatus.setVisible(false);
        lblFactura.setVisible(false);
        limpar();
    }

     //ADD RECIBO
    public void addFactura(){

        String name = facCliente.getSelectedItem().toString();
        String desc = facDesc.getText();
        String estado = "Por pagar";
        String price = facPrice.getText();
        String data = facDate.getText();

        if (name.equals("")|| desc.equals("") || data.equals("") ) {
           JOptionPane.showMessageDialog(null, "preencher todos os campos(*)", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                St = RELC.mycon().createStatement();
                St.executeUpdate("insert into faturas(name, descricao, estado, price, date) values('"+name+"','" + desc + "','"+estado+"' ,'" + price + "', '"+data+"') ");
                JOptionPane.showMessageDialog(null, "Salvo com sucesso");
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar...\nPossivel erro: fatura já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
                System.out.println("Erro adicionar fatura: " + e);
            }
            refreshTabelFac();
            refreshClient();
            limpar();
        }
    }
    
    //EDITA RECIBO
    public void editFatura(){
        
        String facid = facId.getText();
        String name = facCliente.getSelectedItem().toString();
        String estado = "Por pagar";
        String desc = facDesc.getText();
        String price = facPrice.getText();
        String data = facDate.getText();


        if ( name.equals("")|| desc.equals("") || data.equals("")) {
            JOptionPane.showMessageDialog(null, "preencher todos os campos(*)", "Atenção", JOptionPane.WARNING_MESSAGE);
        }  else {
            try {
                St = RELC.mycon().createStatement();
                St.executeUpdate("update faturas set name='" + name + "', descricao='"+desc+"',estado='"+estado+"', price='"+price+"', date='"+data+"' where id='"+facid+"' ");
                JOptionPane.showMessageDialog(null, "Salvo com sucesso");
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar...\nPossivel erro: fatura já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
                System.out.println("Erro editar factura: "+e);
            }
            limpar();
            refreshClient();
            refreshTabelFac();
        }
    }
    
    //DELETE RECIBO
     public void deleteFatura(){
        
        String facid = facId.getText();

        if ( facid.equals("")) {
            JOptionPane.showMessageDialog(null, "selecionar fatura a apagar(*)", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                try {
                    St = RELC.mycon().createStatement();
                    St.executeUpdate("delete from faturas where id='"+facid+"' ");
                   JOptionPane.showMessageDialog(null, "Dados eliminados", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao apagar...\nPossivel erro: base de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Erro apagar fatura: "+e);
                }
                limpar();
                refreshTabelFac();
                refreshClient();
            }
        }
    }
     
      //COMBO Cliente
    public void refreshClient(){
        try {
           
            St = RELC.mycon().createStatement();
            Rs = St.executeQuery("select * from clientes order by name");
            Vector v = new Vector();
            
            while (Rs.next()) {                
                v.add(Rs.getString("name"));
                DefaultComboBoxModel combo = new DefaultComboBoxModel(v);
                facCliente.setModel(combo);
            }
            facCliente.setSelectedIndex(-1);
        } catch (SQLException e) {
            System.out.println("Erro refresh cliente: "+e);
        }
    }
    
    //ATUALIZAR TABELA
    public void refreshTabelFac(){
        try {
            DefaultTableModel dt = (DefaultTableModel) facTable.getModel();
            St = RELC.mycon().createStatement();
            Rs = St.executeQuery("select id as Fatura, date as Data, name as Cliente,descricao as Descrição, estado as Estado, price as Valor from faturas where estado='Por pagar' order by id desc");
            facTable.setModel(DbUtils.resultSetToTableModel(Rs));
       } catch (SQLException e) {
            System.out.println("Erro refresh tabela"+e);
        }
    }
    
    //SETAR TABELA
    public void selectTable(){
        DefaultTableModel model = (DefaultTableModel) facTable.getModel();
        int myindex = facTable.getSelectedRow();
        facId.setText(model.getValueAt(myindex, 0).toString());
        facDate.setText(model.getValueAt(myindex, 1).toString());
        facCliente.setSelectedItem(model.getValueAt(myindex, 2).toString());
        facDesc.setText(model.getValueAt(myindex, 3).toString());
        facStatus.setText(model.getValueAt(myindex, 4).toString());
        facPrice.setText(model.getValueAt(myindex, 5).toString());
        btnSave.setVisible(false);
        btnEdit.setVisible(true);
        btnDelete.setVisible(true);
        btnPrint.setVisible(true);
        facId.setVisible(true);
        lblEstado.setVisible(true);
        lblFactura.setVisible(true);
        facStatus.setVisible(true);
    }
    
    //LIMPAR TUDO
    public void limpar(){
        facId.setText("");
        facDate.setText("");
        facCliente.setSelectedIndex(-1);
        facDesc.setText("");
        facPrice.setText("");
        btnSave.setVisible(true);
        btnEdit.setVisible(false);
        btnDelete.setVisible(false);
        btnPrint.setVisible(false);
        facId.setVisible(false);
        lblEstado.setVisible(false);
        lblFactura.setVisible(false);
        facStatus.setVisible(false);
    }
    
    //IMPRIMIR
    private void imprimirFatura() {
        int confirmar = JOptionPane.showConfirmDialog(null, "Confirma a impressão da fatura?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                //usando a class HassMap para criar um filtro
                HashMap para = new HashMap();
                para.put("id_para", facId.getText());
                ReportView r = new ReportView("build\\classes\\Reports\\facturaId.jasper", para);
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

        FaturaPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        facTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        facCliente = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        facDate = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        facDesc = new javax.swing.JTextField();
        facPrice = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnEdit = new javax.swing.JLabel();
        btnSave = new javax.swing.JLabel();
        btnClear = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        facId = new javax.swing.JTextField();
        btnPrint = new javax.swing.JLabel();
        btnDelete = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        facStatus = new javax.swing.JTextField();
        lblFactura = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        FaturaPanel.setBackground(new java.awt.Color(255, 255, 255));
        FaturaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Faturas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 20), new java.awt.Color(0, 102, 102))); // NOI18N
        FaturaPanel.setPreferredSize(new java.awt.Dimension(800, 500));

        facTable.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        facTable.setForeground(new java.awt.Color(51, 51, 51));
        facTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Data", "Cliente", "Descrição", "Estado", "Preço"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        facTable.setGridColor(new java.awt.Color(204, 204, 204));
        facTable.setSelectionBackground(new java.awt.Color(0, 187, 255));
        facTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                facTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(facTable);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
        });

        facCliente.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        facCliente.setForeground(new java.awt.Color(153, 0, 0));
        facCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cliente Final" }));
        facCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                facClienteMouseClicked(evt);
            }
        });
        facCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                facClienteActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel6.setText("Cliente");

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setText("Data");

        facDate.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        facDate.setForeground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel1.setText("Descrição");

        facDesc.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        facDesc.setForeground(new java.awt.Color(51, 51, 51));

        facPrice.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        facPrice.setForeground(new java.awt.Color(51, 51, 51));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setText("Valor");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(153, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnEdit.setBackground(new java.awt.Color(0, 204, 204));
        btnEdit.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save.png"))); // NOI18N
        btnEdit.setToolTipText("Salvar fatura");
        btnEdit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 204, 204), 2));
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.setOpaque(true);
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });
        jPanel3.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 71, 27));

        btnSave.setBackground(new java.awt.Color(51, 204, 0));
        btnSave.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add.png"))); // NOI18N
        btnSave.setToolTipText("Criar nova fatura");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setOpaque(true);
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
        jPanel3.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 71, 27));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel3)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(facDate, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                            .addComponent(facPrice)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(facDesc)
                            .addComponent(facCliente, 0, 403, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(facCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(facDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(facPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(facDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        facId.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        facId.setForeground(new java.awt.Color(153, 0, 0));
        facId.setEnabled(false);
        facId.setMinimumSize(new java.awt.Dimension(0, 25));
        facId.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel2.add(facId, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 100, 25));

        btnPrint.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/print.png"))); // NOI18N
        btnPrint.setToolTipText("Imprimir fatura");
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrintMouseClicked(evt);
            }
        });
        jPanel2.add(btnPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 10, 50, 30));

        btnDelete.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete.png"))); // NOI18N
        btnDelete.setToolTipText("Apagar fatura");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteMouseClicked(evt);
            }
        });
        jPanel2.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 50, 30));

        lblEstado.setBackground(new java.awt.Color(255, 255, 255));
        lblEstado.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("Estado");
        lblEstado.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel2.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 50, -1));

        facStatus.setBackground(new java.awt.Color(240, 240, 240));
        facStatus.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        facStatus.setForeground(new java.awt.Color(153, 0, 0));
        facStatus.setDisabledTextColor(new java.awt.Color(153, 0, 0));
        facStatus.setEnabled(false);
        facStatus.setMinimumSize(new java.awt.Dimension(0, 25));
        facStatus.setPreferredSize(new java.awt.Dimension(0, 25));
        jPanel2.add(facStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 100, 25));

        lblFactura.setBackground(new java.awt.Color(255, 255, 255));
        lblFactura.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        lblFactura.setText("Factura Nº");
        lblFactura.setPreferredSize(new java.awt.Dimension(49, 25));
        jPanel2.add(lblFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 10, 70, -1));

        javax.swing.GroupLayout FaturaPanelLayout = new javax.swing.GroupLayout(FaturaPanel);
        FaturaPanel.setLayout(FaturaPanelLayout);
        FaturaPanelLayout.setHorizontalGroup(
            FaturaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FaturaPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(FaturaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 715, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        FaturaPanelLayout.setVerticalGroup(
            FaturaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FaturaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FaturaPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 760, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(FaturaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void facTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_facTableMouseClicked
        selectTable();
    }//GEN-LAST:event_facTableMouseClicked

    private void facClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_facClienteMouseClicked
        
    }//GEN-LAST:event_facClienteMouseClicked

    private void facClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_facClienteActionPerformed

    }//GEN-LAST:event_facClienteActionPerformed

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        editFatura();
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        addFactura();
    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnClearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClearMouseClicked
        limpar();
        refreshTabelFac();
    }//GEN-LAST:event_btnClearMouseClicked

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        limpar();
    }//GEN-LAST:event_jPanel1MouseClicked

    private void btnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrintMouseClicked
        imprimirFatura();
    }//GEN-LAST:event_btnPrintMouseClicked

    private void btnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteMouseClicked
        deleteFatura();
    }//GEN-LAST:event_btnDeleteMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel FaturaPanel;
    private javax.swing.JLabel btnClear;
    private javax.swing.JLabel btnDelete;
    private javax.swing.JLabel btnEdit;
    private javax.swing.JLabel btnPrint;
    private javax.swing.JLabel btnSave;
    private javax.swing.JComboBox<String> facCliente;
    private javax.swing.JTextField facDate;
    private javax.swing.JTextField facDesc;
    private javax.swing.JTextField facId;
    private javax.swing.JTextField facPrice;
    private javax.swing.JTextField facStatus;
    private javax.swing.JTable facTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFactura;
    // End of variables declaration//GEN-END:variables
}
