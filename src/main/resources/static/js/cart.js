const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }
    console.log(productId);
    axios.request({
        url: `/cart/products/${productId}`,
        method: 'POST',
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        console.log(response);
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        console.error(error);
    });
}

const removeCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.request({
        url: `/cart/products/${productId}`,
        method: 'DELETE',
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        data: {
            'id': productId,
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
