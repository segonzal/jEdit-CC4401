// Programado en base a http://www.jedit.org/users-guide/writing-plugins-part.html
// y siguiendo como ejemplo de plugin a Minimap.

package GraphvizView;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import org.gjt.sp.jedit.EditPane;
import org.gjt.sp.jedit.textarea.JEditTextArea;
import org.gjt.sp.jedit.jEdit;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import graphvizapi.GraphvizAPI;



public class GraphvizView extends JPanel
{

	private final EditPane editPane;
	private Component child;
	private final JSplitPane splitter;
	private final String LADO_PROP = "options.GraphvizView.lado";
	private JPanel jpnPanel;
	private final JLabel jlbImagen;
	
	public GraphvizView(EditPane editPane)
	{
		// GraphvizView extiende a JPanel. Se establece un layout de una
		// sola celda.
		this.jpnPanel = new JPanel();
		this.jlbImagen = new JLabel();
		this.jlbImagen.setHorizontalAlignment(JLabel.CENTER);
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
		
		this.jpnPanel.setLayout(new BorderLayout());
		
		Dimension minSize = new Dimension(150, 0);
		// Establecer tamaño mínimo de panel.
		this.jpnPanel.setMinimumSize(minSize);	
		
		final JButton jbtDibujar = new JButton("Dibujar");
		final JButton jbtLimpiar = new JButton("Limpiar");
		
		JPanel jpnBotones = new JPanel(new GridLayout(1,2));
		jpnBotones.add(jbtDibujar);
		jpnBotones.add(jbtLimpiar);
		this.jpnPanel.add(jpnBotones, BorderLayout.SOUTH);
		this.jpnPanel.add(this.jlbImagen, BorderLayout.CENTER);
		
		jbtDibujar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				actualizarGrafo();
			}
		});
		
		jbtLimpiar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				limpiarGrafo();
			}
		});
		
		if(lado.equals("DER"))
		{
			splitter.setRightComponent(this.jpnPanel);
			splitter.setLeftComponent(this.child);
		}
		else
		{
			splitter.setLeftComponent(this.jpnPanel);
			splitter.setRightComponent(this.child);
		}
	}
	
	///////////////////////////////////////
	public void actualizarGrafo()
	{
		//JEditTextArea txt = editPane.getTextArea();
		//lbl.setText(txt.getText());
		this.jlbImagen.setIcon(new ImageIcon("prueba.png"));
		GraphvizAPI gv = new GraphvizAPI();
	}
	
	public void limpiarGrafo()
	{
		this.jlbImagen.setIcon(null);
	}
	///////////////////////////////////////
	
	
	public void propertiesChanged()
	{
		// Si las propiedades cambian, probablemente haya que hacer una
		// reconfiguración del Splitter.
		
		String lado = jEdit.getProperty(LADO_PROP);
		if(lado.equals("DER"))
		{
			splitter.setRightComponent(this.jpnPanel);
			splitter.setLeftComponent(this.child);
		}
		else
		{
			splitter.setLeftComponent(this.jpnPanel);
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
