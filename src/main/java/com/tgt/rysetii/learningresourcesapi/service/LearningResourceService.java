package com.tgt.rysetii.learningresourcesapi.service;

import com.tgt.rysetii.learningresourcesapi.entity.LearningResource;
import com.tgt.rysetii.learningresourcesapi.entity.LearningResourceStatus;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LearningResourceService {
    public void saveLearningResource(List<LearningResource> learningResources){
        saveLearningResourcesToCSV(learningResources);
    }
    public List<LearningResource> getLearningResource(){
        File learningResourceFile = new File("learningResources.csv");
        return readLearningResourcesFromCSV(learningResourceFile);
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
    private List<LearningResource> readLearningResourcesFromCSV(File csv){
        List<LearningResource> learningResources = new ArrayList<>();
        try {
            FileReader fr1 = new FileReader(csv);
            BufferedReader br1 = new BufferedReader(fr1);

            String line = null;
            line = br1.readLine();
            while(line!=null) {
                String[] attributes = line.split(",");
                LearningResource learningResource = createLearningResource(attributes);
                learningResources.add(learningResource);
                line = br1.readLine();
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return learningResources;
    }
    private LearningResource createLearningResource(String[] attributes){
        Integer id = Integer.parseInt(attributes[0].replaceAll("\\D+",""));
        String name = attributes[1];
        Double costPrice = Double.parseDouble(attributes[2]);
        Double sellingPrice = Double.parseDouble(attributes[3]);
        LearningResourceStatus learningResourcestatus = LearningResourceStatus.valueOf(attributes[4]);
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate createdDate = LocalDate.parse(attributes[5],dtf1);
        LocalDate publishedDate = LocalDate.parse(attributes[6],dtf1);
        LocalDate retiredDate = LocalDate.parse(attributes[7],dtf1);
        LearningResource learningResource = new LearningResource(id, name, costPrice, sellingPrice, learningResourcestatus, createdDate, publishedDate, retiredDate);
        return learningResource;
    }

    private void saveLearningResourcesToCSV(List<LearningResource> learningResources){
        try{
            File learningResourceFile = new File("learningResources.csv");
            FileWriter fw1 = new FileWriter(learningResourceFile, true);
            BufferedWriter bw1 = new BufferedWriter(fw1);
            for (LearningResource learningResource : learningResources) {
                bw1.newLine();
                String data = "";
                data.concat(learningResource.getId().toString());
                data.concat(",");
                data.concat(learningResource.getName());
                data.concat(",");
                data.concat(learningResource.getCostPrice().toString());
                data.concat(",");
                data.concat(learningResource.getSellingPrice().toString());
                data.concat(",");
                data.concat(learningResource.isProductStatus().toString());
                data.concat(",");
                data.concat(learningResource.getCreatedDate().toString());
                data.concat(",");
                data.concat(learningResource.getPublishedDate().toString());
                data.concat(",");
                data.concat(learningResource.getRetiredDate().toString());
                bw1.write(data);
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
