const selectMember = (member) => {
    const {email, password} = member;
    const token = `${email}:${password}`;
    localStorage.setItem('credentials', btoa(token));
    alert(`${email} 사용자로 설정 했습니다.`);

    axios.request({
        url: `/`,
        method: 'GET',
        headers: {
            'Authorization': `Basic ${token}`
        },
    }).then((response) => {
        window.location.href = '/'
    }).catch((error) => {
        console.error(error);
    });
}
