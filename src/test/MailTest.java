package test;

import org.junit.jupiter.api.Test;

import app.util.Mail;

class MailTest {

	@Test
	void testSend() {
		Mail.send("テストタイトル", "これはテストコンテンツ", "ri-ueno@virtualex.co.jp");
	}
}
