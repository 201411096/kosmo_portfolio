const https = require('https');
const fs = require('fs');
const options = {
  pfx: fs.readFileSync('petcommunity.pfx'),
  passphrase: '123456'
};

var app = https.createServer(options, (req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type,Origin,Accept,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization');
    res.setHeader('Access-Control-Allow-Credentials', true);

//}).listen(3000, '192.168.0.18', () => {
}).listen(3000, '121.171.119.57', () => {
    console.log('listening on *:3000');
});

var io = require('socket.io')(app);

io.on('connection', function(socket){
  console.log('user connected');
  socket.on('disconnect', function(){
    console.log('user disconnected');
  });
  socket.on('joinRoom', function(roomInfo){
    if(roomInfo.memberId!="tempMember"){ // 회원일 경우 socket의 nickname 지정
      // console.log('memberInfo check ... ' + roomInfo.memberId);
      socket.nickname = roomInfo.memberId;
      console.log('check socket_nickname in joinRoomEvent_01 ...' + socket.nickname);
    }
    else{ // 비회원일 경우 socket의 nickname 지정
      roomInfo.memberId='guest__'+socket.id;
      socket.nickname = roomInfo.memberId;
      console.log('check socket_nickname in joinRoomEvent_02 ...' + socket.nickname);
    }
    if(roomInfo.prev == roomInfo.cur){
      socket.join(roomInfo.prev);
      io.to(roomInfo.prev).emit('chat message', roomInfo.memberId+"님이 입장하셨습니다.");
    }else{
      io.to(roomInfo.prev).emit('chat message', roomInfo.memberId+"님이 퇴장하셨습니다.");
      socket.leave(roomInfo.prev);
      socket.join(roomInfo.cur);
      io.to(roomInfo.cur).emit('chat message', roomInfo.memberId+"님이 입장하셨습니다.");
    }
  });
  socket.on('chat message', (msg) => {
    var memberId;
    if(msg.memberId!='tempMember'){
      memberId = msg.memberId;
    }else{
      memberId = 'guest__'+socket.id;
    }
  //  io.to(msg.roomName).emit('chat message', memberId+' : '+msg.messageContent);
  //  io.to(msg.roomName).emit('chat message', stringHandling(memberId, msg));
    messageHandling(memberId, msg, socket);
  });
});

function messageHandling(memberId, msg, socket){
  var firstArgument="";
  if(msg.messageContent!=""){   // 메시지가 비어있는 경우가 아니라면 첫번째 인자 추출
    var tempMsg = msg.messageContent.split(" ");
    firstArgument = tempMsg[0];
    console.log("firstArgument ... " + firstArgument);
  }
  if(firstArgument!=""){        // 첫번쨰 인자 확인
    if(firstArgument[0]=="/"){  // "/"로 시작하는 경우...
      console.log("start with '/' ... ");
    }
    if(firstArgument=="/help" || firstArgument=="/도움말" || firstArgument=="/h"){   // 도움말 처리
      console.log("help ...");
      console.log('check in /help ... ' + socket.id);
      var tempId = socket.id;
      io.to(tempId).emit('chat message', 'help messageContent ...');
      return;
    }
    if(firstArgument=="/w"){   // 귓속말 처리
      console.log("whispering ...");
    }
  }
  io.to(msg.roomName).emit('chat message', memberId+' : '+msg.messageContent);
}


