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

}).listen(3000, '192.168.0.18', () => {
    console.log('listening on *:3000');
});

var io = require('socket.io')(app);

io.on('connection', function(socket){
  console.log('user connected');
  socket.on('disconnect', function(){
    console.log('user disconnected');
  });
  socket.on('joinRoom', function(roomInfo){
    if(roomInfo.memberId!="tempMember"){
      console.log('memberInfo check ... ' + roomInfo.memberId);
    }
    else{
      roomInfo.memberId=socket.id;
    }
    if(roomInfo.prev == roomInfo.cur){
      socket.join(roomInfo.prev);
    }else{
      socket.leave(roomInfo.prev);
      socket.join(roomInfo.cur);
    }
  });
  socket.on('chat message', (msg) => {
    var memberId;
    console.log('roomName : ' + msg.roomName);
    console.log('messageContent : ' + msg.messageContent);
    if(msg.memberId!='tempMember'){
      console.log(msg.memberId);
      memberId = msg.memberId;
    }else{
      memberId = 'user__'+socket.id;
      console.log('userId : '+ memberId);
    }
    io.to(msg.roomName).emit('chat message', memberId+' : '+msg.messageContent);
  });
});



