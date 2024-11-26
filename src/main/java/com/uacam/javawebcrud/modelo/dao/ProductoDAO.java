package com.uacam.javawebcrud.modelo.dao;

import com.uacam.javawebcrud.config.Conexion;
import com.uacam.javawebcrud.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Clase ProductoDAO - Maneja las operaciones CRUD (Crear, Leer, Actualizar,
 * Eliminar) para la tabla "producto" en la base de datos. Autor: Gerardo
 * Herrera Pacheco ISC 7"B"
 */
public class ProductoDAO {

    // Variables para manejar la conexión, consultas preparadas y resultados.
    private Connection cn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    /**
     * Método para obtener todos los registros de la tabla "producto".
     *
     * @return Una lista de objetos Producto con los datos de todos los
     * productos.
     */
    public ArrayList<Producto> ListarTodos() {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            // Conectar a la base de datos
            cn = Conexion.getConnection();
            String sql = "select * from producto"; // Consulta SQL para obtener todos los productos
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            // Iterar sobre los resultados y agregar cada producto a la lista
            while (rs.next()) {
                Producto obj = new Producto();
                obj.setId(rs.getInt("id")); // Asignar ID
                obj.setNombre(rs.getString("nombre")); // Asignar nombre
                obj.setCategoria(rs.getString("categoria")); // Asignar categoría
                obj.setCantidad(rs.getInt("cantidad")); // Asignar cantidad
                lista.add(obj); // Agregar producto a la lista
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprimir errores en la consola
        } finally {
            // Cerrar recursos para liberar memoria
            try {
                if (cn != null) {
                    cn.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return lista; // Retornar la lista de productos
    }

    /**
     * Método para registrar un nuevo producto en la base de datos.
     *
     * @param obj Objeto Producto con los datos a insertar.
     * @return Un entero indicando el número de filas afectadas (1 si se insertó
     * correctamente).
     */
    public int registrar(Producto obj) {
        int result = 0;

        try {
            // Conectar a la base de datos
            cn = Conexion.getConnection();
            String sql = "INSERT INTO producto(nombre,categoria,cantidad) VALUES (?,?,?)"; // Consulta de inserción
            ps = cn.prepareStatement(sql);
            ps.setString(1, obj.getNombre()); // Establecer el nombre
            ps.setString(2, obj.getCategoria()); // Establecer la categoría
            ps.setInt(3, obj.getCantidad()); // Establecer la cantidad

            result = ps.executeUpdate(); // Ejecutar la consulta
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprimir errores
        } finally {
            // Cerrar recursos
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return result; // Retornar el resultado
    }

    /**
     * Método para actualizar un producto existente en la base de datos.
     *
     * @param obj Objeto Producto con los datos a actualizar.
     * @return Un entero indicando el número de filas afectadas.
     */
    public int editar(Producto obj) {
        int result = 0;

        try {
            // Conectar a la base de datos
            cn = Conexion.getConnection();
            String sql = "UPDATE producto SET nombre=?, categoria=?,cantidad=? WHERE id=?"; // Consulta de actualización
            ps = cn.prepareStatement(sql);
            ps.setString(1, obj.getNombre()); // Establecer el nombre
            ps.setString(2, obj.getCategoria()); // Establecer la categoría
            ps.setInt(3, obj.getCantidad()); // Establecer la cantidad
            ps.setInt(4, obj.getId()); // Establecer el ID del producto a actualizar

            result = ps.executeUpdate(); // Ejecutar la consulta
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprimir errores
        } finally {
            // Cerrar recursos
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return result; // Retornar el resultado
    }

    /**
     * Método para buscar un producto por su ID.
     *
     * @param id Identificador del producto.
     * @return Un objeto Producto si se encuentra, o null si no existe.
     */
    public Producto buscarPorId(int id) {
        Producto obj = null;

        try {
            // Conectar a la base de datos
            cn = Conexion.getConnection();
            String sql = "select * from producto where id = ?"; // Consulta para buscar por ID
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id); // Establecer el ID como parámetro
            rs = ps.executeQuery();

            if (rs.next()) {
                obj = new Producto();
                obj.setId(rs.getInt("id")); // Asignar ID
                obj.setNombre(rs.getString("nombre")); // Asignar nombre
                obj.setCategoria(rs.getString("categoria")); // Asignar categoría
                obj.setCantidad(rs.getInt("cantidad")); // Asignar cantidad
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprimir errores
        } finally {
            // Cerrar recursos
            try {
                if (cn != null) {
                    cn.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return obj; // Retornar el producto encontrado o null
    }

    /**
     * Método para eliminar un producto por su ID.
     *
     * @param id Identificador del producto.
     * @return Un entero indicando el número de filas afectadas (1 si se eliminó
     * correctamente).
     */
    public int eliminar(int id) {
        int result = 0;

        try {
            // Conectar a la base de datos
            cn = Conexion.getConnection();
            String sql = "DELETE FROM producto WHERE id = ?"; // Consulta para eliminar
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id); // Establecer el ID como parámetro

            result = ps.executeUpdate(); // Ejecutar la consulta
        } catch (Exception ex) {
            ex.printStackTrace(); // Imprimir errores
        } finally {
            // Cerrar recursos
            try {
                if (cn != null) {
                    cn.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return result; // Retornar el resultado
    }
}
