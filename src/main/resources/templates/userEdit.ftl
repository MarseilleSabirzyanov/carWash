<#import "parts/common.ftl" as c>

<@c.page>
    <form action="/user" method="post" xmlns="http://www.w3.org/1999/html">
        <div class="form-group">
            <label for="inputUsername">Username</label>
            <input type="text" name="username" value="${user.username}" class="form-control ${(usernameError??)?string('is-invalid', '')}" id="inputUsername" placeholder="Email">
            <#if usernameError??>
                <div class="invalid-feedback">
                ${usernameError}
                </div>
            </#if>
        </div>
        <div class="form-group">
            <label for="inputEmail4">Email</label>
            <input type="email" name="email" value="${user.email}" class="form-control" id="inputEmail4" placeholder="Email">
        </div>
        <div class="form-group">
            <label for="inputScore">Score</label>
            <input type="text" name="score" value="${user.score}" class="form-control" id="inputScore" placeholder="Email">
        </div>
        <fieldset class="form-group">
            <div class="row">
                <legend class="col-form-label col-sm-2 pt-0">Roles</legend>
                    <div class="col-sm-10">
                        <#list roles as role>
                            <div class="form-check">
                                <label><input class="form-check-input" type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                            </div>
                        </#list>
                    </div>
                </legend>
            </div>
        </fieldset>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <div class="form-group row">
            <div class="col-sm-10">
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </div>
    </form>
</@c.page>