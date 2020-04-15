package ro.msg.learning.service.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ro.msg.learning.dto.OrderDto;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:mail/messages.properties")
public class EmailService {

	private static final String EMAIL_TEXT_TEMPLATE_NAME = "email-text.html";
	private static final String EMAIL_SIMPLE_TEMPLATE_NAME = "email-simple-html.html";

	private final TemplateEngine templateEngine;

	private final JavaMailSender emailSender;

	@Value("${spring.mail.subject}")
	private String subject;

	/*
	 * Send plain TEXT mail
	 */
	@SneakyThrows
	public void sendTextMail(final String recipent, final OrderDto orderDto, final Integer orderId)
			{

		final Context ctx = prepareEvaluationContext(orderDto, orderId);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = emailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		message.setSubject(subject);
		message.setTo(recipent);

		// Create the plain TEXT body using Thymeleaf
		final String textContent = templateEngine.process(EMAIL_TEXT_TEMPLATE_NAME, ctx);
		message.setText(textContent);

		emailSender.send(mimeMessage);
	}

	@SneakyThrows
	/*
	 * Send simple HTML mail
	 */
	public void sendSimpleHtmlMail(final String recipent, final OrderDto orderDto, final Integer orderId) {

		final Context ctx = prepareEvaluationContext(orderDto, orderId);

		// Prepare message using a Spring helper
		final MimeMessage mimeMessage = emailSender.createMimeMessage();
		final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
		message.setSubject(subject);
		message.setTo(recipent);

		// Create the HTML body using Thymeleaf
		final String htmlContent = templateEngine.process(EMAIL_SIMPLE_TEMPLATE_NAME, ctx);
		message.setText(htmlContent, true); // true = isHtml

		emailSender.send(mimeMessage);
	}
	
	private Context prepareEvaluationContext(final OrderDto orderDto, final Integer orderId) {
		final Context ctx = new Context();
		ctx.setVariable("order", orderDto);
		ctx.setVariable("orderId", orderId);
		return ctx;
	}
}