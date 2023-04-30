const addCartItem = (product_id) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.request({
        url: '/carts',
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        method: 'POST',
        data: {
            'product_id': product_id
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

    axios.request({
        url: '/carts',
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        method: 'DELETE',
        data: {
            'product_id': id
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
