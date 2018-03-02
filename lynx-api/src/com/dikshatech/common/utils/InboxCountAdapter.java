package com.dikshatech.common.utils;

import org.apache.log4j.Logger;
import com.dikshatech.portal.exceptions.InboxDaoException;
import com.dikshatech.portal.factory.InboxDaoFactory;
import flex.messaging.MessageBroker;
import flex.messaging.messages.AsyncMessage;
import flex.messaging.messages.Message;
import flex.messaging.services.MessageService;
import flex.messaging.services.ServiceAdapter;
import flex.messaging.util.UUIDUtils;

/**
 * @author y.suresh
 */
public class InboxCountAdapter extends ServiceAdapter {

	/*
	 * @see
	 * flex.messaging.services.ServiceAdapter#invoke(flex.messaging.messages.Message)
	 */
	private static String			clientId;
	private static MessageBroker	msgBroker;
	protected static final Logger	logger	= Logger.getLogger(InboxCountAdapter.class);

	/**
	 * @param userId
	 */
	public static void invokeUser(int userId) {
		try{
			if (clientId != null && msgBroker != null){
				AsyncMessage msg = new AsyncMessage();
				msg.setDestination("InboxPush");
				msg.setClientId(clientId);
				msg.setMessageId(UUIDUtils.createUUID());
				String p = generateInboxMessageCount(userId);
				msg.setBody(p);
				logger.info("Sending Inbox unread count to " + p);
				msgBroker.routeMessageToService(msg, null);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void start() {
		if (msgBroker == null) synchronized (InboxCountAdapter.class){
			if (msgBroker == null){
				clientId = UUIDUtils.createUUID();
				msgBroker = MessageBroker.getMessageBroker(null);
				logger.info("Adapter started");
			}
		}
	}

	public void stop() {
		logger.info("Adapter stopped");
		clientId = null;
		msgBroker = null;
	}

	@Override
	public Object invoke(Message msg) {
		logger.info("Adapter sending Inbox Count");
		AsyncMessage newMessage = (AsyncMessage) msg;
		MessageService msgService = (MessageService) getDestination().getService();
		msgService.pushMessageToClients(newMessage, true);
		return null;
	}

	/**
	 * @param userId
	 * @return new String[]{userId, msgUnReadCount}
	 */
	private static String generateInboxMessageCount(int userId) {
		return userId + "," + ModelUtiility.getInstance().getUnreadNotifications(userId);
	}
}
