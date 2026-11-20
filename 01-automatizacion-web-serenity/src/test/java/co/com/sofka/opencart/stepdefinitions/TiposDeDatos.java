package co.com.sofka.opencart.stepdefinitions;

import co.com.sofka.opencart.models.Comprador;
import co.com.sofka.opencart.utils.GeneradorDeCorreo;
import io.cucumber.java.DataTableType;

import java.util.Map;

/**
 * Conversión de las tablas Gherkin a objetos del dominio.
 */
public class TiposDeDatos {

    @DataTableType
    public Comprador comprador(Map<String, String> fila) {
        return Comprador.builder()
                .nombre(fila.get("nombre"))
                .apellido(fila.get("apellido"))
                .correo(GeneradorDeCorreo.unico(fila.get("nombre")))
                .telefono(fila.get("telefono"))
                .direccion(fila.get("direccion"))
                .ciudad(fila.get("ciudad"))
                .codigoPostal(fila.get("codigoPostal"))
                .pais(fila.get("pais"))
                .region(fila.get("region"))
                .build();
    }
}
