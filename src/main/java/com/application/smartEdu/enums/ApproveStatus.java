package com.application.smartEdu.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApproveStatus {
    PENDING("승인대기"),
    POSITIVE("승인됨"),
    NEGATIVE("거절됨");

    private final String ApproveStatusName;

}
