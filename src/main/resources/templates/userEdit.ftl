<#import "parts/common.ftl" as c>

<@c.page>
    <form action="/user/${user.username}" method="post" name="save" xmlns="http://www.w3.org/1999/html"
                                    xmlns="http://www.w3.org/1999/html">
        <div class="form-group row mt-4">
            <label class="col-sm-2">User ID:</label>
            <div class="col-sm-6">
                ${user.username}
            </div>
        </div>
        <#--<div class="form-group row">
            <label for="inputUsername" class="col-sm-2 col-form-label">Username</label>
            <div class="col-sm-6">
                <input type="text" name="username" value="${oldUsername}" class="form-control ${(usernameError??)?string('is-invalid', '')} ${(usernameSuccess??)?string('is-valid', '')}" id="inputUsername" >
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
        </div>-->
        <div class="form-group row">
            <label for="inputName" class="col-sm-2 col-form-label">Name</label>
            <div class="col-sm-6">
                <input type="text" name="name" value="${oldName}" class="form-control ${(nameError??)?string('is-invalid', '')} ${(nameSuccess??)?string('is-valid', '')}" id="inputName" placeholder="Name">
                <#if nameError??>
                    <div class="invalid-feedback">
                    ${nameError}
                    </div>
                </#if>
                <#if nameSuccess??>
                    <div class="valid-feedback">
                    ${nameSuccess}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label for="inputSurname" class="col-sm-2 col-form-label">Surname</label>
            <div class="col-sm-6">
                <input type="text" name="surname" value="${oldSurname}" class="form-control ${(surnameError??)?string('is-invalid', '')} ${(surnameSuccess??)?string('is-valid', '')}" id="inputSurname" placeholder="Surname">
                <#if surnameError??>
                    <div class="invalid-feedback">
                    ${surnameError}
                    </div>
                </#if>
                <#if surnameSuccess??>
                    <div class="valid-feedback">
                    ${surnameSuccess}
                    </div>
                </#if>
            </div>
        </div>
        <div class="form-group row">
            <label for="inputPhone" class="col-sm-2 col-form-label">Phone number</label>
            <div class="col-sm-6">
                <input type="text" name="phone" value="${oldPhone}" class="form-control ${(phoneError??)?string('is-invalid', '')} ${(phoneSuccess??)?string('is-valid', '')}" id="inputPhone" placeholder="Phone number">
                <#if phoneError??>
                    <div class="invalid-feedback">
                    ${phoneError}
                    </div>
                </#if>
                <#if phoneSuccess??>
                    <div class="valid-feedback">
                    ${phoneSuccess}
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

    <div class="form-group row">
    <label for="inputActivatePoints" class="col-sm-2 col-form-label">Activate points</label>
        <div class="col-sm-6">
            <input type="text" name="activatedPoints" value="${oldActivatedPoints}" class="form-control ${(activatedPointsError??)?string('is-invalid', '')} ${(activatedPointsSuccess??)?string('is-valid', '')}" id="inputActivatePoints" >
            <#if activatedPointsError??>
                <div class="invalid-feedback">
                    ${activatedPointsError}
                </div>
            </#if>
            <#if activatedPointsSuccess??>
                <div class="valid-feedback">
                    ${activatedPointsSuccess}
                </div>
            </#if>
        </div>
    </div>
        <button type="submit" class="btn btn-primary" name="activatePoints" value="activatePoints">Use points</button>
    </form>
</@c.page>