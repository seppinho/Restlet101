package rest;


import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class Index extends ServerResource {

	@Get
	public Representation get() {

		return new StringRepresentation("this is a hello world resource");

	}

}
