package Restaurante_app;

import java.awt.CardLayout;
import java.awt.EventQueue;
import java.sql.*;
import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Restaurante_app.DAO.ProductoDao;

import Restaurante_app.roles.Administrador;
import Restaurante_app.roles.Cocinero;
import Restaurante_app.roles.Mesero;
import Restaurante_app.roles.Mesero_Auxiliar;

public class InicioRoles extends JFrame {

    private static final long serialVersionUID = 1L;

    private CardLayout cardLayout;
    private JPanel contenedor_panel;

    private JComboBox<String> usuarios;
    private JPasswordField ingresar_contraseña;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                InicioRoles frame = new InicioRoles();
                frame.setSize(360, 640);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public InicioRoles() {
        setTitle("Restaurante App");

        // Configurar el CardLayout principal
        cardLayout = new CardLayout();
        contenedor_panel = new JPanel(cardLayout);
        setContentPane(contenedor_panel);

        // Paneles de los roles
        JPanel loginPanel = crearLoginPanel();
        Administrador adminPanel = new Administrador(contenedor_panel, cardLayout);
        Mesero meseroPanel = new Mesero();
        Mesero_Auxiliar meseroAuxiliarPanel = new Mesero_Auxiliar();
        Cocinero cocineroPanel = new Cocinero();

        // Agregar al contenedor con nombres de tarjeta
        contenedor_panel.add(loginPanel, "Login");
        contenedor_panel.add(adminPanel, "Administrador");
        contenedor_panel.add(meseroPanel, "Mesero");
        contenedor_panel.add(meseroAuxiliarPanel, "Mesero Auxiliar");
        contenedor_panel.add(cocineroPanel,"Cocinero");

        // Mostrar login al iniciar
        cardLayout.show(contenedor_panel, "Login");
        setSize(360,640);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(
                    InicioRoles.this,
                    "¿Deseas salir? Se reiniciará el menú del día.",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    // Llamar al método que resetea la disponibilidad
                    ProductoDao.resetearDisponibilidadProductos();

                    // Cerrar la aplicación
                    System.exit(0);
                }
            }
        });
    }

    private JPanel crearLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel titulo_ElegirRol = new JLabel("Seleccione el Rol");
        titulo_ElegirRol.setBounds(127, 46, 103, 14);
        panel.add(titulo_ElegirRol);

        usuarios = new JComboBox<>();
        usuarios.setBounds(97, 71, 150, 20);
        panel.add(usuarios);

        JLabel titulo_ingresarPassword = new JLabel("Ingrese Contraseña");
        titulo_ingresarPassword.setBounds(127, 102, 120, 14);
        panel.add(titulo_ingresarPassword);

        ingresar_contraseña = new JPasswordField();
        ingresar_contraseña.setBounds(97, 122, 150, 20);
        panel.add(ingresar_contraseña);

        JButton btnIniciarSecion = new JButton("Iniciar Sesión");
        btnIniciarSecion.setBounds(116, 173, 120, 23);
        panel.add(btnIniciarSecion);
        btnIniciarSecion.addActionListener(e -> autenticarUsuarios());

        // Cargar los usuarios al iniciar
        try (Connection base_datos = DriverManager.getConnection(
                     "jdbc:mysql://localhost:3306/restaurante_app",
                     "usuario_restaurante",
                     "usuario_2025");
             Statement stmt = base_datos.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nombre FROM usuarios")) {

            while (rs.next()) {
                usuarios.addItem(rs.getString("nombre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Error al conectar o consultar la base de datos");
        }

        return panel;
    }

    private void autenticarUsuarios() {
        String user = (String) usuarios.getSelectedItem();
        String passInput = String.valueOf(ingresar_contraseña.getPassword());

        String url = "jdbc:mysql://localhost:3306/restaurante_app";
        String sql = "SELECT contraseña, nombre FROM usuarios WHERE nombre = ?";

        try (Connection conn = DriverManager.getConnection(
                     url,
                     "usuario_restaurante",
                     "usuario_2025");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String passDB = rs.getString("contraseña");
                    String rol = rs.getString("nombre");

                    if (passInput.equals(passDB)) {
                    	cambiarPanelPorRol(rol);
                    } else {
                        JOptionPane.showMessageDialog(this, "Contraseña incorrecta");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario no encontrado");
                }

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error BD: " + ex.getMessage());
        }
    }
    
    private void cambiarPanelPorRol(String rol) {
        switch (rol.toLowerCase()) {
            case "administrador":
                cardLayout.show(contenedor_panel, "Administrador");
                setSize(375, 678);
                break;
            case "mesero":
                cardLayout.show(contenedor_panel, "Mesero");
                setSize(360, 640);
                break;
            case "mesero auxiliar":
                cardLayout.show(contenedor_panel, "Mesero Auxiliar");
                setSize(800, 600);
                break;
            case "cocinero":
            	cardLayout.show(contenedor_panel, "Cocinero");
                setSize(800, 600);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Rol no reconocido aún.");
                return;
        }
        setLocationRelativeTo(null);
        revalidate();
        repaint();
        JOptionPane.showMessageDialog(this, "Bienvenido, rol: " + rol);
    }
}


