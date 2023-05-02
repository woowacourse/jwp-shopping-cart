const modal = document.getElementById('modal');

const showAddModal = () => {
    modal.dataset.formType = 'add';
    modal.style.display = 'block';
};

const showEditModal = (member) => {
    const elements = modal.getElementsByTagName('input');
    // for (const element of elements) {
    //     element.value =element.getAttribute('password');
    // }
    modal.dataset.formType = 'edit';
    modal.dataset.email = member.email;
    modal.dataset.name = member.name;
    modal.style.display = 'block';
};

const hideAddModal = () => {
    modal.style.display = 'none';
    const elements = modal.getElementsByTagName('input');
    for (const element of elements) {
        element.value = '';
    }
}

const form = document.getElementById('form');

form.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    let member = {};
    for (const entry of formData.entries()) {
        const [key, value] = entry;
        member[key] = value;
    }
    if (modal.dataset.formType === 'edit') {
        member['email'] = modal.dataset.email;
        member['name'] = modal.dataset.name;
    }
    createMember(member);
});

const createMember = (member) => {
    const { email, password } = member;
    const string = `${email}:${password}`;
    axios.request({
        method: 'post',
        url: '/settings',
        data: {
            email: member.email,
            name: member.name,
            password: member.password,
        }
    }).then((response) => {
        const email = response.data.email;
        const password = response.data.password;
        const string = `${email}:${password}`;
        alert(`${email} 사용자로 설정 했습니다.`);
        hideAddModal();
        localStorage.setItem('credentials', btoa(string));
        window.location.replace("http://localhost:8080/");
        axios.headers.Authorization
    }).catch((error) => {
        alert(error.response.data.message);
        console.error(error);
        window.location.reload();
    });
};
