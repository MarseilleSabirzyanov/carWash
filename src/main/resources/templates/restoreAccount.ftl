<#import "parts/common.ftl" as c>
<@c.page>
<title>Profile</title>
    <form action="/restore_account" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" value="<#if oldEmail??>${oldEmail}</#if>"
                class="form-control ${(emailError??)?string('is-invalid', '')} ${(emailSuccess??)?string('is-valid', '')}"
                placeholder="some@some.com" />
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

    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">Сбросить пароль</button>
</@c.page>