package org.litespring.context.support;

import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

	public ClassPathXmlApplicationContext(String configFile) {
		super(configFile);
		
	}
/*
 * 通过classpath 加载 配置xml文件
 * (non-Javadoc)
 * @see org.litespring.context.support.AbstractApplicationContext#getResourceByPath(java.lang.String)
 */
	@Override
	protected Resource getResourceByPath(String path) {
		
		return new ClassPathResource(path,this.getBeanClassLoader());
	}

}
