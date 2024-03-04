document.addEventListener("DOMContentLoaded", function (){
    const loginForm = document.getElementById("loginUserForm");

    loginForm.addEventListener("submit", function (event){
        event.preventDefault();

        const formData = {
            username: document.getElementById("username").value,
            password: document.getElementById("password").value
        };

        // 서버에 로그인 요청 보내기
        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
            .then(response => {
                if (!response.ok){
                    throw new Error(response.json());
                }
                localStorage.setItem("token", response.headers.get("Authorization"));
                response.json();
            })
            .then(data => {
                console.log("로그인 성공 : " + data);
                window.location.href = "../edit-user/edit-user.html";
            })
            .catch(error => {
                console.log("로그인 실패 : " + error);
            });
    })
})