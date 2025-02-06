package zerobase.weather.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import zerobase.weather.domain.DateWeather;
import zerobase.weather.domain.Diary;
import zerobase.weather.repository.DateWeatherRepository;
import zerobase.weather.repository.DiaryRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    @Mock
    private DiaryRepository diaryRepository;

    @Mock
    private DateWeatherRepository dateWeatherRepository;

    @InjectMocks
    private DiaryService diaryService;

    private DateWeather sampleDateWeather;
    private Diary sampleDiary;

    @BeforeEach
    void setUp() {
        sampleDateWeather = new DateWeather();
        sampleDateWeather.setDate(LocalDate.of(2024, 2, 6));
        sampleDateWeather.setWeather("Clear");
        sampleDateWeather.setIcon("01d");
        sampleDateWeather.setTemperature(10.0);

        sampleDiary = new Diary();
        sampleDiary.setDateWeather(sampleDateWeather);
        sampleDiary.setText("오늘 날씨는 맑음");
    }

    @Test
    void testCreateDiary() {
        when(dateWeatherRepository.findAllByDate(any())).thenReturn(Collections.singletonList(sampleDateWeather));
        when(diaryRepository.save(any())).thenReturn(sampleDiary);

        diaryService.createDiary(LocalDate.of(2024, 2, 6), "오늘 날씨는 맑음");

        verify(diaryRepository, times(1)).save(any(Diary.class));
    }

    @Test
    void testReadDiary() {
        when(diaryRepository.findAllByDate(any())).thenReturn(Collections.singletonList(sampleDiary));

        List<Diary> diaries = diaryService.readDiary(LocalDate.of(2024, 2, 6));

        assertFalse(diaries.isEmpty());
        assertEquals("오늘 날씨는 맑음", diaries.get(0).getText());
    }

    @Test
    void testReadDiaries() {
        when(diaryRepository.findAllByDateBetween(any(), any())).thenReturn(Collections.singletonList(sampleDiary));

        List<Diary> diaries = diaryService.readDiaries(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1));

        assertEquals(1, diaries.size());
    }

    @Test
    void testUpdateDiary() {
        when(diaryRepository.getFirstByDate(any())).thenReturn(sampleDiary);

        diaryService.updateDiary(LocalDate.of(2024, 2, 6), "수정된 일기");

        assertEquals("수정된 일기", sampleDiary.getText());
        verify(diaryRepository, times(1)).save(sampleDiary);
    }

    @Test
    void testDeleteDiary() {
        doNothing().when(diaryRepository).deleteAllByDate(any());

        diaryService.deleteDiary(LocalDate.of(2024, 2, 6));

        verify(diaryRepository, times(1)).deleteAllByDate(any());
    }
}
