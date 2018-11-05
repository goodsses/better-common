package com.better.common.image;

import com.better.anno.bean.TableImageJPGE;
import com.better.anno.bean.TableImageJPGEProperty;
import com.better.common.file.PoiUtils;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class TableImageUtils {

    //日志
    private static Logger logger = LoggerFactory.getLogger(PoiUtils.class);

    /**
     * 生成图片
     * @param cellsValue 以二维数组形式存放 表格里面的值
     * @param path 文件保存路径
     */
    public static void myGraphicsGeneration(List<?> cellsValue, String path, String title, String fontStyle, int imageWidth, int rowheight) {
        // 字体大小
        int fontTitileSize = 15;
        // 横线的行数
        int totalrow = cellsValue.size()+2;
        // 竖线的行数
        int totalcol = 0;
        //反射对象
        Class<?> clazz = cellsValue.get(0).getClass();
        if (cellsValue.get(0)  != null) {
            //属性的个数 反射抓取个数
            totalcol = clazz.getDeclaredFields().length;
        }
//        // 图片宽度
//        int imageWidth = 1024;
//        // 行高
//        int rowheight = 40;
        // 图片高度
        int imageHeight = totalrow*rowheight+50;
        // 起始高度
        int startHeight = 10;
        // 起始宽度
        int startWidth = 10;
        // 单元格宽度
        int colwidth = (int)((imageWidth-20)/totalcol);
        BufferedImage image = new BufferedImage(imageWidth, imageHeight,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0, imageWidth, imageHeight);
        graphics.setColor(new Color(220,240,240));

        //画横线
        for(int j=0;j<totalrow; j++){
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth, startHeight+(j+1)*rowheight, startWidth+colwidth*totalcol, startHeight+(j+1)*rowheight);
        }
        //画竖线
        for(int k=0;k<totalcol+1;k++){
            graphics.setColor(Color.black);
            graphics.drawLine(startWidth+k*colwidth, startHeight+rowheight, startWidth+k*colwidth, startHeight+rowheight*totalrow);
        }
        //设置字体
        Font font = new Font(fontStyle,Font.BOLD,fontTitileSize);
        graphics.setFont(font);
        //写标题
        if(StringUtils.isNotBlank(title)){
            graphics.drawString(title, startWidth, startHeight+rowheight-10);
        }
        //获取total
        TableImageJPGE jpg = clazz.getAnnotation(TableImageJPGE.class);
        String[] total = jpg.total();
        for(int n=0; n < total.length; n++){
            font = new Font(fontStyle,Font.BOLD,fontTitileSize);
            graphics.setFont(font);
            graphics.drawString(total[n], startWidth+colwidth*n+5, startHeight+rowheight*2-10);
        }
        int yIndex=1;
        for(Object object : cellsValue){
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                TableImageJPGEProperty tableImageJPGEProperty = field.getAnnotation(TableImageJPGEProperty.class);
                if(tableImageJPGEProperty != null){
                    int index = tableImageJPGEProperty.index();
                    font = new Font(fontStyle,Font.PLAIN,fontTitileSize);
                    graphics.setFont(font);
                    try {
                        Method method = clazz.getMethod("get" + StringUtils.capitalize(field.getName()));
                        String cellValue = method.invoke(object).toString();
                        graphics.drawString(cellValue, startWidth+colwidth*index+5, startHeight+rowheight*(yIndex+2)-10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            yIndex++;
        }
        // 保存图片
        createImage(image, path);
    }

    /**
     * 将图片保存到指定位置
     * @param image 缓冲文件类
     * @param fileLocation 文件位置
     */
    private static void createImage(BufferedImage image, String fileLocation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
            encoder.encode(image);
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
