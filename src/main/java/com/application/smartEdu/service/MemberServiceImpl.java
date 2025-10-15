package com.application.smartEdu.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.smartEdu.dao.MemberDAO;
import com.application.smartEdu.dto.InstructorVO;
import com.application.smartEdu.dto.MemberVO;
import com.application.smartEdu.exception.InvalidPasswordException;
import com.application.smartEdu.exception.NotFoundEmailException;

@Service
public class MemberServiceImpl implements MemberService {

@Autowired
private MemberDAO memberDAO;


@Override
public MemberVO login(String email, String pwd) throws SQLException, NotFoundEmailException , InvalidPasswordException {
    MemberVO member = memberDAO.selectMemberByEmail(email);
    if(member == null) throw new NotFoundEmailException();
    if(member.getPwd().equals(pwd)) throw new InvalidPasswordException();

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
public void withdraw(int memberId) throws SQLException {
    memberDAO.withdrawMember(memberId);
    
}

@Override
public void registInstructor(InstructorVO instructor) throws SQLException{
    memberDAO.insertInstructor(instructor);
}
}
