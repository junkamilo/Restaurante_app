package Restaurante_app.BaseDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion_BaseDatos {
	public static Connection conectar() {
		try {
			String url = "jdbc:mysql://localhost:3306/restaurante_app";
			String user = "usuario_restaurante";
			String pass = "usuario_2025";
			return DriverManager.getConnection(url, user, pass);
		}catch(SQLException e){
			e.printStackTrace();
            return null;
		}
	}
}
