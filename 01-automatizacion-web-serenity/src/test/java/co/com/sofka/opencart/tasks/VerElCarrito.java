package co.com.sofka.opencart.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.sofka.opencart.userinterfaces.PaginaCarrito.TITULO;
import static co.com.sofka.opencart.userinterfaces.PaginaInicio.ENLACE_CARRITO;
import static co.com.sofka.opencart.utils.EsperaMaxima.SEGUNDOS_PASO_CHECKOUT;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class VerElCarrito implements Task {

    public static VerElCarrito deCompras() {
        return instrumented(VerElCarrito.class);
    }

    @Override
    @Step("{0} abre el carrito de compras")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(ENLACE_CARRITO),
                WaitUntil.the(TITULO, isVisible()).forNoMoreThan(SEGUNDOS_PASO_CHECKOUT).seconds()
        );
    }
}
