<script>
	import axios from 'axios';
	import '../../../app.css';
	import { onMount } from 'svelte';
	import { page } from '$app/stores';
	import ModifyArticle from './modify/+page.svelte';
	import Icon from '@iconify/svelte';
	import { user } from '../../../stores/userStore';

	let promise = Promise.resolve([]);
	let articleId = $state();
	let isModifyArticle = $state(false);
	let isModifyComment = $state([{}]);

	let article = $state({
		subject: '',
		author: '',
		createDate: '',
		modifyDate: '',
		countVote: 0,
		countComment: 0,
		content: '',
		isPaid: false,
		viewCount: 0
	});

	let comments = $state([{}]);
	let commentVoteDtos = $state([{}]);
	let inputComment = $state('');
	let modifyInputComment = $state('');

	let commentPageConfig = $state({
		currentPage: 0,
		totalElements: 0,
		totalPages: 0
	});

	function formatDateTime(dateTimeString) {
		const date = new Date(dateTimeString);
		const options = {
			year: 'numeric',
			month: '2-digit',
			day: '2-digit',
			hour: '2-digit',
			minute: '2-digit'
		};
		return date.toLocaleDateString('ko-KR', options);
	}

	function toggleVoteArticle() {
		let res = axios.post(`http://localhost:8080/article/vote/${articleId}`, null, {
			withCredentials: true
		});
		location.reload();
	}

	function loadArticle(commentPage) {
		return new Promise(async (resolve, reject) => {
			try {
				const res = await axios.get(
					`http://localhost:8080/article/${articleId}?page=${commentPage}`
				);
				console.log(res);
				article.subject = res.data.articleDto.subject;
				article.author = res.data.articleDto.author.username;
				article.isPaid = res.data.articleDto.author.isPaid;
				article.createDate = formatDateTime(res.data.articleDto.createDate);
				article.modifyDate = formatDateTime(res.data.articleDto.modifyDate);
				article.countVote = res.data.articleVoteDtos.length;
				article.countComment = res.data.pagingComment.totalElements;
				article.content = res.data.articleDto.content;
				article.viewCount = res.data.articleDto.viewCount;

				comments = res.data.pagingComment.content;
				commentVoteDtos = res.data.commentVoteDtos;

				commentPageConfig.currentPage = res.data.pagingComment.number;
				commentPageConfig.totalElements = res.data.pagingComment.totalElements;
				commentPageConfig.totalPages = res.data.pagingComment.totalPages;

				for (let i = 0; i < comments.length; i++) {
					isModifyComment.push({
						[comments[i].id]: false
					});
				}
				console.log(isModifyComment);
				console.log(comments[1].author.username);

				resolve(article);
			} catch (err) {
				console.log('err');
				reject(err);
			}
		});
	}

	function changeCommentPage(page) {
		commentPageConfig.currentPage = page;
		loadArticle(page);
	}

	function createPageArray(currentPage, totalPages) {
		let pages = [];

		const range = 5;
		let start = Math.max(currentPage - range, 0);
		let end = Math.min(start + range * 2, totalPages - 1);

		if (totalPages - end < range) {
			start = Math.max(totalPages - range * 2 - 1, 0);
		}

		for (let i = start; i <= end; i++) {
			pages.push(i);
		}
		return pages;
	}

	function deleteArticle() {
		axios.delete(`http://localhost:8080/article/${articleId}`, { withCredentials: true });
	}

	onMount(() => {
		articleId = $page.params['articleId'];
		promise = loadArticle(commentPageConfig.currentPage);
		login();
	});

	function login() {
		return new Promise(async (resolve, reject) => {
			try {
				let res = await axios.post(
					`http://localhost:8080/user/login`,
					{ username: '4', password: '4' },
					{ withCredentials: true }
				);
				console.log(res);
			} catch (error) {
				reject(error);
			} finally {
				console.log('long done');
			}
		});
	}

	function postComment() {
		return new Promise(async (resolve, reject) => {
			try {
				let formData = new FormData();

				const commentData = JSON.stringify({ content: inputComment });
				formData.append('commentDto', new Blob([commentData], { type: 'application/json' }));

				let res = await axios.post(`http://localhost:8080/article/${articleId}/comment`, formData, {
					headers: {
						'Content-Type': 'multipart/form-data'
					},
					withCredentials: true
				});

				console.log(res);
				comments = [...comments, inputComment];
				inputComment = '';
				comments = comments;
				location.reload();
			} catch (error) {
				reject(error);
			} finally {
				console.log('done');
			}
		});
	}

	function modifyComment(commentId) {
		let formData = new FormData();

		const commentData = JSON.stringify({ content: modifyInputComment });
		formData.append('commentDto', new Blob([commentData], { type: 'application/json' }));

		axios.patch(`http://localhost:8080/article/comment/modify/${commentId}`, formData, {
			headers: {
				'Content-Type': 'multipart/form-data'
			},
			withCredentials: true
		});

		comments = [...comments, modifyInputComment];
		modifyInputComment = '';
		window.location.href = `http://localhost:5173/article/${articleId}`;
	}
	function deleteComment(commentId) {
		axios.delete(`http://localhost:8080/article/comment/delete/${commentId}`, {
			withCredentials: true
		});
		window.location.href = `http://localhost:5173/article/${articleId}`;
	}

	function voteComment(commentId) {
		axios.post(`http://localhost:8080/comment/vote/${commentId}`, null, {
			withCredentials: true
		});
		window.location.href = `http://localhost:5173/article/${articleId}`;
	}
