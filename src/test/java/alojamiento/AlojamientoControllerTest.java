package alojamiento;

import com.example.reservamiento.controller.AlojamientosController;
import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.repository.AlojamientosRepository;
import com.example.reservamiento.service.AlojamientosService;
import com.example.reservamiento.utilidades.UtilidadesAlojamiento;
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
public class AlojamientoControllerTest {

    @Mock
    private AlojamientosService alojamientosService;

    @InjectMocks
    private AlojamientosController alojamientosController;


    @Test
    @DisplayName("Alojamientos Controller -> Test pendiente")
    public void pendiente(){
    }
}
