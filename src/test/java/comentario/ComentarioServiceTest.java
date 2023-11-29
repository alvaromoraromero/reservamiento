package comentario;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Comentario;
import com.example.reservamiento.repository.AlojamientosRepository;
import com.example.reservamiento.repository.ComentariosRepository;
import com.example.reservamiento.service.AlojamientosService;
import com.example.reservamiento.service.ComentariosService;
import com.example.reservamiento.utilidades.UtilidadesAlojamiento;
import com.example.reservamiento.utilidades.UtilidadesComentario;
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
public class ComentarioServiceTest {

    @Mock
    ComentariosRepository comentariosRepository;

    @InjectMocks
    private ComentariosService comentariosService;


    @Test
    @DisplayName("Comentarios Service -> Test buscar Comentario por id")
    public void buscarPorIdTest(){

        //Given
        Comentario comentarioEsperado = UtilidadesComentario.crearComentario();
        System.out.println(comentarioEsperado);
        Mockito.when(comentariosRepository.findTopByIdComentario(anyString())).thenReturn(comentarioEsperado);

        //When
        Comentario comentarioObtenido = comentariosService.obtenerPorId("1");

        //Then
        assertEquals("Los comentarios no coinciden",comentarioEsperado,comentarioObtenido);
    }
}
