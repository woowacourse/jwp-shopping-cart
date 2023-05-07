const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    const headers = {
        'Authorization': `Basic ${credentials}`
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.post("/cart", {
        productId: productId
    }, {
        headers
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

    const headers = {
        'Authorization': `Basic ${credentials}`
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.delete(`/cart/${id}`, {
        headers
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
