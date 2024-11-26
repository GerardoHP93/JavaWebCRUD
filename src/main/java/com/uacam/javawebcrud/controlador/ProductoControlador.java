package com.uacam.javawebcrud.controlador;

import com.uacam.javawebcrud.modelo.Producto;
import com.uacam.javawebcrud.modelo.dao.ProductoDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controlador para manejar las operaciones CRUD de productos.
 * Este servlet utiliza el DAO `ProductoDAO` para interactuar con la base de datos
 * y redirige a las vistas correspondientes según la acción solicitada.
 */
public class ProductoControlador extends HttpServlet {

    // Instancia del DAO para realizar operaciones en la base de datos.
    private ProductoDAO proDao = new ProductoDAO();

    // Rutas de las vistas
    private final String pagListar = "/vista/listar.jsp";
    private final String pagNuevo = "/vista/nuevo.jsp";
    private final String pagEditar = "/vista/editar.jsp";

    /**
     * Método principal para procesar todas las solicitudes.
     * Redirige según el parámetro "accion" recibido en la solicitud.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtener el parámetro "accion" de la solicitud
        String accion = request.getParameter("accion");

        // Determinar qué método ejecutar según la acción recibida
        switch (accion) {
            case "listar":
                listar(request, response);
                break;
            case "nuevo":
                nuevo(request, response);
                break;
            case "guardar":
                guardar(request, response);
                break;
            case "editar":
                editar(request, response);
                break;
            case "eliminar":
                eliminar(request, response);
                break;
            default:
                throw new AssertionError("Acción desconocida: " + accion);
        }
    }

    /**
     * Método para listar todos los productos y mostrarlos en la vista `listar.jsp`.
     */
    protected void listar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener la lista de productos del DAO
        request.setAttribute("productos", proDao.ListarTodos());
        
        // Redirigir a la vista de listar productos
        request.getRequestDispatcher(pagListar).forward(request, response);
    }

    /**
     * Método para inicializar un nuevo producto y redirigir a la vista `nuevo.jsp`.
     */
    private void nuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Crear un producto vacío para llenar en la vista
        request.setAttribute("producto", new Producto());
        
        // Redirigir a la vista para crear un nuevo producto
        request.getRequestDispatcher(pagNuevo).forward(request, response);
    }

    /**
     * Método para guardar o actualizar un producto en la base de datos.
     * Si el ID es 0, se crea un nuevo producto; si no, se actualiza.
     */
    private void guardar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Producto obj = new Producto();

        // Validar y asignar el ID
        String idParam = request.getParameter("id");
        int id = (idParam == null || idParam.isEmpty()) ? 0 : Integer.parseInt(idParam);
        obj.setId(id);

        // Asignar los demás atributos del producto
        obj.setNombre(request.getParameter("nombre"));
        obj.setCategoria(request.getParameter("categoria"));
        obj.setCantidad(Integer.parseInt(request.getParameter("cantidad")));

        // Determinar si registrar o actualizar
        int result = (obj.getId() == 0) ? proDao.registrar(obj) : proDao.editar(obj);

        if (result > 0) {
            // Guardar mensaje de éxito en la sesión y redirigir a listar
            request.getSession().setAttribute("success", "Datos guardados!");
            response.sendRedirect("ProductoControlador?accion=listar");
        } else {
            // Guardar mensaje de error y redirigir a la vista de nuevo/editar
            request.getSession().setAttribute("error", "No se pudo guardar datos.");
            request.setAttribute("producto", obj);
            request.getRequestDispatcher(pagNuevo).forward(request, response);
        }
    }

    /**
     * Método para cargar los datos de un producto para su edición.
     * Redirige a la vista `editar.jsp`.
     */
    private void editar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el ID del producto desde los parámetros de la solicitud
        int id = Integer.parseInt(request.getParameter("id"));

        // Buscar el producto en la base de datos
        Producto obj = proDao.buscarPorId(id);

        if (obj != null) {
            // Si se encuentra, redirigir a la vista de edición con los datos del producto
            request.setAttribute("producto", obj);
            request.getRequestDispatcher(pagEditar).forward(request, response);
        } else {
            // Si no se encuentra, mostrar un mensaje de error y redirigir a listar
            request.getSession().setAttribute("error", "No se encontró producto con ID " + id);
            response.sendRedirect("ProductoControlador?accion=listar");
        }
    }

    /**
     * Método para eliminar un producto de la base de datos.
     */
    private void eliminar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el ID del producto a eliminar
        int id = Integer.parseInt(request.getParameter("id"));

        // Intentar eliminar el producto
        int result = proDao.eliminar(id);

        if (result > 0) {
            // Guardar mensaje de éxito si se eliminó correctamente
            request.getSession().setAttribute("success", "Producto con ID " + id + " eliminado!");
        } else {
            // Guardar mensaje de error si no se pudo eliminar
            request.getSession().setAttribute("error", "No se pudo eliminar el producto.");
        }

        // Redirigir a la vista de listar productos
        response.sendRedirect("ProductoControlador?accion=listar");
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controlador para manejar las operaciones CRUD de productos";
    }

}
