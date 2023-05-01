const selectMember = (member) => {
    <!-- TODO: [2단계] 사용자 정보에 맞게 변경 -->
    const { email } = member;
    const string = `${email}`;
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

document.getElementById("form").addEventListener("submit", function (event) {
    event.preventDefault();
    console.log("form submit event")
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const phoneNumber = document.getElementById("phoneNumber").value;

    axios.post('/members', {
        email: email,
        password: password,
        phoneNumber: phoneNumber
    })
        .then(function (response) {
            window.location.reload();
        })
        .catch(function (error) {
            console.error(error);
            alert(error)
        });
});

