package org.litespring.beans.factory;
/*
 * bean工厂，提供给客户端用
 */
public interface BeanFactory {

	Object getBean(String beanID);

}
