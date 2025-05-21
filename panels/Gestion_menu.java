package Restaurante_app.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import Restaurante_app.Componentes.MenuDelDiaSGT;
import Restaurante_app.DAO.ProductoDao;
import Restaurante_app.Models.Producto;

public class Gestion_menu extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel panelContenedor;
    private CardLayout cardLayoutCategorias;

    // Referencia al panel principal que controla la app entera (para cambiar roles/panels)
    private JPanel mainPanel;
    private CardLayout mainCardLayout;

    // Subpaneles para las categorías
    private especiales panelEspeciales;
    private corrientes panelCorrientes;
    private principio panelPrincipio;
    private sopas panelSopas;

    // Constructor recibe el panel principal y su CardLayout para cambios globales
    public Gestion_menu(JPanel mainPanel, CardLayout mainCardLayout) {
        this.mainPanel = mainPanel;
        this.mainCardLayout = mainCardLayout;

        this.setName("Gestion del menu");
        this.setPreferredSize(new Dimension(360, 640));
        setLayout(null);
        
        
        JPanel panelBotones = new JPanel(null);
        panelBotones.setBounds(0, 0, 360, 40);
        panelBotones.setBackground(Color.LIGHT_GRAY);
        add(panelBotones);

        // Creamos los botones para cambiar categorías
        JToggleButton btnEspeciales = new JToggleButton("Especial");
        JToggleButton btnCorrientes = new JToggleButton("Corrientes");
        JToggleButton btnPrincipio = new JToggleButton("Principio");
        JToggleButton btnSopas = new JToggleButton("Sopas");

        ButtonGroup grupoBotones = new ButtonGroup();
        grupoBotones.add(btnEspeciales);
        grupoBotones.add(btnCorrientes);
        grupoBotones.add(btnPrincipio);
        grupoBotones.add(btnSopas);

        btnEspeciales.setBounds(10, 10, 80, 23);
        btnCorrientes.setBounds(95, 10, 80, 23);
        btnPrincipio.setBounds(180, 10, 80, 23);
        btnSopas.setBounds(265, 10, 90, 23);

        panelBotones.add(btnEspeciales);
        panelBotones.add(btnCorrientes);
        panelBotones.add(btnPrincipio);
        panelBotones.add(btnSopas);

        // Panel contenedor con CardLayout para categorías
        cardLayoutCategorias = new CardLayout();
        panelContenedor = new JPanel(cardLayoutCategorias);
        panelContenedor.setBounds(0, 40, 360, 560);
        add(panelContenedor);

        // Instanciamos los subpaneles
        panelEspeciales = new especiales();
        panelCorrientes = new corrientes();
        panelPrincipio = new principio();
        panelSopas = new sopas();

        panelContenedor.add(panelEspeciales, "Especiales");
        panelContenedor.add(panelCorrientes, "Corrientes");
        panelContenedor.add(panelPrincipio, "Principio");
        panelContenedor.add(panelSopas, "Sopas");
        
                // Botón Guardar Menú del Día (fuera de panelBotones, visible siempre)
                JButton btnGuardarMenu = new JButton("Guardar día");
                btnGuardarMenu.setBounds(242, 11, 108, 30);
                panelSopas.add(btnGuardarMenu);
                
                        btnGuardarMenu.addActionListener(e -> {
                            List<Producto> menuDelDia = new ArrayList<>();
                
                            // Obtener productos seleccionados de cada categoría
                            menuDelDia.addAll(panelEspeciales.getProductosSeleccionados());
                            menuDelDia.addAll(panelCorrientes.getProductosSeleccionados());
                            menuDelDia.addAll(panelPrincipio.getProductosSeleccionados());
                            menuDelDia.addAll(panelSopas.getProductosSeleccionados());
                
                            // Extraer IDs para guardar en BD
                            List<Integer> idsSeleccionados = new ArrayList<>();
                            for (Producto p : menuDelDia) {
                                idsSeleccionados.add(p.getId());
                            }
                
                            // Guardar menú del día en la base de datos
                            ProductoDao.guardarMenuDelDia(idsSeleccionados);
                
                            // Cambiar a panel Mesero en el panel principal
                            mainCardLayout.show(mainPanel, "panelMesero");
                
                            // Actualizar contenido del panel Elmenu si existe
                            for (Component comp : mainPanel.getComponents()) {
                                if (comp instanceof Elmenu) {
                                    ((Elmenu) comp).actualizarContenido();
                                    break;
                                }
                            }
                
                            // Guardar menú en singleton para consulta global
                            MenuDelDiaSGT.getInstance().setMenu(menuDelDia);
                
                            System.out.println("Menú del día guardado con " + menuDelDia.size() + " productos.");
                        });
        

        btnEspeciales.addActionListener(e -> cardLayoutCategorias.show(panelContenedor, "Especiales"));
        btnCorrientes.addActionListener(e -> cardLayoutCategorias.show(panelContenedor, "Corrientes"));
        btnPrincipio.addActionListener(e -> cardLayoutCategorias.show(panelContenedor, "Principio"));
        btnSopas.addActionListener(e -> cardLayoutCategorias.show(panelContenedor, "Sopas"));

        // Mostrar panel por defecto
        cardLayoutCategorias.show(panelContenedor, "Especiales");
        btnEspeciales.setSelected(true);
    }
}
