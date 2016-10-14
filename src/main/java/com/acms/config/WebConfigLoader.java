package com.acms.config;

import java.io.InputStream;
import java.util.Properties;

public class WebConfigLoader {
	private WebConfigLoader() {
	}

	private static VVConfig config;
	static {
		try {
			config = loadConfig();
		} catch (Exception ex) {
			// quit the system
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	public static VVConfig getConfig() {
		return config;
	}

	private static VVConfig loadConfig() throws Exception {
		InputStream in = null;
		try {
			in = WebConfigLoader.class.getClassLoader().getResourceAsStream(
					"vvconf.properties");
			Properties p = new Properties();
			p.load(in);
			VVConfig con = new VVConfig();
			for (Object k : p.keySet()) {
				String key = (String) k;
				con.setConfig(key, p.getProperty(key));
				//System.out.println("VVConfig:    Key:[" + key + "]" + "  Value:[" + p.getProperty(key) + "].");
			}
			return con;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				System.exit(-1);
			}
		}
	}
}
