const selectMember = (member) => {
    <!-- TODO: [2단계] 사용자 정보에 맞게 변경 -->
    const {name, email, password} = member;
    const string = `${name}:${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`이름: ${name} 이메일: ${email}의 사용자로 설정 했습니다.`);
}
