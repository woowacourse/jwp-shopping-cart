const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }
    var requestData = {
        'productId': productId
    }
    axios.request({
        url: '/carts',
        method: 'post',
        data: JSON.stringify(requestData),
        headers: {
            'Authorization': `Basic ${credentials}`,
            'Content-Type': `application/json`
        }
    }).then((response) => {
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        console.error(error);
        alert(error.response.data.errors);
    });
}

const updateCartItem = (cartId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }
    console.log(cartId);

    let count = document.getElementById(cartId).value;
    console.log(count);
    var requestData = {
        'count': count
    }
    axios.request({
        url: '/carts/' + cartId.replace('itemCount', ''),
        method: 'put',
        data: JSON.stringify(requestData),
        headers: {
            'Authorization': `Basic ${credentials}`,
            'Content-Type': `application/json`
        }
    }).then((response) => {
        alert('수정되었습니다.');
        window.location.reload();
    }).catch((error) => {
        console.error(error);
        alert(error.response.data.errors);
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
        url: '/carts/' + id,
        method: 'delete',
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}

const changeToInput = (id) => {
    console.log(id);
    let element = document.getElementById(id);
    element.setAttribute("type", "number");
    element.removeAttribute('onclick');

    let button = document.createElement('button');

    button.setAttribute('type', 'button');
    button.setAttribute('id', 'update-btn');
    button.setAttribute('class', 'cart-item-update');
    button.setAttribute('onclick', `updateCartItem('${id}')`);
    button.innerHTML = 'Update';

    element.after(
        button
    );
}
