package Restaurante_app.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;

public class Tomar_pedidos extends JPanel {

    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout;
    private JPanel panelContenedor;
    private Elmenu elMenu; // <- Nombre en minúscula para distinguir instancia
    private Elpedido elPedido;

    public Tomar_pedidos() {
        this.setName("Tomar_pedidos");
        this.setPreferredSize(new Dimension(360, 640));
        setLayout(null);

        // Panel superior de botones
        JPanel panelBotones = new JPanel(null);
        panelBotones.setBounds(0, 0, 360, 40);
        panelBotones.setBackground(Color.LIGHT_GRAY);
        add(panelBotones);

        // Botones de navegación
        JToggleButton btnElmenu = new JToggleButton("El Menú");
        JToggleButton btnPedido = new JToggleButton("Pedido");

        // Agrupar los botones
        ButtonGroup grupoBotones = new ButtonGroup();
        grupoBotones.add(btnElmenu);
        grupoBotones.add(btnPedido);

        // Posicionar botones
        btnElmenu.setBounds(10, 10, 80, 23);
        btnPedido.setBounds(95, 10, 80, 23);
        panelBotones.add(btnElmenu);
        panelBotones.add(btnPedido);

        // Panel contenedor con CardLayout
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);
        panelContenedor.setBounds(0, 40, 360, 560);
        add(panelContenedor);

        // Instanciar Elmenu y agregarlo al panel con clave
        elMenu = new Elmenu();
        elPedido = new Elpedido();
        panelContenedor.add(elMenu, "menuDelDia");
        panelContenedor.add(elPedido, "elPedido");

        // Acción para mostrar el menú del día
        btnElmenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (elMenu != null) {
                    elMenu.actualizarContenido(); // Actualiza contenido antes de mostrar
                }
                cardLayout.show(panelContenedor, "menuDelDia");
            }
        });
     // Acción para mostrar el menú del día
        btnPedido.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(panelContenedor, "elPedido");
            }
        });

        // Aquí puedes agregar más paneles como "panelPedido" si lo necesitas luego
    }
}

