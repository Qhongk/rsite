package com.kzaza.common.util.file;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;

public class XMLUtils {

    private static final Logger logger = LoggerFactory.getLogger(XMLUtils.class);

    public static final void exportXML(String fileName, Document doc) throws IOException {
        // 将document中的内容写入文件中
        XMLWriter writer = null;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            // 创建写出对象
            writer = new XMLWriter(out, format);
            writer.write(doc);
            writer.flush();
            logger.debug("生成 {} 成功", fileName);
        } catch (IOException e) {
            logger.debug("生成 {} 失败", fileName);
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
