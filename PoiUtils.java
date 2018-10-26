package com.better.common.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
}
