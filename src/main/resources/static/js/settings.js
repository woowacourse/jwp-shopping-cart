const selectMember = (member) => {
    <!-- TODO: [2단계] 사용자 정보에 맞게 변경 -->
    const { email, password, name } = member;
    const string = `${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${name} 님 반갑습니다!`);
}

const showAddModal = () => {
    modal.dataset.formType = 'add';
    modal.style.display = 'block';
};

form.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    let member = {};
    for (const entry of formData.entries()) {
        const [key, value] = entry;
        member[key] = value;
    }

    createMember(member);
});

const createMember = (member) => {
    const data = {
        email: member.email,
        password: member.password,
        name: member.name
    }
    axios.post(
        '/users',
        JSON.stringify(data), {
            headers: {
                "Content-Type": `application/json`
            }
        })
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
        console.error(error);
    });
};
