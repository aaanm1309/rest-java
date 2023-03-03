package br.com.adrianomenezes.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.adrianomenezes.data.vo.v1.UploadFileResponseVO;
import br.com.adrianomenezes.exceptions.MyFileNotFoundException;
import br.com.adrianomenezes.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "File EndPoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {
	private Logger logger =   Logger.getLogger(FileController.class.getName());

	@Autowired
	private FileStorageService fileStorageService;
	
	
	@PostMapping("/uploadfile")
	public UploadFileResponseVO uploadFile(
			@RequestParam("file") MultipartFile file) {
		logger.info("Storing file to disk");
		var returnedFilename = fileStorageService.storeFile(file);
		String fileDownloadUri = ServletUriComponentsBuilder
							.fromCurrentContextPath()
							.path("/api/file/vi/downloadfile/")
							.path(returnedFilename)
							.toUriString();
		return new UploadFileResponseVO(
						returnedFilename
						, fileDownloadUri
						, file.getContentType()
						, file.getSize()); 
		
	}

	@PostMapping("/uploadMultiplefiles")
	public List<UploadFileResponseVO> uploadMultiplefiles(
			@RequestParam("files") MultipartFile[] files) {
		logger.info("Storing files to disk");
		return Arrays.asList(files)
				.stream()
				.map( f -> this.uploadFile(f) )
				.collect(Collectors.toList()); 
		
	}
	
	
	@GetMapping("/downloadfile/{filename:.+}")
	public ResponseEntity<Resource> downloadFile(
			@PathVariable("filename") String filename,
			HttpServletRequest request) {
		logger.info("Reading a file from disk");
		
		Resource resource =  fileStorageService.loadFileAsResource(filename);
		
		String contentType =  "";
		try {
			contentType =  request
					.getServletContext()
					.getMimeType(resource.getFile().getAbsolutePath());
			if (contentType.isBlank()) {
				contentType = "application/octet-stream";
				
			}
		} catch (Exception e) {
			logger.info("Could not determine file type!");
		}
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\""
							+resource.getFilename()
							+"\"")
				.body(resource); 
		
	}
	

	

}
