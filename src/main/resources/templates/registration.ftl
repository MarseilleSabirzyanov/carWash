<#import "parts/common.ftl" as c>
<#import "parts/login1.ftl" as l>

<@c.page>
    ${message?ifExists}
    <@l.login "/registration" true />
</@c.page>