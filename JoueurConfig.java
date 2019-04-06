/*
 * INF4230 - Intelligence artificielle
 * UQAM - Département d'informatique
 */

package Connect5Game;

import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Connect5Game.JoueurArtificiel;

/**
 *
 */
public class JoueurConfig extends javax.swing.JPanel {

    /** Creates new form JoueurConfig */
    public JoueurConfig() {
        initComponents();
    }

    public Joueur getJoueur(){
        if(manuelRB.isSelected())
            return null;
        if(interneRB.isSelected())
            return new JoueurArtificiel();
      
        return null;

    }

    static JFileChooser filechooser = new JFileChooser();

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    java.awt.GridBagConstraints gridBagConstraints;

    jButton1 = new javax.swing.JButton();
    jTextField1 = new javax.swing.JTextField();
    buttonGroup1 = new javax.swing.ButtonGroup();
    manuelRB = new javax.swing.JRadioButton();
    interneRB = new javax.swing.JRadioButton();

    jButton1.setText("jButton1");

    jTextField1.setText("jTextField1");

    setMinimumSize(new java.awt.Dimension(439, 111));
    setLayout(new java.awt.GridBagLayout());

    buttonGroup1.add(manuelRB);
    manuelRB.setSelected(true);
    manuelRB.setText("Manuel");
    manuelRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        manuelRBActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    add(manuelRB, gridBagConstraints);

    buttonGroup1.add(interneRB);
    interneRB.setText("Artificiel");
    interneRB.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        interneRBActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.gridwidth = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    add(interneRB, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

    private void manuelRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manuelRBActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_manuelRBActionPerformed

    private void interneRBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_interneRBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_interneRBActionPerformed



  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.ButtonGroup buttonGroup1;
  public javax.swing.JRadioButton interneRB;
  private javax.swing.JButton jButton1;
  private javax.swing.JTextField jTextField1;
  public javax.swing.JRadioButton manuelRB;
  // End of variables declaration//GEN-END:variables

}