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
                <input type="text" name="username" class="form-control" value="${username!}" placeholder="Поиск по user ID">
                <button type="submit" class="btn btn-primary ml-2">Поиск</button>
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
                <th scope="col">User ID</th>
                <th scope="col">Имя</th>
                <th scope="col">Фамилия</th>
                <th scope="col">Роль</th>
                <th scope="col">Баллы</th>
                <th scope="col">Скидка</th>
                <th scope="col">Добавить баллы</th>
                <th></th>
            </tr>
        </thead>
        <#if page??>
        <tbody>
            <#list page.content as user>
                <tr>
                    <td>${user.username}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><#if user.score??>${user.score}<#else>0</#if></td>
                    <form method="post" action="/user" class="form-inline">
                    <td>
                        <div class="row">
                            <div class="col-5">
                                <input type="number" name="discount" class="form-control ${(discountError?? && pointErrorUsername == user.getUsername())?string('is-invalid', '')}"
                                       value="20">
                                <#if discountError?? && pointErrorUsername == user.getUsername()>
                                    <div class="invalid-feedback">
                                    ${discountError}
                                    </div>
                                </#if>
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="row">
                            <div class="col-xs-2">
                                <input type="number" name="points" class="form-control ${(pointsError?? && pointErrorUsername == user.getUsername())?string('is-invalid', '')}"
                                       value="0" placeholder="${user.score}">
                                <#if pointsError?? && pointErrorUsername == user.getUsername()>
                                    <div class="invalid-feedback">
                                        ${pointsError}
                                    </div>
                                </#if>
                            </div>
                                <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                <input type="hidden" name="usernamePost" value="${user.getUsername()}" placeholder="${user.getUsername()}">
                            <div class="col-xs-2">
                                <button type="submit" class="btn btn-primary ml-2">Добавить</button>
                            </div>
                        </div>
                    </td>
                    </form>
                    <td><a href="/user/${user.username}">профиль</a></td>
                </tr>
            </#list>
        </tbody>
        </#if>
        <#if usersList??>
        <tbody>
            <#list usersList as user>
                <tr>
                    <td>${user.username}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><#if user.score??>${user.score}<#else>0</#if></td>
                    <form method="post" action="/user" class="form-inline">
                    <td>
                        <div class="row">
                            <div class="col-5">
                                <input type="number" name="discount" class="form-control ${(discountError?? && pointErrorUsername == user.getUsername())?string('is-invalid', '')}"
                                       value="20">
                                <#if discountError?? && pointErrorUsername == user.getUsername()>
                                    <div class="invalid-feedback">
                                        ${discountError}
                                    </div>
                                </#if>
                            </div>
                        </div>
                    </td>
                        <td>
                            <div class="row">
                                    <div class="col-xs-2">
                                        <input type="number" name="points" class="form-control ${(pointsError??)?string('is-invalid', '')}"
                                               value="0" placeholder="${user.score}">
                                        <#if pointsError??>
                                            <div class="invalid-feedback">
                                            ${pointsError}
                                            </div>
                                        </#if>
                                        </div>
                                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                                        <input type="hidden" name="usernamePost" value="${user.getUsername()}" placeholder="${user.getUsername()}">
                                        <div class="col-xs-2">
                                            <button type="submit" class="btn btn-primary ml-2">Добавить</button>
                                        </div>
                                    </div>
                                </div>
                        </td>
                    </form>
                    <td><a href="/user/${user.username}">профиль</a></td>
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