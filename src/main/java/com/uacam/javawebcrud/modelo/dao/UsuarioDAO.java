package com.uacam.javawebcrud.modelo.dao;

import com.uacam.javawebcrud.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {
    //Este método registra un usuario en la base de datos con su contraseña hasheada usando BCrypt.
    public boolean registrarUsuario(String username, String password) {
        try (Connection conn = Conexion.getConnection()) {
            // BCrypt.hashpw toma la contraseña en texto plano y genera un hash seguro.
            //BCrypt.gensalt() crea un "salt" único que aumenta la seguridad del hash.
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            //Insertar el usuario en la base de datos
            String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, hashedPassword);
                stmt.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Método para obtener el hash de la contraseña para compararlo con la contraseña ingresada por 
    //el usuario durante el inicio de sesión.
    public String obtenerHashPassword(String username) {
        try (Connection conn = Conexion.getConnection()) {
            //Busca la contraseña del usuario dado (username) usando una consulta preparada.
            String sql = "SELECT password FROM usuarios WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
    //Si se encuentra un resultado, se devuelve el hash de la contraseña. De lo contrario, devuelve null.
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("password");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

