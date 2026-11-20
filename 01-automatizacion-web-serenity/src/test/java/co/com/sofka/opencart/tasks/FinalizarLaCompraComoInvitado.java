package co.com.sofka.opencart.tasks;

import co.com.sofka.opencart.models.Comprador;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

/**
 * Tarea de alto nivel que compone el flujo completo de "Guest Checkout".
 */
public class FinalizarLaCompraComoInvitado implements Task {

    private final Comprador comprador;

    public FinalizarLaCompraComoInvitado(Comprador comprador) {
        this.comprador = comprador;
    }

    public static FinalizarLaCompraComoInvitado con(Comprador comprador) {
        return instrumented(FinalizarLaCompraComoInvitado.class, comprador);
    }

    @Override
    @Step("{0} finaliza la compra como invitado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                IniciarCheckoutComoInvitado.desdeElCarrito(),
                DiligenciarDatosDeFacturacion.de(comprador),
                ConfirmarElPedido.conMetodosPorDefecto()
        );
    }
}
