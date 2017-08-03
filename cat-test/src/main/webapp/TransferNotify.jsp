<%@ page language="java" contentType="text/html; charset=gbk"%>
<%@ page import="java.util.*,java.io.*,com.yeepay.common.utils.*,org.dom4j.*,com.cfca.util.pki.*,com.cfca.util.pki.api.*,com.cfca.util.pki.cert.*,com.cfca.util.pki.cipher.*,com.cfca.util.pki.extension.*"%>
<%
	request.setCharacterEncoding("gbk");
	//商户密钥
	String hmacKey="su1KU96573FKlt580404tU6XJDcA004oD2u75cgA33Q2W7I1542xR38XaI3t";
	
	Map result = new LinkedHashMap();
	Map xmlMap = new LinkedHashMap();
	Map xmlBackMap = new LinkedHashMap();
	//模拟接收回调
	
	//接受主动通知信息
// 	String responseMsg = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n<data><cmd>TransferNotify</cmd><version>1.1</version><group_Id>10000450379</group_Id><mer_Id>10000450379</mer_Id><batch_No>201205280000001</batch_No><order_Id>e808968769</order_Id><status>S</status><message/><hmac>MIIFFAYJKoZIhvcNAQcCoIIFBTCCBQECAQExCzAJBgUrDgMCGgUAMC8GCSqGSIb3DQEHAaAiBCAwNDE1YjIyMTY0NmVhYTEwNjMyNGQ0ZjAyMGEwYzI4MKCCA9YwggPSMIIDO6ADAgECAhBc/XRnbqEydB7AdLyYqBtxMA0GCSqGSIb3DQEBBQUAMCoxCzAJBgNVBAYTAkNOMRswGQYDVQQKExJDRkNBIE9wZXJhdGlvbiBDQTIwHhcNMTYwNjE3MDI0ODU5WhcNMTkwNjE3MDI0ODU5WjCBgjELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMjEWMBQGA1UECxMNcmEueWVlcGF5LmNvbTEUMBIGA1UECxMLRW50ZXJwcmlzZXMxKDAmBgNVBAMUHzA0MUBaMTAwMDA0NTAzNzlAY2VzaGlAMDAwMDAwMDEwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAJoec2QYgGFwsFah4tLebwI2shoawNmmEs8C6DPqDCdZofCE1AFgTtz89VC/B1aZp2md87iwoqfIniZ296NhIC+NqbhWTh4jqlzyg8cFgPjcQJH3pqnG410NJcX+wJ9hGHdGbKsE2ek56RKLc6+41izqi9iQW0rOv4d8k2KMQIQtAgMBAAGjggGeMIIBmjAfBgNVHSMEGDAWgBTwje2zQbv77wgeVQLDMTfvPBROzTAdBgNVHQ4EFgQUAWcd/iBtqPUDGxi70rU3vMjDtYkwCwYDVR0PBAQDAgTwMAwGA1UdEwQFMAMBAQAwOwYDVR0lBDQwMgYIKwYBBQUHAwEGCCsGAQUFBwMCBggrBgEFBQcDAwYIKwYBBQUHAwQGCCsGAQUFBwMIMIH/BgNVHR8EgfcwgfQwV6BVoFOkUTBPMQswCQYDVQQGEwJDTjEbMBkGA1UEChMSQ0ZDQSBPcGVyYXRpb24gQ0EyMQwwCgYDVQQLEwNDUkwxFTATBgNVBAMTDGNybDEwNF8xNTYwODCBmKCBlaCBkoaBj2xkYXA6Ly9jZXJ0ODYzLmNmY2EuY29tLmNuOjM4OS9DTj1jcmwxMDRfMTU2MDgsT1U9Q1JMLE89Q0ZDQSBPcGVyYXRpb24gQ0EyLEM9Q04/Y2VydGlmaWNhdGVSZXZvY2F0aW9uTGlzdD9iYXNlP29iamVjdGNsYXNzPWNSTERpc3RyaWJ1dGlvblBvaW50MA0GCSqGSIb3DQEBBQUAA4GBAFkVpKT+ZPTZOoNL3HCFgzGd3S1zCfF8GmQ78tGIYTjJUMSA8xOUzuw5jc0rMLZp4ISl7pu/94xazyvdnij/Vwiwro2l5X/as4nbTHIbRDLnPgnCPxdyLFvV/elMU1UXlM2dU80zKhI0sT0+o5xiu0ZFIiNwtp3MNh1fKvOYrmb1MYHjMIHgAgEBMD4wKjELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMgIQXP10Z26hMnQewHS8mKgbcTAJBgUrDgMCGgUAMA0GCSqGSIb3DQEBAQUABIGAiL7GI+NV3qh/uI+LJ1jATsaymUs0FCrAbJvTB47X0tNdomldc1OEBsvNWmopyRWHhsYnOMlwUavo7UA0csEa0mj7jsKh++3WmTTMCcjcZI5xg0V0bnkNmrqNBSJxag0jiOYv0hrVxBMNS59BqaoyH4FSZguq3RxzVprFRb/Edlk=</hmac></data>\n";
	StringBuffer sb = new StringBuffer(2000);
	InputStream is = request.getInputStream();
    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
    //读取HTTP请求内容
    String buffer = null;
    while ((buffer = br.readLine()) != null) {
    //在页面中显示读取到的请求参数
   		sb.append(buffer);
    }
	String responseMsg = sb.toString();
	out.println(
	"<html><body><textarea rows=\"23\" cols=\"120\" name=\"xml\" id=\"xml\">" + 
	responseMsg +
	"</textarea></body></html>");
	System.out.println("服务器响应xml报文:" + responseMsg);
	
	Document document = null;
	try {
		document = DocumentHelper.parseText(responseMsg);
	} catch (DocumentException e) {
	} 
	Element rootEle = document.getRootElement();
	String cmdValue = rootEle.elementText("hmac");
	
	//对服务器响应报文进行验证签名
	com.cfca.util.pki.cipher.Session tempsession = null;
	String ALGORITHM = SignatureUtil.SHA1_RSA;
	JCrypto jcrypto =null;
	if(tempsession==null){
		try {
	        //初始化加密库，获得会话session
	        //多线程的应用可以共享一个session,不需要重复,只需初始化一次
	        //初始化加密库并获得session。
	        //系统退出后要jcrypto.finalize()，释放加密库
	        jcrypto = JCrypto.getInstance();
	        jcrypto.initialize(JCrypto.JSOFT_LIB, null);
	        tempsession = jcrypto.openSession(JCrypto.JSOFT_LIB);
	    } catch (Exception ex) {
	        System.out.println(ex.toString());
	    }
	}
	String sysPath = request.getRealPath("");
	JKey jkey = KeyUtil.getPriKey(sysPath+File.separator + "10000450379.pfx", "123456");
	X509Cert cert = CertUtil.getCert(sysPath+File.separator + "10000450379.pfx", "123456");
	X509Cert[] cs=new X509Cert[1];
	cs[0]=cert;
	boolean sigerCertFlag = false;
	SignatureUtil signUtil = new SignatureUtil();;
	if(cmdValue!=null){
		sigerCertFlag = signUtil.p7VerifySignMessage(cmdValue.getBytes(), tempsession);
		String backmd5hmac = xmlBackMap.get("hmac") + "";
		if(sigerCertFlag){
			System.out.println("证书验签成功");
			backmd5hmac = new String(signUtil.getSignedContent());
			System.out.println("证书验签获得的MD5签名数据为----" + backmd5hmac);
			System.out.println("证书验签获得的证书dn为----" + new String(signUtil.getSigerCert()[0].getSubject()));
			//将验签出来的结果数据与自己针对响应数据做MD5签名之后的数据进行比较是否相等
			Document backDocument = null;
			try {
				backDocument = DocumentHelper.parseText(responseMsg);
			} catch (DocumentException e) {
				System.out.println(e);
			} 
			Element backRootEle = backDocument.getRootElement();
			List backlist = backRootEle.elements();
			for(int i = 0; i < backlist.size(); i++){
				Element ele = (Element)backlist.get(i);
				String eleName = ele.getName();
				if(!eleName.equals("list")){
					xmlBackMap.put(eleName, ele.getText().trim());
				}else{
					continue;
				}
			}
			String backHmacStr="";
			String[] backDigestValues = "cmd,mer_Id,batch_No,order_Id,status,message,hmacKey".split(",");
			for(int i = 0; i < backDigestValues.length;i++){
				if(backDigestValues[i].equals("hmacKey")){
					backHmacStr = backHmacStr + hmacKey;
					continue;
				}
				String tempStr = (String)xmlBackMap.get(backDigestValues[i]);
				backHmacStr = backHmacStr + ((tempStr == null) ? "" : tempStr);
			}
			String newmd5hmac = com.yeepay.common.utils.Digest.hmacSign(backHmacStr);
			System.out.println("提交返回源数据为---||" + backHmacStr+"||");
			System.out.println("经过md5签名后的验证返回hmac为---||" + newmd5hmac+"||");
			System.out.println("提交返回的hmac为---||" + backmd5hmac+"||");
			if(newmd5hmac.equals(backmd5hmac)){
				System.out.println("md5验签成功");
				//判断该证书DN是否为易宝
				if(signUtil.getSigerCert()[0].getSubject().toUpperCase().indexOf("OU=YEEPAY,") > 0){
					System.out.println("证书DN是易宝的");
					//回写易宝
					StringBuffer str = new StringBuffer();
					str.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
					str.append("<data>");
					str.append("<cmd>TransferNotify</cmd>");
					str.append("<version>1.1<ersion>");
					str.append("<mer_Id>" + xmlBackMap.get("mer_Id") + "</mer_Id>");
					str.append("<batch_No>" + xmlBackMap.get("batch_No") + "</batch_No>");
					str.append("<order_Id>" + xmlBackMap.get("order_Id") + "</order_Id>");
					str.append("<ret_Code>S</ret_Code>");//这里根据情况传 S 或 F 如果是 S 则不会重发   如果是 F 会重发
					cmdValue = "TransferNotify" + xmlBackMap.get("mer_Id") + xmlBackMap.get("batch_No") + xmlBackMap.get("order_Id") + "S" + hmacKey;
					String hmac = com.yeepay.common.utils.Digest.hmacSign(cmdValue);
					str.append("<hmac>" + new String(signUtil.p7SignMessage(true, hmac.getBytes(),ALGORITHM, jkey, cs, tempsession)) + "</hmac>");
					str.append("</data>");
				 	try {
						System.out.println("回写易宝数据："+str);
						response.getOutputStream().write(str.toString().getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}  
				}else{
					System.out.println("证书DN不是易宝的");
				}
			}else{
				System.out.println("md5验签失败");
			}
		}else{
			System.out.println("证书验签失败....");
		}
	}
	
	
%>

