const selectMember = (member) => {
    <!-- TODO: [2단계] 사용자 정보에 맞게 변경 -->
    const {email, password} = member;
    const string = `${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${email} 사용자로 설정 했습니다.`);
}

const showAddModal = () => {
    modal.dataset.formType = 'add';
    document.getElementById("email").disabled = false;
    modal.style.display = 'block';
};

const showEditModal = (member) => {
    const elements = modal.getElementsByTagName('input');
    for (const element of elements) {
        element.value = member[element.getAttribute('name')];
    }
    modal.dataset.formType = 'edit';
    modal.dataset.email = member.email;
    document.getElementById("email").disabled = true;
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

    if (modal.dataset.formType === 'edit') {
        member['email'] = modal.dataset.email;
        updateProduct(member);
        return;
    }

    createMember(member);
});

const createMember = (member) => {
    axios.post("member", member)
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
        console.error(error);
    });
};

const updateProduct = (member) => {
    axios.put("/member", member
    ).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

const deleteMember = (email) => {
    axios.delete("/member/" + email)
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
        console.error(error);
    });
}

const hideAddModal = () => {
    modal.style.display = 'none';
    const elements = modal.getElementsByTagName('input');
    for (const element of elements) {
        element.value = '';
    }
}
