<#import "parts/common.ftl" as c>
<#import "parts/pager.ftl" as p>

<@c.page>
    <h5>${username}</h5>
    ${message!}
<form method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control" placeholder="Password" />
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control" placeholder="some@some.com" value="${email!''}"/>
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