package htmlviewer;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import org.gjt.sp.jedit.EditBus;
import org.gjt.sp.jedit.GUIUtilities;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.gui.EnhancedDialog;
import org.gjt.sp.jedit.msg.PropertiesChanged;


public class BookmarksDialog extends EnhancedDialog {

	private static final long serialVersionUID = 1504675651662267292L;
	private JTable table;
    private JButton bOk, bCancel, bAdd, bDelete, bMoveUp, bMoveDown;
    private Bookmarks model;

    public BookmarksDialog(Frame parent) {
        super(parent, jEdit.getProperty("htmlviewer.bdialog.title"), true);

        model = new Bookmarks();
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scroller = new JScrollPane(table);

        bOk       = new JButton(jEdit.getProperty("htmlviewer.bdialog.ok"));
        bCancel   = new JButton(jEdit.getProperty("htmlviewer.bdialog.cancel"));
        bAdd      = new JButton(jEdit.getProperty("htmlviewer.bdialog.add"));
        bDelete   = new JButton(jEdit.getProperty("htmlviewer.bdialog.delete"));
        bMoveUp   = new JButton(jEdit.getProperty("htmlviewer.bdialog.moveup"));
        bMoveDown = new JButton(jEdit.getProperty("htmlviewer.bdialog.movedown"));

        ActionHandler ah = new ActionHandler();
        bOk.addActionListener(ah);
        bCancel.addActionListener(ah);
        bAdd.addActionListener(ah);
        bDelete.addActionListener(ah);
        bMoveUp.addActionListener(ah);
        bMoveDown.addActionListener(ah);

        Box south = Box.createHorizontalBox();
        south.add(Box.createHorizontalGlue());
        south.add(bOk);
        south.add(Box.createHorizontalStrut(20));
        south.add(bCancel);
        south.add(Box.createHorizontalGlue());

        JPanel buttons = new JPanel(new GridLayout(0,1,5,5));
        buttons.add(bAdd);
        buttons.add(bDelete);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(bMoveUp);
        buttons.add(bMoveDown);

        JPanel east = new JPanel();
        east.add(buttons, BorderLayout.NORTH);
        east.add(new JPanel(), BorderLayout.CENTER); // don't ask

        getContentPane().setLayout(new BorderLayout(10,10));
        getContentPane().add(scroller, BorderLayout.CENTER);
        getContentPane().add(south, BorderLayout.SOUTH);
        getContentPane().add(east, BorderLayout.EAST);

        getRootPane().setDefaultButton(bOk);
        getRootPane().setBorder(BorderFactory.createMatteBorder(10,10,10,10, UIManager.getColor("Panel.background")));

        setSize(500,300);
        GUIUtilities.loadGeometry(this, "htmlviewer.bdialog");
        setLocationRelativeTo(parent);
        setVisible(true);
    }


    public void ok() {
        model.save();
        GUIUtilities.saveGeometry(this, "htmlviewer.bdialog");
        EditBus.send(new PropertiesChanged(null));
        setVisible(false);
    }


    public void cancel() {
        GUIUtilities.saveGeometry(this, "htmlviewer.bdialog");
        setVisible(false);
    }


    private class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            JButton button = (JButton) evt.getSource();
            if (button == bAdd) {
                model.add(new TitledURLEntry("", ""));
            }
            else if (button == bDelete) {
                int rows[] = table.getSelectedRows();
                if (rows.length == 0) {
                    GUIUtilities.error(null, "htmlviewer.error.bdialog.noselection", null);
                } else {
                    for (int i = rows.length - 1; i >= 0; i--) {
                        model.delete(rows[i]);
                    }
                }
            }
            else if (button == bMoveUp) {
                int rows[] = table.getSelectedRows();
                if (rows.length == 0) {
                    GUIUtilities.error(null, "htmlviewer.error.bdialog.noselection", null);
                } else if (rows.length > 1) {
                    GUIUtilities.error(null, "htmlviewer.error.bdialog.selecttoomuch", null);
                } else if (rows[0] > 0) {
                    model.moveup(rows[0]);
                    table.setRowSelectionInterval(rows[0]-1, rows[0]-1);
                }
            }
            else if (button == bMoveDown) {
                int rows[] = table.getSelectedRows();
                if (rows.length == 0) {
                    GUIUtilities.error(null, "htmlviewer.error.bdialog.noselection", null);
                } else if (rows.length > 1) {
                    GUIUtilities.error(null, "htmlviewer.error.bdialog.selecttoomuch", null);
                } else if (rows[0] < model.getRowCount()-1) {
                    model.movedown(rows[0]);
                    table.setRowSelectionInterval(rows[0]+1, rows[0]+1);
                }
            }
            else if (button == bOk) {
                ok();
            }
            else if (button == bCancel) {
                cancel();
            }
        }
    } // inner class ActionHandler

}

