package co.com.sofka.opencart.userinterfaces;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

@DefaultUrl("http://opencart.abstracta.us/")
public class PaginaInicio extends PageObject {

    public static final Target CAMPO_BUSQUEDA = Target.the("campo de búsqueda")
            .located(By.cssSelector("#search input[name='search']"));

    public static final Target BOTON_BUSCAR = Target.the("botón de búsqueda")
            .located(By.cssSelector("#search button"));

    public static final Target ENLACE_CARRITO = Target.the("enlace 'Shopping Cart' del encabezado")
            .located(By.cssSelector("a[title='Shopping Cart']"));
}
