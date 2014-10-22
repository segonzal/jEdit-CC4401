Archivo build.xml 'malo'.
Para crear el .jar se debe usar la opcion de exportar -> jar file de eclipse mientras se tiene seleccionado
el paquete CC4401Plugins.UMLViewer.
Para instalar el Plugin se debe copiar el .jar generado y pegarlo en la carpeta jars de jEdit.
***Tareas
-HECHO-Que jEdit detecte el plugin.
-HECHO-Que se desplieguen las opciones en el menú.
-HECHO-Cambiar los archivos a su propia carpeta src para mejorar el orden.
-HECHO-Hacer que la opción 'UMLViewer' cree una ventana de prueba.
-HECHO-Comprobar que se pueden agregar acciones y que se vea un efecto de prueba.
-RECHAZADO-Cambiar las opciones del menu Plugins de la Barra Superior al menu Plugins de la barra de File Browser.
	Al final se crea otra instancia de File Browser para seleccionar la carpeta.
-HECHO-Implementar clase que recorra el fichero actual de File Browser.
	-Hacer que el recorrido incluya subcarpetas.
	-Sólo entregar archivos con extensión .java
-Implementar Parser para obtener datos de las clases.
-Implementar rutina de salida/término.