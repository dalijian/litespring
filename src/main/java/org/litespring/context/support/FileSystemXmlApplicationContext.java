package org.litespring.context.support;

import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;
/*
 * 通过文件系统加载配置文件
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

	public FileSystemXmlApplicationContext(String path) {
		super(path);		
	}

	@Override
	protected Resource getResourceByPath(String path) {
		return new FileSystemResource(path);
	}	

}
