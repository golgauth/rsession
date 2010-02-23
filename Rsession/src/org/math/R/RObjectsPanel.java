package org.math.R;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;

/**
 *
 * @author  richet
 */
public class RObjectsPanel extends javax.swing.JPanel implements UpdateObjectsListener {

    private RObjectsModel _model;
    private LinkedList<File> Rfiles = new LinkedList<File>();
    private static int _fontSize = 12;
    private static Font _smallFont = new Font("Arial", Font.PLAIN, _fontSize - 2);
    TypeCellRenderer typerenderer = new TypeCellRenderer();
    ObjectCellRenderer objectrenderer = new ObjectCellRenderer();

    enum ObjectColumns {

        NAME(0, 100, "Object"),
        TYPE(1, 100, "Type");
        //SOURCE(1, 100, "Source", new CellRenderer());
        String name;
        int value, width;

        ObjectColumns(int v, int w, String n) {
            value = v;
            width = w;
            name = n;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    class TypeCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object name, boolean isSelected, boolean hasFocus, int row, int col) {
            super.getTableCellRendererComponent(table, name, isSelected, hasFocus, row, col);
            setText((String) name);
            setFont(_smallFont);
            setHorizontalAlignment(CENTER);
            return this;
        }
    }

    class ObjectCellRenderer extends TypeCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object name, boolean isSelected, boolean hasFocus, int row, int col) {
            super.getTableCellRendererComponent(table, name, isSelected, hasFocus, row, col);
            String ttip = "?";
            try {
                ttip = R.eval("print(" + name.toString() + ")").asString();
            } catch (Exception re) {
            }
            setToolTipText(ttip);
            return this;
        }
    }
    String[] ls = new String[0];
    HashMap<String, String> typeOf = new HashMap<String, String>() {

        @Override
        public String get(Object key) {
            if (key == null) {
                return null;
            }
            String keystr = (String) key;
            if (!super.containsKey(keystr)) {
                super.put(keystr, R == null ? "" : R.typeOf(keystr));
            }
            return super.get(keystr);
        }
    };

    public class RObjectsModel extends DefaultTableModel {

        public RObjectsModel() {
            super(ObjectColumns.values(), 0);
        }

        @Override
        public int getRowCount() {
            //int ls = R.silentlyEval("length(ls())").asInt();
            //System.out.println(ls+" lines");
            return ls.length;
        }

        @Override
        public Object getValueAt(int row, int col) {
            //String oname = R.silentlyEval("ls()").asStringArray()[row];
            if (col == ObjectColumns.NAME.value) {
                //return oname;
                return ls[row];
            }
            if (col == ObjectColumns.TYPE.value) {
                //return R.typeOf(oname);
                return typeOf.get(ls[row]);
            }
            return null;
        }

        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }

        @Override
        public Class getColumnClass(int col) {
            return String.class;
        }
    }
    Rsession R;

    public void setTarget(Rsession r) {
        R = r;
    }

    /** Creates new form OutputListPanel */
    public RObjectsPanel(Rsession r) {
        setTarget(r);

        initComponents();

        _model = (RObjectsModel) _oList.getModel();

        _oList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        _oList.getTableHeader().setFont(_smallFont);
        _oList.getTableHeader().setReorderingAllowed(false);
        for (ObjectColumns col : ObjectColumns.values()) {
            _oList.getColumnModel().getColumn(col.value).setPreferredWidth(col.width);
        }
        _oList.getColumnModel().getColumn(ObjectColumns.NAME.value).setCellRenderer(objectrenderer);
        _oList.getColumnModel().getColumn(ObjectColumns.TYPE.value).setCellRenderer(typerenderer);

        _oList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        _oList.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //R.eval("help(htmlhelp=TRUE," + _oList.getValueAt(_oList.getSelectedRow(), 0) + ")");
                }
            }
        });
    }

    public void update() {
        try {
            if (R == null) {
                ls = new String[0];
            } else {
                REXP rls = R.silentlyEval("ls()");
                if (rls != null) {
                    ls = rls.asStrings();
                } else {
                    ls = new String[0];
                }
            }
            EventQueue.invokeLater(new Runnable() {

                public void run() {
                    _model.fireTableDataChanged();
                }
            });
        } catch (REXPMismatchException ex) {
            ex.printStackTrace();
        }
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
        _oList = new javax.swing.JTable();
        _bar = new javax.swing.JToolBar();
        _add = new javax.swing.JButton();
        _del = new javax.swing.JButton();
        _save = new javax.swing.JButton();

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        _oList.setAutoCreateRowSorter(true);
        _oList.setModel(new RObjectsModel());
        jScrollPane1.setViewportView(_oList);

        _bar.setFloatable(false);
        _bar.setOrientation(1);
        _bar.setRollover(true);

        _add.setText("Add");
        _add.setToolTipText("Add R object");
        _add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _addActionPerformed(evt);
            }
        });
        _bar.add(_add);

        _del.setText("Delete");
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
            .addComponent(_bar, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void _addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__addActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".R") || f.getName().endsWith(".Rdata");
            }

            @Override
            public String getDescription() {
                return "R object file";
            }
        });
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION && fc.getSelectedFiles() != null) {
            File[] files = fc.getSelectedFiles();
            for (File file : files) {
                //System.out.println("+ " + file.getName());
                if (file.getName().endsWith(".R")) {
                    if (R != null) {
                        R.source(file);
                    }
                } else if (file.getName().endsWith(".Rdata")) {
                    if (R != null) {
                        R.load(file);
                    }
                } else {
                    System.err.println("Not loading/sourcing " + file.getName());
                }
            }
        }
        update();
}//GEN-LAST:event__addActionPerformed

    private void _delActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__delActionPerformed
        int[] i = _oList.getSelectedRows();
        String[] o = new String[i.length];
        for (int j = 0; j < i.length; j++) {
            o[j] = (String) _oList.getValueAt(i[j], 0);
        }
        if (R != null) {
            R.rm(o);
        }
        update();
}//GEN-LAST:event__delActionPerformed

    private void _saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__saveActionPerformed
        int[] i = _oList.getSelectedRows();
        String[] o = new String[i.length];
        for (int j = 0; j < i.length; j++) {
            o[j] = (String) _oList.getValueAt(i[j], 0);
        }
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("R data file", "Rdata"));
        if (R != null) {
            fc.setSelectedFile(new File(R.cat("_", o) + ".Rdata"));
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION && fc.getSelectedFile() != null) {
                R.save(fc.getSelectedFile(), o);
            }
        }
}//GEN-LAST:event__saveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton _add;
    private javax.swing.JToolBar _bar;
    public javax.swing.JButton _del;
    private javax.swing.JTable _oList;
    public javax.swing.JButton _save;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
