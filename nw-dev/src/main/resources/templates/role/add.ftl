<form class="app-form" id="role_info_form">
    <input type="hidden" name="fdid" <#if role.fdid??>value="${role.fdid}"</#if>>
    <table class="edit_table">
        <col width="20%">
        <col width="80%">
        <tr class="tr_02">
            <td class="bg_01">角色标记：</td>
            <td><input class="pct60 easyui-textbox" id="name" name="name" maxlength="100" <#if role.name??>value="${role.name}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">角色名称：</td>
            <td><input name="notes" class="pct60 easyui-textbox" maxlength="100" <#if role.notes??>value="${role.notes}"</#if>/></td>
        </tr>
    </table>
</form>