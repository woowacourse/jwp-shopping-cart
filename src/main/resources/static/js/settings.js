const selectMember = (account) => {
    <!-- TODO: [2단계] 사용자 정보에 맞게 변경 -->
    const { email, password } = account;
    const string = `${email}:${password}`;
    console.log(string);
    localStorage.setItem('credentials', btoa(string));
    alert(`${email} 사용자로 설정 했습니다.`);
}
