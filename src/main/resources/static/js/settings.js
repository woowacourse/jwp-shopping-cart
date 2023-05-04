const selectMember = (member) => {
    const {id, email, password} = member;
    const string = `${id}:${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${email} 사용자로 설정 했습니다.`);
}
