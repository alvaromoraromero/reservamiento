package comentario;

import com.example.reservamiento.controller.AlojamientosController;
import com.example.reservamiento.controller.ComentariosController;
import com.example.reservamiento.service.AlojamientosService;
import com.example.reservamiento.service.ComentariosService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ComentarioControllerTest {

    @Mock
    private ComentariosService comentariosService;

    @InjectMocks
    private ComentariosController comentariosController;


    @Test
    @DisplayName("Comentarios Controller -> Test pendiente")
    public void pendiente(){
    }
}
