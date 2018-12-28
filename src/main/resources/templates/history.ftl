<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-row">
    <div class="form-group col-md-6">
    <form method="get" action="/history" class="form-inline">
    <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Search by user name">
    <button type="submit" class="btn btn-primary ml-2">Search</button>
    </form>
    </div>
    </div>

    <div>
    <table class="table">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Date</th>
                <th scope="col">Total</th>
                <th scope="col">User name</th>
                <th scope="col">Admin name</th>
                <th scope="col">Operation</th>
                <th scope="col"></th>
            </tr>
        </thead>
    <tbody>
    <#list histories as history>
        <tr>
        <td>${history.date}</td>
        <td>${history.total}</td>
        <td>${history.getUser().username}</td>
        <td>${history.getAdmin().username}</td>
        <td>${history.getOp()}</td>
        <td><a href="/user/${history.getUser().id}">edit</a></td>
        </tr>
    </#list>
    </tbody>
    </table>
    </div>
</@c.page>