package com.example.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.example.config.AwsConfig;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class S3Service {

    AwsConfig awsConfig = new AwsConfig();

    private static final Logger log = LoggerFactory.getLogger(S3Service.class);

    AmazonS3 amazonS3Client = awsConfig.amazonS3();

    //Criar um bucket
    public void createS3Bucket(String bucketName) {
        if(amazonS3Client.doesBucketExist(bucketName)) {
            log.info("Bucket name already in use. Try another name.");
            return ;
        }
        amazonS3Client.createBucket(bucketName);
    }

    //Listar buckets existentes
    public List<Bucket> listBuckets(){
        return amazonS3Client.listBuckets();
    }

    //Apagar um bucket
    public void deleteBucket(String bucketName){
        try {
            amazonS3Client.deleteBucket(bucketName);
        } catch (AmazonServiceException e) {
            log.error(e.getErrorMessage());
            return;
        }
    }
    //Colocar um objeto no bucket:
    public void putObject(String bucketName, String objectName){
        File myObj = new File("C:\\Projetos\\PROJETO S3 + SPRING\\aws_s3_spring_boot\\src\\main\\resources\\arquivo.txt");
        amazonS3Client.putObject(new PutObjectRequest(bucketName, objectName,
                myObj));
    }

    //Listar os nomes de objetos de um bucket
    public List<S3ObjectSummary> listObjects(String bucketName){
        ObjectListing objectListing = amazonS3Client.listObjects(bucketName);
        return objectListing.getObjectSummaries();
    }


    //Fazer download de um objeto de um bucket
    public void downloadObject(String bucketName, String objectName){
        S3Object s3object = amazonS3Client.getObject(bucketName, objectName);
        S3ObjectInputStream inputStream = s3object.getObjectContent();
        try {
            FileUtils.copyInputStreamToFile(inputStream, new File("." + File.separator + objectName));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

   //Apagar um objeto:
   public void deleteObject(String bucketName, String objectName){
       amazonS3Client.deleteObject(bucketName, objectName);
   }

    //Mover um objeto entre dois buckets
    public void moveObject(String bucketSourceName, String objectName, String bucketTargetName){
        amazonS3Client.copyObject(
                bucketSourceName,
                objectName,
                bucketTargetName,
                objectName
        );
    }




}

