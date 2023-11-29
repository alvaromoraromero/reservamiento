package usuario;

import com.example.reservamiento.model.Usuario;
import com.example.reservamiento.repository.UsuariosRepository;
import com.example.reservamiento.utilidades.UtilidadesUsuario;
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
public class UsuarioRepositoryTest {


    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private TestEntityManager entityManager;


    @BeforeEach


    @AfterEach


    @Test
    public void guardarUsuarioTest(){

        //Given
        Usuario usuarioEsperado = UtilidadesUsuario.crearUsuario();

        //When
        Usuario usuarioObtenido = usuariosRepository.save(usuarioEsperado);

        //Then
        assertNotNull( "No se ha guardado en bbdd el usuario", usuarioEsperado);
        assertEquals("Los usuarios no coinciden",usuarioEsperado.getUsername(),usuarioObtenido.getUsername());

    }


    @Test
    @DisplayName("UsuarioRepository -> Test 1 -> Método buscar Usuario por id ")
    @Tag("Usuario Repository")
    public void buscarUsuarioTest(){

        //Given
        Usuario usuarioEsperado = UtilidadesUsuario.crearUsuario();
        entityManager.persist(usuarioEsperado);

        //When
        Usuario usuarioObtenido = usuariosRepository.findById(usuarioEsperado.getId_usuario()).orElse(null);

        //Then
        assertNotNull( "No se ha encontrado usuario", usuarioObtenido);
        assertEquals("Los usuarios no coinciden ",usuarioEsperado.getUsername(),usuarioObtenido.getUsername());

    }

}
