package com.archisacademy.ecommercespringboot.service.impl;

import com.archisacademy.ecommercespringboot.dto.ReviewDto;
import com.archisacademy.ecommercespringboot.model.Product;
import com.archisacademy.ecommercespringboot.model.Review;
import com.archisacademy.ecommercespringboot.model.User;
import com.archisacademy.ecommercespringboot.repository.ProductRepository;
import com.archisacademy.ecommercespringboot.repository.ReviewRepository;
import com.archisacademy.ecommercespringboot.repository.UserRepository;
import com.archisacademy.ecommercespringboot.service.ReviewService;
import org.springframework.stereotype.Service;

<<<<<<< HEAD
import java.util.*;
import java.util.stream.Collectors;
=======
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
>>>>>>> 91cbb2d753978fff1c32abe66b9c55afe0a91666

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReviewDto saveReview(ReviewDto reviewDto) {
        Optional<Product> product = productRepository.findByUuid(reviewDto.getProductUuid());
        Optional<User> user = userRepository.findByUuid(reviewDto.getUserUuid());

        if (!user.isPresent() || !product.isPresent()) {
            throw new RuntimeException("User or product not found");
        }

        Review review = new Review();
        review.setUuid(reviewDto.getUuid());
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setIsApproved(false);
        review.setCreatedAt(new Date()); // chnage this to local date
        review.setUser(user.get());
        review.setProduct(product.get());

        reviewRepository.save(review);
        return reviewDto;
    }

    @Override
    public ReviewDto getReviewByUserUuid(String userUuid) {
        Review review = reviewRepository.findByUserUuid(userUuid);
        if (review == null) {
            throw new RuntimeException("Review not found for userUuid: " + userUuid);
        }

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setUuid(review.getUuid());
        reviewDto.setRating(review.getRating());
        reviewDto.setComment(review.getComment());
        reviewDto.setCreatedAt(review.getCreatedAt());
        reviewDto.setUserUuid(review.getUser().getUuid());
        reviewDto.setProductUuid(review.getProduct().getUuid());

        return reviewDto;
    }

    @Override
    public List<ReviewDto> getReviewByProductUuid(String productUuid) {
        List<Review> reviews = reviewRepository.findAllByProductUuid(productUuid);
        if (reviews.isEmpty()) {
            throw new RuntimeException("No reviews found for productUuid: " + productUuid);
        }

        return reviews.stream()
                .map(this::convertReviewToDto)
                .collect(Collectors.toList());
    }


    @Override
    public String updateReviewByUserUuid(String userUuid, ReviewDto updatedReviewDto) {
        Review existingReview = reviewRepository.findByUserUuid(userUuid);
        if (existingReview != null) {
            existingReview.setRating(updatedReviewDto.getRating());
            existingReview.setComment(updatedReviewDto.getComment());
            reviewRepository.save(existingReview);
        }
        return "Review updated successfully";
    }

    @Override
    public void deleteReviewByUserUuid(String userUuid) {
        Review existingReview = reviewRepository.findByUserUuid(userUuid);
        if (existingReview == null) {
            throw new RuntimeException("Review not found for userUuid: " + userUuid);
        }

        reviewRepository.delete(existingReview);
    }

    @Override
    public void deleteReviewByProductUuid(String reviewUuid) {
        List<Review> existingReviews = reviewRepository.findAllByReviewUuid(reviewUuid);
        if (existingReviews.isEmpty()) {
            throw new RuntimeException("No reviews found for productUuid: " + reviewUuid);
        }
        reviewRepository.deleteAll(existingReviews);
    }

    @Override
    public List<ReviewDto> getAllReviewsByProductUUID(String productUUID) {
        List<Review> reviews = reviewRepository.findAllReviewsByUserUuid(productUUID);
        if(reviews.isEmpty()){
            throw new RuntimeException("There is no reviews present for this product");
        }
        List<ReviewDto> response = new ArrayList<>();
        reviews.forEach(review -> {
            if (review.getIsApproved()){
                ReviewDto reviewDto = ReviewDto.builder()
                        .uuid(review.getUuid())
                        .build();
            }
        });
        return response;
    }

    @Override
    public String approveReview(String reviewUUID) {
        Optional<Review> review =reviewRepository.findByUuid(reviewUUID);
        if (review.isEmpty()){
            throw new RuntimeException("Review not found");
        }
        review.get().setIsApproved(true);
        reviewRepository.save(review.get());
        return "Review has been approved successfully";
    }

    private ReviewDto convertReviewToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setUuid(review.getUuid());
        reviewDto.setRating(review.getRating());
        reviewDto.setComment(review.getComment());
        reviewDto.setCreatedAt(review.getCreatedAt());
        reviewDto.setUserUuid(review.getUser().getUuid());
        reviewDto.setProductUuid(review.getProduct().getUuid());

        return reviewDto;
    }

    @Override
    public String approveReview(String reviewUUID) {
        Optional<Review> review = reviewRepository.findByUuid(reviewUUID);
        if(review.isEmpty()){
            throw new RuntimeException("Review not found");
        }
        review.get().setIsApproved(true);
        reviewRepository.save(review.get());
        return "Review has been approved successfully";
    }

    @Override
    public List<ReviewDto> getAllReviewsByProductUUID(String productUUID) {
        List<Review> reviews = reviewRepository.findAllReviewsByUserUuid(productUUID);
        if(reviews.isEmpty()){
            throw new RuntimeException("There is no review present for this product");
        }
        List<ReviewDto> response = new ArrayList<>();
        reviews.forEach(review -> {

            // checks for approved reviews
            if(review.getIsApproved()){
                ReviewDto reviewDto = ReviewDto.builder()
                        .uuid(review.getUuid())
                        .build(); // write the reset of code here
                // add review to the dto list
            }
        });

        return response;
    }
}