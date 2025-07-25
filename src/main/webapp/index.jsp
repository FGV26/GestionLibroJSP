
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sistema de Biblioteca</title>
</head>
<body>
    <h2>Agregar Libro</h2>
    <form id="formAgregarLibro">
      <input type="text" id="titulo" placeholder="Título del libro" required>
      <input type="text" id="autor" placeholder="Autor del libro" required>
      <button type="submit">Agregar</button>
    </form>
    <div id="resultadoAgregarLibro"></div>

    <hr>

    <h2>Buscar Libro por ID</h2>
    <form id="formBuscarLibro">
      <input type="number" id="buscarIdLibro" placeholder="ID del libro" required>
      <button type="submit">Buscar</button>
    </form>
    <div id="resultadoBuscarLibro"></div>

    <script src="index.js"></script>
</body>
</html>
