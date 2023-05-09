const selectMember = (member) => {
    const {username, password} = member;
    const string = `${username}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${username} 사용자로 설정 했습니다.`);
    window.location.href = '/';
}
