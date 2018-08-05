package org.litespring.core.type.classreading;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;

/**
 * ClassMetadataReadingVisitor 具体实现类不处理Annotation
 * AnnotationMetadata 接口 提供 关于Annotation 的方法
 * 依赖    AnnotationAttributesReadingVisitor 得到 annotation 的属性  将属性封装到map中
 * AnnotationMetadataReadingVisitor 是一个集大成者类，他将其他类联系起来
 */
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements  AnnotationMetadata {
	
	/*LinkedHashSet 保存 class字节码文件中的annotation
	 * 具有可预知迭代顺序的 Set 接口的哈希表和链接列表实现。此实现与 HashSet 的不同之外在于，后者维护着一个运行于所有条目的双重链接列表。
	 */
	private final Set<String> annotationSet = new LinkedHashSet<String>(4);
	/*
	 * LinkedHashMap 保存class字节码文件中的annotation 和他的属性
	 * AnnotationAttributesReadingVisitor 是如何将attributeMap传递给AnnotationMetadataReadingVisitor 类中的attributeMap的？？？？？
	 */
	private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<String, AnnotationAttributes>(4);
	
	public AnnotationMetadataReadingVisitor() {
		
	}
	@Override
	public AnnotationVisitor visitAnnotation(final String desc, boolean visible) {
		/*
		 * 将class字节码 对象转换成 String 类型 例如 org.literspring.v4.Compenent
		 */
		String className = Type.getType(desc).getClassName();
		//保存 className
		this.annotationSet.add(className);
		//依赖 AnnotationAttributesReadingVisitor 拿到 className 的 annotation 属性
		return new AnnotationAttributesReadingVisitor(className, this.attributeMap);
	}
	public Set<String> getAnnotationTypes() {
		return this.annotationSet;
	}

	public boolean hasAnnotation(String annotationType) {
		return this.annotationSet.contains(annotationType);
	}

	public AnnotationAttributes getAnnotationAttributes(String annotationType) {
		return this.attributeMap.get(annotationType);
	}

	
	
}
