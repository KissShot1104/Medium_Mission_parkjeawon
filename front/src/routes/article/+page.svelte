<script>
	import '../../app.css';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import Icon from '@iconify/svelte';
	import { page } from '$app/stores';

	let articleList = $state({
		articles: [{}],
		currentPage: 0,
		totalPages: 0,
		totalElements: 0,
		sliceSize: 10
	});

	let sortCode = $state('');
	let kw = $state('');
	let kwType = $state('');

	let promise;
	let container = $state();
	let isPost = $state(false);
	let inputTitle = $state('');
	let inputContent = $state('');

	function loadArticleList(pageNumber) {
		return new Promise(async (resolve, reject) => {
			try {
				sortCode = $page.url.searchParams.get('sortCode') || '';
				kw = $page.url.searchParams.get('kw') || '';
				kwType = $page.url.searchParams.get('kwType') || '';
				const res = await axios.get(
					`http://localhost:8080/article?sortCode=${sortCode}&kw=${kw}&kwType=${kwType}&page=${pageNumber}`,
					{
						withCredentials: true
					}
				);

				articleList.articles = articleList.articles.concat(res.data.content);
				articleList.totalPages = res.data.totalPages;
				articleList.currentPage = res.data.number;
				articleList.totalElements = res.data.totalElements;
				console.log(articleList);
				resolve(articleList.articles);
				articleList.currentPage += 1;
			} catch (err) {
				console.log('err');
				reject(err);
			} finally {
				console.log('done');
			}
		});
	}

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

	function handleScroll() {
		const { scrollTop, scrollHeight, clientHeight } = container;
		if (scrollHeight - scrollTop === clientHeight) {
			if (articleList.currentPage < articleList.totalPages) {
				loadArticleList(articleList.currentPage);
			}
		}
	}

	function login() {
		return new Promise(async (resolve, reject) => {
			try {
				let res = await axios.post(
					`http://localhost:8080/user/login`,
					{
						username: '4',
						password: '4'
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

	function logout() {
		axios.delete(`http://localhost:8080/user/logout`, { withCredentials: true });
	}
	onMount(() => {
		promise = loadArticleList(articleList.currentPage);
		container.addEventListener('scroll', handleScroll);
		login();

		return () => {
			container.removeEventListener('scroll', handleScroll);
		};
	});
</script>

{#await promise}
	<span class="loading loading-bars loading-lg"></span>
{:then articles}
	<div class="flex max-w-7xl mx-auto scroll-container" bind:this={container}>
		<div class="flex-1 p-8">
			<div class="flex space-x-4 mb-6">
				<button
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
					on:click={() => {
						location.href = 'article/write';
					}}
				>
					글쓰기
				</button>
				<a class="btn btn-neutral" href="../user/login">로그인</a>
				<button class="btn btn-neutral" on:click={logout}>로그아웃</button>
				<!-- <button
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2">
					category2
				</button>
				<button
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2">
					category3
				</button>
				<button
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2">
					category4
				</button>
				<button
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2">
					category5
				</button> -->
			</div>
			{#each articleList.articles as article}
				{#if article.author}
					<div
						class="rounded-lg border bg-card text-card-foreground shadow-sm mb-6"
						data-v0-t="card"
					>
						<div class="flex flex-col space-y-1.5 p-6">
							<a href="http://localhost:5173/article/{article.id}">
								<h3 class="text-2xl font-semibold leading-none tracking-tight">
									{article.subject}
								</h3>
								<p class="text-sm text-muted-foreground">
									{article.content}
								</p>
							</a>

							{#if article.author && article.author.isPaid}
								<div class="gap-2">
									<svg
										xmlns="http://www.w3.org/2000/svg"
										fill="none"
										viewBox="0 0 24 24"
										class="inline-block w-4 h-4 stroke-current"
										><path
											stroke-linecap="round"
											stroke-linejoin="round"
											stroke-width="2"
											d="M6 18L18 6M6 6l12 12"
										></path></svg
									>
									이 글은 유료멤버십전용 입니다.
								</div>
							{/if}
						</div>

						<div class="p-6">
							<div class="flex justify-between items-center">
								<div
									class="inline-flex items-center rounded-full border px-2.5 py-0.5 w-fit text-xs font-semibold transition-colors focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 border-transparent bg-secondary text-secondary-foreground hover:bg-secondary/80"
								>글쓴이 : {article.author.username}</div>
								<Icon
									class="ml-4 clickable-icon"
									icon="ic:outline-recommend"
									color="white"
									width="20"
								/>
								<p class="ml-2 font-sans">{article.voteCount}</p>

								<Icon class="ml-4" icon="ant-design:comment-outlined" color="white" width="20" />
								<p class="ml-2 font-sans">{article.commentCount}</p>
								<Icon icon="ep:view" class="ml-4" color="white" />
								<p class="ml-2 font-sans">{article.viewCount}</p>
								<span>{formatDateTime(article.createDate)}</span>
							</div>
						</div>
					</div>
				{/if}
			{/each}
		</div>
		<div class="w-80 p-8">
			<div class="mb-6">
				
			</div>
		</div>
	</div>
{:catch err}
	<p style="color: red;">{err.message}</p>
{/await}

<style>
	.scroll-container {
		overflow-y: auto;
		height: 100vh; /* 스크롤 컨테이너의 높이 */
	}

	.navbar {
		background-color: #ffffff; /* or the color of your choice */
		box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	}

	.btn-ghost {
		background-color: transparent;
		/* Add other styles such as padding, font, etc. */
	}

	.btn-circle {
		border-radius: 9999px;
		/* Adjust width and height to make it a circle */
	}

	.indicator {
		position: relative;
		/* Add custom styles */
	}

	.badge {
		position: absolute;
		top: -0.5rem;
		right: -0.5rem;
		/* Add other styles like background-color, padding, etc. */
	}

	.dropdown-content {
		/* Add styles for your dropdown content */
	}
	.avatar img {
		border-radius: 9999px;
		/* This will make the image round */
	}
</style>
