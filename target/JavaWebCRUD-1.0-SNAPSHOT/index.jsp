<%-- 
    Document   : index
    Created on : 18 nov. 2024, 22:20:18
    Author     : Gerardo Herrera Pacheco ISC 7"B"
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Redirección</title>
    </head>
    <body>
        <%
            // Usar la variable implícita 'session' directamente
            if (session != null && session.getAttribute("username") != null) {
                // Si el usuario está autenticado, redirige al controlador
                response.sendRedirect("ProductoControlador?accion=listar");
            } else {
                // Si no está autenticado, redirige al login
                response.sendRedirect("vista/login.jsp");
            }
        %>
    </body>
</html>
    </body>
</html>
