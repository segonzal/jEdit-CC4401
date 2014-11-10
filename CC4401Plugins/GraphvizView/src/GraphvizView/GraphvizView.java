// Programado en base a http://www.jedit.org/users-guide/writing-plugins-part.html
// y siguiendo como ejemplo de plugin a Minimap.

package GraphvizView;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.gjt.sp.jedit.EditPane;
import org.gjt.sp.jedit.textarea.JEditTextArea;
import org.gjt.sp.jedit.jEdit;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class GraphvizView extends JPanel
{

	private final EditPane editPane;
	private Component child;
	private final JSplitPane splitter;
	private final String LADO_PROP = "options.GraphvizView.lado";
	private JPanel pnl = new JPanel();
	
	public GraphvizView(EditPane editPane)
	{
		// GraphvizView extiende a JPanel. Se establece un layout de una
		// sola celda.
		this.setLayout(new GridLayout(1, 1));
		this.editPane = editPane;
		JEditTextArea textArea = editPane.getTextArea();
		Container c = textArea.getParent();
		// Tomar puntero a textArea del EditPane.
		this.child = textArea;
		// Conseguir referencia su contenedor.
		while (! (c instanceof EditPane))
		{
			this.child = c;
			c = c.getParent();
		}
		// Crear un Splitter.
		this.splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		// Colocar elementos en su lugar, dentro de Splitter.
		this.setSplitterComponents();
		// Agregar Splitter.
		this.add(splitter);
	}

	private void setSplitterComponents()
	{
		// Colocar en Spliter, el contenedor del EditPane y el panel
		// asociado al plugin (por ahora, sólo un "Hola mundo").
		// Averiguar primero en qué lado debe mostrarse, y luego insertar objetos.
		String lado = jEdit.getProperty(LADO_PROP);
		
		this.pnl.setLayout(new GridLayout(2, 1));
		
		Dimension minSize = new Dimension(100, 0);
		// Establecer tamaño mínimo de panel.
		this.pnl.setMinimumSize(minSize);	
		
		final JButton btn = new JButton();
		final JLabel lbl = new JLabel();
		this.pnl.add(lbl);
		this.pnl.add(btn);
		final JEditTextArea txt = editPane.getTextArea();
		btn.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				lbl.setText(txt.getText());
			}
		});
		
		if(lado.equals("DER"))
		{
			splitter.setRightComponent(this.pnl);
			splitter.setLeftComponent(this.child);
		}
		else
		{
			splitter.setLeftComponent(this.pnl);
			splitter.setRightComponent(this.child);
		}
	}
	
	public void propertiesChanged()
	{
		// Si las propiedades cambian, probablemente haya que hacer una
		// reconfiguración del Splitter.
		//setSplitterComponents();
		
		String lado = jEdit.getProperty(LADO_PROP);
		if(lado.equals("DER"))
		{
			splitter.setRightComponent(this.pnl);
			splitter.setLeftComponent(this.child);
		}
		else
		{
			splitter.setLeftComponent(this.pnl);
			splitter.setRightComponent(this.child);
		}
	}
	
	public void start()
	{
		// Agregar plugin.
		editPane.add(this);
		editPane.validate();
	}
	
	public void stop(boolean restore)
	{
		// Remover plugin.
		editPane.remove(this);
		if (restore)
		{
			// Agregar contenedor del EditPane original.
			editPane.add(child);
			editPane.validate();
		}
		// Remover Splitter.
		remove(splitter);
		splitter.remove(child);
	}
}
