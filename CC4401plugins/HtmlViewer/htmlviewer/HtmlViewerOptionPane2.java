/*
 * HtmlViewerOptionPane2.java - second HtmlViewer options panel
 * Copyright (C) 2001 Dirk Moebius
 *
 * :tabSize=4:indentSize=4:noTabs=true:maxLineLen=0:
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package htmlviewer;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.gjt.sp.jedit.AbstractOptionPane;
import org.gjt.sp.jedit.jEdit;


public class HtmlViewerOptionPane2 extends AbstractOptionPane implements ActionListener {

	private static final long serialVersionUID = 8785488835232627553L;
	public HtmlViewerOptionPane2() {
        super("internalBrowser");
    }


    public void _init() {
        // "Appearance"
        addSeparator("options.htmlviewer.appearance.label");

        // "Show ..."
        showFloatingMenu = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.appearance.floating.showMenu"),
            jEdit.getBooleanProperty("htmlviewer.appearance.floating.showMenu"));
        showFloatingToolbar = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.appearance.floating.showToolbar"),
            jEdit.getBooleanProperty("htmlviewer.appearance.floating.showToolbar"));
        showFloatingAddressbar = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.appearance.floating.showAddressbar"),
            jEdit.getBooleanProperty("htmlviewer.appearance.floating.showAddressbar"));
        showFloatingStatusbar = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.appearance.floating.showStatusbar"),
            jEdit.getBooleanProperty("htmlviewer.appearance.floating.showStatusbar"));

        showDockedMenu = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.appearance.docked.showMenu"),
            jEdit.getBooleanProperty("htmlviewer.appearance.docked.showMenu"));
        showDockedToolbar = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.appearance.docked.showToolbar"),
            jEdit.getBooleanProperty("htmlviewer.appearance.docked.showToolbar"));
        showDockedAddressbar = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.appearance.docked.showAddressbar"),
            jEdit.getBooleanProperty("htmlviewer.appearance.docked.showAddressbar"));
        showDockedStatusbar = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.appearance.docked.showStatusbar"),
            jEdit.getBooleanProperty("htmlviewer.appearance.docked.showStatusbar"));

        JPanel appearance = new JPanel(new GridLayout(0,2));
        appearance.add(new JLabel(jEdit.getProperty("options.htmlviewer.appearance.floating.label")));
        appearance.add(new JLabel(jEdit.getProperty("options.htmlviewer.appearance.docked.label")));
        appearance.add(showFloatingMenu);
        appearance.add(showDockedMenu);
        appearance.add(showFloatingToolbar);
        appearance.add(showDockedToolbar);
        appearance.add(showFloatingAddressbar);
        appearance.add(showDockedAddressbar);
        appearance.add(showFloatingStatusbar);
        appearance.add(showDockedStatusbar);
        addComponent(appearance);

        // "Homepage:"
        
        restorePreviousPage = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.restorePreviousPage"),
            jEdit.getBooleanProperty("htmlviewer.restorePreviousPage"));
        restorePreviousPage.addActionListener(this);
        
        tHome = new JTextField(jEdit.getProperty("htmlviewer.homepage"), 30);
        Box bHome = Box.createHorizontalBox();
        bHome.add(new JLabel(jEdit.getProperty("options.htmlviewer.homepage")));
        bHome.add(tHome);
        
        addComponent(Box.createVerticalStrut(10));
        addComponent(bHome);
        addComponent(restorePreviousPage);

        // "Auto-Update"
        addComponent(Box.createVerticalStrut(15));
        addSeparator("options.htmlviewer.autoupdate.label");

        autoUpdate = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.autoupdate"),
            jEdit.getBooleanProperty("htmlviewer.autoupdate"));
        autoUpdate.addActionListener(this);

        autoUpdateOnSwitch = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.autoupdate.onSwitch"),
            jEdit.getBooleanProperty("htmlviewer.autoupdate.onSwitch"));

        autoUpdateOnSave = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.autoupdate.onSave"),
            jEdit.getBooleanProperty("htmlviewer.autoupdate.onSave"));

        autoUpdateOnChange = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.autoupdate.onChange"),
            jEdit.getBooleanProperty("htmlviewer.autoupdate.onChange"));
        autoUpdateOnChange.addActionListener(this);

        autoUpdatePeriodically = new JCheckBox(
            jEdit.getProperty("options.htmlviewer.autoupdate.periodically"),
            jEdit.getBooleanProperty("htmlviewer.autoupdate.periodically"));
        autoUpdatePeriodically.addActionListener(this);

        int delayOnChange;
        try { delayOnChange = Integer.parseInt(jEdit.getProperty("htmlviewer.autoupdate.onChange.delay")); }
        catch(NumberFormatException nf) { delayOnChange = 3000; }
        delayOnChange = Math.min(Math.max(delayOnChange, 1000), 5000);

        autoUpdateDelayOnChange = new JSlider(1000, 5000, delayOnChange);
        Hashtable labelTable = new Hashtable();
        for(int i = 1000; i <= 5000; i += 1000)
            labelTable.put(new Integer(i), new JLabel(String.valueOf((double)i / 1000.0)));
        autoUpdateDelayOnChange.setLabelTable(labelTable);
        autoUpdateDelayOnChange.setPaintLabels(true);
        autoUpdateDelayOnChange.setMajorTickSpacing(1000);
        autoUpdateDelayOnChange.setMinorTickSpacing(100);
        autoUpdateDelayOnChange.setPaintTicks(true);
        autoUpdateDelayOnChange.setSnapToTicks(true);

        int delayPeriodically;
        try { delayPeriodically = Integer.parseInt(jEdit.getProperty("htmlviewer.autoupdate.periodically.delay")) / 1000; }
        catch(NumberFormatException nf) { delayPeriodically = 20; }
        delayPeriodically = Math.min(Math.max(delayPeriodically, 10), 300);

        autoUpdateDelayPeriodically = new JSlider(10, 300, delayPeriodically);
        Hashtable labelTable2 = new Hashtable();
        labelTable2.put(new Integer(10), new JLabel("10"));
        for(int i = 50; i <= 300; i += 50)
            labelTable2.put(new Integer(i), new JLabel(String.valueOf(i)));
        autoUpdateDelayPeriodically.setLabelTable(labelTable2);
        autoUpdateDelayPeriodically.setPaintLabels(true);
        autoUpdateDelayPeriodically.setMinorTickSpacing(10);
        autoUpdateDelayPeriodically.setPaintTicks(true);

        addComponent(autoUpdate);
        addComponent(autoUpdateOnSwitch);
        addComponent(autoUpdateOnSave);
        //addComponent(autoUpdateOnChange);    not yet...
        addComponent(autoUpdatePeriodically);
        //addComponent(jEdit.getProperty("options.htmlviewer.autoupdate.onChange.delay"), autoUpdateDelayOnChange);
        addComponent(jEdit.getProperty("options.htmlviewer.autoupdate.periodically.delay"), autoUpdateDelayPeriodically);

        // init:
        actionPerformed(null);
    }


    /**
     * Called when the options dialog's `OK' button is pressed.
     * This saves any properties saved in this option pane.
     */
    public void _save()
    {
        jEdit.setProperty("htmlviewer.homepage", tHome.getText());
        jEdit.setBooleanProperty("htmlviewer.appearance.floating.showMenu", showFloatingMenu.isSelected());
        jEdit.setBooleanProperty("htmlviewer.appearance.floating.showToolbar", showFloatingToolbar.isSelected());
        jEdit.setBooleanProperty("htmlviewer.appearance.floating.showAddressbar", showFloatingAddressbar.isSelected());
        jEdit.setBooleanProperty("htmlviewer.appearance.floating.showStatusbar", showFloatingStatusbar.isSelected());
        jEdit.setBooleanProperty("htmlviewer.appearance.docked.showMenu", showDockedMenu.isSelected());
        jEdit.setBooleanProperty("htmlviewer.appearance.docked.showToolbar", showDockedToolbar.isSelected());
        jEdit.setBooleanProperty("htmlviewer.appearance.docked.showAddressbar", showDockedAddressbar.isSelected());
        jEdit.setBooleanProperty("htmlviewer.appearance.docked.showStatusbar", showDockedStatusbar.isSelected());
        jEdit.setBooleanProperty("htmlviewer.autoupdate", autoUpdate.isSelected());
        jEdit.setBooleanProperty("htmlviewer.autoupdate.onSwitch", autoUpdateOnSwitch.isSelected());
        jEdit.setBooleanProperty("htmlviewer.restorePreviousPage", restorePreviousPage.isSelected());
        jEdit.setBooleanProperty("htmlviewer.autoupdate.onSave", autoUpdateOnSave.isSelected());
        //jEdit.setBooleanProperty("htmlviewer.autoupdate.onChange", autoUpdateOnChange.isSelected());
        jEdit.setBooleanProperty("htmlviewer.autoupdate.periodically", autoUpdatePeriodically.isSelected());
        jEdit.setProperty("htmlviewer.autoupdate.onChange.delay", String.valueOf(autoUpdateDelayOnChange.getValue()));
        jEdit.setProperty("htmlviewer.autoupdate.periodically.delay", String.valueOf(autoUpdateDelayPeriodically.getValue() * 1000));
    }


    /**
     * Called when one of the radio buttons is clicked.
     */
    public void actionPerformed(ActionEvent e)
    {
    	tHome.setEnabled(!restorePreviousPage.isSelected());
        autoUpdateOnSwitch.setEnabled(autoUpdate.isSelected());
        autoUpdateOnSave.setEnabled(autoUpdate.isSelected());
        //autoUpdateOnChange.setEnabled(autoUpdate.isSelected());
        autoUpdatePeriodically.setEnabled(autoUpdate.isSelected());
        //autoUpdateDelayOnChange.setEnabled(autoUpdate.isSelected() && autoUpdateOnChange.isSelected());
        autoUpdateDelayPeriodically.setEnabled(autoUpdate.isSelected() && autoUpdatePeriodically.isSelected());
    }


    private JTextField tHome;
    private JCheckBox showFloatingMenu;
    private JCheckBox showFloatingToolbar;
    private JCheckBox showFloatingAddressbar;
    private JCheckBox showFloatingStatusbar;
    private JCheckBox showDockedMenu;
    private JCheckBox showDockedToolbar;
    private JCheckBox showDockedAddressbar;
    private JCheckBox showDockedStatusbar;
    private JCheckBox autoUpdate;
    private JCheckBox restorePreviousPage;
    private JCheckBox autoUpdateOnSwitch;
    private JCheckBox autoUpdateOnSave;
    private JCheckBox autoUpdateOnChange;
    private JCheckBox autoUpdatePeriodically;
    private JSlider autoUpdateDelayOnChange;
    private JSlider autoUpdateDelayPeriodically;

}
