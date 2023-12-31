<script>
	import axios from "axios";

	let username = '';
	let password = '';


	function login() {
		return new Promise(async (resolve, reject) => {
			try {
				let res = await axios.post(
					`http://localhost:8080/api/login`,
					{ username: username, password: password }
				);
				console.log(res);
			} catch (error) {
				reject(error);
			} finally {
				console.log('long done');
			}
		});
	}
</script>

<div>
	<input
		type="text"
		bind:value={username}
		placeholder="아이디를 입력해주세요"
		class="input input-bordered input-primary w-full max-w-xs"
	/>
</div>

<div>
	<input
		type="text"
		bind:value={password}
		placeholder="비밀번호를 입력해주세요"
		class="input input-bordered input-primary w-full max-w-xs"
	/>
</div>

<button on:click={login}>로그인</button>
