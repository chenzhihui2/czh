<%@ page language="java" contentType="text/html; charset=gbk"%>
<%@ page import="java.util.*,java.io.*,com.yeepay.common.utils.*,org.dom4j.*,com.cfca.util.pki.*,com.cfca.util.pki.api.*,com.cfca.util.pki.cert.*,com.cfca.util.pki.cipher.*,com.cfca.util.pki.extension.*"%>
<%
	request.setCharacterEncoding("gbk");
	//�̻���Կ
	String hmacKey="su1KU96573FKlt580404tU6XJDcA004oD2u75cgA33Q2W7I1542xR38XaI3t";
	
	Map result = new LinkedHashMap();
	Map xmlMap = new LinkedHashMap();
	Map xmlBackMap = new LinkedHashMap();
	
	String[] digestValues = request.getParameter("digest").split(",");
	String[] backDigestValues = request.getParameter("backDigest").split(",");
	String xml = request.getParameter("xml");
	
	//��һ��:����������ݺ��̻��Լ�����Կƴ��һ���ַ���,
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
	System.out.println("ǩ��֮ǰ��Դ����Ϊ---||" + hmacStr+"||");
	
	//����������֤�����ǩ��
	com.cfca.util.pki.cipher.Session tempsession = null;
	String ALGORITHM = SignatureUtil.SHA1_RSA;
	JCrypto jcrypto =null;
	if(tempsession==null){
		try {
	        //��ʼ�����ܿ⣬��ûỰsession
	        //���̵߳�Ӧ�ÿ��Թ���һ��session,����Ҫ�ظ�,ֻ���ʼ��һ��
	        //��ʼ�����ܿⲢ���session��
	        //ϵͳ�˳���Ҫjcrypto.finalize()���ͷż��ܿ�
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
		// �ڶ���:������Ĵ�����MD5�����ݽ���ǩ��
		String yphs = com.yeepay.common.utils.Digest.hmacSign(hmacStr);
		signUtil = new SignatureUtil();
		byte[] b64SignData;
		// ������:��MD5ǩ��֮�����ݵ���CFCA�ṩ��api�������̻��Լ�������֤�����ǩ��
		b64SignData = signUtil.p7SignMessage(true, yphs.getBytes(),ALGORITHM, jkey, cs, tempsession);
		 if(jcrypto!=null){
	         jcrypto.finalize (com.cfca.util.pki.cipher.JCrypto.JSOFT_LIB,null);
	     }
		signMessage = new String(b64SignData, "UTF-8");
	}catch(Exception e){
	}
	System.out.println("����md5������֤��ǩ��֮�������Ϊ---||"+signMessage+"||");	
		
	Element r=rootEle.element("hmac");
	r.setText(signMessage);
	result.put("xml",xml);
	document.setXMLEncoding("GBK");
	System.out.println("����xml������:"+document.asXML());
	
	
	String textHost=request.getParameter("textHost");
	System.out.println("�����ַΪ:"+textHost);

	//���Ĳ�:����https����
	String responseMsg = CallbackUtils.httpRequest(textHost, document.asXML(), "POST", "gbk","text/xml ;charset=gbk", false);
	
	out.println(
	"<html><body><textarea rows=\"23\" cols=\"120\" name=\"xml\" id=\"xml\">" + 
	responseMsg +
	"</textarea></body></html>");
	System.out.println("��������Ӧxml����:" + responseMsg);
	
	try {
		document = DocumentHelper.parseText(responseMsg);
	} catch (DocumentException e) {
	} 
	rootEle = document.getRootElement();
	cmdValue = rootEle.elementText("hmac");
	
	//���岽:�Է�������Ӧ���Ľ�����֤ǩ��
	boolean sigerCertFlag = false;
	if(cmdValue!=null){
		sigerCertFlag = signUtil.p7VerifySignMessage(cmdValue.getBytes(), tempsession);
		String backmd5hmac = xmlBackMap.get("hmac") + "";
		if(sigerCertFlag){
			System.out.println("֤����ǩ�ɹ�");
			backmd5hmac = new String(signUtil.getSignedContent());
			System.out.println("֤����ǩ��õ�MD5ǩ������Ϊ----" + backmd5hmac);
			System.out.println("֤����ǩ��õ�֤��dnΪ----" + new String(signUtil.getSigerCert()[0].getSubject()));
			//������.����ǩ�����Ľ���������Լ������Ӧ������MD5ǩ��֮������ݽ��бȽ��Ƿ����
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
			System.out.println("�ύ����Դ����Ϊ---||" + backHmacStr+"||");
			System.out.println("����md5ǩ�������֤����hmacΪ---||" + newmd5hmac+"||");
			System.out.println("�ύ���ص�hmacΪ---||" + backmd5hmac+"||");
			if(newmd5hmac.equals(backmd5hmac)){
				System.out.println("md5��ǩ�ɹ�");
				//���߲�:�жϸ�֤��DN�Ƿ�Ϊ�ױ�
				if(signUtil.getSigerCert()[0].getSubject().toUpperCase().indexOf("OU=YEEPAY,") > 0){
					System.out.println("֤��DN���ױ���");
					//�ڰ˲�:.......����ҵ���߼�
				}else{
					System.out.println("֤��DN�����ױ���");
				}
				
				//
			}else{
				System.out.println("md5��ǩʧ��");
			}
		}else{
			System.out.println("֤����ǩʧ��....");
		}
	}
	
	
%>

