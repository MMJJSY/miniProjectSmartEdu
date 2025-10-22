package com.application.smartEdu.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CourseStatus {

    PENDING("대기"),
    OPEN("개강"),
    CLOSE("종료");

    private final String CourseStatusName;

}
