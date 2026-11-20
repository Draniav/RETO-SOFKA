package co.com.sofka.opencart.userinterfaces;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

/**
 * Checkout de OpenCart: un acordeón de 6 pasos cuyos paneles se cargan por AJAX.
 */
public final class PaginaCheckout {

    // Paso 1: opciones de checkout
    public static final Target OPCION_INVITADO = Target.the("opción 'Guest Checkout'")
            .located(By.cssSelector("input[name='account'][value='guest']"));
    public static final Target BOTON_CONTINUAR_OPCIONES = Target.the("botón continuar de opciones")
            .located(By.id("button-account"));

    // Paso 2: datos de facturación
    public static final Target CAMPO_NOMBRE = Target.the("campo nombre")
            .located(By.id("input-payment-firstname"));
    public static final Target CAMPO_APELLIDO = Target.the("campo apellido")
            .located(By.id("input-payment-lastname"));
    public static final Target CAMPO_CORREO = Target.the("campo correo")
            .located(By.id("input-payment-email"));
    public static final Target CAMPO_TELEFONO = Target.the("campo teléfono")
            .located(By.id("input-payment-telephone"));
    public static final Target CAMPO_DIRECCION = Target.the("campo dirección")
            .located(By.id("input-payment-address-1"));
    public static final Target CAMPO_CIUDAD = Target.the("campo ciudad")
            .located(By.id("input-payment-city"));
    public static final Target CAMPO_CODIGO_POSTAL = Target.the("campo código postal")
            .located(By.id("input-payment-postcode"));
    public static final Target LISTA_PAIS = Target.the("lista de países")
            .located(By.id("input-payment-country"));
    public static final Target LISTA_REGION = Target.the("lista de regiones")
            .located(By.id("input-payment-zone"));
    /** La lista de regiones se recarga por AJAX al cambiar el país. */
    public static final Target OPCION_REGION = Target.the("región '{0}'")
            .locatedBy("//select[@id='input-payment-zone']/option[normalize-space()='{0}']");
    public static final Target BOTON_CONTINUAR_FACTURACION = Target.the("botón continuar de facturación")
            .located(By.id("button-guest"));

    // Paso 4: método de envío
    public static final Target BOTON_CONTINUAR_ENVIO = Target.the("botón continuar del método de envío")
            .located(By.id("button-shipping-method"));

    // Paso 5: método de pago
    public static final Target CHECK_TERMINOS_Y_CONDICIONES = Target.the("check de términos y condiciones")
            .located(By.cssSelector("input[name='agree']"));
    public static final Target BOTON_CONTINUAR_PAGO = Target.the("botón continuar del método de pago")
            .located(By.id("button-payment-method"));

    // Paso 6: confirmación
    public static final Target BOTON_CONFIRMAR_PEDIDO = Target.the("botón 'Confirm Order'")
            .located(By.id("button-confirm"));

    private PaginaCheckout() {
    }
}
