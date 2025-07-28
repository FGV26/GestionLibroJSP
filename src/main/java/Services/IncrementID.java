package Services;

import Dao.LibroDao;
import Dao.SocioDao;
import Dao.PrestamoDao;
import util.EntityType;

import java.sql.SQLException;

public class IncrementID {

    private static LibroDao libroDAO = new LibroDao();
    private static SocioDao socioDAO = new SocioDao();
    private static PrestamoDao prestamoDAO = new PrestamoDao();

    //este metodo obtiene el id de las entidades, esto nse lograr a partir de un enum
    public static String getLastId(EntityType entityType) throws Exception {
        switch(entityType){
            case LIBRO:
                return libroDAO.getLastId();
            case SOCIO:
                return socioDAO.getLastId();
            case PRESTAMO:
                return prestamoDAO.getLastId();
            default:
                throw new IllegalArgumentException("Tipo de entidad ni soportada " + entityType );
        }
    }

    // Generar un nuevo ID en base al último ID
    public static String generateNewId(EntityType entityType) throws Exception {
        String lastId = getLastId(entityType);; //getLastId();    //obtener el ultimo id de las entidades
        String prefix ="";  //tipo de caracter de inicio

        //seleccionar el primer caracter
        switch (entityType){
            case LIBRO:
                prefix ="L";
                break;
            case SOCIO:
                prefix="S";
                break;
            case PRESTAMO:
                prefix="P";
                break;
            default:
                throw new IllegalArgumentException("Prefijo no definido para la entidad "+ entityType);
        }

        if (lastId != null && lastId.startsWith(prefix)) {
            try {
                int num = Integer.parseInt(lastId.substring(1));
                num++;
                return String.format(prefix+"%03d", num);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return prefix+"001";
    }
}
