const selectMember = (member) => {
    <!-- TODO: [2단계] 사용자 정보에 맞게 변경 -->
    const { email, password, name } = member;
    const string = `${email}:${password}`;
    localStorage.setItem('credentials', btoa(string));
    alert(`${name} 님 반갑습니다!`);
}

const signUpModal = document.getElementById('signUp-modal');

const showSignUpAddModal = () => {
    signUpModal.dataset.formType = 'add';
    signUpModal.style.display = 'block';
};

const hideSignUpAddModal = () => {
    signUpModal.style.display = 'none';
    const elements = signUpModal.getElementsByTagName('input');
    for (const element of elements) {
        element.value = '';
    }
}

const signUpForm = document.getElementById('signUp-form');

signUpForm.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    console.log(formData);
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
        '/users/signUp',
        JSON.stringify(data), {
            headers: {
                "Content-Type": `application/json`
            }
        })
        .then((response) => {
            window.location.reload();
        }).catch((error) => {
            alert(error.response.data);
            window.location.reload();
    });
};

const loginModal = document.getElementById('login-modal');

const showLoginAddModal = () => {
    loginModal.dataset.formType = 'add';
    loginModal.style.display = 'block';
};

const hideLoginAddModal = () => {
    loginModal.style.display = 'none';
    const elements = loginModal.getElementsByTagName('input');
    for (const element of elements) {
        element.value = '';
    }
}

const loginForm = document.getElementById('login-form');

loginForm.addEventListener('submit', (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    console.log(formData);
    let member = {};
    for (const entry of formData.entries()) {
        const [key, value] = entry;
        member[key] = value;
    }

    loginMember(member);
});

const loginMember = (member) => {
    const data = {
        email: member.email,
        password: member.password
    }
    axios.post(
        '/users/login',
        JSON.stringify(data), {
            headers: {
                "Content-Type": `application/json`
            }
        })
        .then((response) => {
            selectMember(response.data);
            window.location.reload();
        }).catch((error) => {
        alert(error.response.data);
        window.location.reload();
    });
};
