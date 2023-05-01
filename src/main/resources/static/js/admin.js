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

const form = document.getElementById('product-form');

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

const url = "http://localhost:8080/products";
const createProduct = (product) => {
    axios.post(
        url,
        JSON.stringify({
            name: product.name,
            price: product.price,
            imgUrl: product.imgUrl
        }),
        {
            headers: {'Content-Type': 'application/json'}
        }
    ).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

const updateProduct = (product) => {
    const {id} = product;

    axios.put(url + "/" + id,
        JSON.stringify({
            name: product.name,
            price: product.price,
            imgUrl: product.imgUrl
        }),
        {
            headers: {'Content-Type': 'application/json'}
        }
    ).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

const deleteProduct = (id) => {
    axios.delete(url + "/" + id,
    ).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};
