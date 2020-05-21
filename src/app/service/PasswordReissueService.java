package app.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import app.dao.AccountsDAO;
import app.model.Account;
import app.util.Mail;

public class PasswordReissueService {
	public static boolean call(String mailAddress) {
		AccountsDAO accountsDAO = new AccountsDAO();

		String password = generatePassword();
		Account account = accountsDAO.getByMailAddress(mailAddress);

		if (account.getId() != 0 && accountsDAO.updateAccountPassword(mailAddress, password)) {
			String message =
					account.getName() + "さん\n" +
					"図書管理システムです。\n" +
					"\n" +
					"パスワードを再発行しました。以下のパスワードでログインしてください。\n" +
					"【新パスワード】\n" +
					password + "\n" +
					"\n" +
					"※このメールアドレスは送信専用です。返信されても対応できません。";
			Mail.send("【図書管理システム】パスワード再発行", message, mailAddress);
			return true;
		}

		return false;
	}

	protected static String generatePassword() {
	    byte token[] = new byte[4];
	    StringBuffer buf = new StringBuffer();
	    SecureRandom random = null;

	    try {
	      random = SecureRandom.getInstance("SHA1PRNG");
	      random.nextBytes(token);

	      for (int i = 0; i < token.length; i++) {
	        buf.append(String.format("%02x", token[i]));
	      }

	    } catch (NoSuchAlgorithmException e) {
	      e.printStackTrace();
	    }

	    return buf.toString();
	 }
}
