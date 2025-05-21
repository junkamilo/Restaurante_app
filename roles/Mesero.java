package Restaurante_app.roles;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Mesero extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Mesero() {
		this.setName("Mesero");
		this.setPreferredSize(new Dimension(360,640));
		setLayout(null);
		
		JLabel titulo_mesero = new JLabel("Mesero");
		titulo_mesero.setHorizontalAlignment(SwingConstants.CENTER);
		titulo_mesero.setBounds(129, 11, 100, 14);
		add(titulo_mesero);

	}

}
