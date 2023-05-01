const modal = document.getElementById('modal');

const showAddModal = () => {
    modal.dataset.formType = 'add';
    modal.style.display = 'block';
};

const showEditModal = (product) => {
    const elements = modal.getElementsByTagName('input');
    for (const element of elements) {
        element.value = product[element.getAttribute('name')];
    }
    modal.dataset.formType = 'edit';
    modal.dataset.productId = product.id;
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

// TODO: [1단계] 상품 관리 CRUD API에 맞게 변경
const createMember = (member) => {
    const { email, password } = member;
    const string = `${email}:${password}`;
    axios.request({
        method: 'post',
        url: '/register',
        data: {
            email: member.email,
            name: member.name,
            password: member.password,
        }
    }).then((response) => {
        alert(`${email} 으로 로그인해주세요.`)
        hideAddModal();
        window.location.replace("http://localhost:8080/settings");
    }).catch((error) => {
        alert(error.response.data.message);
        console.error(error);
        window.location.reload();
    });
};
