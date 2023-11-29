package usuario;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Usuario;
import com.example.reservamiento.repository.UsuariosRepository;
import com.example.reservamiento.service.UsuariosService;
import com.example.reservamiento.utilidades.UtilidadesAlojamiento;
import com.example.reservamiento.utilidades.UtilidadesUsuario;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @Mock
    UsuariosRepository usuariosRepository;

    @InjectMocks
    private UsuariosService usuariosService;


    @Test
    @DisplayName("Usuarios Service -> Test buscar Usuario por id")
    public void buscarPorIdTest(){

        //Given
        Usuario usuarioEsperado = UtilidadesUsuario.crearUsuario();
        System.out.println(usuarioEsperado);
        Mockito.when(usuariosRepository.findTopByIdUsuario(anyString())).thenReturn(usuarioEsperado);

        //When
        Usuario usuarioObtenido = usuariosService.buscarPorId("1");

        //Then
        assertEquals("Los usuarios no coinciden",usuarioEsperado,usuarioObtenido);
    }
}
