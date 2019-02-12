<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>
    <title>Profile</title>
<form method="post">
    <h5>${username}</h5>
    <#if message??>
        <div class="alert alert-success" role="alert">
            ${message}
        </div>
    </#if>
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
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')} ${(passwordSuccess??)?string('is-valid', '')}" placeholder="Password" />
            <#if passwordError??>
                <div class="invalid-feedback">
                ${passwordError}
                </div>
            </#if>
            <#if passwordSuccess??>
                <div class="valid-feedback">
                ${passwordSuccess}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password confirmation:</label>
        <div class="col-sm-6">
            <input type="password" name="password2" class="form-control ${(password2Error??)?string('is-invalid', '')}" placeholder="Password confirmation" />
            <#if password2Error??>
                <div class="invalid-feedback">
                ${password2Error}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control ${(emailSuccess??)?string('is-valid', '')} ${(emailError??)?string('is-invalid', '')}" placeholder="some@some.com" value="<#if email??>${email}<#else>some@some</#if>"/>
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
        <label class="col-sm-2">Total points:</label>
        <div class="col-sm-6">
            <#if score??>${score}<#else>0</#if>
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">Save</button>

    <div class="my-3">
        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
            Operations history
        </button>
    </div>
    <div class="collapse" id="collapseExample">
        <div class="card card-body">
            <div>
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">Date</th>
                        <th scope="col">Total</th>
                        <th scope="col">Admin name</th>
                        <th scope="col">Operation</th>
                    </tr>
                </thead>
                    <tbody>
                    <#list page.content as history>
                        <tr>
                        <td>${history.date}</td>
                        <td>${history.total}</td>
                        <td>${history.getAdmin().username}</td>
                        <td>${history.getOp()}</td>
                        </tr>
                    </#list>
                    </tbody>
            </table>
            </div>
        </div>
    </div>
</form>
</@c.page>