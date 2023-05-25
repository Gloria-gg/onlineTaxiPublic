package com.mashibing.internalcommon.util;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:11 PM 5/25/23
 */
public class SsePrefixUtils {

    public static  final String sperator = "$";

    public  static String generatorSseKey(Long userId , String identity){
        return userId+sperator+identity;
    }
}
