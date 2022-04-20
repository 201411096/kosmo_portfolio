var today = new Date();
today = today.getFullYear().toString()+(today.getMonth()+1).toString()+today.getDate().toString();

const schedule = require('node-schedule');
const https = require('https');
const fs = require('fs');
let {PythonShell} = require('python-shell');
const { time } = require('console');
const options = {
  pfx: fs.readFileSync('petcommunity.pfx'),
  passphrase: '123456'
};
var systemPythonPath = 'C:/ProgramData/Anaconda3/python.exe';
var directoryPath = process.cwd();
var directoryPathArray = directoryPath.split('\\');
for(var i=0; i<2; i++){
  directoryPathArray.pop();
}
directoryPath = directoryPathArray[0];
for(var i=1; i<directoryPathArray.length; i++){
  directoryPath+="/";
  directoryPath+=directoryPathArray[i];
}
directoryPath+="/python project/pythonProject_kys/";

var app = https.createServer(options, (req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type,Origin,Accept,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization');
    res.setHeader('Access-Control-Allow-Credentials', true);
}).listen(3000, '192.168.0.18', () => {
// }).listen(3000, '121.171.119.57', () => { // 집
    console.log('listening on *:3000');
});
var io = require('socket.io')(app);

const scheduler_01 = schedule.scheduleJob('0 0 0 * * *', function(){ // 매일 정각에 실행
  executePythonFileAndReadJsonFile();
  executePythonFileAndReadJsonFile2();
});

io.on('connection', function(socket){
  console.log('user connected');
  socket.on('disconnecting', () => {
    console.log(socket.nickname + ' disconnecting event... ');
  });
  socket.on('disconnect', function(){
    console.log('user disconnected');
  });
  socket.on('joinRoom', function(roomInfo){
    var socketList = io.sockets.sockets; // 소켓 리스트_ 소켓 아이디로 소켓의 정보를 가져올 수 있음
    if(roomInfo.memberId!="tempMember"){ // 회원일 경우 socket의 nickname 지정
      socket.nickname = roomInfo.memberId;
    }
    else{ // 비회원일 경우 socket의 nickname 지정
      roomInfo.memberId='guest__'+socket.id;
      socket.nickname = roomInfo.memberId;
    }
    if(roomInfo.prev == roomInfo.cur){
      socket.join(roomInfo.prev);
      io.to(roomInfo.prev).emit('chat message', makeMessageObject('system', roomInfo.memberId+"님이 입장하셨습니다."));
    }else{
      io.to(roomInfo.prev).emit('chat message', makeMessageObject('system', roomInfo.memberId+"님이 퇴장하셨습니다."));
      socket.leave(roomInfo.prev);
      socket.join(roomInfo.cur);
      io.to(roomInfo.cur).emit('chat message', makeMessageObject('system', roomInfo.memberId+"님이 입장하셨습니다."));
    }
  });
  socket.on('chat message', (msg) => {
    var memberId;
    if(msg.memberId!='tempMember'){
      memberId = msg.memberId;
    }else{
      memberId = 'guest__'+socket.id;
    }
    messageHandling(memberId, msg, socket); // message 처리
  });
  // 공공데이터(파이썬 부분)-----
  socket.on('getPublicData', function(dataOptions){
    executePythonFileAndReadJsonFile(dataOptions, socket);
  })
  socket.on('getMainPublicData', function(dataOptions){
    console.log('getMainPublicData 이벤트 확인 ...');
    executePythonFileAndReadJsonFile2(dataOptions, socket);
  })
  // 공공데이터(파이썬 부분)-----

  // 이미지 분류 ...
  socket.on('ClassifyingImage', function(data){
    console.log('받아온 데이터 타입 확인', typeof(data));
    executePythonFileForML(data);
  });
  // 이미지 분류 ...

  // 쪽지 알림 부분 -----
  socket.on('sendMessageData', function(sendMessageObject){
    var socketList = io.sockets.sockets;
    io.clients(function(error, clients){
      if (error) throw error;
      for(var i=0; i<clients.length; i++){
        if(socketList[clients[i]].nickname == sendMessageObject.messageTo){
          io.to(socketList[clients[i]].id).emit('sendMessageData', sendMessageObject);      
        }
      }
    });
  });
  socket.on('sendDelData', function(sendMessageObject){
    var socketList = io.sockets.sockets;
    io.clients(function(error, clients){
      if (error) throw error;
      for(var i=0; i<clients.length; i++){
        if(socketList[clients[i]].nickname == sendMessageObject.messageTo){
          io.to(socketList[clients[i]].id).emit('sendDelData', sendMessageObject);      
        }
      }
    });
  });
  // 쪽지 알림 부분 -----
  socket.on('setNickname', function(memberId){
    socket.nickname = memberId;
  });
});

