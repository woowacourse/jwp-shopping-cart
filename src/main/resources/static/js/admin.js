const modal = document.getElementById('modal');

const showAddModal = () => {
  modal.dataset.formType = 'add';
  modal.style.display = 'block';
};

const showEditModal = (product) => {
  const elements = modal.getElementsByTagName('input');
  for (const element of elements) {
    element.value = product[element.getAttribute('name')];
  }

  const categorySelect = modal.querySelector('select[name="category"]');
  for (let i = 0; i < categorySelect.options.length; i++) {
    if (categorySelect.options[i].value === product.category) {
      categorySelect.selectedIndex = i;
      break;
    }
  }

  modal.dataset.formType = 'edit';
  modal.dataset.productId = product.id;
  modal.style.display = 'block';
};

const hideAddModal = () => {
  modal.style.display = 'none';
  const elements = modal.getElementsByTagName('input');
  for (const element of elements) {
    element.value = '';
  }
}

const form = document.getElementById('form');

form.addEventListener('submit', (event) => {
  event.preventDefault();

  const formData = new FormData(event.target);
  let product = {};
  for (const entry of formData.entries()) {
    const [key, value] = entry;
    product[key] = value;
  }

  if (modal.dataset.formType === 'edit') {
    product['id'] = modal.dataset.productId;
    updateProduct(product);
    return;
  }

  createProduct(product);
});

const createProduct = (product) => {
  const credentials = localStorage.getItem('credentials');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/settings';
    return;
  }
  axios.request({
    url: `/admin/register`,
    method: 'POST',
    data: product,
    headers: {
      'Authorization': `Basic ${credentials}`
    }
  }).then((response) => {
    window.location.reload();
  }).catch((error) => {
    const {data} = error.response;
    window.alert(data.errorMessage)
  });
};

const updateProduct = (product) => {
  const {id} = product;
  const credentials = localStorage.getItem('credentials');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/settings';
    return;
  }

  axios.request({
    url: `/admin/${id}`,
    method: 'PUT',
    data: product,
    headers: {
      'Authorization': `Basic ${credentials}`
    }
  }).then((response) => {
    window.location.reload();
  }).catch((error) => {
    const {data} = error.response;
    window.alert(data.errorMessage)
  });
};

const deleteProduct = (id) => {
  const credentials = localStorage.getItem('credentials');
  if (!credentials) {
    alert('사용자 정보가 없습니다.');
    window.location.href = '/settings';
    return;
  }

  axios.request({
    url: `/admin/${id}`,
    method: 'DELETE',
    headers: {
      'Authorization': `Basic ${credentials}`
    }
  }).then((response) => {
    window.location.reload();
  }).catch((error) => {
    const {data} = error.response;
    window.alert(data.errorMessage)
  });
};
