const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    const member_id = localStorage.getItem('member_id');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.request({
        method: 'post',
        url: '/carts/' + member_id,
        data: {
            productId: productId
        },
        headers: {
            'Authorization': `Basic ${credentials}`
        },
    }).then((response) => {
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        console.error(error);
    });
}

const removeCartItem = (id) => {
    const credentials = localStorage.getItem('credentials');
    const member_id = localStorage.getItem('member_id');

    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.request({
        method: 'delete',
        url: '/carts/' + member_id,
        params: {
            productId: parseInt(id),
        },
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
