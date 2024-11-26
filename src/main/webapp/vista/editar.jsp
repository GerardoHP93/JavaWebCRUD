<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Editar Producto</title>
        <link rel="stylesheet" href="/JavaWebCRUD/css/styles.css">
    </head>
    <body>
        <div class="form-container">
            <h2>Editar Producto</h2>
            <form action="ProductoControlador" method="post">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" value="${producto.nombre}" required>

                <label for="categoria">Categoría:</label>
                <input type="text" id="categoria" name="categoria" value="${producto.categoria}" required>

                <label for="cantidad">Cantidad:</label>
                <input type="number" id="cantidad" name="cantidad" value="${producto.cantidad}" required>

                <input type="hidden" name="id" value="${producto.id}">
                <input type="hidden" name="accion" value="guardar">
                <input type="submit" value="Actualizar Producto" class="submit-btn">
            </form>
        </div>
    </body>
</html>

