package co.com.sofka.petstore;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Ejecuta todos los escenarios de usuarios en paralelo. Es seguro porque cada escenario
 * crea sus propios datos (usuario único) y los limpia al terminar.
 */
class PetstoreRunnerTest {

    private static final String FEATURES = "classpath:co/com/sofka/petstore/usuarios";
    private static final int HILOS = 3;

    @Test
    void gestionDeUsuarios() {
        Results resultados = Runner.path(FEATURES)
                .tags("~@ignore")
                .outputCucumberJson(true)
                .parallel(HILOS);

        assertEquals(0, resultados.getFailCount(), resultados.getErrorMessages());
    }
}
