<table class="table is-stripped is-hoverable is-fullwidth is-bordered">
    <#list infoBox as key, value>
        <tr>
            <td class="has-background-light"><b>${key}</b></td>
            <td>${value}</td>
        </tr>
    </#list>
</table>