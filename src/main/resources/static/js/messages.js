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
    messageContainer.scrollTo({ top: messageContainer.scrollHeight, behavior: 'smooth' });
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

const createImageMessage = (username, content, sender) => {
    let newMessage = document.createElement("div");
    newMessage = `
        <p id="message-username" class="${sender ? "text-end" : "text-start"} fs-5 m-0">
            ${username}
        </p>
        <div id="message-text" class="d-flex ${sender ? "justify-content-end" : "justify-content-start"}">
            <div id="message-text-wrapper" class="box-container img-container">
                <img class="m-0 p-1" src="${content}" alt="img">
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

const addImageMessage = (message) => {
    const messageContainer = document.getElementById("message-container");
    messageContainer.innerHTML += message;
    setTimeout(() => {
        scrollToBottom();
    }, 500);
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
        timestamp: new Date().toISOString(),
        isImage: false
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

        let createdMessage;
        if (parsedMessage.isImage) {
            createdMessage = createImageMessage(recipientUsername, parsedMessage.content, false)
        }
        createdMessage = createMessage(recipientUsername, parsedMessage.content, false);
        addMessage(createdMessage);
    })
}

const main = () => {
    const socket = new SockJS("/ws");
    const stompClient = StompJs.Stomp.over(socket);
    stompClient.connect({}, () => registerLocation(stompClient));

    const btn = document.getElementById("message-btn");
    btn.addEventListener("click", (e) => handleMessageBtnClick(e, stompClient));

    const imageInput = document.getElementById("file");
    imageInput.addEventListener("change", (e) => {
        const container = document.getElementById("message-file-container");
        container.className = "d-flex justify-content-center";
        const textContainer = document.getElementById("message-text-container");
        textContainer.className = "d-flex justify-content-center d-none";

        const fileNameEl = document.getElementById("file-name");
        fileNameEl.innerText = e.target.files[0].name;
    })

    const cancelBtn = document.getElementById("message-file-btn-reset");
    cancelBtn.addEventListener("click", () => {
        const container = document.getElementById("message-file-container");
        container.className = "d-flex justify-content-center d-none";

        const textContainer = document.getElementById("message-text-container");
        textContainer.className = "d-flex justify-content-center";
    })

    const imageForm = document.getElementById("image-form");
    imageForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const data = new FormData(imageForm);

        const res = await fetch("/file/messages/upload", {
            method: "POST",
            body: data
        })
        const url = (await res.json()).url;

        const message = {
            chatId: getChatId(),
            senderId: getMessageIds().senderId,
            recipientId: getMessageIds().recipientId,
            content: url,
            timestamp: new Date().toISOString(),
            isImage: true
        }
        stompClient.send("/app/chat", {}, JSON.stringify(message));


        const senderUsername = document.getElementById("right-container").dataset.username;
        const createdMessage = createImageMessage(senderUsername, url, true);
        addImageMessage(createdMessage);

        const container = document.getElementById("message-file-container");
        container.className = "d-flex justify-content-center d-none";

        const textContainer = document.getElementById("message-text-container");
        textContainer.className = "d-flex justify-content-center";

        const fileNameEl = document.getElementById("file-name");
        fileNameEl.innerText = "";

        imageForm.reset();

    })

    document.addEventListener("DOMContentLoaded", () => {
        setTimeout(() => {
            scrollToBottom();
        }, 500);
    });
}

main();