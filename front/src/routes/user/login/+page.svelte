<script>
	import axios from "axios";
	import { user } from "../../../stores/userStore";
	import { setContext } from "svelte";

	let username = '';
	let password = '';
	let promise = Promise.resolve();
	
	
	function login() {
		return new Promise(async (resolve, reject) => {
			try {
				let res = await axios.post(
					`http://localhost:8080/user/login`,
					{ username: username, password: password }
				);
				resolve(res);
				window.location.href="http://localhost:5173/article";
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

<button on:click={() => {
	promise = login();
}}>로그인</button>

{#await promise}
<p>login....</p>
{:then userData}

{:catch error}
{/await}
