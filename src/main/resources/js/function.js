importPackage(java.util);

function add(a, b) {
	return a + b;
}

function list() {
	var list = ArrayList();
	list.add("aaa");
	list.add("bbb");
	list.add("ccc");
	return list;
}

function maps(){
	var map = HashMap();
	map.put(1,"aaa");
	map.put(2,"bbb");
	map.put(3,"ccc");
	return map;
}

function users() {
	var users = new ArrayList();
	users.add(new examples.showcase.User(1, "aaa", 11, "shanghai", new Date()));
	users.add(new examples.showcase.User(2, "bbb", 22, "nanjing", new Date()));
	users.add(new examples.showcase.User(3, "ccc", 33, "beijing", new Date()));
	return users;
}

function printUser(user) {
	println(user.id);
	println(user.name);
	println(user.age);
	println(user.address);
	println(user.createAt);
}