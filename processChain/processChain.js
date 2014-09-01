

var logStream = {};

var cache = {};

function Queue(length) {
	this.length = length+1;
	this.data = {};
	this.head = 0;
	this.tail = 0;

	this.append = function(element) {
		console.log('head=' + this.head + ' tail=' + this.tail + ', element=' + element);
		if((this.tail+1)%this.length === this.head){
			return false;
		}
		this.data[this.tail] = element;
		this.tail++;
		return true;

	}

	this.front = function () {
		if(this.tail === this.head){
			return false;
		}
		var ret = this.data[this.head];
		this.head++;
		return ret;
	}
}




