<#import "parts/common.ftl" as c>

<@c.page>
    <form method="get" action="/main">
    <input type="text" name="firstName" value="${firstName!}">
    <button type="submit">Найти</button>
    </form>
    <div>
    <form method="post">
        <input type="text" name="firstName" placeholder="Введите имя">
        <input type="text" name="secondName" placeholder="Введите фамилию">
        <input type="text" name="phoneNumber" placeholder="Введите номер телефона">
        <input type="number" name="points" placeholder="Введите количество начисленных баллов">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Добавить</button>
    </form>
    </div>

    <#list customers as customer>
        <div>
        <b>${customer.id}</b>
        <span>${customer.firstName}</span>
        <span>${customer.secondName}</span>
        <i>${customer.phoneNumber}</i>
        <i>${customer.points}</i>
        <strong>${customer.getUserName()}</strong>
        </div>
    <#else>
        No message
    </#list>
</@c.page>