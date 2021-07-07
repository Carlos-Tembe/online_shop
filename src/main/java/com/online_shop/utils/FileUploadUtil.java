package com.online_shop.utils;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
//		System.out.println("caminho ");
//		Path uploadPath = Paths.get(uploadDir);
//
//		if (!Files.exists(uploadPath)) {
//			Files.createDirectories(uploadPath);
//		}
//
//		try (InputStream inputStream = multipartFile.getInputStream()) {
//			Path filePath = uploadPath.resolve(fileName);
//			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//		} catch (IOException ioe) {
//			throw new IOException("Could not save image file: " + fileName, ioe);
//		}
	}
}
