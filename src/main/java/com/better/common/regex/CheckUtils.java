package com.better.common.regex;

import com.better.common.excel.PoiUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Auther: 梁晓宇
 * @Date: 2018/10/23 14:53
 * @Description: 验证手机号工具类
 */
public class CheckUtils  {

    //日志
    private static Logger logger = LoggerFactory.getLogger(PoiUtils.class);

    /**
     * 验证是否是手机号
     * @param mobile 手机号
     * @return 布尔
     */
    public static boolean isMobile(String mobile) {
        if(StringUtils.isBlank(mobile)){
            return false;
        }
        String regexp = "(1(3|5|8)\\d|147|170|176|177|178)((\\*{4}|\\d{4}))\\d{4}$";
        Pattern pattern = Pattern.compile(regexp);
        return pattern.matcher(mobile).matches();
    }

    /**
     * 验证手机号是否有隐号
     * @param mobile 手机号
     * @return 布尔
     */
    public static boolean isMobileIsHide(String mobile){
        Pattern pattern = Pattern.compile("^\\d+$");
        return pattern.matcher(mobile).matches();
    }

    /**
     * 验证Email
     * @param email email地址，格式：zhangsan@zuidaima.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkEmail(String email) {
        String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
        return Pattern.matches(regex, email);
    }

    /**
     * 验证身份证号码
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex,idCard);
    }

    public final static boolean isNull(Object[] objs){
        if(objs == null || objs.length == 0) return true;
        return false;
    }

    public final static boolean isNull(Collection collection){
        if(collection == null || collection.size() == 0) return true;
        return false;
    }

    public final static boolean isNull(Map map){
        if(map == null || map.size() == 0) return true;
        return false;
    }

    public final static boolean isNull(Integer integer){
        if(integer == null || integer == 0) return true;
        return false;
    }

    public final static boolean isNull(String str){
        return str == null || "".equals(str.trim()) || "null".equals(str.toLowerCase());
    }

    public final static boolean isNull(Long longs){
        if(longs == null || longs == 0) return true;
        return false;
    }
}
