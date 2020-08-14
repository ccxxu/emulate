<form class="app-form" id="user_info_form">
    <input type="hidden" name="superId" value="${user.superId?c}"/>
    <input type="hidden" name="userId" <#if user.userId??>value="${user.userId?c}"</#if>/>
    <input type="hidden" name="objType" value="0"/>
    <table class="edit_table">
        <#if user.superId==1>
            <tr class="tr_02">
                <td class="bg_01"><span><font color="red">*</font></span>单位类型：</td>
                <td>
                    <input name="orgType" type="radio" value="0" <#if user.orgType?? && user.orgType==0>checked</#if>></input>
                    <span>部机关</span>
                    <input name="orgType" type="radio" value="1" <#if user.orgType?? && user.orgType==1>checked</#if>></input>
                    <span>直属事业单位</span>
                    <input name="orgType" type="radio" value="2" <#if user.orgType?? && user.orgType==2>checked</#if>></input>
                    <span>商会</span>
                    <input name="orgType" type="radio" value="3" <#if user.orgType?? && user.orgType==3>checked</#if>></input>
                    <span>协会</span>
                    <input name="orgType" type="radio" value="4" <#if user.orgType?? && user.orgType==4>checked</#if>></input>
                    <span>学会</span>
                </td>
            </tr>
        </#if>
        <tr class="tr_03">
            <td class="bg_01"><span><font color="red">*</font></span>全称：</td>
            <td><input class="pct60 easyui-textbox" id="objName" name="objName" maxlength="30" <#if user.objName??>value="${user.objName}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">简称:</td>
            <td><input name="abbrName" class="pct60 easyui-textbox" maxlength="30" <#if user.abbrName??>value="${user.abbrName}"</#if>/></td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">原单位名：</td>
            <td><input name="aliasName" class="pct60 easyui-textbox"  maxlength="30" <#if user.aliasName??>value="${user.aliasName}"</#if>/></td>
        </tr>

        <tr class="tr_03">
            <td class="bg_01">是否显示：</td>
            <td>
                <input name="isValid" type="radio" value="1" <#if user.isValid?? && user.isValid!=0>checked</#if>></input>
                <span>是</span>
                <input name="isValid" type="radio" value="0" <#if user.isValid?? && user.isValid==0>checked</#if>></input>
                <span>否</span>
                <#if user.userId??>
                    <div id="validMsg" style="margin-left: 10px; color:red">修改时，子部门及其下面的所有用户的显示情况将和此选择相同，请慎重修改！</div>
                </#if>
            </td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">状态：</td>
            <td>
                <input name="state" type="radio" value="1" <#if user.state?? && user.state!=0>checked</#if>></input>
                <span>正常</span>
                <input name="state" type="radio" value="0" <#if user.state?? && user.state==0>checked</#if>></input>
                <span>已删除</span>
                <#if user.userId??>
                    <div id="stateMsg" style="margin-left: 10px; color:red">修改时，子部门及其下面的所有用户的状态将和此选择相同，请慎重修改！</div>
                </#if>
            </td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">单位级别：</td>
            <td>
                <select id="levelId" class="pct60 easyui-combobox" data-options="panelHeight:'auto'" name="levelId">
                    <option value="1">部级</option>
                    <option value="2">司级</option>
                    <option value="3">处级</option>
                    <option value="4">科级</option>
                    <option value="5">未定级</option>
                </select>
            </td>
        </tr>
        <tr class="tr_03">
            <td class="bg_01">司局处室编号：</td>
            <td><input class="pct60 easyui-textbox" id="deptNo" name="deptNo" maxlength="10" <#if user.deptNo??>value="${user.deptNo}"</#if>/></td>
        </tr>
        <tr>
            <td class="bg_01"><span><font color="red">*</font></span>次序：</td>
            <td><input class="pct60 easyui-textbox" id="displayOrder" maxlength="8" name="displayOrder" <#if user.displayOrder??>value="${user.displayOrder?c}"</#if>/><span><font color="red"></font></span></td>
        </tr>
    </table>
</form>