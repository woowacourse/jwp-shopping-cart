const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.request({
        url: '/carts',
        method: 'POST',
        data: JSON.stringify(productId),
        headers: {
            'Authorization': `Basic ${credentials}`,
            "Content-Type" : "application/json; charset=utf-8"
        }
    }).then((response) => {
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        console.error(error);
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
        url: '/carts',
        method: "DELETE",
        data: JSON.stringify(id),
        headers: {
            'Authorization': `Basic ${credentials}`,
            "Content-Type" : "application/json; charset=utf-8"
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
