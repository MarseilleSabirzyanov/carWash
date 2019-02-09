<#import "parts/common.ftl" as c>

<@c.page>
    <form action="/user/${user.id}" method="post" name="save" xmlns="http://www.w3.org/1999/html"
                                    xmlns="http://www.w3.org/1999/html">
        <h5>${user.username}</h5>
        <div class="form-group row">
            <label for="inputUsername" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-6">
                <input type="text" name="username" value="${oldUsername}" class="form-control ${(usernameError??)?string('is-invalid', '')} ${(usernameSuccess??)?string('is-valid', '')}" id="inputUsername" placeholder="Email">
                <#if usernameError??>
                    <div class="invalid-feedback">
                    ${usernameError}
                    </div>
                </#if>
                <#if usernameSuccess??>
                    <div class="valid-feedback">
                    ${usernameSuccess}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label for="inputEmail4" class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-6">
                <input type="email" name="email" value="${oldEmail}" class="form-control ${(emailError??)?string('is-invalid', '')} ${(emailSuccess??)?string('is-valid', '')}" id="inputEmail4" placeholder="Email">
                <#if emailError??>
                    <div class="invalid-feedback">
                    ${emailError}
                    </div>
                </#if>
                <#if emailSuccess??>
                    <div class="valid-feedback">
                    ${emailSuccess}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
                <legend class="col-sm-2 col-form-label">Roles</legend>
                    <div class="col-sm-6">
                        <#list roles as role>
                            <div class="form-check">
                                <label><input class="form-check-input" type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                            </div>
                        </#list>
                    </div>
                </legend>
        </div>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-primary " name="save" value="save">Save</button>
        <input type="hidden" value="${user.id}" name="userId">
        <div class="form-group row mt-4">
            <label class="col-sm-2">Total points:</label>
            <div class="col-sm-6">
                ${user.score}
            </div>
        </div>
        <button <#if 500 gte user.getScore()>disabled</#if> type="submit" class="btn btn-primary" name="activatePoints" value="activatePoints">Use points</button>
    </form>
</@c.page>