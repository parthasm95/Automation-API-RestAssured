package com.automazing.util;

import java.util.Random;

public class RandomUtil {
	
	public static int gerenateInt() {
		Random random = new Random();
		int randomNumber = random.nextInt();
		return randomNumber;
	}

}
