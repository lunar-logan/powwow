<#include "../common/header.ftlh">
<section class="section is-grey">
    <div class="container">
        <#include "actionBar.ftlh">
        <table class="table is-striped is-hoverable is-fullwidth">
            <thead>
            <tr>
                <th>SN</th>
                <th>FSM ID</th>
                <th>Description</th>
                <th>Starting State</th>
                <th>Accepting States</th>
            </tr>
            </thead>
            <tbody>
            <#list fsms as fsm>
                <tr>
                    <td>${(fsm?index)+1}</td>
                    <td><a href="/fsm/${fsm.id}">${fsm.id}</a></td>
                    <td>${fsm.description!'None'}</td>
                    <td>${fsm.startingState}</td>
                    <td>${fsm.acceptingStates?join(", ")}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</section>
<#include "../common/footer.ftlh">