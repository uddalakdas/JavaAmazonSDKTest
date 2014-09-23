package com.rishi.amazonsdktest;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
public class CreateEc2Instance {
	static AmazonEC2 ec2;
	public CreateEc2Instance(AWSCredentials credentials){
		ec2 = new AmazonEC2Client(credentials);
	}
	public String createInstance(){
		String dns = null;
		RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
	    .withInstanceType("t1.micro")
	    .withImageId("ami-8c4d81e4")
	    .withMinCount(1)
	    .withMaxCount(1)
	    .withSecurityGroupIds("sg-324fa259")
	    .withKeyName("WarDeploymentviaPepprKey");
		
		 RunInstancesResult runInstancesResult = ec2.runInstances(runInstancesRequest);
		 List<Instance> instances = runInstancesResult.getReservation().getInstances();
		 Collection<String> instanceIds=new HashSet<String>();
		 for(Instance instance : instances){
			 instanceIds.add(instance.getInstanceId());
		 }
		 int state = 0;
		 do {
			 DescribeInstancesRequest request = new DescribeInstancesRequest();
			 request.setInstanceIds(instanceIds);
			 DescribeInstancesResult describeInstancesRequest = ec2.describeInstances(request);
			 List<Reservation> reservations = describeInstancesRequest.getReservations();
			 for (Reservation reservation : reservations) {
				 List<Instance> instanceResult = reservation.getInstances();
				 for(Instance ins : instanceResult){
					 state = ins.getState().getCode();
					 dns = ins.getPublicDnsName();
					 
				 }
             
			 }
			 try {
					Thread.sleep(10000);
				} 
			 catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			 
		 }
         while(state != 16);
      
		 return dns;
	}

}
