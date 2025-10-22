package com.application.smartEdu.dao;

import java.sql.SQLException;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.application.smartEdu.dto.InstructorVO;
import com.application.smartEdu.dto.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void insertMember(MemberVO member) throws SQLException {
        sqlSession.insert("Member-Mapper.insertMember", member);

    }

    @Override
    public void updateMember(MemberVO member) throws SQLException {
        sqlSession.update("Member-Mapper.updateMember", member);

    }

    @Override
    public void withdrawMember(int memberId) throws SQLException {
        sqlSession.update("Member-Mapper.withdrawMember", memberId);

    }

    @Override
    public MemberVO selectMemberByEmail(String email) throws SQLException {
        return sqlSession.selectOne("Member-Mapper.selectMemberByEmail", email);
    }

    @Override
    public void insertInstructor(InstructorVO instructor) throws SQLException {
        sqlSession.insert("Member-Mapper.insertInstructor", instructor);
        
    }

    @Override
    public InstructorVO selectInstructorById(int memberId) throws SQLException {
        return sqlSession.selectOne("Member-Mapper.selectInstructorById", memberId);
    }

    @Override
    public void insertStudent(int memberId) throws SQLException {
        sqlSession.insert("Member-Mapper.insertStudent", memberId);   
    }
    
}
