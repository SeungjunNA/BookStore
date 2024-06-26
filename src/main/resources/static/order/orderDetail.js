document.addEventListener('DOMContentLoaded', () => {
    getUser();
    getOrder();
});

async function getUser() {
    const jwtToken = localStorage.getItem("token");
    const headers = {
        'Content-Type': 'application/json'
    };
    if (jwtToken !== null){
        headers['Authorization'] = jwtToken;
    }

    const nickname = document.querySelector(".nickname");
    console.log("nickname: " + nickname);
    fetch('/user', {
        method: 'GET',
        headers: headers
    })
        .then(response=>{
            if(!response.ok){
                throw new Error('유저 정보를 가져오는데 실패했습니다.');
            }
            return response.json();
        })
        .then(data=>{
            nickname.textContent = data.name;
        })
        .catch(error=>{
            console.log('유저 정보를 가져오는데 실패했습니다.', error);
        })
}

function getOrder() {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('orderId');

    getOrderById(orderId);
}

async function getOrderById(orderId) {
    const response = await fetch(`/api/order/${orderId}`);
    const orderItem = await response.json();
    const orderBookList = orderItem['orderBookList'];
    const orderDetailWrap = document.querySelector(".order-detail-wrap");
    orderDetailWrap.innerHTML = '';

    const orderDate = orderItem['orderDate'].split('T')[0];
    let orderDetailHtml = `
        <div class="order-detail-meta-wrap">
            <span id="order-detail-date">${orderDate}</span>
            <span id="order-detail-number">(${orderItem['id']})</span>
        </div>
        <div class="order-detail-book-list-wrap">
           <div class="order-detail-book-wrap">
    `;

    let totalPrice = 0;
    orderBookList.forEach(book => {
        const subtotal = book['stock'] * book['book']['price'];
        const bookDetailLink = `/book/bookDetail.html?bookId=${book['book']['id']}`;
        const orderDetailBookListHtml = `
            <div class="order-detail-book-item">
                <div class="order-detail-book">
                    <img src=${book['book']['thumbnailUrl']} alt="책 표지 사진"/>
                    <div id="order-book-info">
                        <p><a href="${bookDetailLink}">${book['book']['title']}</a></p>
                        <span>수량: ${book['stock']}</span>
                    </div>
                </div>
                <div class="order-detail-book-price">
                    <p>${subtotal}</p>
                </div>
            </div>    
        `;
        totalPrice += subtotal;
        orderDetailHtml += orderDetailBookListHtml;
    });

    const orderDetailDeliverStatusHtml = `
            </div>
             <div class="order-detail-deliver-wrap">
                <p>배송상태</p>
                <p>배송번호</p>
                <button class="order-deliver-button">배송 조회</button>
            </div>
        </div>
    `;
    orderDetailHtml += orderDetailDeliverStatusHtml;

    const orderDetailAddressHtml = `
        <div class="order-detail-address-wrap">
            <div class="order-detail-deliver-address">
                <h3>배송정보</h3>
                <p>${orderItem['address']['addressName']} / ${orderItem['address']['phoneNumber']}</p>
                <p>[${orderItem['address']['zipCode']}] ${orderItem['address']['mainAddress']} ${orderItem['address']['subAddress']}</p>
            </div>
            <div class="order-detail-pay">
                <h3>결제정보</h3>
                <div class="order-detail-pay-price">
                    <p>주문 금액</p>
                    <p>${totalPrice}</p>
                </div>
            </div>
        </div>
    `;
    orderDetailHtml += orderDetailAddressHtml;

    orderDetailWrap.insertAdjacentHTML('beforeend', orderDetailHtml);
}





