<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
        </div>
    </#if>
    <#--<#if passwordError??>
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
    </#if>-->
    <@l.login "/login" false/>
    <div class="mt-2">
        <td><a href="/restore_account">Забыли пароль?</a></td>
    </div>
</@c.page>