// import axios from "axios"

// export async function handle({event, resolve}) {

// 	await axios.post(
// 		`http://localhost:8080/api/login`,
// 		{ username: 3, password: 3 }
// 	);

// 	const session = event.cookies.get("JSESSIONID");
	
// 	console.log(session);
// 	if (!session) {
// 		return await resolve(event);
// 	}

// 	const res = await axios.get("http://localhost:8080/user/find",
// 	 {withCredentials: true });

// 	console.log(res);

// 	const userInfo = await res.json();
// 	if (res) {
// 		event.locals.user = {
// 		  username: res.username,
// 		//   role: user.role,
// 		}
// 	  }
	
// 	return await resolve(event);
// }

// import axios from "axios"

// export async function handle({event, resolve}) {

// 	await axios.post(
// 		`http://localhost:8080/api/login`,
// 		{ username: 3, password: 3 }
// 	);

// 	const session = event.cookies.get("JSESSIONID");
	
// 	console.log(session);
// 	if (!session) {
// 		return await resolve(event);
// 	}

// 	const res = await axios.get("http://localhost:8080/user/find",{
// 	 withCredentials: true });

// 	console.log(res);

// 	if (res) {
// 		event.locals.user = {
// 		  username: res.username,
// 		//   role: user.role,
// 		}
// 	  }
	
// 	return await resolve(event);
// }



import axios from "axios"

export async function handle({event, resolve}) {

	// await axios.post(
	// 	`http://localhost:8080/api/login`,
	// 	{ username: 3, password: 3 }
	// );

	const session = event.cookies.get("JSESSIONID");

	console.log(session);
	if (!session) {
		return await resolve(event);
	}

	const res = await axios.post("http://localhost:8080/user/find",{
		withCredentials: true });

	console.log(res);

	if (res) {
		event.locals.user = {
		  username: res.username,
		//   role: user.role,
		}
	  }
	
	return await resolve(event);
}