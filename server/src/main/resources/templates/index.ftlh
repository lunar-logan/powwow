<#include "common/header.ftlh">
<section class="section is-grey">
    <div class="container">
        <table class="table is-striped is-hoverable is-fullwidth">
            <thead>
            <tr>
                <th>FSM ID</th>
                <th>Correlation ID</th>
                <th>State</th>
                <th>Status</th>
                <th>Started</th>
                <th>Last Updated</th>
                <th>Running Time</th>
            </tr>
            </thead>
            <tbody>
            <#list contexts as ctx>
                <tr>
                    <td>${ctx.stateMachineDef.id}</td>
                    <td><a href="/execution/${ctx.correlationId}">${ctx.correlationId}</a></td>
                    <td>${ctx.state}</td>
                    <td>${ctx.executionStatus}</td>
                    <td>${ctx.started?datetime}</td>
                    <td>${(ctx.lastUpdated!ctx.started)?datetime}</td>
                    <td>${ctx.started?datetime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</section>
<#include "common/footer.ftlh">