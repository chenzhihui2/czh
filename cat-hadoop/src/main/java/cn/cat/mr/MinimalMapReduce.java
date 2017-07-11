package cn.cat.mr;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;


import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * author czh 453775810@qq.com
 * date: 2017/6/2
 * comments:
 */
public  class MinimalMapReduce extends Configured implements Tool{
    @Override
    public int run(String[] args) throws Exception {
        if(args.length!=2){
            System.err.printf("Usage:%s [generic options] <input> <output>\n",
                   getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }
        Job job=new Job(getConf());
        job.setJarByClass(getClass());
        FileInputFormat.addInputPath(job,new Path(args[0]));
        FileOutputFormat.setOutputPath(job,new Path(args[1]));
        return job.waitForCompletion(true)?0:1;
    }

    public static void main(String args[]) throws Exception {
        String[] arr={"hdfs://localhost:9000/user/root/input/core-site.xml","output"};
        int exitCode=ToolRunner.run(new MinimalMapReduce(),arr);
        System.exit(exitCode);
    }
}
