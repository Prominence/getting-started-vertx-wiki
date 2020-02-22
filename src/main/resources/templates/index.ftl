<#include "header.ftl">

<div class="row">

  <div class="col-md-12 mt-1">
    <div class="float-right">
      <form class="form-inline" action="/create" method="post">
        <div class="form-group">
          <input type="text" class="form-control" id="name" name="name" placeholder="New page name">
        </div>
        <button type="submit" class="btn btn-primary">Create</button>
      </form>
    </div>
    <h1 class="display-4">${title}</h1>
  </div>

  <div class="col-md-12 mt-1">
    <#list pages>
      <h2>Pages:</h2>
      <ul>
        <#items as page>
          <li><a href="/wiki/${page}">${page}</a></li>
        </#items>
      </ul>
    <#else>
      <p>The wiki is currently empty!</p>
    </#list>
    <#if backup_gist_url??>
      <div class="alert alert-success" role="alert">
        Successfully created a backup: <a href="${backup_gist_url}" target="_blank">${backup_gist_url}</a>
      </div>
    <#else>
      <a href="/backup" class="btn btn-secondary">Backup</a>
    </#if>
  </div>

</div>

<#include "footer.ftl">

