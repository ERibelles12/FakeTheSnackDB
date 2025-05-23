package de.pecus.api.vo.messaging;

import java.util.List;

public class SendSmsMessageResponseVO   {
	private List<notificationSMSresponseVO> messageResponseVO;
	private String canalNotificacionEnum;
	
	public List<notificationSMSresponseVO> getMessageResponseVO() {
		return messageResponseVO;
	}
	public void setMessageResponseVO(List<notificationSMSresponseVO> messageResponseVO) {
		this.messageResponseVO = messageResponseVO;
	}
	public String getCanalNotificacionEnum() {
		return canalNotificacionEnum;
	}
	public void setCanalNotificacionEnum(String canalNotificacionEnum) {
		this.canalNotificacionEnum = canalNotificacionEnum;
	}

	

}
