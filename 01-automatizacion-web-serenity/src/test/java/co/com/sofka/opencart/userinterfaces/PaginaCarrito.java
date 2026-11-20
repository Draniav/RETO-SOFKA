package co.com.sofka.opencart.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public final class PaginaCarrito {

    public static final Target TITULO = Target.the("título del carrito")
            .located(By.cssSelector("#content h1"));

    public static final Target NOMBRES_DE_PRODUCTOS = Target.the("nombres de los productos del carrito")
            .located(By.xpath("//div[@id='content']//form//table/tbody/tr/td[@class='text-left']/a"));

    public static final Target BOTON_CHECKOUT = Target.the("botón 'Checkout'")
            .located(By.cssSelector("#content a.btn-primary[href*='checkout/checkout']"));

    private PaginaCarrito() {
    }
}
