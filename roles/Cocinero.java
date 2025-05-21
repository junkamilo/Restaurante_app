package Restaurante_app.roles;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Restaurante_app.EstilosRestaurante;

public class Cocinero extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Cocinero() {
		this.setName("Cocinero");
		this.setPreferredSize(new Dimension(800,600));
		setLayout(null);
		
		//Aplicamos los estilos al panel
		EstilosRestaurante.ColorPanel_cocinero(this);
		
		JLabel titulo_cocinero = new JLabel("Cocinero");
		titulo_cocinero.setHorizontalAlignment(SwingConstants.CENTER);
		titulo_cocinero.setBounds(180, 11, 100, 14);
		add(titulo_cocinero);
		
		
	}

}
