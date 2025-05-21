package Restaurante_app.panels;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Control_ventas extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Control_ventas() {
		this.setName("Control_ventas");
		this.setPreferredSize(new Dimension(360,640));
		this.setLayout(null);
		
		JLabel titulo_detallesPedido = new JLabel("Control de ventas");
		titulo_detallesPedido.setHorizontalAlignment(SwingConstants.CENTER);
		titulo_detallesPedido.setBounds(130, 11, 100, 14);
		add(titulo_detallesPedido);
	}

}
