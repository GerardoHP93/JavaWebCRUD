<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Nuevo Producto</title>
        <link rel="stylesheet" href="/JavaWebCRUD/css/styles.css">
    </head>
    <body>
        <div class="form-container">
            <h2>Insertar Nuevo Producto</h2>
            <form action="ProductoControlador" method="post">
                
                <!-- Campo oculto para enviar el ID -->
                <input type="hidden" name="id" value="0">

                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required>

                <label for="categoria">Categoría:</label>
                <input type="text" id="categoria" name="categoria" required>

                <label for="cantidad">Cantidad:</label>
                <input type="number" id="cantidad" name="cantidad" required>

                <input type="hidden" name="accion" value="guardar">
                <input type="submit" value="Guardar Producto" class="submit-btn">
            </form>
        </div>
    </body>
</html>
