<#assign actualDateTime = .now>

package ${hm.package};

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
public class ${hm.name}Bean {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(${hm.name}Bean.class);

  <#if hm.property.injected_bean_0??>
	private ${injected_bean_0} bean0;
  </#if>
  <#if hm.property.injected_bean_1??>
	private ${injected_bean_1} bean1;
  </#if>
  <#if hm.property.injected_bean_2??>
	private ${injected_bean_2} bean2;
  </#if>

  
  <#if hm.property.method_Java_Spring_POJO??>
	/**
	 * TODO please define what the method does!
	 *
	 * @param  param1 First parameter
	 * @return The value to return
	 * @throws The exception to be thrown
	 */
	 ${hm.property.method_Java_Spring_POJO} {
        // TODO auto generated method - Please fill the method!
		LOGGER.info("The method has been called!");
    }
  </#if>
  

  <#if hm.property.injected_bean_0??>
	public ${injected_bean_0} getBean0() {
		return bean0;
	}
	
    public void setBean0(${injected_bean_0} bean0) {
		this.bean0 = bean0;
	}
  </#if>

  <#if hm.property.injected_bean_1??>
	public ${injected_bean_1} getBean1() {
		return bean1;
	}
	
    public void setBean1(${injected_bean_1} bean1) {
		this.bean1 = bean1;
	}
  </#if>

  <#if hm.property.injected_bean_2??>
	public ${injected_bean_2} getBean2() {
		return bean2;
	}
	
    public void setBean2(${injected_bean_2} bean2) {
		this.bean2 = bean2;
	}
  </#if>

}