const selectMember = (member) => {
    const {email, password} = member;
    const string = `${email}:${password}`;
    localStorage.setItem('credentials', Base64Encode(string));
    alert(`${email} 사용자로 설정 했습니다.`);
}

function Base64Encode(str) {
    let bytes = new (TextEncoder || TextEncoderLite)('utf-8').encode(str);
    return base64js.fromByteArray(bytes);
}
