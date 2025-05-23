package de.pecus.api.vo.messaging;

import java.util.Objects;

public class SendMailMessageResponseVO   {
	private Integer idUser;
	private boolean status;
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SendPushMessageResponseVO [idUser=" + idUser + ", status=" + status + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(idUser, status);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SendMailMessageResponseVO other = (SendMailMessageResponseVO) obj;
		return Objects.equals(idUser, other.idUser) && status == other.status;
	}


}
