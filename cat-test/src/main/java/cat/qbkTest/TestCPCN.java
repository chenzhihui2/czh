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
        bankInfo.setAccountNumber("6214830295637655");
        bankInfo.setAccountName("李海斌");
        bankInfo.setAccountType("11");

        BankInfo bankInfo2=new BankInfo();
        bankInfo2.setBankId("105");
        bankInfo2.setAccountNumber("6217002000035227141");
        bankInfo2.setAccountName("马嘉悦");
        bankInfo2.setAccountType("11");

        BankInfo bankInfo3=new BankInfo();
        bankInfo3.setBankId("301");
        bankInfo3.setAccountNumber("6222620920003700799");
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
    public static void main(String[] args) throws Exception {
//            testWhiteListUpload();
//        testWhiteListOneQuery();
        testWhiteListBatcheQuery();
    }

}
