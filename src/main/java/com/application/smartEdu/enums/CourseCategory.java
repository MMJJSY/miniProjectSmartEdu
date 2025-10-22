package com.application.smartEdu.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseCategory {

    BACKEND("백엔드"),
    FRONTEND("프론트엔드"),
    DATABASE("데이터베이스");

    private final String CourseCategoryName;

}
