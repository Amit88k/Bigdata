package bigdata.loaddataintomarfs;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class LoadFileIntoMaprFS {

    public static void main(String[] args) throws IOException {
    	
    	// this job takes two command line arguments
    	// input path: local file path and 
    	// output path: hdfs or mapr path where to save the file
        String uri = args[0];
        InputStream in = null;
        Path pt = new Path(uri);
        Configuration myConf = new Configuration();
        myConf.set("fs.defaultFS","hdfs://maprdemo:7222");
        Path outputPath = new Path(args[1]);

        FileSystem fs = FileSystem.get(myConf);
        OutputStream os = fs.create(outputPath);
        try{
            InputStream is = new BufferedInputStream(new FileInputStream(uri));
            IOUtils.copyBytes(is, os, 4096, false);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            IOUtils.closeStream(in);
        }
    }
}

////spark-submit --class bigdata.loaddataintomaprfs.LoadFileIntoMaprFS --master local[*] uber-loaddataintomarfs-0.0.1-SNAPSHOT.jar /tmp/SalesData.csv maprfs:///user/user01/filename.csv
////spark-submit --class MainClassName --master local[*] jar path/of/local/file/to/move path/to/destination
