package reserva;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.model.Reserva;
import com.example.reservamiento.repository.AlojamientosRepository;
import com.example.reservamiento.repository.ReservasRepository;
import com.example.reservamiento.service.AlojamientosService;
import com.example.reservamiento.service.ReservasService;
import com.example.reservamiento.utilidades.UtilidadesAlojamiento;
import com.example.reservamiento.utilidades.UtilidadesReserva;
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
public class ReservaServiceTest {

    @Mock
    ReservasRepository reservasRepository;

    @InjectMocks
    private ReservasService reservasService;


    @Test
    @DisplayName("Reserva Service -> Test buscar Reserva por id")
    public void buscarPorIdTest(){

        //Given
        Reserva reservaEsperada = UtilidadesReserva.crearReserva();
        System.out.println(reservaEsperada);
        Mockito.when(reservasRepository.findTopByIdReserva(anyString())).thenReturn(reservaEsperada);

        //When
        Reserva reservaObtenida = reservasService.obtenerPorId("1");

        //Then
        assertEquals("Las reservas no coinciden",reservaEsperada,reservaObtenida);
    }
}
