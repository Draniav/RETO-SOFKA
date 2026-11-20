package co.com.sofka.opencart.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import java.util.List;

import static co.com.sofka.opencart.userinterfaces.PaginaInicio.BOTON_BUSCAR;
import static co.com.sofka.opencart.userinterfaces.PaginaInicio.CAMPO_BUSQUEDA;
import static co.com.sofka.opencart.userinterfaces.PaginaProducto.ALERTA_PRODUCTO_AGREGADO;
import static co.com.sofka.opencart.userinterfaces.PaginaProducto.BOTON_AGREGAR_AL_CARRITO;
import static co.com.sofka.opencart.userinterfaces.PaginaResultadosBusqueda.PRODUCTO_POR_NOMBRE;
import static co.com.sofka.opencart.utils.EsperaMaxima.SEGUNDOS_ALERTA;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Busca cada producto por su nombre exacto, abre su detalle y lo agrega al carrito.
 */
public class AgregarAlCarrito implements Task {

    private final List<String> productos;

    public AgregarAlCarrito(List<String> productos) {
        this.productos = List.copyOf(productos);
    }

    public static AgregarAlCarrito losProductos(List<String> productos) {
        return instrumented(AgregarAlCarrito.class, productos);
    }

    @Override
    @Step("{0} agrega al carrito los productos #productos")
    public <T extends Actor> void performAs(T actor) {
        productos.forEach(producto -> agregar(actor, producto));
    }

    private void agregar(Actor actor, String producto) {
        actor.attemptsTo(
                Enter.theValue(producto).into(CAMPO_BUSQUEDA),
                Click.on(BOTON_BUSCAR),
                Click.on(PRODUCTO_POR_NOMBRE.of(producto)),
                Click.on(BOTON_AGREGAR_AL_CARRITO),
                WaitUntil.the(ALERTA_PRODUCTO_AGREGADO, isVisible()).forNoMoreThan(SEGUNDOS_ALERTA).seconds()
        );
    }
}
