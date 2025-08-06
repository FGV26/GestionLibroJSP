<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Socios</title>
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

        <h2>Actualizar Socio</h2>
        <form id="formActualizarSocio">
            <input type="text" id="actualizarId" placeholder="ID del socio" required>
            <input type="text" id="nombreActualizar" placeholder="Nombre del socio" required>
            <input type="text" id="correoActualizar" placeholder="Correo del socio" required>
            <button type="submit">Actualizar</button>
        </form>
        <div id="resultadoActualizarSocio" class="resultado-mensaje"></div>

        <hr>

        <h2>Eliminar Socio</h2>
        <form id="formEliminarSocio">
            <input type="text" id="eliminarID" placeholder="ID del socio" required>
            <button type="submit">Eliminar</button>
        </form>
        <div id="resultadoEliminarSocio" class="resultado-mensaje"></div>

        <h2>Todos los Socios</h2>
        <button id="btnCargarSocios">Cargar Socios</button>
        <div id="resultadoListarSocios" class="resultado-mensaje"></div>
        <div class="table-container">
            <table id="tablaSocios">
                <thead>
                    <tr>
                        <th>ID Socio</th>
                        <th>Nombre</th>
                        <th>Correo</th>
                    </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
        <hr>

        <a href="home.jsp" class="back-to-home">Volver al Inicio</a>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const apiUrl = "/ProyectoBiblioteca/api/socios";
            document.getElementById("formAgregarSocio").addEventListener("submit", agregarSocio);
            document.getElementById("formActualizarSocio").addEventListener("submit", actulizarSocio);
            document.getElementById("formEliminarSocio").addEventListener("submit", elimiarSocio);
            document.getElementById("btnCargarSocios").addEventListener("click", listarSocios);

            function agregarSocio(e) {
                e.preventDefault();
                const nombre = document.getElementById("nombreSocio").value;
                const correo = document.getElementById("correoSocio").value;

                fetch(apiUrl + "?action=agregar", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: `nombre=${encodeURIComponent(nombre)}&correo=${encodeURIComponent(correo)}`
                })
                .then(res => res.json())
                .then(data => {
                    showMessage("resultadoAgregarSocio", `Socio agregado: ${data.idSocio}`, "success");
                    document.getElementById("formAgregarSocio").reset();
                    listarSocios();
                })
                .catch(err => showMessage("resultadoAgregarSocio", `Error: ${err.message}`, "error"));
            }

            function actulizarSocio(e){
                e.preventDefault();
                const id = document.getElementById("actualizarId").value;
                const nombre = document.getElementById("nombreActualizar").value;
                const correo = document.getElementById("correoActualizar").value;

                fetch(apiUrl + "?action=update", {
                    method: "POST",
                    headers: { "Content-Type": "application/x-www-form-urlencoded" },
                    body: `id=${encodeURIComponent(id)}&nombre=${encodeURIComponent(nombre)}&correo=${encodeURIComponent(correo)}`
                })
                .then(res => res.json())
                .then(data => {
                    showMessage("resultadoActualizarSocio", `Socio actualizado: ${data.idSocio}`, "success");
                    listarSocios();
                })
                .catch(err => showMessage("resultadoActualizarSocio", `Error: ${err.message}`, "error"));
            }

            function elimiarSocio(e){
                e.preventDefault();
                const id = document.getElementById("eliminarID").value;

                fetch(apiUrl + `?action=delete&id=${encodeURIComponent(id)}`, {
                    method: "GET"
                })
                .then(res => res.json())
                .then(data => {
                    showMessage("resultadoEliminarSocio", `Socio eliminado: ${data.idSocio}`, "success");
                    listarSocios();
                })
                .catch(err => showMessage("resultadoEliminarSocio", `Error: ${err.message}`, "error"));
            }

            function listarSocios() {
                const tbody = document.querySelector("#tablaSocios tbody");
                tbody.innerHTML = '';
                fetch(apiUrl + "?action=ListarTodo")
                    .then(res => res.json())
                    .then(socios => {
                        if (socios.length === 0) {
                            showMessage("resultadoListarSocios", "No hay socios registrados.", "info");
                            return;
                        }

                        socios.forEach(s => {
                            const row = tbody.insertRow();
                            row.insertCell().innerText = s.idSocio;
                            row.insertCell().innerText = s.nombre;
                            row.insertCell().innerText = s.correo;
                        });
                        showMessage("resultadoListarSocios", `Socios cargados: ${socios.length}`, "success");
                    })
                    .catch(err => showMessage("resultadoListarSocios", `Error: ${err.message}`, "error"));
            }

            function showMessage(id, message, type = '') {
                const el = document.getElementById(id);
                el.innerText = message;
                el.className = 'resultado-mensaje ' + type;
                setTimeout(() => { el.innerText = ""; }, 3000);
            }

            listarSocios();
        });
    </script>
</body>
</html>
