package com.application.smartEdu.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.smartEdu.dao.MemberDAO;
import com.application.smartEdu.dto.InstructorVO;
import com.application.smartEdu.dto.MemberVO;
import com.application.smartEdu.enums.PendingStatus;
import com.application.smartEdu.enums.Role;
import com.application.smartEdu.exception.InstructorPendingException;
import com.application.smartEdu.exception.InstructorRejectedException;
import com.application.smartEdu.exception.InvalidPasswordException;
import com.application.smartEdu.exception.NotFoundEmailException;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

@Autowired
private MemberDAO memberDAO;


@Override
public MemberVO login(String email, String pwd) throws SQLException, NotFoundEmailException,InvalidPasswordException,InstructorPendingException,InstructorRejectedException {
    MemberVO member = memberDAO.selectMemberByEmail(email);
    if(member == null) throw new NotFoundEmailException();
    if(member.getPwd() ==null || !member.getPwd().equals(pwd)) throw new InvalidPasswordException();

    // 강사
    if (member.getRole() == Role.INSTRUCTOR) {
        InstructorVO instructor = memberDAO.selectInstructorById(member.getMemberId());
        if(instructor !=null) {
            if (instructor.getPendingStatus() == PendingStatus.PENDING) {
                throw new InstructorPendingException();
            }
            if (instructor.getPendingStatus() == PendingStatus.REJECTED){
                throw new InstructorRejectedException();
            }
        }
        
    }

    return member;
}
@Override
public MemberVO getMember(String email) throws SQLException {
    return memberDAO.selectMemberByEmail(email);
}

@Override
public void modify(MemberVO member) throws SQLException {
    memberDAO.updateMember(member);
    
}

@Override
public void regist(MemberVO member) throws SQLException {

    memberDAO.insertMember(member);
    
}

@Override
public void registStudent(int MemberId) throws SQLException{

    memberDAO.insertStudent(MemberId);
}

@Override
public void withdraw(int memberId) throws SQLException {
    memberDAO.withdrawMember(memberId);
    
}

@Override
public void registInstructor(InstructorVO instructor) throws SQLException{
    memberDAO.insertInstructor(instructor);
}
@Override
public InstructorVO getInstructor(int memberId) throws SQLException {
    return memberDAO.selectInstructorById(memberId);
}
@Override
public void changePassword(String email, String newPwd)
        throws SQLException, NotFoundEmailException {
    //회원 조회
    
    MemberVO current = memberDAO.selectMemberByEmail(email);
    if (current == null) {
        throw new NotFoundEmailException();
    }

    current.setPwd(newPwd);
    modify(current);
}


}
