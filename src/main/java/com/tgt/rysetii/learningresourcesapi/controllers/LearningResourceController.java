package com.tgt.rysetii.learningresourcesapi.controllers;

import com.tgt.rysetii.learningresourcesapi.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapi.service.LearningResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/learningresources/v1")
public class LearningResourceController {

    @Autowired
    private LearningResourceService learningResourceService;

    @GetMapping("/")
    public List<LearningResource> getAllLearningResources(){
        return learningResourceService.getLearningResource();
    }

    @PostMapping(value = "/", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveLearningResource(@RequestBody List<LearningResource> learningResources){
        learningResourceService.saveLearningResource(learningResources);
    }

    @DeleteMapping("/learningResource/{learningResourceId}")
    public void deleteLearningResource(@PathVariable int learningResourceId){
        learningResourceService.deleteLearningResourceById(learningResourceId);
    }
}
