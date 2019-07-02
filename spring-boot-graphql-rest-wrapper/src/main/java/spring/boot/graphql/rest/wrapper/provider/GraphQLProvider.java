package spring.boot.graphql.rest.wrapper.provider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.TypeRuntimeWiring;

@Component
public class GraphQLProvider {

	private GraphQL graphQL;

	@Autowired
	private GraphQLDataFetcher dataFetcher;

	@PostConstruct
	public void init() throws IOException {
		final Resource resource = new ClassPathResource("schema.graphql");
		String sdl = null;
		try {
			sdl = new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		GraphQLSchema graphQLSchema = buildSchema(sdl);
		this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
	}

	private GraphQLSchema buildSchema(String sdl) {
		TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
		RuntimeWiring runtimeWiring = buildWiring();
		SchemaGenerator schemaGenerator = new SchemaGenerator();
		return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
	}

	private RuntimeWiring buildWiring() {
		return RuntimeWiring.newRuntimeWiring()
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("websites", dataFetcher.getWebsiteList()))
				.type(TypeRuntimeWiring.newTypeWiring("Query").dataFetcher("website", dataFetcher.getWebsiteById()))
				.type(TypeRuntimeWiring.newTypeWiring("Mutation").dataFetcher("addWebsite", dataFetcher.addWebsite()))
				.type(TypeRuntimeWiring.newTypeWiring("Mutation").dataFetcher("updateWebsite",
						dataFetcher.updateWebsite()))
				.type(TypeRuntimeWiring.newTypeWiring("Mutation").dataFetcher("deleteWebsite",
						dataFetcher.deleteWebsite()))
				.build();
	}

	@Bean
	public GraphQL graphQL() {
		return graphQL;
	}
}
