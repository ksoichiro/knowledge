package redcomet.knowledge.control;

import redcomet.common.log.Log;
import redcomet.common.log.LogFactory;
import redcomet.web.bean.MessageResult;
import redcomet.web.boundary.Boundary;
import redcomet.web.config.MessageStatus;

public class IndexControl extends Control {
	/** ログ */
	private static Log log = LogFactory.getLog(IndexControl.class);
	
	public Boundary msg() {
		MessageResult result = new MessageResult();
		result.setStatus(MessageStatus.Success.getValue());
		result.setMessage("OK");
		return send(result);
	}
	
}
