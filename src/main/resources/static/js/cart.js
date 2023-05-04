const addCartItem = (productId) => {
  const credentials = localStorage.getItem('credentials');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/settings';
    return;
  }

  // TODO: [2단계] 장바구니 CRUD API에 맞게 변경
  axios.request({
    url: '/carts',
    method: 'POST',
    data: {
      productId
    },
    headers: {
      'Authorization': `Basic ${credentials}`,
      'Content-type': 'application/json'
    }
  }).then((response) => {
    alert('장바구니에 담았습니다.');
  }).catch((error) => {
    console.error(error);
    alert(error.response?.data?.message || "예상치 못한 예외가 발생했습니다");
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
    window.location.reload();
  }).catch((error) => {
    console.error(error);
    alert('사용자 정보가 없습니다.');
  });
}
