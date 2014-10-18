// Programado en base a http://www.jedit.org/users-guide/writing-plugins-part.html
// y siguiendo como ejemplo de plugin a Minimap.

package GraphvizView;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;

import org.gjt.sp.jedit.EditPane;
import org.gjt.sp.jedit.textarea.JEditTextArea;
import org.gjt.sp.jedit.jEdit;

public class GraphvizView extends JPanel
{

	private final EditPane editPane;
	private Component child;
	private final JSplitPane splitter;
  private final String LADO_PROP = "options.GraphvizView.lado";
	
	public GraphvizView(EditPane editPane)
	{
		// GraphvizView extiende a JPanel. Se establece un layout de una
		// sola celda.
		setLayout(new GridLayout(1, 1));
		this.editPane = editPane;
		JEditTextArea textArea = editPane.getTextArea();
		Container c = textArea.getParent();
		// Tomar puntero a textArea del EditPane.
		child = textArea;
		// Conseguir referencia su contenedor.
		while (! (c instanceof EditPane))
		{
			child = c;
			c = c.getParent();
		}
		// Crear un Splitter.
		splitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		// Colocar elementos en su lugar, dentro de Splitter.
		setSplitterComponents();
		// Agregar Splitter.
		add(splitter);
	}

	private void setSplitterComponents()
	{
		// Colocar en Spliter, el contenedor del EditPane y el panel
		// asociado al plugin (por ahora, sólo un "Hola mundo").
		// Averiguar primero en qué lado debe mostrarse, y luego insertar objetos.
		String lado = jEdit.getProperty(LADO_PROP);
		if(lado.equals("DER"))
		{
			splitter.setRightComponent(new JLabel("¡Hola mundo!"));
			splitter.setLeftComponent(child);
		}
		else
		{
			splitter.setLeftComponent(new JLabel("¡Hola mundo!"));
			splitter.setRightComponent(child);
		}
	}
	
	public void propertiesChanged()
	{
		// Si las propiedades cambian, probablemente haya que hacer una
		// reconfiguración del Splitter.
		setSplitterComponents();
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
