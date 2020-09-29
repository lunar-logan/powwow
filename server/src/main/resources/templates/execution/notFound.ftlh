<#include "../common/header.ftlh">
<section class="section is-grey" style="min-height: 70vh">
    <div class="container">
        <h1 class="has-text-centered">
            <span class="icon has-text-warning is-large" style="font-size: 5em;">
                <i class="fas fa-exclamation-triangle"></i>
            </span>
        </h1>
        <h1 class="title has-text-centered">Correlation ID <b>${correlationId!''?esc}</b> was not found</h1>
        <h2 class="subtitle has-text-centered has-text-info">
            <a href="/">Go back to home page</a>
        </h2>
    </div>
</section>
<#include "../common/footer.ftlh">