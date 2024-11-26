package com.uacam.javawebcrud.seguridad;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Gerardo Herrera Pacheco ISC 7"B"
 *
 * El filtro JwtFilter protege las rutas de la aplicación. Antes de que el
 * servidor procese una solicitud, el filtro verifica si el usuario está
 * autenticado.
 */
@WebFilter(filterName = "JwtFilter", urlPatterns = {"/*"})
public class JwtFilter implements Filter {

    private static final String SECRET_KEY = "miClaveJWTSecretaYSeguraParaValidacion12345"; // Clave fija

    private static final boolean debug = true;

    private FilterConfig filterConfig = null;

    public JwtFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("JwtFilter:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("JwtFilter:DoAfterProcessing");
        }

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();

        // Rutas a excluir del filtro
        if (uri.endsWith("login.jsp") || uri.endsWith("index.jsp") || uri.endsWith("LoginServlet")|| uri.endsWith("styles.css") ) {
            chain.doFilter(request, response); // Continuar sin aplicar el filtro
            return;
        }

        //Primero se valida que la sesión exista y que contenga el atributo username
        //Si no hay una sesión activa, se redirige al formulario de login.
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            httpResponse.sendRedirect("vista/login.jsp?error=Debes iniciar sesion");
            return;
        }

        // Validar el token JWT
        //Se busca una cookie llamada token y se obtiene su valor.
        Cookie[] cookies = httpRequest.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        //Validar la firma y la integridad del token:
        //Se verifica que el token sea válido y que no haya sido modificado (gracias a la firma con SECRET_KEY).
        if (token != null) {
            try {
                Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY.getBytes()) // Usar la misma clave fija
                        .build()
                        .parseClaimsJws(token); // Validar el token
                //Si la sesión y el token son válidos, el filtro permite que la solicitud continúe:
                chain.doFilter(request, response);
            } catch (Exception e) {
                //Si el token es inválido o ha expirado, se redirige al login con un mensaje de error
                httpResponse.sendRedirect("vista/login.jsp?error=Token invalido o expirado");
            }
        } else {
            httpResponse.sendRedirect("vista/login.jsp?error=Falta token");
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("JwtFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("JwtFilter()");
        }
        StringBuffer sb = new StringBuffer("JwtFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
