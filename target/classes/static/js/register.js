// 숫자 입력 길이 제한
function limitInput(el, maxLength) {
    if (el.value.length > maxLength) {
        el.value = el.value.slice(0, maxLength);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    // 학생 또는 강사 폼 가져오기
    const form = document.getElementById("studentForm") || document.getElementById("instructorForm");
    if (!form) return; // 폼이 없으면 실행 안함

    const checkEmailBtn = document.getElementById("checkEmail");
    const emailMsg = document.getElementById("emailMsg");
    const passwordMsg = document.getElementById("passwordMsg");
    const password = document.getElementById("password");
    const passwordCheck = document.getElementById("passwordCheck");
    const pwInfo = document.querySelector(".info"); // 비밀번호 안내 문구
    const emailInput = document.getElementById("email");

    // 강사 폼의 첨부파일
    const resumeInput = document.getElementById("resume");
    let resumeMsg = null;
    if (resumeInput) {
        resumeMsg = document.createElement("p");
        resumeMsg.id = "resumeMsg";
        resumeMsg.style.color = "#e63946";
        resumeInput.parentNode.appendChild(resumeMsg);
    }

    // 이메일 바로 유효성 검사
    if (emailInput) {
        emailInput.addEventListener("input", () => {
            const email = emailInput.value.trim();
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

            if (!email) {
                emailMsg.textContent = "";
            } else if (!emailPattern.test(email)) {
                emailMsg.textContent = "이메일을 올바른 형식으로 작성해주세요.";
                emailMsg.style.color = "#e63946";
            } else {
                emailMsg.textContent = "";
            }
        });
    }

    // 이메일 중복확인 (모의 기능)
    if (checkEmailBtn && emailInput) {
        checkEmailBtn.addEventListener("click", () => {
            const email = emailInput.value.trim();
            if (!email) {
                emailMsg.textContent = "이메일을 입력해주세요.";
                emailMsg.style.color = "#e63946";
                return;
            }
            emailMsg.textContent = "사용 가능한 이메일입니다.";
            emailMsg.style.color = "green";
        });
    }

    // 비밀번호 유효성 검사
    if (password) {
        password.addEventListener("input", () => {
            const pw = password.value.trim();
            const pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/;

            if (pw === "") {
                pwInfo.style.color = "#666";
            } else if (!pwPattern.test(pw)) {
                pwInfo.style.color = "#e63946"; // 빨간색 경고
            } else {
                pwInfo.style.color = "green"; // 충족 시 초록색
            }
        });
    }

    // 비밀번호 일치 확인
    if (passwordCheck) {
        passwordCheck.addEventListener("input", () => {
            if (password.value && passwordCheck.value) {
                if (password.value === passwordCheck.value) {
                    passwordMsg.textContent = "비밀번호가 일치합니다.";
                    passwordMsg.style.color = "green";
                } else {
                    passwordMsg.textContent = "비밀번호가 일치하지 않습니다.";
                    passwordMsg.style.color = "#e63946";
                }
            } else {
                passwordMsg.textContent = "";
            }
        });
    }

    // 폼 제출
    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const email = document.getElementById("email").value.trim();
        const name = document.getElementById("name").value.trim();
        const pw = password.value.trim();
        const pwCheck = passwordCheck.value.trim();
        const phone1 = document.getElementById("phone1").value.trim();
        const phone2 = document.getElementById("phone2").value.trim();
        const phone3 = document.getElementById("phone3").value.trim();

        const pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$/;

        // 필수 입력 체크
        if (!email || !name || !pw || !pwCheck || !phone1 || !phone2 || !phone3) {
            alert("모든 항목을 입력해주세요.");
            return;
        }

        if (!pwPattern.test(pw)) {
            pwInfo.style.color = "#e63946";
            return;
        }

        if (pw !== pwCheck) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        if (!/^\d{3}$/.test(phone1) || !/^\d{4}$/.test(phone2) || !/^\d{4}$/.test(phone3)) {
            alert("전화번호 형식이 올바르지 않습니다.");
            return;
        }

        // 강사 폼일 때 첨부파일 필수 체크
        if (resumeInput && !resumeInput.value) {
            resumeMsg.textContent = "이력서는 반드시 첨부해야 합니다.";
            return;
        } else if (resumeMsg) {
            resumeMsg.textContent = "";
        }

        const phone = `${phone1}-${phone2}-${phone3}`;

        alert(`${name}님, 회원가입이 완료되었습니다.`);
        form.submit();
        form.reset();

        // 초기화 시 안내문 색상 복구
        if (pwInfo) pwInfo.style.color = "#666";
        if (passwordMsg) passwordMsg.textContent = "";
        if (resumeMsg) resumeMsg.textContent = "";
    });
});