package com.uacam.javawebcrud.controlador;

import com.uacam.javawebcrud.modelo.dao.UsuarioDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Date;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Gerardo Herrera Pacheco ISC 7"B"
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private static final String SECRET_KEY = "miClaveJWTSecretaYSeguraParaValidacion12345"; // Clave fija

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Obtener el hash de la contraseña almacenada en la base de datos
        String hashedPassword = usuarioDAO.obtenerHashPassword(username);
        
        //Se utiliza BCrypt.checkpw para comparar la contraseña ingresada por el usuario (password) con 
        //el hash almacenado en la base de datos (hashedPassword).
        if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
            // Generar el token JWT Este token contiene información del usuario y una firma para garantizar su autenticidad.
            String token = Jwts.builder()
                    .setSubject(username) // Establece el nombre del usuario como el sujeto
                    .setIssuedAt(new Date()) // Fecha de emisión
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hora de validez
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())// Firma con clave secreta
                    .compact();

            // Guardar información del usuario en la sesión, servirá luego para mostrar la bienvenida del usuario
            //Porque Esto permite acceder al nombre del usuario en las vistas o en otros servlets.
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Enviar el token como una cookie al cliente
            //El token se almacena en una cookie para que el cliente lo envíe automáticamente en 
            //cada solicitud posterior.
            Cookie jwtCookie = new Cookie("token", token);
            jwtCookie.setHttpOnly(true); //Evita que JavaScript acceda a la cookie
            jwtCookie.setMaxAge(3600); // 1 hora
            response.addCookie(jwtCookie);

            // Si el login es exitoso, se redirige al servlet ProductoControlador:
            //Si las credenciales son incorrectas, se redirige al formulario de login con un mensaje de error
            response.sendRedirect("ProductoControlador?accion=listar");
        } else {
            response.sendRedirect("vista/login.jsp?error=Credenciales invalidas");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
