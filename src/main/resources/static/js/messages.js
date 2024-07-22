
const path = window.location.pathname;
const slashIndex = path.indexOf("/", 2);
const chatId = slashIndex !== -1 ? path.substring(slashIndex + 1) : path;

const messageContent = document.getElementById("message-content");
const messageIdList = messageContent.dataset.messageId.split("-");
const senderId = messageIdList[0];
const recipientId = messageIdList[1];

const socket = new SockJS("/ws");
const stompClient = StompJs.Stomp.over(socket);
stompClient.connect({}, () => {
    stompClient.subscribe(`/user/${chatId}/${senderId}/queue/messages`, (message) => {
        const parsedMessage = JSON.parse(message.body);

        const messageConatiner = document.getElementById("message-container");
        const newMessage = document.createElement("p");
        newMessage.innerText = parsedMessage.content;
        messageConatiner.appendChild(newMessage);
        console.log(parsedMessage);
    })
})

const btn = document.getElementById("message-btn");
btn.addEventListener("click", (e) => {
    e.preventDefault();
    if (messageContent.value === "") {
        return;
    }

    stompClient.send("/app/chat", {}, JSON.stringify({
        chatId: chatId,
        senderId: senderId,
        recipientId: recipientId,
        content: messageContent.value,
        timestamp: new Date().toISOString()
    }))

    const messageConatiner = document.getElementById("message-container");
    const newMessage = document.createElement("p");
    newMessage.innerText = messageContent.value;
    newMessage.style = "color: var(--red);";
    messageConatiner.appendChild(newMessage);

    messageContent.value = "";
})