/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author richard
 */
import AST.ASTNodo;
import CHTML.*;
import java.io.FileReader;
import javax.swing.JOptionPane;
import CHTML_EXEC.Interprete_CHTML;
public class Pestania extends javax.swing.JPanel {

    /**
     * Creates new form Pestania
     */
    
    
    public Pestania() {
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

        jPanel1 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        btnForward = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        URL_Search = new javax.swing.JTextField();
        btnExec = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        MainPageTab = new javax.swing.JTabbedPane();
        Principal = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        btnBack.setIcon(new javax.swing.ImageIcon("/home/richard/Documents/Universidad/Compiladores 2/USAC_WEB/IMGS/if_arrow_full_left_103292.png")); // NOI18N
        btnBack.setMaximumSize(new java.awt.Dimension(50, 50));
        btnBack.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel1.add(btnBack);

        btnForward.setIcon(new javax.swing.ImageIcon("/home/richard/Documents/Universidad/Compiladores 2/USAC_WEB/IMGS/if_arrow_full_right_103295.png")); // NOI18N
        btnForward.setMaximumSize(new java.awt.Dimension(50, 50));
        btnForward.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel1.add(btnForward);

        btnRefresh.setIcon(new javax.swing.ImageIcon("/home/richard/Documents/Universidad/Compiladores 2/USAC_WEB/IMGS/if_refresh22_216527.png")); // NOI18N
        btnRefresh.setMaximumSize(new java.awt.Dimension(50, 50));
        btnRefresh.setPreferredSize(new java.awt.Dimension(50, 50));
        jPanel1.add(btnRefresh);

        URL_Search.setFont(new java.awt.Font("Ubuntu Condensed", 0, 24)); // NOI18N
        URL_Search.setToolTipText("URL...");
        jPanel1.add(URL_Search);

        btnExec.setIcon(new javax.swing.ImageIcon("/home/richard/Documents/Universidad/Compiladores 2/USAC_WEB/IMGS/if_11_Search_106236.png")); // NOI18N
        btnExec.setMaximumSize(new java.awt.Dimension(50, 50));
        btnExec.setPreferredSize(new java.awt.Dimension(100, 50));
        btnExec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecActionPerformed(evt);
            }
        });
        jPanel1.add(btnExec);

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setForeground(new java.awt.Color(102, 102, 102));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 48)); // NOI18N
        jLabel1.setText("USAC WEB");
        jPanel2.add(jLabel1);

        javax.swing.GroupLayout PrincipalLayout = new javax.swing.GroupLayout(Principal);
        Principal.setLayout(PrincipalLayout);
        PrincipalLayout.setHorizontalGroup(
            PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
        );
        PrincipalLayout.setVerticalGroup(
            PrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );

        MainPageTab.addTab("Contenido", Principal);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );

        MainPageTab.addTab("Consola de Salida", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );

        MainPageTab.addTab("Consola de Errores", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );

        MainPageTab.addTab("CCSS", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );

        MainPageTab.addTab("CHTML", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1033, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 431, Short.MAX_VALUE)
        );

        MainPageTab.addTab("CJS", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(MainPageTab)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MainPageTab))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnExecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecActionPerformed
        // BOTON QUE INICIA LA INTERPTRETACION DEL CHTML
        ASTNodo raizCHTML = null;
        try 
        {
            LexCHTML lex = new LexCHTML(new FileReader(URL_Search.getText()));
            parser p = new parser(lex);
            p.parse();
            if(parser.raiz!=null)
            {
                raizCHTML = parser.raiz;
                //EJECUTA LA ACCION DE PARSEAR EL CHTML
                System.out.println(raizCHTML.graficaAST(raizCHTML));
                Interprete_CHTML nuevo = new Interprete_CHTML(raizCHTML, Principal);
                nuevo.InicioCHTML();
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Se encontraron muchos errores en el archivo, revisa la sintaxis...");
            }
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Error al Intentar acceder a la Pagina: "+URL_Search.getText());
        }
    }//GEN-LAST:event_btnExecActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane MainPageTab;
    private javax.swing.JPanel Principal;
    private javax.swing.JTextField URL_Search;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnExec;
    private javax.swing.JButton btnForward;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    // End of variables declaration//GEN-END:variables
}