function messageHandling(memberId, msg, socket){ // memberId가 보내는 사람
  var firstArgument="";
  var secondArgument="";
  var socketList = io.sockets.sockets; // 소켓 리스트_ 소켓 아이디로 소켓의 정보를 가져올 수 있음
  if(msg.messageContent!=""){          // 메시지가 비어있는 경우가 아니라면 첫번째 인자 추출
    var tempMsg = msg.messageContent.split(" ");
    firstArgument = tempMsg[0];
  }
  if(firstArgument!=""){        // 첫번째 인자가 있는지 확인
    if(firstArgument[0]=="/"){                                                                                                                      // 명령어 처리 부분 시작 ---
      if(firstArgument=="/help" || firstArgument=="/도움말" || firstArgument=="/h"){   // 도움말 처리 시작 ---
        io.to(socket.id).emit('chat message', makeMessageObject('system', '========================================'));
        io.to(socket.id).emit('chat message', makeMessageObject('system', '귓속말 사용방법 ==> [/귓속말][/whisper][/w][/ㅈ] 상대닉네임 내용'));
        io.to(socket.id).emit('chat message', makeMessageObject('system', '채팅창 지우기 ==> /clear'));
        io.to(socket.id).emit('chat message', makeMessageObject('system', '채팅창 닫기 ==> /exit'));
        io.to(socket.id).emit('chat message', makeMessageObject('system', '========================================'));
        return;
      }
      if(firstArgument=="/clear" || firstArgument=="/c"){
        io.to(socket.id).emit('eventHandling', 'clearMessageBox');
        return;
      }
      if(firstArgument=="/exit"){
        io.to(socket.id).emit('eventHandling', 'exitChat');
        return;
      }                                                                              // 도움말 처리 끝 ---
      if(firstArgument=="/w" || firstArgument=="/ㅈ" || firstArgument=="/귓속말" || firstArgument=="/whisper"){                                                                                                // 귓속말 처리 시작 ---
        secondArgument = tempMsg[1]; // 귓속말의 대상이 있는지 확인하는데 사용
        thridArgument = tempMsg[2];  // 귓속말의 내용이 있는지 확인하는데 새용
        var whisperContent="";
        for(var i=2; i<tempMsg.length; i++){
          whisperContent +=tempMsg[i] + " ";
        }
        if(secondArgument==undefined || thridArgument==undefined){
          io.to(socket.id).emit('chat message', makeMessageObject('system', '귓속말 형식 => /w 상대방닉네임 내용'));
          return;
        }
        io.clients(function(error, clients) { // 현재 접속중인 socket의 목록을 다 가져옴
          if (error) throw error;
          var flagForCheckingTarget=0; // 귓속말의 대상이 존재하는지...
          for (var i=0; i<clients.length; i++){ // 현재 접속중인 socket들을 하나씩 확인함
            if(secondArgument == memberId){
              io.to(socket.id).emit('chat message', makeMessageObject('system', '자기 자신에게는 귓속말을 전송할 수 없습니다.'));
              return;
            }
            if(socketList[clients[i]].nickname == secondArgument){ // socket의 nickname이 귓속말의 대상과 같다면 메시지를 전달함
              io.to(socketList[clients[i]].id).emit('chat message', makeMessageObject('receive_whisper', memberId + "님으로부터의 귓속말 : " + whisperContent));
              flagForCheckingTarget++;
            }
          }
          if(flagForCheckingTarget==0){ // 귓속말의 대상이 접속중이지 않다면...
            io.to(socket.id).emit('chat message', makeMessageObject('system', '귓속말의 대상이 존재하지 않거나 접속중이지 않습니다.'));
          }else{
            io.to(socket.id).emit('chat message', makeMessageObject('send_whisper', secondArgument + "님에게 귓속말 : " + whisperContent)); 
          }         
        });
        return;
      }                                                                                                                         // 귓속말 처리 끝 ---
      io.to(socket.id).emit('chat message', makeMessageObject('system', '적절하지 않은 명령어입니다.'));
      return;
    }                                                                                                                                                 // 명령어 처리 부분 끝 ---
  }
  socket.broadcast.to(msg.roomName).emit('chat message', makeMessageObject('receive' , memberId+' : '+msg.messageContent));
  io.to(socket.id).emit('chat message', makeMessageObject('send' , msg.messageContent));
}
// 공공데이터(파이썬 부분)-----
function executePythonFileAndReadJsonFile(dataOptions, socket){
  var python_options = {
    mode: 'text',
    pythonPath: systemPythonPath, //python의 설치경로를 입력하는 부분
    pythonOptions: ['-u'],
    scriptPath: '',
    // args: [0, dataOptions.startDate, dataOptions.endDate, dataOptions.dataCnt]
    args: [0, today.toString().substring(0,4)+'0101', today.toString().substring(0,4)+'1231', 1000]
  }
  fs.stat('publicData_'+today+'.json', function(err, stat) {
    if(err == null) { // 파일이 존재할 떄
      fs.readFile('publicData_'+today+'.json', 'utf8', function(err, data){
        data = JSON.parse(data);
        if(socket!=undefined){
          socket.emit('getPublicData', data);
        }
      });  
    } else if(err.code === 'ENOENT') { // 파일이 존재하지 않을 때
      PythonShell.run(directoryPath+"PublicData.py", python_options, function (err, results) {
        if (err) throw err;
        fs.readFile('publicData_'+today+'.json', 'utf8', function(err, data){
            data = JSON.parse(data);
            if(socket!=undefined){
              socket.emit('getPublicData', data);
            }
        });    
      });
    } else {
        console.log('Some other error: ', err.code);
    }
  });
}
// 공공데이터 2

