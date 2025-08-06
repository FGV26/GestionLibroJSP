<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestión de Préstamos - Sistema de Biblioteca</title>
</head>
<body>
    <div class="container">
        <h2>Agregar Préstamo</h2>
        <form id="formAgregarPrestamo">
            <input type="text" id="idLibro" placeholder="ID del Libro" required>
            <input type="text" id="idSocio" placeholder="ID del Socio" required>
            <label for="fechaDevolucion">Fecha Límite de Devolución:</label>
            <input type="date" id="fechaDevolucion" required>
            <button type="submit">Registrar Préstamo</button>
        </form>
        <div id="resultadoAgregarPrestamo" class="resultado-mensaje"></div>

        <hr>

        <h2>Todos los Préstamos</h2>
        <button id="btnCargarPrestamos">Cargar Préstamos</button>
        <div id="resultadoListarPrestamos" class="resultado-mensaje"></div>
        <div class="table-container">
            <table id="tablaPrestamos">
                <thead>
                    <tr>
                        <th>ID Préstamo</th>
                        <th>Libro (ID - Título)</th>
                        <th>Socio (ID - Nombre)</th>
                        <th>F. Préstamo</th>
                        <th>F. Devolución Prevista</th>
                        <th>F. Retorno Real</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Los préstamos se cargarán aquí con JavaScript --%>
                </tbody>
            </table>
        </div>

        <a href="home.jsp" class="back-to-home">Volver al Inicio</a>
    </div>

   <script>
       // --- Lógica para Agregar Préstamo ---
       document.getElementById("formAgregarPrestamo").addEventListener("submit", function (e) {
           e.preventDefault(); // Previene el envío tradicional del formulario

           const idLibro = document.getElementById("idLibro").value;
           const idSocio = document.getElementById("idSocio").value;
           const fechaDevolucion = document.getElementById("fechaDevolucion").value;

           console.log(`Registrando préstamo: Libro ID ${idLibro}, Socio ID ${idSocio}, Fecha Devolución ${fechaDevolucion}`);

           // Envía los datos como "x-www-form-urlencoded"
           fetch("/ProyectoBiblioteca/api/prestamos", {
               method: "POST",
               headers: { "Content-Type": "application/x-www-form-urlencoded" },
               body: new URLSearchParams({
                   idLibro: idLibro,
                   idSocio: idSocio,
                   fechaDevolucion: fechaDevolucion
               })
           })
           .then(res => {
               if (!res.ok) {
                   return res.text().then(text => { throw new Error(text || "Error al registrar el préstamo"); });
               }
               return res.json();
           })
           .then(data => {
               document.getElementById("resultadoAgregarPrestamo").innerText =
                   `Préstamo registrado exitosamente: ID ${data.idPrestamo}`;
               document.getElementById("formAgregarPrestamo").reset();
               document.getElementById("btnCargarPrestamos").click();
           })
           .catch(err => {
               document.getElementById("resultadoAgregarPrestamo").innerText = `Error: ${err.message}`;
           });
       });

       // --- Lógica para Listar Préstamos ---
       document.getElementById("btnCargarPrestamos").addEventListener("click", function () {
           const tbody = document.querySelector("#tablaPrestamos tbody");
           tbody.innerHTML = '';
           document.getElementById("resultadoListarPrestamos").innerText = '';

           fetch("/ProyectoBiblioteca/api/prestamos")
           .then(res => {
               if (!res.ok) {
                   throw new Error("Error al cargar los préstamos.");
               }
               return res.json();
           })
           .then(prestamos => {
               if (prestamos.length === 0) {
                   document.getElementById("resultadoListarPrestamos").innerText = "No hay préstamos registrados.";
                   return;
               }

               prestamos.forEach(prestamo => {
                   const row = tbody.insertRow();
                   row.insertCell().innerText = prestamo.idPrestamo;
                   row.insertCell().innerText = prestamo.idLibro + ' - ' + prestamo.tituloLibro;
                   row.insertCell().innerText = prestamo.idSocio + ' - ' + prestamo.nombreSocio;
                   row.insertCell().innerText = prestamo.fechaPrestamo;
                   row.insertCell().innerText = prestamo.fechaDevolucion;
                   row.insertCell().innerText = prestamo.fechaRetorno ? prestamo.fechaRetorno : 'Pendiente';
               });

               document.getElementById("resultadoListarPrestamos").innerText =
                   `Préstamos cargados: ${prestamos.length}`;
           })
           .catch(err => {
               document.getElementById("resultadoListarPrestamos").innerText = `Error: ${err.message}`;
           });
       });

       // Al cargar la página, establecer fecha y listar
       document.addEventListener('DOMContentLoaded', () => {
           document.getElementById("btnCargarPrestamos").click();
           const today = new Date().toISOString().split('T')[0];
           document.getElementById("fechaPrestamo").value = today;
       });
   </script>
</body>
</html>