const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.request({
        url: '/carts/' + productId,
        method: 'POST',
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        alert("장바구니에 담아지지 않았습니다.")
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
        url: '/carts/' + id,
        method: 'DELETE',
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        alert('해당 상품을 장바구니에서 삭제하였습니다.')
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
