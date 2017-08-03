<%@ page language="java" contentType="text/html; charset=gbk"%>
<%@ page import="java.util.*,java.io.*,com.yeepay.common.utils.*,org.dom4j.*,com.cfca.util.pki.*,com.cfca.util.pki.api.*,com.cfca.util.pki.cert.*,com.cfca.util.pki.cipher.*,com.cfca.util.pki.extension.*"%>
<%
	request.setCharacterEncoding("gbk");
	//商户密钥
	String hmacKey="su1KU96573FKlt580404tU6XJDcA004oD2u75cgA33Q2W7I1542xR38XaI3t";
	
	Map result = new LinkedHashMap();
	Map xmlMap = new LinkedHashMap();
	Map xmlBackMap = new LinkedHashMap();
	
	String[] digestValues = request.getParameter("digest").split(",");
	String[] backDigestValues = request.getParameter("backDigest").split(",");
	String xml = request.getParameter("xml");
	
	//第一步:将请求的数据和商户自己的密钥拼成一个字符串,
	Document document = null;
	try {
		document=DocumentHelper.parseText(xml);
	} catch (DocumentException e) {
	} 
	Element rootEle = document.getRootElement();
	String cmdValue = rootEle.elementText("cmd");
	List list = rootEle.elements();
	for(int i=0;i<list.size();i++){
		Element ele = (Element)list.get(i);
		String eleName = ele.getName();
		if(!eleName.equals("list")){
			xmlMap.put(eleName,ele.getText().trim());
		}else{
			continue;
		}
	}
	
	String hmacStr="";
	for(int i=0;i<digestValues.length;i++){
		if(digestValues[i].equals("hmacKey")){
			hmacStr = hmacStr+hmacKey;
			continue;
		}
		hmacStr = hmacStr + xmlMap.get(digestValues[i]);
		
	}
	System.out.println("签名之前的源数据为---||" + hmacStr+"||");
	
	//下面用数字证书进行签名
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
	System.out.println("------"+sysPath+"------"+File.separator+"------");
	JKey jkey = KeyUtil.getPriKey(sysPath+File.separator + "10000450379.pfx", "123456");
	X509Cert cert = CertUtil.getCert(sysPath+File.separator + "10000450379.pfx", "123456");
	System.out.println(cert.getSubject());
	X509Cert[] cs=new X509Cert[1];
	cs[0]=cert;
	String signMessage ="";
	SignatureUtil signUtil =null;
	try{
		// 第二步:对请求的串进行MD5对数据进行签名
		String yphs = com.yeepay.common.utils.Digest.hmacSign(hmacStr);
		signUtil = new SignatureUtil();
		byte[] b64SignData;
		// 第三步:对MD5签名之后数据调用CFCA提供的api方法用商户自己的数字证书进行签名
		b64SignData = signUtil.p7SignMessage(true, yphs.getBytes(),ALGORITHM, jkey, cs, tempsession);
		 if(jcrypto!=null){
	         jcrypto.finalize (com.cfca.util.pki.cipher.JCrypto.JSOFT_LIB,null);
	     }
		signMessage = new String(b64SignData, "UTF-8");
	}catch(Exception e){
	}
	System.out.println("经过md5和数字证书签名之后的数据为---||"+signMessage+"||");	
		
	Element r=rootEle.element("hmac");
	r.setText(signMessage);
	result.put("xml",xml);
	document.setXMLEncoding("GBK");
	System.out.println("完整xml请求报文:"+document.asXML());
	
	
	String textHost=request.getParameter("textHost");
	System.out.println("请求地址为:"+textHost);

	//第四步:发送https请求
	String responseMsg = CallbackUtils.httpRequest(textHost, document.asXML(), "POST", "gbk","text/xml ;charset=gbk", false);
	
	out.println(
	"<html><body><textarea rows=\"23\" cols=\"120\" name=\"xml\" id=\"xml\">" + 
	responseMsg +
	"</textarea></body></html>");
	System.out.println("服务器响应xml报文:" + responseMsg);
	
	try {
		document = DocumentHelper.parseText(responseMsg);
	} catch (DocumentException e) {
	} 
	rootEle = document.getRootElement();
	cmdValue = rootEle.elementText("hmac");
	
	//第五步:对服务器响应报文进行验证签名
	boolean sigerCertFlag = false;
	if(cmdValue!=null){
		sigerCertFlag = signUtil.p7VerifySignMessage(cmdValue.getBytes(), tempsession);
		String backmd5hmac = xmlBackMap.get("hmac") + "";
		if(sigerCertFlag){
			System.out.println("证书验签成功");
			backmd5hmac = new String(signUtil.getSignedContent());
			System.out.println("证书验签获得的MD5签名数据为----" + backmd5hmac);
			System.out.println("证书验签获得的证书dn为----" + new String(signUtil.getSigerCert()[0].getSubject()));
			//第六步.将验签出来的结果数据与自己针对响应数据做MD5签名之后的数据进行比较是否相等
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
				//第七步:判断该证书DN是否为易宝
				if(signUtil.getSigerCert()[0].getSubject().toUpperCase().indexOf("OU=YEEPAY,") > 0){
					System.out.println("证书DN是易宝的");
					//第八步:.......加上业务逻辑
				}else{
					System.out.println("证书DN不是易宝的");
				}
				
				//
			}else{
				System.out.println("md5验签失败");
			}
		}else{
			System.out.println("证书验签失败....");
		}
	}
	
	
%>

