document.addEventListener("DOMContentLoaded", () => {
    const chatWindow = document.getElementById("chatWindow");
    const messageInput = document.getElementById("messageInput");
    const sendButton = document.getElementById("sendButton");

    if (!chatWindow || !messageInput || !sendButton) {
        console.error("중요 요소 중 하나가 제대로 할당되지 않았습니다.");
        return;
    }

    let currentPage = 0;
    let isLoading = false;

    const socket = new WebSocket(`ws://localhost:8080/ws/chatRoom/${roomId}`);

    // 테스트 메시지 추가 - 스크롤바 생성 유도
    // for (let i = 0; i < 100; i++) {
    //     const messageElement = document.createElement("div");
    //     messageElement.classList.add("message");
    //     messageElement.textContent = `Test Message ${i + 1}`;
    //     chatWindow.appendChild(messageElement);
    // }

    socket.onopen = () => {
        console.log("WebSocket 연결이 성공적으로 열렸습니다.");
        loadMessages(currentPage,50);

        // 입장 메시지 전송
        const enterMessage = {
            messageType: "ENTER",
            senderId: senderId,
            senderType: currentUserRole,
            content: currentUserRole + "님께서 채팅방에 입장하셨습니다."
        };
        socket.send(JSON.stringify(enterMessage));
    };



    sendButton.addEventListener("click", () => {
        const message = messageInput.value.trim();
        if (message !== "") {
            const chatMessage = {
                messageType: "TALK",
                senderId: senderId,
                senderType: currentUserRole,
                content: message
            };
            // displayMessage(currentUserRole, message, true);
            socket.send(JSON.stringify(chatMessage));
            messageInput.value = "";
        }
    });

    socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        const isOwnMessage = message.senderId === senderId;
        displayMessage(message.senderType, message.content, isOwnMessage);
    };


    messageInput.addEventListener("keyup", (event) => {
        if (event.key === "Enter") {
            sendButton.click();
        }
    });

    chatWindow.addEventListener("scroll", () => {
        console.log("Scrolling...", chatWindow.scrollTop);
        if (chatWindow.scrollTop <= 100 && !isLoading) {
            console.log("Loading more messages...");
            isLoading = true;
            currentPage++;
            loadMessages(currentPage,20);
        }
    });

    function loadMessages(page, size ) {
        console.log("Loading messages for page:", page);
        fetch(`/api/chat/chatRoom/${roomId}/messages?page=${page}&size=${size}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log("Received data:", data); // 데이터를 콘솔에 출력해 확인

                if (data.data.content.length > 0) {
                    const previousHeight = chatWindow.scrollHeight;

                    data.data.content.reverse().forEach(message => {
                        const isOwnMessage = message.senderId === senderId;
                        displayMessage(message.senderType, message.content, isOwnMessage, true);
                    });

                    setTimeout(() => {

                        chatWindow.scrollTop = chatWindow.scrollHeight - previousHeight;
                    }, 0);
                }
                isLoading = false;
            })
            .catch(error => {
                console.error("Failed to load messages:", error);
                isLoading = false;
            });
    }

    function displayMessage(senderType, content, isOwnMessage, prepend = false) {
        const messageElement = document.createElement("div");
        messageElement.classList.add("message");
        if (isOwnMessage) {
            messageElement.classList.add("own-message");
        } else {
            messageElement.classList.add("other-message");
            if (senderType === "BUYER") {
                messageElement.classList.add("buyer-message");
            } else if (senderType === "SELLER") {
                messageElement.classList.add("seller-message");
            }
        }
        messageElement.textContent = content;

        if (prepend) {
            chatWindow.prepend(messageElement);
        } else {
            chatWindow.appendChild(messageElement);
            chatWindow.scrollTop = chatWindow.scrollHeight;
        }

        console.log("Message added to chat:", content); // 메시지가 DOM에 추가되었는지 확인

    }
});
