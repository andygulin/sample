namespace java code.sample.thrift.service

service HelloService{
	void voidMethod(),
	string hello(),
	string sayHello(1:string name),
	i32 ints(),
	i64 longs(),
	bool isMan(),
	list<string> lists(),
	set<string> sets(),
	map<i32,string> maps()
}