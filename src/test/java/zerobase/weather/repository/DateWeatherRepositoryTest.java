package zerobase.weather.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import zerobase.weather.domain.DateWeather;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class DateWeatherRepositoryTest {

    @Autowired
    private DateWeatherRepository dateWeatherRepository;

    @Test
    void testFindAllByDate() {
        DateWeather sampleDateWeather = new DateWeather();
        sampleDateWeather.setDate(LocalDate.of(2024, 2, 6));
        sampleDateWeather.setWeather("Clear");
        sampleDateWeather.setIcon("01d");
        sampleDateWeather.setTemperature(10.0);
        dateWeatherRepository.save(sampleDateWeather);

        List<DateWeather> dateWeatherList = dateWeatherRepository.findAllByDate(LocalDate.of(2024, 2, 6));
        assertFalse(dateWeatherList.isEmpty());
        assertEquals(1, dateWeatherList.size());
        assertEquals("Clear", dateWeatherList.get(0).getWeather());
    }
  
}