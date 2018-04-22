package com.hristofor.mirchev.outfittery.challenge.stylists;

import com.hristofor.mirchev.outfittery.challenge.stylists.controller.StylistController;
import com.hristofor.mirchev.outfittery.challenge.stylists.service.StylistService;
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
@WebMvcTest(StylistController.class)
public class StylistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StylistService stylistService;

    @Test
    public void getStylists_unauthorized() throws Exception {
        mockMvc.perform(get("/v1/stylists")).andExpect(status().isUnauthorized());
    }
}
