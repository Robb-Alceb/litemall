package org.linlinjava.litemall.admin.util;

import java.util.Random;

public class RandomUtils {

	private static final Random RANDOM = new Random();

	public static void main(String[] args) {
		String random = RandomUtils.random(8);
		System.out.println(random);
	}

	public static String random(int count) {
		char[] buffer = new char[count];
		int end = 'z' + 1;
		int start = ' ';

		int gap = end - start;

		while (count-- != 0) {
			char ch = (char) (RANDOM.nextInt(gap));

			if (Character.isLetter(ch) || Character.isDigit(ch)) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						// low surrogate, insert high surrogate after putting it in
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + RANDOM.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						// high surrogate, insert low surrogate before putting it in
						buffer[count] = (char) (56320 + RANDOM.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					// private high surrogate, no effing clue, so skip it
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}

	/**
	 * 生成不重复随机字符串包括字母数字
	 *
	 * @param len
	 * @return
	 */
	public static String generateRandomStr(int len) {
		//字符源，可以根据需要删减
		String generateSource = "0123456789abcdefghigklmnopqrstuvwxyz";
		String rtnStr = "";
		for (int i = 0; i < len; i++) {
			//循环随机获得当次字符，并移走选出的字符
			String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
			rtnStr += nowStr;
			generateSource = generateSource.replaceAll(nowStr, "");
		}
		return rtnStr;
	}
	/**
	 * 获取申请货品订单号
	 * @return
	 */
	public static String getMerchandiseOrderId() {
		return "MOI" + System.currentTimeMillis() + RandomUtils.generateRandomStr(3);
	}
}