package org.litespring.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.litespring.util.ClassUtils;

public class ClassPathResource implements Resource {
/*
 * 属性有path，classLoader
 */
	private String path;
	private ClassLoader classLoader;
/*
 * 单参构造调用多参构造
 */
	public ClassPathResource(String path) {
		this(path, (ClassLoader) null);
	}
	public ClassPathResource(String path, ClassLoader classLoader) {
		this.path = path;
		this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
	}
/*
 * 提供getInputStream方法
 * (non-Javadoc)
 * @see org.litespring.core.io.Resource#getInputStream()
 */
	public InputStream getInputStream() throws IOException {
		InputStream is = this.classLoader.getResourceAsStream(this.path);
		
		if (is == null) {
			throw new FileNotFoundException(path + " cannot be opened");
		}
		return is;
		
	}
	
	public String getDescription(){
		return this.path;
	}

}
