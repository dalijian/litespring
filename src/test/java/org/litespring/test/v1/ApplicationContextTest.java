package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemXmlApplicationContext;
import org.litespring.service.v1.PetStoreService;

public class ApplicationContextTest {

	@Test
	public void testGetBean() {
		/*
		 * 新建ClassPathXmlApplicationContext(传入configFile),-->AbstractBeanFactory()构造 新建DefaultBeanfactory对象，
		 * 由于BeanDefinitionFactory对象实现了DefaultBeanRegistry，将他传入XmlBeanDefinitionReader(BeanDefinitionRegistry registry)，
		 * 由于xmlBeanDefinitionReader只处理InputStream,所以先调用getResourceByPath将configFile传入，由具体子类实现，再将结果以InputStream形式返回，
		 * 传入XmlBeanDefinitionReader.loadBeanDefinitions 得到关于ConfigFile的bean信息，再传入BeanDefaultFactory，factory再设置BeanClassLoader
		 */
		ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
		/*
		 * 由AbstractApplicationContext 返回factory.getBean();getBean()就是从Map中查找出对应的BeanClass在判断是否是Singleton，然后实现反射生成javaBean对象
		 */
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		Assert.assertNotNull(petStore);
	}
    @Test 
	public void testGetBeanFromFileSystemContext(){
	    //注意啊，这里仍然是hardcode了一个本地路径，这是不好的实践!! 如何处理，留作作业
		/*ApplicationContext ctx = new FileSystemXmlApplicationContext("C:\\Users\\liuxin\\git-litespring\\src\\test\\resources\\petstore-v1.xml");
		PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
		Assert.assertNotNull(petStore);*/
		
	}

}
