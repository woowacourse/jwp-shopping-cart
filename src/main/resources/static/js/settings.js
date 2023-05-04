const selectMember = (member) => {
    const { id, email, password } = member;
    const string = `${email}:${password}`;
    const member_id = `${id}`;
    localStorage.setItem('member_id', member_id);
    localStorage.setItem('credentials', btoa(string));
    alert(`${email} 사용자로 설정 했습니다.`);
}
