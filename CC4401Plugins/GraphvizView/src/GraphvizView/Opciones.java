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

  private JComboBox jcb_lado;
  private JLabel jlb_lado;

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
    jcb_lado.setSelectedItem(getLadoProp());
    jlb_lado = new JLabel(jEdit.getProperty(LADO_LABEL));
    addComponent(jlb_lado, jcb_lado);
  }

  @Override
  public void _save()
  {
    // Indicar qué propiedades deben guardarse.
    jEdit.setProperty(LADO_PROP, jcb_lado.getSelectedItem().toString());
  }

  // Métodos estáticos para recuperar propiedades.
  public static String getLadoProp()
  {
    return jEdit.getProperty(LADO_PROP);
  }
}