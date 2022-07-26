package com.tgt.rysetii.learningresourcesapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tgt.rysetii.learningresourcesapi.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapi.entity.LearningResourceStatus;
import com.tgt.rysetii.learningresourcesapi.service.LearningResourceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LearningResourceController.class)
public class LearningResourceControllerTest {

    @MockBean
    private LearningResourceService learningResourceService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getAllLearningResourcesTest() throws Exception {
        List<LearningResource> learningResources = new ArrayList<LearningResource>();
        learningResources.add(new LearningResource(121, "TestLearningResource1", 100.0, 140.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));
        learningResources.add(new LearningResource(122, "TestLearningResource2", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));

        String expectedResponse = objectMapper.writeValueAsString(learningResources);

        when(learningResourceService.getLearningResource()).thenReturn(learningResources);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/Resources/")).andExpect(status().isOk()).andReturn();
        Assertions.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void saveLearningResourceTest() throws Exception {
        List<LearningResource> learningResources = new ArrayList<LearningResource>();
        learningResources.add(new LearningResource(121, "TestLearningResource1", 100.0, 140.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));
        learningResources.add(new LearningResource(122, "TestLearningResource2", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/learningresources/v1/").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(learningResources))).andExpect(status().isCreated());
    }

    @Test
    public void deleteLearningResourceTest() throws Exception {
        int id = 323;
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/learningresources/v1/learningresource/" + id)).andExpect(status().isOk());
    }
}
