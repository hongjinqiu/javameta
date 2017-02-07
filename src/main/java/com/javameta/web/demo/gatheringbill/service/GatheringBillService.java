package com.javameta.web.demo.gatheringbill.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.model.ValueBusinessObject;
import com.javameta.model.datasource.Datasource;
import com.javameta.model.handler.DiffDataRow;
import com.javameta.model.template.FormTemplate;
import com.javameta.value.ValueString;
import com.javameta.web.demo.gatheringbill.dao.GatheringBillDao;
import com.javameta.web.form.service.AFormService;

@Service
@Transactional
public class GatheringBillService extends AFormService {
	@Autowired
	private GatheringBillDao gatheringBillDao;
	
	@Override
	public void afterNewData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo) {
		valueBo.getMasterData().put("balanceNo", ValueString.get("put in program"));
	}
	
	@Override
	public void afterSaveData(HttpServletRequest request, HttpServletResponse response, Datasource datasource, FormTemplate formTemplate, ValueBusinessObject valueBo,
			List<DiffDataRow> diffDateRowLi) {
		System.out.println("66666666666666666666666");
		for (DiffDataRow diffDataRow: diffDateRowLi) {
			if (diffDataRow.getDestData() != null) {
				System.out.println(diffDataRow.getFieldLi().get(0).getDataSetId());
			}
		}
		System.out.println("66666666666666666666666");
	}

	public GatheringBillDao getGatheringBillDao() {
		return gatheringBillDao;
	}

	public void setGatheringBillDao(GatheringBillDao gatheringBillDao) {
		this.gatheringBillDao = gatheringBillDao;
	}
}
