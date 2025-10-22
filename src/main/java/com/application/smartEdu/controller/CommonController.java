package com.application.smartEdu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.application.smartEdu.dto.MemberVO;
import com.application.smartEdu.enums.Role;
import com.application.smartEdu.exception.InstructorPendingException;
import com.application.smartEdu.exception.InstructorRejectedException;
import com.application.smartEdu.exception.InvalidPasswordException;
import com.application.smartEdu.exception.NotFoundEmailException;
import com.application.smartEdu.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/loginForm")
    public String loginForm(@RequestParam(required = false) String retUrl, Model model) {
        model.addAttribute("retUrl", retUrl);
        return "/common/login";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "main"; // templates/main.html 렌더링
    }

    @PostMapping("/login")
    public String login(String email, String pwd, String role, String retUrl,
            HttpSession session, RedirectAttributes rttr) {
        String url = "redirect:/common/main";

        try {
            MemberVO loginUser = memberService.login(email, pwd);

            if (role != null && !role.isBlank()) {
                if ("instructor".equals(role) && loginUser.getRole() != Role.INSTRUCTOR && loginUser.getRole() != Role.ADMIN) {
                    rttr.addFlashAttribute("msg", "수강생으로 로그인하세요.");
                    return "redirect:/common/loginForm";
                }
                if ("student".equals(role) && loginUser.getRole() != Role.STUDENT) {
                    rttr.addFlashAttribute("msg", "강사로 로그인하세요.");
                    return "redirect:/common/loginForm";

                }
            }
            // 세션 저장
            session.setAttribute("loginUser", loginUser);
            session.setMaxInactiveInterval(30 * 60);

            // 리다이렉트 대상이 있으면 거기로
            if (retUrl != null && !retUrl.isEmpty()) {
                url = "redirect:" + retUrl;
            }
        } catch (InstructorPendingException e) {
            rttr.addFlashAttribute("msg", e.getMessage()); // "승인 요청 중입니다."
            url = "redirect:/common/loginForm";

        } catch (InstructorRejectedException e) {
            rttr.addFlashAttribute("msg", e.getMessage()); // "승인이 거절되었습니다."
            url = "redirect:/common/loginForm";

        } catch (NotFoundEmailException | InvalidPasswordException e) {
            // 로그인 실패
            url = "redirect:/common/loginForm";
            rttr.addFlashAttribute("msg", "이메일 또는 비밀번호가 올바르지 않습니다.");
            rttr.addFlashAttribute("msgType", "error");
            if (retUrl != null && !retUrl.isEmpty()) {
                rttr.addAttribute("retUrl", retUrl);
            }
        } catch (Exception e) {
            // logger.error("Login error", e);
            rttr.addFlashAttribute("msg", "알 수 없는 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            url = "redirect:/common/loginForm";
        }
        return url;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/common/main";
    }

}
