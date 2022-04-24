package com.bb1.reflection;

import org.jetbrains.annotations.NotNull;

public final class ClassUtils {
	
	private ClassUtils() { }
	
	public static final boolean doesClassExtend(@NotNull final Class<?> checkee, @NotNull final Class<?> potentialParent) {
		Class<?> temp = checkee;
		do {
			if (temp.getSuperclass() == potentialParent) return true;
			temp = temp.getSuperclass();
		} while (temp != Object.class);
		return false;
	}
	
}
