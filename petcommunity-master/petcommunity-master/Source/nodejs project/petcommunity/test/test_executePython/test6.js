// json객체로 가져옴
let {PythonShell} = require('python-shell')
// let PythonShell = require('python-shell') // 이거 왜 안되는지 모름
var fs = require('fs')
var systemPythonPath = 'C:/ProgramData/Anaconda3/python.exe'
var directoryPath = process.cwd();
var directoryPathArray = directoryPath.split('\\');
for(var i=0; i<4; i++){
    directoryPathArray.pop();
}
directoryPath = directoryPathArray[0];
for(var i=1; i<directoryPathArray.length; i++){
    directoryPath+="/";
    directoryPath+=directoryPathArray[i];
}
directoryPath+="/python project/pythonProject_kys/";

var options = {
    mode: 'text',
    pythonPath: systemPythonPath, //python의 설치경로를 입력하는 부분
    pythonOptions: ['-u'],
    scriptPath: '',
    // args:[1],
    args: [0, '20200801', '20200831', '3']
}

PythonShell.run(directoryPath+"PublicData.py", options, function (err, results) {
    if (err) throw err;
    fs.readFile(directoryPath+'publicData.json', 'utf8', function(err, data){
        console.log(data);
        console.log(typeof(data));
        data = JSON.parse(data);
        console.log(typeof(data));
        console.log(data.response.body.items.item);
        console.log(data.response.body.items.item[0].age);
    });
    
  });