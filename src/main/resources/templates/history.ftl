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
            <input type="text" name="username" class="form-control" id="username" value="${username!}" placeholder="Поиск по user ID">
        </div>
         <div class="form-group col-md-6">
                <input type="text" name="admin" class="form-control" value="${admin!}" placeholder="Поиск по имени админа">
        </div>
    </div>
    <div class="form-row ml-2">
        <div class="form-group col-md-6">
                <input type="date" name="dateFrom" class="form-control" value="${dateFrom!}" placeholder="Поиск по дате">
        </div>
        <div class="form-group col-md-6">
                <input type="date" name="dateTo" class="form-control" value="${dateTo!}" placeholder="Поиск по дате">
        </div>
    </div>
                <button type="submit" class="btn btn-primary ml-2">Поиск</button>
    </form>

    <@p.pager url page />
    <div>
    <table class="table">
        <thead class="thead-dark">
            <tr>
                <th scope="col">Дата</th>
                <th scope="col">Баллы</th>
                <th scope="col">User ID</th>
                <th scope="col">Админ</th>
                <th scope="col">Операция</th>
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
        <td><a href="/user/${history.getUser().id}">профиль</a></td>
        </tr>
    </#list>
    </tbody>
    </table>
    </div>
    <@p.pager url page />
</@c.page>