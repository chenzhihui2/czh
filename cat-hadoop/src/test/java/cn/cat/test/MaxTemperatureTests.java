package cn.cat.test;

import cn.cat.mr.MaxTemperatureMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Test;

import java.io.IOException;
import java.util.Calendar;

/**
 * author czh 453775810@qq.com
 * date: 2017/6/1
 * comments:
 */
public class MaxTemperatureTests {
    @Test
    public void processValidRecord() throws IOException {
//        Calendar loanCalendar=Calendar.getInstance();
//        loanCalendar.add(Calendar.MONTH,36);
//        loanCalendar.add(Calendar.DATE,-1);
//        System.out.println(loanCalendar.getTime());
        if("".equals(null)){

        }
//        Text text=new Text("0029029070999991901010320004+64333+023450FM-12+000599999V0202301N" +
//                "011819999999N0000001N9-00281+99999101741ADDGF108991999999999999999999");
//        new MapDriver<LongWritable,Text,Text,IntWritable>()
//                .withMapper(new MaxTemperatureMapper())
//                .withInput(new LongWritable(1901),text)
//                .withOutput(new Text("1901"),new IntWritable(-28))
//                .runTest();
    }
}
