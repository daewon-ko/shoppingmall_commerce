document.addEventListener("DOMContentLoaded", () => {
    const chatWindow = document.getElementById("chatWindow");
    const messageInput = document.getElementById("messageInput");
    const sendButton = document.getElementById("sendButton");

    // const roomId = [[${chatRoom.id}]];
    // const currentUserRole = /*[[${currentUserRole}]]*/ 'buyer';  // 서버에서 전달된 사용자 역할을 사용합니다.
    const socket = new WebSocket(`ws://localhost:8080/ws/chatRoom/${roomId}`);

    socket.onmessage = (event) => {
        const message = JSON.parse(event.data);
        //TODO : 일단은 내가 보낸 모든 메시지가 웹소켓을 통해 서버에 거쳐서 다시 돌아와 Display 되게끔 설계
        // 그러나 이러한 방법은 서버가 장애가 나서 보내지 못할 때와 같은 경우 서버에 보낼 수 없다.
        // 카톡을 생각해보면 서버가 장애가 나도 채팅화면에 내가 작성한 내용이 전시가 되게끔 구성할 수는 없을까?
        // 수신한 메시지가 자기 자신일 경우 메시지를 display 하지 않아도 된다.
        // 해당 로직을 여기에 추가해야한다.
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
            // displayMessage(currentUserRole, message);
            socket.send(JSON.stringify(chatMessage));
            messageInput.value = "";
        }
    });

    function displayMessage(sender, content) {
        const messageElement = document.createElement("div");
        messageElement.classList.add("message");
        if (sender === "BUYER") {
            messageElement.classList.add("buyer-message");
        } else if (sender === "SELLER") {
            messageElement.classList.add("seller-message");
        }
        messageElement.textContent = content;
        chatWindow.appendChild(messageElement);
        chatWindow.scrollTop = chatWindow.scrollHeight;
    }
});
