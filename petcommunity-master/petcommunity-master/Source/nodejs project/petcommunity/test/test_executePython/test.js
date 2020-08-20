console.log(process.cwd())
var directoryPath = process.cwd()
console.log(directoryPath)
console.log('split.....')
//console.log(directoryPath.split('\\'))
var directoryPathArray = directoryPath.split('\\')
//console.log(directoryPathArray.pop())
for(var i=0; i<4; i++){
    directoryPathArray.pop()
}
console.log(directoryPathArray)