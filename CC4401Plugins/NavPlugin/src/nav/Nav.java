package nav;
/*
 * jEdit - Programmer's Text Editor
 * :tabSize=8:indentSize=8:noTabs=false:
 * :folding=explicit:collapseFolds=1:
 *
 * Copyright © 2014 jEdit contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import org.gjt.sp.jedit.EditPane;
import org.gjt.sp.jedit.textarea.JEditTextArea;

/**
 * Created by luism on 18-10-14.
 */
public class Nav extends JPanel
{
	private final EditPane editPane;
	private Component child;
	private final JSplitPane splitter;
	public Nav(EditPane editPane){
		setLayout(new GridLayout(1, 1));
		this.editPane = editPane;
		JEditTextArea textArea = editPane.getTextArea();
		Container c = textArea.getParent();
		child = textArea;
		while (! (c instanceof EditPane))
		{
			child = c;
			c = c.getParent();
		}
		splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		setSplitterComponents();
		add(splitter);
	}
	private void setSplitterComponents()
	{
		splitter.setRightComponent(new JLabel("¡Hola mundo!"));
		splitter.setLeftComponent(child);
	}

	public void propertiesChanged()
	{
		setSplitterComponents();
	}

	public void start()
	{
		editPane.add(this);
		editPane.validate();
	}

	public void stop(boolean restore)
	{
		editPane.remove(this);
		if (restore)
		{
			editPane.add(child);
			editPane.validate();
		}
		remove(splitter);
		splitter.remove(child);
	}
}
