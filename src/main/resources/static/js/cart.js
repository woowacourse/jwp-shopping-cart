const addCartItem = (productId) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    const addUrl = "http://localhost:8080/carts/" + productId;
    axios.post(addUrl,
        JSON.stringify({
            productId: productId
        }),
        {
            headers: {'Authorization': `Basic ${credentials}}`
        }
    }).then((response) => {
        alert('장바구니에 담았습니다.');
    }).catch((error) => {
        console.error(error);
    });
}

const removeCartItem = (productId) => {
    console.log("dsadsadsa")
    alert("asd");
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
    axios.delete('http://localhost:8080/carts/' + productId, {
        headers: {
            'Authorization': `Basic ${credentials}`
        },
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
}
