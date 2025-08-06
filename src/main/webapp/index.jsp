<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sistema de Biblioteca</title>
</head>
<body>
    <div class="container">

        <h2>Agregar Libro</h2>
        <form id="formAgregarLibro">
            <input type="text" id="tituloAgregar" placeholder="Título del libro" required>
            <input type="text" id="autorAgregar" placeholder="Autor del libro" required>
            <button type="submit">Agregar</button>
        </form>
        <div id="resultadoAgregarLibro" class="resultado-mensaje"></div>

        <hr>

        <h2>Actualizar Libro</h2>
        <form id="formActualizarLibro">
            <input type="text" id="actualizarId" placeholder="ID del libro" required>
            <input type="text" id="tituloActualizar" placeholder="Título del libro" required>
            <input type="text" id="autorActualizar" placeholder="Autor del libro" required>
            <button type="submit">Buscar</button>
        </form>
        <div id="resultadoActualizarLibro" class="resultado-mensaje"></div>

        <hr>

        <h2>Eliminar Libro</h2>
        <form id="formEliminarLibro">
            <input type="text" id="eliminarID" placeholder="ID del libro" required>
            <button type="submit">Buscar</button>
        </form>
        <div id="resultadoEliminarLibro" class="resultado-mensaje"></div>

        <h2>Todos los Libros</h2>
        <button id="btnCargarLibros">Cargar Libros</button>
        <div id="resultadoListarLibros" class="resultado-mensaje"></div>
        <div class="table-container">
            <table id="tablaLibros">
                <thead>
                    <tr>
                        <th>ID Libro</th>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>Fecha Creación</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Libros cargados por JS --%>
                </tbody>
            </table>
        </div>
        <hr>

        <a href="home.jsp" class="back-to-home">Volver al Inicio</a>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const AgregarLibro = document.getElementById("formAgregarLibro");
            const ActualizarLibro = document.getElementById("formActualizarLibro");
            const EliminarLibro = document.getElementById("formEliminarLibro");
            const botonListarLibros = document.getElementById("btnCargarLibros");

            console.log("DOM cargado y listo");
            listarLibros();

            AgregarLibro.addEventListener("submit", agregarLibro);
            ActualizarLibro.addEventListener("submit", actualizarLibro);
            EliminarLibro.addEventListener("submit", eliminarLibro);
            botonListarLibros.addEventListener("click", listarLibros);

            function agregarLibro(e) {
                e.preventDefault();
                const titulo = document.getElementById("tituloAgregar").value;
                const autor = document.getElementById("autorAgregar").value;

                console.log(`Agregando libro: Título: ${titulo}, Autor: ${autor}`);

                fetch("/ProyectoBiblioteca/api/libros?action=agregar", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: `titulo=${encodeURIComponent(titulo)}&autor=${encodeURIComponent(autor)}`
                })
                .then(res => {
                    if (!res.ok) throw new Error("Error al agregar el libro");
                    return res.json();
                })
                .then(data => {
                    document.getElementById("resultadoAgregarLibro").innerText = `Libro agregado con ID: ${data.id}`;
                    listarLibros();
                    setTimeout(() => {
                      document.getElementById("resultadoAgregarLibro").innerText = ""
                    }, "3000");
                })
                .catch(err => {
                    document.getElementById("resultadoAgregarLibro").innerText = `${err.message}`;
                    setTimeout(() => {
                      document.getElementById("resultadoAgregarLibro").innerText = ""
                    }, "3000");
                });
            }

            function actualizarLibro(e) {
                e.preventDefault();
                const id = document.getElementById("actualizarId").value;
                const titulo = document.getElementById("tituloActualizar").value;
                const autor = document.getElementById("autorActualizar").value;

                console.log(`Actualizando libro: ID: ${id}, Título: ${titulo}, Autor: ${autor}`);

                fetch("/ProyectoBiblioteca/api/libros?action=update", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    },
                    body: `id=${encodeURIComponent(id)}&titulo=${encodeURIComponent(titulo)}&autor=${encodeURIComponent(autor)}`
                })
                .then(res => {
                    if (!res.ok) throw new Error("Error al actualizar el libro");
                    return res.json();
                })
                .then(data => {
                    document.getElementById("resultadoActualizarLibro").innerText = `Libro actualizado con ID: ${data.id}`;
                    listarLibros();
                    setTimeout(() => {
                      document.getElementById("resultadoActualizarLibro").innerText = ""
                    }, "3000");
                })
                .catch(err => {
                    document.getElementById("resultadoActualizarLibro").innerText = `${err.message}`;
                    setTimeout(() => {
                      document.getElementById("resultadoActualizarLibro").innerText = ""
                    }, "3000");
                });
                titulo.textContent = "";
                autor.textContent = "";
            }

            function eliminarLibro(e) {
                e.preventDefault();
                const id = document.getElementById("eliminarID").value;

                console.log(`Eliminando libro con ID: ${id}`);

                fetch(`/ProyectoBiblioteca/api/libros?action=delete&id=${encodeURIComponent(id)}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                })
                .then(res => {
                    if (!res.ok) throw new Error("Error al eliminar el libro");
                    return res.json();
                })
                .then(data => {
                    document.getElementById("resultadoEliminarLibro").innerText = `Libro eliminado con ID: ${data.id}`;
                    listarLibros();
                    setTimeout(() => {
                      document.getElementById("resultadoEliminarLibro").innerText = ""
                    }, "3000");
                })
                .catch(err => {
                    document.getElementById("resultadoEliminarLibro").innerText = `${err.message}`;
                    setTimeout(() => {
                      document.getElementById("resultadoEliminarLibro").innerText = ""
                    }, "3000");
                });
            }

            function listarLibros() {
                const tbody = document.querySelector("#tablaLibros tbody");
                tbody.innerHTML = '';
                document.getElementById("resultadoListarLibros").innerText = '';

                fetch("/ProyectoBiblioteca/api/libros?action=ListarTodo")
                .then(res => {
                    if (!res.ok) throw new Error("Error al cargar libros");
                    return res.json();
                })
                .then(libros => {
                    if (libros.length === 0) {
                        document.getElementById("resultadoListarLibros").innerText = "No hay libros registrados.";
                        return;
                    }

                    libros.forEach(libro => {
                        const row = tbody.insertRow();
                        row.insertCell().innerText = libro.id;
                        row.insertCell().innerText = libro.title;
                        row.insertCell().innerText = libro.author;
                        row.insertCell().innerText = libro.creationDate || '-';
                    });

                    document.getElementById("resultadoListarLibros").innerText =
                        `Libros cargados: ${libros.length}`;
                })
                .catch(err => {
                    document.getElementById("resultadoListarLibros").innerText = `Error: ${err.message}`;
                });
            }
        });
    </script>
</body>
</html>
