<%-- src/main/webapp/socio.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Socios</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <div class="container">
        <h2>Agregar Socio</h2>
        <form id="formAgregarSocio">
            <input type="text" id="nombreSocio" placeholder="Nombre del socio" required>
            <input type="email" id="correoSocio" placeholder="Correo electrónico" required>
            <button type="submit">Agregar</button>
        </form>
        <div id="resultadoAgregarSocio" class="resultado-mensaje"></div>

        <hr>

        <h2>Buscar Socio por ID</h2>
        <form id="formBuscarSocio">
            <input type="input" id="buscarIdSocio" placeholder="ID del socio" required>
            <button type="submit">Buscar</button>
        </form>
        <div id="resultadoBuscarSocio" class="resultado-mensaje"></div>

        <a href="home.jsp" class="back-to-home">Volver al Inicio</a>
    </div>

    <script>
        // Función para limpiar y mostrar mensajes con estilos
        function showMessage(elementId, message, type) {
            const element = document.getElementById(elementId);
            element.innerText = message;
            element.className = 'resultado-mensaje ' + (type || ''); // Agrega clase para estilo (success, error, info)
        }

        // --- Lógica para Agregar Socio ---
        document.getElementById("formAgregarSocio").addEventListener("submit", function (e) {
            e.preventDefault();

            const nombre = document.getElementById("nombreSocio").value;
            const correo = document.getElementById("correoSocio").value;

            // La URL de tu SocioController
            const apiUrl = "/ProyectoBiblioteca/api/socios"; // <-- ¡IMPORTANTE: AJUSTA '/ProyectoBiblioteca' al contexto de tu app!

            // Crear URLSearchParams para enviar datos como application/x-www-form-urlencoded
            const formData = new URLSearchParams();
            formData.append('nombre', nombre);
            formData.append('correo', correo);

            fetch(apiUrl, {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded" // Tipo de contenido esperado por tu SocioController
                },
                body: formData // Envía los datos codificados
            })
            .then(res => {
                if (!res.ok) { // Si la respuesta no es 2xx OK
                    // Intentamos leer el mensaje de error del servidor si lo envía como texto
                    return res.text().then(errorMessage => {
                        // Si el servidor envía un HTML de error (como el que te mostró Tomcat 500), lo capturamos
                        if (errorMessage.startsWith("<!doctype html>")) {
                            throw new Error("Error del servidor: " + res.status); // O un mensaje más genérico
                        }
                        try {
                            // Intentar parsear como JSON si el error no es HTML (para mensajes de error JSON del controller)
                            const errorJson = JSON.parse(errorMessage);
                            throw new Error(errorJson.message || "Error desconocido");
                        } catch (e) {
                            // Si no es JSON válido, el mensaje es el texto plano
                            throw new Error(errorMessage || res.statusText);
                        }
                    });
                }
                return res.json(); // Si la respuesta es OK (201 Created), esperamos JSON
            })
            .then(data => {
                // Asumiendo que el JSON de respuesta es { "idSocio": "S001", "nombre": "...", "correo": "..." }
                showMessage("resultadoAgregarSocio",
                            `Socio agregado: [${data.idSocio}] - ${data.nombre} - ${data.correo}`,
                            "success");
                document.getElementById("formAgregarSocio").reset(); // Limpiar el formulario
            })
            .catch(err => {
                showMessage("resultadoAgregarSocio", `Error al agregar socio: ${err.message}`, "error");
                console.error("Error en la petición POST:", err);
            });
        });

        // --- Lógica para Buscar Socio por ID ---
        document.getElementById("formBuscarSocio").addEventListener("submit", function (e) {
            e.preventDefault();

            const id = document.getElementById("buscarIdSocio").value;
            console.log(`Buscando socio con ID: ${id}`);

            if (!id) {
                showMessage("resultadoBuscarSocio", "Por favor, ingrese un ID de socio.", "info");
                return;
            }

            // La URL de tu SocioController para buscar por ID
            const apiUrl = `/ProyectoBiblioteca/api/socios?id=${id}`; // <-- ¡IMPORTANTE: AJUSTA '/ProyectoBiblioteca'!

            fetch(apiUrl)
            .then(res => {
                if (res.status === 404) {
                    throw new Error("Socio no encontrado con ese ID.");
                }
                if (!res.ok) { // Si la respuesta no es 2xx OK y no es 404
                    return res.text().then(errorMessage => {
                         if (errorMessage.startsWith("<!doctype html>")) {
                            throw new Error("Error del servidor: " + res.status);
                        }
                        try {
                            const errorJson = JSON.parse(errorMessage);
                            throw new Error(errorJson.message || "Error desconocido");
                        } catch (e) {
                            throw new Error(errorMessage || res.statusText);
                        }
                    });
                }
                return res.json(); // Si la respuesta es OK, esperamos JSON
            })
            .then(data => {
                // Asumiendo que el JSON de respuesta es { "idSocio": "S001", "nombre": "...", "correo": "..." }
                showMessage("resultadoBuscarSocio",
                            `Socio encontrado: [${data.idSocio}] ${data.nombre} - ${data.correo}`,
                            "success");
            })
            .catch(err => {
                showMessage("resultadoBuscarSocio", `${err.message}`, "error");
                console.error("Error en la petición GET:", err);
            });
        });
    </script>
</body>
</html>