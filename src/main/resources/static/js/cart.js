const cartBaseUrl = 'http://localhost:8080/user/cartItems';
const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.post(cartBaseUrl,
        JSON.stringify({
            productId: +productId
        }),
        {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Basic ${credentials}`,
            }
        }
    ).then((response) => {
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
    axios.delete(
        cartBaseUrl + `/${id}`,
        {
            headers: {
                'Authorization': `Basic ${credentials}`,
            }
        }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
