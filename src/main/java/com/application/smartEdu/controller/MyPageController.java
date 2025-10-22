package com.application.smartEdu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.application.smartEdu.dto.MemberVO;
import com.application.smartEdu.exception.NotFoundEmailException;
import com.application.smartEdu.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String studentDashboard() {
        return "mypage/student_mypage";
    }

    @GetMapping("/info")
    public String studentInfo(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        

        if (loginUser == null) {
            return "redirect:/common/loginForm";
        }

        model.addAttribute("member", loginUser);

        return "mypage/student_info";
    }

    // 비밀번호 확인
    @GetMapping("/pwdcheck")
    public String pwdCheck(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/common/loginForm";

        }
        model.addAttribute("member", loginUser);

        return "mypage/student_pwdcheck";
    }

    @PostMapping("/pwdcheck")
    public String pwdCheck(String pwd, HttpSession session, RedirectAttributes rttr) throws Exception {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/common/loginForm";
        }

        MemberVO member = memberService.getMember(loginUser.getEmail());

        if (member != null && member.getPwd().equals(pwd)) {
            return "redirect:/mypage/pwdedit";
        } else {
            rttr.addFlashAttribute("msg", "비밀번호가 올바르지 않습니다.");
            return "redirect:/mypage/pwdcheck";
        }
    }

    // 비밀번호 수정
    @GetMapping("/pwdedit")
    public String pwdEditForm(HttpSession session, Model model) {
        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/common/loginForm";
        }
        model.addAttribute("member", loginUser);
        return "mypage/student_pwdedit";
    }

    @PostMapping("/pwdedit")
    public String pwdEdit(String newPwd, String confirmPwd,
            HttpSession session, RedirectAttributes rttr) throws Exception {

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/common/loginForm";
        }

        if(!newPwd.equals(confirmPwd)){
            rttr.addFlashAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "redirect:/mypage/pwdedit";
        }

        try {
            memberService.changePassword(loginUser.getEmail(), newPwd);
            loginUser.setPwd(newPwd);
            rttr.addFlashAttribute("msg","비밀번호가 성공적으로 변경되었습니다.");
            return "redirect:/mypage";
        } catch (NotFoundEmailException e) {
            rttr.addFlashAttribute("msg"," 회원 정보를 찾을 수 없습니다.");
            return "redirect:/common/loginForm";
        }
    }

    @GetMapping("/mycourse")
    public String mycourse(){
        return "mypage/student_class_detail";
    }

}
