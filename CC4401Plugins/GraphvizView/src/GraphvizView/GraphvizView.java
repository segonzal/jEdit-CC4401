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

import java.io.File;

public class GraphvizView extends JPanel
{

	private final EditPane editPane;
	private Component child;
	private final JSplitPane splitter;
	private final String LADO_PROP = "options.GraphvizView.lado";
	private final String RUTA_DOT_PROP = "options.GraphvizView.ruta_dot";
	private final String TMP_DIR_PROP = "options.GraphvizView.tmp_dir";
	private JPanel jpnPanel;
	private final JLabel jlbImagen;
	
	public GraphvizView(EditPane editPane)
	{
		this.jpnPanel = new JPanel();
		this.jlbImagen = new JLabel();
		this.jlbImagen.setHorizontalAlignment(JLabel.CENTER);
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
		
<<<<<<< HEAD
		this.pnl.setLayout(new BorderLayout());
		
<<<<<<< HEAD
		Dimension minSize = new Dimension(100, 0);
		// Establecer tamaño mínimo de panel.
		this.pnl.setMinimumSize(minSize);	
		
		final JButton btn = new JButton();
		final JLabel lbl = new JLabel();
		this.pnl.add(lbl);
		this.pnl.add(btn);
=======
=======
		this.jpnPanel.setLayout(new BorderLayout());
>>>>>>> joseo
		
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
		
<<<<<<< HEAD
		JPanel pnlBotones = new JPanel(new GridLayout(1,2));
		this.pnl.add(lbl, BorderLayout.CENTER);
		pnlBotones.add(jbtDibujar);
		pnlBotones.add(jbtLimpiar);
		this.pnl.add(pnlBotones, BorderLayout.SOUTH);
>>>>>>> joseo
		final JEditTextArea txt = editPane.getTextArea();
=======
>>>>>>> joseo
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
	
	public void actualizarGrafo()
	{
		String dir_tmp = jEdit.getProperty(TMP_DIR_PROP);
		String texto_grafo = "";
		String arch_tmp = "~graphviz_tmp.png";
		String ruta_tmp = dir_tmp + "/" + arch_tmp;
		
		// Obtener texto del área de texto activa.
		JEditTextArea txt = editPane.getTextArea();
		texto_grafo = txt.getText();
		
		// Crear enlace a Graphviz y configurarlo.
		GraphvizAPI.DOT = jEdit.getProperty(RUTA_DOT_PROP);
		GraphvizAPI.TEMP_DIR = dir_tmp;
		GraphvizAPI gv = new GraphvizAPI();
		
		// Verter código del grafo.
		gv.addln(texto_grafo);
		
		// Generar imagen.
		File out = new File(ruta_tmp);
		gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), "png"), out);
		
		// Mostrar imagen en plugin.
		ImageIcon icon = new ImageIcon(ruta_tmp);
		icon.getImage().flush();
		jlbImagen.setIcon(icon);
	}
	
	public void limpiarGrafo()
	{
		this.jlbImagen.setIcon(null);
	}	
	
	public void propertiesChanged()
	{
		// Si las propiedades cambian, probablemente haya que hacer una
		// reconfiguración del Splitter.
<<<<<<< HEAD
		//setSplitterComponents();
=======
>>>>>>> joseo
		
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
