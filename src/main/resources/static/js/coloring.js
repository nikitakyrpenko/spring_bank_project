var colorCard = document.getElementsByClassName('colored');
var reciever = document.getElementsByClassName('recieverID');
var sender = document.getElementsByClassName('senderID');
var myId = document.getElementById('testId').textContent;
for(var i = 0; i< colorCard.length; i++){
    var rec = reciever[i].textContent;
    var send = sender[i].textContent;
    if(rec == myId){
        colorCard[i].style.backgroundColor = '#b8edb7'
    }else if(send == myId){
        colorCard[i].style.backgroundColor = '#ff908c'
    }else{
        colorCard[i].style.backgroundColor = '#fff';
    }
}