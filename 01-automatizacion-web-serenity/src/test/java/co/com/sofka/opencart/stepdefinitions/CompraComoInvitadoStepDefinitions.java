package co.com.sofka.opencart.stepdefinitions;

import co.com.sofka.opencart.models.Comprador;
import co.com.sofka.opencart.questions.MensajeDeConfirmacion;
import co.com.sofka.opencart.questions.ProductosEnElCarrito;
import co.com.sofka.opencart.tasks.AbrirLaTienda;
import co.com.sofka.opencart.tasks.AgregarAlCarrito;
import co.com.sofka.opencart.tasks.FinalizarLaCompraComoInvitado;
import co.com.sofka.opencart.tasks.VerElCarrito;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class CompraComoInvitadoStepDefinitions {

    @Dado("que {string} está en la tienda OpenCart")
    public void queElClienteEstaEnLaTienda(String nombreDelActor) {
        theActorCalled(nombreDelActor).wasAbleTo(AbrirLaTienda.opencart());
    }

    @Cuando("agrega al carrito los productos")
    public void agregaAlCarritoLosProductos(List<String> productos) {
        theActorInTheSpotlight().attemptsTo(AgregarAlCarrito.losProductos(productos));
    }

    @Y("visualiza el carrito de compras")
    public void visualizaElCarritoDeCompras() {
        theActorInTheSpotlight().attemptsTo(VerElCarrito.deCompras());
    }

    @Entonces("debe ver en el carrito los productos")
    public void debeVerEnElCarritoLosProductos(List<String> productosEsperados) {
        theActorInTheSpotlight().should(
                seeThat(ProductosEnElCarrito.visibles(),
                        containsInAnyOrder(productosEsperados.toArray(new String[0])))
        );
    }

    @Cuando("finaliza la compra como invitado con los datos")
    public void finalizaLaCompraComoInvitado(Comprador comprador) {
        theActorInTheSpotlight().attemptsTo(FinalizarLaCompraComoInvitado.con(comprador));
    }

    @Entonces("debe ver el mensaje de confirmación {string}")
    public void debeVerElMensajeDeConfirmacion(String mensajeEsperado) {
        theActorInTheSpotlight().should(
                seeThat(MensajeDeConfirmacion.delPedido(mensajeEsperado), equalTo(mensajeEsperado))
        );
    }
}
