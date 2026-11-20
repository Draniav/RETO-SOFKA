package co.com.sofka.opencart.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.sofka.opencart.userinterfaces.PaginaPedidoConfirmado.TITULO_CONFIRMACION;
import static co.com.sofka.opencart.utils.EsperaMaxima.SEGUNDOS_PASO_CHECKOUT;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;

public class MensajeDeConfirmacion implements Question<String> {

    private final String mensajeEsperado;

    private MensajeDeConfirmacion(String mensajeEsperado) {
        this.mensajeEsperado = mensajeEsperado;
    }

    /**
     * @param mensajeEsperado se usa solo para esperar a que la página de éxito cargue;
     *                        la validación la hace el paso que consulta esta pregunta.
     */
    public static MensajeDeConfirmacion delPedido(String mensajeEsperado) {
        return new MensajeDeConfirmacion(mensajeEsperado);
    }

    @Override
    @Subject("el mensaje de confirmación del pedido")
    public String answeredBy(Actor actor) {
        actor.attemptsTo(
                WaitUntil.the(TITULO_CONFIRMACION, containsText(mensajeEsperado))
                        .forNoMoreThan(SEGUNDOS_PASO_CHECKOUT).seconds()
        );
        return Text.of(TITULO_CONFIRMACION).answeredBy(actor).trim();
    }
}
