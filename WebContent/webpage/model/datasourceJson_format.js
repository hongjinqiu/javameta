var g_datasourceJson = {
	"detailData" : [],
	"displayName" : "系统用户",
	"id" : "SysUser",
	"inUsedDenyEdit" : false,
	"masterData" : {
		"allowCopy" : true,
		"bizField" : {
			"field" : [ {
				"allowCopy" : false,
				"allowDuplicate" : false,
				"allowEmpty" : false,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : true,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "代码",
				"extends" : "code",
				"fieldLength" : 20,
				"fieldName" : "",
				"fieldType" : "STRING",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "code",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : false
			}, {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : false,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "名称",
				"extends" : "name",
				"fieldLength" : 40,
				"fieldName" : "",
				"fieldType" : "STRING",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "name",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : false
			}, {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "D_TAOBAO_TYPE",
				"dictionaryWhere" : "",
				"displayName" : "类型",
				"extends" : "taobaoType",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "type",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : false
			}, {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "D_TAOBAO_STATUS",
				"dictionaryWhere" : "",
				"displayName" : "状态",
				"extends" : "taobaoStatus",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "status",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : false
			}, {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "昵称",
				"extends" : "nick",
				"fieldLength" : 100,
				"fieldName" : "",
				"fieldType" : "STRING",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "nick",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : false
			} ]
		},
		"displayName" : "主数据集",
		"fixField" : {
			"createBy" : {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "创建人",
				"extends" : "CREATE_BY",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "createBy",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : [ {
						"displayField" : "code,nick",
						"id" : "SysUserSelector",
						"name" : "SysUser",
						"relationDataSetId" : "A",
						"relationExpr" : {
							"mode" : "text",
							"value" : "true"
						},
						"relationModelId" : "SysUser",
						"valueField" : "id"
					} ]
				},
				"relationField" : true,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : false
			},
			"createTime" : {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "创建时间",
				"extends" : "CREATE_TIME",
				"fieldLength" : 14,
				"fieldName" : "",
				"fieldType" : "LONG",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "createTime",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : true
			},
			"createUnit" : {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "创建组织",
				"extends" : "CREATE_UNIT",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "createUnit",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : true
			},
			"modifyBy" : {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "最后修改人",
				"extends" : "MODIFY_BY",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "modifyBy",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : [ {
						"displayField" : "code,nick",
						"id" : "SysUserSelector",
						"name" : "SysUser",
						"relationDataSetId" : "A",
						"relationExpr" : {
							"mode" : "text",
							"value" : "true"
						},
						"relationModelId" : "SysUser",
						"valueField" : "id"
					} ]
				},
				"relationField" : true,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : true
			},
			"modifyTime" : {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "最后修改时间",
				"extends" : "MODIFY_TIME",
				"fieldLength" : 14,
				"fieldName" : "",
				"fieldType" : "LONG",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "modifyTime",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : true
			},
			"modifyUnit" : {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "最后修改组织",
				"extends" : "MODIFY_UNIT",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : true,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "modifyUnit",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : true
			},
			"primaryKey" : {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "主键",
				"extends" : "PRIMARY_KEY",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : false,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "id",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : false
			},
			"remark" : {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : true,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "A",
				"datasourceId" : "SysUser",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "备注",
				"extends" : "REMARK",
				"fieldLength" : 400,
				"fieldName" : "",
				"fieldType" : "STRING",
				"fixHide" : false,
				"fixReadOnly" : false,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "remark",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : true,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"zeroShowEmpty" : false
			}
		},
		"id" : "A"
	},
	"tableName" : "SysUser"
};