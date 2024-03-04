document.addEventListener("DOMContentLoaded", function () {
    // 토큰 가져오기
    const jwtToken = localStorage.getItem("token");

    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${jwtToken}`
    };

    // 서버에 GET 요청 보내기
    fetch('/edit-user', {
        method: 'GET',
        headers: headers
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('유저 정보를 가져오는데 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            // 받아온 데이터를 사용하여 화면 업데이트 등 필요한 작업 수행
            updateUserInfoOnPage(data);
        })
        .catch(error => {
            console.error('유저 정보를 가져오는데 실패했습니다.', error);
        });

    function updateUserInfoOnPage(user) {
        document.getElementById("name").value = user.name;
        document.getElementById("username").value = user.username;
        document.getElementById("birthday").value = user.birthday;
        document.getElementById("email").value = user.email;
        document.getElementById("mobileNumber").value = user.mobileNumber;
    }
});
