/*
 * HtmlViewerOptionPane.java - HtmlViewer options panel
 * Copyright (C) 1999-2001 Dirk Moebius
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.gjt.sp.jedit.AbstractOptionPane;
import org.gjt.sp.jedit.jEdit;

public class HtmlViewerOptionPane extends AbstractOptionPane implements ActionListener
{

	private static final long serialVersionUID = -8581345434069856402L;

	public HtmlViewerOptionPane()
	{
		super("chooseBrowser");
	}

	public void _init()
	{

		useForHelp = new JCheckBox(jEdit.getProperty("options.htmlviewer.useforhelp.label"));
		useForHelp.setSelected(jEdit.getBooleanProperty("htmlviewer.useforhelp"));
		addComponent(useForHelp);
		// "Choose Preferred Browser"
		addSeparator("options.htmlviewer.browser.label");

		// "Internal (HtmlViewer)"
		rbInternal = new JRadioButton(jEdit
			.getProperty("options.htmlviewer.browser.internal"));
		rbInternal.addActionListener(this);
		addComponent(rbInternal);

		// "Class in classpath"
		rbClass = new JRadioButton(jEdit.getProperty("options.htmlviewer.browser.class"));
		rbClass.addActionListener(this);
		addComponent(rbClass);

		// "Firefox"
		rbFirefox = new JRadioButton(jEdit
			.getProperty("options.htmlviewer.browser.firefox"));
		rbFirefox.addActionListener(this);
		// addComponent(rbFirefox);

		// "External browser"
		rbOther = new JRadioButton(jEdit.getProperty("options.htmlviewer.browser.other"));
		rbOther.addActionListener(this);
		addComponent(rbOther);

		ButtonGroup browserGroup = new ButtonGroup();
		browserGroup.add(rbInternal);
		browserGroup.add(rbClass);
		browserGroup.add(rbFirefox);
		browserGroup.add(rbOther);

		String browserType = jEdit.getProperty("htmlviewer.browsertype");
		if ("firefox".equals(browserType))
			rbFirefox.setSelected(true);
		else if ("class".equals(browserType))
			rbClass.setSelected(true);
		else if ("external".equals(browserType))
			rbOther.setSelected(true);
		else
			rbInternal.setSelected(true);

		// "Browser settings:"
		addComponent(Box.createVerticalStrut(20));
		addSeparator("options.htmlviewer.browser.settings.label");

		// "Class:"
		tClass = new JTextField(jEdit.getProperty("htmlviewer.class"), 15);
		addComponent(jEdit.getProperty("options.htmlviewer.browser.class.label"), tClass);

		// "Method:"
		tMethod = new JTextField(jEdit.getProperty("htmlviewer.method"), 15);
		addComponent(jEdit.getProperty("options.htmlviewer.browser.method.label"), tMethod);

		// "External browser command:"
		tBrowser = new JTextField(jEdit.getProperty("htmlviewer.otherBrowser"), 15);
		addComponent(jEdit.getProperty("options.htmlviewer.browser.other.label"), tBrowser);

		// init:
		actionPerformed(null);
	}

	/**
	 * Called when the options dialog's `OK' button is pressed. This saves
	 * any properties saved in this option pane.
	 */
	public void _save()
	{
		jEdit.setProperty("htmlviewer.browsertype", rbInternal.isSelected() ? "internal"
			: rbClass.isSelected() ? "class" : rbFirefox.isSelected() ? "firefox"
				: "external");

		jEdit.setBooleanProperty("htmlviewer.useforhelp", useForHelp.isSelected());
		jEdit.setProperty("htmlviewer.otherBrowser", tBrowser.getText());
		jEdit.setProperty("htmlviewer.class", tClass.getText());
		jEdit.setProperty("htmlviewer.method", tMethod.getText());
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

	private JRadioButton rbFirefox;

	private JCheckBox useForHelp;

	private JTextField tBrowser;

	private JTextField tClass;

	private JTextField tMethod;

}
