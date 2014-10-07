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
	private static Map<EditPane, GraphvizView> views;

	
	private static void hide(EditPane editPane, boolean restore)
	{
		if (!views.containsKey(editPane))
		{
			return;
		}
		
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
		views = new HashMap<EditPane, GraphvizView>();
	}
	
	public static void showAll()
	{
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
		if (views.containsKey(editPane))
		{
			return;
		}
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
		hide(editPane, true);
	}
	
}