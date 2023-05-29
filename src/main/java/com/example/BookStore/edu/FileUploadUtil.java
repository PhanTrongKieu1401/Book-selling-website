package com.example.BookStore.edu;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	//tạo hàm save file với đầu vào: link đường dẫn, tên file, multipartfile (đại diện cho file upload)
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path path = Paths.get(uploadDir); //lấy đường dẫn file
		
		//kiểm tra xem đường dẫn path có tồn tại hay không
		if(!Files.exists(path)) {
			Files.createDirectories(path);
		}
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = path.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);//replace nếu nó tồn tại
		} catch (Exception e) {
			// TODO: handle exception
			throw new IOException("Không thể lưu file " + fileName);
		}
	}
}
