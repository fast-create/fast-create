package com.alibaba.fastcreate.utils;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.commons.codec.binary.Base64;

public class MarshallerUtil {

    private static final String UTF8 = "UTF-8";

    /**
     * 利用JAXB将XML解析成对象
     */
    public static String buildJaxb(Object object) {
        String result = null;
        try {
            Class<?> clazz = object.getClass();
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, UTF8);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            marshaller.marshal(object, os);
            result = new String(os.toByteArray(), UTF8);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 利用JAXB将XML解析成对象
     */
    public static Object buildJaxb(Class<?> clazz, String xml) {
        Object object = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            object = unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static String decodeBase64StringWithUTF8(String sourceStr) {
        String decodeStr = null;
        byte[] decodeBytes = Base64.decodeBase64(sourceStr);
        try {
            decodeStr = new String(decodeBytes, UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeStr;
    }

    public static String encodeBase64StringWithUTF8(String sourceStr) {
        String encodeStr = null;
        try {
            byte[] encodeBytes = Base64.encodeBase64(sourceStr.getBytes(UTF8));
            encodeStr = new String(encodeBytes, UTF8);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        return encodeStr;
    }

}
