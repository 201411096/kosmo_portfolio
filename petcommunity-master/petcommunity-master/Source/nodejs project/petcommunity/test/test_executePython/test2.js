console.log(process.cwd());
var directoryPath = process.cwd();
console.log(directoryPath);
console.log('split.....');
var directoryPathArray = directoryPath.split('\\');
for(var i=0; i<4; i++){
    directoryPathArray.pop();
}
console.log(directoryPathArray);
console.log('directory에 붙이기 전...')
directoryPath = directoryPathArray[0];
for(var i=1; i<directoryPathArray.length; i++){
    directoryPath+="/";
    directoryPath+=directoryPathArray[i];
}
directoryPath+="/python project/pythonProject_kys/PublicData.py"
console.log(directoryPath)