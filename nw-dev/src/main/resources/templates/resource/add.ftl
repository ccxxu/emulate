<form class="app-form" id="resource_info_form">
    <input type="hidden" name="pid" value="${resource.pid}">
    <input type="hidden" name="fdid" <#if resource.fdid??>value="${resource.fdid}"</#if>>
    <table class="edit_table">
        <col width="20%">
        <col width="80%">
        <tr class="tr_02">
            <td class="bg_01">资源ID：</td>
            <td><input class="pct60 easyui-textbox" name="id" maxlength="30" <#if resource.id??>value="${resource.id}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">资源名称:</td>
            <td><input name="name" class="pct60 easyui-textbox" maxlength="100" <#if resource.name??>value="${resource.name}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">资源URL：</td>
            <td><input name="url" class="pct60 easyui-textbox"  maxlength="100" <#if resource.url??>value="${resource.url}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">叶子节点：</td>
            <td>
                <input name="state" type="radio" value="open" <#if !resource.state?? || resource.state!='closed'>checked</#if>></input>
                <span>是</span>
                <input name="state" type="radio" value="closed" <#if resource.state?? && resource.state=='closed'>checked</#if>></input>
                <span>否</span>
            </td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">次序：</td>
            <td><input class="pct60 easyui-textbox" maxlength="8" name="ordernum" <#if resource.ordernum??>value="${resource.ordernum}"</#if>/></td>
        </tr>
    </table>
</form>