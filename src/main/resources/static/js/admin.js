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

const handleException = (e) => {
    console.error(e);
    alert(e.message);
}

const createProduct = (product) => {
    axios.post('/products', product)
        .then(() => window.location.reload())
        .catch(handleException);
};

const updateProduct = (product) => {
    const {id} = product;

    axios.put(`/products/${id}`, product)
        .then(() => window.location.reload())
        .catch(handleException);
};

const deleteProduct = (id) => {
    axios.delete(`/products/${id}`)
        .then(() => window.location.reload())
        .catch(handleException);
};
