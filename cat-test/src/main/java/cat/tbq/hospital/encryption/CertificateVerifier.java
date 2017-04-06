package cat.tbq.hospital.encryption;

import java.io.FileInputStream;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;


public class CertificateVerifier
{
    private PublicKey publicKey;
    private String algorithm = "SHA1withRSA";

    public CertificateVerifier(String paramString)throws Exception
    {
        FileInputStream localFileInputStream = new FileInputStream(paramString);
        CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
        this.publicKey = localCertificateFactory.generateCertificate(localFileInputStream).getPublicKey();
    }

    public CertificateVerifier(String paramString1, String paramString2)
            throws Exception
    {
        FileInputStream localFileInputStream = new FileInputStream(paramString1);
        CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
        this.publicKey = localCertificateFactory.generateCertificate(localFileInputStream).getPublicKey();
        this.algorithm = paramString2;
    }

    public boolean verify(String paramString1, String paramString2)
            throws Exception
    {
        byte[] arrayOfByte = StringUtil.hex2bytes(paramString2);
        Signature localSignature = Signature.getInstance(this.algorithm);
        localSignature.initVerify(this.publicKey);
        localSignature.update(paramString1.getBytes("UTF-8"));
        return localSignature.verify(arrayOfByte);
    }

    public boolean verify(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
            throws Exception
    {
        Signature localSignature = Signature.getInstance(this.algorithm);
        localSignature.initVerify(this.publicKey);
        localSignature.update(paramArrayOfByte1);
        return localSignature.verify(paramArrayOfByte2);
    }

    public String getAlgorithm()
    {
        return this.algorithm;
    }

    public void setAlgorithm(String paramString)
    {
        this.algorithm = paramString;
    }
}

