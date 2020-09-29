<#include "../common/header.ftlh">
<section class="section is-grey">
    <div class="container">
        <#include "navigationActions.ftlh">
        <h2 class="subtitle">General Information</h2>
        <table class="table is-fullwidth is-bordered is-hoverable">
            <tr>
                <td class="has-background-light"><b>FSM ID</b></td>
                <td>${ctx.stateMachineDef.id}</td>
            </tr>

            <tr>
                <td class="has-background-light"><b>Correlation ID</b></td>
                <td>${ctx.correlationId}</td>
            </tr>

            <tr>
                <td class="has-background-light"><b>State</b></td>
                <td>${ctx.state}</td>
            </tr>

            <tr>
                <td class="has-background-light"><b>Status</b></td>
                <#if ctx.executionStatus == 'COMPLETED'>
                    <td>${ctx.executionStatus} <span class="icon has-text-success">
                            <i class="far fa-check-circle"></i></span>
                    </td>
                <#elseif ctx.executionStatus == 'FAILED'>
                    <td>${ctx.executionStatus} <span class="icon has-text-danger"><i class="fas fa-ban"></i></span></td>
                <#else >
                    <td>${ctx.executionStatus}</td>
                </#if>

            </tr>

            <tr>
                <td class="has-background-light"><b>Started</b></td>
                <td>${ctx.started?datetime}</td>
            </tr>

            <tr>
                <td class="has-background-light"><b>Last Updated</b></td>
                <td>${(ctx.lastUpdated!ctx.started)?datetime}</td>
            </tr>
        </table>

        <h2 class="subtitle">Transition History</h2>
        <table class="table is-fullwidth is-striped is-hoverable is-bordered">
            <thead>
            <tr class="has-background-light">
                <th class="has-text-centered">SN</th>
                <th class="has-text-centered">From State</th>
                <th class="has-text-centered">To State</th>
                <th class="has-text-centered">Event</th>
                <th class="has-text-centered">Timestamp</th>
            </tr>
            </thead>
            <tbody>
            <#if ctx.transitionHistory??>
                <#list ctx.transitionHistory as el>
                    <tr>
                        <td class="has-text-centered">${el?index + 1}</td>
                        <td class="has-text-centered">${el.fromState}</td>
                        <td class="has-text-centered">${el.toState}</td>
                        <td class="has-text-centered">${el.event}</td>
                        <td class="has-text-centered">${el.timestamp?datetime}</td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</section>
<#include "../common/footer.ftlh">