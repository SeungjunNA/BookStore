document.addEventListener("DOMContentLoaded", function (){
    const jwtToken = localStorage.getItem("token");
    const headers = {
        'Content-Type': 'application/json'
    };
    if (jwtToken !== null){
        headers['Authorization'] = jwtToken;
    }
    const name = document.getElementById("name");
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
            name.textContent = data.name;
        })
        .catch(error=>{
            console.log('유저 정보를 가져오는데 실패했습니다.', error);
        })

    const orderList = document.getElementById("orderList");

    fetch('/api/order/userOrder', {
        method: 'GET',
        headers: headers
    })
        .then(response=>{
            if(!response.ok){
                return response.text();
            }
            return response.json()
        })
        .then(data=>{
            if(data === "주문한 상품이 없습니다."){
                orderList.innerHTML = '<p style="text-align: center">주문한 내역이 없습니다.</p>';
            }
            console.log(data);
        }).catch(error=>{
            console.log(error);
    })
})