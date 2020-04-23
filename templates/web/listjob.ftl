<#import "/templates/system/common/cstudio-support.ftl" as studio />
<div class="content__title">
    <div class="container">
        <div>
          <h1 class="text--uppercase">${contentModel.title_s}</h1>
        </div>
    </div>
  </div>
  
  <div class="content__details container text-white">
      <div class="row">
        <div class="col-lg6 col-md-6 col-sm-6 col-6" style="padding: 0;">
            <#list jobs as job>
            	<p>${job.title}</p>
            </#list>
        </div>
        <div class="col-lg6 col-md-6 col-sm-6 col-6" >
            <img src="${contentModel.image_s}" alt="" width="100%">
        </div>
      </div>
  </div>
<@studio.toolSupport />