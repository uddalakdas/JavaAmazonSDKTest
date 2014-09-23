package com.rishi.amazonsdktest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.autoscaling.model.Instance;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Reservation;

public class CheckEc2Instances{
	static AmazonEC2 ec2;
	public CheckEc2Instances(AWSCredentials credentials){
		ec2 = new AmazonEC2Client(credentials);
	}
	public void checkEc2Instance(){
		 
		 try {
	           /* DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();
	            System.out.println("You have access to " + availabilityZonesResult.getAvailabilityZones().size() +
	                    " Availability Zones.");
				*/
	            DescribeInstancesResult describeInstancesRequest = ec2.describeInstances();
	            List<Reservation> reservations = describeInstancesRequest.getReservations();
	            Set<Instance> instances = new HashSet<Instance>();

	            for (Reservation reservation : reservations) {
	                instances.addAll((List)reservation.getInstances());
	            }

	            System.out.println("You have " + instances.size() + " Amazon EC2 instance(s) running.");
	        } catch (AmazonServiceException ase) {
	                System.out.println("Caught Exception: " + ase.getMessage());
	                System.out.println("Reponse Status Code: " + ase.getStatusCode());
	                System.out.println("Error Code: " + ase.getErrorCode());
	                System.out.println("Request ID: " + ase.getRequestId());
	        }
		 
	 }
}