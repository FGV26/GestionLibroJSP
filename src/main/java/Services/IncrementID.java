package Services;

import Dao.LibroDAO;

public class IncrementID {

    private static LibroDAO libroDAO = new LibroDAO();

    public static String getLastId() {
        try {
            return libroDAO.getLastId();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Generar un nuevo ID en base al último ID
    public static String generateNewId() {
        String lastId = getLastId(); // Ej: "L007"
        if (lastId != null && lastId.startsWith("L")) {
            try {
                int num = Integer.parseInt(lastId.substring(1));
                num++;
                return String.format("L%03d", num);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return "L001x";
    }
}
