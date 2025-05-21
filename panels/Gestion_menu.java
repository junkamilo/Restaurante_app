package Restaurante_app.panels;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JOptionPane;

import Restaurante_app.DAO.ProductoDao;
import Restaurante_app.Models.Producto;
import Restaurante_app.Componentes.MenuDelDiaSGT;

public class Gestion_menu extends JPanel {

    private static final long serialVersionUID = 1L;

    private JPanel panelContenedor;
    private CardLayout cardLayoutCategorias;

    // Referencia al panel principal y su CardLayout para poder cambiar pantalla
    private JPanel mainPanel;
    private CardLayout mainCardLayout;

    // Subpaneles de categorías
    private especiales panelEspeciales;
    private corrientes panelCorrientes;
    private principio panelPrincipio;
    private sopas panelSopas;

    public Gestion_menu(JPanel mainPanel, CardLayout mainCardLayout) {
        this.mainPanel = mainPanel;
        this.mainCardLayout = mainCardLayout;

        setPreferredSize(new Dimension(360, 640));
        setLayout(null);

        // 1) Botones de categoría
        JPanel panelBotones = new JPanel(null);
        panelBotones.setBounds(0, 0, 360, 40);
        panelBotones.setBackground(Color.LIGHT_GRAY);
        add(panelBotones);

        JToggleButton btnEspeciales = new JToggleButton("Especial");
        JToggleButton btnCorrientes = new JToggleButton("Corrientes");
        JToggleButton btnPrincipio  = new JToggleButton("Principio");
        JToggleButton btnSopas      = new JToggleButton("Sopas");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(btnEspeciales);
        grupo.add(btnCorrientes);
        grupo.add(btnPrincipio);
        grupo.add(btnSopas);

        btnEspeciales.setBounds(10, 10, 80, 23);
        btnCorrientes.setBounds(95, 10, 80, 23);
        btnPrincipio.setBounds(180, 10, 80, 23);
        btnSopas.setBounds(265, 10, 90, 23);

        panelBotones.add(btnEspeciales);
        panelBotones.add(btnCorrientes);
        panelBotones.add(btnPrincipio);
        panelBotones.add(btnSopas);

        // 2) Panel contenedor con los subpaneles
        cardLayoutCategorias = new CardLayout();
        panelContenedor = new JPanel(cardLayoutCategorias);
        panelContenedor.setBounds(0, 40, 360, 560);
        add(panelContenedor);

        panelEspeciales = new especiales();
        panelCorrientes = new corrientes();
        panelPrincipio  = new principio();
        panelSopas      = new sopas();

        panelContenedor.add(panelEspeciales, "Especiales");
        panelContenedor.add(panelCorrientes, "Corrientes");
        panelContenedor.add(panelPrincipio, "Principio");
        panelContenedor.add(panelSopas, "Sopas");

        btnEspeciales.addActionListener(e -> cardLayoutCategorias.show(panelContenedor, "Especiales"));
        btnCorrientes.addActionListener(e -> cardLayoutCategorias.show(panelContenedor, "Corrientes"));
        btnPrincipio.addActionListener(e -> cardLayoutCategorias.show(panelContenedor, "Principio"));
        btnSopas.addActionListener(e -> cardLayoutCategorias.show(panelContenedor, "Sopas"));

        cardLayoutCategorias.show(panelContenedor, "Especiales");
        btnEspeciales.setSelected(true);

        // 3) Botón GUARDAR DÍA, siempre visible
        JButton btnGuardarMenu = new JButton("Guardar día");
        btnGuardarMenu.setBounds(250, 604, 100, 25);
        add(btnGuardarMenu);

        btnGuardarMenu.addActionListener(e -> {
            // 4) Obtener selecciones
            List<Producto> especiales = panelEspeciales.getProductosSeleccionados();
            List<Producto> corrientes = panelCorrientes.getProductosSeleccionados();
            List<Producto> principio  = panelPrincipio.getProductosSeleccionados();
            List<Producto> sopas      = panelSopas.getProductosSeleccionados();

            // 5) Validar
            if (!validarSelecciones(especiales, corrientes, principio, sopas)) {
                return;
            }

            // 6) Armar lista e IDs
            List<Producto> menuDelDia = new ArrayList<>();
            menuDelDia.addAll(especiales);
            menuDelDia.addAll(corrientes);
            menuDelDia.addAll(principio);
            menuDelDia.addAll(sopas);

            List<Integer> idsSeleccionados = new ArrayList<>();
            for (Producto p : menuDelDia) {
                idsSeleccionados.add(p.getId());
            }

            // 7) Guardar en BD
            try {
                if (ProductoDao.guardarMenuDelDia(idsSeleccionados)) {
                    JOptionPane.showMessageDialog(this,
                        "Menú del día guardado exitosamente!",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);

                    // 8) Cambiar a panel de pedidos
                    mainCardLayout.show(mainPanel, "panelMesero");
                    actualizarPanelElpedido();

                    // 9) Actualizar menú de mesero
                    actualizarPanelElmenu();

                    // 10) Guardar en singleton
                    MenuDelDiaSGT.getInstance().setMenu(menuDelDia);

                } else {
                    JOptionPane.showMessageDialog(this,
                        "Error al guardar el menú en la base de datos",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void actualizarPanelElpedido() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof Elpedido) {
                ((Elpedido) comp).cargarDatos();
                break;
            }
        }
    }

    private void actualizarPanelElmenu() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof Elmenu) {
                ((Elmenu) comp).actualizarContenido();
                break;
            }
        }
    }

    private boolean validarSelecciones(List<Producto> especiales,
                                       List<Producto> corrientes,
                                       List<Producto> principio,
                                       List<Producto> sopas) {
        if (especiales.isEmpty()) {
            mostrarError("Selecciona al menos un plato especial", "Especiales");
            return false;
        }
        if (corrientes.isEmpty()) {
            mostrarError("Selecciona al menos un plato corriente", "Corrientes");
            return false;
        }
        if (principio.isEmpty()) {
            mostrarError("Selecciona al menos un Principio", "Principio");
            return false;
        }
        if (sopas.isEmpty()) {
            mostrarError("Selecciona al menos una Sopa", "Sopas");
            return false;
        }
        return true;
    }

    private void mostrarError(String mensaje, String panel) {
        JOptionPane.showMessageDialog(this, mensaje, "Validación", JOptionPane.WARNING_MESSAGE);
        cardLayoutCategorias.show(panelContenedor, panel);
    }
}

