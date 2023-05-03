const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }
    console.log(credentials);

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.request({
        url: '/carts',
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        method: 'POST',
        data: {
            'productId': productId
        }
    }).then((response) => {
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        console.error(error);
        alert(error.response.data.message);
    });
}

const removeCartItem = (id) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.request({
        url: '/carts/' + id,
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        method: "delete"
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
