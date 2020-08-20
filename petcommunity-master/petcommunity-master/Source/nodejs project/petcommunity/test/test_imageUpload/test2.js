// socket.io-stream을 이용한 ...
const https = require('https');
const fs = require('fs');
const options = {
  pfx: fs.readFileSync('petcommunity.pfx'),
  passphrase: '123456'
};

var app = https.createServer(options, (req, res) => {
    res.setHeader("Access-Control-Allow-Origin", "*");
    res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);

}).listen(3000, '192.168.0.18', () => {
    console.log('listening on *:3000');
});

var io = require('socket.io')(app);
var ss = require('socket.io-stream');
var path = require('path');

io.on('connection', (socket) => {
  console.log('a user connected');
  socket.on('disconnect', () => {
    console.log('user disconnected');
  });
  ss(socket).on('file', function(stream, data) {
    console.log('file event 진입 ... ');
    var filename = path.basename(data.name);
    stream.pipe(fs.createWriteStream(filename));
  });
});
