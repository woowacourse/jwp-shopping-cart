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
const error_name = document.getElementById('error_name');
const error_price = document.getElementById('error_price');
const error_image = document.getElementById('error_image');

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
    axios.request({
        url: '/products',
        method: 'post',
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        },
        data: product
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        showAllError(error.response.data.validation);
        console.error(error);
    });
};

const updateProduct = (product) => {
    const {id} = product;

    axios.request({
        url: '/products/' + id,
        method: 'put',
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        },
        data: product
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        showAllError(error.response.data.validation);
        console.error(error);
    });
};

const deleteProduct = (id) => {
    axios.request({
        url: '/products/' + id,
        method: 'delete',
        headers: {
            "Content-Type": "application/json;charset=UTF-8"
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        showAllError(error.response.data.validation);
        console.error(error);
    });
};

const showError = (component, message) => {
    if (!message) {
        message = "";
    }
    component.innerHTML = message;
}

const showAllError = (error) => {
    showError(error_name, error.name);
    showError(error_price, error.price);
    showError(error_image, error.imageUrl);
}
