package cn.cat.mr;

import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.io.IOUtils;

import java.io.InputStream;
import java.net.URL;


public class URLCat {
    static {
        URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
    }

    public static void main(String[] args) throws Exception {
//        InputStream in = null;
//        String url="hdfs://localhost:9000/user/root/input/core-site.xml";
//        try {
//            in = new URL(url).openStream();
//            IOUtils.copyBytes(in, System.out, 4096, false);
//        } finally {
//            IOUtils.closeStream(in);
//        }
         print();

    }

    public static String print(){
        try {
            try {
                if(true){
                    throw new Exception("sfds");
                }
               return "22";
            }catch (Exception e){
                System.out.println(1);
                return "11";
            }
        }catch (Exception e){

        }finally {
            System.out.println(2);
        }
        return "";
    }
}
