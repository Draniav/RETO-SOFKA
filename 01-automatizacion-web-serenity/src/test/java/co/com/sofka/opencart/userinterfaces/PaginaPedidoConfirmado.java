package co.com.sofka.opencart.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class PaginaPedidoConfirmado {

    public static final Target TITULO_CONFIRMACION = Target.the("título de confirmación del pedido")
            .located(By.cssSelector("#content h1"));

    private PaginaPedidoConfirmado() {
    }
}
