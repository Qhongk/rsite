package com.kza.common.util.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileUploadUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);

    public static final List<String> fileUpload(String tmpPath, String filePath, HttpServletRequest request) throws FileUploadException {
        boolean isMulti = ServletFileUpload.isMultipartContent(request);
        List<String> fileNames = new ArrayList<>();
        if (isMulti) {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Configure a repository (to ensure a secure temp location is used)
            File repository = new File(tmpPath);
            factory.setRepository(repository);
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(8 * 1024 * 1024);
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            if (null != items && items.size() > 0) {
                if (!new File(filePath).exists()) {
                    new File(filePath).mkdir();
                }
            }

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    int iIdx = 0;
                    if ((iIdx = item.getName().lastIndexOf(".")) > 0) {
                        String fileName = item.getName();
                        fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
                        String name = new StringBuilder(fileName.substring(0, iIdx)).append("_")
                                .append(DateFormatUtils.format(System.currentTimeMillis(), "yyyy_MM_dd_HH_mm"))
                                .append(item.getName().substring(iIdx)).toString();
                        try {
                            String tmpFileName = filePath + "/" + name;
                            item.write(new File(tmpFileName));
                            fileNames.add(tmpFileName);
                        } catch (Exception e) {
                            logger.error("upload file {} error!!!", name);
                            logger.error("upload file error !", e);
                        }
                    }
                }
            }
        }
        return fileNames;
    }
}