package Restaurante_app.roles;

import javax.swing.JPanel;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Mesero_Auxiliar extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public Mesero_Auxiliar() {
		setBackground(new Color(255, 255, 0));
		this.setName("Mesero Auxiliar");
		this.setPreferredSize(new Dimension(800,600));
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cocinero");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(350, 11, 100, 14);
		add(lblNewLabel);
	}

}
