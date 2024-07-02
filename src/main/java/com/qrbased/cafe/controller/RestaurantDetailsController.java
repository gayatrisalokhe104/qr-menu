package com.qrbased.cafe.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaTypeFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.qrbased.cafe.dto.RestaurantDetails;
import com.qrbased.cafe.exception.CustomDirectoryCreationException;
import com.qrbased.cafe.service.RestaurantDetailsService;

@RestController
@RequestMapping("/api/v1/admin/restaurantDetails")
public class RestaurantDetailsController {
	
	@Autowired
	private RestaurantDetailsService restaurantDetailsService;
	
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/restaurantsLogos";
	
	 @PostConstruct
	    public void initialize() {
	        try {
	            createUploadDirectoryIfNotExists();
	        } catch (CustomDirectoryCreationException e) {
	            throw new CustomDirectoryCreationException("Failed to create upload directory", e);
	        }
	    }
	
	 private void createUploadDirectoryIfNotExists() throws CustomDirectoryCreationException {
	        Path directoryPath = Paths.get(uploadDirectory);

	        try {
	            if (Files.notExists(directoryPath)) {
	                Files.createDirectories(directoryPath);
	                System.out.println("Upload directory created: " + directoryPath);
	            } else {
	                System.out.println("Upload directory already exists: " + directoryPath);
	            }
	        } catch (IOException e) {
	            throw new CustomDirectoryCreationException("Failed to create upload directory", e);
	        }
	    }
	
	@PostMapping("/create-restaurantDetails")
	public ResponseEntity<RestaurantDetails> createRestaurant(@ModelAttribute RestaurantDetails restaurantDetails,
								@RequestParam("image") MultipartFile file){
		
		RestaurantDetails savedRestaurantDetails = restaurantDetailsService.createRestaurantDetails(restaurantDetails, file);
		return ResponseEntity.status(HttpStatus.CREATED.value()).body(savedRestaurantDetails);
	}
	
	@GetMapping("/get-restaurantDetails/{restaurantId}")
	public ResponseEntity<RestaurantDetails> getRestaurantDetailsById(@PathVariable int restaurantId){
		
		return ResponseEntity.status(HttpStatus.OK.value()).body(restaurantDetailsService.getRestaurantDetailsById(restaurantId));
	}

	@GetMapping("/get-Logo/{restaurantId}")
	public ResponseEntity<Resource> getRestaurantLogo(@PathVariable int restaurantId) throws IOException{
		
		RestaurantDetails restaurantDetails = restaurantDetailsService.getRestaurantDetailsById(restaurantId);
		
		Path imagePath = Paths.get(uploadDirectory, restaurantDetails.getRestaurantLogo());
		Resource resource = new FileSystemResource(imagePath.toFile());
		String contentType = Files.probeContentType(imagePath);
		
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.parseMediaType(contentType)).body(resource);
	}
	
	@PutMapping("/update-restaurantDetails/{restaurantId}")
    public ResponseEntity<RestaurantDetails> updateRestaurantDetails(@PathVariable int restaurantId,
                               @ModelAttribute RestaurantDetails restaurantDetails) {
            RestaurantDetails updatedRestaurantDetails = restaurantDetailsService.updateRestaurantDetails(restaurantId, restaurantDetails);
            return ResponseEntity.status(HttpStatus.OK.value()).body(updatedRestaurantDetails);
       
    }


	@PutMapping("/update-Logo/{restaurantId}")
	public ResponseEntity<Resource> updateRestaurantLogo(@PathVariable int restaurantId,
	                                                     @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {

	    Resource updatedImage = restaurantDetailsService.updateLogo(restaurantId, file);
	    MediaType mediaType = determineMediaType(updatedImage);
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(mediaType);

	    return new ResponseEntity<>(updatedImage, headers, HttpStatus.OK);
	}

	private MediaType determineMediaType(Resource resource) throws IOException {
	    try {
	        return MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return MediaType.APPLICATION_OCTET_STREAM;
	    }
	}
}
