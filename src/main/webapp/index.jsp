
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="DTO.*"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<body>
<h1>Welcome to the JSP Page</h1>
<h2>Registrar Libro</h2>
<form action="controller/libro" method="post">
    <input type="hidden" name="accion" value="registrar">

    <label for="titulo">Título:</label>
    <input type="text" id="titulo" name="titulo" required><br><br>

    <label for="autor">Autor:</label>
    <input type="text" id="autor" name="autor" required><br><br>

    <button type="submit">Registrar Libro</button>
</form>

<h2>Buscar Libro por ID</h2>
<form action="controller/libro" method="get">
    <input type="hidden" name="accion" value="buscar">

    <label for="id">ID del Libro:</label>
    <input type="number" id="id" name="id" required><br><br>

    <button type="submit">Buscar Libro</button>
</form>

</body>
</html>
