<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-row">
    <div class="form-group col-md-6">
    <form method="get" action="/main" class="form-inline">
    <input type="text" name="firstName" class="form-control" value="${firstName!}" placeholder="Search by name">
    <button type="submit" class="btn btn-primary ml-2">Search</button>
    </form>
    </div>
    </div>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new Message
    </a>
    <div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
    <form method="post" enctype="multipart/form-data">
    <div class="form-group">
        <input type="text" class="form-control" name="firstName" placeholder="Введите имя" />
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="secondName" placeholder="Введите фамилию" />
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="phoneNumber" placeholder="Введите номер телефона">
    </div>
    <div class="form-group">
        <input type="text" class="form-control" name="points" placeholder="Введите количество начисленных баллов">
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div class="form-group">
        <button type="submit" class="btn btn-primary">Добавить</button>
    </div>
    </form>
    </div>
    </div>

    <div >
    <#list customers as customer>
        <div class="card my-3">
        <div class="m-2">
        <span>${customer.firstName}</span>
        <i>${customer.secondName}</i>
        </div>
        <div class="card-footer text-muted">
        ${customer.getUserName()}
        </div>
        </div>
    <#else>
        No message
    </#list>
    </div>
</@c.page>