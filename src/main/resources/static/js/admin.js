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
    axios.request({
        url: '/admin/product',
        method: 'POST',
        data: product
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

// TODO: [1단계] 상품 관리 CRUD API에 맞게 변경
const updateProduct = (product) => {
    const { id } = product;

    axios.request({
        url: `/admin/product/${id}`,
        method: 'PATCH',
        data: product
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

// TODO: [1단계] 상품 관리 CRUD API에 맞게 변경
const deleteProduct = (id) => {
    axios.request({
        url: `/admin/product/${id}`,
        method: 'DELETE',
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};
