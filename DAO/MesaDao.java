package Restaurante_app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Restaurante_app.BaseDatos.Conexion_BaseDatos;
import Restaurante_app.Models.Mesa;

public class MesaDao {
	public static List<Mesa> obtenerMesas() {
        List<Mesa> mesas = new ArrayList<>();
        String sql = "SELECT id_mesa, nombre_mesa, estado FROM mesas ORDER BY id_mesa";

        try (Connection conn = Conexion_BaseDatos.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_mesa");
                String nombre = rs.getString("nombre_mesa");
                String estado = rs.getString("estado");
                mesas.add(new Mesa(id, nombre, estado));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mesas;
    }

    /**
     * Actualiza el estado de una mesa.
     */
    public static boolean actualizarEstadoMesa(int idMesa, String nuevoEstado) {
        String sql = "UPDATE mesas SET estado = ? WHERE id_mesa = ?";

        try (Connection conn = Conexion_BaseDatos.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idMesa);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
