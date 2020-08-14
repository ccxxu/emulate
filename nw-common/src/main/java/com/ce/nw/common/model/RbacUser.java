package com.ce.nw.common.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.ce.nw.common.util.StringUtil;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "RBAC_USER")
public class RbacUser implements Serializable{

	private static final long serialVersionUID = 5454155825314635342L;

    /**
     * userId       db_column: USER_ID 
     */ 	
	private Long userId;

    /**
     * abbrName       db_column: ABBR_NAME 
     */ 	
	@Length(max=200)
	private String abbrName;

    /**
     * aliasName       db_column: ALIAS_NAME 
     */
	@Length(max=400)
	private String aliasName;

    /**
     * createDate       db_column: CREATE_DATE 
     */ 	
	private Date createDate;

	/**
     * dengLuMing       db_column: DENG_LU_MING 
     */ 	
	@Length(max=512)
	private String dengluming;

	/**
     * displayOrder       db_column: DISPLAY_ORDER 
     */
	private Long displayOrder;

    /**
     * errorLogNum       db_column: ERROR_LOG_NUM 
     */ 	
	private Integer errorLogNum;

	/**
     * idcard       db_column: IDCARD 
     */ 	
	@Length(max=80)
	private String idcard;

	/**
     * mima       db_column: MIMA 
     */ 	
	@Length(max=512)
	private String mima;

	/**
     * modifierid       db_column: MODIFIERID 
     */ 	
	private Long modifierid;

	/**
     * modifiername       db_column: MODIFIERNAME 
     */ 	
	@Length(max=40)
	private String modifiername;

	/**
     * modifydate       db_column: MODIFYDATE 
     */ 	
	private Date modifydate;

	/**
     * newoaId       db_column: NEWOA_ID 
     */ 	
	@Length(max=80)
	private String newoaId;

	/**
     * note       db_column: NOTE 
     */ 	
	@Length(max=800)
	private String note;

	/**
     * objName       db_column: OBJ_NAME 
     */ 	
	@Length(max=400)
	private String objName;

	/**
     * 0 司局    1 人员       db_column: OBJ_TYPE 
     */ 	
	@Max(127)
	private Integer objType;

	/**
     * password       db_column: PASSWORD 
     */ 	
	@Length(max=20)
	private String password;

	/**
     * isValid       db_column: IS_VALID 
     */
	private Integer isValid;

    /**
     * loginName       db_column: LOGIN_NAME 
     */ 	
	@Length(max=30)
	private String loginName;

    /**
     * phone       db_column: PHONE 
     */ 	
	@Length(max=60)
	private String phone;

    /**
     * sex       db_column: SEX 
     */ 	
	@Length(max=4)
	private String sex;

    /**
     * sourceId       db_column: SOURCE_ID 
     */ 	
	private Long sourceId;

	/**
     * state       db_column: STATE 
     */ 	
	@Max(127)
	private Integer state;

    /**
     * useremail       db_column: USEREMAIL 
     */ 	
	@Length(max=400)
	private String useremail;

    /**
     * rankId       db_column: RANK_ID 
     */ 	
	private Long rankId;

	/**
     * levelId       db_column: LEVEL_ID 
     */ 	
	private Long levelId;

	/**
     * superId       db_column: SUPER_ID 
     */ 	
	@NotNull 
	private Long superId;

	/**
     * operateType       db_column: OPERATE_TYPE 
     */ 	
	private Integer operateType;

	/**
     * outemail       db_column: OUTEMAIL 
     */ 	
	@Length(max=100)
	private String outemail;

	/**
     * mobile       db_column: MOBILE 
     */ 	
	@Length(max=20)
	private String mobile;

	/**
     * 单位类型： 0 部机关  1 直属事业单位  2 商会  3 协会  4 学会       db_column: ORG_TYPE 
     */ 	
	private Integer orgType;

	/**
     * incomingFunctions       db_column: INCOMING_FUNCTIONS 
     */ 	
	@Length(max=200)
	private String incomingFunctions;

	/**
     * leftTimes       db_column: LEFT_TIMES 
     */ 	
	private Long leftTimes;

	/**
     * lastLoginDate       db_column: LAST_LOGIN_DATE 
     */ 	
	private Date lastLoginDate;

	/**
     * 是否不可以登录  0 或者空可以登录   1 不可以       db_column: CAN_NOT_LOGIN 
     */ 	
	private Integer canNotLogin;

	/**
     * 密码MD5       db_column: PASSWORDMD 
     */ 	
	@Length(max=100)
	private String passwordmd;

