
var express = require('express');
var fs = require('fs');
var app = express();

app.get('/', function(req, res){
    res.send('Index Page !');
});


app.get('/chart', function(req, res){
    var filePath = '/home/wang/work/pms/LogProcess/display/testBar.html';
    res.sendFile(filePath);
});

app.get('/refresh', function(req, res){
    refresh(req, res);
});

function refresh(req, response) {
    var filename = "/home/wang/work/pms/LogProcess/processChain/class/result_file.txt";
    fs.readFile(filename,'UTF-8' , function(err, data) {
        if(err) {
            console.log('Open ' + filename + ' error');
        } else {
            console.log(data);
            data = data.substr(0, data.length-2);
            var jsonStr = '{"DATA":[' + data + ']}';
            var list = JSON.parse(jsonStr);
            console.log('\n--------------------\n');
            for(var i=0; i<list.DATA.length; i++) {
                console.log(list.DATA[i]);
            }
            response.send(jsonStr);
        }
    });
}




app.listen(3000);
