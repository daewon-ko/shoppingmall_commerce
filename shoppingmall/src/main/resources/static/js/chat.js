document.addEventListener("DOMContentLoaded", () => {
    const chatWindow = document.getElementById("chatWindow");
    const messageInput = document.getElementById("messageInput");
    const sendButton = document.getElementById("sendButton");

    const socket = new WebSocket(`ws://localhost:8080/ws/chatRoom/${roomId}`);

    socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        // 수신한 메시지가 자신이 보낸 메시지인지 확인
        const isOwnMessage = message.senderId === senderId;
        displayMessage(message.senderType, message.content, isOwnMessage);
    };

    socket.onopen = () => {
        const enterMessage = {
            messageType: "ENTER",
            senderId: senderId,
            senderType: currentUserRole,
            content: currentUserRole + " has entered the room."
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
            // 메시지를 서버로 전송하고 동시에 로컬에 표시
            // displayMessage(currentUserRole, message, true);
            socket.send(JSON.stringify(chatMessage));
            messageInput.value = "";
        }
    });

    function displayMessage(senderType, content, isOwnMessage) {
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
        chatWindow.appendChild(messageElement);
        chatWindow.scrollTop = chatWindow.scrollHeight;
    }
});
