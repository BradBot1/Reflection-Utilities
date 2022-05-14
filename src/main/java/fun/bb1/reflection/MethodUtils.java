package fun.bb1.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
 * A collection of methods to make interacting with methods via reflection easier
 * 
 * @author BradBot_1
 */
public final class MethodUtils {
	
	private MethodUtils() { }
	
	public static final @NotNull Method[] getInheritedMethods(Class<?> of, @Nullable List<Class<?>> done) {
		if (done==null) done = new ArrayList<Class<?>>(); // if the given list of already checked classes is null we set it to an empty list
		final List<Method> methods = new ArrayList<Method>();
		Class<?> temp = of;
		do {
			if (!done.contains(temp)) {
				for (final Method method : temp.getDeclaredMethods()) {
					methods.add(method);
				}
				done.add(temp);
			}
			for (final Class<?> inter : temp.getInterfaces()) {
				if (done.contains(inter)) continue; // already checked
				for (final Method method : getInheritedMethods(inter, done)) {
					methods.add(method);
				}
			}
			temp = temp.getSuperclass();
		} while(temp != Object.class);
	    return methods.toArray(new Method[methods.size()]);
	}
	
	public static final @NotNull Method[] getInheritedMethodsWithAnnotation(Class<?> of, @NotNull final Class<? extends Annotation> annotation, @Nullable List<Class<?>> done) {
		if (done==null) done = new ArrayList<Class<?>>(); // if the given list of already checked classes is null we set it to an empty list
		final List<Method> methods = new ArrayList<Method>();
		Class<?> temp = of;
		do {
			if (!done.contains(temp)) {
				for (final Method method : temp.getDeclaredMethods()) {
					if (!method.isAnnotationPresent(annotation)) continue;
					methods.add(method);
				}
				done.add(temp);
			}
			for (final Class<?> inter : temp.getInterfaces()) {
				if (done.contains(inter)) continue; // already checked
				for (final Method method : getInheritedMethods(inter, done)) {
					if (!method.isAnnotationPresent(annotation)) continue;
					methods.add(method);
				}
			}
			temp = temp.getSuperclass();
		} while(temp != Object.class);
	    return methods.toArray(new Method[methods.size()]);
	}
	
	public static final @Nullable Object invokeMethod(@NotNull final Method method, @NotNull final Object from, @Nullable final Object... parameters) {
		try {
			method.setAccessible(true);
			return method.invoke(from, parameters);
		} catch (Throwable t) {
			return null;
		}
	}
	
	public static final @Nullable Object invokeStaticMethod(@NotNull final Method method, @Nullable final Object... parameters) {
		return invokeMethod(method, null, parameters);
	}
	
}
