package com.better.common.file;

import com.better.anno.bean.Excel;
import com.better.anno.bean.ExcelProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

/**
 * @Auther: 梁晓宇
 * @Date: 2018/10/23 11:01
 * @Description: POI文件Excel读写工具类
 */
public class PoiUtils {

    //日志
    private static Logger logger = LoggerFactory.getLogger(PoiUtils.class);

    //2003年Excel
    private final static String xls = "xls";

    //2007
    private final static String xlsx = "xlsx";

    /**
     * 读入Excel文件，解析并返回数据
     * （下标读文件）
     * @param multipartFile 文件流
     * @return 封装集合数据
     */
    public static List<String[]> readExcel(MultipartFile multipartFile) throws IOException{
        //1.验证Excel格式
        checkExcelFile(multipartFile);
        //2.根据Excel类型获取workbook
        Workbook workbook = getWorkBook(multipartFile);
        //创建集合待会用于存储数据
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
            //遍历sheet
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++){
                //3.获取sheet
                Sheet sheet = workbook.getSheetAt(sheetNum);
                //判断sheet
                if(sheet == null){
                    continue;
                }
                //获取sheet工作表上的第一行，工作表上第一个逻辑行的编号（从0开始） 官方文档：获取工作表上的第一行。注意：以前具有内容并且之后设置为空的行可能仍会被Excel和Apache POI计为行，因此此方法的结果将包含此类行，因此返回的值可能低于预期！
                int firstRowNum = sheet.getFirstRowNum();
                //获取工作表上的最后一行注意事项与上同
                int lastRowNum = sheet.getLastRowNum();
                //遍历有的行数  firstRowNum和lastRowNum下标都是从0起，first+1不包含标题行   last<=及可
                for(int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++){
                    //获取行对象
                    Row row = sheet.getRow(rowNum);
                    //验证行
                    if(row == null){
                        continue;
                    }
                    //行的首列 从0起
                    int firstCellNum = row.getFirstCellNum();
                    //获取已定单元格的数量 实际单元格的数量
                    int lastCellNum = row.getPhysicalNumberOfCells();
                    //一会放在最外层的list里，这里用于存储数据
                    String[] cells = new String[row.getPhysicalNumberOfCells()];
                    //遍历列cell
                    for(int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++){
                        Cell cell = row.getCell(cellNum);
                        //赋值到对应位置
                        cells[cellNum] = getCellValue(cell);
                    }
                    list.add(cells);
                }
            }
            workbook.close();
        }
        return list;
    }


    /**
     *
     * 其他待完善····
     *
     *
     *
     * 生成写入Excel文件
     *
     *
     *
     */


    /**
     * 判断Excel列数据属于哪中类型
     * @param cell 列
     * @return 值
     */
    public static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当String来读，避免出现数字过长9.e的情况
        if(cell.getCellTypeEnum() == NUMERIC){
            cell.setCellType(CellType.STRING);
        }
        //判断数据的类型
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK:
                cellValue = "";
                break;
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 验证是否是正确Excel文件
     * @param multipartFile 流
     */
    public static void checkExcelFile(MultipartFile multipartFile) throws IOException{
        //判断文件是否存在
        //assert multipartFile != null; 断言方式有的机器未开启，暂不使用
        if(multipartFile == null){
            logger.error("文件不存在");
            throw new FileNotFoundException("文件不存在");
        }
        //获取文件名称，判断是否是excel文件
        String fileName = multipartFile.getOriginalFilename();
        if(!fileName.endsWith(xls) && !fileName.endsWith(xlsx)){
            logger.error(fileName + "文件不是Excel文件");
            throw new IOException(fileName + "文件不是Excel文件");
        }
    }

    /**
     * 根据流获取wb对象
     * @param multipartFile 流
     * @return wb对象
     */
    public static Workbook getWorkBook(MultipartFile multipartFile){
        //获取wb名称
        String fileName = multipartFile.getOriginalFilename();
        //wb对象
        Workbook workbook = null;
        try {
            //获取文件流
            InputStream inputStream = multipartFile.getInputStream();
            //判断文件
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(inputStream);
            }else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(inputStream);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return workbook;
    }

    /**
     * 单个sheet导出Excel
     * @param tList 数据
     * @param fileName Excel名称
     * @param charsetName 编码格式
     * @return response响应流
     */
    public static ResponseEntity<byte[]> downToExcel(List<?> tList, String fileName, String charsetName) {
        try {
            byte[] bytes = PoiUtils.beanToExcelBytes(tList);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //转换编码 防止页面乱码
            headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"), charsetName));
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 导出Excel文件（单个）
     * @param tList 数据
     * @param <T> 泛型
     * @return byte字节数组
     */
    public static <T> byte[] beanToExcelBytes(List<T> tList) {
        //创建byte[]数组用于后面存储
        byte[] bytes = new byte[0];
        //创建workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //判断用户传入有无数据
        if (tList != null && !tList.isEmpty()) {
            workbook = commonExcelCode(tList, workbook);
            //缓冲区
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                workbook.write(baos);
                bytes = baos.toByteArray();
            } catch (IOException e) {
                logger.error("", e);
            } finally {
                try {
                    baos.close();
                    workbook.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
        return bytes;
    }

    /**
     * 多个sheet导出Excel
     * @param tList 数据
     * @param fileName Excel名称
     * @param charsetName 编码格式
     * @return response响应流
     */
    public static ResponseEntity<byte[]> downToExcelMore(List<List<?>> tList, String fileName, String charsetName) {
        try {
            //调用多sheet方法
            byte[] bytes = PoiUtils.beanToExcelMoreBytes(tList);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //转换编码 防止页面乱码
            headers.setContentDispositionFormData("attachment", new String(fileName.getBytes("utf-8"), charsetName));
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 导出Excel文件（多个）
     * @param tList 数据
     * @param <T> 泛型
     * @return 返回bytes字节
     */
    public static <T> byte[] beanToExcelMoreBytes(List<List<?>> tList) {
        //创建byte[]数组用于后面存储
        byte[] bytes = new byte[0];
        //创建workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
        //判断用户传入有无数据
        if (tList != null && !tList.isEmpty()) {
            //从这就开始循环了····
            for (List list01 : tList){
                if(list01 != null && !list01.isEmpty()){
                    List<?> list02 = list01;
                    //赋值还回来
                    workbook = commonExcelCode(list02, workbook);
                }
            }
            //缓冲区
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                workbook.write(baos);
                bytes = baos.toByteArray();
            } catch (IOException e) {
                logger.error("", e);
            } finally {
                try {
                    baos.close();
                    workbook.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
        return bytes;
    }

    /**
     * 公用ExcelCode代码
     * @param list02
     * @param workbook
     * @return
     */
    public static HSSFWorkbook commonExcelCode(List<?> list02, HSSFWorkbook workbook){
        //获取对象
        Object t0 = list02.get(0);
        //获取反射对象
        Class<?> t0Clazz = t0.getClass();
        //反射对象注解
        Excel excel = t0Clazz.getAnnotation(Excel.class);
        //sheet名称
        String sheetName = excel.sheetName();
        //标题
        String[] headers = excel.header();
        int rowCount = 0;
        //创建sheet
        HSSFSheet sheet = workbook.createSheet(sheetName);
        if (headers != null && headers.length > 0) {
            //在工作表中创建一个新行并返回高级别表示  执行过后才+1
            HSSFRow row = sheet.createRow(rowCount++);
            //列数量 下标从0开始
            int cellCount = 0;
            for (String head : headers) {
                HSSFCell cell = row.createCell(cellCount++);
                cell.setCellValue(head);
            }
        }
        //创建map集合存域
        Map<String, Field> fieldMap = new HashMap<String, Field>(8);
        Class tempClazz = t0Clazz;
        do {
            Field[] declaredFields = tempClazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                fieldMap.put(declaredField.getName(), declaredField);
            }
            tempClazz = tempClazz.getSuperclass();
        } while (!tempClazz.equals(Object.class));
        //遍历集合
        for (Object t : list02) {
            //标题上面有了 开始数据
            HSSFRow row = sheet.createRow(rowCount++);
            //映射
            for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
                Field field = entry.getValue();
                ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
                if (excelProperty != null) {
                    int index = excelProperty.index();
                    Cell cell = row.createCell(index);
                    Method method = null;
                    try {
                        method = t0Clazz.getMethod("get" + StringUtils.capitalize(field.getName()));
                    } catch (NoSuchMethodException e) {
                        try {
                            method = t0Clazz.getMethod("is" + StringUtils.capitalize(field.getName()));
                        } catch (NoSuchMethodException e1) {
                            logger.error("no such method: {}", field.getName());
                        }
                    }
                    String cellValue = null;
                    try {
                        cellValue = method.invoke(t).toString();
                    } catch (Exception e) {
                        cellValue = "";
                    }
                    cell.setCellValue(cellValue);
                }
            }
        }
        for (int colNum = 0; colNum < headers.length; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if (colNum == 0) {
                sheet.setColumnWidth(colNum, (columnWidth - 2) * 256);
            } else {
                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
        }
        return workbook;
    }
}
