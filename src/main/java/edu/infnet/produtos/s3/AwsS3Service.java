/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.infnet.produtos.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Marcelo Vergara <http://marcelo-vergara.codes/>
 */
@Service
public class AwsS3Service {

    @Autowired
    private AmazonS3 amazonS3Client;

    public boolean upload(File file, String filename, String bucketName) {

        try {
            amazonS3Client.putObject(new PutObjectRequest(bucketName, filename, file).withCannedAcl(CannedAccessControlList.PublicRead));

            return true;

        } catch (SdkClientException e) {

            System.out.println("Exception upload: " + e.getMessage());

            return false;
        }
    }

    //delete
    public boolean delete(String bucketName, String filename) {

        try {
            DeleteObjectsRequest delObjReq = new DeleteObjectsRequest(bucketName).withKeys(filename);

            amazonS3Client.deleteObjects(delObjReq);

            return true;

        } catch (SdkClientException e) {

            System.out.println("Exception delete: " + e.getMessage());

            return false;
        }
    }

}
