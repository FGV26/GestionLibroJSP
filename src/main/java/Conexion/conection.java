package Conexion;

import Dao.LibroDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class conection {
    public static void main(String[] args) {

        // Aquí puedes probar la conexión a la base de datos
        try {
            Connection conn = Conexion.getConnection();
            System.out.println("Conexión exitosa a la base de datos.");
            Conexion.close(conn);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }


    }
}
