document.addEventListener("DOMContentLoaded", () => {
    const inquiryButton = document.getElementById("inquiryButton");
    const loginButton = document.getElementById("loginButton");

    inquiryButton.addEventListener("click", () => {
        const chatRoomCreateDto = {
            productId: '2', // 예시로 적절한 productId를 설정하세요.
            buyerId: '115',    // 예시로 적절한 buyerId를 설정하세요.
            sellerId: '2'  // 예시로 적절한 sellerId를 설정하세요.
        };

        fetch('/chat/chatRoom', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(chatRoomCreateDto)
        })
            .then(response => {
                if (response.redirected) {
                    window.location.href = response.url;
                } else {
                    alert('Failed to create chat room');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error creating chat room');
            });
    });


    // loginButton.addEventListener("click", async () => {
    //     // const userId = document.getElementById('userId').value;
    //     const formData = new URLSearchParams();
    //     formData.append('userId', 1); // 예시로 입력한 값(DB에 저장되어 있는 값)
    //
    //     try {
    //         const response = await fetch('/auth/login', {
    //             method: 'POST',
    //             headers: {
    //                 'Content-Type': 'application/x-www-form-urlencoded'
    //             },
    //             body: formData.toString()
    //         });
    //
    //         if (response.ok) {
    //             alert('로그인 성공!');
    //             window.location.href = '/'; // 로그인 성공 후 리다이렉션
    //         } else {
    //             alert('로그인 실패');
    //         }
    //     } catch (error) {
    //         console.error('Error during login:', error);
    //         alert('로그인 실패');
    //     }
    // });
});
