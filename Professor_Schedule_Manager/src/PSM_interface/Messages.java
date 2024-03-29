/*
 * Messages.java
 *
 * Created on April 15, 2008, 3:54 PM
 */

package PSM_interface;

/**
 *
 * @author  Kurt
 */
public class Messages extends javax.swing.JFrame {
    private static String m = "";
    private static boolean logout;
    private static boolean incorrectLogin;
    public static boolean ack;
    private static boolean lockedOut;
    /** Creates new form Messages */
    public Messages() {
        initComponents();
    }
   
    public void lockedOut()
    {
        m = "Too many tries. System Exiting.";
        lockedOut = true;
        launchPopup();
    }
    public void incorrectLogin()
    {
        incorrectLogin = true;
        m = "Incorrect username/password";
        launchPopup();
    }
    public void FifteenMinWarning()
    {
        m = "15 Minutes Left";
        launchPopup();
    }

    public void FiveMinWarning()
    {
        m = "5 Minutes Left";
        launchPopup();
    }
    
    public void endClassWarning()
    {
        m = "End of Class Reached";
        launchPopup();
        
        
    }
    
    public void logoutConfirmation()
    {
        m = "You have sucessfully logged out.";
        logout = true;
        launchPopup();
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("System Message");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(m);
        jLabel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Warning", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 1, 12), new java.awt.Color(0, 0, 255)));
        jLabel1.setMaximumSize(new java.awt.Dimension(200, 100));
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 50));
        jLabel1.setName("Message"); // NOI18N
        jLabel1.setOpaque(true);
        jLabel1.setPreferredSize(new java.awt.Dimension(200, 50));

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(26, 26, 26)
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(95, 95, 95)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(39, 39, 39)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 59, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jButton1)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(logout){
            System.exit(0);
            System.out.println("Close the program!");
        }
        if(incorrectLogin)
        {
            ack = true;
        }
        if(lockedOut)
        {
            ack = true;
        }
        dispose();
        
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public void launchPopup() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Messages().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    
}
