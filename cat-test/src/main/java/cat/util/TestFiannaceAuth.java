package cat.util;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/28.
 */
public class TestFiannaceAuth {
    public static void main(String args[]) throws Exception {
        HashMap<String,String> header=new HashMap<>();
        header.put("Content-pwkey","1");
        header.put("Content-uid","1");
        HttpUtils.setHeader(header);
        String re=HttpUtils.doPost("http://finance.upenny.cn:5133/loanReg/getLoanInfoToThird",null,null,"UTF-8");
        System.out.println(re);
    }
}
