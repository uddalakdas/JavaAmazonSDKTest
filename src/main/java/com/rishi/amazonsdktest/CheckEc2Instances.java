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

class CheckEc2Instances{
	static AmazonEC2 ec2;
	private static void init() throws Exception {

        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (~/.aws/credentials).
         */
        AWSCredentials credentials = null;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (~/.aws/credentials), and is in valid format.",
                    e);
        }
        ec2 = new AmazonEC2Client(credentials);
	}
	 public static void main(String[] args) throws Exception {

	        System.out.println("===========================================");
	        System.out.println("Welcome to the AWS Java SDK!");
	        System.out.println("===========================================");

	        init();

	        /*
	         * Amazon EC2
	         *
	         * The AWS EC2 client allows you to create, delete, and administer
	         * instances programmatically.
	         *
	         * In this sample, we use an EC2 client to get a list of all the
	         * availability zones, and all instances sorted by reservation id.
	         */
	        try {
	            DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();
	            System.out.println("You have access to " + availabilityZonesResult.getAvailabilityZones().size() +
	                    " Availability Zones.");

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