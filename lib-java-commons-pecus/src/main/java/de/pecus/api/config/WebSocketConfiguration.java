package de.pecus.api.config;

import java.util.Arrays;
import java.util.List;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import de.pecus.api.constant.GeneralConstants;
import de.pecus.api.util.ValidatorUtil;

public class WebSocketConfiguration extends Configurator {

	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
		List<String> protocols = request.getHeaders().get(HandshakeRequest.SEC_WEBSOCKET_PROTOCOL);
		if (!ValidatorUtil.isNullOrEmpty(protocols)) {
			List<String> authorizationList = Arrays.asList(protocols.get(GeneralConstants.ZERO).split(","));
			config.getUserProperties().put(GeneralConstants.AUTHORIZATION,
					authorizationList.get(GeneralConstants.ZERO).trim());
		}
	}

}
