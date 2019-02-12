<#macro login path isRegisterForm>
<form action="${path}" method="post">

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <div class="col-sm-6">
            <input  type="text" name="username" value="<#if user??>${user.username}</#if>"
                    class="form-control ${(usernameError?? || loginError??)?string('is-invalid', '')} ${(registrationSuccess??)?string('is-valid', '')}"
                    placeholder="User name" />
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
    </div>
    <#if isRegisterForm>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Name :</label>
        <div class="col-sm-6">
            <input  type="text" name="name" value="<#if user??>${user.name}</#if>"
            class="form-control ${(nameError?? || loginError??)?string('is-invalid', '')} ${(registrationSuccess??)?string('is-valid', '')}"
                placeholder="Name" />
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Surname :</label>
        <div class="col-sm-6">
            <input  type="text" name="surname" value="<#if user??>${user.surname}</#if>"
            class="form-control ${(surnameError?? || loginError??)?string('is-invalid', '')} ${(registrationSuccess??)?string('is-valid', '')}"
                placeholder="Surname" />
            <#if surnameError??>
                <div class="invalid-feedback">
                    ${surnameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Phone number :</label>
        <div class="col-sm-6">
            <input  type="text" name="phone" value="<#if user??>${user.phone}</#if>"
                class="form-control ${(phoneError?? || loginError??)?string('is-invalid', '')} ${(registrationSuccess??)?string('is-valid', '')}"
                placeholder="Phone number" />
            <#if phoneError??>
                <div class="invalid-feedback">
                    ${phoneError}
                </div>
            </#if>
        </div>
    </div>
    </#if>
    <#if !isRegisterForm>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password"
                   class="form-control ${(passwordError?? || loginError??)?string('is-invalid', '')}"
                   placeholder="Password" />
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if>
            <#if activationCodeError??>
                <div class="invalid-feedback">
                ${activationCodeError}
                </div>
            </#if>
            <#if loginError??>
                <div class="invalid-feedback">
                ${loginError}
                </div>
            </#if>
        </div>
    </div>
    </#if>
        <#--<div class="form-group row">
            <label class="col-sm-2 col-form-label">Repeat password:</label>
            <div class="col-sm-6">
                <input type="password" name="password2"
                       class="form-control ${(password2Error??)?string('is-invalid', '')}"
                       placeholder="Retype password" />
                <#if password2Error??>
                    <div class="invalid-feedback">
                    ${password2Error}
                    </div>
                </#if>
            </div>
        </div>-->
    <#if isRegisterForm>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email:</label>
            <div class="col-sm-6">
                <input type="email" name="email" value="<#if user??>${user.email}</#if>"
                       class="form-control ${(emailError??)?string('is-invalid', '')} ${(registrationSuccess??)?string('is-valid', '')}"
                       placeholder="some@some.com" />
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
                <#if registrationSuccess??>
                    <div class="valid-feedback">
                        ${registrationSuccess}
                    </div>
                </#if>
            </div>
        </div>
    </#if>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <#--<#if !isRegisterForm><a href="/registration">Add new user</a></#if>-->
    <button class="btn btn-primary" type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>
</form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>