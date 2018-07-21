package org.litespring.beans.factory.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;
/*
 * 依靠XmlBeanDefinitionReader解析xml文件 拿到BeanDefinition信息 并将信息封装在BeanDefinition中
 * 所以在需要在构造器中初始化BeanDefinitionRegistry。
 */
public class XmlBeanDefinitionReader {
	/*
	 * 解析字段 id，class，scope
	 */
	public static final String ID_ATTRIBUTE = "id";	

	public static final String CLASS_ATTRIBUTE = "class";
	
	public static final String SCOPE_ATTRIBUTE = "scope";
	/*
	 * 依赖BeanDefinitionRegistry 
	 */
	
	BeanDefinitionRegistry registry;
	
	/*
	 *
	 *构造方法调用BeanDefinitionRegistry ,需要我们手动传递 registry
	 *
	 */
	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
		this.registry = registry;
	}
	/*
	 * 从 配置文件中 拿到 ID，Class，scope，将这些信息封装到BeanDefinition中
	 * 由BeanDefinitionRegistry向Application完成Context注册
	 */
	public void loadBeanDefinitions(Resource resource){
		InputStream is = null;
		try{			
			is = resource.getInputStream();
			SAXReader reader = new SAXReader();
			Document doc = reader.read(is);
			
			Element root = doc.getRootElement(); //<beans>
			Iterator<Element> iter = root.elementIterator();
			while(iter.hasNext()){
				Element ele = (Element)iter.next();
				String id = ele.attributeValue(ID_ATTRIBUTE);
				String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
				BeanDefinition bd = new GenericBeanDefinition(id,beanClassName); //将beanId，beanClassName封装在BeanDefinition中
				if (ele.attribute(SCOPE_ATTRIBUTE)!=null) {					
					bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));					
				}
				this.registry.registerBeanDefinition(id, bd);    //调用registerBeanDefinition 注册beanDefinition
			}
		} catch (Exception e) {		
			throw new BeanDefinitionStoreException("IOException parsing XML document from " + resource.getDescription(),e);
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		}
		
	}
}

