<script>
	import '../../app.css';
	import axios from 'axios';
	import { onMount } from 'svelte';

	let sliceList = $state({
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
				const res = await axios.get(`http://localhost:8080/article?page=${page}`);
				console.log(res);
				sliceList.articles = sliceList.articles.concat(res.data.content);
				sliceList.totalPages = res.data.totalPages;
				sliceList.currentPage = res.data.number;
				sliceList.totalElements = res.data.totalElements;
				console.log(sliceList);
				resolve(sliceList.articles);
				console.log(sliceList.articles[0].title);
				sliceList.currentPage += 1;
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
			if (sliceList.currentPage < sliceList.totalPages) {
				loadArticleList(sliceList.currentPage);
			}
		}
	}

	onMount(() => {
		promise = loadArticleList(sliceList.currentPage);
		container.addEventListener('scroll', handleScroll);
		return () => {
			container.removeEventListener('scroll', handleScroll);
		};
	});
</script>


<!-- <div class="navbar bg-base-100">
  <div class="flex-1">
    <a class="btn btn-ghost text-xl">daisyUI</a>
  </div>
  <div class="flex-none">
    <div class="dropdown dropdown-end">
      <div tabindex="0" role="button" class="btn btn-ghost btn-circle">
        <div class="indicator">
          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" /></svg>
          <span class="badge badge-sm indicator-item">8</span>
        </div>
      </div>
      <div tabindex="0" class="mt-3 z-[1] card card-compact dropdown-content w-52 bg-base-100 shadow">
        <div class="card-body">
          <span class="font-bold text-lg">8 Items</span>
          <span class="text-info">Subtotal: $999</span>
          <div class="card-actions">
            <button class="btn btn-primary btn-block">View cart</button>
          </div>
        </div>
      </div>
    </div>
    <div class="dropdown dropdown-end">
      <div tabindex="0" role="button" class="btn btn-ghost btn-circle avatar">
        <div class="w-10 rounded-full">
          <img alt="Tailwind CSS Navbar component" src="https://daisyui.com/images/stock/photo-1534528741775-53994a69daeb.jpg" />
        </div>
      </div>
      <ul tabindex="0" class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52">
        <li>
          <a class="justify-between">
            Profile
            <span class="badge">New</span>
          </a>
        </li>
        <li><a>Settings</a></li>
        <li><a>Logout</a></li>
      </ul>
    </div>
  </div>
</div> -->

{#if isPost}
	<div class="flex max-w-7xl mx-auto">
		<div class="flex-1 p-8">
			<div class="flex space-x-4 mb-6">
				<button
					class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
					on:click={() => {
						isPost = !isPost;
					}}
				>
					취소
				</button>
			</div>

			<div class="flex space-x-4 mb-6">
				<p>제목 입력 :</p>
				<input type="text" bind:value={inputTitle} />

				<p>내용 입력 :</p>
				<textarea bind:value={inputContent} />
			</div>

			<button
				class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
				on:click={() => {
					isPost = !isPost;
				}}
			>
				작성 완료
			</button>
		</div>
	</div>
{:else}
	{#await promise}
		<span class="loading loading-bars loading-lg"></span>
	{:then articles}
		<div class="flex max-w-7xl mx-auto scroll-container" bind:this={container}>
			<div class="flex-1 p-8">
				<div class="flex space-x-4 mb-6">
					<button
						class="inline-flex items-center justify-center rounded-md text-sm font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input bg-background hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2"
						on:click={() => {
							isPost = !isPost;
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
				{#each sliceList.articles as article}
					<div
						class="rounded-lg border bg-card text-card-foreground shadow-sm mb-6"
						data-v0-t="card"
					>
						<div class="flex flex-col space-y-1.5 p-6">
							<!-- <a href="http://localhost:5173/article/{article.id}"> -->
							<h3 class="text-2xl font-semibold leading-none tracking-tight">
								{article.subject}
							</h3>
							<p class="text-sm text-muted-foreground">
								{article.content}
							</p>
							<!-- </a> -->
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
{/if}

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
