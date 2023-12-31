<script>
	import axios from "axios";

	let username = "";
	let password1 = "";
	let password2 = "";
	let email = "";

	function createUser() {

		const formData = new FormData();

		const userData = JSON.stringify({
			username: username,
			password1: password1,
			password2: password2,
			email: email
		})

		formData.append("userCreateDto", new Blob([userData], {type: "application/json"}));

		axios.post("http://localhost:8080/user/signup",formData, {
			headers: {
				"Content-Type": "multipart/form-data"
			},
			withCredentials: true
		});
	}
</script>

<div>
	<input type="text" bind:value={username} placeholder="아이디를 입력해주세요" class="input input-bordered input-primary w-full max-w-xs" />
</div>

<div>
	<input type="text" bind:value={password1} placeholder="패스워드를 입력해주세요" class="input input-bordered input-primary w-full max-w-xs" />
</div>

<div>
	<input type="text" bind:value={password2} placeholder="확인 패스워드를 입력해주세요" class="input input-bordered input-primary w-full max-w-xs" />
</div>

<div>
	<input type="text" bind:value={email} placeholder="Email을 입력해주세요" class="input input-bordered input-primary w-full max-w-xs" />
</div>

<div>
	<button on:click={createUser}>생성</button> 
</div>
