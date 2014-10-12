package plugin.InfoViewer.infoviewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.gjt.sp.jedit.AbstractOptionPane;
import org.gjt.sp.jedit.jEdit;

public class InfoViewerOptionPane extends AbstractOptionPane implements ActionListener
{

	private static final long serialVersionUID = -8581345434069856402L;

	public InfoViewerOptionPane()
	{
		super("infoviewer.chooseBrowser");
	}

	public void _init()
	{
		
		useForHelp = new JCheckBox(jEdit.getProperty("options.infoviewer.useforhelp.label"));
		useForHelp.setSelected(jEdit.getBooleanProperty("infoviewer.useforhelp"));
		addComponent(useForHelp);
		// "Choose Preferred Browser"
		addSeparator("options.infoviewer.browser.label");

		// "Internal (InfoViewer)"
		rbInternal = new JRadioButton(jEdit
			.getProperty("options.infoviewer.browser.internal"));
		rbInternal.addActionListener(this);
		addComponent(rbInternal);

		// "Class in classpath"
		rbClass = new JRadioButton(jEdit.getProperty("options.infoviewer.browser.class"));
		rbClass.addActionListener(this);
		addComponent(rbClass);

		// "Netscape"
		rbNetscape = new JRadioButton(jEdit
			.getProperty("options.infoviewer.browser.netscape"));
		rbNetscape.addActionListener(this);
		addComponent(rbNetscape);

		// "External browser"
		rbOther = new JRadioButton(jEdit.getProperty("options.infoviewer.browser.other"));
		rbOther.addActionListener(this);
		addComponent(rbOther);

		ButtonGroup browserGroup = new ButtonGroup();
		browserGroup.add(rbInternal);
		browserGroup.add(rbClass);
		browserGroup.add(rbNetscape);
		browserGroup.add(rbOther);

		String browserType = jEdit.getProperty("infoviewer.browsertype");
		if ("netscape".equals(browserType))
			rbNetscape.setSelected(true);
		else if ("class".equals(browserType))
			rbClass.setSelected(true);
		else if ("external".equals(browserType))
			rbOther.setSelected(true);
		else
			rbInternal.setSelected(true);

		// "Browser settings:"
		addComponent(Box.createVerticalStrut(20));
		addSeparator("options.infoviewer.browser.settings.label");

		// "Class:"
		tClass = new JTextField(jEdit.getProperty("infoviewer.class"), 15);
		addComponent(jEdit.getProperty("options.infoviewer.browser.class.label"), tClass);

		// "Method:"
		tMethod = new JTextField(jEdit.getProperty("infoviewer.method"), 15);
		addComponent(jEdit.getProperty("options.infoviewer.browser.method.label"), tMethod);

		// "External browser command:"
		tBrowser = new JTextField(jEdit.getProperty("infoviewer.otherBrowser"), 15);
		addComponent(jEdit.getProperty("options.infoviewer.browser.other.label"), tBrowser);

		// init:
		actionPerformed(null);
	}

	/**
	 * Called when the options dialog's `OK' button is pressed. This saves
	 * any properties saved in this option pane.
	 */
	public void _save()
	{
		jEdit.setProperty("infoviewer.browsertype", rbInternal.isSelected() ? "internal"
			: rbClass.isSelected() ? "class" : rbNetscape.isSelected() ? "netscape"
				: "external");

		jEdit.setBooleanProperty("infoviewer.useforhelp", useForHelp.isSelected());
		jEdit.setProperty("infoviewer.otherBrowser", tBrowser.getText());
		jEdit.setProperty("infoviewer.class", tClass.getText());
		jEdit.setProperty("infoviewer.method", tMethod.getText());
	}

	/**
	 * Called when one of the radio buttons is clicked.
	 */
	public void actionPerformed(ActionEvent e)
	{
		tClass.setEnabled(rbClass.isSelected());
		tMethod.setEnabled(rbClass.isSelected());
		tBrowser.setEnabled(rbOther.isSelected());
	}

	private JRadioButton rbInternal;

	private JRadioButton rbOther;

	private JRadioButton rbClass;

	private JRadioButton rbNetscape;

	private JCheckBox useForHelp;

	private JTextField tBrowser;

	private JTextField tClass;

	private JTextField tMethod;

}
