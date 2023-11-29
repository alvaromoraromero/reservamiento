package alojamiento;

import com.example.reservamiento.model.Alojamiento;
import com.example.reservamiento.repository.AlojamientosRepository;
import com.example.reservamiento.service.AlojamientosService;
import com.example.reservamiento.utilidades.UtilidadesAlojamiento;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.util.AssertionErrors.*;

@RunWith(MockitoJUnitRunner.class)
public class AlojamientoServiceTest {

    @Mock
    AlojamientosRepository alojamientosRepository;

    @InjectMocks
    private AlojamientosService alojamientosService;


    @Test
    @DisplayName("Alojamientos Service -> Test buscar Alojamiento por id")
    public void buscarPorIdTest(){

        //Given
        Alojamiento alojamientoEsperado = UtilidadesAlojamiento.crearAlojamiento();
        System.out.println(alojamientoEsperado);
        Mockito.when(alojamientosRepository.findTopByIdAlojamiento(anyString())).thenReturn(alojamientoEsperado);

        //When
        Alojamiento alojamientoObtenido = alojamientosService.buscarPorId("1");

        //Then
        assertEquals("Los alojamientos no coinciden",alojamientoEsperado,alojamientoObtenido);
    }
}
