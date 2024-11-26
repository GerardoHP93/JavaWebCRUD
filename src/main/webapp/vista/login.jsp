<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <!-- Enlace al archivo CSS para los estilos -->
        <link rel="stylesheet" href="/JavaWebCRUD/css/styles.css">
    </head>
    <body>
        <!-- Contenedor para el formulario de inicio de sesión -->
        <div class="form-container">
            <h2>Iniciar Sesión</h2>
                        <jsp:include page="../componentes/Mensaje.jsp" />


            <!-- Formulario para enviar las credenciales al LoginServlet -->
            <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                <label for="username">Usuario:</label>
                <input type="text" id="username" name="username" required>

                <label for="password">Contraseña:</label>
                <input type="password" id="password" name="password" required>

                <input type="submit" value="Iniciar Sesión" class="submit-btn">
            </form>

        </div>
    </body>
</html>

