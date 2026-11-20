package co.com.sofka.opencart.tasks;

import co.com.sofka.opencart.interactions.ClicCuandoSeaClicable;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static co.com.sofka.opencart.userinterfaces.PaginaCarrito.BOTON_CHECKOUT;
import static co.com.sofka.opencart.userinterfaces.PaginaCheckout.BOTON_CONTINUAR_OPCIONES;
import static co.com.sofka.opencart.userinterfaces.PaginaCheckout.OPCION_INVITADO;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Paso 1 del checkout: elegir "Guest Checkout".
 */
public class IniciarCheckoutComoInvitado implements Task {

    public static IniciarCheckoutComoInvitado desdeElCarrito() {
        return instrumented(IniciarCheckoutComoInvitado.class);
    }

    @Override
    @Step("{0} inicia el checkout como invitado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClicCuandoSeaClicable.en(BOTON_CHECKOUT),
                ClicCuandoSeaClicable.en(OPCION_INVITADO),
                ClicCuandoSeaClicable.en(BOTON_CONTINUAR_OPCIONES)
        );
    }
}
