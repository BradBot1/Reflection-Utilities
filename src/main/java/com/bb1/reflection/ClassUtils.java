package com.bb1.reflection;

import java.lang.reflect.Modifier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.bb1.exceptions.handler.ExceptionHandler.handle;

/**
 * 
 * Copyright 2022 BradBot_1
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * A collection of methods to aid with class manipulation
 * 
 * @author BradBot_1
 */
public final class ClassUtils {
	
	private ClassUtils() { }
	/**
	 * Checks if the given class extends the other given class
	 * 
	 * @param checkee The class to check
	 * @param potentialParent The class to checked against
	 * @return If the class extends the other class
	 */
	public static final boolean doesClassExtend(@NotNull final Class<?> checkee, @NotNull final Class<?> potentialParent) {
		Class<?> temp = checkee;
		do {
			if (temp.getSuperclass() == potentialParent) return true;
			temp = temp.getSuperclass();
		} while (temp != Object.class);
		return false;
	}
	/**
	 * Instantiates a child class and returns it
	 * 
	 * @param parent The class that contains the child class
	 * @param childClassName The name of the child class
	 * @param params The parameters of the constructor
	 * @return Instantiates a child class and returns it as long as the child class
	 */
	public static final @Nullable Object instantateChildClass(@NotNull final Object parent, @NotNull final String childClassName, @Nullable final Object... params) {
		for (final Class<?> childClass : parent.getClass().getDeclaredClasses()) {
			if (childClass.getSimpleName().equals(childClassName)) return instantateChildClass(parent, childClass, params);
		}
		return null;
	}
	/**
	 * Instantiates a child class and returns it
	 * 
	 * @param parent The class that contains the child class
	 * @param childClass The child class
	 * @param params The parameters of the constructor
	 * @return Instantiates a child class and returns it as long as the child class
	 */
	public static final @Nullable Object instantateChildClass(@NotNull final Object parent, @NotNull final Class<?> childClass, @Nullable final Object... params) {
		final Class<?> parentClass = parent.getClass();
		if (Modifier.isStatic(childClass.getModifiers())) {
			final Class<?>[] classes = new Class<?>[params == null ? 0 : params.length];
			if (params != null) {
				for (int i = 0; i < classes.length; i++) {
					classes[i] = params[i] == null ? null : params[i].getClass();
				}
			}
			return handle(()->childClass.getDeclaredConstructor(childClass).newInstance(params));
		} else {
			final Class<?>[] classes = new Class<?>[params == null ? 1 : params.length + 1];
			classes[0] = parentClass;
			final Object[] params2 = new Object[classes.length];
			params2[0] = parent;
			if (params != null) {
				for (int i = 1; i < classes.length; i++) {
					classes[i] = params[i] == null ? null : params[i].getClass();
				}
				for (int i = 1; i < classes.length; i++) {
					params2[i] = params[i] == null ? null : params[i];
				}
			}
			return handle(()->childClass.getDeclaredConstructor(classes).newInstance(params2));
		}
	}
	
}
