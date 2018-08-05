package org.litespring.context.annotation;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.stereotype.Component;
import org.litespring.util.StringUtils;

/*
 * 对指定的package进行扫描，找到那些标记为@Component的类，创建
 * ScannedGenericBeanDefinition,并且注册到BeanFactory中
 */
public class ClassPathBeanDefinitionScanner {
	
	/*
	 * 通过registry 拿到 beanFactory
	 */
	private final BeanDefinitionRegistry registry;
	/*
	 * packageResourceLoader  类 扫描包并拿到 Set<resource>
	 * 
	 */
	
	private PackageResourceLoader resourceLoader = new PackageResourceLoader();
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	/*
	 * AnnotationBeanNameGenerator 注解名称生成器 id默认为类名的首字母小写
	 */
	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {		
		this.registry = registry;		
	}
	
	public Set<BeanDefinition> doScan(String packagesToScan) {
		//tokenizeToStringArray这个函数用于将给定的字符串按照给定的分隔符分隔成字符串数组，这里就是 把nameAttr按照“,”分隔开
		String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan,",");
		
		Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();
		for (String basePackage : basePackages) {
			Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
			for (BeanDefinition candidate : candidates) {
				beanDefinitions.add(candidate);
				registry.registerBeanDefinition(candidate.getID(),candidate);
				
			}
		}
		return beanDefinitions;
	}
	
	
	//从包名中拿到Set<BeanDefinition>
	public Set<BeanDefinition> findCandidateComponents(String basePackage) {
		Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
		try {
			//通过PackageResourceLoader 包资源加载器 拿到包下所有资源
			Resource[] resources = this.resourceLoader.getResources(basePackage);
			
			for (Resource resource : resources) {
				try {
					
					MetadataReader metadataReader = new SimpleMetadataReader(resource);
				
					if(metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())){
						ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
						String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
						sbd.setId(beanName);
						candidates.add(sbd);					
					}
				}
				catch (Throwable ex) {
					throw new BeanDefinitionStoreException(
							"Failed to read candidate component class: " + resource, ex);
				}
				
			}
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
		}
		return candidates;
	}
	
}
