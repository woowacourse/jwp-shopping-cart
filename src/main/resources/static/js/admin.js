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

const createProduct = (product) => {
    try {
        validImage(product.image)
        axios.request({
            url: '/products',
            method: "POST",
            data: product
        }).then((response) => {
            window.location.reload();
        }).catch((error) => {
            let errorMessages = '';
            for (let message of error.response.data.messages) {
                errorMessages += (message + '\n');
            }
            alert(errorMessages)
        });
    } catch (error) {
        alert(error.message)
    }
};

const updateProduct = (product) => {
    try {
        validImage(product.image)
        const {id} = product;

        axios.request({
            url: '/products/' + id,
            method: "PUT",
            data: product
        }).then((response) => {
            window.location.reload();
        }).catch((error) => {
            alert(error.response.data.messages)
        });
    } catch (error) {
        alert(error.message)
    }
};

const validImage = (imageUrl) => {
    const regex = /((([A-Za-z]{3,9}:(?:\/\/)?)(?:[-;:&=\+\$,\w]+@)?[A-Za-z0-9.-]+(:[0-9]+)?|(?:www.|[-;:&=\+\$,\w]+@)[A-Za-z0-9.-]+)((?:\/[\+~%\/.\w-_]*)?\??(?:[-\+=&;%@.\w_]*)#?(?:[\w]*))?)/;
    if (!imageUrl.match(regex)) {
        throw new Error("유효하지 않은 Url 입니다.");
    }
};

const deleteProduct = (id) => {
    axios.request({
        url: '/products/' + id,
        method: "DELETE"
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        alert(error.response.data.messages)
    });
};