    /**
     * updatePwTime       db_column: UPDATE_PW_TIME 
     */ 	
	private Date updatePwTime;

	/**
     * 传真       db_column: FAX 
     */ 	
	@Length(max=100)
	private String fax;
	
	private String deptNo;
	
	@Column(length=600)
	private String secretLevel;//涉密等级
	
	@Column(length=100)
	private String roomNo;
	
	private String delIdentify;//是否离职注销，离职注销的此字段填写“已离职”

    /**
     * <p>显示标示：0，正常数据 1、被动显示或不显示</p>
     */
	private Integer validFlag;

	/**
     * <p>状态标示：0，正常数据 1、被动删除或正常</p>
     */
    private Integer stateFlag;

    @Column(length = 100)
    private String bcryptPwd;
	//columns END

	public RbacUser(){
	}

	public RbacUser(Long userId){
		this.userId = userId;
	}

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	@Id @GeneratedValue(generator="custom-id")
	@GenericGenerator(name="custom-id", strategy = "increment")
	@Column(name = "USER_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 10)
	public Long getUserId() {
		return this.userId;
	}
	
	@Column(name = "ABBR_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getAbbrName() {
		return this.abbrName;
	}
	
	public void setAbbrName(String value) {
		this.abbrName = value;
	}
	
	@Column(name = "ALIAS_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 400)
	public String getAliasName() {
		return this.aliasName;
	}
	
	public void setAliasName(String value) {
		this.aliasName = value;
	}
	
	@Column(name = "CREATE_DATE", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public Date getCreateDate() {
		return this.createDate;
	}
	
	public void setCreateDate(Date value) {
		this.createDate = value;
	}
	
	@Column(name = "DENGLUMING", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getDengluming() {
		return this.dengluming;
	}
	
	public void setDengluming(String value) {
		this.dengluming = value;
	}
	
	@Column(name = "DISPLAY_ORDER", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getDisplayOrder() {
		return this.displayOrder;
	}
	
	public void setDisplayOrder(Long value) {
		this.displayOrder = value;
	}
	
	@Column(name = "ERROR_LOG_NUM", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public Integer getErrorLogNum() {
		return this.errorLogNum;
	}
	
	public void setErrorLogNum(Integer value) {
		this.errorLogNum = value;
	}
	
	@Column(name = "IDCARD", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getIdcard() {
		return this.idcard;
	}
	
	public void setIdcard(String value) {
		this.idcard = value;
	}
	
	@Column(name = "MIMA", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public String getMima() {
		return this.mima;
	}
	
	public void setMima(String value) {
		this.mima = value;
	}
	
	@Column(name = "MODIFIERID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getModifierid() {
		return this.modifierid;
	}
	
	public void setModifierid(Long value) {
		this.modifierid = value;
	}
	
	@Column(name = "MODIFIERNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 40)
	public String getModifiername() {
		return this.modifiername;
	}
	
	public void setModifiername(String value) {
		this.modifiername = value;
	}
	
	@Column(name = "MODIFYDATE", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public Date getModifydate() {
		return this.modifydate;
	}
	
	public void setModifydate(Date value) {
		this.modifydate = value;
	}
	
	@Column(name = "NEWOA_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 80)
	public String getNewoaId() {
		return this.newoaId;
	}
	
	public void setNewoaId(String value) {
		this.newoaId = value;
	}
	
	@Column(name = "NOTE", unique = false, nullable = true, insertable = true, updatable = true, length = 800)
	public String getNote() {
		return this.note;
	}
	
	public void setNote(String value) {
		this.note = value;
	}
	
	@Column(name = "OBJ_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 400)
	public String getObjName() {
		return this.objName;
	}
	
	public void setObjName(String value) {
		this.objName = value;
	}
	
	@Column(name = "OBJ_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public Integer getObjType() {
		return this.objType;
	}
	
	public void setObjType(Integer value) {
		this.objType = value;
	}
	
	@Column(name = "PASSWORD", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String value) {
		this.password = value;
	}
	
	@Column(name = "IS_VALID", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public Integer getIsValid() {
		return this.isValid;
	}
	
	public void setIsValid(Integer value) {
		this.isValid = value;
	}
	
	@Column(name = "LOGIN_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public String getLoginName() {
		return this.loginName;
	}
	
	public void setLoginName(String value) {
		this.loginName = value;
	}
	
	@Column(name = "PHONE", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public String getPhone() {
		return this.phone;
	}
	
	public void setPhone(String value) {
		this.phone = value;
	}
	
	@Column(name = "SEX", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public String getSex() {
		return this.sex;
	}
	
	public void setSex(String value) {
		this.sex = value;
	}
	
	@Column(name = "SOURCE_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getSourceId() {
		return this.sourceId;
	}
	
	public void setSourceId(Long value) {
		this.sourceId = value;
	}
	
	@Column(name = "STATE", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public Integer getState() {
		return this.state;
	}
	
	public void setState(Integer value) {
		this.state = value;
	}
	
	@Column(name = "USEREMAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 400)
	public String getUseremail() {
		return this.useremail;
	}
	
	public void setUseremail(String value) {
		this.useremail = value;
	}
	
	@Column(name = "RANK_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getRankId() {
		return this.rankId;
	}
	
	public void setRankId(Long value) {
		this.rankId = value;
	}
	
	@Column(name = "LEVEL_ID", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getLevelId() {
		return this.levelId;
	}
	
	public void setLevelId(Long value) {
		this.levelId = value;
	}
	
	@Column(name = "SUPER_ID", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public Long getSuperId() {
		return this.superId;
	}
	
	public void setSuperId(Long value) {
		this.superId = value;
	}
	
	@Column(name = "OPERATE_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public Integer getOperateType() {
		return this.operateType;
	}
	
	public void setOperateType(Integer value) {
		this.operateType = value;
	}
	
	@Column(name = "OUTEMAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getOutemail() {
		return this.outemail;
	}
	
	public void setOutemail(String value) {
		this.outemail = value;
	}
	
	@Column(name = "MOBILE", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	@Column(name = "ORG_TYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public Integer getOrgType() {
		return this.orgType;
	}
	
	public void setOrgType(Integer value) {
		this.orgType = value;
	}
	
	@Column(name = "INCOMING_FUNCTIONS", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public String getIncomingFunctions() {
		return this.incomingFunctions;
	}
	
	public void setIncomingFunctions(String value) {
		this.incomingFunctions = value;
	}
	
	@Column(name = "LEFT_TIMES", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public Long getLeftTimes() {
		return this.leftTimes;
	}
	
	public void setLeftTimes(Long value) {
		this.leftTimes = value;
	}

	@Column(name = "LAST_LOGIN_DATE", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public Date getLastLoginDate() {
		return this.lastLoginDate;
	}
	
	public void setLastLoginDate(Date value) {
		this.lastLoginDate = value;
	}
	
	@Column(name = "CAN_NOT_LOGIN", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
	public Integer getCanNotLogin() {
		return this.canNotLogin;
	}
	
	public void setCanNotLogin(Integer value) {
		this.canNotLogin = value;
	}
	
	@Column(name = "PASSWORDMD", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getPasswordmd() {
		return this.passwordmd;
	}
	
	public void setPasswordmd(String value) {
		this.passwordmd = value;
	}

	@Column(name = "UPDATE_PW_TIME", unique = false, nullable = true, insertable = true, updatable = true, length = 7)
	public Date getUpdatePwTime() {
		return this.updatePwTime;
	}
	
	public void setUpdatePwTime(Date value) {
		this.updatePwTime = value;
	}
	
	@Column(name = "FAX", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getFax() {
		return this.fax;
	}
	
	public void setFax(String value) {
		this.fax = value;
	}
	
	@Column(name = "deptNo",  length = 100)
	public String getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}
	
	public String getSecretLevel() {
		return secretLevel;
	}
	public void setSecretLevel(String secretLevel) {
		this.secretLevel = secretLevel;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	@Column(name = "delIdentify",  length = 100)
	public String getDelIdentify() {
		return delIdentify;
	}

	public void setDelIdentify(String delIdentify) {
		this.delIdentify = delIdentify;
	}
    @Column(name = "valid_flag", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public Integer getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Integer validFlag) {
        this.validFlag = validFlag;
    }
    @Column(name = "state_flag", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public Integer getStateFlag() {
        return stateFlag;
    }

    public void setStateFlag(Integer stateFlag) {
        this.stateFlag = stateFlag;
    }

	@Column(name = "BCRYPT_PWD", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public String getBcryptPwd() {
		return bcryptPwd;
	}

	public void setBcryptPwd(String bcryptPwd) {
		this.bcryptPwd = bcryptPwd;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof RbacUser) {
			RbacUser other = (RbacUser) obj;
			return this.getUserId().intValue() == other.getUserId().intValue();
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (StringUtil.isNotNullOrEmpty(this.getUserId())) {
			return this.getUserId().hashCode();
		}
		return 0;
	}
}

