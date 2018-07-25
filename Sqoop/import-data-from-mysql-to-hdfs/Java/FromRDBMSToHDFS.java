package hadoop.batch_ingestion;

import org.apache.sqoop.client.SqoopClient;
import org.apache.sqoop.model.MDriverConfig;
import org.apache.sqoop.model.MFromConfig;
import org.apache.sqoop.model.MJob;
import org.apache.sqoop.model.MLink;
import org.apache.sqoop.model.MLinkConfig;
import org.apache.sqoop.model.MSubmission;
import org.apache.sqoop.model.MToConfig;
import org.apache.sqoop.submission.counter.Counter;
import org.apache.sqoop.submission.counter.CounterGroup;
import org.apache.sqoop.submission.counter.Counters;
import org.apache.sqoop.validation.Status;

public class FromRDBMSToHDFS
{
	
	static MLink createSqlLink(SqoopClient client){
		MLink link = client.createLink("generic-jdbc-connector");
        link.setName("linkSql");
        link.setCreationUser("Amit Khandelwal");
        MLinkConfig linkConfig = link.getConnectorLinkConfig();
        
        //fill in the link config values
        linkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:mysql://192.168.56.1/userdb");
        linkConfig.getStringInput("linkConfig.jdbcDriver").setValue("com.mysql.jdbc.Driver");
        linkConfig.getStringInput("linkConfig.username").setValue("root");
        linkConfig.getStringInput("linkConfig.password").setValue("@Khandelwal88");
        
        //save the link object that was filled 
        Status status = client.saveLink(link);
        if(status.canProceed()) {
        	System.out.println("Created link with Link Id : " + link.getPersistenceId());
        } else {
        	System.out.println("Something went wrong creating the link");
        }
        
        return link;
    }
	
	static MLink createHdfsLink(SqoopClient client){
		MLink link = client.createLink("hdfs-connector");
		link.setName("linkHdfs");
		link.setCreationUser("Amit Khandelwal");
		
		MLinkConfig linkConfig = link.getConnectorLinkConfig();
		linkConfig.getStringInput("linkConfig.uri").setValue("maprfs://maprdemo:7222");
		
		Status status = client.saveLink(link);
        if(status.canProceed()) {
        	System.out.println("Created link with Link Id : " + link.getPersistenceId());
        } else {
        	System.out.println("Something went wrong creating the link");
        }
		
		return link;
	}
	
//	static MJob createJob(SqoopClient client, long connectorId) {
//		//creating dummy job object
//		
//		
//	}
	
         
	public static void main( String[] args ) throws Exception
    {
		final String url = "http://localhost:12000/sqoop/";
        SqoopClient client = new SqoopClient(url);
        
//        long mySqlConnectorId = 1;
//        long hdfsConnectorId = 2;
        
        MLink mySqlLink = createSqlLink(client);
        MLink hdfsLink = createHdfsLink(client);
        
      //Creating dummy job object
//        long fromLinkId = 1;// for jdbc connector
//        long toLinkId = 2; // for HDFS connector
        
        MJob job = client.createJob(mySqlLink.getName(), hdfsLink.getName());
        job.setName("newJob");
        job.setCreationUser("Amit Khandelwal");
        
        //set the "FROM" link job config values
        MFromConfig fromJobConfig = job.getFromJobConfig();
        fromJobConfig.getStringInput("fromJobConfig.schemaName").setValue("userdb");
        fromJobConfig.getStringInput("fromJobConfig.tableName").setValue("user_info_table");
        fromJobConfig.getStringInput("fromJobConfig.partitionColumn").setValue("uniqueid");
        // set the "TO" link Job config values 
        MToConfig toJobConfig = job.getToJobConfig();
        toJobConfig.getEnumInput("toJobConfig.outputFormat").setValue("TEXT_FILE");
        toJobConfig.getEnumInput("toJobConfig.compression").setValue("NONE");
        toJobConfig.getStringInput("toJobConfig.outputDirectory").setValue("/user/root/sqoopData");
        // set the driver config values
        MDriverConfig driverConfig = job.getDriverConfig();
        driverConfig.getIntegerInput("throttlingConfig.numExtractors").setValue(1);
        driverConfig.getIntegerInput("throttlingConfig.numLoaders").setValue(1);
        
        Status status = client.saveJob(job);
        if(status.canProceed()) {
        	System.out.println("Created job with Job Id: " + job.getPersistenceId());
        } else {
        	System.out.println("Something went wrong creating the job");
        }
        
      //Job start
        MSubmission submission = client.startJob(job.getName());
        System.out.println("Job Submission Status : " + submission.getStatus());
        if(submission.getStatus().isRunning() && submission.getProgress() != -1) {
          System.out.println("Progress : " + String.format("%.2f %%", submission.getProgress() * 100));
        }
        System.out.println("Job link : " + submission.getExternalLink());
        Counters counters = submission.getCounters();
        if(counters != null) {
          System.out.println("Counters:");
          for(CounterGroup group : counters) {
            System.out.print("\t");
            System.out.println(group.getName());
            for(Counter counter : group) {
              System.out.print("\t\t");
              System.out.print(counter.getName());
              System.out.print(": ");
              System.out.println(counter.getValue());
            }
          }
        }


        //Check job status for a running job
        MSubmission submission1 = client.getJobStatus(job.getName());
        if(submission1.getStatus().isRunning() && submission1.getProgress() != -1) {
          System.out.println("Progress : " + String.format("%.2f %%", submission1.getProgress() * 100));
        }
        
        //client.wait(60000);
        //Stop a running job
        //client.stopJob(job.getName());
       
        
    }
}
