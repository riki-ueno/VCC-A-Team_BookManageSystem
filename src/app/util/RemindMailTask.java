package app.util;

import java.util.List;
import java.util.TimerTask;

import app.dao.RemindDAO;
import app.model.Remind;

public class RemindMailTask extends TimerTask {

	public RemindMailTask() {
		super();
	}

	@Override
	public void run() { // 実行内容
		 List<Remind> remindList = getDelinquentList();
		 remindMail(remindList);
	}

	public static List<Remind> getDelinquentList() { // 延滞リストの取得
		RemindDAO remindDao = new RemindDAO();
		List<Remind> remindList = remindDao.RemindList();
		return remindList;
	}

	public static void remindMail(List<Remind> remindList) { // 延滞リストからメールを作って1件ずつ送信する
		// 送信アドレスは"testAddress.lib@gmail.com"

		// テスト用受信アドレス
//		String reciever = "testAddress.lib@gmail.com";

		String subject = "【図書管理システム】未返却図書のお知らせ";
		for (int i = 0; i < remindList.size(); i++) {
			Remind remind = remindList.get(i);
			String reciever = remind.getMailAddress();
			String accountName = remind.getAccountName();

			String delayBookInformation = "・" + remind.getBookTitle() + "/" + remind.getPublisherName()
					+ "　返却期限:" + remind.getScheduledReturnDate();

			String content = accountName + "さん 図書管理システムです。\n" + "\n" + "現在以下の書籍が返却されていません。速やかに返却してください。\n"
					+ "システムでの返却処理も忘れずお願いします。\n" + "【未返却書籍】\n" + delayBookInformation + " \n" + "\n"
					+ "※このメールは送信専用です。返信されても対応できません。";
			Mail.send(subject, content, reciever);

		}
	}
}
