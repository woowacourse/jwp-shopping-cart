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

// TODO: [1단계] 상품 관리 CRUD API에 맞게 변경
const createProduct = (product) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }

    axios.request({
        url: 'admin/products',
        method: 'post',
        data: product,
        headers: {
            'Authorization': `Basic ${credentials}`,
            'Content-Type': "application/json; charset=utf-8"
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

// TODO: [1단계] 상품 관리 CRUD API에 맞게 변경
const updateProduct = (product) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }
    const { id } = product;

    axios.request({

        url: 'admin/products/' + id,
        method: 'put',
        data: product,
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

// TODO: [1단계] 상품 관리 CRUD API에 맞게 변경
const deleteProduct = (id) => {
    const credentials = localStorage.getItem('credentials');
    if (!credentials) {
        alert('사용자 정보가 없습니다.');
        window.location.href = '/settings';
        return;
    }
    axios.request({
        url: 'admin/products/' + id,
        method: 'DELETE',
        headers: {
            'Authorization': `Basic ${credentials}`
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};
