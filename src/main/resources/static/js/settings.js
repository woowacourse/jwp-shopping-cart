const selectMember = (member) => {
    const {email, password, name} = member;
    const string = `${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${name} 사용자로 설정 했습니다.`);
}
