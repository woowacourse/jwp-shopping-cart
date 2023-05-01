const modal = document.getElementById('modal');

const showAddModal = () => {
    modal.dataset.formType = 'add';
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

    createMember(member);
});

const createMember = (member) => {
    axios.request({
        url: '/member/register',
        data: member,
        method: 'post'
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

const selectMember = (member) => {
    <!-- TODO: [2단계] 사용자 정보에 맞게 변경 -->
    const { email, password } = member;
    const string = `${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${email} 사용자로 설정 했습니다.`);
}
