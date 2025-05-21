package Restaurante_app.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Restaurante_app.BaseDatos.Conexion_BaseDatos;
import Restaurante_app.Models.Producto;

public class ProductoDao {
	public static List<Producto> obtenerProductosPorCategorias(String Categoria){
		List<Producto> productos = new ArrayList<>();
		String sql = "SELECT * FROM productos WHERE categoria = ?";
		
		try(Connection conn = Conexion_BaseDatos.conectar();
	        PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, Categoria);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                productos.add(new Producto(
                    rs.getInt("id_producto"),
                    rs.getString("nombre"),
                    rs.getDouble("precio"),
                    rs.getString("categoria"),
                    rs.getString("descripcion"),
                    rs.getInt("disponibilidad") == 1
                ));
            }
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return productos;
	}
	
	public static boolean agregarProducto(Producto producto) {
		String sql = "INSERT INTO productos (nombre, precio, categoria,descripcion) VALUES (?, ?, ?, ?)";
		try (Connection con = Conexion_BaseDatos.conectar();
		    PreparedStatement stmt = con.prepareStatement(sql)){
			stmt.setString(1, producto.getNombre());
	        stmt.setDouble(2, producto.getPrecio());
	        stmt.setString(3, producto.getCategoria());
	        stmt.setString(4, producto.getDescripcion());
	        return stmt.executeUpdate() > 0;
			
		}catch(SQLException e) {
			e.printStackTrace();
	        return false;
		}
		
	}
	
	public static boolean EliminarProducto(int id) {
	    String sql = "DELETE FROM productos WHERE id_producto = ?";
	    
	    try (Connection conn = Conexion_BaseDatos.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	         
	        stmt.setInt(1, id);
	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static boolean actualizarProducto(Producto producto) {
	    String sql = "UPDATE productos SET nombre = ?, precio = ?, categoria = ?, descripcion = ? WHERE id_producto = ?";

	    try (Connection conn = Conexion_BaseDatos.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, producto.getNombre());
	        stmt.setDouble(2, producto.getPrecio());
	        stmt.setString(3, producto.getCategoria());
	        stmt.setString(4, producto.getDescripcion());
	        stmt.setInt(5, producto.getId());

	        int filasAfectadas = stmt.executeUpdate();
	        return filasAfectadas > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static List<Producto> obtenerProductosDelMenu() {
	    List<Producto> productos = new ArrayList<>();
	    String sql = "SELECT * FROM productos WHERE disponibilidad = 1 ORDER BY categoria, nombre";

	    try (Connection conn = Conexion_BaseDatos.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            productos.add(new Producto(
	                rs.getInt("id_producto"),
	                rs.getString("nombre"),
	                rs.getDouble("precio"),
	                rs.getString("categoria"),
	                rs.getString("descripcion"),
	                rs.getBoolean("disponibilidad")
	            ));
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return productos;
	}
	
	public static boolean actualizarDisponibilidad(int idProducto, boolean disponible) {
	    String sql = "UPDATE productos SET disponibilidad = ? WHERE id_producto = ?";
	    
	    try (Connection conn = Conexion_BaseDatos.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setBoolean(1, disponible);
	        stmt.setInt(2, idProducto);

	        return stmt.executeUpdate() > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public static void marcarTodosComoNoDisponibles(String categoria) {
	    String sql = "UPDATE productos SET disponibilidad = false WHERE categoria = ?";
	    try (Connection conn = Conexion_BaseDatos.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, categoria);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static void resetearDisponibilidadProductos() {
	    String sql = "UPDATE productos SET disponibilidad = 0";

	    try (Connection conn = Conexion_BaseDatos.conectar();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.executeUpdate();
	        System.out.println("Disponibilidad de productos reseteada.");

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public static boolean guardarMenuDelDia(List<Integer> idsSeleccionados) throws SQLException {
	    String desmarcarTodos = "UPDATE productos SET disponibilidad = 0";
	    String marcarSeleccionados = "UPDATE productos SET disponibilidad = 1 WHERE id_producto = ?";

	    try (Connection conn = Conexion_BaseDatos.conectar()) {
	        // Primero, desmarcamos todos
	        try (PreparedStatement stmt1 = conn.prepareStatement(desmarcarTodos)) {
	            stmt1.executeUpdate();
	        }

	        // Luego, marcamos los seleccionados
	        try (PreparedStatement stmt2 = conn.prepareStatement(marcarSeleccionados)) {
	            for (Integer id : idsSeleccionados) {
	                stmt2.setInt(1, id);
	                stmt2.executeUpdate();
	            }
	            int[] resultados = stmt2.executeBatch();
	            
	         // Verificar que todas las actualizaciones fueron exitosas
	            for (int resultado : resultados) {
	                if (resultado == PreparedStatement.EXECUTE_FAILED) {
	                    return false;
	                }
	        }
	            return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	}
}
