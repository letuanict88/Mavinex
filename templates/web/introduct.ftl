<#import "/templates/system/common/cstudio-support.ftl" as studio />
<div class="content" <@studio.iceAttr component=contentModel/>>
  <div class="content__title">
        <div class="container">
            <div class="row">
                <#list 0..1 as i>
                	<div class="col-lg-6 col-md-6 col-sm-6 col-6">
                      <h2 class="text--red text--title__small" >${contentModel.introduct_o.item[i].introductitemtitle_s}</h2>
                    </div>
                </#list>
            </div>
        </div>
      </div>
      <div class="content__details container">
          <div class="row">
            <#list 0..1 as i>
                <div class="col-lg6 col-md-6 col-sm-6 col-6">
                    <img src="${contentModel.introduct_o.item[i].introducitemavatar_s}" alt="" class="img--full">
                </div>
            </#list>
            
            <#list 0..1 as i>
                <div class="col-lg6 col-md-6 col-sm-6 col-6 seemore_btn">
                     <a class="mt-3 rounded-0" href="${contentModel.introduct_o.item[i].introductUrl_s}"> 
                        <span class="mr-1">Xem thêm</span>
                        <i class="fa fa-play text--red"></i>
                    </a>
                </div>
            </#list>
          </div>
      </div>

      <div class="content__title">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 col-md-6 col-sm-6 col-6" style="padding-left: 0px;" >
                  <h2 class="text--red text--title__small" >${intros[1].title}</h2>
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6 col-6" >
                  <h2 class="text--red text--title__small" >${intros[4].title}</h2>
                </div>
            </div>
        </div>
      </div>
      <div class="content__details container">
          <div class="row">
            <div class="col-lg6 col-md-6 col-sm-6 col-6" style="padding: 0;">
                <img src="${intros[1].avatar}" alt="" class="img--full">
            </div>
            <div class="col-lg6 col-md-6 col-sm-6 col-6" >
                <img src="${intros[4].avatar}" alt="" class="img--full">
            </div>
            <div class="col-lg6 col-md-6 col-sm-6 col-6 seemore_btn" style="padding: 0;">
                 <a class="mt-3 rounded-0" href="${intros[1].url}"> 
                    <span class="mr-1">Xem thêm</span>
                    <i class="fa fa-play text--red"></i>
                </a>
            </div>
            <div class="col-lg6 col-md-6 col-sm-6 col-6 seemore_btn" >
                 <a class="mt-3 rounded-0" href="${intros[4].url}"> 
                    <span class="mr-1">Xem thêm</span>
                    <i class="fa fa-play text--red"></i>
                </a>
            </div>
          </div>
      </div>    
</div>
<@studio.toolSupport />