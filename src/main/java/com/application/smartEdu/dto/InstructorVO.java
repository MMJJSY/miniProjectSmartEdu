package com.application.smartEdu.dto;

import com.application.smartEdu.enums.PendingStatus;

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
public class InstructorVO {
    private Integer instructor_id; // 강사번호
    private String resume; // 이력서
    private PendingStatus pending_status; // 승인상태
    

}
