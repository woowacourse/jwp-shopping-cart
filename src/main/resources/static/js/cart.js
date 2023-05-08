const handleException = (e) => {
    console.error(e);
    const applicationMessage = e.response?.data?.messages;
    if (applicationMessage) {
        alert(applicationMessage);
        return;
    }
    alert(e.message);
}

const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    const config = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Basic ${credentials}`
        }
    };

    axios.post('/cart/items', {productId}, config)
        .then(() => alert('장바구니에 담았습니다.'))
        .catch(handleException);
}

const removeCartItem = (id) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    const config = {
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    };
    axios.delete(`/cart/items/${id}`, config)
        .then(() => window.location.reload())
        .catch(handleException);
}
