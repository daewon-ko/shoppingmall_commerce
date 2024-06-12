document.addEventListener("DOMContentLoaded", () => {
    const chatWindow = document.getElementById("chatWindow");
    const messageInput = document.getElementById("messageInput");
    const sendButton = document.getElementById("sendButton");

    // const roomId = [[${chatRoom.id}]];
    // const currentUserRole = /*[[${currentUserRole}]]*/ 'buyer';  // 서버에서 전달된 사용자 역할을 사용합니다.
    const socket = new WebSocket(`ws://localhost:8080/ws/chatRoom/${roomId}`);

    socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        displayMessage(message.sender, message.content);
    };

    socket.onopen = () => {
        const enterMessage = {
            messageType: "ENTER",
            sender: currentUserRole,
            content: currentUserRole + " has entered the room."
        };
        socket.send(JSON.stringify(enterMessage));
    };

    sendButton.addEventListener("click", () => {
        const message = messageInput.value.trim();
        if (message !== "") {
            const chatMessage = {
                messageType: "TALK",
                sender: currentUserRole,
                content: message
            };
            displayMessage(currentUserRole, message);
            socket.send(JSON.stringify(chatMessage));
            messageInput.value = "";
        }
    });

    function displayMessage(sender, content) {
        const messageElement = document.createElement("div");
        messageElement.classList.add("message");
        if (sender === "buyer") {
            messageElement.classList.add("buyer-message");
        } else if (sender === "seller") {
            messageElement.classList.add("seller-message");
        }
        messageElement.textContent = content;
        chatWindow.appendChild(messageElement);
        chatWindow.scrollTop = chatWindow.scrollHeight;
    }
});
