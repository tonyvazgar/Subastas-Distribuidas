/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente.View;

import Cliente.Cliente;
import Cliente.Utilidades;
import Model.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author luisluna
 */
public class AceptarSubasta extends javax.swing.JFrame {

    /**
     * Creates new form AceptarSubasta
     */
    public AceptarSubasta() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        vender = new javax.swing.JButton();
        subastas = new javax.swing.JComboBox<>();
        oferton = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        comprador = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Aceptar Subasta");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("DecoType Naskh", 0, 18)); // NOI18N
        jLabel1.setText("Subasta:");

        jLabel2.setFont(new java.awt.Font("DecoType Naskh", 0, 18)); // NOI18N
        jLabel2.setText("Ultima Oferta:");

        vender.setFont(new java.awt.Font("DecoType Naskh", 0, 18)); // NOI18N
        vender.setText("Vender");
        vender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                venderActionPerformed(evt);
            }
        });

        subastas.setFont(new java.awt.Font("DecoType Naskh", 0, 18)); // NOI18N
        subastas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subastasActionPerformed(evt);
            }
        });

        oferton.setEditable(false);
        oferton.setFont(new java.awt.Font("DecoType Naskh", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("DecoType Naskh", 0, 18)); // NOI18N
        jLabel3.setText("Posible Comprador:");

        comprador.setEditable(false);
        comprador.setFont(new java.awt.Font("DecoType Naskh", 0, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(oferton)
                            .addComponent(comprador, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(subastas, 0, 230, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(vender, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(subastas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(oferton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comprador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(vender)
                .addGap(69, 69, 69))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        actualizarSubastas();
    }//GEN-LAST:event_formWindowOpened

    private void subastasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subastasActionPerformed
        // TODO add your handling code here:
        SubastaInt miSubasta = null;

        if (subastas.getSelectedIndex() == 0) {
            return;
        }

        try {
            String nombreSubasta = subastas.getSelectedItem().toString();

            for (SubastaInt s : Cliente.stub.subastas()) {
                if (nombreSubasta.equals(s.getProducto().getNombre())) {
                    miSubasta = s;
                    break;
                }

            }

            if (miSubasta != null) {
                //Encontro la subasta

                if (miSubasta.getUltimaOferta() != -1.0) {
                    oferton.setText(String.valueOf(miSubasta.getOfertas().getLast().oferta));
                    comprador.setText(miSubasta.getOfertas().getLast().ofertante.getNombre());

                } else {
                    oferton.setText("---");
                    comprador.setText("---");
                }

            }

        } catch (RemoteException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_subastasActionPerformed

    private void venderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_venderActionPerformed
        // TODO add your handling code here:
        boolean ready = true;
        SubastaInt subasta = null;
        if (subastas.getSelectedIndex() == 0) {
            ready = false;
            Utilidades.showWarning("Seleccione una subasta");
            return;
        }

        if (oferton.getText().equals("---")) {
            ready = false;
            Utilidades.showWarning("No hay ofertas para esta subasta");
        }

        if (ready) {
            //Buscar la subasta seleccionada
            String sub = subastas.getSelectedItem().toString();
            try {

                for (SubastaInt s : Cliente.stub.subastas()) {
                    if (s.getProducto().getNombre().equals(sub)) {
                        subasta = s;
                        break;
                    }

                }
                
                if (subasta != null) { //subasta encontrada
                    
                    UsuarioInt comprador = subasta.getOfertas().getLast().ofertante;
                    double precio = subasta.getUltimaOferta();
                    ProductoInt producto = subasta.getProducto();
                    
                    producto.setPrecio(subasta.getUltimaOferta());
                    
                    subasta.getProducto().getDueno().vender(producto);
                    
                    Cliente.stub.comprarProducto(producto, comprador);
                    
                    Cliente.stub.eliminarSubasta(subasta);
                    
                    
                    Utilidades.showMessage("Producto Vendido");
                    this.dispose();
                    
                    
                    
                    
                }
                
                
                
                
            } catch (Exception ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }//GEN-LAST:event_venderActionPerformed

    private void actualizarSubastas() {

        System.out.println("Actualizando Subastas...");
        try {

            DefaultComboBoxModel model = (DefaultComboBoxModel) subastas.getModel();
            model.addElement("Seleccione una subasta");

            for (SubastaInt s : Cliente.stub.subastas()) {

                System.out.println("Subasta en for: " + s.getProducto().getNombre());

                if (s.getProducto().getDueno().getUsuario().equals(Cliente.usuario.getUsuario())) {

                    model.addElement(s.getProducto().getNombre());

                    //model.addRow(new Object[]{s.getProducto().getNombre(), s.getPrecio(),s.getProducto().getDueno().getNombre(), lastOffer, status, new JButton("Ofertar") });
                    System.out.println(s.getProducto().getNombre());
                }

            }

        } catch (RemoteException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void subastasCambiado() {

    }

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(AceptarSubasta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AceptarSubasta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AceptarSubasta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AceptarSubasta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AceptarSubasta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField comprador;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField oferton;
    private javax.swing.JComboBox<String> subastas;
    private javax.swing.JButton vender;
    // End of variables declaration//GEN-END:variables
}
