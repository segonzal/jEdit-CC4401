// Programado en base a http://www.jedit.org/users-guide/writing-plugins-part.html
// y siguiendo como ejemplo de plugin a Minimap.

package GraphvizView;

import java.awt.*;
import javax.swing.*;

import org.gjt.sp.jedit.AbstractOptionPane;
import org.gjt.sp.jedit.jEdit;

public class Opciones extends AbstractOptionPane
{
  private static final String LADO_LABEL = "labels.GraphvizView.lado";
  private static final String LADO_PROP = "options.GraphvizView.lado";
  private static final String RUTA_DOT_LABEL = "labels.GraphvizView.ruta_dot";
  private static final String RUTA_DOT_PROP = "options.GraphvizView.ruta_dot";

  private JComboBox jcb_lado;
  private JLabel jlb_lado;
  private JTextField jtf_ruta_dot;
  private JLabel jlb_ruta_dot;

  public static final String IZQ = "IZQ";
  public static final String DER = "DER";

  public Opciones()
  {
    super("GraphvizView");
  }

  @Override
  public void _init()
  {
    // Crear e insertar elementos en el panel de configuración del plugin.
    jcb_lado = new JComboBox();
    jcb_lado.addItem(IZQ);
    jcb_lado.addItem(DER);
    jcb_lado.setSelectedItem(jEdit.getProperty(LADO_PROP));
    jlb_lado = new JLabel(jEdit.getProperty(LADO_LABEL));
    addComponent(jlb_lado, jcb_lado);
    jtf_ruta_dot = new JTextField(jEdit.getProperty(RUTA_DOT_PROP));
    jlb_ruta_dot = new JLabel(jEdit.getProperty(RUTA_DOT_LABEL));
    addComponent(jlb_ruta_dot, jtf_ruta_dot);
  }

  @Override
  public void _save()
  {
    // Indicar qué propiedades deben guardarse.
    jEdit.setProperty(LADO_PROP, jcb_lado.getSelectedItem().toString());
    jEdit.setProperty(RUTA_DOT_PROP, jtf_ruta_dot.getText());
  }
}