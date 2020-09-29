<#include "../common/header.ftlh">
<section class="section is-grey">
    <div class="container">
        <#include "actionBar.ftlh">
        <h1 class="title">Details</h1>
        <#include "../common/infoBox.ftlh">

        <h1 class="title">Subscribers</h1>
        <table class="table is-stripped is-fullwidth is-hoverable">
            <thead>
            <tr>
                <th>Name</th>
                <th>Subscribed State</th>
                <th>Callback URL</th>
                <th>Properties</th>
            </tr>
            </thead>
            <tbody>
            <#list fsm.subscribers as subscriber>
                <tr>
                    <td>${subscriber.name}</td>
                    <td>${subscriber.state}</td>
                    <td>${subscriber.callbackUrl}</td>
                    <td>${subscriber.properties!'None'}</td>
                </tr>
            </#list>
            </tbody>
        </table>
        <h1 class="title">Transitions</h1>
        <table class="table is-hoverable is-fullwidth is-bordered">
            <thead>
            <tr>
                <#list transitions.headers as header>
                    <th class="has-background-light">${header}</th>
                </#list>
            </tr>
            </thead>
            <tbody>
            <#list transitions.rows as row>
                <tr>
                    <#list row as col>
                        <td>${col}</td>
                    </#list>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</section>
<#include "../common/footer.ftlh">