package com.shuaiwu.wsbook.utils;

/**
 * 一些通用方法
 *
 * @author shuaiwu
 * @date 2023-12-07 10:13
 */
public class CommonUtil {

    public static String getCodeByName(String name){
        if (name.contains("xuanhuan")){
            return "100001";
        } else if (name.contains("xiuzhen")) {
            return "100002";
        } else if (name.contains("dushi")) {
            return "100003";
        } else if (name.contains("lishi")) {
            return "100004";
        } else if (name.contains("wangyou")) {
            return "100005";
        } else if (name.contains("kehuan")) {
            return "100006";
        } else {
            return "100007";
        }
    }

}
