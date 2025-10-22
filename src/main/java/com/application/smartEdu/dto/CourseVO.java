package com.application.smartEdu.dto;

import java.time.LocalDateTime;

import com.application.smartEdu.enums.ApproveStatus;
import com.application.smartEdu.enums.CourseCategory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseVO {
    private int courseId;
    private String title;
    private String price;
    private int instructorId;
    private CourseCategory category;
    private String img;
    private String description;
    private String curriculum;
    private int viewCount;
    private ApproveStatus approvedStatus;
    private CouresStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
