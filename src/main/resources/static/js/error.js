function alertError(error) {
    if (error.response && error.response.data && error.response.data.message) {
        alert(error.response.data.message);
    } else {
        alert(error)
    }
}
