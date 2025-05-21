package Restaurante_app.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Restaurante_app.DAO.ProductoDao;
import Restaurante_app.Models.Producto;
import Restaurante_app.Componentes.MenuDelDiaSGT;

public class Elmenu extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel panelMenu;

    public Elmenu() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(360, 640));
        
        List<Producto> productos = MenuDelDiaSGT.getInstance().getMenu();

        int y = 10; // Posición vertical inicial
        for (Producto producto : productos) {
            JLabel lblProducto = new JLabel(producto.getNombre()); // o producto.toString()
            lblProducto.setBounds(10, y, 300, 20); // Ajusta tamaño si necesitas
            add(lblProducto);
            y += 25; // Espaciado entre productos
        }

        panelMenu = new JPanel();
        panelMenu.setLayout(new BoxLayout(panelMenu, BoxLayout.Y_AXIS));
        panelMenu.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(panelMenu);
        add(scrollPane, BorderLayout.CENTER);

        actualizarContenido(); // Mostrar contenido al iniciar
    }

    public void actualizarContenido() {
        panelMenu.removeAll();  // Limpia el contenido anterior

        // Obtener productos disponibles para mostrar en el menú
        List<Producto> productosMenu = ProductoDao.obtenerProductosDelMenu();

        String categoriaActual = "";

        for (Producto p : productosMenu) {
            // Si la categoría cambió, agregamos un label para la categoría
            if (!p.getCategoria().equalsIgnoreCase(categoriaActual)) {
                categoriaActual = p.getCategoria();

                // Espacio antes de la nueva categoría
                panelMenu.add(Box.createVerticalStrut(15));

                JLabel categoriaLabel = new JLabel(categoriaActual.toUpperCase() + ":");
                categoriaLabel.setFont(new Font("Arial", Font.BOLD, 18));
                categoriaLabel.setForeground(new Color(30, 144, 255)); // Azul oscuro para destacar
                panelMenu.add(categoriaLabel);

                // Espacio debajo del título de categoría
                panelMenu.add(Box.createVerticalStrut(8));
            }

            // Panel para cada producto, para mejor control y posibles mejoras
            JPanel productoPanel = new JPanel();
            productoPanel.setLayout(new BoxLayout(productoPanel, BoxLayout.Y_AXIS));
            productoPanel.setBackground(Color.WHITE);

            // Nombre y precio en una línea
            JLabel nombrePrecio = new JLabel("• " + p.getNombre() + " - $" + String.format("%.2f", p.getPrecio()));
            nombrePrecio.setFont(new Font("Arial", Font.BOLD, 14));
            nombrePrecio.setForeground(Color.BLACK);
            productoPanel.add(nombrePrecio);

            // Descripción en línea separada, con fuente más pequeña y gris
            if (p.getDescripcion() != null && !p.getDescripcion().isEmpty()) {
                JLabel descripcion = new JLabel("   " + p.getDescripcion());
                descripcion.setFont(new Font("Arial", Font.ITALIC, 12));
                descripcion.setForeground(Color.DARK_GRAY);
                productoPanel.add(descripcion);
            }

            // Agregar espacio debajo de cada producto
            productoPanel.add(Box.createVerticalStrut(10));

            panelMenu.add(productoPanel);
        }

        panelMenu.revalidate();
        panelMenu.repaint();
    }
}

