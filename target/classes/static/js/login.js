document.addEventListener("DOMContentLoaded", () => {
  const loginBtn = document.getElementById("loginBtn");

  loginBtn.addEventListener("click", (e) => {
    e.preventDefault();

    const selectedRole = document.querySelector('input[name="role"]:checked');
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    // --- 검증 단계 ---
    if (!selectedRole) {
      alert("수강생 또는 강사를 선택해주세요.");
      return;
    }

    if (email === "" || password === "") {
      alert("이메일과 비밀번호를 모두 입력해주세요.");
      return;
    }

    // 이메일 기본 검증
    const emailPattern = /^[^@]+@[^@]+\.[a-zA-Z]{2,}$/;
    if (!emailPattern.test(email)) {
      alert("올바른 이메일 형식이 아닙니다.");
      return;
    }

    // 로그인 요청 (모의)
    // 실제 연결 시: fetch("/login", { method:"POST", body: JSON.stringify({email, password, role}) })
    if (email === "test@smartedu.com" && password === "1234") {
      alert(`${selectedRole.value === "student" ? "수강생" : "강사"}님 환영합니다!`);
      location.href = "index.html"; // 로그인 성공 후 메인으로
    } else {
      alert("이메일 또는 비밀번호가 올바르지 않습니다.");
    }
  });
});