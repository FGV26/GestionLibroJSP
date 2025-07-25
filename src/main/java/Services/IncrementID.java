package Services;

import Dao.LibroDAO;

public class IncrementID {

    private static LibroDAO libroDAO = new LibroDAO();

    // Obtener el último ID existente
    public static String getLastId() {
        try {
            return libroDAO.getLastId(); // Por ejemplo: "L007"
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Mejor manejar null que un ID inválido como "X0"
        }
    }

    // Generar un nuevo ID en base al último ID
    public static String generateNewId() {
        String lastId = getLastId(); // Ej: "L007"
        if (lastId != null && lastId.startsWith("L")) {
            try {
                int num = Integer.parseInt(lastId.substring(1)); // "007" → 7
                num++; // 8
                return String.format("L%03d", num); // → "L008"
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return "L001x"; // ID inicial si no hay libros o error
    }
}
