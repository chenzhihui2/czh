package cat.encryption;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Enumeration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

/**
 * Created by Administrator on 2017/1/17.
 * 证书工具
 */
@Slf4j
public class CertUtils {
        private X509Certificate x509Certificate;
        private PrivateKey privateKey;

        public static void main(String args[]) throws Exception {
//            InputStream fis = new FileInputStream("D:\\workspace\\czh\\cat-test\\src\\main\\resources\\cert.cer");
//            byte test[]=new byte[1024*10];
//            fis.read(test);
//            System.out.println(new String(test,0,test.length));
            //公钥
//            String cerStr="MIIEOTCCAyGgAwIBAgIFEHWTAIQwDQYJKoZIhvcNAQEFBQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAoTCUNGQ0EgT0NBMTAeFw0xNzAxMTYwODEzMTlaFw0yMjAxMTYwODEzMTlaMIGlMQswCQYDVQQGEwJjbjESMBAGA1UEChMJQ0ZDQSBPQ0ExMRYwFAYDVQQLEw1DaGluYUNsZWFyaW5nMRQwEgYDVQQLEwtFbnRlcnByaXNlczFUMFIGA1UEAwxLMDQxQE45MTMxMDAwME1BMUszNTVENVRA5LiK5rW35Luf6LSi6YeR6J6N5L+h5oGv5pyN5Yqh5pyJ6ZmQ5YWs5Y+4QDAwMDAwMDAxMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0XkTsWHVTrGDwZnsVtdZtr5mqFZOA/M6DrSXqzp46Zq02aZs1GeW3rGnl8qJiRdxl9+RGY0Rc4P4Avu3BG0W5WtftV/4p+ghUDCzvDBCY8xOMzHMOfkzE+NzamWjy0O5GN0E/8+p6I+2Ldz3vLvCqlJ3OYo2mpK1Uo0KLJ67g9eIY3E4vSV5W33lmUrav7vVY+PlnqNx1EyEpv66D8itYQTKf37A1XTOuVbPWvbYIh4F43Cf5SPwRp3xtbjb/2fEd2BpQbejpxJWuAAy+js+htSnY6tm8GNc4RbQQy3oHhdu9TAgFugEKOP2wqlik61qwyT0u5zTdnm/htyfpJvHGwIDAQABo4HyMIHvMB8GA1UdIwQYMBaAFNHb6YiC5d0aj0yqAIy+fPKrG/bZMEgGA1UdIARBMD8wPQYIYIEchu8qAQEwMTAvBggrBgEFBQcCARYjaHR0cDovL3d3dy5jZmNhLmNvbS5jbi91cy91cy0xNC5odG0wNwYDVR0fBDAwLjAsoCqgKIYmaHR0cDovL2NybC5jZmNhLmNvbS5jbi9SU0EvY3JsNTgxOS5jcmwwCwYDVR0PBAQDAgPoMB0GA1UdDgQWBBTQ9tUGZqpbhxwhf49Ft/tlh/xvazAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDQYJKoZIhvcNAQEFBQADggEBAFQaWDjNW7MVI/BCQctdnLq/AcRa6OCrWjsFkfJpHczIe/RzdANgjXPic2fKlAogtgcSzKF+ZkdUmvbnE+OH8M1Y1TKJIyHNVqDn89eDZvLXzkwU5LVGedQwWnrhDe23DIiCZQsquwscs89HHujmLOVKzbKlD1SCgrwGfDE6m/HZozVbQNf76iYJaMsOBqRaxQLg/9dmoFpXZZR/9NGSdSo+Cpf0SeA0NfA7f49SV/UMfeMbibb+aJvS2rw0rcVa/YIx7OfYdbBdmaa/0JE0rLtTlq+0ASJwfAxGK8PyxKf2lFdtHRYe8iT0qg7xuq1SItzlPAEuj4DpvhDydfd5JlM=";
//            CertUtils certUtils=new CertUtils();
//            certUtils.loadX509Certificate(cerStr);

            //私钥
              CertUtils certUtils=new CertUtils();
              certUtils.loadPKCS12("D:\\workspace\\czh\\cat-test\\src\\main\\resources\\personal.pfx","");
        }

    public PrivateKey loadPKCS12(String path,String strPassword){
        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(path);
            // If the keystore password is empty(""), then we have to set
            // to null, otherwise it won't work!!!
            char[] nPassword = null;
            if ((strPassword == null) || strPassword.trim().equals("")){
                nPassword = null;
            }
            else
            {
                nPassword = strPassword.toCharArray();
            }
            ks.load(fis, nPassword);
            fis.close();
            System.out.println("keystore type=" + ks.getType());
            // Now we loop all the aliases, we need the alias to get keys.
            // It seems that this value is the "Friendly name" field in the
            // detals tab <-- Certificate window <-- view <-- Certificate
            // Button <-- Content tab <-- Internet Options <-- Tools menu
            // In MS IE 6.
            Enumeration enumas = ks.aliases();
            String keyAlias = null;
            if (enumas.hasMoreElements())// we are readin just one certificate.
            {
                keyAlias = (String)enumas.nextElement();
                System.out.println("alias=[" + keyAlias + "]");
            }
            // Now once we know the alias, we could get the keys.
            System.out.println("is key entry=" + ks.isKeyEntry(keyAlias));
            PrivateKey prikey = (PrivateKey) ks.getKey(keyAlias, nPassword);
            Certificate cert = ks.getCertificate(keyAlias);
            PublicKey pubkey = cert.getPublicKey();
            System.out.println("cert class = " + cert.getClass().getName());
            System.out.println("cert = " + cert);
            System.out.println("public key = " + pubkey);
            System.out.println("private key = " + prikey);
            return prikey;
        }catch (Exception e){
            log.error("实例化私钥错误",e);
            return null;
        }
    }

    public X509Certificate loadX509Certificate(String cert) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        final java.io.ByteArrayInputStream streamCertificate = new java.io.ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(cert));
        X509Certificate x509Cert = (java.security.cert.X509Certificate) cf.generateCertificate(streamCertificate);
        return x509Cert;
    }

}