package com.egc.crowd.util;

import com.egc.crowd.constant.CrowdConstant;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @PROJECT_NAME: crowdfunding
 * @DESCRIPTION:
 * @USER: eugenechow
 * @DATE: 2020/12/25 下午5:18
 */
public class CrowdUtil {

    /**
     * 判断当前请求是否为Ajax请求
     *
     * @param request 请求对象
     * @return true:是Ajax请求
     * false:不是Ajax请求
     */
    public static boolean judgeRequestType(HttpServletRequest request) {

        // 1.获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Request-With");

        // 2.判断
        return
                (acceptHeader != null && acceptHeader.contains("application/json"))

                        ||

                        (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"));
    }


    /**
     * 对明文字符串进行MD5加密
     *
     * @param source 传入对明文字符串
     * @return 加密结果
     */
    public static String MD5(String source) {

        // 1.判断source是否有效
        if (source == null || source.length() == 0) {

            // 2.如果不是有效字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {

            // 3.获取MessageDigest对象
            String algorithm = "md5";

            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 4.获取字符串对应的字节组
            byte[] input = source.getBytes();

            // 5.执行加密
            byte[] output = messageDigest.digest(input);

            // 6.创建BigInteger对象
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);

            // 7.按照16进制将bigInteger的值转换为字符串
            int radix = 16;
            String encoded = bigInteger.toString(radix).toUpperCase();

            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
