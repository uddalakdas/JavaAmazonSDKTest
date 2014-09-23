package com.rishi.amazonsdktest.utils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.rishi.amazonsdktest.CheckEc2Instances;
import com.rishi.amazonsdktest.CreateEc2Instance;
public class Sample {
	static AWSCredentials credentials = null;
	public static void init(){
		try {
    		credentials = new ProfileCredentialsProvider().getCredentials();
    } 
    catch (Exception e) {
    	throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
	
	}
	public static void main(String[] args) {
		
		init();
        System.out.println("===========================================");
        System.out.println("Welcome to the AWS Java SDK!");
        System.out.println("===========================================");
		
		
		
        CheckEc2Instances ec2Instances = new CheckEc2Instances(credentials);
		ec2Instances.checkEc2Instance();
		CreateEc2Instance createInstances = new CreateEc2Instance(credentials);
		String instanceDns = createInstances.createInstance();
		System.out.println("DNS - "+instanceDns);
		

	}

}