</script>

{#await promise}
	<span class="loading loading-bars loading-lg"></span>
{:then ar}
	<div class="width-45 mr-auto ml-auto">
		<h1 class="text-4xl font-bold">{article.subject}</h1>
		<div class="flex items-center space-x-2 mt-6">
			<div class="flex-container">
				<div>
					<p class="text-sm font-semibold">{article.author}</p>
					<p class="text-sm text-gray-500">발행 : {article.createDate}</p>
					<p class="text-sm text-gray-500 mt-2">수정 : {article.modifyDate}</p>
				</div>
				<div class="flex space-x-4 mb-6 container flex-wrap flex-row-reverse">
					<div class="flex-wrap">
						<button
							class="btn border-gray-600 btn-ghost"
							style="color: red;"
							on:click={() => {
								deleteArticle();
								window.location.href = `http://localhost:5173/article`;
							}}>게시글 삭제</button
						>
						<a
							class="btn btn-neutral"
							href={`${articleId}/modify`}
							on:click={() => {
								localStorage.setItem('subject', article.subject);
								localStorage.setItem('content', article.content);
							}}>수정</a
						>
					</div>
				</div>
			</div>
		</div>
		<div class="divider divider-Neutral mb-1" />
		<div class="flex-container">
			<button on:click={toggleVoteArticle}
				><Icon
					class="ml-4 clickable-icon"
					icon="ic:outline-recommend"
					color="white"
					width="20"
				/></button
			>
			<p class="ml-2 font-sans">{article.countVote}</p>

			<Icon class="ml-4" icon="ant-design:comment-outlined" color="white" width="20" />
			<p class="ml-2 font-sans">{article.countComment}</p>
			<Icon icon="ep:view" class="ml-4" color="white" /><p class="ml-2 font-sans">{article.viewCount}</p>
			
		</div>
		<div class="divider divider-Neutral mt-1" />
		{#if article.isPaid}
			<div class="badge badge-secondary badge-outline">이 글은 유료멤버십전용 입니다.</div>
		{/if}
		<div class="mt-8">
			<p class="mt-4">
				{article.content}
			</p>
		</div>

		<div class="divider divider-Neutral mb-1 mt-10" />
	</div>

	<div class="grid place-items-center">
		{#each comments as comment}
			<div class="width-45">
				<!-- {#if article.authorId === comment.author.id} -->
				<!-- {console.log(article.author)} -->

				<!-- {console.log(comment.author.username)} -->
				{#if comment.author && comment.author.username === article.author}
					<div class="chat chat-end">
						<div class="chat-header">
							이름!
							<time class="text-xs opacity-50">{formatDateTime(comment.createDate)}</time>
						</div>
						<div class="chat-bubble chat-bubble-primary">{comment.content}</div>

						{#if isModifyComment[comment.id]}
							<textarea
								class="textarea textarea-primary"
								placeholder="댓글을 입력해주세요."
								bind:value={modifyInputComment}
							></textarea>
							<button
								on:click={() => {
									if (!modifyInputComment.trim()) {
										return;
									}
									modifyComment(comment.id);
									modifyInputComment = '';
									isModifyComment[comment.id] = !isModifyComment[comment.id];
								}}>수정 완료</button
							>
							<button on:click={() => (isModifyComment[comment.id] = !isModifyComment[comment.id])}
								>취소</button
							>
						{:else}
							<button
								on:click={() => {
									modifyInputComment = comment.content;
									isModifyComment[comment.id] = !isModifyComment[comment.id];
								}}>수정</button
							>
							<button
								on:click={() => {
									deleteComment(comment.id);
								}}
								style="color: red;">댓글 삭제</button
							>
						{/if}
					</div>
				{:else}
					<div class="chat chat-start">
						<div class="chat-header">
							이름!
							<time class="text-xs opacity-50">{formatDateTime(comment.createDate)}</time>
						</div>
						<div class="chat-bubble chat-bubble-primary">{comment.content}</div>
						{#if isModifyComment[comment.id]}
							<textarea
								class="textarea textarea-primary"
								placeholder="댓글을 입력해주세요."
								bind:value={modifyInputComment}
							></textarea>
							<button
								on:click={() => {
									if (!modifyInputComment.trim()) {
										return;
									}
									modifyComment(comment.id);
									modifyInputComment = '';
									isModifyComment[comment.id] = !isModifyComment[comment.id];
								}}>수정 완료</button
							>
							<button on:click={() => (isModifyComment[comment.id] = !isModifyComment[comment.id])}
								>취소</button
							>
						{:else}
							<button on:click={() => (isModifyComment[comment.id] = !isModifyComment[comment.id])}
								>수정</button
							>
							<button
								on:click={() => {
									deleteComment(comment.id);
								}}
								style="color: red;">댓글 삭제</button
							>
						{/if}
					</div>
				{/if}
			</div>
		{/each}
		<div class="flex-container">
			<textarea
				class="textarea textarea-primary row-span-auto"
				placeholder="댓글을 입력해주세요."
				bind:value={inputComment}
				on:keydown={(e) => {
					e.key === 'Enter' ? postComment() : undefined;
				}}
			/>
			<button class="btn border-gray-600 btn-ghost ml-1" on:click={postComment}>등록</button>
		</div>
		<input
			type="button"
			class="btn border-gray-600 btn-ghost ml-1 mt-10"
			value="뒤로가기"
			on:click={() => (window.location.href = 'http://localhost:5173/article')}
		/>
	</div>

	<!-- 페이지네이션 컨트롤 -->
	<div class="pagination-container">
		<button
			on:click={() => changeCommentPage(commentPageConfig.currentPage - 1)}
			disabled={commentPageConfig.currentPage <= 0}
			class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
		>
			이전
		</button>
		<!-- <span>페이지 {commentPageConfig.currentPage + 1} / {commentPageConfig.totalPages}</span> -->

		<div class="join">
			{#each createPageArray(commentPageConfig.currentPage, commentPageConfig.totalPages) as page}
				<input
					on:click={() => changeCommentPage(page)}
					class="join-item btn btn-squar"
					type="radio"
					aria-label={`${page + 1}`}
					checked={commentPageConfig.currentPage === page}
				/>
			{/each}
		</div>
		<button
			on:click={() => changeCommentPage(commentPageConfig.currentPage + 1)}
			disabled={commentPageConfig.currentPage >= commentPageConfig.totalPages - 1}
			class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
		>
			다음
		</button>
	</div>
{:catch err}
	<h1 style="color: red;">{err.message}</h1>
{/await}

<style>
	.flex-container {
		display: flex;
		align-items: center; /* 요소들을 수직 중앙에 정렬 */
	}
	.pagination-container {
		display: flex;
		justify-content: center; /* Center horizontally */
		width: 100%; /* Take full width */
		margin-top: auto; /* Push towards the bottom */
	}
	.width-45 {
		width: 45%;
	}
</style>
