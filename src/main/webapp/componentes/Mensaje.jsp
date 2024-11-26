<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Mostrar un mensaje de error si existe en la sesión o como parámetro -->
<c:if test="${sessionScope.error != null || not empty param.error}">
    <div  class="message-error" role="alert">
        ${sessionScope.error != null ? sessionScope.error : param.error}
    </div>
    <c:if test="${sessionScope.error != null}">
        <c:remove var="error" scope="session" />
    </c:if>
</c:if>

<!-- Mostrar un mensaje de éxito si existe en la sesión -->
<c:if test="${sessionScope.success != null}">
    <div id="success-message" class="message-success" role="alert">
        ${sessionScope.success}
    </div>
    <c:remove var="success" scope="session" />
</c:if>

<!-- JavaScript para ocultar los mensajes automáticamente después de 4 segundos -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        // Ocultar el mensaje de error después de 4 segundos
        var errorMessage = document.getElementById("error-message");
        if (errorMessage) {
            setTimeout(function() {
                errorMessage.style.display = "none";
            }, 4000);
        }

        // Ocultar el mensaje de éxito después de 4 segundos
        var successMessage = document.getElementById("success-message");
        if (successMessage) {
            setTimeout(function() {
                successMessage.style.display = "none";
            }, 4000);
        }
    });
</script>

