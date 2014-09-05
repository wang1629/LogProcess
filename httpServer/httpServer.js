

var http = require('http');
var url  = require('url');
var util = require('util');
var fs   = require('fs');



http.createServer(function (req, res) {
    res.writeHead(200, { 'Content-Type': 'text/plain' });
    
    var url_parts = url.parse(req.url, true);
    var query = url_parts.query;
    var action = query.action;
    var id = query.id;

    console.log('action = ' + action);
    if(action == 'refresh') {
        refresh(res);   
    } else if(action == 'chart') {
        replyChart(res);
    }

}).listen(5555, '127.0.0.1');


function refresh(response) {
    var filename = '/home/wang/work/LPS/LogProcess/processChain/class/result_file.txt';
    fs.readFile(filename,'UTF-8' , function(err, data) {
        if(err) {
            console.log('Open ' + filename + ' error');
        } else {
            console.log(data);
            data = data.substr(0, data.length-2);
            var jsonStr = '{"StepResult":[' + data + ']}';
            var list = JSON.parse(jsonStr);
            console.log('\n--------------------\n');
            var firstStepResult = list.StepResult[0];
            console.log(firstStepResult);
            response.end(jsonStr);
        }
    });
}


function replyChart(response) {
    var filePath = '../display/testBar.html';
    var stat = fs.statSync(filePath);
    response.writeHead(200, { 'Content-Type': 'audio/mpeg', 'Content-Length': stat.size });
    var readStream = fs.createReadStream(filePath);
    readStream.pipe(response);
}

console.log('Server running at http://127.0.0.1:5555/');

