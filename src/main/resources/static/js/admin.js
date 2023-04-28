const modal = document.getElementById('modal');

const showAddModal = () => {
    const categoriesInfo = modal.getElementsByClassName("category");
    for (const categoryInfo of categoriesInfo) {
        categoryInfo.removeAttribute("checked");
    }
    modal.dataset.formType = 'add';
    modal.style.display = 'block';
};

const showEditModal = (product, categories) => {
        const productInfos = modal.getElementsByClassName("productInfo");
        for (const productInfo of productInfos) {
            productInfo.value = product[productInfo.getAttribute('name')];
        }
        const categoriesInfo = modal.getElementsByClassName("category");
        for (const category of categoriesInfo) {
            let currentCategory = categories[category.getAttribute('name') - 1];
            category.value = currentCategory.id;
            if (product.categoryNames.includes(currentCategory.name)) {
                category.setAttribute("checked", "checked");
            }
        }

        modal.dataset.formType = 'edit';
        modal.dataset.productId = product.id;
        modal.style.display = 'block';
    }
;

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
    const {name, imageUrl, price, description, ...categoryIds} = product;

    axios.request({
        url: 'http://localhost:8080/products',
        method: 'post',
        data: {
            "name": name,
            "imageUrl": imageUrl,
            "price": price,
            "description": description,
            "categoryIds": Object.values(categoryIds).map(Number)
        }
    }).then((response) => {
        window.location = "/admin"
    }).catch((error) => {
        console.error(error);
    });
};

// TODO: [1단계] 상품 관리 CRUD API에 맞게 변경
const updateProduct = (product) => {
    const {id, name, imageUrl, price, description, ...categoryIds} = product;

    axios.request({
        url: 'http://localhost:8080/products/' + id,
        method: 'put',
        data: {
            "name": name,
            "imageUrl": imageUrl,
            "price": price,
            "description": description,
            "categoryIds": Object.values(categoryIds).map(Number)
        }
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};

// TODO: [1단계] 상품 관리 CRUD API에 맞게 변경
const deleteProduct = (id) => {
    axios.request({
        url: 'http://localhost:8080/products/' + id,
        method: 'delete',
    }).then((response) => {
        window.location.reload();
    }).catch((error) => {
        console.error(error);
    });
};
