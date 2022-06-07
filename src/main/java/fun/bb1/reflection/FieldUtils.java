package fun.bb1.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
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
 * A collection of methods to make interacting with fields via reflection easier
 * 
 * @author BradBot_1
 */
public final class FieldUtils {
	
	private FieldUtils() { }
	
	public static final @NotNull Field[] getInheritedFields(@NotNull final Class<?> of) {
		final List<Field> fields = new ArrayList<Field>();
		Class<?> temp = of;
		do {
			for (final Field field : temp.getDeclaredFields()) {
				fields.add(field);
			}
			temp = temp.getSuperclass();
		} while(temp != Object.class);
	    return fields.toArray(new Field[fields.size()]);
	}
	
	public static final @NotNull Field[] getInheritedFieldsWithAnnotation(@NotNull final Class<?> of, @NotNull final Class<? extends Annotation> annotation) {
		final List<Field> fields = new ArrayList<Field>();
		Class<?> temp = of;
		do {
			for (final Field field : temp.getDeclaredFields()) {
				if (!field.isAnnotationPresent(annotation)) continue; // not present
				fields.add(field);
			}
			temp = temp.getSuperclass();
		} while(temp != Object.class);
	    return fields.toArray(new Field[fields.size()]);
	}
	
	public static final @Nullable Object getField(@NotNull final Field field, @NotNull final Object from) {
		try {
			field.setAccessible(true);
			return field.get(from);
		} catch (Throwable t) {
			return null;
		}
	}
	
	public static final boolean setField(@NotNull final Field field, @NotNull final Object on, @NotNull final Object to) {
		try {
			field.setAccessible(true);
			field.set(on, to);
			return true;
		} catch (Throwable t) {
			return false;
		}
	}
	
}
