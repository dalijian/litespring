package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/*
 * 配置文件接口，提供InputStream，在XmlBeanDefinitionReader中载入
 */
public interface Resource {
	public InputStream getInputStream() throws IOException;
	public String getDescription();
}
