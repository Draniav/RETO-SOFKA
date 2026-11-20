package co.com.sofka.opencart.tasks;

import co.com.sofka.opencart.userinterfaces.PaginaInicio;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AbrirLaTienda implements Task {

    public static AbrirLaTienda opencart() {
        return instrumented(AbrirLaTienda.class);
    }

    @Override
    @Step("{0} abre la tienda OpenCart")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.browserOn().the(PaginaInicio.class));
    }
}
