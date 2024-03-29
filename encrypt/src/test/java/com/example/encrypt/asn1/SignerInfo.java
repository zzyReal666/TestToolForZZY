package com.example.encrypt.asn1;

import lombok.Getter;
import org.bouncycastle.asn1.*;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;

import java.util.Enumeration;

/**
 * @author zhangzhongyuan@szanfu.cn
 * @description
 * @since 2023/9/11 14:35
 */
@Getter
public class SignerInfo extends ASN1Object {
    private ASN1Integer version;
    private SignerIdentifier sid;
    private AlgorithmIdentifier digAlgorithm;
    private ASN1Set authenticatedAttributes;
    private AlgorithmIdentifier digEncryptionAlgorithm;
    private ASN1OctetString encryptedDigest;
    private ASN1Set unauthenticatedAttributes;


    public SignerInfo(ASN1Sequence seq) {
        Enumeration e = seq.getObjects();
        this.version = (ASN1Integer)e.nextElement();
        this.sid = SignerIdentifier.getInstance(e.nextElement());
        this.digAlgorithm = AlgorithmIdentifier.getInstance(e.nextElement());
        Object obj = e.nextElement();
        if (obj instanceof ASN1TaggedObject) {
            this.authenticatedAttributes = ASN1Set.getInstance((ASN1TaggedObject)obj, false);
            this.digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(e.nextElement());
        } else {
            this.authenticatedAttributes = null;
            this.digEncryptionAlgorithm = AlgorithmIdentifier.getInstance(obj);
        }

        this.encryptedDigest = DEROctetString.getInstance(e.nextElement());
        if (e.hasMoreElements()) {
            this.unauthenticatedAttributes = ASN1Set.getInstance((ASN1TaggedObject)e.nextElement(), false);
        } else {
            this.unauthenticatedAttributes = null;
        }

    }
    public static SignerInfo getInstance(Object o) throws IllegalArgumentException {
        if (o instanceof SignerInfo) {
            return (SignerInfo)o;
        } else {
            return o != null ? new SignerInfo(ASN1Sequence.getInstance(o)) : null;
        }
    }

    public SignerInfo(SignerIdentifier sid, AlgorithmIdentifier digAlgorithm, ASN1Set authenticatedAttributes, AlgorithmIdentifier digEncryptionAlgorithm, ASN1OctetString encryptedDigest, ASN1Set unauthenticatedAttributes) {
        if (sid.isTagged()) {
            this.version = new ASN1Integer(3L);
        } else {
            this.version = new ASN1Integer(1L);
        }

        this.sid = sid;
        this.digAlgorithm = digAlgorithm;
        this.authenticatedAttributes = authenticatedAttributes;
        this.digEncryptionAlgorithm = digEncryptionAlgorithm;
        this.encryptedDigest = encryptedDigest;
        this.unauthenticatedAttributes = unauthenticatedAttributes;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(this.version);
        v.add(this.sid);
        v.add(this.digAlgorithm);
        if (this.authenticatedAttributes != null) {
            v.add(new DERTaggedObject(false, 0, this.authenticatedAttributes));
        }

        v.add(this.digEncryptionAlgorithm);
        v.add(this.encryptedDigest);
        if (this.unauthenticatedAttributes != null) {
            v.add(new DERTaggedObject(false, 1, this.unauthenticatedAttributes));
        }

        return new DERSequence(v);
    }
}
