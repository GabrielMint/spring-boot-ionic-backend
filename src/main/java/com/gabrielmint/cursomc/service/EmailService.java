package com.gabrielmint.cursomc.service;

import org.springframework.mail.SimpleMailMessage;

import com.gabrielmint.cursomc.domain.Pedido;

public interface EmailService {
	
		void sendOrderConfirmationEmail(Pedido obj);
		
		void sendEmail(SimpleMailMessage msg);
}
