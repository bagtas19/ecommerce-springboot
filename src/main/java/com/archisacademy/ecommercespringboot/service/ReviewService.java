package com.archisacademy.ecommercespringboot.service;

import com.archisacademy.ecommercespringboot.dto.ReviewDto;
import com.archisacademy.ecommercespringboot.model.Review;

import java.util.List;

public interface ReviewService {
    ReviewDto saveReview(ReviewDto reviewDto);

    ReviewDto getReviewByUserUuid(String userUuid);

    List<ReviewDto> getReviewByProductUuid(String productUuid);

    String updateReviewByUserUuid(String userUuid, ReviewDto updatedReviewDto);

    void deleteReviewByUserUuid(String userUuid);

    void deleteReviewByProductUuid(String reviewUuid);

    List<ReviewDto> getAllReviewsByProductUUID(String productUUID);

    String approveReview(String reviewUUID);

}
