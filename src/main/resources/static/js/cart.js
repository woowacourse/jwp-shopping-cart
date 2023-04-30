const showDetail = (productId) => {
    window.location.href = `/${productId}`
}

const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.request({
        url: `/cart/${productId}`,
        method: 'POST',
        headers: {
            'Authorization': `Basic ${credentials}`
        },
    }).then((response) => {
        alert('장바구니에 담았습니다.');
        window.location.href = '/cart'
    }).catch((error) => {
        console.error(error);
    });
}

const removeCartItem = (memberId, productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.request({
        url: `/cart/${memberId}/${productId}`,
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
