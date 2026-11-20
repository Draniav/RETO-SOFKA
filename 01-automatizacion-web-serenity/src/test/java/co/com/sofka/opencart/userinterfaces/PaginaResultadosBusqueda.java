package co.com.sofka.opencart.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;

public final class PaginaResultadosBusqueda {

    /** Coincidencia exacta del nombre: buscar "MacBook" también devuelve "MacBook Air" y "MacBook Pro". */
    public static final Target PRODUCTO_POR_NOMBRE = Target.the("producto '{0}' en los resultados")
            .locatedBy("//div[contains(@class,'product-thumb')]//h4/a[normalize-space()='{0}']");

    private PaginaResultadosBusqueda() {
    }
}
