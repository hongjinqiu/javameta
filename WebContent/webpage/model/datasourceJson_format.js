var g_datasourceJson = {
	"detailData" : [ {
		"allowCopy" : true,
		"allowEmpty" : false,
		"bizField" : {
			"field" : [ {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : false,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : "1"
				},
				"denyEditInUsed" : false,
				"dictionary" : "D_ACCOUNT_TYPE",
				"dictionaryWhere" : "",
				"displayName" : "费用账户类型",
				"extends" : "accountType",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : false,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "accountType",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "费用账户",
				"extends" : "accountId",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : false,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "accountId",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : false,
				"relationDS" : {
					"relationItem" : [ {
						"displayField" : "code,name",
						"id" : "CashAccountSelector",
						"name" : "CashAccount",
						"relationDataSetId" : "A",
						"relationExpr" : {
							"mode" : "js",
							"value" : "data[\"accountType\"] == \"1\""
						},
						"relationModelId" : "CashAccount",
						"valueField" : "id"
					}, {
						"displayField" : "code,name",
						"id" : "BankAccountSelector",
						"name" : "BankAccount",
						"relationDataSetId" : "A",
						"relationExpr" : {
							"mode" : "js",
							"value" : "data[\"accountType\"] == \"2\""
						},
						"relationModelId" : "BankAccount",
						"valueField" : "id"
					} ]
				},
				"relationField" : true,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "费用项目",
				"extends" : "incomeItemId",
				"fieldLength" : 11,
				"fieldName" : "",
				"fieldType" : "INT",
				"fixHide" : false,
				"fixReadOnly" : false,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "incomeItemId",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : false,
				"relationDS" : {
					"relationItem" : [ {
						"displayField" : "code,name",
						"id" : "IncomeItemSelector",
						"name" : "IncomeItem",
						"relationDataSetId" : "A",
						"relationExpr" : {
							"mode" : "text",
							"value" : "true"
						},
						"relationModelId" : "IncomeItem",
						"valueField" : "id"
					} ]
				},
				"relationField" : true,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
				"decimalPointLength" : 4,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "费用金额",
				"extends" : "amtFee",
				"fieldLength" : 19,
				"fieldName" : "",
				"fieldType" : "DECIMAL",
				"fixHide" : false,
				"fixReadOnly" : false,
				"formatExpr" : {
					"mode" : "",
					"value" : "localCurrency"
				},
				"id" : "amtFee",
				"limitMax" : "1000000000",
				"limitMin" : "-1000000000",
				"limitOption" : "limitRange",
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
				"zeroShowEmpty" : true
			} ],
			"xmlName" : "bizField"
		},
		"displayName" : "收款费用信息",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
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
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
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
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "B",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
				"zeroShowEmpty" : false
			},
			"xmlName" : "fixField"
		},
		"id" : "B",
		"xmlName" : "detailData"
	}, {
		"allowCopy" : true,
		"allowEmpty" : false,
		"bizField" : {
			"field" : [ {
				"allowCopy" : false,
				"allowDuplicate" : true,
				"allowEmpty" : false,
				"calcValueExpr" : {
					"mode" : "",
					"value" : ""
				},
				"checkInUsed" : true,
				"dataSetId" : "C",
				"datasourceId" : "GatheringBill",
				"decimalPointLength" : 0,
				"defaultValueExpr" : {
					"mode" : "text",
					"value" : ""
				},
				"denyEditInUsed" : false,
				"dictionary" : "",
				"dictionaryWhere" : "",
				"displayName" : "姓名测试",
				"extends" : "NAME_FIELD",
				"fieldLength" : 40,
				"fieldName" : "",
				"fieldType" : "STRING",
				"fixHide" : false,
				"fixReadOnly" : false,
				"formatExpr" : {
					"mode" : "",
					"value" : ""
				},
				"id" : "NAME_FIELD",
				"limitMax" : "",
				"limitMin" : "",
				"limitOption" : "unLimit",
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
				"zeroShowEmpty" : false
			} ],
			"xmlName" : "bizField"
		},
		"displayName" : "test",
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
				"dataSetId" : "C",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
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
				"xmlName" : "field",
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
				"dataSetId" : "C",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "C",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "C",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
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
				"xmlName" : "field",
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
				"dataSetId" : "C",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "C",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "C",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
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
				"dataSetId" : "C",
				"datasourceId" : "GatheringBill",
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
				"masterField" : false,
				"relationDS" : {
					"relationItem" : []
				},
				"relationField" : false,
				"validateExpr" : null,
				"validateMessage" : "",
				"xmlName" : "field",
				"zeroShowEmpty" : false
			},
			"xmlName" : "fixField"
		},
		"id" : "C",
		"xmlName" : "detailData"
	} ],
	"displayName" : "收款单",
	"id" : "GatheringBill",
	"inUsedDenyEdit" : false,
	"masterData" : {
		"allowCopy" : true,
		"bizField" : {
			"field" : [
					{
						"allowCopy" : false,
						"allowDuplicate" : true,
						"allowEmpty" : false,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "js",
							"value" : "new Date().getFullYear() + \"\" + (new Date().getMonth() + 1 < 10 ? \"0\" + (new Date().getMonth() + 1) : (new Date().getMonth() + 1)) + (new Date().getDate() < 10 ? \"0\" + new Date().getDate() : new Date().getDate())"
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "单据日期",
						"extends" : "billDate",
						"fieldLength" : 20,
						"fieldName" : "",
						"fieldType" : "DATE",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "billDate",
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
						"xmlName" : "field",
						"zeroShowEmpty" : true
					},
					{
						"allowCopy" : false,
						"allowDuplicate" : false,
						"allowEmpty" : false,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "单据编号",
						"extends" : "billNo",
						"fieldLength" : 30,
						"fieldName" : "",
						"fieldType" : "STRING",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "billNo",
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
						"xmlName" : "field",
						"zeroShowEmpty" : false
					},
					{
						"allowCopy" : false,
						"allowDuplicate" : true,
						"allowEmpty" : false,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : "1"
						},
						"denyEditInUsed" : false,
						"dictionary" : "D_FIN_BUSI_PROPERTY",
						"dictionaryWhere" : "",
						"displayName" : "业务属性",
						"extends" : "property",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "property",
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
						"xmlName" : "field",
						"zeroShowEmpty" : false
					},
					{
						"allowCopy" : false,
						"allowDuplicate" : true,
						"allowEmpty" : false,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : "1"
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "单据类型",
						"extends" : "billTypeId",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : true,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "billTypeId",
						"limitMax" : "",
						"limitMin" : "",
						"limitOption" : "unLimit",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : [ {
								"displayField" : "code,name",
								"id" : "BillTypeSelector",
								"name" : "BillType",
								"relationDataSetId" : "A",
								"relationExpr" : {
									"mode" : "text",
									"value" : "true"
								},
								"relationModelId" : "BillType",
								"valueField" : "id"
							} ]
						},
						"relationField" : true,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
						"zeroShowEmpty" : false
					},
					{
						"allowCopy" : false,
						"allowDuplicate" : true,
						"allowEmpty" : true,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "收款账户",
						"extends" : "gatheringAccountId",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "accountId",
						"limitMax" : "",
						"limitMin" : "",
						"limitOption" : "unLimit",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : [ {
								"displayField" : "code,name",
								"id" : "BankAccountSelector",
								"name" : "BankAccount",
								"relationDataSetId" : "A",
								"relationExpr" : {
									"mode" : "js",
									"value" : "data[\"property\"] == \"1\""
								},
								"relationModelId" : "BankAccount",
								"valueField" : "id"
							}, {
								"displayField" : "code,name",
								"id" : "CashAccountSelector",
								"name" : "CashAccount",
								"relationDataSetId" : "A",
								"relationExpr" : {
									"mode" : "js",
									"value" : "data[\"property\"] == \"2\""
								},
								"relationModelId" : "CashAccount",
								"valueField" : "id"
							}, {
								"displayField" : "code,name",
								"id" : "NullSelector",
								"name" : "Null",
								"relationDataSetId" : "A",
								"relationExpr" : {
									"mode" : "text",
									"value" : "true"
								},
								"relationModelId" : "Null",
								"valueField" : "id"
							} ]
						},
						"relationField" : true,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
						"zeroShowEmpty" : false
					},
					{
						"allowCopy" : false,
						"allowDuplicate" : true,
						"allowEmpty" : true,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "结算日期",
						"extends" : "balanceDate",
						"fieldLength" : 20,
						"fieldName" : "",
						"fieldType" : "DATE",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "balanceDate",
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
						"xmlName" : "field",
						"zeroShowEmpty" : true
					},
					{
						"allowCopy" : false,
						"allowDuplicate" : true,
						"allowEmpty" : true,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "结算方式",
						"extends" : "balanceTypeId",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "balanceTypeId",
						"limitMax" : "",
						"limitMin" : "",
						"limitOption" : "unLimit",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : [ {
								"displayField" : "code,name",
								"id" : "BalanceTypeSelector",
								"name" : "BalanceType",
								"relationDataSetId" : "A",
								"relationExpr" : {
									"mode" : "text",
									"value" : "true"
								},
								"relationModelId" : "BalanceType",
								"valueField" : "id"
							} ]
						},
						"relationField" : true,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
						"zeroShowEmpty" : false
					},
					{
						"allowCopy" : false,
						"allowDuplicate" : true,
						"allowEmpty" : true,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "结算号",
						"extends" : "balanceNo",
						"fieldLength" : 30,
						"fieldName" : "",
						"fieldType" : "STRING",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "balanceNo",
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
						"xmlName" : "field",
						"zeroShowEmpty" : false
					},
					{
						"allowCopy" : false,
						"allowDuplicate" : true,
						"allowEmpty" : false,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : "1"
						},
						"denyEditInUsed" : false,
						"dictionary" : "D_FIN_OBJECT_TYPE",
						"dictionaryWhere" : "",
						"displayName" : "收款对象类型",
						"extends" : "chamberlainType",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "chamberlainType",
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
						"xmlName" : "field",
						"zeroShowEmpty" : false
					},
					{
						"allowCopy" : false,
						"allowDuplicate" : true,
						"allowEmpty" : true,
						"calcValueExpr" : {
							"mode" : "",
							"value" : ""
						},
						"checkInUsed" : true,
						"dataSetId" : "A",
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "收款对象",
						"extends" : "chamberlainId",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "chamberlainId",
						"limitMax" : "",
						"limitMin" : "",
						"limitOption" : "unLimit",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : [
									{
										"displayField" : "code,name",
										"id" : "CustomerSelector",
										"name" : "Customer",
										"relationDataSetId" : "A",
										"relationExpr" : {
											"mode" : "js",
											"value" : "data[\"chamberlainType\"] == \"1\""
										},
										"relationModelId" : "Customer",
										"valueField" : "id"
									},
									{
										"displayField" : "code,name",
										"id" : "ProviderSelector",
										"name" : "Provider",
										"relationDataSetId" : "A",
										"relationExpr" : {
											"mode" : "js",
											"value" : "data[\"chamberlainType\"] == \"2\""
										},
										"relationModelId" : "Provider",
										"valueField" : "id"
									},
									{
										"displayField" : "code,nick",
										"id" : "SysUserSelector",
										"name" : "SysUser",
										"relationDataSetId" : "A",
										"relationExpr" : {
											"mode" : "js",
											"value" : "data[\"chamberlainType\"] == \"3\""
										},
										"relationModelId" : "SysUser",
										"valueField" : "id"
									}, {
										"displayField" : "code,name",
										"id" : "NullSelector",
										"name" : "Null",
										"relationDataSetId" : "A",
										"relationExpr" : {
											"mode" : "text",
											"value" : "true"
										},
										"relationModelId" : "Null",
										"valueField" : "id"
									} ]
						},
						"relationField" : true,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
						"zeroShowEmpty" : true
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "收款对象描述",
						"extends" : "chamberlainDesc",
						"fieldLength" : 30,
						"fieldName" : "",
						"fieldType" : "STRING",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "chamberlainDesc",
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
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "采购/业务员",
						"extends" : "sysUserId",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "sysUserId",
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
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "项目",
						"extends" : "articleId",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "articleId",
						"limitMax" : "",
						"limitMin" : "",
						"limitOption" : "unLimit",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : [ {
								"displayField" : "code,name",
								"id" : "ArticleSelector",
								"name" : "Article",
								"relationDataSetId" : "A",
								"relationExpr" : {
									"mode" : "text",
									"value" : "true"
								},
								"relationModelId" : "Article",
								"valueField" : "id"
							} ]
						},
						"relationField" : true,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : "1"
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "币别",
						"extends" : "currencyTypeId",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : true,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "currencyTypeId",
						"limitMax" : "",
						"limitMin" : "",
						"limitOption" : "unLimit",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : [ {
								"displayField" : "code,name",
								"id" : "CurrencyTypeSelector",
								"name" : "CurrencyType",
								"relationDataSetId" : "A",
								"relationExpr" : {
									"mode" : "text",
									"value" : "true"
								},
								"relationModelId" : "CurrencyType",
								"valueField" : "id"
							} ]
						},
						"relationField" : true,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : "1:1"
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "汇率",
						"extends" : "exchangeRateShow",
						"fieldLength" : 20,
						"fieldName" : "",
						"fieldType" : "STRING",
						"fixHide" : false,
						"fixReadOnly" : true,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "exchangeRateShow",
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
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 4,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : "1"
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "汇率隐藏",
						"extends" : "exchangeRate",
						"fieldLength" : 19,
						"fieldName" : "",
						"fieldType" : "FLOAT",
						"fixHide" : true,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "exchangeRate",
						"limitMax" : "100",
						"limitMin" : "0",
						"limitOption" : "limitRange",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : []
						},
						"relationField" : false,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 4,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "收款金额",
						"extends" : "amtGathering",
						"fieldLength" : 19,
						"fieldName" : "",
						"fieldType" : "DECIMAL",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : "localCurrency"
						},
						"id" : "amtGathering",
						"limitMax" : "1000000000",
						"limitMin" : "0",
						"limitOption" : "limitRange",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : []
						},
						"relationField" : false,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
						"zeroShowEmpty" : true
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 4,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "已冲销金额",
						"extends" : "amtAgainst",
						"fieldLength" : 19,
						"fieldName" : "",
						"fieldType" : "DECIMAL",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : "localCurrency"
						},
						"id" : "amtAgainst",
						"limitMax" : "1000000000",
						"limitMin" : "0",
						"limitOption" : "limitRange",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : []
						},
						"relationField" : false,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
						"zeroShowEmpty" : true
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 4,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "未冲销金额",
						"extends" : "amtNotAgainst",
						"fieldLength" : 19,
						"fieldName" : "",
						"fieldType" : "DECIMAL",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : "localCurrency"
						},
						"id" : "amtNotAgainst",
						"limitMax" : "1000000000",
						"limitMin" : "0",
						"limitOption" : "limitRange",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : []
						},
						"relationField" : false,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
						"zeroShowEmpty" : true
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "付款人全称",
						"extends" : "chamberlainName",
						"fieldLength" : 20,
						"fieldName" : "",
						"fieldType" : "STRING",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "chamberlainName",
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
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "银行代码",
						"extends" : "bankId",
						"fieldLength" : 11,
						"fieldName" : "",
						"fieldType" : "INT",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "bankId",
						"limitMax" : "",
						"limitMin" : "",
						"limitOption" : "unLimit",
						"masterField" : true,
						"relationDS" : {
							"relationItem" : [ {
								"displayField" : "code,name",
								"id" : "BankSelector",
								"name" : "Bank",
								"relationDataSetId" : "A",
								"relationExpr" : {
									"mode" : "text",
									"value" : "true"
								},
								"relationModelId" : "Bank",
								"valueField" : "id"
							} ]
						},
						"relationField" : true,
						"validateExpr" : null,
						"validateMessage" : "",
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "银行帐号",
						"extends" : "bankAccount",
						"fieldLength" : 30,
						"fieldName" : "",
						"fieldType" : "STRING",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "bankAccount",
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
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "联系人",
						"extends" : "linkman",
						"fieldLength" : 30,
						"fieldName" : "",
						"fieldType" : "STRING",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "linkman",
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
						"xmlName" : "field",
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
						"datasourceId" : "GatheringBill",
						"decimalPointLength" : 0,
						"defaultValueExpr" : {
							"mode" : "text",
							"value" : ""
						},
						"denyEditInUsed" : false,
						"dictionary" : "",
						"dictionaryWhere" : "",
						"displayName" : "联系电话",
						"extends" : "linkPhone",
						"fieldLength" : 50,
						"fieldName" : "",
						"fieldType" : "STRING",
						"fixHide" : false,
						"fixReadOnly" : false,
						"formatExpr" : {
							"mode" : "",
							"value" : ""
						},
						"id" : "linkPhone",
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
						"xmlName" : "field",
						"zeroShowEmpty" : false
					} ],
			"xmlName" : "bizField"
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
				"datasourceId" : "GatheringBill",
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
				"xmlName" : "field",
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
				"datasourceId" : "GatheringBill",
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
				"xmlName" : "field",
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
				"datasourceId" : "GatheringBill",
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
				"xmlName" : "field",
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
				"datasourceId" : "GatheringBill",
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
				"xmlName" : "field",
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
				"datasourceId" : "GatheringBill",
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
				"xmlName" : "field",
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
				"datasourceId" : "GatheringBill",
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
				"xmlName" : "field",
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
				"datasourceId" : "GatheringBill",
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
				"xmlName" : "field",
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
				"datasourceId" : "GatheringBill",
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
				"xmlName" : "field",
				"zeroShowEmpty" : false
			},
			"xmlName" : "fixField"
		},
		"id" : "A",
		"xmlName" : "masterData"
	},
	"tableName" : "GatheringBill",
	"xmlName" : "datasource"
};