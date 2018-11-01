package com.better.common.main;

import com.better.anno.bean.Excel;
import com.better.common.file.PdfUtils;
import com.better.common.file.PoiUtils;
import com.better.common.file.entity.AwardMailAddress;
import com.better.common.file.entity.testExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: 梁晓宇
 * @Date: 2018/10/25 17:45
 * @Description:
 */
@RestController
@RequestMapping("/xy/web")
@Api(tags = "xy（测试类）")
public class Main {


    @GetMapping("/pdf")
    @ApiOperation(value = "pdf", notes = "xy")
    public void writePdf(){
        PdfUtils.exportPdfFile();
    }

    @GetMapping("/down")
    @ApiOperation(value = "down", notes = "xy")
    public ResponseEntity<byte[]> down() throws Exception {
        List<List<?>> tList = new ArrayList<>();
        List<AwardMailAddress> list = new ArrayList<AwardMailAddress>();
        AwardMailAddress awa1 = new AwardMailAddress();
        awa1.setAddressName("aaa");
        list.add(awa1);
        AwardMailAddress awa2 = new AwardMailAddress();
        awa2.setAddressName("bbb");
        list.add(awa2);
        AwardMailAddress awa3 = new AwardMailAddress();
        awa3.setAddressName("ccc");
        list.add(awa3);

        List<testExcel> list1 = new ArrayList<testExcel>();
        testExcel test1 = new testExcel();
        test1.setId("1");
        test1.setName("aaa");
        list1.add(test1);
        testExcel test2 = new testExcel();
        test2.setId("2");
        test2.setName("aaa");
        list1.add(test2);
        testExcel test3 = new testExcel();
        test3.setId("3");
        test3.setName("aaa");
        list1.add(test3);

        tList.add(list);
        tList.add(list1);
        return PoiUtils.downToExcelMore(tList,"多sheet测试文件.xls", "ISO8859-1");
    }

    @GetMapping("/dan")
    @ApiOperation(value = "dan", notes = "xy")
    public ResponseEntity<byte[]> dan() throws UnsupportedEncodingException {
        //单个sheet测试数据
        List<AwardMailAddress> list = new ArrayList<AwardMailAddress>();
        AwardMailAddress awa1 = new AwardMailAddress();
        awa1.setAddressName("aaa");
        list.add(awa1);
        AwardMailAddress awa2 = new AwardMailAddress();
        awa2.setAddressName("bbb");
        list.add(awa2);
        AwardMailAddress awa3 = new AwardMailAddress();
        awa3.setAddressName("ccc");
        list.add(awa3);
        return PoiUtils.downToExcel(list, "测试文件.xls", "ISO8859-1");
    }

    @GetMapping("gsg")
    @ApiOperation(value = "gsg", notes = "xy")
    public String gsg(){
        String a="机会代码： 901239183 受理顺序： 123123";
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(a);
        System.out.println( m.replaceAll("---分隔线---").trim());
        return m.replaceAll("").trim();
    }

    public static void main(String... args) {


    }


    /**
     * 反射类注解
     * @throws Exception
     */
    public void fanshe() throws Exception{
        Class clazz = AwardMailAddress.class;
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations){
            InvocationHandler handler = Proxy.getInvocationHandler(annotation);
            Field f;
            try {
                f = handler.getClass().getDeclaredField("memberValues");
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalStateException(e);
            }
            f.setAccessible(true);
            Map<String, Object> memberValues;
            memberValues = (Map<String, Object>) f.get(handler);
            Object oldValue = memberValues.get("sheetname");
            memberValues.put("sheetName", "111111111");
            // 获取类上的注解
            Excel my = (Excel) annotation;
            String value = my.sheetName();
            System.out.println(value);
        }

        AwardMailAddress a1 = new AwardMailAddress();
        Class clz = a1.getClass();
        Annotation[] annotations1 = clz.getAnnotations();
        for (Annotation item : annotations1){
            Excel my = (Excel) item;
            System.out.println(my.sheetName()+"\t---------"+my.header());
        }
    }
}

