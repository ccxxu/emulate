<form class="app-form" id="group_info_form">
    <input type="hidden" name="fdid" <#if group.fdid??>value="${group.fdid}"</#if>>
    <table class="edit_table">
        <col width="20%">
        <col width="80%">
        <tr class="tr_02">
            <td class="bg_01">用户组ID：</td>
            <td><input class="pct60 easyui-textbox" id="id" name="id" maxlength="30" <#if group.id??>value="${group.id}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">用户组名称：</td>
            <td><input name="name" class="pct60 easyui-textbox" maxlength="100" <#if group.name??>value="${group.name}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">用户组KEY：</td>
            <td><input name="descc" class="pct60 easyui-textbox" maxlength="100" <#if group.descc??>value="${group.descc}"</#if>/></td>
        </tr>
    </table>
</form>