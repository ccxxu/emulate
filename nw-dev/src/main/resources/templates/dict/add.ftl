<form class="app-form" id="dict_info_form">
    <input type="hidden" name="pid" <#if dict.pid??>value="${dict.pid}"</#if>>
    <input type="hidden" name="fdid" <#if dict.fdid??>value="${dict.fdid}"</#if>>
    <table class="edit_table">
        <col width="20%">
        <col width="80%">
        <tr class="tr_02">
            <td class="bg_01">ID：</td>
            <td><input class="pct60 easyui-textbox" name="id" maxlength="30" <#if dict.id??>value="${dict.id}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">整形代码：</td>
            <td><input class="pct60 easyui-textbox" name="code" maxlength="30" <#if dict.code??>value="${dict.code}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">简称:</td>
            <td><input name="name" class="pct60 easyui-textbox" maxlength="100" <#if dict.name??>value="${dict.name}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">全称：</td>
            <td><input name="fullname" class="pct60 easyui-textbox"  maxlength="100" <#if dict.fullname??>value="${dict.fullname}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">叶子节点：</td>
            <td>
                <input name="state" type="radio" value="open" <#if !dict.state?? || dict.state!='closed'>checked</#if>></input>
                <span>是</span>
                <input name="state" type="radio" value="closed" <#if dict.state?? && dict.state=='closed'>checked</#if>></input>
                <span>否</span>
            </td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">是否有效：</td>
            <td>
                <input name="enable" type="radio" value="1" <#if !dict.enable?? || dict.enable!='0'>checked</#if>></input>
                <span>有效</span>
                <input name="enable" type="radio" value="0" <#if dict.enable?? && dict.enable=='0'>checked</#if>></input>
                <span>无效</span>
            </td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">次序：</td>
            <td><input class="pct60 easyui-textbox" maxlength="8" name="displayOrder" <#if dict.displayOrder??>value="${dict.displayOrder}"</#if>/></td>
        </tr>
    </table>
</form>