
package com.uacam.javawebcrud.util;

import com.uacam.javawebcrud.modelo.dao.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Gerardo Herrera
 * Este servlet es un script temporal diseñado para registrar usuarios de prueba en la base de datos.
 *
 */
@WebServlet(name = "UsuarioScriptServlet", urlPatterns = {"/UsuarioScriptServlet"})
public class UsuarioScriptServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    //Este método se ejecuta al acceder a la URL del servlet, por ejemplo:
    // localhost:8080/JavaWebCRUD/UsuarioScriptServlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // Llama al método registrarUsuario para agregar el usuario admin con contraseña 12345 a la base de datos.
        boolean adminRegistrado = usuarioDAO.registrarUsuario("admin", "12345");

        //Si el registro tiene éxito, muestra un mensaje en texto plano.
        //Si falla, muestra un mensaje de error.
        response.setContentType("text/plain");
        response.getWriter().println(adminRegistrado
                ? "Usuario 'admin' registrado correctamente."
                : "Error al registrar al usuario 'admin'.");

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
