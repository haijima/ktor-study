<#import "template.ftl" as layout />

<@layout.mainLayout title="Welcome">
<#if user??>
    <div class="jumbotron">
        <h2 class="display-3">Hello, ${user.login?has_content?then(user.login, user.id)}</h2>
        <p class="lead">You signed in with GitHub using OAuth2.</p>
        <hr class="my-4">
        <div class="d-block mb-2"><a href="${user.url}"><img width=100 class="rounded" src="${user.avatarUrl}" /></a></div>
        <div class="h4 d-block mb-0">${user.name}</div>
        <div class="d-block text-secondary">${user.login?has_content?then(user.login, user.id)}</div>
    </div>
<#else>
    <div>not login</div>
</#if>
</@layout.mainLayout>