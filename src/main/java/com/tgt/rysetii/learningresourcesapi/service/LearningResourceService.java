package com.tgt.rysetii.learningresourcesapi.service;

import com.tgt.rysetii.learningresourcesapi.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapi.entity.LearningResourceStatus;
import com.tgt.rysetii.learningresourcesapi.repository.LearningResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class LearningResourceService {

    @Autowired
    private LearningResourceRepository learningResourcesRepo;
    public void saveLearningResource(List<LearningResource> learningResources){
        for (LearningResource learningResource:learningResources){
            learningResourcesRepo.save(learningResource);
        }
    }
    public List<LearningResource> getLearningResource(){
        return learningResourcesRepo.findAll();
    }
    public List<Double> getProfitMargins(){
        List<LearningResource> learningResources = getLearningResource();
        List<Double> profitMargins = new ArrayList<>();
        for (LearningResource learningResource:learningResources) {
            profitMargins.add((learningResource.getSellingPrice()-learningResource.getCostPrice())/learningResource.getSellingPrice());
        }
        return profitMargins;
    }
    public List<LearningResource> sortByProfitMargin(){
        List<LearningResource> learningResources = getLearningResource();
        learningResources.sort((lr1,lr2)->{
            Double pf1 = (lr1.getSellingPrice()- lr1.getCostPrice())/lr1.getSellingPrice();
            Double pf2 = (lr2.getSellingPrice()- lr2.getCostPrice())/lr2.getSellingPrice();
            return pf2.compareTo(pf1);
        });
        return learningResources;
    }
}
