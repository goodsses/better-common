package com.better.common.file;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Auther: 梁晓宇
 * @Date: 2018/10/29 11:05
 * @Description: 生成或转换PDF
 */
public class PdfUtils {

    //日志
    private static Logger logger = LoggerFactory.getLogger(PdfUtils.class);

    /*
    *
        用iText生成PDF文档需要5个步骤：

    　　①建立com.lowagie.text.Document对象的实例。
    　　Document document = new Document();

    　　②建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
    　　PDFWriter.getInstance(document, new FileOutputStream("Helloworld.PDF"));

    　　③打开文档。
    　　document.open();

    　　④向文档中添加内容。
    　　document.add(new Paragraph("Hello World"));

    　　⑤关闭文档。
    　　document.close();
    *
    * */

    public static void exportPdfFile(){
        Document document = null;
        File file = null;
        try {
            file = new File("d://pdf");
            if (!file.exists()) {
                file.mkdirs(); // 创建目录
            }
            //itextPdf 默认不支持中文需要开启
            BaseFont bfchinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Rectangle rectangle = new Rectangle(PageSize.A4);
            document = new Document(rectangle);
            OutputStream out = new FileOutputStream(file + File.separator + "API文档.pdf");
            PdfWriter pdfWriter = PdfWriter.getInstance(document, out);
            document.open();
            Font font = new Font(bfchinese);
            document.add(new Paragraph("hello Word你好", font));
            document.close();
            pdfWriter.close();
        }catch (Exception e){
            logger.error(e.getMessage());
        }
    }

}
