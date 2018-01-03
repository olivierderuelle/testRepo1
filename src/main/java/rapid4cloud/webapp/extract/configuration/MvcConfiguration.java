package rapid4cloud.webapp.extract.configuration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.InfoEndpoint;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan
public class MvcConfiguration extends WebMvcConfigurerAdapter
{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MvcConfiguration.class);
	
	@Autowired
	private InfoEndpoint infoEndpoint;
	private static String buildVersion;

	@SuppressWarnings("unchecked")
	public void initBuildVersion() {
		buildVersion="N/A";
		Map<String, Object> infoProperties=infoEndpoint.invoke();
		if (!infoProperties.isEmpty()){
			LinkedHashMap<String,Object> values=(LinkedHashMap<String,Object>) infoProperties.get("build");
			if (values!=null) {
				String buildVersionStr=(String) values.get("version");
				if (buildVersionStr!=null) {
					buildVersion=buildVersionStr;
				}
			}
		}
		String str="#### VERSION: "+buildVersion;
		System.out.println(str);
		LOGGER.info(str); 
	}
	
	public static String getBuildVersion() {
		return buildVersion;
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		String currentFolder="#### CURRENT FOLDER: '"+(new File(".")).getAbsolutePath()+"'";
		System.out.println(currentFolder);
		LOGGER.info(currentFolder);
		initBuildVersion();
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setViewClass(JstlView.class);
		registry.viewResolver(resolver);
	}

}