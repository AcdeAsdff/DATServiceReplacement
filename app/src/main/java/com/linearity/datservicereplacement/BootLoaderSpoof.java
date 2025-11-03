package com.linearity.datservicereplacement;

import static com.linearity.utils.LoggerUtils.LoggerLog;
import static com.linearity.utils.PublicSeed.publicSeed;

import com.linearity.utils.ExtendedRandom;
import com.linearity.utils.NotFinished;

import android.app.AndroidAppHelper;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.StringReader;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyPairGeneratorSpi;
import java.security.KeyStore;
import java.security.KeyStoreSpi;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
@NotFinished//now i disabled keystore via selinux
public class BootLoaderSpoof {

//    private static final List<Certificate> certs_EC = new LinkedList<>();
//    private static final List<Certificate> certs_RSA = new LinkedList<>();
//    private static KeyPair parseKeyPair(String key) throws Throwable {
//        Object object;
//        try (PEMParser parser = new PEMParser(new StringReader(key))) {
//            object = parser.readObject();
//        }
//
//        PEMKeyPair pemKeyPair = (PEMKeyPair) object;
//
//        return new JcaPEMKeyConverter().getKeyPair(pemKeyPair);
//    }
//
//    private static Certificate parseCert(String cert) throws Throwable {
//        PemObject pemObject;
//        try (PemReader reader = new PemReader(new StringReader(cert))) {
//            pemObject = reader.readPemObject();
//        }
//
//        X509CertificateHolder holder = new X509CertificateHolder(pemObject.getContent());
//
//        return new JcaX509CertificateConverter().getCertificate(holder);
//    }
//    private static KeyPair keyPair_EC = null;
//    private static KeyPair keyPair_RSA = null;
//    static {
//        try {
//            SecureRandom rng = SecureRandom.getInstance("SHA1PRNG");
//            rng.setSeed(new ExtendedRandom(publicSeed * "EC".hashCode()).nextBytes(128));
//
//            // --- EC key pair ---
//            KeyPairGenerator ecGen = KeyPairGenerator.getInstance("EC");
//            ecGen.initialize(256, rng);
//
//            KeyPair ecPair = ecGen.generateKeyPair();
//            keyPair_EC = parseKeyPair(toPEM("EC PRIVATE KEY", ecPair.getPrivate().getEncoded()));
//
//            KeyPairGenerator rsaGen = KeyPairGenerator.getInstance("RSA");
//            rsaGen.initialize(2048, rng);
//            KeyPair rsaPair = rsaGen.generateKeyPair();
//            keyPair_RSA = parseKeyPair(toPEM("PRIVATE KEY", rsaPair.getPrivate().getEncoded()));
//
//        }catch (Throwable e){
//            LoggerLog(e);
//        }
//    }
//
//    static String toPEM(String type, byte[] encoded) {
//        String base64 = Base64.getMimeEncoder(64, new byte[]{'\n'}).encodeToString(encoded);
//        return "-----BEGIN " + type + "-----\n" + base64 + "\n-----END " + type + "-----\n";
//    }
//    private static Extension createHackedExtensions(byte[] attestationChallengeBytes) {
//        try {
//            SecureRandom random = new SecureRandom();
//
//            byte[] bytes1 = new byte[32];
//            byte[] bytes2 = new byte[32];
//
//            random.nextBytes(bytes1);
//            random.nextBytes(bytes2);
//
//            ASN1Encodable[] rootOfTrustEncodables = {new DEROctetString(bytes1), ASN1Boolean.TRUE, new ASN1Enumerated(0), new DEROctetString(bytes2)};
//
//            ASN1Sequence rootOfTrustSeq = new DERSequence(rootOfTrustEncodables);
//
//            ASN1Integer[] purposesArray = {new ASN1Integer(0), new ASN1Integer(1), new ASN1Integer(2), new ASN1Integer(3), new ASN1Integer(4), new ASN1Integer(5)};
//
//            ASN1Encodable[] digests = {new ASN1Integer(1), new ASN1Integer(2), new ASN1Integer(3), new ASN1Integer(4), new ASN1Integer(5), new ASN1Integer(6)};
//
//            var Apurpose = new DERSet(purposesArray);
//            var Aalgorithm = new ASN1Integer(3);
//            var AkeySize = new ASN1Integer(256);
//            var Adigest = new DERSet(digests);
//            var AecCurve = new ASN1Integer(1);
//            var AnoAuthRequired = DERNull.INSTANCE;
//            var AosVersion = new ASN1Integer(130000);
//            var AosPatchLevel = new ASN1Integer(202401);
//            var AcreationDateTime = new ASN1Integer(System.currentTimeMillis());
//            var Aorigin = new ASN1Integer(0);
//
//            var purpose = new DERTaggedObject(true, 1, Apurpose);
//            var algorithm = new DERTaggedObject(true, 2, Aalgorithm);
//            var keySize = new DERTaggedObject(true, 3, AkeySize);
//            var digest = new DERTaggedObject(true, 5, Adigest);
//            var ecCurve = new DERTaggedObject(true, 10, AecCurve);
//            var noAuthRequired = new DERTaggedObject(true, 503, AnoAuthRequired);
//            var creationDateTime = new DERTaggedObject(true, 701, AcreationDateTime);
//            var origin = new DERTaggedObject(true, 702, Aorigin);
//            var rootOfTrust = new DERTaggedObject(true, 704, rootOfTrustSeq);
//            var osVersion = new DERTaggedObject(true, 705, AosVersion);
//            var osPatchLevel = new DERTaggedObject(true, 706, AosPatchLevel);
//
//            ASN1Encodable[] teeEnforcedEncodables = {purpose, algorithm, keySize, digest, ecCurve, noAuthRequired, creationDateTime, origin, rootOfTrust, osVersion, osPatchLevel};
//
//            ASN1Integer attestationVersion = new ASN1Integer(4);
//            ASN1Enumerated attestationSecurityLevel = new ASN1Enumerated(1);
//            ASN1Integer keymasterVersion = new ASN1Integer(41);
//            ASN1Enumerated keymasterSecurityLevel = new ASN1Enumerated(1);
//            ASN1OctetString attestationChallenge = new DEROctetString(attestationChallengeBytes);
//            ASN1OctetString uniqueId = new DEROctetString("".getBytes());
//            ASN1Sequence softwareEnforced = new DERSequence();
//            ASN1Sequence teeEnforced = new DERSequence(teeEnforcedEncodables);
//
//            ASN1Encodable[] keyDescriptionEncodables = {attestationVersion, attestationSecurityLevel, keymasterVersion, keymasterSecurityLevel, attestationChallenge, uniqueId, softwareEnforced, teeEnforced};
//
//            ASN1Sequence keyDescriptionHackSeq = new DERSequence(keyDescriptionEncodables);
//
//            ASN1OctetString keyDescriptionOctetStr = new DEROctetString(keyDescriptionHackSeq);
//
//            return new Extension(new ASN1ObjectIdentifier("1.3.6.1.4.1.11129.2.1.17"), false, keyDescriptionOctetStr);
//
//        } catch (Throwable t) {
//            LoggerLog(t);
//        }
//        return null;
//    }
//    private static Certificate createLeafCert() {
//        try {
//            long now = System.currentTimeMillis();
//            Date notBefore = new Date(now);
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(notBefore);
//            calendar.add(Calendar.HOUR, 1);
//
//            Date notAfter = calendar.getTime();
//
//            X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(new X500Name("CN=chiteroman"), BigInteger.ONE, notBefore, notAfter, new X500Name("CN=Android Keystore Key"), keyPair_EC.getPublic());
//
//            KeyUsage keyUsage = new KeyUsage(KeyUsage.keyCertSign);
//            certBuilder.addExtension(Extension.keyUsage, true, keyUsage);
//
//            certBuilder.addExtension(createHackedExtensions());
//
//            ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withECDSA").build(keyPair_EC.getPrivate());
//
//            X509CertificateHolder certHolder = certBuilder.build(contentSigner);
//
//            return new JcaX509CertificateConverter().getCertificate(certHolder);
//
//        } catch (Throwable t) {
//            LoggerLog(t);
//        }
//        return null;
//    }
//
//    private static Certificate hackLeafExistingCert(Certificate certificate) {
//        try {
//            X509CertificateHolder certificateHolder = new X509CertificateHolder(certificate.getEncoded());
//
//            KeyPair keyPair;
//            if (KeyProperties.KEY_ALGORITHM_EC.equals(certificate.getPublicKey().getAlgorithm())) {
//                keyPair = keyPair_EC;
//            } else {
//                keyPair = keyPair_RSA;
//            }
//
//            long now = System.currentTimeMillis();
//            Date notBefore = new Date(now);
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(notBefore);
//            calendar.add(Calendar.HOUR, 1);
//
//            Date notAfter = calendar.getTime();
//
//            X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(certificateHolder.getIssuer(), certificateHolder.getSerialNumber(), notBefore, notAfter, certificateHolder.getSubject(), keyPair.getPublic());
//
//            for (Object extensionOID : certificateHolder.getExtensionOIDs()) {
//
//                ASN1ObjectIdentifier identifier = (ASN1ObjectIdentifier) extensionOID;
//
//                if ("1.3.6.1.4.1.11129.2.1.17".equals(identifier.getId())) continue;
//
//                certBuilder.addExtension(certificateHolder.getExtension(identifier));
//            }
//
//            Extension extension = certificateHolder.getExtension(new ASN1ObjectIdentifier("1.3.6.1.4.1.11129.2.1.17"));
//
//            certBuilder.addExtension(addHackedExtension(extension));
//
//            ContentSigner contentSigner;
//            if (KeyProperties.KEY_ALGORITHM_EC.equals(certificate.getPublicKey().getAlgorithm())) {
//                contentSigner = new JcaContentSignerBuilder("SHA256withECDSA").build(keyPair.getPrivate());
//            } else {
//                contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());
//            }
//
//            X509CertificateHolder certHolder = certBuilder.build(contentSigner);
//
//            return new JcaX509CertificateConverter().getCertificate(certHolder);
//
//        } catch (Throwable t) {
//            LoggerLog(t);
//        }
//        return certificate;
//    }

}
