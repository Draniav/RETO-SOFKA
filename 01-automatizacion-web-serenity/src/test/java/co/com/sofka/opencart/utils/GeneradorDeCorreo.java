package co.com.sofka.opencart.utils;

import java.util.UUID;

/**
 * Genera correos únicos para que cada ejecución sea independiente de las anteriores.
 */
public final class GeneradorDeCorreo {

    private static final String DOMINIO_DE_PRUEBA = "@pruebas-sofka.test";
    private static final int LONGITUD_SUFIJO = 8;

    private GeneradorDeCorreo() {
    }

    public static String unico(String prefijo) {
        String sufijo = UUID.randomUUID().toString().substring(0, LONGITUD_SUFIJO);
        return prefijo.toLowerCase() + "." + sufijo + DOMINIO_DE_PRUEBA;
    }
}
