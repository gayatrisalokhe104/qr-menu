package com.qrbased.cafe.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qrbased.cafe.dao.RestaurantDetailsRepository;
import com.qrbased.cafe.dto.RestaurantDetails;
import com.qrbased.cafe.exception.DuplicateEntryException;
import com.qrbased.cafe.exception.FileOperationException;
import com.qrbased.cafe.exception.ResourceNotFoundException;
import com.qrbased.cafe.service.RestaurantDetailsService;

@Service
public class RestaurantDetailsServiceImpl implements RestaurantDetailsService{

	@Autowired
	private RestaurantDetailsRepository restaurantDetailsRepository;
	
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/restaurantsLogos";
	
	@Override
	public RestaurantDetails createRestaurantDetails(RestaurantDetails restaurantDetails, MultipartFile file) {

	    String name = restaurantDetails.getRestaurantName();
	    String logo = file.getOriginalFilename();

	    try {
	        boolean existsByName = restaurantDetailsRepository.existsByRestaurantName(name);
	        boolean existsByLogo = restaurantDetailsRepository.existsByRestaurantLogo(logo);
	        if (existsByName && existsByLogo) {
	            throw new DuplicateEntryException("Restaurant details with same name and logo already exist.");
	        } else if (existsByName) {
	            throw new DuplicateEntryException("Restaurant details with same name already exist.");
	        } else if (existsByLogo) {
	            throw new DuplicateEntryException("Restaurant details with same logo already exist.");
	        }
	        String originalFilename = file.getOriginalFilename();
	        Path filePath = Paths.get(uploadDirectory, originalFilename);
	        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
	            outputStream.write(file.getBytes());
	        }
	        restaurantDetails.setRestaurantLogo(originalFilename);
	        return restaurantDetailsRepository.save(restaurantDetails);
	    } catch (DuplicateEntryException e) {
	        throw e;
	    } catch (IOException e) {
	        throw new FileOperationException("Failed to create restaurant details.", e);
	    }
	    catch (Exception e) {
	        throw new RuntimeException("Unexpected error occurred.", e);
	    }
	}

	@Override
	public RestaurantDetails updateRestaurantDetails(int restaurantId, RestaurantDetails restaurantDetail) {

	    RestaurantDetails existingDetails = restaurantDetailsRepository.findById(restaurantId).orElseThrow(
	            () -> new ResourceNotFoundException("Restaurant not found with id " + restaurantId));

	    if (restaurantDetail.getRestaurantName() != null) {
	        
	        boolean existsByName = restaurantDetailsRepository.existsByRestaurantName(
	                restaurantDetail.getRestaurantName()); // Check for duplicate name (excluding the currently updated object)
	        if (existsByName) {
	            throw new DuplicateEntryException("Restaurant details with the same name already exist.");
	        }

	        existingDetails.setRestaurantName(restaurantDetail.getRestaurantName());
	        existingDetails.setAddress(restaurantDetail.getAddress());
	    }

	    return restaurantDetailsRepository.save(existingDetails);
	}

	@Override
	public Resource updateLogo(int restaurantId, MultipartFile file) {
	    RestaurantDetails existingDetails = restaurantDetailsRepository.findById(restaurantId)
	            .orElseThrow(() -> new ResourceNotFoundException("Restaurant not found with id " + restaurantId));

	    String oldLogoPath = existingDetails.getRestaurantLogo(); // Store the path of the old logo

	    if (file != null) {
	        String originalFilename = file.getOriginalFilename();
	        Path filePath = Paths.get(uploadDirectory, originalFilename);

	        try (OutputStream outputStream = Files.newOutputStream(filePath)) {
	            outputStream.write(file.getBytes());
	        } catch (IOException e) {
	            throw new FileOperationException("Failed to update logo.", e);
	        }

	        boolean existsByLogo = restaurantDetailsRepository.existsByRestaurantLogo(
	        		originalFilename); // Check for duplicate logo (excluding the currently updated object)
	        if (existsByLogo) {
	            throw new DuplicateEntryException("Restaurant details with the same logo already exist.");
	        }

	        existingDetails.setRestaurantLogo(originalFilename);
	    }

	    	restaurantDetailsRepository.save(existingDetails);
	    	deleteLogoFile(oldLogoPath);

	    Path imagePath = Paths.get(uploadDirectory, existingDetails.getRestaurantLogo());
	    return new FileSystemResource(imagePath.toFile());
	}

	private void deleteLogoFile(String filename) {
	    try {
	        Path filePath = Paths.get(uploadDirectory, filename);
	        Files.deleteIfExists(filePath);
	    } catch (IOException e) {
	        throw new FileOperationException("Failed to delete old logo file: " + filename, e);
	    }
	}

	@Override
	public RestaurantDetails getRestaurantDetailsById(int restaurantId) {
		
		RestaurantDetails details = restaurantDetailsRepository.findById(restaurantId).orElseThrow(
				()-> new ResourceNotFoundException("Restaurant not found with id "+restaurantId));
		
		return details;
	}

}
