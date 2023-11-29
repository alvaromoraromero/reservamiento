package comentario;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Comentario;
import com.example.reservamiento.repository.AlojamientosRepository;
import com.example.reservamiento.repository.ComentariosRepository;
import com.example.reservamiento.utilidades.UtilidadesAlojamiento;
import com.example.reservamiento.utilidades.UtilidadesComentario;
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
public class ComentarioRepositoryTest {


    @Autowired
    private ComentariosRepository comentariosRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach


    @AfterEach


    @Test
    public void guardarComentarioTest(){

        //Given
        Comentario comentarioEsperado = UtilidadesComentario.crearComentario();

        //When
        Comentario comentarioObtenido = comentariosRepository.save(comentarioEsperado);

        //Then
        assertNotNull( "No se ha guardado en bbdd el comentario", comentarioObtenido);
        assertEquals("Los comentarios no coinciden",comentarioEsperado.getDescripcion(),comentarioObtenido.getDescripcion());

    }


    @Test
    @DisplayName("ComentariosRepository -> Test 1 -> Método buscar Comentario por id ")
    @Tag("Comentario Repository")
    public void buscarAlojamientoTest(){

        //Given
        Comentario comentarioEsperado = UtilidadesComentario.crearComentario();
        entityManager.persist(comentarioEsperado);

        //When
        Comentario comentarioObtenido = comentariosRepository.findById(comentarioEsperado.getId_comentario()).orElse(null);

        //Then
        assertNotNull( "No se ha encontrado comentario", comentarioObtenido);
        assertEquals("Los comentarios no coinciden ",comentarioEsperado.getDescripcion(),comentarioObtenido.getDescripcion());

    }

}
