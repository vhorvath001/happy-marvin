<#assign actualDateTime = .now>

package ${hm.package};

/**
 *
 * @author      Firstname Lastname <address @ example.com>
 * @since       ${actualDateTime?string("yyyy-MM-dd")}
 *
 */
public interface ${hm.name}${hm.file.suffix} {

	void doSomething();
	
}
