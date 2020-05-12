package com.ranjan.aws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/awssqs")
public class SQSController {

	private static final Logger log = LoggerFactory.getLogger(SQSController.class);

	@Autowired
	private QueueMessagingTemplate queue;

	@Value("${cloud.aws.end-point.uri}")
	private String sqsEndPoint;

	@GetMapping
	public void sendMessage() {
		queue.send(sqsEndPoint, MessageBuilder.withPayload("hello from spring boot").build());
		log.info("Message sent to SQS.");
	}
	
	@SqsListener("spring-boot-sqs") // pass the Queue name // it will read the message automatically as soon as the app is up
	public String getMessage(String message) {
		log.info("Mesage from SQS Queue : " + message);
		return message;
	}

}
