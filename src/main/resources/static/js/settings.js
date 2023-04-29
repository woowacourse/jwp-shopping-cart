const selectMember = (member) => {
    axios.post('/users/sign-in', {
        email : member.email,
        password: member.password
    }).then((response) => {
        console.log(response.data.basic);
        localStorage.setItem('credentials', response.data.basic);
        alert(`${member.email} 사용자로 설정 했습니다.`);
    }).catch((error) => {
        console.log(error);
        alert(error.response.data.message);
        console.error(error);
    });
}
