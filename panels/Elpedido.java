package Restaurante_app.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Restaurante_app.DAO.MesaDao;
import Restaurante_app.DAO.ProductoDao;
import Restaurante_app.Models.Mesa;
import Restaurante_app.Models.Producto;

public class Elpedido extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox<Mesa> comboMesas;
	private JComboBox<Producto> comboProductos;
	

	public Elpedido() {
		setPreferredSize(new Dimension(360, 640));
		setLayout(null);
		
		comboMesas = new JComboBox<>();
		comboMesas.setBounds(10, 5, 160, 22);   // ajústalo a donde quieras ponerlo
		add(comboMesas);
		
		JLabel lblEstadoMesa = new JLabel();
		lblEstadoMesa.setBounds(180, 9, 55, 14);
		add(lblEstadoMesa);
		
		comboMesas.addActionListener(e -> {
		    Mesa seleccionada = (Mesa) comboMesas.getSelectedItem();
		    if (seleccionada != null) {
		        lblEstadoMesa.setText(seleccionada.getEstado());
		        lblEstadoMesa.setForeground(
		            "Disponible".equals(seleccionada.getEstado())
		              ? Color.GREEN
		              : Color.RED
		        );
		    }
		});
		
		// 1) Creas el combo y lo agregas al panel
		comboProductos = new JComboBox<>();
		comboProductos.setBounds(10, 50, 300, 25);   // ajusta las coordenadas
		add(comboProductos);
		
		// Carga inicial de mesas
        cargarMesas();
		
	}
	
	/** 
     * Llena comboMesas y comboProductos desde la BD.
     * Llamar cada vez que abras o refresques el panel. 
     */
    public void cargarDatos() {
        cargarMesas();
        cargarProductosMenu();
    }
    
    private void cargarMesas() {
        comboMesas.removeAllItems();
        for (Mesa m : MesaDao.obtenerMesas()) {
            comboMesas.addItem(m);
        }
    }
    
    private void cargarProductosMenu() {
        comboProductos.removeAllItems();
        List<Producto> productosMenu = ProductoDao.obtenerProductosDelMenu();
        for (Producto p : productosMenu) {
            comboProductos.addItem(p);
        }
    }
	

}
