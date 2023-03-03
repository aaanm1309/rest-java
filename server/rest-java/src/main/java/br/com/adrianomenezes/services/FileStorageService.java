package br.com.adrianomenezes.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.adrianomenezes.config.FileStorageConfig;
import br.com.adrianomenezes.exceptions.FileStorageException;
import br.com.adrianomenezes.exceptions.MyFileNotFoundException;

@Service
public class FileStorageService {

	
	private final Path fileStorageLocation;


	public FileStorageService(FileStorageConfig fileStorageConfig) {
		Path path = Paths.get(fileStorageConfig.getUploadDir())
				.toAbsolutePath().normalize();
		
		this.fileStorageLocation = path;
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException(
					"Could not create trhe directory where the uploaded files wil be stored!",e);
		}
	
	}
	
	public String storeFile(MultipartFile file) {
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (filename.contains("..") ) {
				throw new FileStorageException(
						"Sorry! Filename contains invalid path sequence " + filename);
			}
			Path targetLocation = this.fileStorageLocation.resolve(filename);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return filename;
		} catch (Exception e) {
			throw new FileStorageException(
					"Could not store file " + filename + ". Please try again!",e);
		}
	}
	
	

	public Resource loadFileAsResource(String filename) {
		try {
			Path filePath = this.fileStorageLocation.resolve(filename);
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new MyFileNotFoundException("File not found "+filename);
			}
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found "+filename, e);
		}
	}

	
	
}
