package org.litespring.core.type.classreading;



import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.litespring.core.io.Resource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

//MetadataReader 的实现类  可以拿到 classMetadata 和AnnotationMetaData
public class SimpleMetadataReader implements MetadataReader {

	private final Resource resource;

	private final ClassMetadata classMetadata;

	private final AnnotationMetadata annotationMetadata;


	public SimpleMetadataReader(Resource resource) throws IOException {
		// BufferedInputStream 为另一个输入流添加一些功能，即缓冲输入以及支持 mark 和 reset 方法的能力
		InputStream is = new BufferedInputStream(resource.getInputStream());
		ClassReader classReader;
		
		try {
			//读取输入流
			classReader = new ClassReader(is);
		}
		finally {
			is.close();
		}
		//AnnotationMetadataReadingVisitor 拿到AnnotationMetadata
		AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
		//classReader  ASM 将读取的annotation  注册到 visitor中
		classReader.accept(visitor, ClassReader.SKIP_DEBUG);

		this.annotationMetadata = visitor;
		this.classMetadata = visitor;
		this.resource = resource;
	}


	public Resource getResource() {
		return this.resource;
	}

	public ClassMetadata getClassMetadata() {
		return this.classMetadata;
	}

	public AnnotationMetadata getAnnotationMetadata() {
		return this.annotationMetadata;
	}

}
