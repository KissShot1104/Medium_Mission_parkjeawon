<script>
	import '../../app.css';
	import axios from 'axios';
	import { onMount } from 'svelte';

	let articleList = $state({
		articles: [{}],
		currentPage: 0,
		totalPages: 0,
		totalElements: 0,
		sliceSize: 10
	});

	let promise;
	let container = $state();
	let isPost = $state(false);
	let inputTitle = $state('');
	let inputContent = $state('');

	function loadArticleList(page) {
		return new Promise(async (resolve, reject) => {
			try {
				const res = await axios.get(`http://localhost:8080/article?page=${page}`, {
					withCredentials: true
				});
				console.log(res);
				articleList.articles = articleList.articles.concat(res.data.content);
				articleList.totalPages = res.data.totalPages;
				articleList.currentPage = res.data.number;
				articleList.totalElements = res.data.totalElements;
				console.log(articleList);
				resolve(articleList.articles);
				console.log(articleList.articles[0].title);
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
						location.href="article/write";	
					}}
				>
					글쓰기
				</button>
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
				<div class="rounded-lg border bg-card text-card-foreground shadow-sm mb-6" data-v0-t="card">
					<div class="flex flex-col space-y-1.5 p-6">
						<a href="http://localhost:5173/article/{article.id}">
							<h3 class="text-2xl font-semibold leading-none tracking-tight">
								{article.subject}
							</h3>
							<p class="text-sm text-muted-foreground">
								{article.content}
							</p>
						</a>
					</div>
					<div class="p-6">
						<div class="flex justify-between items-center">
							<div
								class="inline-flex items-center rounded-full border px-2.5 py-0.5 w-fit text-xs font-semibold transition-colors focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 border-transparent bg-secondary text-secondary-foreground hover:bg-secondary/80"
							>
								카테고리
							</div>
							<span>{formatDateTime(article.createDate)}</span>
						</div>
					</div>
				</div>
			{/each}
		</div>
		<div class="w-80 p-8">
			<div class="mb-6">
				<h2 class="text-lg font-semibold mb-3">Staff Picks</h2>
				<ul>
					<li class="mb-2">
						<a href="#">32 of our favorite Medium stories of 2023</a>
					</li>
					<li class="mb-2">
						<a href="#">End-of-Year Reflection Questions for the Procrastinating Perfectionist</a>
					</li>
					<li class="mb-2">
						<a href="#">NY Times Missed These 12 Trailblazers: Meet the Women Transforming AI</a>
					</li>
				</ul>
				<button
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
				>
					See the full list
				</button>
			</div>
			<div class="sticky top-8 bg-white p-4 shadow rounded-lg">
				<h3 class="text-lg font-semibold mb-3">Writing on Medium</h3>
				<ul>
					<li class="mb-2">
						<a href="#">New writer FAQ</a>
					</li>
					<li class="mb-2">
						<a href="#">Expert writing advice</a>
					</li>
					<li class="mb-2">
						<a href="#">Grow your readership</a>
					</li>
				</ul>
				<button
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 bg-primary text-primary-foreground hover:bg-primary/90 h-10 px-4 py-2 w-full mt-4"
				>
					Start writing
				</button>
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
