package zerobase.weather.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import zerobase.weather.domain.Diary;
import zerobase.weather.service.DiaryService;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DiaryController.class)
class DiaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiaryService diaryService;

    @Test
    void testCreateDiary() throws Exception {
        mockMvc.perform(post("/create/diary")
                        .param("date", "2024-02-06")
                        .content("오늘 날씨는 맑음")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());

        verify(diaryService, times(1)).createDiary(LocalDate.of(2024, 2, 6), "오늘 날씨는 맑음");
    }

    @Test
    void testReadDiary() throws Exception {
        when(diaryService.readDiary(any())).thenReturn(Collections.singletonList(new Diary()));

        mockMvc.perform(get("/read/diary")
                        .param("date", "2024-02-06"))
                .andExpect(status().isOk());
    }

    @Test
    void testReadDiaries() throws Exception {
        when(diaryService.readDiaries(any(), any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/read/diaries")
                        .param("startDate", "2024-01-01")
                        .param("endDate", "2024-02-01"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateDiary() throws Exception {
        mockMvc.perform(put("/update/diary")
                        .param("date", "2024-02-06")
                        .content("수정된 일기")
                        .contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());

        verify(diaryService, times(1)).updateDiary(LocalDate.of(2024, 2, 6), "수정된 일기");
    }

    @Test
    void testDeleteDiary() throws Exception {
        mockMvc.perform(delete("/delete/diary")
                        .param("date", "2024-02-06"))
                .andExpect(status().isOk());

        verify(diaryService, times(1)).deleteDiary(LocalDate.of(2024, 2, 6));
    }
}
