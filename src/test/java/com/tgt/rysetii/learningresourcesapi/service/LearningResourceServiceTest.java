package com.tgt.rysetii.learningresourcesapi.service;

import com.tgt.rysetii.learningresourcesapi.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapi.entity.LearningResourceStatus;
import com.tgt.rysetii.learningresourcesapi.repository.LearningResourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LearningResourceServiceTest {
    private LearningResourceRepository learningResourceRepository = Mockito.mock(LearningResourceRepository.class);

    @InjectMocks
    private  LearningResourceService learningResourceService;

    @Test
    public void getProfitMarginsOfAllLearningResourcesTest(){
        List<LearningResource> learningResources = new ArrayList<LearningResource>();
        learningResources.add(new LearningResource(121, "TestLearningResource1", 100.0, 140.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));
        learningResources.add(new LearningResource(122, "TestLearningResource2", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));

        List<Double> expectedProfitMargins = learningResources.stream().map(lr -> ((lr.getSellingPrice()-lr.getCostPrice())/lr.getSellingPrice())).collect(Collectors.toList());
        when(learningResourceRepository.findAll()).thenReturn(learningResources);

        List<Double> actualProfitMargins = learningResourceService.getProfitMargins();
        Assertions.assertEquals(expectedProfitMargins, actualProfitMargins, "Profit Margins Mismatch");
    }

    @Test
    public void sortByProfitMarginTest(){
        List<LearningResource> learningResources = new ArrayList<LearningResource>();
        learningResources.add(new LearningResource(121, "TestLearningResource1", 100.0, 140.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));
        learningResources.add(new LearningResource(122, "TestLearningResource2", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));

        learningResources.sort((lr1,lr2)->{
            Double pm1 = (lr1.getSellingPrice()-lr1.getCostPrice())/lr1.getSellingPrice();
            Double pm2 = (lr2.getSellingPrice()- lr2.getCostPrice())/lr2.getSellingPrice();
            return pm2.compareTo(pm1);
        });

        when(learningResourceRepository.findAll()).thenReturn(learningResources);
        List<LearningResource> sortedLearningResource = learningResourceService.sortByProfitMargin();

        Assertions.assertEquals(learningResources, sortedLearningResource, "Sorting Mismatch");
    }

    @Test
    public void saveLearningResourceTest(){
        List<LearningResource> learningResources = new ArrayList<LearningResource>();
        learningResources.add(new LearningResource(121, "TestLearningResource1", 100.0, 140.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));
        learningResources.add(new LearningResource(122, "TestLearningResource2", 100.0, 120.0, LearningResourceStatus.LIVE, LocalDate.now(), LocalDate.now().plusMonths(3), LocalDate.now().plusYears(2)));

        learningResourceService.saveLearningResource(learningResources);
        verify(learningResourceRepository, times(learningResources.size())).save(any(LearningResource.class));
    }

    @Test
    public void deleteLearningResourceByIdTest(){
        int id = 323;
        learningResourceService.deleteLearningResourceById(id);
        verify(learningResourceRepository, times(1)).deleteById(id);
    }
}
