
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javameta.web.form.service.AFormService;

@Service
@Transactional
public class {template}Service extends AFormService {
	@Autowired
	private {template}Dao {lowerTemplate}Dao;

	public {template}Dao get{template}Dao() {
		return {lowerTemplate}Dao;
	}

	public void set{template}Dao({template}Dao {lowerTemplate}Dao) {
		this.{lowerTemplate}Dao = {lowerTemplate}Dao;
	}
}
