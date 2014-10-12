// Programado en base a http://www.jedit.org/users-guide/writing-plugins-part.html
// y siguiendo como ejemplo de plugin a Minimap.

package GraphvizView;

import org.gjt.sp.jedit.EditPlugin;
import org.gjt.sp.jedit.EditPane;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.visitors.JEditVisitorAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.awt.EventQueue;

public class GraphvizViewPlugin extends EditPlugin
{
	// Map de todas las instancias de visores de Graphviz.
	// Se almacenan pares (EditPane de jEdit y visor de GraphvizView).
	private static Map<EditPane, GraphvizView> views;

	private static void hide(EditPane editPane, boolean restore)
	{
		// Si el EditPane no está en el Map, entonces no tiene actualmente
		// un visor.
		if (!views.containsKey(editPane))
		{
			return;
		}
		
		// Si está, se obtiene su visor, y se le ordena ocultarse.
		GraphvizView view = views.get(editPane);
		view.stop(restore);
		views.remove(editPane);
	}
	
	@Override
	public void stop()
	{
		hideAll();
		views = null;
	}
	
	@Override
	public void start()
	{
		// Inicializar el Map de vistas.
		views = new HashMap<EditPane, GraphvizView>();
	}
	
	public static void showAll()
	{
		// Visitar cada EditPane, y hacer que muestre un visor.
		jEdit.visit(new JEditVisitorAdapter()
		{
			@Override
			public void visit(EditPane editPane)
			{
				show(editPane);
			}
		});
	}
	
	public static void show(final EditPane editPane)
	{
		// Si EditPane ya está en Map, entonces ya muestra el visor.
		if (views.containsKey(editPane))
		{
			return;
		}
		
		// Si no, ordenar mostrarlo.
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				GraphvizView view = new GraphvizView(editPane);
				view.start();
				views.put(editPane, view);
			}
		});
	}
	
	public static void hideAll()
	{
		// Visitar cada EditPane, y hacer que oculten sus visores.
		Set<EditPane> editPanes = new HashSet<EditPane>();
		for (EditPane ep: views.keySet())
		{
			editPanes.add(ep);
		}
		for (EditPane ep: editPanes)
		{
			hide(ep);
		}
	}
	
	public static void hide(EditPane editPane)
	{
		// Ordenar la ocultación del visor asociado al EditPane indicado.
		hide(editPane, true);
	}
	
}