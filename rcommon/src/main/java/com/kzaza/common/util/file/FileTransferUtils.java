package com.kzaza.common.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileTransferUtils {

	private static final Logger logger = LoggerFactory.getLogger(FileTransferUtils.class);

	public static final boolean cleanFileDirectory(String filePath) {
		logger.debug("cleanFileDirectory {}", filePath);
		boolean result = false;
		File file = new File(filePath);
		if (!file.exists()) {
			result = file.mkdir();
		} else {
			// 清空文件夹
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirFile(new File(file, children[i]));
				if (!success) {
					return false;
				}
			}
			result = true;
		}
		return result;
	}

	public static final boolean deleteDirFile(File dir) {
		if (dir.isDirectory()) {
			// 递归删除目录中的子目录下
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDirFile(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * @param filePath
	 * @param destPath
	 * @param uploadURL
	 * @throws FileNotFoundException
	 */
	public static final void uploadFile(String filePath, String destPath, String uploadURL) throws FileNotFoundException {
		File file = new File(filePath);
		if (!file.exists()) {
			return;
		}
		if (file.isDirectory()) {
			String[] child = file.list();
			for (int i = 0; i < child.length; i++) {
				File f = new File(filePath, child[i]);
				if (f.isDirectory()) {
					uploadFile(filePath + "/" + child[i], destPath + child[i] +"/", uploadURL);
				} else {
					FileInputStream input = new FileInputStream(f);
					FileUploadCommitUtils.upload(uploadURL, destPath + child[i], true, input);
				}
			}
		} else {
			FileInputStream input = new FileInputStream(file);
			FileUploadCommitUtils.upload(uploadURL, destPath + file.getName(), true, input);
		}
	}

}
