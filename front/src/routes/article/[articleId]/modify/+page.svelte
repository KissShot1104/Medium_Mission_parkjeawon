<script>
	import { onMount } from 'svelte';
	import '../../../../app.css';
	import axios from 'axios';
	import { page } from '$app/stores';

	let subject = $state('');
	let content = $state('');
	let articleId= "";

	function modifyArticle() {
		return new Promise(async (resolve, reject) => {
			try {

				const formData = new FormData();

				const articleData = JSON.stringify({
					subject: subject,
					content: content
				})
				console.log(subject);
				console.log(content);

				formData.append("articleDto", new Blob([articleData], { type: "application/json"}));

				const res = await axios.patch(`http://localhost:8080/article/${articleId}`, formData, {
					headers: {
						'Content-Type': 'multipart/form-data'
					},
					withCredentials: true
				});
				console.log(res);
			} catch (error) {
				console.log(error);
			}
		});
	}

	onMount(() => {
		subject = localStorage.getItem('subject');
		content = localStorage.getItem('content');
		articleId = $page.params["articleId"];
	});
</script>

<div>
	<p>subject :</p>
	<input type="text" bind:value={subject} />
</div>

<div>
	<p>content :</p>
	<input type="text" bind:value={content} />
</div>

<a on:click="{() => {
	modifyArticle();
	window.location.href=`http://localhost:5173/article/${articleId}`
	}}">보내기</a>