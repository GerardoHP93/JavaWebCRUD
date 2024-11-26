<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!-- Declaración de la librería JSTL para usar etiquetas como "c:if" y "c:forEach" -->
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Productos</title>
        <link rel="stylesheet" href="/JavaWebCRUD/css/styles.css"> <!-- Enlace al archivo CSS para estilos -->
    </head>
    <body>
        <!-- Mostrar un mensaje de bienvenida si la sesión contiene el atributo "username" -->
        <c:if test="${not empty sessionScope.username}">
            <p class="mensaje-bienvenida">Bienvenido, ${sessionScope.username}!</p> <!-- Saludo personalizado con el nombre del usuario -->
        </c:if>

        <h1>Gestión de Productos</h1>

        <!-- Enlace para insertar un nuevo producto -->
        <a href="ProductoControlador?accion=nuevo" class="btn btn-insertar">Insertar Producto</a>
        
        <!-- Incluir el archivo "Mensaje.jsp" para mostrar notificaciones como errores o mensajes de éxito -->
        <jsp:include page="../componentes/Mensaje.jsp" />

        <!-- Tabla que muestra la lista de productos -->
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th> 
                    <th>Categoría</th>
                    <th>Cantidad</th> 
                    <th>Acción</th> 
                </tr>
            </thead>
            <tbody>
                <!-- Iterar sobre la lista de productos usando "c:forEach" -->
                <c:forEach items="${productos}" var="item">
                    <tr>
                        <td>${item.id}</td> <!-- Mostrar el ID del producto -->
                        <td>${item.nombre}</td> <!-- Mostrar el nombre del producto -->
                        <td>${item.categoria}</td> <!-- Mostrar la categoría del producto -->
                        <td>${item.cantidad}</td> <!-- Mostrar la cantidad del producto -->
                        <td>
                            <!-- Enlace para editar el producto, pasando el ID como parámetro -->
                            <a href="ProductoControlador?accion=editar&id=${item.id}" class="btn">Editar</a>

                            <!-- Enlace para eliminar el producto, con confirmación antes de proceder -->
                            <a href="ProductoControlador?accion=eliminar&id=${item.id}" 
                               onclick="return confirm('¿Estás seguro de que deseas eliminar el Producto: ${item.nombre}?')" 
                               class="btn btn-danger">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>

                <!-- Mostrar un mensaje si no hay productos en la lista -->
                <c:if test="${productos.size() == 0}">
                    <tr>
                        <td colspan="5">No hay productos registrados.</td> <!-- Mensaje ocupando las 5 columnas -->
                    </tr>
                </c:if>
            </tbody>
        </table>

        <!-- Enlace para cerrar sesión, redirige al servlet LogoutServlet -->
        <a href="LogoutServlet" class="btn btn-danger">Cerrar Sesión</a>

    </body>
</html>
