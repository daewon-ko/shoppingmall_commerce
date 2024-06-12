document.addEventListener("DOMContentLoaded", () => {
    const inquiryButton = document.getElementById("inquiryButton");

    inquiryButton.addEventListener("click", () => {
        const chatRoomCreateDto = {
            sellerId: '1',  // 예시로 적절한 sellerId를 설정하세요.
            buyerId: '2',    // 예시로 적절한 buyerId를 설정하세요.
            productId: '1' // 예시로 적절한 productId를 설정하세요.
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
});
