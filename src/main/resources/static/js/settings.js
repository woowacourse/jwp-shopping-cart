const selectMember = (user) => {
    const { email, password } = user;
    const string = `${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${email} 사용자로 설정 했습니다.`);
}
