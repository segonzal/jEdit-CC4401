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

/**
 * Created by luism on 18-10-14.
 */
import org.gjt.sp.jedit.EditPlugin;
import org.gjt.sp.jedit.EditPane;
import org.gjt.sp.jedit.jEdit;
import org.gjt.sp.jedit.visitors.JEditVisitorAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import java.awt.EventQueue;
public class NavPlugin extends EditPlugin
{
	public static final String NAME = "Nav";
	private static Map<EditPane, Nav> views;

	private static void hide(EditPane editPane, boolean restore)
	{
		// Si el EditPane no está en el Map, entonces no tiene actualmente
		// un visor.
		if (!views.containsKey(editPane))
		{
			return;
		}

		// Si está, se obtiene su visor, y se le ordena ocultarse.
		Nav view = views.get(editPane);
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
		views = new HashMap<EditPane, Nav>();
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
				Nav view = new Nav(editPane);
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
