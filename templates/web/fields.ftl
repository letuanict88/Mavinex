<#import "/templates/system/common/cstudio-support.ftl" as studio />
<div class="mavinex-content" <@studio.iceAttr component=contentModel/>>
    <div class="mavinex-content__container">
        <div class="mavinex-content__header">
            <div class="mavinex-content__header-content">
                <div class="row">
                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                    <h1 style="color: red">${contentModel.itemTitle_s}</h1></div>
                </div>
                </div>
        </div>

        <div class="mavinex-content__body">
            <div class="mavinex-content__body-content container">
                ${contentModel.itemContent_html}
                <div class="row" <@studio.componentContainerAttr target="carousels" objectId=contentModel.objectId/>>
                <#if contentModel.carousels_o?? && contentModel.carousels_o.item??>
                    <div style="color: #fff;">
                    <#list contentModel.carousels_o.item as carousel>
                            <@renderComponent component=carousel />
                    </#list>
                    </div>
                </#if>
            </div>
            </div>
        </div>
    </div>
</div>
<@studio.toolSupport />