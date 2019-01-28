<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>
    <#if errorMessage??>
        <div class="alert alert-danger" role="alert">
        ${errorMessage!}
        </div>
    </#if>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/user" class="form-inline">
                <input type="text" name="username" class="form-control" value="${username!}" placeholder="Search by name">
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>
    <#if page??>
        <@p.pager url page />
    </#if>
    <div>
        <table class="table">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Role</th>
            <th scope="col">Total points</th>
            <th scope="col">Add points</th>
            <th></th>
        </tr>
        </thead>
        <#if page??>
        <tbody>
            <#list page.content as user>
                <tr>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td>${user.score}</td>
                    <td>
                    <form method="post" action="/user" class="form-inline">
                        <div class="row">
                            <div class="col-xs-2">
                                <input type="number" name="points" class="form-control" value="0" placeholder="${user.score}">
                                <select class="custom-select">
                                    <option value="1">10</option>
                                    <option value="2">20</option>
                                    <option value="3">30</option>
                                </select>
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                <input type="hidden" name="usernamePost" value="${user.getUsername()}" placeholder="${user.getUsername()}">
                                <button type="submit" class="btn btn-primary ml-2">Add points</button>
                            </div>
                        </div>
                    </form>
                    </td>
                    <td><a href="/user/${user.id}">edit</a></td>
                </tr>
            </#list>
        </tbody>
        </#if>
        <#if usersList??>
        <tbody>
            <#list usersList as user>
                <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td>${user.score}</td>
                <form method="post" action="/user" class="form-inline">
                    <td>
                        <div class="row">
                                <div class="col-xs-2">
                                    <input type="number" name="points" class="form-control" value="0" placeholder="${user.score}">
                                    <div class="form-group">
                                        <select name="discount" class="custom-select">
                                            <option name="discount" value="one">10</option>
                                            <option name="discount" value="two">20</option>
                                            <option name="discount" value="three">30</option>
                                        </select>
                                    </div>
                                    <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                    <input type="hidden" name="usernamePost" value="${user.getUsername()}" placeholder="${user.getUsername()}">
                                    <button type="submit" class="btn btn-primary ml-2">Add points</button>
                                </div>
                            </div>
                    </td>
                </form>
                <td><a href="/user/${user.id}">edit</a></td>
                </tr>
            </#list>
        </tbody>
        </#if>
        </table>
    </div>
    <#if page??>
        <@p.pager url page />
    </#if>
</@c.page>