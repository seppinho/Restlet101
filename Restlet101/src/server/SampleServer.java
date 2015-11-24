package server;
    import java.io.File;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Parameter;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.restlet.util.Series;

import rest.Index;

    public class SampleServer {
    	
	public static void main(String[] args)  {

		// Create a new Component.
		Component component = new Component();

		// Add a new HTTP server listening on port 8080
		Server server = component.getServers().add(Protocol.HTTP, 8080);

		// Add HTTPS if file exists
		String file = "location-to-your-file";
		File keystoreFile = new File(file);
		
		if (keystoreFile.exists()) {
			
			component.getServers().add(Protocol.HTTPS, 4443);
			Series<Parameter> parameters = server.getContext().getParameters();
			parameters.add("sslContextFactory",
					"org.restlet.engine.ssl.DefaultSslContextFactory");
			component
					.getContext()
					.getParameters()
					.add("enabledCipherSuites",
							"TLS_DHE_RSA_WITH_AES_256_GCM_SHA384"
									+ " TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256"
									+ " TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256"
									+ " TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384"
									+ " TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256"
									+ " TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256"
									+ " TLS_RSA_WITH_AES_256_CBC_SHA"
									+ " TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA"
									+ " TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA"
									+ " TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA"
									+ " TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA"
									+ " TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA");

			parameters.add("keyStorePath", keystoreFile.getAbsolutePath());
			parameters.add("keyStorePassword", "password");
			parameters.add("keyPassword", "password");
			parameters.add("keyStoreType", "JKS");
			parameters.add("allowRenegotiate", "false");
		}

		component.getDefaultHost().attach(new Application() {
			@Override
			public Restlet createInboundRoot() {
				Router router = new Router();
				router.attach("/", Index.class);

				return router;
			}
		});

		// Start the component.
		try {
			component.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    }