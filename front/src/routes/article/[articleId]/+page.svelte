<script>
	import axios from 'axios';
	import '../../../app.css';
	import { onMount } from 'svelte';
	import { page } from '$app/stores';
	import ModifyArticle from './modify/+page.svelte';

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
		content: ''
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
	}

	function loadArticle(commentPage) {
		return new Promise(async (resolve, reject) => {
			try {
				console.log('load');
				const res = await axios.get(
					`http://localhost:8080/article/${articleId}?page=${commentPage}`
				);
				console.log(res);
				article.subject = res.data.articleDto.subject;
				article.author = res.data.articleDto.author.username;
				article.createDate = formatDateTime(res.data.articleDto.createDate);
				article.modifyDate = formatDateTime(res.data.articleDto.modifyDate);
				article.countVote = res.data.articleVoteDtos.length;
				article.countComment = res.data.pagingComment.totalElements;
				article.content = res.data.articleDto.content;

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
					`http://localhost:8080/api/login`,
					{ username: '3', password: '3' },
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
		window.location.href=`http://localhost:5173/article/${articleId}`;
	}

	function deleteComment(commentId) {
		axios.delete(`http://localhost:8080/article/comment/delete/${commentId}`, {
			withCredentials: true
		})
		window.location.href=`http://localhost:5173/article/${articleId}`;
	}
</script>

{#await promise}
	<h1 style="color: royalblue;">loading...</h1>
{:then ar}
	<div>
		<p>게시글 제목 : {article.subject}</p>
	</div>
	<div>
		<p>게시글 작성 이름 : {article.author}</p>
	</div>
	<div>
		<p>게시글 작성 기간 : {article.createDate}</p>
	</div>
	<div>
		<p>게시글 수정 기간 : {article.modifyDate}</p>
	</div>
	<div>
		<p>게시글 추천 수 : {article.countVote}</p>
		<button
			class="btn btn-neutral"
			on:click={() => {
				toggleVoteArticle();
				window.location.href = `http://localhost:5173/article/${articleId}`;
			}}>추천!</button
		>
	</div>

	<div>
		<p>댓글 수 : {article.countComment}</p>
	</div>
	<div>
		<p>게시글 내용 : {article.content}</p>
	</div>
	<div>
		<a
			class="btn btn-neutral"
			href={`${articleId}/modify`}
			on:click={() => {
				localStorage.setItem('subject', article.subject);
				localStorage.setItem('content', article.content);
			}}>수정</a
		>
		<button
			class="btn btn-neutral"
			on:click={() => {
				deleteArticle();
				window.location.href = 'http://localhost:5173/article';
			}}>삭제</button
		>
		<a class="btn btn-neutral" href="../">뒤로가기</a>
	</div>

	<hr />
	<p>댓글</p>
	<hr />

	{#each comments as comment}
		<!-- <div>
			<p>댓글 작성자 : {comment.author.username}</p>
		</div> -->
		<div>
			<p>댓글 작성자 :</p>
		</div>
		<div>
			<p>댓글 : {comment.content}</p>
		</div>
		<div>
			<p>댓글 좋아요 수 : {commentVoteDtos[comment.id]}</p>
		</div>
		<div>
			<p>댓글 생성일 : {formatDateTime(comment.createDate)}</p>
		</div>
		<div>
			{#if isModifyComment[comment.id]}
				<textarea
					class="textarea textarea-primary"
					bind:value={modifyInputComment}
					placeholder="댓글을 입력해주세요"
				></textarea>
				<button
					class="btn btn-neutral"
					on:click={() => {
						modifyComment(comment.id);
						isModifyComment[comment.id] = !isModifyComment[comment.id];
					}}>수정 완료</button
				>
				<button
					class="btn btn-neutral"
					on:click={() => {
						isModifyComment[comment.id] = !isModifyComment[comment.id];
					}}>취소</button
				>
			{:else}
				<button
					class="btn btn-neutral"
					on:click={() => {
						isModifyComment[comment.id] = !isModifyComment[comment.id];
					}}>댓글 수정</button
				>
				<button
					class="btn btn-neutral"
					on:click={() => {
						deleteComment(comment.id);
					}}>댓글 삭제</button
				>
			{/if}
		</div>
		<hr />
	{:else}
		<div>
			<p>댓글이 없어습니다 OTL</p>
		</div>
	{/each}

	<div>
		<textarea
			class="textarea textarea-primary"
			bind:value={inputComment}
			placeholder="댓글을 입력해주세요"
		></textarea>
		<button
			class="btn btn-neutral"
			on:click={() => {
				postComment();
			}}>작성 완료</button
		>
	</div>

	<!-- 페이지네이션 컨트롤 -->
	<div>
		<button
			on:click={() => changeCommentPage(commentPageConfig.currentPage - 1)}
			disabled={commentPageConfig.currentPage <= 0}
			class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
		>
			이전
		</button>
		<span>페이지 {commentPageConfig.currentPage + 1} / {commentPageConfig.totalPages}</span>

		<div class="pagination">
			{#each createPageArray(commentPageConfig.currentPage, commentPageConfig.totalPages) as page}
				<button
					on:click={() => changeCommentPage(page)}
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
				>
					{`Page ${page + 1}`}
				</button>
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
