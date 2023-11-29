package reserva;

import com.example.reservamiento.model.Reserva;
import com.example.reservamiento.repository.ReservasRepository;
import com.example.reservamiento.utilidades.UtilidadesReserva;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.url=jdbc:h2:mem:db",
        "spring.jpa.properties.hibernate.default_schema=",
        "spring.jpa.hibernate.ddl-auto=update",
})
public class ReservaRepositoryTest {


    @Autowired
    private ReservasRepository reservasRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach


    @AfterEach


    @Test
    public void guardarReservaTest(){

        //Given
        Reserva reservaEsperada = UtilidadesReserva.crearReserva();

        //When
        Reserva reservaObtenida = reservasRepository.save(reservaEsperada);

        //Then
        assertNotNull( "No se ha guardado en bbdd la reserva", reservaObtenida);
        assertEquals("Las reservas no coinciden",reservaEsperada.getObservaciones(),reservaObtenida.getObservaciones());

    }


    @Test
    @DisplayName("ReservaRepository -> Test 1 -> Método buscar Reserva por id ")
    @Tag("Reserva Repository")
    public void buscarReservaTest(){

        //Given
        Reserva reservaEsperada = UtilidadesReserva.crearReserva();
        entityManager.persist(reservaEsperada);

        //When
        Reserva reservaObtenida = reservasRepository.findById(reservaEsperada.getId_reserva()).orElse(null);

        //Then
        assertNotNull( "No se ha encontrado reserva", reservaObtenida);
        assertEquals("Las reservas no coinciden ",reservaEsperada.getObservaciones(),reservaObtenida.getObservaciones());

    }

}
