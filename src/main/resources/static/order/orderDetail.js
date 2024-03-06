document.addEventListener('DOMContentLoaded', () => {
    getOrder();
});

let orderItem;

function getOrder() {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('orderId');

    getOrderById(orderId)
}

async function getOrderById(orderId) {
    const response = await fetch(`/myroom/order/${orderId}`);
    const orderItem = await response.json();
    const orderBookList = orderItem['orderBookList'];
    const orderDetailWrap = document.querySelector(".order-detail-wrap");
    orderDetailWrap.innerHTML = '';

    let orderDetailHtml = `
        <div class="order-detail-meta-wrap">
            <span id="order-detail-date">${orderItem['orderDate']}</span>
            <span id="order-detail-number">${orderItem['id']}</span>
        </div>
        <div class="order-detail-book-list-wrap">
           <div class="order-detail-book-wrap">
    `;

    orderBookList.forEach(book => {
        const orderDetailBookListHtml = `
            <div class="order-detail-book-item">
                <div class="order-detail-book">
                    <img src="../images/book.png" alt="책 표지 사진"/>
                    <div id="order-book-info">
                        <p>${book['book']['title']}</p>
                        <span>수량: ${book['stock']}</span>
                    </div>
                </div>
                <div class="order-detail-book-price">
                    <p>가격</p>
                </div>
            </div>    
        `;
        orderDetailHtml += orderDetailBookListHtml;
    });

    const orderDetailDeliverHtml = `
            </div>
             <div class="order-detail-deliver-wrap">
                <p>배송상태</p>
                <p>배송번호</p>
                <button class="order-deliver-button">배송 조회</button>
            </div>
        </div>
    `;
    orderDetailHtml += orderDetailDeliverHtml;

    orderDetailWrap.insertAdjacentHTML('beforeend', orderDetailHtml);
}





