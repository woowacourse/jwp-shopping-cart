const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.request({
        method: 'post',
        url: '/cart',
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        data: {
            "productId": productId
        }
    }).then((response) => {
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        alert('장바구니에 존재하는 제품입니다.');
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
        method: 'delete',
        url: '/cart/' + id,
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
