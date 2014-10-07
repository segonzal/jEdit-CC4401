package GraphvizView;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;

import org.gjt.sp.jedit.EditPane;
import org.gjt.sp.jedit.textarea.JEditTextArea;

public class GraphvizView extends JPanel
{

	private final EditPane editPane;
	private Component child;
	private final JSplitPane splitter;
	
	public GraphvizView(EditPane editPane)
	{
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
