package co.com.sofka.opencart.questions;

import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;

import java.util.List;
import java.util.stream.Collectors;

import static co.com.sofka.opencart.userinterfaces.PaginaCarrito.NOMBRES_DE_PRODUCTOS;

public class ProductosEnElCarrito implements Question<List<String>> {

    public static ProductosEnElCarrito visibles() {
        return new ProductosEnElCarrito();
    }

    @Override
    @Subject("los productos visibles en el carrito")
    public List<String> answeredBy(Actor actor) {
        return Text.ofEach(NOMBRES_DE_PRODUCTOS).answeredBy(actor).stream()
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
