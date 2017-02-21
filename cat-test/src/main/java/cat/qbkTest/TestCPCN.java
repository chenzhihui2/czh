package cat.qbkTest;

import cat.util.HttpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/15.
 */
public class TestCPCN {
    public static void testWhiteListUpload() throws Exception {
        BankInfo bankInfo=new BankInfo();
        bankInfo.setBankId("308");
        bankInfo.setAccountNumber("6214830295637655"); //CMB 招商银行
        bankInfo.setAccountName("李海斌");
        bankInfo.setAccountType("11");

        BankInfo bankInfo2=new BankInfo();
        bankInfo2.setBankId("105");
        bankInfo2.setAccountNumber("6217002000035227141");//CCB 建设银行
        bankInfo2.setAccountName("马嘉悦");
        bankInfo2.setAccountType("11");

        BankInfo bankInfo3=new BankInfo();
        bankInfo3.setBankId("301");
        bankInfo3.setAccountNumber("6222620920003700799");//BCM 交通银行
        bankInfo3.setAccountName("杨世博");
        bankInfo3.setAccountType("11");
        List<BankInfo> list=new ArrayList<>();
//        list.add(bankInfo);
        list.add(bankInfo2);
        list.add(bankInfo3);
        String json= JSON.toJSONString(list);
        String result=HttpUtils.doPost("http://localhost:8081/pay/cpcn/whiteListUpload",json,"application/json","utf-8");
        System.out.print(result);
    }

    public static void testWhiteListOneQuery() throws Exception {
        BankInfo bankInfo=new BankInfo();
        bankInfo.setAccountNumber("6214830295637655");
        String json= JSON.toJSONString(bankInfo);
        String result=HttpUtils.doPost("http://localhost:8081/pay/cpcn/whiteListOnQuery",json,"application/json","utf-8");
        System.out.print(result);
    }

    public static void testWhiteListBatcheQuery() throws Exception {//batchNo 2017021515225142042
        Map<String,String> map=new HashMap<String,String>();
        map.put("batchNo","20170215152251420421");
        String json= JSON.toJSONString(map);
        String result=HttpUtils.doPost("http://localhost:8081/pay/cpcn/whiteListBatchQuery",json,"application/json","utf-8");
        System.out.print(result);
    }

    public static void onePayRequest() throws Exception {
        Map<String,String> map=new HashMap<String,String>();
//        map.put("amount","100");
//        map.put("accountName","马嘉悦");
//        map.put("accountNumber","6217002000035227141");
//        map.put("identificationNumber","362301198611020522");

        map.put("amount","100.01");
        map.put("name","马嘉悦");
        map.put("cardNo","6217002000035227141");
        map.put("idCard","362301198611020522");
        map.put("nid","201612200001427144");
//        map.put("remark","30");//测试环境备注10成功，备注20失败，备注30处理中
        String json= JSON.toJSONString(map);
//        String result=HttpUtils.doPost("http://localhost:8081/pay/cpcn/onePayRequest",json,"application/json","utf-8");
        String result=HttpUtils.doPost("http://117.78.45.101:8083/pay/cpcn/onePayRequest",json,"application/json","utf-8");
        System.out.print(result);
    }

    public static void batchPayRequest() throws Exception {
        Map<String,String> map=new HashMap<String,String>();
        map.put("amount","100.01");
        map.put("name","马嘉悦");
        map.put("cardNo","6217002000035227141");
        map.put("idCard","362301198611020522");
        map.put("nid","201612200001427144");

        Map<String,String> map2=new HashMap<String,String>();
        map2.put("amount","111.01");
        map2.put("name","李海斌");
        map2.put("cardNo","6214830295637655");
        map2.put("idCard","610303198611221212");
        map2.put("nid","201612200001427142");

        List<Map> mapList=new ArrayList<>();
        mapList.add(map);
        mapList.add(map2);
//        map.put("remark","30");//测试环境备注10成功，备注20失败，备注30处理中
        String json= JSON.toJSONString(mapList);
        String result=HttpUtils.doPost("http://localhost:8081/pay/cpcn/batchPay",json,"application/json","utf-8");
//        String result=HttpUtils.doPost("http://117.78.45.101:8083/pay/cpcn/batchPay",json,"application/json","utf-8");
        System.out.print(result);
    }

    public static void batchPayStatQuery() throws Exception {
        String params="batchNo=2017022015420896790";
        String result=HttpUtils.doPost("http://localhost:8081/pay/cpcn/batchPayQuery",params,"application/x-www-form-urlencoded","utf-8");
//        String result=HttpUtils.doPost("http://117.78.45.101:8083/pay/cpcn/batchPay",json,"application/json","utf-8");
        System.out.print(result);
    }
    public static void batchPayDetailQuery() throws Exception {
        String params="batchNo=2017022015393043433&itemNo=2017022015393147922";
        String result=HttpUtils.doPost("http://localhost:8081/pay/cpcn/batchPayDetailQuery",params,"application/x-www-form-urlencoded","utf-8");
//        String result=HttpUtils.doPost("http://117.78.45.101:8083/pay/cpcn/batchPay",json,"application/json","utf-8");
        System.out.print(result);
    }

    public static void payWithRouter() throws Exception {

        StringBuffer sb=new StringBuffer();
        sb.append("amount").append("=").append("1000");
        sb.append("&").append("name").append("=").append("马嘉悦");
        sb.append("&").append("cardNo").append("=").append("6217002000035227141");
        sb.append("&").append("idCard").append("=").append("362301198611020522");
        sb.append("&").append("nid").append("=").append("201612200001427149");
//        String result=HttpUtils.doPost("http://localhost:8081/pay/repay/custRepayWithRouter",sb.toString(),"application/x-www-form-urlencoded","utf-8");
        String result=HttpUtils.doPost("http://117.78.45.101:8083/pay/repay/custRepayWithRouter",sb.toString(),"application/x-www-form-urlencoded","utf-8");
        System.out.print(result);
    }

    public static void callBack() throws Exception {
        Map<String,String> map=new HashMap<String,String>();
        map.put("message","100.01");
        map.put("name","马嘉悦");
        String json= JSON.toJSONString(map);
        String result=HttpUtils.doPost("http://localhost:8081/pay/cpcn/onePayCallBack",json,"application/json","utf-8");
        System.out.print(result);
    }

    public static void onePayCheckBookQuery() throws Exception {
       String json="date=2017-02-16";
        String result=HttpUtils.doPost("http://localhost:8081/pay/cpcn/checkBookQuery",json,"application/x-www-form-urlencoded","utf-8");
        System.out.print(result);
    }
    public static void main(String[] args) throws Exception {
//            testWhiteListUpload();
//        testWhiteListOneQuery();
//        testWhiteListBatcheQuery();
//        onePayRequest();
//        callBack();
//        onePayCheckBookQuery();
        payWithRouter();
//        batchPayRequest();
//        batchPayStatQuery();
//        batchPayDetailQuery();
    }

}
