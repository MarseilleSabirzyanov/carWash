<#macro login path isRegisterForm>
<form action="${path}" method="post">

    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User ID :</label>
        <div class="col-sm-6">
            <input  type="text" name="username" value="<#if user??>${user.username}</#if>"
                    class="form-control ${(usernameError?? || loginError??)?string('is-invalid', '')} ${(registrationSuccess??)?string('is-valid', '')}"
                    placeholder="User ID" />
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if>
        </div>
    </div>
    <#if isRegisterForm>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Имя :</label>
        <div class="col-sm-6">
            <input  type="text" name="name" value="<#if user??>${user.name}</#if>"
            class="form-control ${(nameError?? || loginError??)?string('is-invalid', '')} ${(registrationSuccess??)?string('is-valid', '')}"
                placeholder="Имя" />
            <#if nameError??>
                <div class="invalid-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Фамилия :</label>
        <div class="col-sm-6">
            <input  type="text" name="surname" value="<#if user??>${user.surname}</#if>"
            class="form-control ${(surnameError?? || loginError??)?string('is-invalid', '')} ${(registrationSuccess??)?string('is-valid', '')}"
                placeholder="Фамилия" />
            <#if surnameError??>
                <div class="invalid-feedback">
                    ${surnameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Номер телефона :</label>
        <div class="col-sm-6">
            <input  type="text" name="phone" value="<#if user??>${user.phone}</#if>"
                class="form-control ${(phoneError?? || loginError??)?string('is-invalid', '')} ${(registrationSuccess??)?string('is-valid', '')}"
                placeholder="Номер телефона" />
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
        <label class="col-sm-2 col-form-label">Пароль:</label>
        <div class="col-sm-6">
            <input type="password" name="password"
                   class="form-control ${(passwordError?? || loginError??)?string('is-invalid', '')}"
                   placeholder="Пароль" />
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
    <button class="btn btn-primary" type="submit"><#if isRegisterForm>Добавить<#else>Вход</#if></button>
</form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">Выход</button>
    </form>
</#macro>