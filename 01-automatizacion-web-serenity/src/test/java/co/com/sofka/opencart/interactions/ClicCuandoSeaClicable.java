package co.com.sofka.opencart.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.sofka.opencart.utils.EsperaMaxima.SEGUNDOS_PASO_CHECKOUT;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;

/**
 * Espera explícitamente a que un elemento sea clicable antes de hacer clic.
 * Necesario en el checkout, donde los paneles se abren con animación tras respuestas AJAX.
 */
public class ClicCuandoSeaClicable implements Interaction {

    private final Target objetivo;

    public ClicCuandoSeaClicable(Target objetivo) {
        this.objetivo = objetivo;
    }

    public static ClicCuandoSeaClicable en(Target objetivo) {
        return instrumented(ClicCuandoSeaClicable.class, objetivo);
    }

    @Override
    @Step("{0} hace clic en #objetivo")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(objetivo, isClickable()).forNoMoreThan(SEGUNDOS_PASO_CHECKOUT).seconds(),
                Click.on(objetivo)
        );
    }
}
