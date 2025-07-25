 document.getElementById("formAgregarLibro").addEventListener("submit", function (e) {
    e.preventDefault();

    const titulo = document.getElementById("titulo").value;
    const autor = document.getElementById("autor").value;

    fetch("/api/libros", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: new URLSearchParams({ titulo, autor })
    })
      .then(res => {
        if (!res.ok) throw new Error("Error al agregar libro");
        return res.json();
      })
      .then(data => {
        document.getElementById("resultadoAgregarLibro").innerText =
          `Libro agregado: [${data.id}] ${data.titulo} - ${data.autor}`; // Mostrar ID del libro agregado
      })
      .catch(err => {
        document.getElementById("resultadoAgregarLibro").innerText = `${err.message}`; // Mostrar mensaje de error
      });
  });

  // Buscar libro
  document.getElementById("formBuscarLibro").addEventListener("submit", function (e) {
    e.preventDefault();

    const id = document.getElementById("buscarIdLibro").value;

    fetch(`/api/libros?id=${id}`)
      .then(res => {
        if (!res.ok) throw new Error("Libro no encontrado");
        return res.json();
      })
      .then(data => {
        document.getElementById("resultadoBuscarLibro").innerText =
          `Libro encontrado: [${data.id}] ${data.titulo} - ${data.autor}`; // Mostrar ID del libro encontrado
      })
      .catch(err => {
        document.getElementById("resultadoBuscarLibro").innerText = `${err.message}`; // Mostrar mensaje de error
      });
  });