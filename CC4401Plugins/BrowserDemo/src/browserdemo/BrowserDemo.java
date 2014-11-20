/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browserdemo;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 *
 * @author segonzal
 */
public class BrowserDemo {
    private static Browser browser;
    private static Shell shell;
    private static Display display;
    private static Boolean locked = false;
    
    public BrowserDemo(){
        display = new Display();
        shell = new Shell(display);
        
        shell.setLayout(new GridLayout());
        Composite compTools = new Composite(shell, SWT.NONE);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        compTools.setLayoutData(data);
        compTools.setLayout(new GridLayout(2,false));
	// CREA LA BARRA DE NAVEGACION (LADO DERECHO)
	ToolBar navBar = new ToolBar(compTools, SWT.NONE);
	navBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	// BOTON DE REGRESAR HISTORIAL
	final ToolItem back = new ToolItem(navBar, SWT.PUSH);
	back.setText("Back");
	back.setEnabled(false);
	// BOTON DE AVANZAR HISTORIAL
	final ToolItem forward = new ToolItem(navBar, SWT.PUSH);
	forward.setText("Forward");
	forward.setEnabled(false);
        // BOTON DE REFRESH
	final ToolItem refresh = new ToolItem(navBar, SWT.PUSH);
	refresh.setText("Refresh");
	refresh.setEnabled(true);
        // BOTON DE LOCK
	final ToolItem lock = new ToolItem(navBar, SWT.PUSH);
	lock.setText("Lock");
	lock.setEnabled(true);
        
        //ESPACIO DEL BROWSER
        
        Composite comp = new Composite(shell, SWT.NONE);
	data = new GridData(GridData.FILL_BOTH);
	comp.setLayoutData(data);
	comp.setLayout(new FillLayout());
	final SashForm form = new SashForm(comp, SWT.HORIZONTAL);
	form.setLayout(new FillLayout());
        try {
		browser = new Browser(form, SWT.NONE);
	} catch (SWTError e) {
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
		messageBox.setMessage("Closing application. The Browser could not be initialized.");
		messageBox.setText("Fatal error - application terminated");
		messageBox.open();
		System.exit(-1);
	}
        
        shell.setText("HtmlViewer Browser DEMO - " + browser.getBrowserType());
        
        //AGREGAR FUNCIONALIDAD A LOS BOTONES
        back.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                browser.back();
            }
	});
        forward.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                browser.forward();
            }
	});
        final LocationListener locationListener = new LocationListener() {
            @Override
            public void changing(LocationEvent event) {
                Browser browser = (Browser)event.widget;
                back.setEnabled(browser.isBackEnabled());
                forward.setEnabled(browser.isForwardEnabled());
            }

            @Override
            public void changed(LocationEvent event) {
                //does nothing
            }
	};
        browser.addLocationListener(locationListener);
        
        refresh.addListener(SWT.Selection, new Listener(){
            @Override
            public void handleEvent(Event event) {
                if (!locked)
                    browser.refresh();
            }
        });
        lock.addListener(SWT.Selection, new Listener(){
            @Override
            public void handleEvent(Event event) {
                locked = !locked;
                if (locked)
                    lock.setText("Unlock");
                else
                    lock.setText("Lock");
            }
        });
    }
    
    public static void run(){
        shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
    }
    
    public static void setURL(String url){
        browser.setUrl(url);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BrowserDemo demo = new BrowserDemo();
        demo.setURL("http://www.google.cl/");
        demo.run();
    }
    
}