function executePythonFileAndReadJsonFile2(dataOptions, socket){
  var python_options = {
    mode: 'text',
    pythonPath: systemPythonPath, //python의 설치경로를 입력하는 부분
    pythonOptions: ['-u'],
    scriptPath: '',
    // args: [0, dataOptions.startDate, dataOptions.endDate, dataOptions.dataCnt]
    args: [1]
  }
  fs.stat('main_publicData_'+today+'.json', function(err, stat) {
    if(err == null) { // 파일이 존재할 떄
      fs.readFile('main_publicData_'+today+'.json', 'utf8', function(err, data){
        data = JSON.parse(data);
        if(socket!=undefined){
          console.log("파일존재"+socket)
          socket.emit('getMainPublicData', data);
        }
      });  
    } else if(err.code === 'ENOENT') { // 파일이 존재하지 않을 때
      PythonShell.run(directoryPath+"main_publicData.py", python_options, function (err, results) {
        if (err) throw err;
        fs.readFile('main_publicData_'+today+'.json', 'utf8', function(err, data){
            data = JSON.parse(data);
            if(socket!=undefined){
              console.log("파일존재x"+socket)
              socket.emit('getMainPublicData', data);
            }
        });    
      });
    } else {
        console.log('Some other error: ', err.code);
    }
  });
}

function makeMessageObject(messageType, messageContent){
  var resultObject = new Object();
  resultObject.messageType = messageType;
  resultObject.messageContent = messageContent;
  return resultObject;
}

// machineLearning(파이썬 부분)-----
function executePythonFileForML(imageData){
  fs.writeFile('test.jpg', imageData, 'binary', function(err){
  });
  var python_options = {
    mode: 'text',
    pythonPath: systemPythonPath, //python의 설치경로를 입력하는 부분
    pythonOptions: ['-u'],
    scriptPath: '',
    // args:[1],
    // args: [0, dataOptions.startDate, dataOptions.endDate, dataOptions.dataCnt]
    args:[0, "test.jpg"]
  }
  PythonShell.run(directoryPath+"ClassifyingImage.py", python_options, function (err, results) {
    if (err) throw err;
    //results = JSON.parse(results);
    console.log('pythonshell에서 ... results 확인', results);    
    console.log('results 타입 확인', typeof(results));
    console.log('results %j', results);
    fs.readFile("ClassifyingImage.txt", 'utf8', function(err, data){
      console.log('파일에서 읽은 값 : ' + data);
    });
  });
}
