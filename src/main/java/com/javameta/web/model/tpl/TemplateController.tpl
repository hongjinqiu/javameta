
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javameta.web.form.controller.AFormController;
import com.javameta.web.form.service.AFormService;

@Scope("prototype")
@Controller
@RequestMapping("/{lowerTemplate}")
public class {template}Controller extends AFormController {
	private static final long serialVersionUID = 1L;
	@Autowired
	private {template}Service {lowerTemplate}Service;

	@Override
	public AFormService getService() {
		return {lowerTemplate}Service;
	}

	public {template}Service get{template}Service() {
		return {lowerTemplate}Service;
	}

	public void set{template}Service({template}Service {lowerTemplate}Service) {
		this.{lowerTemplate}Service = {lowerTemplate}Service;
	}

}
