<form class="app-form" id="user_info_form">
    <input type="hidden" name="superId" value="${user.superId?c}"/>
    <input type="hidden" name="userId" <#if user.userId??>value="${user.userId?c}"</#if>/>
    <input type="hidden" name="objType" value="1"/>
    <table class="edit_table">
        <tr class="tr_02">
            <td class="bg_user_01">司局：</td>
            <td class="bg_user_02"><#if dept0??>${dept0}</#if></td>
            <td class="bg_user_01">处室：</td>
            <td class="bg_user_02"><#if dept1??>${dept1}</#if></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_user_01">名称：</td>
            <td class="bg_user_02"><input class="pct30 easyui-textbox" id="abbrName" name="abbrName" maxlength="30" <#if user.abbrName??>value="${user.abbrName}"</#if>/></td>
            <td class="bg_user_01">职务：</td>
            <td class="bg_user_02"><input class="pct30 easyui-textbox" id="aliasName" name="aliasName" maxlength="30" <#if user.aliasName??>value="${user.aliasName}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_user_01">性别：</td>
            <td class="bg_user_02">
                <input name="sex" type="radio" class="easyui-radiobox" value="1" <#if user.sex?? && user.sex!='0'>checked</#if>></input>
                <span>是</span>
                <input name="sex" type="radio" class="easyui-radiobox" value="0" <#if user.sex?? && user.sex=='0'>checked</#if>></input>
                <span>否</span>
            </td>
            <td class="bg_user_01">用户级别：</td>
            <td class="bg_user_02">
                <input name="levelId" class="pct30 easyui-textbox" maxlength="30" <#if user.levelId??>value="${user.levelId}"</#if>/>
            </td>
        </tr>
        <tr class="tr_03">
            <td class="bg_user_01">登录名：</td>
            <td class="bg_user_02"><input name="loginName" class="pct30 easyui-textbox"  maxlength="30" <#if user.loginName??>value="${user.loginName}"</#if>/></td>
            <td class="bg_user_01">次序：</td>
            <td class="bg_user_02"><input name="displayOrder" class="pct30 easyui-textbox"  maxlength="30" <#if user.displayOrder??>value="${user.displayOrder}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_user_01">内网邮箱：</td>
            <td class="bg_user_02"><input name="useremail" class="pct30 easyui-textbox"  maxlength="30" <#if user.useremail??>value="${user.useremail}"</#if>/></td>
            <td class="bg_user_01">外网邮箱：</td>
            <td class="bg_user_02"><input name="outemail" class="pct30 easyui-textbox"  maxlength="30" <#if user.outemail??>value="${user.outemail}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_user_01">电话：</td>
            <td class="bg_user_02"><input name="phone" class="pct30 easyui-textbox"  maxlength="30" <#if user.phone??>value="${user.phone}"</#if>/></td>
            <td class="bg_user_01">手机：</td>
            <td class="bg_user_02"><input name="mobile" class="pct30 easyui-textbox"  maxlength="30" <#if user.mobile??>value="${user.mobile}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_user_01">是否显示：</td>
            <td colspan="3">
                <input name="isValid" type="radio" value="1" <#if user.isValid?? && user.isValid!=0>checked</#if>></input>
                <span>是</span>
                <input name="isValid" type="radio" value="0" <#if user.isValid?? && user.isValid==0>checked</#if>></input>
                <span>否</span>
            </td>
        </tr>
    </table>
</form>