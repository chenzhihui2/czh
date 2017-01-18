package cat.encryption;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

/**
 * Created by Administrator on 2017/1/17.
 * 证书工具
 */
public class CertUtils {
        private X509Certificate x509Certificate;
        public static void main(String args[]) throws Exception {
//            InputStream fis = new FileInputStream("D:\\workspace\\czh\\cat-test\\src\\main\\resources\\cert.cer");
//            byte test[]=new byte[1024*10];
//            fis.read(test);
//            System.out.println(new String(test,0,test.length));
            String cerStr="MIIEOTCCAyGgAwIBAgIFEHWTAIQwDQYJKoZIhvcNAQEFBQAwITELMAkGA1UEBhMCQ04xEjAQBgNVBAoTCUNGQ0EgT0NBMTAeFw0xNzAxMTYwODEzMTlaFw0yMjAxMTYwODEzMTlaMIGlMQswCQYDVQQGEwJjbjESMBAGA1UEChMJQ0ZDQSBPQ0ExMRYwFAYDVQQLEw1DaGluYUNsZWFyaW5nMRQwEgYDVQQLEwtFbnRlcnByaXNlczFUMFIGA1UEAwxLMDQxQE45MTMxMDAwME1BMUszNTVENVRA5LiK5rW35Luf6LSi6YeR6J6N5L+h5oGv5pyN5Yqh5pyJ6ZmQ5YWs5Y+4QDAwMDAwMDAxMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0XkTsWHVTrGDwZnsVtdZtr5mqFZOA/M6DrSXqzp46Zq02aZs1GeW3rGnl8qJiRdxl9+RGY0Rc4P4Avu3BG0W5WtftV/4p+ghUDCzvDBCY8xOMzHMOfkzE+NzamWjy0O5GN0E/8+p6I+2Ldz3vLvCqlJ3OYo2mpK1Uo0KLJ67g9eIY3E4vSV5W33lmUrav7vVY+PlnqNx1EyEpv66D8itYQTKf37A1XTOuVbPWvbYIh4F43Cf5SPwRp3xtbjb/2fEd2BpQbejpxJWuAAy+js+htSnY6tm8GNc4RbQQy3oHhdu9TAgFugEKOP2wqlik61qwyT0u5zTdnm/htyfpJvHGwIDAQABo4HyMIHvMB8GA1UdIwQYMBaAFNHb6YiC5d0aj0yqAIy+fPKrG/bZMEgGA1UdIARBMD8wPQYIYIEchu8qAQEwMTAvBggrBgEFBQcCARYjaHR0cDovL3d3dy5jZmNhLmNvbS5jbi91cy91cy0xNC5odG0wNwYDVR0fBDAwLjAsoCqgKIYmaHR0cDovL2NybC5jZmNhLmNvbS5jbi9SU0EvY3JsNTgxOS5jcmwwCwYDVR0PBAQDAgPoMB0GA1UdDgQWBBTQ9tUGZqpbhxwhf49Ft/tlh/xvazAdBgNVHSUEFjAUBggrBgEFBQcDAgYIKwYBBQUHAwQwDQYJKoZIhvcNAQEFBQADggEBAFQaWDjNW7MVI/BCQctdnLq/AcRa6OCrWjsFkfJpHczIe/RzdANgjXPic2fKlAogtgcSzKF+ZkdUmvbnE+OH8M1Y1TKJIyHNVqDn89eDZvLXzkwU5LVGedQwWnrhDe23DIiCZQsquwscs89HHujmLOVKzbKlD1SCgrwGfDE6m/HZozVbQNf76iYJaMsOBqRaxQLg/9dmoFpXZZR/9NGSdSo+Cpf0SeA0NfA7f49SV/UMfeMbibb+aJvS2rw0rcVa/YIx7OfYdbBdmaa/0JE0rLtTlq+0ASJwfAxGK8PyxKf2lFdtHRYe8iT0qg7xuq1SItzlPAEuj4DpvhDydfd5JlM=";
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            final java.io.ByteArrayInputStream streamCertificate = new java.io.ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(cerStr));
            X509Certificate testCert = (java.security.cert.X509Certificate) cf.generateCertificate(streamCertificate);
            testCert.getPublicKey();
//            RSAEncrypt rsaEncrypt=new RSAEncrypt();
//            rsaEncrypt.loadPublicKey(cerStr);


//            BASE64Decoder base64Decoder= new BASE64Decoder();
//            byte[] buffer= base64Decoder.decodeBuffer(cerStr);
//            KeyFactory keyFactory= KeyFactory.getInstance("RSA");
//            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
//            PublicKey publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);


        }
    public X509Certificate loadX509Certificate(String cert) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        final java.io.ByteArrayInputStream streamCertificate = new java.io.ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(cert));
        X509Certificate x509Cert = (java.security.cert.X509Certificate) cf.generateCertificate(streamCertificate);
        return x509Cert;
    }

}
