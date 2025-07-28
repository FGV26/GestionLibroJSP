
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sistema de Biblioteca</title>
    <link rel="stylesheet" href="css/styles.css">

</head>
<body>
    <div class="container">
            <h2>Agregar Libro</h2>
            <form id="formAgregarLibro">
              <input type="text" id="titulo" placeholder="Título del libro" required>
              <input type="text" id="autor" placeholder="Autor del libro" required>
              <button type="submit">Agregar</button>
            </form>
            <div id="resultadoAgregarLibro" class="resultado-mensaje"></div>

            <hr>

            <h2>Buscar Libro por ID</h2>
            <form id="formBuscarLibro">
              <input type="input" id="buscarIdLibro" placeholder="ID del libro" required>
              <button type="submit">Buscar</button>
            </form>
            <div id="resultadoBuscarLibro" class="resultado-mensaje"></div>

            <a href="home.jsp" class="back-to-home">Volver al Inicio</a>
        </div>

    <script>
     document.getElementById("formAgregarLibro").addEventListener("submit", function (e) {
        e.preventDefault();

        const titulo = document.getElementById("titulo").value;
        const autor = document.getElementById("autor").value;

        fetch("/ProyectoBiblioteca/api/libros", {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({titulo, autor })
        })
          .then(res => {
            if (!res.ok) throw new Error("Error al agregar libro");
            return res.json();
          })
          .then(data => {
            document.getElementById("resultadoAgregarLibro").innerText =
              `Libro agregado: [${data.id}]-${data.title} - ${data.author}`; // Mostrar ID del libro agregado
          })
          .catch(err => {
            document.getElementById("resultadoAgregarLibro").innerText = `${err.message}`; // Mostrar mensaje de error
          });
      });

      // Buscar libro
      document.getElementById("formBuscarLibro").addEventListener("submit", function (e) {
        e.preventDefault();

        const id = document.getElementById("buscarIdLibro").value;
        //Imprimir ID del libro buscado
        console.log(`Buscando libro con ID: ${id}`);

        fetch(`/ProyectoBiblioteca/api/libros?id=${id}`)
          .then(res => {
            if (!res.ok) throw new Error("Libro no encontrado");
            return res.json();
          })
          .then(data => {
            document.getElementById("resultadoBuscarLibro").innerText =
              `Libro encontrado: [${data.id}] ${data.title} - ${data.author}`; // Mostrar ID del libro encontrado
          })
          .catch(err => {
            document.getElementById("resultadoBuscarLibro").innerText = `${err.message}`; // Mostrar mensaje de error
          });
      });
    </script>
</body>
</html>
