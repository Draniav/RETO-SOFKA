package co.com.sofka.opencart.utils;

/**
 * Tiempos de espera explícitos usados por las tareas. Centralizados para ajustarlos en un solo lugar.
 */
public final class EsperaMaxima {

    /** Paneles del checkout que se cargan por AJAX. */
    public static final int SEGUNDOS_PASO_CHECKOUT = 20;

    /** Mensajes de confirmación tras agregar al carrito. */
    public static final int SEGUNDOS_ALERTA = 10;

    private EsperaMaxima() {
    }
}
