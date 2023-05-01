const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    console.log(`productId is ${productId}`)
    const data = {
        "productId": productId
    };

    axios.request({
        url: `/cart/items`,
        method: 'POST',
        headers: {
            'Authorization': `Basic ${credentials}`
        },
        data: data,
    }).then((response) => {
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        alert('담기에 실패했습니다');
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
        url: `/cart/items/${id}`,
        method: 'DELETE',
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
