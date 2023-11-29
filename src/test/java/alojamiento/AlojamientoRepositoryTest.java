package alojamiento;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.repository.AlojamientosRepository;
import com.example.reservamiento.utilidades.UtilidadesAlojamiento;
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
public class AlojamientoRepositoryTest {


    @Autowired
    private AlojamientosRepository alojamientosRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach


    @AfterEach


    @Test
    public void guardarAlojamientoTest(){

        //Given
        Alojamiento alojamientoEsperado = UtilidadesAlojamiento.crearAlojamiento();

        //When
        Alojamiento alojamientoObtenido = alojamientosRepository.save(alojamientoEsperado);

        //Then
        assertNotNull( "No se ha guardado en bbdd el alojamiento", alojamientoObtenido);
        assertEquals("Los alojamientos no coinciden",alojamientoEsperado.getNombre(),alojamientoObtenido.getNombre());

    }


    @Test
    @DisplayName("AlojamientoRepository -> Test 1 -> Método buscar Alojamiento por id ")
    @Tag("Alojamiento Repository")
    public void buscarAlojamientoTest(){

        //Given
        Alojamiento alojamientoEsperado = UtilidadesAlojamiento.crearAlojamiento();
        entityManager.persist(alojamientoEsperado);

        //When
        Alojamiento alojamientoObtenido = alojamientosRepository.findById(alojamientoEsperado.getId_alojamiento()).orElse(null);

        //Then
        assertNotNull( "No se ha encontrado alojamiento", alojamientoObtenido);
        assertEquals("Los alojamientos no coinciden ",alojamientoEsperado.getNombre(),alojamientoObtenido.getNombre());

    }

}
