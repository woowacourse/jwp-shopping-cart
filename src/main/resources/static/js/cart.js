const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.request({
<<<<<<< HEAD
<<<<<<< HEAD
        url: '/cart/product',
=======
        url: 'http://localhost:8080/cart/product',
>>>>>>> e07c1629 (feat: 사용자 인증 처리 구현)
=======
        url: '/cart/product',
>>>>>>> 46ded3a7 (feat: 장바구니 상품 삭제)
        method: 'POST',
        headers: {
            'Authorization': `Basic ${credentials}`,
            'Content-type': 'application/json'
        },
        data: productId
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
        url: '/cart/product/' + id,
        method: 'DELETE',
        headers: {
            'Authorization': `Basic ${credentials}`,
            'Content-type': 'application/json'
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
