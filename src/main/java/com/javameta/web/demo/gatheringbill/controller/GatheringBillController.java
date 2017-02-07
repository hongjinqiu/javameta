package com.javameta.web.demo.gatheringbill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javameta.web.demo.gatheringbill.service.GatheringBillService;
import com.javameta.web.form.controller.AFormController;
import com.javameta.web.form.service.AFormService;

@Scope("prototype")
@Controller
@RequestMapping("/gatheringBill")
public class GatheringBillController extends AFormController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private GatheringBillService gatheringBillService;

	@Override
	public AFormService getService() {
		return gatheringBillService;
	}

	public GatheringBillService getGatheringBillService() {
		return gatheringBillService;
	}

	public void setGatheringBillService(GatheringBillService gatheringBillService) {
		this.gatheringBillService = gatheringBillService;
	}

}
