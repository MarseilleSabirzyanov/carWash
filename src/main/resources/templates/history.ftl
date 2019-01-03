<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>
    <#if errorMessage??>
    <div class="alert alert-danger" role="alert">
        ${errorMessage!}
    </div>
    </#if>

    <form method="get" action="/history" class="form-inline mb-3">
    <div class="form-row">
        <div class="form-group col-md-6">
            <input type="text" name="username" class="form-control" id="username" value="${username!}" placeholder="Search by username">
        </div>
         <div class="form-group col-md-6">
                <input type="text" name="admin" class="form-control" value="${admin!}" placeholder="Search by admin name">
        </div>
    </div>
    <div class="form-row ml-2">
        <div class="form-group col-md-6">
                <input type="date" name="dateFrom" class="form-control" value="${dateFrom!}" placeholder="Search by date">
        </div>
        <div class="form-group col-md-6">
                <input type="date" name="dateTo" class="form-control" value="${dateTo!}" placeholder="Search by date">
        </div>
    </div>
                <button type="submit" class="btn btn-primary ml-2">Search</button>
    </form>

    <@p.pager url page />
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
    <#list page.content as history>
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
    <@p.pager url page />
</@c.page>