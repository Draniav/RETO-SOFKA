package co.com.sofka.opencart.tasks;

import co.com.sofka.opencart.interactions.ClicCuandoSeaClicable;
import co.com.sofka.opencart.models.Comprador;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static co.com.sofka.opencart.userinterfaces.PaginaCheckout.*;
import static co.com.sofka.opencart.utils.EsperaMaxima.SEGUNDOS_PASO_CHECKOUT;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Paso 2 del checkout: datos personales y dirección de facturación del invitado.
 * La dirección de envío queda igual a la de facturación (opción por defecto de la tienda).
 */
public class DiligenciarDatosDeFacturacion implements Task {

    private final Comprador comprador;

    public DiligenciarDatosDeFacturacion(Comprador comprador) {
        this.comprador = comprador;
    }

    public static DiligenciarDatosDeFacturacion de(Comprador comprador) {
        return instrumented(DiligenciarDatosDeFacturacion.class, comprador);
    }

    @Override
    @Step("{0} diligencia sus datos de facturación como invitado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(CAMPO_NOMBRE, isVisible()).forNoMoreThan(SEGUNDOS_PASO_CHECKOUT).seconds(),
                Enter.theValue(comprador.getNombre()).into(CAMPO_NOMBRE),
                Enter.theValue(comprador.getApellido()).into(CAMPO_APELLIDO),
                Enter.theValue(comprador.getCorreo()).into(CAMPO_CORREO),
                Enter.theValue(comprador.getTelefono()).into(CAMPO_TELEFONO),
                Enter.theValue(comprador.getDireccion()).into(CAMPO_DIRECCION),
                Enter.theValue(comprador.getCiudad()).into(CAMPO_CIUDAD),
                Enter.theValue(comprador.getCodigoPostal()).into(CAMPO_CODIGO_POSTAL),
                SelectFromOptions.byVisibleText(comprador.getPais()).from(LISTA_PAIS),
                WaitUntil.the(OPCION_REGION.of(comprador.getRegion()), isPresent())
                        .forNoMoreThan(SEGUNDOS_PASO_CHECKOUT).seconds(),
                SelectFromOptions.byVisibleText(comprador.getRegion()).from(LISTA_REGION),
                ClicCuandoSeaClicable.en(BOTON_CONTINUAR_FACTURACION)
        );
    }
}
