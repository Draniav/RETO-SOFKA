package co.com.sofka.opencart.tasks;

import co.com.sofka.opencart.interactions.ClicCuandoSeaClicable;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static co.com.sofka.opencart.userinterfaces.PaginaCheckout.*;
import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Pasos 4 a 6 del checkout: método de envío por defecto, aceptación de términos
 * con el método de pago por defecto y confirmación del pedido.
 */
public class ConfirmarElPedido implements Task {

    public static ConfirmarElPedido conMetodosPorDefecto() {
        return instrumented(ConfirmarElPedido.class);
    }

    @Override
    @Step("{0} acepta los métodos de envío y pago y confirma el pedido")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClicCuandoSeaClicable.en(BOTON_CONTINUAR_ENVIO),
                ClicCuandoSeaClicable.en(CHECK_TERMINOS_Y_CONDICIONES),
                ClicCuandoSeaClicable.en(BOTON_CONTINUAR_PAGO),
                ClicCuandoSeaClicable.en(BOTON_CONFIRMAR_PEDIDO)
        );
    }
}
