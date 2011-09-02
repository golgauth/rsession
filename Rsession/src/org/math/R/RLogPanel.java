package org.math.R;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

/**
 *
 * @author  richet
 */
public class RLogPanel extends JPanel implements Logger {

    private static int _fontSize = 12;
    private static Font _smallFont = new Font("Arial", Font.PLAIN, _fontSize - 2);
    public long maxsize = 100000;
    public long minsize = 10000;

    public void println(final String message, Level l) {
        //if (isVisible()) {
        //    EventQueue.invokeLater(new Runnable() {
        //
        //       public void run() {
        if (l == Level.INFO) {
            getInfoPrintStream().println(message);
        } else if (l == Level.WARNING) {
            getWarnPrintStream().println(message);
        } else if (l == Level.ERROR) {
            getErrorPrintStream().println(message);
        }
        //       }
        //   });
        //}

    }

    public RLogPanel() {
        initComponents();

        StyleConstants.setForeground(jTextPane1.addStyle("INFO", null), Color.black);
        StyleConstants.setForeground(jTextPane1.addStyle("WARN", null), Color.blue);
        StyleConstants.setForeground(jTextPane1.addStyle("ERROR", null), Color.red);

        jTextPane1.setEditable(false);
        jTextPane1.setFont(_smallFont);
    }
    private OutputStream info_stream;
    private OutputStream error_stream;
    private OutputStream warn_stream;

    public void close() {
        if (info_stream != null) {
            try {
                info_stream.close();
            } catch (IOException ex) {
            }
        }
        if (error_stream != null) {
            try {
                error_stream.close();
            } catch (IOException ex) {
            }
        }
        if (warn_stream != null) {
            try {
                warn_stream.close();
            } catch (IOException ex) {
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    OutputStream getInfoStream() {
        if (info_stream == null) {
            info_stream = new OutputStream() {

                @Override
                public void write(int b) throws IOException {
                    try {
                        if (jTextPane1.getDocument().getLength() > maxsize) {
                            jTextPane1.getDocument().remove(0, (int) (maxsize - minsize));
                        }
                        jTextPane1.getDocument().insertString(jTextPane1.getDocument().getLength(), "" + (char) b, jTextPane1.getStyle("INFO"));
                        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        return info_stream;
    }

    OutputStream getWarnStream() {
        if (warn_stream == null) {
            warn_stream = new OutputStream() {

                @Override
                public void write(int b) throws IOException {
                    try {
                        jTextPane1.getDocument().insertString(jTextPane1.getDocument().getLength(), "" + (char) b, jTextPane1.getStyle("WARN"));
                        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        return warn_stream;
    }

    OutputStream getErrorStream() {
        if (error_stream == null) {
            error_stream = new OutputStream() {

                @Override
                public void write(int b) throws IOException {
                    try {
                        jTextPane1.getDocument().insertString(jTextPane1.getDocument().getLength(), "" + (char) b, jTextPane1.getStyle("ERROR"));
                        jTextPane1.setCaretPosition(jTextPane1.getDocument().getLength());
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            };
        }
        return error_stream;
    }
    private PrintStream info_pstream;
    private PrintStream warn_pstream;
    private PrintStream error_pstream;

    public PrintStream getInfoPrintStream() {
        if (info_pstream == null) {
            //FIXME: need to use the EventQueue for non-blocking printing
            info_pstream = new PrintStream(getInfoStream());
        }
        return info_pstream;
    }

    public PrintStream getWarnPrintStream() {
        if (warn_pstream == null) {
            //FIXME: need to use the EventQueue for non-blocking printing
            warn_pstream = new PrintStream(getWarnStream());
        }
        return warn_pstream;
    }

    public PrintStream getErrorPrintStream() {
        if (error_pstream == null) {
            //FIXME: need to use the EventQueue for non-blocking printing
            error_pstream = new PrintStream(getErrorStream());
        }
        return error_pstream;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        _bar = new javax.swing.JToolBar();
        _del = new javax.swing.JButton();
        _save = new javax.swing.JButton();

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setViewportView(jTextPane1);

        _bar.setFloatable(false);
        _bar.setOrientation(1);
        _bar.setRollover(true);

        _del.setText("Clear");
        _del.setToolTipText("Remove R object");
        _del.setFocusable(false);
        _del.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        _del.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        _del.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _delActionPerformed(evt);
            }
        });
        _bar.add(_del);

        _save.setText("Save");
        _save.setToolTipText("Remove R object");
        _save.setFocusable(false);
        _save.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        _save.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        _save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _saveActionPerformed(evt);
            }
        });
        _bar.add(_save);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(_bar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
            .addComponent(_bar, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__delActionPerformed
        jTextPane1.setText("");
}//GEN-LAST:event__delActionPerformed

    private void _saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__saveActionPerformed
        JFileChooser fc = new JFileChooser(new File("R.log"));
        if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION && fc.getSelectedFile() != null) {
            FileOutputStream os = null;
            try {
                os = new FileOutputStream(fc.getSelectedFile());
                os.write(jTextPane1.getText().getBytes());
                os.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
}//GEN-LAST:event__saveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar _bar;
    public javax.swing.JButton _del;
    public javax.swing.JButton _save;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
