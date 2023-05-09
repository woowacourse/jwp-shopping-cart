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
    modal.dataset.productId = product.productId;
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
        product['productId'] = modal.dataset.productId;
        updateProduct(product);
        return;
    }

    createProduct(product);
});

const createProduct = (product) => {
    axios.request({
        method: 'post',
        url: '/products',
        data: product,
        headers: {
            'Content-Type': 'application/json',
        },
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

const updateProduct = (product) => {
    const {productId} = product;

    axios.request({
        method: 'patch',
        url: `/products/${productId}`,
        data: product,
        header: {
            'Content-Type': 'application/json',
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

const deleteProduct = (productId) => {
    axios.request({
        method: 'delete',
        url: `/products/${productId}`,
        header: {
            'Content-Type': 'application/json',
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};
