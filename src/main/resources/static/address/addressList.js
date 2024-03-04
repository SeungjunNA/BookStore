document.addEventListener('DOMContentLoaded', getAddressList);

async function getAddressList() {
    const response = await fetch("/myroom/address");
    const addressList = await response.json();

    const addressListWrap = document.querySelector('.address-list-wrap');
    addressListWrap.innerHTML = '<button type="submit">+ 새 주소록 등록</button>';

    addressList.forEach(address => {
        const isDefaultSpan = address['default'] ? '| <span>기본배송지</span>' : '';
        const addressItemHtml = `
            <div class="address-item-wrap">
                <div class="address-info">
                    <span>${address['addressName']}</span>
                    ${isDefaultSpan}<br>
                    <span>${address['phoneNumber']}</span><br>
                    [<span>${address['zipCode']}</span>]
                    <span>${address['mainAddress']}</span>
                </div>
                <button type="submit">수정</button>
            </div>
        `
        addressListWrap.insertAdjacentHTML('beforeend', addressItemHtml);
    })
}