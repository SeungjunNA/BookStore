document.addEventListener('DOMContentLoaded', () => {
    setEventListeners();
    getOrder();
});

let orderItem;

function getOrder() {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('orderId');
    console.log(orderId);
}

