<script>
	import "../app.css";
	import Header from "./header.svelte"

	const {children} = $props();
</script>

<div class="h-screen">
	<Header />
	{@render children()}
</div>

