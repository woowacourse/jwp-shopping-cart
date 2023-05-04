const addCartItem = (productId) => {
  const credentials = localStorage.getItem('credentials');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/settings';
    return;
  }

  console.log(productId)
  axios.request({
    method: 'post',
    url: `/api/cart/${productId}`,
    headers: {
      'Authorization': `Basic ${credentials}`
    }
  }).then((response) => {
    alert('장바구니에 담았습니다.');
  }).catch((error) => {
    console.error(error);
  });
}

const removeCartItem = (productId) => {
  const credentials = localStorage.getItem('credentials');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/settings';
    return;
  }

  axios.request({
    method: 'delete',
    url: `/api/cart/${productId}`,
    headers: {
      'Authorization': `Basic ${credentials}`
    }
  }).then((response) => {
    window.location.reload();
  }).catch((error) => {
    console.error(error);
  });
}
