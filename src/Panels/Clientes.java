
package Panels;

import RelcCon.RELC;
import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

public class Clientes extends javax.swing.JPanel {
    
    ResultSet Rs = null;
    Statement St = null;

    public Clientes() {
        initComponents();
        refreshTabelCli();
        limpar();
    }

    //ADD CLIENTE
    public void addCli(){
        String nome = cliName.getText();

        if (nome.equals("")) {
           JOptionPane.showMessageDialog(null, "Inserir nome do cliente(*)", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                St = RELC.mycon().createStatement();
                St.executeUpdate("insert into clientes(name) values('" + nome + "') ");
                JOptionPane.showMessageDialog(null, "Salvo com sucesso");
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao salvar...\nPossivel erro: Cliente já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
                System.out.println(e);
            }
            limpar();
            refreshTabelCli();
        }
    }
    
    //ATUALIZAR
    public void editCli(){
        String id = cliId.getText();
        String nome = cliName.getText();

        if ( id.equals("ID")||nome.equals("")) {
            JOptionPane.showMessageDialog(null, "Inserir nome do cliente(*)", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
             int confirma = JOptionPane.showConfirmDialog(null, "Confirmar alteração?", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                try {
                    St = RELC.mycon().createStatement();
                    St.executeUpdate("update clientes set name='" + nome + "' where id='"+id+"' ");
                    JOptionPane.showMessageDialog(null, "Salvo com sucesso");
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar...\nPossivel erro: Cliente já existe.", "Erro", JOptionPane.ERROR_MESSAGE);
                    System.out.println(e);
                }
                limpar();
                refreshTabelCli();
            }
        }
    }
    
    //APAGAR
    public void deleteCli(){
        
        String id = cliId.getText();

        if ( id.equals("ID")) {
            JOptionPane.showMessageDialog(null, "selecionar cliente a apagar(*)", "Atenção", JOptionPane.WARNING_MESSAGE);
        } else {
            int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir", "Atenção", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                try {
                    St = RELC.mycon().createStatement();
                    St.executeUpdate("delete from clientes where id='"+id+"' ");
                   JOptionPane.showMessageDialog(null, "Dados eliminados", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao apagar...\nPossivel erro: base de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                    System.out.println(e);
                }
                limpar();
                refreshTabelCli();
            }
        }
    }
    
    //=================TABELA CLIENTE=======================
    //ATUALIZAR
    public void refreshTabelCli(){
        try {
            DefaultTableModel dt = (DefaultTableModel) clienteTb.getModel();
            St = RELC.mycon().createStatement();
            Rs = St.executeQuery("select * from clientes");
            clienteTb.setModel(DbUtils.resultSetToTableModel(Rs));
       } catch (SQLException e) {
            System.out.println(e);
        }
    }
    //SETAR TABELA
    public void selectTable(){
        DefaultTableModel model = (DefaultTableModel) clienteTb.getModel();
        int myindex = clienteTb.getSelectedRow();
        cliId.setText(model.getValueAt(myindex, 0).toString());
        cliName.setText(model.getValueAt(myindex, 1).toString());
        cliId.setVisible(true);
        btnAdd.setVisible(false);
        btnDelete.setVisible(true);
        btnEdit.setVisible(true);
    }
    
    public void limpar(){
        cliName.setText("");
        cliName.requestFocus();
        cliId.setVisible(false);
        btnAdd.setVisible(true);
        btnDelete.setVisible(false);
        btnEdit.setVisible(false);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        clienteTb = new javax.swing.JTable();
        cliId = new javax.swing.JLabel();
        cliName = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Clientes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 20), new java.awt.Color(0, 102, 102))); // NOI18N
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        clienteTb.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        clienteTb.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        clienteTb.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome"
            }
        ));
        clienteTb.setSelectionBackground(new java.awt.Color(0, 187, 255));
        clienteTb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clienteTbMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(clienteTb);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 700, 350));

        cliId.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        cliId.setForeground(new java.awt.Color(51, 51, 51));
        cliId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cliId.setText("ID");
        jPanel2.add(cliId, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 52, 27));

        cliName.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        cliName.setForeground(new java.awt.Color(51, 51, 51));
        jPanel2.add(cliName, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 410, -1));

        btnAdd.setBackground(new java.awt.Color(51, 204, 0));
        btnAdd.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnAdd.setForeground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add.png"))); // NOI18N
        btnAdd.setToolTipText("Salvar");
        btnAdd.setBorder(null);
        btnAdd.setBorderPainted(false);
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel2.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 100, 27));

        btnEdit.setBackground(new java.awt.Color(0, 187, 255));
        btnEdit.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnEdit.setForeground(new java.awt.Color(255, 255, 255));
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save.png"))); // NOI18N
        btnEdit.setToolTipText("Salvar alteração");
        btnEdit.setBorder(null);
        btnEdit.setBorderPainted(false);
        btnEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditMouseClicked(evt);
            }
        });
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel2.add(btnEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, 100, 28));

        btnDelete.setBackground(new java.awt.Color(255, 51, 51));
        btnDelete.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/deleteW.png"))); // NOI18N
        btnDelete.setToolTipText("Apagar");
        btnDelete.setBorder(null);
        btnDelete.setBorderPainted(false);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel2.add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 70, 97, 28));

        jLabel1.setBackground(new java.awt.Color(255, 153, 0));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh.png"))); // NOI18N
        jLabel1.setToolTipText("Limpar tudo");
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(685, 30, 30, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 478, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clienteTbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clienteTbMouseClicked
        selectTable();
    }//GEN-LAST:event_clienteTbMouseClicked

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addCli();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditMouseClicked
        editCli();
    }//GEN-LAST:event_btnEditMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteCli();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        limpar();
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        limpar();
    }//GEN-LAST:event_jLabel1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JLabel cliId;
    private javax.swing.JTextField cliName;
    private javax.swing.JTable clienteTb;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
