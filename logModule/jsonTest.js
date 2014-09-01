

var str = '{"student":[{"Tom": {"age":10, "city":"Beijing"}}, {"Jack":{"age":12, "city":"Tianjin"}} ]}';
var res = JSON.parse(str);
console.log(res);
console.log('---------------------');
console.log(res.student);

console.log('---------------------');
console.log(res.student[0]);

console.log('---------------------');
console.log((res.student[0]).Tom);
console.log((res.student[0]).Tom.age);
console.log((res.student[0]).Tom.city);
