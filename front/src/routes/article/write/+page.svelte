<script>
	import '../../../app.css';
	import axios from 'axios';

	let promise = Promise.resolve();
	let inputSubject = '';
	let inputContent = '';

	async function postArticle() {
		const formData = new FormData();

		const articleData = JSON.stringify({
			subject: inputSubject,
			content: inputContent
		});
		console.log(inputSubject);
		console.log(inputContent);
		formData.append('articleDto', new Blob([articleData], { type: 'application/json' }));

		const res = await axios.post(`http://localhost:8080/article`, formData, {
			headers: {
				'Content-Type': 'multipart/form-data'
			},
			withCredentials: true
		});

		inputContent="";
		inputSubject="";
		console.log(res);
		location.href = res.headers.location;
	}

	function login() {
		return new Promise(async (resolve, reject) => {
			try {
				let res = await axios.post(
					`http://localhost:8080/api/login`,
					{
						username: '3',
						password: '3'
					},
					{
						withCredentials: true
					}
				);
				console.log(res);
			} catch (error) {
				reject(error);
			}
		});
	}

	$effect(() => {
	});
</script>

{#await promise}
	<span class="loading loading-bars loading-lg"></span>
{:then}
	<div class="flex max-w-7xl mx-auto">
		<div class="flex-1 p-8">
			<div class="flex space-x-4 mb-6">
				<button
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
					on:click={() => {
						location.href = 'article';
					}}>
					취소
				</button>
			</div>

			<div class="flex space-x-4 mb-6">
				<p>제목 입력 :</p>
				<input type="text" bind:value={inputSubject} />

				<p>내용 입력 :</p>
				<textarea bind:value={inputContent} />
			</div>

			<button
				class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
				on:click={() => {
					postArticle();
				}}>
				작성 완료
			</button>
		</div>
	</div>
{:catch error}
	<h1>{error.message}</h1>
{/await}
