const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.request({
        method: 'post',
        url: `/cartitems/${productId}`,
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        alert('장바구니에 담았습니다.');

    }).catch((error) => {
        alert(error.response.data.message);
        console.error(error.response);
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
        method: 'delete',
        url: `/cartitems/${id}`,
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        alert(error.response.data.message);
        console.error(error);
    });
}
