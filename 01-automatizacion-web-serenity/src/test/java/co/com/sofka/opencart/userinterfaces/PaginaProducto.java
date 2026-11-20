package co.com.sofka.opencart.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class PaginaProducto {

    public static final Target BOTON_AGREGAR_AL_CARRITO = Target.the("botón 'Add to Cart'")
            .located(By.id("button-cart"));

    public static final Target ALERTA_PRODUCTO_AGREGADO = Target.the("alerta de producto agregado")
            .located(By.cssSelector(".alert-success"));

    private PaginaProducto() {
    }
}
