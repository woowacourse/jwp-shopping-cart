const selectMember = (member) => {
    <!-- TODO: [2단계] 사용자 정보에 맞게 변경 -->
    const {email, password} = member;
    const string = `${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${email} 사용자로 설정 했습니다.`);
}

function showAddModal() {
    const modal = document.getElementById("modal");
    modal.style.display = "block";
}

function hideAddModal() {
    const modal = document.getElementById("modal");
    modal.style.display = "none";
}


form.addEventListener('submit', (event) => {
    event.preventDefault();
    const formData = new FormData(event.target);
    let user = {};

    for (const entry of formData.entries()) {
        const [key, value] = entry;
        user[key] = value;
    }

    createUser(user);
});


const createUser = (user) => {
    axios.request({
        method: 'post',
        url: '/member',
        data: {
            email: user.email,
            password: user.password
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        window.alert(error.response.data)
        console.error(error);
    });
};
