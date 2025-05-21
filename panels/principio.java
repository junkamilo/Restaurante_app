package Restaurante_app.panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import Restaurante_app.Componentes.btnAcciones_categorias;
import Restaurante_app.Models.Producto;

public class principio extends btnAcciones_categorias {

	public principio() {
        super("principio");
    }
	public List<Producto> getProductosSeleccionados() {
	    List<Producto> seleccionados = new ArrayList<>();
	    for (Producto producto : listaProductos) {
	    	JCheckBox check = checkboxes.get(producto.getId());
	        if (check.isSelected()) {
	            seleccionados.add(producto);
	        }
	    }
	    return seleccionados;
	}

}
