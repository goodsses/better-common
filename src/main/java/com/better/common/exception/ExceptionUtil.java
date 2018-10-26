package com.better.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Auther: 梁晓宇
 * @Date: 2018/10/23 15:49
 * @Description: 异常工具类
 */
public class ExceptionUtil {

    /**
     * 获取异常的堆栈信息
     *
     * @param t 异常
     * @return 打印信息
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
