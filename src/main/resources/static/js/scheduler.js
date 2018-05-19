var stompClient = null;


function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({ }

    , function (frame) {
        console.log('Connected: ' + frame);
         stompClient.subscribe('/user/queue/reply', function(greeting) {
                                                                    showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function showMessage(message) {
   alert(message);
}


$(function () {

});