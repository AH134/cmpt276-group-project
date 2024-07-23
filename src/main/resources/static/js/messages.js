
const path = window.location.pathname;
const slashIndex = path.indexOf("/", 2);
const chatId = slashIndex !== -1 ? path.substring(slashIndex + 1) : path;

const messageContent = document.getElementById("message-content");
const messageIdList = messageContent.dataset.messageId.split("-");
const senderId = messageIdList[0];
const recipientId = messageIdList[1];

const scrollToBottom = () => {
    const messageContainer = document.getElementById("message-container");
    messageContainer.scrollTop = messageContainer.scrollHeight;
}

document.addEventListener("DOMContentLoaded", () => {
    scrollToBottom();
})

const socket = new SockJS("/ws");
const stompClient = StompJs.Stomp.over(socket);
stompClient.connect({}, () => {
    stompClient.subscribe(`/user/${chatId}/${senderId}/queue/messages`, (message) => {
        const parsedMessage = JSON.parse(message.body);
        const messageConatiner = document.getElementById("message-container");
        const recipientUsername = document.getElementById("plant-sponsor").innerText.split(":")[1];
        let newMessage = document.createElement("div");
        newMessage = `
        <p id="message-username" class="text-align-start fs-5 m-0">
            ${recipientUsername}
        </p>
                        <div id="message-text"
                            class="d-flex justify-content-start">
                            <div id="message-text-wrapper" class="box-container">
                                <p class="m-0 p-1">${parsedMessage.content}</p>
                            </div>
                        </div>
        `
        messageConatiner.innerHTML += newMessage;
        scrollToBottom();
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
    const senderUsername = document.getElementById("right-container").dataset.username;
    let newMessage = document.createElement("div");
    newMessage = `
    <p id="message-username" class="text-end fs-5 m-0">
        ${senderUsername}
    </p>
                    <div id="message-text"
                        class="d-flex justify-content-end">
                        <div id="message-text-wrapper" class="box-container">
                            <p class="m-0 p-1">${messageContent.value}</p>
                        </div>
                    </div>
    `
    messageConatiner.innerHTML += newMessage;
    scrollToBottom();

    messageContent.value = "";
})