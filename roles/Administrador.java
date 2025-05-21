package Restaurante_app.roles;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/*importamos las ventanas de los jpaneles*/
import Restaurante_app.panels.Control_ventas;
import Restaurante_app.panels.Detalles_pedidos;
import Restaurante_app.panels.Gestion_menu;
import Restaurante_app.panels.Tomar_pedidos;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

public class Administrador extends JPanel {

	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
	private JPanel panelContenedor;/*Crearemos el jpanel donde se colocaran todos los demas paneles*/
	

	/**
	 * Create the panel.
	 */
	public Administrador(JPanel mainPanel, CardLayout mainCardLayout) {
		setBackground(new Color(255, 128, 0));
		this.setPreferredSize(new Dimension(360,660));
		setLayout(null);
		
		
		/*Creamos el panel para agregar los botones*/
        JPanel panelBotones = new JPanel(null);
        panelBotones.setBounds(0, 600, 360, 40);
        panelBotones.setBackground(Color.LIGHT_GRAY);
        add(panelBotones);

        /*Creamos los botones*/
        JToggleButton btnMenu = new JToggleButton("🛠️ Menú");
        JToggleButton btnTomar = new JToggleButton("Tomar Pedido");
        JToggleButton btnDetalle = new JToggleButton("Detalles");
        JToggleButton btnControl = new JToggleButton("Control Ventas");

        /*Agrupamos los botones*/
        ButtonGroup grupoBotones = new ButtonGroup();
        grupoBotones.add(btnMenu);
        grupoBotones.add(btnTomar);
        grupoBotones.add(btnDetalle);
        grupoBotones.add(btnControl);

        /*Le damos tamaños y ubicacion a los botones*/
        btnMenu.setBounds(10, 10, 80, 23);
        btnTomar.setBounds(95, 10, 80, 23);
        btnDetalle.setBounds(180, 10, 80, 23);
        btnControl.setBounds(265, 10, 90, 23);
        
        /*Agregamos los botones al panel de los botones*/
        panelBotones.add(btnMenu);
        panelBotones.add(btnTomar);
        panelBotones.add(btnDetalle);
        panelBotones.add(btnControl);

        /*Creamos una card layout que va a contener los panales*/
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBounds(0, 0, 360, 600); // Ocupa toda el área menos los botones
        add(panelContenedor);

        /*Creamos el panel de bienvenida*/
        JPanel bienvenidaPanel = new JPanel();
        bienvenidaPanel.setBackground(Color.WHITE);
        bienvenidaPanel.setLayout(null);

        
        JLabel lblBienvenida = new JLabel("Bienvenida Jefa");
        lblBienvenida.setFont(new Font("Britannic Bold", Font.PLAIN, 16));
        lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
        lblBienvenida.setBounds(80, 250, 200, 30);
        bienvenidaPanel.add(lblBienvenida);

        /*Agregamos los paneles al cardLayout y al contenedor*/
        panelContenedor.add(bienvenidaPanel, "Bienvenida");
        panelContenedor.add(new Gestion_menu(mainPanel, mainCardLayout), "Menu");
        panelContenedor.add(new Tomar_pedidos(), "Tomar");
        panelContenedor.add(new Detalles_pedidos(), "Detalle");
        panelContenedor.add(new Control_ventas(), "Control");

        /*Le agregamos acciones a los botones*/
        btnMenu.addActionListener(e -> cardLayout.show(panelContenedor, "Menu"));
        btnTomar.addActionListener(e -> cardLayout.show(panelContenedor, "Tomar"));
        btnDetalle.addActionListener(e -> cardLayout.show(panelContenedor, "Detalle"));
        btnControl.addActionListener(e -> cardLayout.show(panelContenedor, "Control"));

        // Mostrar panel por defecto
        cardLayout.show(panelContenedor, "Bienvenida");
    }
        
}
	
