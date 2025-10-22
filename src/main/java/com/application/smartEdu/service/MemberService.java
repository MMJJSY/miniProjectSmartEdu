package com.application.smartEdu.service;

import java.sql.SQLException;


import com.application.smartEdu.dto.InstructorVO;
import com.application.smartEdu.dto.MemberVO;
import com.application.smartEdu.exception.InstructorPendingException;
import com.application.smartEdu.exception.InstructorRejectedException;
import com.application.smartEdu.exception.InvalidPasswordException;
import com.application.smartEdu.exception.NotFoundEmailException;

public interface MemberService {

    // 회원조회
    MemberVO getMember(String email) throws SQLException;

    // 강사조회
    InstructorVO getInstructor(int memberId) throws SQLException;

    // 회원등록
    void regist(MemberVO member) throws SQLException;

    // 학생등록
    void registStudent(int memberId) throws SQLException;
    // 강사등록
    void registInstructor(InstructorVO instructor) throws SQLException;

    // 회원수정
    void modify(MemberVO member) throws SQLException;

    // 비밀번호 변경
    void changePassword(String email , String newPwd) throws SQLException, NotFoundEmailException;

    // 회원탈퇴
    void withdraw(int memberId) throws SQLException;

    // 로그인
    MemberVO login(String email, String pwd) throws SQLException, NotFoundEmailException, InvalidPasswordException,
            InstructorPendingException, InstructorRejectedException;
}
