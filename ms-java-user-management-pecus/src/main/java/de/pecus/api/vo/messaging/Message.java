package de.pecus.api.vo.messaging;

import java.util.Objects;

/**
 * Mensaje de la notificacion
 */

public class Message   {
	private String title = null;
	private String text = null;
	private String name = null;
	public Message title(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Get title
	 * @return title
	 **/
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Message text(String text) {
		this.text = text;
		return this;
	}

	/**
	 * Get text
	 * @return text
	 **/
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Message [title=" + title + ", text=" + text + ", name=" + name + "]";
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Message message = (Message) o;
		return Objects.equals(this.title, message.title) &&
				Objects.equals(this.text, message.text);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, text);
	}

}
