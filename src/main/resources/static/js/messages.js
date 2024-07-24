const getChatId = () => {
    const path = window.location.pathname;
    const slashIndex = path.indexOf("/", 2);
    const chatId = slashIndex !== -1 ? path.substring(slashIndex + 1) : path;
    return chatId;
}

const getMessageIds = () => {
    const messageContent = document.getElementById("message-content");
    const messageIdList = messageContent.dataset.messageId.split("-");
    const senderId = messageIdList[0];
    const recipientId = messageIdList[1];

    return { senderId, recipientId };
}

const scrollToBottom = () => {
    const messageContainer = document.getElementById("message-container");
    messageContainer.scrollTop = messageContainer.scrollHeight;
}

const createMessage = (username, content, sender) => {
    let newMessage = document.createElement("div");
    newMessage = `
        <p id="message-username" class="${sender ? "text-end" : "text-start"} fs-5 m-0">
            ${username}
        </p>
        <div id="message-text" class="d-flex ${sender ? "justify-content-end" : "justify-content-start"}">
            <div id="message-text-wrapper" class="box-container">
                <p class="m-0 p-1">${content}</p>
            </div>
        </div>
        `
    return newMessage;
}

const addMessage = (message) => {
    const messageContainer = document.getElementById("message-container");
    messageContainer.innerHTML += message;
    scrollToBottom();
}

const handleMessageBtnClick = (e, stompClient) => {
    e.preventDefault();

    const messageContent = document.getElementById("message-content");
    if (messageContent.value === "") {
        return;
    }

    const message = {
        chatId: getChatId(),
        senderId: getMessageIds().senderId,
        recipientId: getMessageIds().recipientId,
        content: messageContent.value,
        timestamp: new Date().toISOString()
    }
    stompClient.send("/app/chat", {}, JSON.stringify(message));


    const senderUsername = document.getElementById("right-container").dataset.username;
    const createdMessage = createMessage(senderUsername, messageContent.value, true);
    addMessage(createdMessage);

    messageContent.value = "";
}

const registerLocation = (stompClient) => {
    const chatId = getChatId();
    const senderId = getMessageIds().senderId;
    const subscribeLocation = `/user/${chatId}/${senderId}/queue/messages`;

    stompClient.subscribe(subscribeLocation, (message) => {
        const parsedMessage = JSON.parse(message.body);
        const recipientUsername = document.getElementById("plant-sponsor").innerText.split(":")[1];

        const createdMessage = createMessage(recipientUsername, parsedMessage.content, false);
        addMessage(createdMessage);
    })
}

const main = () => {
    const socket = new SockJS("/ws");
    const stompClient = StompJs.Stomp.over(socket);
    stompClient.connect({}, () => registerLocation(stompClient));

    const btn = document.getElementById("message-btn");
    btn.addEventListener("click", (e) => handleMessageBtnClick(e, stompClient));

    document.addEventListener("DOMContentLoaded", scrollToBottom)
}

main();