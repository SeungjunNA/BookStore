document.addEventListener('DOMContentLoaded', getAddressList);

let defaultAddress;

function getDefaultAddress(defaultAddress) {
    const defaultAddressWrap = document.querySelector('.default-address-wrap');
    defaultAddressWrap.innerHTML = '';

    const defaultAddressHtml = `
        <p>배송주소록</p>
        <div class="address-info">
            <span>기본배송지</span> |
            <span>${defaultAddress['addressName']}</span><br>
            <span>${defaultAddress['phoneNumber']}</span><br>
            [<span>${defaultAddress['zipCode']}</span>]
            <span>${defaultAddress['mainAddress']}</span>
        </div>
    `

    defaultAddressWrap.insertAdjacentHTML('beforeend', defaultAddressHtml);
}

async function getAddressList() {
    const response = await fetch("/myroom/address");
    const addressList = await response.json();

    const addressListWrap = document.querySelector('.address-list-wrap');
    addressListWrap.innerHTML = '';

    addressList.forEach(address => {
        let isDefaultSpan;
        let defaultAddressBtn;

        if (address['default'] === true) {
            defaultAddress = address;
            isDefaultSpan = '| <span>기본배송지</span>';
            defaultAddressBtn = '';
        } else {
            isDefaultSpan = '';
            defaultAddressBtn = '<button class="set-default-address-btn" type="submit">기본 배송지로 설정</button>';
        }

        const addressItemHtml = `
            <div class="address-item-wrap">
                <div class="address-info">
                    <span>${address['addressName']}</span>
                    ${isDefaultSpan}<br>
                    <span>${address['phoneNumber']}</span><br>
                    [<span>${address['zipCode']}</span>]
                    <span>${address['mainAddress']}</span>
                </div>
                <div class="address-item-button-wrap">
                    ${defaultAddressBtn}
                    <button class="edit-address-btn" type="submit">수정</button>
                </div>
            </div>
        `
        addressListWrap.insertAdjacentHTML('beforeend', addressItemHtml);
    })

    getDefaultAddress(defaultAddress);
}

async function addAddress(address){
    const response = await fetch("/myroom/address", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(address)
    })

    const createdAddress = await response.json();
    console.log(createdAddress.addressName);
    location.reload();
}

const modal = document.querySelector(".modal");
const submitBtn = document.querySelector("#submitAddress");
const addAddressButton = document.querySelector(".add-address-btn");
addAddressButton.addEventListener('click', () => {
    modal.style.display = "block";
});

submitBtn.addEventListener('click',  () => {
    const address = {
        addressName: document.getElementById("addressName").value,
        phoneNumber: document.getElementById("phoneNumber").value,
        mainAddress: document.getElementById("mainAddress").value,
        subAddress: document.getElementById("subAddress").value,
        zipCode: document.getElementById("zipCode").value
    }

    addAddress(address);
    modal.style.display = "none";
});