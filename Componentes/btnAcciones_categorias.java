package Restaurante_app.Componentes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Restaurante_app.DAO.ProductoDao;
import Restaurante_app.Models.Producto;

public class btnAcciones_categorias extends JPanel {

    private static final long serialVersionUID = 1L;
    protected String categoria;
    public JPanel panelProductos;
    protected Map<Integer, JCheckBox> checkboxes = new HashMap<>();
    protected List<Producto> listaProductos = new ArrayList<>();
    

    public btnAcciones_categorias(String categoria) {
        this.categoria = categoria;
        this.setName(categoria);
        this.setPreferredSize(new Dimension(360, 640));
        setLayout(null);

        // Panel para mostrar productos
        panelProductos = new JPanel();
        panelProductos.setLayout(new BoxLayout(panelProductos, BoxLayout.Y_AXIS));
        panelProductos.setBackground(Color.WHITE);
        panelProductos.setAlignmentX(LEFT_ALIGNMENT);
        panelProductos.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JScrollPane scrollPane = new JScrollPane(panelProductos);
        scrollPane.setBounds(10, 50, 340, 540);
        add(scrollPane);

        // Botones
        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(162, 23, 71, 23);
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });
        add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(82, 23, 78, 23);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });
        add(btnEliminar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(10, 23, 71, 23);
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarProducto();
            }
        });
        add(btnEditar);

        recargarProductos();
    }

    protected void recargarProductos() {
        panelProductos.removeAll();
        checkboxes.clear();
        listaProductos = ProductoDao.obtenerProductosPorCategorias(categoria);

        for (Producto p : listaProductos) {
            JCheckBox checkBox = new JCheckBox(p.getNombre() + " - $" + p.getPrecio());
            checkBox.setAlignmentX(LEFT_ALIGNMENT);
            panelProductos.add(checkBox);
            checkboxes.put(p.getId(), checkBox);
        }

        panelProductos.revalidate();
        panelProductos.repaint();
    }

    protected Producto getProductoSeleccionado() {
        for (int i = 0; i < checkboxes.size(); i++) {
            if (checkboxes.get(i).isSelected()) {
                return listaProductos.get(i);
            }
        }
        return null;
    }

    protected void agregarProducto() {
        String nombre = JOptionPane.showInputDialog("Nombre del producto:");
        String precioStr = JOptionPane.showInputDialog("Precio del producto:");
        String descripcion = JOptionPane.showInputDialog("Descripción del producto:");
        try {
            double precio = Double.parseDouble(precioStr);
            Producto nuevo = new Producto(nombre, precio, categoria, descripcion);
            if (ProductoDao.agregarProducto(nuevo)) {
                JOptionPane.showMessageDialog(null, "Producto agregado");
                recargarProductos();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Precio inválido");
        }
    }

    protected void eliminarProducto() {
        Producto seleccionado = getProductoSeleccionado();
        if (seleccionado != null) {
            int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                if (ProductoDao.EliminarProducto(seleccionado.getId())) {
                    JOptionPane.showMessageDialog(null, "Producto eliminado");
                    recargarProductos();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto para eliminar");
        }
    }

    protected void editarProducto() {
        Producto seleccionado = getProductoSeleccionado();
        if (seleccionado != null) {
            String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre:", seleccionado.getNombre());
            String nuevoPrecioStr = JOptionPane.showInputDialog("Nuevo precio:", seleccionado.getPrecio());
            String nuevaDescripcion = JOptionPane.showInputDialog("Nueva descripción:", seleccionado.getDescripcion());
            try {
                double nuevoPrecio = Double.parseDouble(nuevoPrecioStr);
                seleccionado.setNombre(nuevoNombre);
                seleccionado.setPrecio(nuevoPrecio);
                seleccionado.setDescripcion(nuevaDescripcion);

                if (ProductoDao.actualizarProducto(seleccionado)) {
                    JOptionPane.showMessageDialog(null, "Producto actualizado");
                    recargarProductos();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Precio inválido");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona un producto para editar");
        }
    }
    
    protected void agregarAlMenuDelDia() {
    	boolean algunoSeleccionado = false;
    	
    	// 1. Primero marcamos todos los productos de esta categoría como NO disponibles
        ProductoDao.marcarTodosComoNoDisponibles(categoria);
        
        for (int i = 0; i < listaProductos.size(); i++) {
        	if (checkboxes.get(i).isSelected()) {
                Producto producto = listaProductos.get(i);
                ProductoDao.actualizarDisponibilidad(producto.getId(), true);
                algunoSeleccionado = true;
            }
        }

        if (algunoSeleccionado) {
            JOptionPane.showMessageDialog(null, "Menú del día actualizado");
        } else {
            JOptionPane.showMessageDialog(null, "No has seleccionado ningún producto para el menú del día");
        }
    }
    public List<Producto> getProductosSeleccionados() {
        List<Producto> seleccionados = new ArrayList<>();

        for (int i = 0; i < checkboxes.size(); i++) {
            if (checkboxes.get(i).isSelected()) {
                seleccionados.add(listaProductos.get(i));
            }
        }

        return seleccionados;
    }
}