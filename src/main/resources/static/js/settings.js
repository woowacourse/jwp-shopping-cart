const modal = document.getElementById('modal');

const showAddModal = () => {
    modal.dataset.formType = 'add';
    modal.style.display = 'block';
};

const showEditModal = (member) => { //
    const elements = modal.getElementsByTagName('input');
    for (const element of elements) {
        element.value = member[element.getAttribute('name')]; //
    }
    modal.dataset.formType = 'edit';
    modal.dataset.memberId = member.id; //
    modal.style.display = 'block';
};

const hideAddModal = () => {
    modal.style.display = 'none';
    const elements = modal.getElementsByTagName('input');
    for (const element of elements) {
        element.value = '';
    }
};

const form = document.getElementById('form');

form.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    let member = {};
    for (const entry of formData.entries()) {
        const [key, value] = entry;
        member[key] = value === '' ? null : value;
    }

    if (modal.dataset.formType === 'edit') {
        member['id'] = modal.dataset.memberId; //
        updateMember(member); //
        return;
    }

    createMember(member); //
});

const createMember = (member) => { //
    axios.request({
        method: 'post',
        url: '/members',
        data: member
    }
    ).then((response) => {
        window.location.reload();
    }).catch((error) => {
        const {data} = error.response;
        console.log(data);
        window.alert(data.message)
    });
};

// 상품 업데이
const updateMember = (member) => {
    const { id } = member; //왜???

    axios.request({
        method: 'put',
        url: '/members/' + id,
        data: member
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        const {data} = error.response;
        console.log(data);
        window.alert(data.message)
    });
};

// 상품 삭제
const deleteMember = (id) => {
    axios.request({
        method: 'delete',
        url: '/members/' + id,
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};



//~여기부터 원래 파일~
const selectMember = (member) => {
    <!-- TODO: [2단계] 사용자 정보에 맞게 변경 -->
    const { email, password } = member;
    const string = `${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${email} 사용자로 설정 했습니다.`);
}
