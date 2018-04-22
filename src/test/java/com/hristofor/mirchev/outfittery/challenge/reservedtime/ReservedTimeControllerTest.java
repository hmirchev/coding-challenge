package com.hristofor.mirchev.outfittery.challenge.reservedtime;

import com.hristofor.mirchev.outfittery.challenge.reservedtime.controller.ReservedTimeController;
import com.hristofor.mirchev.outfittery.challenge.reservedtime.service.ReservedTimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ReservedTimeController.class)
public class ReservedTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservedTimeService reservedTimeService;

    @Test
    public void getReservedTime_unauthorized() throws Exception {
        mockMvc.perform(get("/v1/reservedTimes")).andExpect(status().isUnauthorized());
    }
}
