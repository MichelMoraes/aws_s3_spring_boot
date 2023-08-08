package com.example.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.example.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buckets")
@RequiredArgsConstructor
public class S3Controller {

    @Autowired
    private S3Service s3Service;

    @PostMapping(value = "/{bucketName}")
    public void createBucket(@PathVariable String bucketName){
        s3Service.createS3Bucket(bucketName);
    }

    //Listar os nomes de objetos de um bucket
    @GetMapping
    public List<String> listBuckets(){
        var buckets = s3Service.listBuckets();
        var names = buckets.stream().map(Bucket::getName).collect(Collectors.toList());
        return names;
    }

    // Deletar Bucket por param name
    @DeleteMapping(value = "/{bucketName}")
    public void deleteBucket(@PathVariable String bucketName){
        s3Service.deleteBucket(bucketName);
    }

    // criar objeto em bucket
    @PostMapping(value = "/{bucketName}/objects/{objectName}")
    public void createObject(@PathVariable String bucketName, @PathVariable String objectName) throws IOException {
        s3Service.putObject(bucketName, objectName);
    }

    //Fazer download de um objeto de um bucket
    @GetMapping(value = "/{bucketName}/objects/{objectName}")
    public File downloadObject(@PathVariable String bucketName, @PathVariable String objectName) throws IOException {
        s3Service.downloadObject(bucketName, objectName);
        return new File("./" + objectName);
    }

    //Mover um objeto entre dois buckets
    @PatchMapping(value = "/{bucketName}/objects/{objectName}/{bucketSource}")
    public void moveObject(@PathVariable String bucketName, @PathVariable String objectName, @PathVariable String bucketSource) throws IOException {
        s3Service.moveObject(bucketName, objectName, bucketSource);
    }

    // deletar Bucket
    @DeleteMapping(value = "/{bucketName}/objects/{objectName}")
    public void deleteBucket(@PathVariable String bucketName, @PathVariable String objectName){
        s3Service.deleteObject(bucketName, objectName);
    }

}
